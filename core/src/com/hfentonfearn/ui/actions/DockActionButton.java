package com.hfentonfearn.ui.actions;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.hfentonfearn.utils.AssetLoader;

public class DockActionButton extends ActionButton{

    public DockActionButton() {
        super(new Sprite(AssetLoader.actions.dock));
    }

    @Override
    public void onClick() {
    //Show Docks Gui
    }
}
