package com.hfentonfearn.utils;

import com.badlogic.ashley.core.ComponentMapper;
import com.hfentonfearn.components.*;

public class Components {
    public static final ComponentMapper<CollisionComponent> COLLISION = ComponentMapper.getFor(CollisionComponent.class);
    public static final ComponentMapper<SpriteComponent> SPRITE = ComponentMapper.getFor(SpriteComponent.class);
    public static final ComponentMapper<PhysicsComponent> PHYSICS = ComponentMapper.getFor(PhysicsComponent.class);
    public static final ComponentMapper<AccelerationComponent> ACCELERATION = ComponentMapper.getFor(AccelerationComponent.class);
    public static final ComponentMapper<AnimationComponent> ANIMATION = ComponentMapper.getFor(AnimationComponent.class);
    public static final ComponentMapper<HealthComponent> HEALTH = ComponentMapper.getFor(HealthComponent.class);
    public static final ComponentMapper<KillComponent> KILL = ComponentMapper.getFor(KillComponent.class);
    public static final ComponentMapper<ShipStatisticComponent> STATS = ComponentMapper.getFor(ShipStatisticComponent.class);
    public static final ComponentMapper<InventoryComponent> INVENTORY = ComponentMapper.getFor(InventoryComponent.class);
    public static final ComponentMapper<PlayerComponent> PLAYER = ComponentMapper.getFor(PlayerComponent.class);
    public static final ComponentMapper<CannonFiringComponent> CANNON_FIRE = ComponentMapper.getFor(CannonFiringComponent.class);
    public static final ComponentMapper<ParticleComponent> PARTICLE = ComponentMapper.getFor(ParticleComponent.class);
    public static final ComponentMapper<CurrencyComponent> CURRENCY = ComponentMapper.getFor(CurrencyComponent.class);
    public static final ComponentMapper<ZoneTypeComponent> ZONETYPE = ComponentMapper.getFor(ZoneTypeComponent.class);
}
