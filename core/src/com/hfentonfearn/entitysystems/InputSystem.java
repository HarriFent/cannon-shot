package com.hfentonfearn.entitysystems;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.hfentonfearn.GameManager;
import com.hfentonfearn.components.AccelerationComponent;
import com.hfentonfearn.components.PlayerComponent;
import com.hfentonfearn.components.ShipStatisticComponent;
import com.hfentonfearn.inputs.Keybinds;
import com.hfentonfearn.ui.PauseDialog;
import com.hfentonfearn.utils.AssetLoader;
import com.hfentonfearn.utils.Components;

public class InputSystem extends EntitySystem implements InputProcessor {

    private InputMultiplexer multiplexer;
    private GUISystem guiSystem;
    private Entity player;

    public InputSystem (GUISystem guiSystem) {
        this.guiSystem = guiSystem;
    }
    
    @Override
    public void addedToEngine (Engine engine) {
        super.addedToEngine(engine);
        initalizeInput();
    }

    @Override
    public void update(float deltaTime) {
        if (player == null && PlayerComponent.player != null)
            player = PlayerComponent.player;
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

    @Override
    public boolean keyDown(int keycode) {
        switch (keycode) {
            case Keybinds.FORWARD:
            case Keybinds.BACKWARD:
            case Keybinds.TURN_LEFT:
            case Keybinds.TURN_RIGHT:
                if (player != null)
                    setPlayerVelocity(keycode);
                return true;
            case Keybinds.ZOOM_IN:
                //Z00M In
                getEngine().getSystem(ZoomSystem.class).zoomIn();
                return true;
            case Keybinds.ZOOM_OUT:
                //Z00M Out
                getEngine().getSystem(ZoomSystem.class).zoomOut();
                return true;

            case Input.Keys.ESCAPE:
                if (!GameManager.isPaused()) {
                    GameManager.pause();
                    PauseDialog dialog = new PauseDialog(AssetLoader.skin);
                    dialog.show(guiSystem.getStage());
                }
                return true;

        }

        return false;
    }

    public void setPlayerVelocity(int keycode) {
        AccelerationComponent acceleration = Components.ACCELERATION.get(player);
        ShipStatisticComponent stats = Components.STATS.get(player);
        switch (keycode) {
            case Keybinds.FORWARD:
                acceleration.linear = stats.getSpeed();
                return;
            case Keybinds.BACKWARD:
                acceleration.linear = -stats.getSpeed();
                return;
            case Keybinds.TURN_LEFT:
                acceleration.angular = stats.getSteering();
                return;
            case Keybinds.TURN_RIGHT:
                acceleration.angular = -stats.getSteering();
        }

    }

    @Override
    public boolean keyUp(int keycode) {
        switch (keycode) {
            case Keybinds.FORWARD:
            case Keybinds.BACKWARD:
            case Keybinds.TURN_LEFT:
            case Keybinds.TURN_RIGHT:
                if (player != null)
                    resetPlayerVelocity(keycode);
                return true;
        }
        return false;
    }

    private void resetPlayerVelocity(int keycode) {
        AccelerationComponent acceleration = Components.ACCELERATION.get(player);
        switch (keycode) {
            case Keybinds.FORWARD:
                if (acceleration.linear > 0)
                    acceleration.linear = 0;
                return;
            case Keybinds.BACKWARD:
                if (acceleration.linear < 0)
                    acceleration.linear = 0;
                return;
            case Keybinds.TURN_LEFT:
                if (acceleration.angular > 0)
                    acceleration.angular = 0;
                return;
            case Keybinds.TURN_RIGHT:
                if (acceleration.angular < 0)
                    acceleration.angular = 0;
        }
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        Components.CANNON_FIRE.get(player).firing = true;
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        Components.CANNON_FIRE.get(player).firing = false;
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
