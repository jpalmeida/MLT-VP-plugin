package br.ufes.inf.nemo.mltplugin.model;

import java.util.ArrayList;
import java.util.List;

import br.ufes.inf.nemo.mltplugin.LogUtilitary;

import com.vp.plugin.model.IGeneralization;
import com.vp.plugin.model.IGeneralizationSet;
import com.vp.plugin.model.IModelElement;

public class GeneralizationSetWrapper extends ModelElementWrapper {

	GeneralizationSetWrapper(IGeneralizationSet source) {
		super(source);
	}
	
	@Override
	public IGeneralizationSet getSourceEntity(){
		return (IGeneralizationSet) super.getSourceEntity();
	}
	
	public String getPowerTypeId(){
		final IModelElement powerType = getSourceEntity().getPowerType();
		if (powerType!=null) {
			return powerType.getId();
		}
		return null;
	}
	
	/*
	 * The method is null safe, because a generalization set will have at least one generalization
	 */
	public String getSuperTypeId(){
		return getSourceEntity().toGeneralizationArray()[0].getFrom().getId();
	}
	
	/*
	 * The method is null safe, because a generalization set will have at least one generalization
	 */
	public String[] getSubTypesId(){
		final List<String> ret = new ArrayList<String>();
		for (IGeneralization generalization : getSourceEntity().toGeneralizationArray()) {
			ret.add(generalization.getTo().getId());
		}
		return ret.toArray(new String[0]);
	}
	
	public boolean isDisjoint(){
		return getSourceEntity().isDisjoint();
	}
	
	public boolean isCovering(){
		return getSourceEntity().isCovering();
	}
	
	@Override
	public String report() {
		return "SET, ID="+getId()
				+", SUPER_ID="+getSuperTypeId()
				+", N_SUBTYPES="+getSubTypesId().length
				+", POWERTTYPE="+getPowerTypeId();
	}
	
	@Override
	public void validate() {
		checkMatchingUMLPowerTypeMLTCharacterizer();
	}

	/*
	 * Working just fine
	 * 
	 * If the GS has a power type, checks if there is a <<instantion>>
	 * relation between the super type and the power type.
	 */
	private void checkMatchingUMLPowerTypeMLTCharacterizer() {
//		LogUtilitary.log("another");
		if(getPowerType().isPowertype()){
			LogUtilitary.log("ERRO: '"+getPowerType().getName()
					+"' cannot classify a generalization set since it's a power type."
					);
		} else if(!getSuperType().isBaseTypeof(getPowerTypeId())){
			LogUtilitary.log("ERRO: Missing <<instantitation>> between characterizer type ("
				+(getPowerType()==null ? "" : getPowerType().getName())+") and  base type ("
				+getSuperType().getName()+") in set("+getSourceEntity().getName()
				+" ID="+getId()+")");
		}
	}

	public ClassWrapper getSuperType() {
		return (ClassWrapper) ModelManager.getModelCopy().get(getSuperTypeId());
	}

	public ClassWrapper getPowerType() {
		return (ClassWrapper) ModelManager.getModelCopy().get(getPowerTypeId());
	}

}