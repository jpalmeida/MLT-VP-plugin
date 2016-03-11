package br.ufes.inf.nemo.mltplugin.actions;

import br.ufes.inf.nemo.mltplugin.model.ModelManager;

import com.vp.plugin.action.VPAction;
import com.vp.plugin.action.VPActionController;

public class RunMLTPluginActionController implements VPActionController {

	public void performAction(VPAction arg0) {
		ModelManager.populateModel();
	}

	public void update(VPAction arg0) {}

}
