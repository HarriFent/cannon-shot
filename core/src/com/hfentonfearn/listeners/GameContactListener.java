package com.hfentonfearn.listeners;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.hfentonfearn.utils.Components;

import static com.hfentonfearn.ecs.EntityCategory.*;

public class GameContactListener implements ContactListener {
    @Override
    public void beginContact(Contact contact) {
        Entity entityA = (Entity) contact.getFixtureA().getBody().getUserData();
        Entity entityB = (Entity) contact.getFixtureB().getBody().getUserData();
        if (!runContact(entityA,entityB)) runContact(entityB,entityA);
    }

    public boolean runContact(Entity entityA, Entity entityB) {
        float damage = Components.PHYSICS.get(entityA).getBody().getLinearVelocity().len() + Components.PHYSICS.get(entityB).getBody().getLinearVelocity().len();

        //if (damage < 1) damage = 1;
        switch (entityA.flags) {
            case PLAYER:
            case ENEMY:
                switch (entityB.flags) {
                    case LAND:
                    case SCENERY:
                    case CANNONBALL:
                    case ENEMY:
                    case PLAYER:
                        Components.HEALTH.get(entityA).damage(damage);
                }
                return true;
        }
        return false;
    }

    @Override
    public void endContact(Contact contact) {

    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
}
