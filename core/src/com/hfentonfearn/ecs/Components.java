package com.hfentonfearn.ecs;

import com.badlogic.ashley.core.ComponentMapper;
import com.hfentonfearn.components.CollisionComponent;
import com.hfentonfearn.components.PhysicsComponent;
import com.hfentonfearn.components.SpriteComponent;
import com.hfentonfearn.components.VelocityComponent;

public class Components {
    public static final ComponentMapper<CollisionComponent> COLLISION = ComponentMapper.getFor(CollisionComponent.class);
    public static final ComponentMapper<SpriteComponent> SPRITE = ComponentMapper.getFor(SpriteComponent.class);
    public static final ComponentMapper<PhysicsComponent> PHYSICS = ComponentMapper.getFor(PhysicsComponent.class);
    public static final ComponentMapper<VelocityComponent> VELOCITY = ComponentMapper.getFor(VelocityComponent.class);
}
