package com.hfentonfearn.entitysystems;

import com.badlogic.ashley.core.*;
import com.badlogic.ashley.utils.ImmutableArray;
import com.hfentonfearn.components.AccelerationComponent;
import com.hfentonfearn.components.PhysicsComponent;
import com.hfentonfearn.components.TransformComponent;
import com.hfentonfearn.components.VelocityComponent;
import com.hfentonfearn.helpers.Constants;
import com.hfentonfearn.helpers.MappersHandler;
import com.hfentonfearn.helpers.MathsHandler;

import static com.hfentonfearn.helpers.Constants.*;

public class MovementSystem extends EntitySystem {

    private ImmutableArray<Entity> entities;

    public MovementSystem() {}

    public void addedToEngine(Engine engine) {
        entities = engine.getEntitiesFor(Family.all(TransformComponent.class, VelocityComponent.class, AccelerationComponent.class, PhysicsComponent.class).get());
    }

    public void update(float deltaTime) {
        for (int i = 0; i < entities.size(); ++i) {
            Entity entity = entities.get(i);
            TransformComponent transform = MappersHandler.transform.get(entity);
            VelocityComponent velocity = MappersHandler.velocity.get(entity);
            AccelerationComponent acceleration = MappersHandler.acceleration.get(entity);
            PhysicsComponent physics = MappersHandler.physics.get(entity);

            velocity.incVelocity(acceleration.getTangentAcc());
            velocity.incAngle(acceleration.getAngleAcc());

            float[] target = MathsHandler.getEntityTarget(entity);
            physics.body.applyForceToCenter(target[0], velocity.getTangentVel(), true);
            physics.body.applyTorque(velocity.getAngleVel(), true);

            transform.setPosition(physics.body.getPosition().x * PPM, physics.body.getPosition().y * PPM);
            transform.setAngle(physics.body.getAngle() * PPM);
        }
    }
}
