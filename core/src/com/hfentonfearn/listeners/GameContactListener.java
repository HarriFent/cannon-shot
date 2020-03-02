package com.hfentonfearn.listeners;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.hfentonfearn.components.TypeComponent;
import com.hfentonfearn.utils.Components;

public class GameContactListener implements ContactListener {
    @Override
    public void beginContact(Contact contact) {
        Entity entityA = (Entity) contact.getFixtureA().getBody().getUserData();
        Entity entityB = (Entity) contact.getFixtureB().getBody().getUserData();
        if (Components.TYPE.get(entityA).type == TypeComponent.CANNONBALL) {
            if (Components.TYPE.get(entityB).type == TypeComponent.ENEMY) {
                Components.HEALTH.get(entityB).damage(Components.PHYSICS.get(entityA).getBody().getLinearVelocity().len());
            }
        } else  if (Components.TYPE.get(entityB).type == TypeComponent.CANNONBALL) {
            if (Components.TYPE.get(entityA).type == TypeComponent.ENEMY) {
                Components.HEALTH.get(entityA).damage(Components.PHYSICS.get(entityB).getBody().getLinearVelocity().len());
            }
        }
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
