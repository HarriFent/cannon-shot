package com.hfentonfearn.entitysystems;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.EntitySystem;
import com.hfentonfearn.GameManager;

public class EnemySpawningSystem extends EntitySystem {

    @Override
    public void addedToEngine(Engine engine) {

    }

    @Override
    public void update(float deltaTime) {

    }

    @Override
    public boolean checkProcessing () {
        return !GameManager.isPaused();
    }
}
