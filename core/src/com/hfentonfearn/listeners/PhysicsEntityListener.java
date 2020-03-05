package com.hfentonfearn.listeners;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntityListener;
import com.badlogic.gdx.physics.box2d.Body;
import com.hfentonfearn.entitysystems.PhysicsSystem;
import com.hfentonfearn.utils.Components;

public class PhysicsEntityListener implements EntityListener {

    private PhysicsSystem physicsSystem;

    public PhysicsEntityListener(PhysicsSystem physicsSystem) {
        this.physicsSystem = physicsSystem;
    }

    @Override
    public void entityAdded(Entity entity) {

    }

    @Override
    public void entityRemoved(Entity entity) {
        Body body = Components.PHYSICS.get(entity).getBody();
        physicsSystem.destroyBody(body);
    }
}
