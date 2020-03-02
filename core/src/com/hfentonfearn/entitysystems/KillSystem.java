package com.hfentonfearn.entitysystems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.hfentonfearn.components.AnimationComponent;
import com.hfentonfearn.components.KillComponent;
import com.hfentonfearn.utils.Components;

public class KillSystem extends IteratingSystem {

    public KillSystem() {
        super(Family.all(KillComponent.class).get());
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        KillComponent killComponent = Components.KILL.get(entity);
        if (killComponent.timed) {
            killComponent.timer--;
            killComponent.kill = killComponent.timer <= 0 || killComponent.kill;
        }
        if (killComponent.fromAnimation) {
            AnimationComponent ani = Components.ANIMATION.get(entity);
            killComponent.kill = ani.animation.isAnimationFinished(ani.stateTime) || killComponent.kill;
        }
        if (killComponent.kill) {
            getEngine().getSystem(PhysicsSystem.class).destroyBody(Components.PHYSICS.get(entity).getBody());
            getEngine().removeEntity(entity);
        }
    }
}
