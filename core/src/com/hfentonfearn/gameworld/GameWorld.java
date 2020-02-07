package com.hfentonfearn.gameworld;

import com.badlogic.ashley.core.*;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.hfentonfearn.objects.PlayerBoat;
import com.hfentonfearn.entitysystems.MovementSystem;
import com.hfentonfearn.entitysystems.PlayerControllerSystem;
import com.hfentonfearn.entitysystems.RenderingSystem;

public class GameWorld {

    private Engine engine;
    private PlayerBoat playerBoat;

    public GameWorld(SpriteBatch batch) {

        engine = new Engine();

        //Add Engine Systems
        engine.addSystem(new MovementSystem());
        engine.addSystem(new PlayerControllerSystem());
        engine.addSystem(new RenderingSystem(batch));

        //Add Entities
        playerBoat = new PlayerBoat();
        engine.addEntity(playerBoat);
    }

    public void update(float delta) {
        engine.update(delta);
    }

    public void dispose() {
        for (Entity e : engine.getEntities())
            e.removeAll();
        engine.removeAllEntities();
    }

    public ImmutableArray<Entity> getEntitiesFor(Family family) {
        return engine.getEntitiesFor(family);
    }

    public ImmutableArray<Entity> getEntities() {
        return engine.getEntities();
    }
}
