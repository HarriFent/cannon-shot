package com.hfentonfearn.entitysystems;

import com.badlogic.ashley.core.*;
import com.badlogic.ashley.utils.ImmutableArray;
import com.hfentonfearn.components.AccelerationComponent;
import com.hfentonfearn.components.TransformComponent;
import com.hfentonfearn.components.VelocityComponent;
import com.hfentonfearn.helpers.MappersHandler;

public class MovementSystem extends EntitySystem {

    private ImmutableArray<Entity> entities;

    public MovementSystem() {}

    public void addedToEngine(Engine engine) {
        entities = engine.getEntitiesFor(Family.all(TransformComponent.class, VelocityComponent.class).get());
    }

    public void update(float deltaTime) {
        for (int i = 0; i < entities.size(); ++i) {
            Entity entity = entities.get(i);

            TransformComponent transform = MappersHandler.transform.get(entity);
            VelocityComponent velocity = MappersHandler.velocity.get(entity);
            AccelerationComponent acceleration = MappersHandler.acceleration.get(entity);

            velocity.incVelocity(acceleration.getTangentAcc());
            velocity.incAngle(acceleration.getAngleAcc());

            float dx, dy;

            dx = (float) (velocity.getTangentVel() * Math.sin(Math.toRadians(transform.getAngle())));
            dy = (float) (velocity.getTangentVel() * Math.cos(Math.toRadians(transform.getAngle()))) * -1;

            transform.incPosition(dx,dy);
            transform.incAngle(velocity.getAngleVel());
        }
    }
}
