package com.hfentonfearn.gameworld;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.EllipseMapObject;
import com.badlogic.gdx.maps.objects.PolygonMapObject;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2D;
import com.badlogic.gdx.physics.box2d.World;
import com.hfentonfearn.entitysystems.*;
import com.hfentonfearn.helpers.AssetLoader;
import com.hfentonfearn.helpers.Constants;
import com.hfentonfearn.helpers.PhysicsContactListener;
import com.hfentonfearn.objects.Ground;
import com.hfentonfearn.objects.PlayerBoat;
import com.hfentonfearn.objects.WaterRocks;

import static com.hfentonfearn.helpers.Constants.WORLD_PIXEL_HEIGHT;
import static com.hfentonfearn.helpers.Constants.WORLD_PIXEL_WIDTH;

public class GameWorld {

    private final World world;
    private final Engine engine;
    private final PlayerBoat playerBoat;
    private OrthographicCamera cam;

    private float accumulator = 0;

    public GameWorld(SpriteBatch batch) {

        //View Camera
        cam = new OrthographicCamera(WORLD_PIXEL_WIDTH * 2, WORLD_PIXEL_HEIGHT * 2);

        //Physics world
        Box2D.init();
        world = new World(new Vector2(0,0), true);
        world.setContactListener(new PhysicsContactListener());

        engine = new Engine();

        //Add Engine Systems
        engine.addSystem(new PlayerInputSystem());
        engine.addSystem(new PlayerMovementSystem());
        engine.addSystem(new RenderingSystem(batch, cam));
        engine.addSystem(new DebugRendererSystem(world, cam));
        engine.addSystem(new CollisionSystem());

        //Add Entities
        //playerBoat = new PlayerBoat(world,500,500);
        playerBoat = new PlayerBoat(world,400,5900);
        engine.addEntity(playerBoat);

        CreateCollisionMapObjects();
    }

    private void CreateCollisionMapObjects() {
        MapObjects objects = AssetLoader.map.getLayers().get("collision").getObjects();
        Entity newE;
        for (MapObject obj : objects) {
            switch (obj.getProperties().get("type", String.class)) {
                case "ground":
                    newE = new Ground(world, (PolygonMapObject) obj);
                    break;
                case "rock":
                    newE = new WaterRocks(world, (EllipseMapObject) obj);
                    break;
                default:
                    newE = null;
            }
            engine.addEntity(newE);
        }
    }

    public void update(float delta) {
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
