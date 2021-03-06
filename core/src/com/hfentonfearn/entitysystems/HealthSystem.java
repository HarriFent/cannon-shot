package com.hfentonfearn.entitysystems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.physics.box2d.Body;
import com.hfentonfearn.GameManager;
import com.hfentonfearn.components.HealthComponent;
import com.hfentonfearn.components.PlayerComponent;
import com.hfentonfearn.ecs.EntityFactory;
import com.hfentonfearn.utils.Components;

public class HealthSystem extends IteratingSystem {

    public HealthSystem() {
        super(Family.all(HealthComponent.class).exclude(PlayerComponent.class).get());
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        if (Components.HEALTH.get(entity).value <= 0) {
            Entity dyingship = EntityFactory.createDyingShip(Components.PHYSICS.get(entity).getPosition(), Components.CURRENCY.get(entity).currency);
            Body shipBody = Components.PHYSICS.get(entity).getBody();
            Body dyingBody = Components.PHYSICS.get(dyingship).getBody();
            dyingBody.setAngularVelocity(shipBody.getAngularVelocity());
            dyingBody.setTransform(shipBody.getPosition(),shipBody.getAngle());
            dyingBody.setLinearVelocity(shipBody.getLinearVelocity());
            Components.KILL.get(entity).kill = true;
        }
    }

    @Override
    public boolean checkProcessing () {
        return !GameManager.isPaused();
    }
}
