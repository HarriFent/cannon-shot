package com.hfentonfearn.entitysystems;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.hfentonfearn.GameManager;
import com.hfentonfearn.inputs.Keybinds;

public class InputSystem extends EntitySystem implements InputProcessor {

    private InputMultiplexer multiplexer;
    
    @Override
    public void addedToEngine (Engine engine) {
        super.addedToEngine(engine);
        initalizeInput();
    }

    public InputMultiplexer getMultiplexer () {
        return multiplexer;
    }

    private void initalizeInput() {
        multiplexer = new InputMultiplexer();
        multiplexer.addProcessor(this);
        Gdx.input.setInputProcessor(multiplexer);
    }

    @Override
    public boolean keyDown(int keycode) {
        switch (keycode) {
            // Keybindings for selecting squads
            case Keybinds.FORWARD:
                return true;
            case Keybinds.BACKWARD:
                return true;
            case Keybinds.TURN_LEFT:
                return true;
            case Keybinds.TURN_RIGHT:
                return true;
            case Keybinds.ZOOM_IN:
                return true;
            case Keybinds.ZOOM_OUT:
                return true;

            case Input.Keys.ESCAPE:
                GameManager.pause();
                /*PauseDialog dialog = new PauseDialog(Assets.skin);
                dialog.show(guiSystem.getStage());*/
                return true;

        }

        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}
