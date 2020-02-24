package com.hfentonfearn.entitysystems;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.hfentonfearn.GameManager;
import com.hfentonfearn.inputs.Keybinds;
import com.hfentonfearn.ui.PauseDialog;
import com.hfentonfearn.utils.AssetLoader;

public class InputSystem extends EntitySystem implements InputProcessor {

    private InputMultiplexer multiplexer;
    private GUISystem guiSystem;

    public InputSystem (GUISystem guiSystem) {
        this.guiSystem = guiSystem;
    }
    
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
        multiplexer.addProcessor(guiSystem.getStage());
        Gdx.input.setInputProcessor(multiplexer);
    }

    public void driveForward() {

    }

    public void driveBackward() {

    }

    public void turnLeft() {

    }

    public void turnRight() {

    }

    @Override
    public boolean keyDown(int keycode) {
        switch (keycode) {
            case Keybinds.FORWARD:
                driveForward();
                return true;
            case Keybinds.BACKWARD:
                driveBackward();
                return true;
            case Keybinds.TURN_LEFT:
                turnLeft();
                return true;
            case Keybinds.TURN_RIGHT:
                turnRight();
                return true;
            case Keybinds.ZOOM_IN:
                //Z00M In

                return true;
            case Keybinds.ZOOM_OUT:
                //Z00M Out

                return true;

            case Input.Keys.ESCAPE:
                GameManager.pause();
                PauseDialog dialog = new PauseDialog(AssetLoader.skin);
                dialog.show(guiSystem.getStage());
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
