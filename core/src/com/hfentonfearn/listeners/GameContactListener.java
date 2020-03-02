package com.hfentonfearn.listeners;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.hfentonfearn.components.TypeComponent;
import com.hfentonfearn.utils.Components;

import static com.hfentonfearn.components.TypeComponent.CANNONBALL;
import static com.hfentonfearn.components.TypeComponent.PLAYER;

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
                if (Components.TYPE.get(entityB).type == TypeComponent.ENEMY)
                    Components.HEALTH.get(entityB).damage(Components.PHYSICS.get(entityA).getBody().getLinearVelocity().len());
                if (Components.TYPE.get(entityB).type == TypeComponent.PLAYER)
                    //Remove player ship health
                return true;
            case PLAYER:
                if (Components.TYPE.get(entityB).type == TypeComponent.ENEMY)
                    Components.HEALTH.get(entityB).damage(Components.PHYSICS.get(entityA).getBody().getLinearVelocity().len());
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
