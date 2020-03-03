package com.hfentonfearn.entitysystems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.hfentonfearn.components.AnimationComponent;
import com.hfentonfearn.components.KillComponent;
import com.hfentonfearn.components.PhysicsComponent;
import com.hfentonfearn.components.TypeComponent;
import com.hfentonfearn.ecs.EntityFactory;
import com.hfentonfearn.utils.Components;

import static com.hfentonfearn.utils.Constants.CANNONBALL_DYING_VELOCITY;

public class KillSystem extends IteratingSystem {

    public KillSystem() {
        super(Family.all(KillComponent.class).get());
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        KillComponent kill = Components.KILL.get(entity);
        if (Components.TYPE.has(entity))
            if (Components.TYPE.get(entity).type == TypeComponent.CANNONBALL){
                PhysicsComponent physics = Components.PHYSICS.get(entity);
                if (physics.getBody().getLinearVelocity().len() < CANNONBALL_DYING_VELOCITY) {
                    EntityFactory.createCannonBallSplash(physics.getPosition());
                    kill.kill = true;
                }
            }

        if (kill.timed) {
            kill.timer--;
            kill.kill = kill.timer <= 0 || kill.kill;
        }
        if (kill.fromAnimation) {
            AnimationComponent ani = Components.ANIMATION.get(entity);
            kill.kill = ani.animation.isAnimationFinished(ani.stateTime) || kill.kill;
        }
        if (kill.kill) {
            getEngine().getSystem(PhysicsSystem.class).destroyBody(Components.PHYSICS.get(entity).getBody());
            getEngine().removeEntity(entity);
        }
        if (kill.fade)
            if (Components.SPRITE.has(entity))
                for (Sprite s : Components.SPRITE.get(entity).getSprites()){
                    float alpha = ((float) kill.timer / (float) kill.starttime);
                    s.setAlpha(alpha);
                }
    }
}
