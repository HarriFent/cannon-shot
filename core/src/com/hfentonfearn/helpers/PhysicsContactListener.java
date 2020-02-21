package com.hfentonfearn.helpers;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.hfentonfearn.components.CollisionComponent;

public class PhysicsContactListener implements ContactListener {

    @Override
    public void beginContact(Contact contact) {
        Object e1 = contact.getFixtureA().getBody().getUserData();
        Object e2 = contact.getFixtureB().getBody().getUserData();
        if(e1 instanceof Entity && e2 instanceof Entity){
            CollisionComponent cola = MappersHandler.collision.get((Entity) e1);
            CollisionComponent colb = MappersHandler.collision.get((Entity) e2);
            if(cola != null){
                cola.collisionEntities.add((Entity) e2);
            }
            if(colb != null){
                colb.collisionEntities.add((Entity) e1);
            }
        }
    }

    @Override
    public void endContact(Contact contact) {
        Object e1 = contact.getFixtureA().getBody().getUserData();
        Object e2 = contact.getFixtureB().getBody().getUserData();
        if(e1 instanceof Entity && e2 instanceof Entity){
            CollisionComponent cola = MappersHandler.collision.get((Entity) e1);
            CollisionComponent colb = MappersHandler.collision.get((Entity) e2);
            if(cola != null){
                cola.collisionEntities.remove(e2);
            }
            if(colb != null){
                colb.collisionEntities.remove(e1);
            }
        }
    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
}
