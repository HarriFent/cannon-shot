package com.hfentonfearn.entitysystems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.hfentonfearn.components.ShipStatisticComponent;
import com.hfentonfearn.utils.Components;

public class StatisticSystem extends IteratingSystem {

    public StatisticSystem() {
        super(Family.all(ShipStatisticComponent.class).get());
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        ShipStatisticComponent stats = Components.STATS.get(entity);
        if (stats.changed) {
            if (Components.HEALTH.has(entity))
                Components.HEALTH.get(entity).max = stats.getHull();
            if (Components.INVENTORY.has(entity))
                Components.INVENTORY.get(entity).setSize(stats.getInventorySize());
            if (Components.CANNON_FIRE.has(entity))
                Components.CANNON_FIRE.get(entity).update(stats.getFireRate(), stats.getFireRange());
            stats.changed = false;
        }
    }
}
