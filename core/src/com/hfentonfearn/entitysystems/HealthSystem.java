package com.hfentonfearn.entitysystems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.physics.box2d.Body;
import com.hfentonfearn.GameManager;
import com.hfentonfearn.components.HealthComponent;
import com.hfentonfearn.ecs.EntityFactory;
import com.hfentonfearn.utils.Components;

import java.util.HashSet;
import java.util.Timer;
import java.util.TimerTask;

public class HealthSystem extends IteratingSystem {

    private Timer t;
    private HashSet<Entity> entities = new HashSet<>();

    public HealthSystem() {
        super(Family.all(HealthComponent.class).get());
        t = new Timer();
        t.schedule(new TimerTask() {
            @Override
            public void run() {
                for (Entity e : entities) {
                    HealthComponent health = Components.HEALTH.get(e);
                    health.displayValue += Math.signum(health.value - health.displayValue)/2;
                    if (Math.abs(health.displayValue - health.value) < 0.5) {
                        health.displayValue = health.value;
                    }
                }
            }
        }, 0, 100);
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        HealthComponent health = Components.HEALTH.get(entity);
        if (health.value != health.displayValue) {
            entities.add(entity);
        } else entities.remove(entity);
        if (Components.PLAYER.has(entity)){
            //PLAYER DEATH

        } else {
            if (health.value <= 0) {
                Entity dyingship = EntityFactory.createDyingShip(Components.PHYSICS.get(entity).getPosition(), Components.CURRENCY.get(entity).currency);
                Body shipBody = Components.PHYSICS.get(entity).getBody();
                Body dyingBody = Components.PHYSICS.get(dyingship).getBody();
                dyingBody.setAngularVelocity(shipBody.getAngularVelocity());
                dyingBody.setTransform(shipBody.getPosition(), shipBody.getAngle());
                dyingBody.setLinearVelocity(shipBody.getLinearVelocity());
                Components.KILL.get(entity).kill = true;
            }
        }
    }

    @Override
    public boolean checkProcessing () {
        return !GameManager.isPaused();
    }
}
