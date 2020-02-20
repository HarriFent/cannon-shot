package com.hfentonfearn.ecs;

import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.utils.Disposable;

public class EntityManager extends PooledEngine {

    public EntityManager() {
        initSystems();
    }

    private EntityManager initSystems() {
        //Add all the entity systems here
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
