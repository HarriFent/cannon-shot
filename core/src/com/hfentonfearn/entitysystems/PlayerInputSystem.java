package com.hfentonfearn.entitysystems;

import com.badlogic.ashley.core.*;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.hfentonfearn.components.DriveComponent;
import com.hfentonfearn.components.PlayerComponent;
import com.hfentonfearn.helpers.MappersHandler;

import static com.hfentonfearn.helpers.Constants.*;

public class PlayerInputSystem extends EntitySystem {

    private ImmutableArray<Entity> players;

    public PlayerInputSystem() {}

    public void addedToEngine(Engine engine) {
        players = engine.getEntitiesFor(Family.all(PlayerComponent.class).get());
    }

    public void update(float deltaTime) {
        float speed = deltaTime * 10;
        for (Entity player : players) {
            DriveComponent drive = MappersHandler.drive.get(player);

            if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
                drive.mDriveDirection = DRIVE_DIRECTION_FORWARD;
            } else if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
                drive.mDriveDirection = DRIVE_DIRECTION_BACKWARD;
            } else {
                drive.mDriveDirection = DRIVE_DIRECTION_NONE;
            }

            if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
                drive.mTurnDirection = TURN_DIRECTION_LEFT;
            } else if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
                drive.mTurnDirection = TURN_DIRECTION_RIGHT;
            } else {
                drive.mTurnDirection = TURN_DIRECTION_NONE;
            }
        }
    }
}
