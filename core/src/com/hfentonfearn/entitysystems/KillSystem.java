package com.hfentonfearn.entitysystems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.hfentonfearn.GameManager;
import com.hfentonfearn.components.AnimationComponent;
import com.hfentonfearn.components.KillComponent;
import com.hfentonfearn.components.PhysicsComponent;
import com.hfentonfearn.ecs.EntityFactory;
import com.hfentonfearn.utils.Components;

import static com.hfentonfearn.ecs.EntityCategory.CANNONBALL;
import static com.hfentonfearn.utils.Constants.CANNONBALL_DYING_VELOCITY;

public class KillSystem extends IteratingSystem {

    public KillSystem() {
        super(Family.all(KillComponent.class).get());
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        KillComponent kill = Components.KILL.get(entity);
        if (entity.flags == CANNONBALL) {
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
        if (kill.afterAnimation) {
            AnimationComponent ani = Components.ANIMATION.get(entity);
            kill.kill = ani.animation.isAnimationFinished(ani.stateTime) || kill.kill;
        }
        if (kill.kill) {
            getEngine().removeEntity(entity);
        }
        if (kill.fade) {
            if (Components.SPRITE.has(entity)) {
                for (Sprite s : Components.SPRITE.get(entity).getSprites()) {
                    float alpha = kill.getProgress();
                    s.setAlpha(alpha);
                }
            }
        }
        if (kill.exploding) {
            if (Math.random() < 0.05 && kill.getProgress() > 0.5) {
                Vector2 pos = Components.PHYSICS.get(entity).getPosition();
                EntityFactory.createExplosion(new Vector2(kill.explodingRadius, 0).rotate(MathUtils.random(0, 360)).add(pos), MathUtils.random(0.3f, 1f));
            }
        }
    }

    @Override
    public boolean checkProcessing () {
        return !GameManager.isPaused();
    }
}
