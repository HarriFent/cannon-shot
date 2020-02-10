package com.hfentonfearn.entitysystems;

import com.badlogic.ashley.core.*;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.hfentonfearn.components.PhysicsComponent;
import com.hfentonfearn.components.PlayerComponent;
import com.hfentonfearn.components.TransformComponent;
import com.hfentonfearn.components.VelocityComponent;
import com.hfentonfearn.helpers.MappersHandler;

import static com.hfentonfearn.helpers.Constants.*;

public class PlayerMovementSystem extends EntitySystem {

    private ImmutableArray<Entity> players;
    private VelocityComponent velocity;
    private PhysicsComponent phys;
    private Vector2 currentPos;
    private Vector2 currentVector;
    private Vector2 impulseVector = new Vector2();

    public PlayerMovementSystem() {}

    public void addedToEngine(Engine engine) {
        players = engine.getEntitiesFor(Family.all(TransformComponent.class, PhysicsComponent.class, PlayerComponent.class).get());
    }

    public void update(float deltaTime) {
        for (Entity player : players) {

            TransformComponent transform = MappersHandler.transform.get(player);
            VelocityComponent velocity = MappersHandler.velocity.get(player);
            PhysicsComponent physics = MappersHandler.physics.get(player);
            currentPos = physics.body.getWorldCenter();
            currentVector = physics.body.getLinearVelocity();

            physics.body.setAngularVelocity(velocity.turnVelocity);

            //float impulse = Velocity.DEFAULT_IMPULSE * world.getDelta();
            //float maxVel = Velocity.DEFAULT_MOVE_SPEED;
            float impulse = 1f;
            float maxVel = 6f;

            if(velocity.driveVelocity > 0f) {
                // accelerate
                impulseVector.set(impulse, 0f).rotateRad(transform.angle);

                // impulseVector has to be applied directly to allow direction changes at max velocity
                physics.body.applyLinearImpulse(impulseVector, currentPos, true);
                currentVector = physics.body.getLinearVelocity();

                if(currentVector.len() >= maxVel) {
                    // set velocity to maxVel
                    currentVector.nor().scl(maxVel);
                    physics.body.setLinearVelocity(currentVector);
                }
            } else {
                // decelerate
                impulseVector.set(currentVector).nor().scl(-1f * impulse);

                if(currentVector.len() - impulseVector.len() / physics.body.getMass() > 0f) {
                    // only apply impulse does not stop and accelerate the body in the opposite direction
                    physics.body.applyLinearImpulse(impulseVector, currentPos, true);
                } else {
                    // if impulse would accelerate in opposite direction set velocity to 0 instead
                    physics.body.setLinearVelocity(0f, 0f);
                }
            }

            transform.setPosition(physics.body.getPosition().x * PPM, physics.body.getPosition().y * PPM);
            transform.angle = physics.body.getAngle() * PPM;
        }
    }
}
