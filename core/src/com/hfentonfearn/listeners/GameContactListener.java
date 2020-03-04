package com.hfentonfearn.listeners;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.hfentonfearn.utils.Components;

import static com.hfentonfearn.components.TypeComponent.*;

public class GameContactListener implements ContactListener {
    @Override
    public void beginContact(Contact contact) {
        Entity entityA = (Entity) contact.getFixtureA().getBody().getUserData();
        Entity entityB = (Entity) contact.getFixtureB().getBody().getUserData();
        if (!runContact(entityA,entityB)) runContact(entityB,entityA);
    }

    public boolean runContact(Entity entityA, Entity entityB) {
        switch (Components.TYPE.get(entityA).type) {
            case CANNONBALL:
                if (Components.TYPE.get(entityB).type == ENEMY)
                    Components.HEALTH.get(entityB).damage(Components.PHYSICS.get(entityA).getBody().getLinearVelocity().len());
                if (Components.TYPE.get(entityB).type == PLAYER)
                    Components.HEALTH.get(entityB).damage(Components.PHYSICS.get(entityA).getBody().getLinearVelocity().len());
                return true;
            case PLAYER:
                if (Components.TYPE.get(entityB).type == ENEMY) {
                    float damage = Components.PHYSICS.get(entityA).getBody().getLinearVelocity().len() + Components.PHYSICS.get(entityB).getBody().getLinearVelocity().len();
                    Components.HEALTH.get(entityB).damage(damage);
                    Components.HEALTH.get(entityA).damage(damage);
                }
                if (Components.TYPE.get(entityB).type == LAND)
                    Components.HEALTH.get(entityA).damage(Components.PHYSICS.get(entityA).getBody().getLinearVelocity().len());
                if (Components.TYPE.get(entityB).type == SCENERY)
                    Components.HEALTH.get(entityA).damage(Components.PHYSICS.get(entityA).getBody().getLinearVelocity().len());
                if (Components.TYPE.get(entityB).type == CANNONBALL)
                    Components.HEALTH.get(entityA).damage(Components.PHYSICS.get(entityB).getBody().getLinearVelocity().len());
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
