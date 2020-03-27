package com.hfentonfearn.ui.actions;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.hfentonfearn.GameManager;
import com.hfentonfearn.entitysystems.GUISystem;
import com.hfentonfearn.ui.DockDialog;
import com.hfentonfearn.utils.AssetLoader;

public class DockActionButton extends ActionButton{

    GUISystem guiSystem;

    public DockActionButton(GUISystem guiSystem) {
        super(new Sprite(AssetLoader.actions.dock));
        this.guiSystem = guiSystem;
    }

    @Override
    public void onClick() {
        //Show Docks Gui
        GameManager.pause();
        new DockDialog(AssetLoader.skin, guiSystem).show(guiSystem.getStage());
    }

}
