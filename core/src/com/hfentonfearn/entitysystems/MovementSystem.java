package com.hfentonfearn.entitysystems;

import com.badlogic.ashley.core.*;
import com.badlogic.ashley.utils.ImmutableArray;
import com.hfentonfearn.components.AccelerationComponent;
import com.hfentonfearn.components.TransformComponent;
import com.hfentonfearn.components.VelocityComponent;

public class MovementSystem extends EntitySystem {

    private ImmutableArray<Entity> entities;

    private ComponentMapper<TransformComponent> pm = ComponentMapper.getFor(TransformComponent.class);
    private ComponentMapper<VelocityComponent> vm = ComponentMapper.getFor(VelocityComponent.class);
    private ComponentMapper<AccelerationComponent> am = ComponentMapper.getFor(AccelerationComponent.class);

    public MovementSystem() {}

    public void addedToEngine(Engine engine) {
        entities = engine.getEntitiesFor(Family.all(TransformComponent.class, VelocityComponent.class).get());
    }

    public void update(float deltaTime) {
        for (int i = 0; i < entities.size(); ++i) {
            Entity entity = entities.get(i);

            TransformComponent position = pm.get(entity);
            VelocityComponent velocity = vm.get(entity);
            AccelerationComponent acceleration = am.get(entity);

            velocity.incVelocity(acceleration.getTangentAcc());
            velocity.incAngle(acceleration.getAngleAcc());

            float dx, dy;

            dx = (float) (velocity.getTangentVel() * Math.sin(Math.toRadians(position.getAngle())));
            dy = (float) (velocity.getTangentVel() * Math.cos(Math.toRadians(position.getAngle()))) * -1;

            position.incPosition(dx,dy);
            position.incAngle(velocity.getAngleVel());
        }
    }
}
