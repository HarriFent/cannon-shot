package com.hfentonfearn.utils;

import com.badlogic.ashley.core.ComponentMapper;
import com.hfentonfearn.components.*;

public class Components {
    public static final ComponentMapper<CollisionComponent> COLLISION = ComponentMapper.getFor(CollisionComponent.class);
    public static final ComponentMapper<SpriteComponent> SPRITE = ComponentMapper.getFor(SpriteComponent.class);
    public static final ComponentMapper<PhysicsComponent> PHYSICS = ComponentMapper.getFor(PhysicsComponent.class);
    public static final ComponentMapper<VelocityComponent> VELOCITY = ComponentMapper.getFor(VelocityComponent.class);
    public static final ComponentMapper<StaticMovementComponent> STATIC_MOVEMENT = ComponentMapper.getFor(StaticMovementComponent.class);
}
