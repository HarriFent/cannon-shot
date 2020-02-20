package com.hfentonfearn.ecs;

import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.utils.Disposable;
import com.hfentonfearn.entitysystems.CameraSystem;
import com.hfentonfearn.entitysystems.EntityRenderSystem;
import com.hfentonfearn.entitysystems.InputSystem;
import com.hfentonfearn.entitysystems.PhysicsSystem;
import com.hfentonfearn.helpers.Constants;

public class EntityManager extends PooledEngine {

    public EntityManager() {
        initSystems();
        // Add the Entity Listeners
    }

    private EntityManager initSystems() {
        //Add all the entity systems here
        //Camera System
        CameraSystem cameraSystem = new CameraSystem(Constants.WORLD_PIXEL_WIDTH, Constants.WORLD_PIXEL_HEIGHT);
        addSystem(cameraSystem);
        //Physics System
        addSystem(new PhysicsSystem());
        //AI Systems (Steering)

        //Health System

        //Input System
        addSystem(new InputSystem());
        //Entity Render System
        addSystem(new EntityRenderSystem());
        //Particle System

        //GUI System

        return this;
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        //getSystem(PhysicsSystem.class).drawDebug();
    }

    public void dispose() {
        removeAllEntities();
        clearPools();
        for (EntitySystem system : getSystems()) {
            if (system instanceof Disposable) {
                ((Disposable) system).dispose();
            }
            system = null;
            removeSystem(system);
        }
    }
}
