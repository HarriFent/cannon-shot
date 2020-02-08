package com.hfentonfearn.gameworld;

import com.badlogic.ashley.core.*;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.hfentonfearn.entitysystems.CollisionSystem;
import com.hfentonfearn.objects.PlayerBoat;
import com.hfentonfearn.entitysystems.MovementSystem;
import com.hfentonfearn.entitysystems.PlayerControllerSystem;
import com.hfentonfearn.entitysystems.RenderingSystem;

public class GameWorld {

    private Engine engine;
    private PlayerBoat playerBoat;

    public static boolean DEBUGMODE = false;

    public GameWorld(SpriteBatch batch) {

        engine = new Engine();

        //Add Engine Systems
        engine.addSystem(new MovementSystem());
        engine.addSystem(new PlayerControllerSystem());
        engine.addSystem(new RenderingSystem(batch));
        engine.addSystem(new CollisionSystem());

        //Add Entities
        playerBoat = new PlayerBoat(1000,1000);
        engine.addEntity(playerBoat);
    }

    public void update(float delta) {
        if (Gdx.input.isKeyJustPressed(Input.Keys.B))
            DEBUGMODE = !DEBUGMODE;
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
