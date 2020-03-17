package com.hfentonfearn.entitysystems;

import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.gdx.utils.Array;
import com.hfentonfearn.GameManager;
import com.hfentonfearn.ui.actions.ActionButton;
import com.hfentonfearn.ui.actions.DockActionButton;

public class PlayerActionSystem extends EntitySystem {

    private GUISystem guiSystem;
    private static Array<ActionButton> actions;

    public PlayerActionSystem(GUISystem guiSystem) {
        this.guiSystem = guiSystem;
        actions.add(new DockActionButton());
    }

    public static <T extends ActionButton> void showButton(Class<T> actionButtonClass) {
        for (ActionButton a : actions ) {
            if (actionButtonClass.isInstance(a)) {
                a.show();
            }
        }
    }

    @Override
    public void update(float deltaTime) {

    }

    @Override
    public boolean checkProcessing () {
        return !GameManager.isPaused();
    }
}
