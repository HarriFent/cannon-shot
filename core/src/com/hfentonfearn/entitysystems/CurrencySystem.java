package com.hfentonfearn.entitysystems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.hfentonfearn.components.CurrencyComponent;

public class CurrencySystem extends IteratingSystem {

    public CurrencySystem() {
        super(Family.all(CurrencyComponent.class).get());
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {

    }
}
