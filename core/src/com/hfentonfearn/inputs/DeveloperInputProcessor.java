package com.hfentonfearn.inputs;

import com.badlogic.gdx.InputAdapter;
import com.hfentonfearn.utils.DeveloperTools;

import static com.badlogic.gdx.Input.Keys;

public class DeveloperInputProcessor extends InputAdapter {

    @Override
    public boolean keyDown (int keycode) {
        switch(keycode){
            case Keys.F5:
                DeveloperTools.spawnEnemyAtCursor();
                return true;
        }
        return false;
    }

}
