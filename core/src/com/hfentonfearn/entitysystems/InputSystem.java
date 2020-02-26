package com.hfentonfearn.entitysystems;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.hfentonfearn.GameManager;
import com.hfentonfearn.components.PlayerComponent;
import com.hfentonfearn.components.VelocityComponent;
import com.hfentonfearn.ecs.Components;
import com.hfentonfearn.inputs.Keybinds;
import com.hfentonfearn.ui.PauseDialog;
import com.hfentonfearn.utils.AssetLoader;

import static com.hfentonfearn.utils.Constants.ACCELERATION_DRIVE;
import static com.hfentonfearn.utils.Constants.ACCELERATION_TURN;

public class InputSystem extends EntitySystem implements InputProcessor {

    private InputMultiplexer multiplexer;
    private GUISystem guiSystem;
    private ImmutableArray<Entity> players;
    private Entity player;

    public InputSystem (GUISystem guiSystem) {
        this.guiSystem = guiSystem;
    }
    
    @Override
    public void addedToEngine (Engine engine) {
        super.addedToEngine(engine);
        players = engine.getEntitiesFor(Family.all(PlayerComponent.class).get());
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

    @Override
    public boolean keyDown(int keycode) {
        player = players.get(0);
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

    public void setPlayerVelocity(int keycode) {
        VelocityComponent velocity = Components.VELOCITY.get(player);
        switch (keycode) {
            case Keybinds.FORWARD:
                velocity.linearVelocity = ACCELERATION_DRIVE;
                return;
            case Keybinds.BACKWARD:
                velocity.linearVelocity = -ACCELERATION_DRIVE;
                return;
            case Keybinds.TURN_LEFT:
                velocity.angularVelocity = ACCELERATION_TURN;
                return;
            case Keybinds.TURN_RIGHT:
                velocity.angularVelocity = -ACCELERATION_TURN;
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
        VelocityComponent velocity = Components.VELOCITY.get(player);
        switch (keycode) {
            case Keybinds.FORWARD:
                if (velocity.linearVelocity > 0)
                    velocity.linearVelocity = 0;
                return;
            case Keybinds.BACKWARD:
                if (velocity.linearVelocity < 0)
                    velocity.linearVelocity = 0;
                return;
            case Keybinds.TURN_LEFT:
                if (velocity.angularVelocity > 0)
                    velocity.angularVelocity = 0;
                return;
            case Keybinds.TURN_RIGHT:
                if (velocity.angularVelocity < 0)
                    velocity.angularVelocity = 0;
        }
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