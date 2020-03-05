package com.hfentonfearn.ecs;

import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.utils.Disposable;
import com.hfentonfearn.components.PhysicsComponent;
import com.hfentonfearn.entitysystems.*;
import com.hfentonfearn.listeners.PhysicsEntityListener;
import com.hfentonfearn.utils.Constants;

public class EntityManager extends PooledEngine {

    public EntityManager() {
        initSystems();
        // Add the Entity Listeners
        addEntityListener(Family.all(PhysicsComponent.class).get(), new PhysicsEntityListener(getSystem(PhysicsSystem.class)));
    }

    private EntityManager initSystems() {
        //Add all the entity systems here
        //Camera System
        CameraSystem cameraSystem = new CameraSystem(Constants.WINDOW_WIDTH, Constants.WINDOW_HEIGHT);
        addSystem(cameraSystem);
        addSystem(new ZoomSystem());
        //Physics System
        addSystem(new PhysicsSystem());
        addSystem(new ParticleSystem());
        //Player movement system
        addSystem(new PlayerMovementSystem());

        addSystem(new CannonFiringSystem());
        addSystem(new KillSystem());
        addSystem(new HealthSystem());
        addSystem(new StatisticSystem());

        GUISystem guiSystem = new GUISystem();
        InputSystem inputSystem = new InputSystem(guiSystem);

        //Input System
        addSystem(inputSystem);
        //Entity Render System
        addSystem(new FarRenderSystem());
        addSystem(new MapRenderSystem());
        addSystem(new HUDSystem());
        //Debug Render System
        addSystem(new DebugRendererSystem());
        //Particle System

        //GUI System
        addSystem(guiSystem);

        return this;
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
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
