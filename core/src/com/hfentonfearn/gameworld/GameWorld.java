package com.hfentonfearn.gameworld;

import com.badlogic.ashley.core.*;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2D;
import com.badlogic.gdx.physics.box2d.World;
import com.hfentonfearn.helpers.Constants;
import com.hfentonfearn.objects.PlayerBoat;
import com.hfentonfearn.entitysystems.*;

public class GameWorld {

    private final World world;
    private final Engine engine;
    private final PlayerBoat playerBoat;

    private float accumulator = 0;

    public static boolean DEBUGMODE = false;

    public GameWorld(SpriteBatch batch) {

        //Physics world
        Box2D.init();
        world = new World(new Vector2(0,0), true);

        engine = new Engine();

        //Add Engine Systems
        engine.addSystem(new PlayerControllerSystem());
        engine.addSystem(new MovementSystem());
        engine.addSystem(new RenderingSystem(batch, world));

        //Add Entities
        playerBoat = new PlayerBoat(world,100,100);
        engine.addEntity(playerBoat);
    }

    public void update(float delta) {
        if (Gdx.input.isKeyJustPressed(Input.Keys.B))
            DEBUGMODE = !DEBUGMODE;
        doPhysicsStep(delta);
        engine.update(delta);
    }

    public void dispose() {
        for (Entity e : engine.getEntities())
            e.removeAll();
        engine.removeAllEntities();
    }

    private void doPhysicsStep(float deltaTime) {
        // fixed time step
        // max frame time to avoid spiral of death (on slow devices)
        float frameTime = Math.min(deltaTime, 0.25f);
        accumulator += frameTime;
        while (accumulator >= Constants.TIME_STEP) {
            world.step(Constants.TIME_STEP, Constants.VELOCITY_ITERATIONS, Constants.POSITION_ITERATIONS);
            accumulator -= Constants.TIME_STEP;
        }
    }
}
