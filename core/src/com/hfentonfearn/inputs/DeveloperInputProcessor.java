package com.hfentonfearn.inputs;

import com.badlogic.gdx.InputAdapter;
import com.hfentonfearn.utils.DeveloperTools;

import static com.badlogic.gdx.Input.Keys;

public class DeveloperInputProcessor extends InputAdapter {

    @Override
    public boolean keyDown (int keycode) {
        switch(keycode){
            case Keys.F1:
                DeveloperTools.incPlayerSpeedState();
                return true;
            case Keys.F2:
                DeveloperTools.decPlayerSpeedState();
                return true;
            case Keys.F4:
                DeveloperTools.spawnDeadEnemyAtCursor();
                return true;
            case Keys.F5:
                DeveloperTools.spawnEnemyAtCursor();
                return true;
        }
        return false;
    }

}
