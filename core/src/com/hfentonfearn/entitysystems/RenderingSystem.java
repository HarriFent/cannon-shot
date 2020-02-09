package com.hfentonfearn.entitysystems;

import com.badlogic.ashley.core.*;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.*;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.PolygonMapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.hfentonfearn.components.*;
import com.hfentonfearn.gameworld.GameWorld;
import com.hfentonfearn.helpers.AssetLoader;
import com.hfentonfearn.helpers.MappersHandler;

import static com.hfentonfearn.helpers.Constants.*;

public class RenderingSystem extends EntitySystem {

    private Family family;
    private ImmutableArray<Entity> entities;

    private SpriteBatch batch;
    private Array<Entity> renderQueue;
    private OrthographicCamera cam;
    private ImmutableArray<Entity> players;
    private TiledMapRenderer mapRenderer;

    //For Debug Mode
    private ShapeRenderer debugRenderer;
    private Box2DDebugRenderer debug2dRenderer;
    private SpriteBatch debugBatch;
    private BitmapFont font;
    private World world;

    public RenderingSystem(SpriteBatch batch, World world) {
        this.family = Family.all(TransformComponent.class, TextureComponent.class).get();

        renderQueue = new Array<Entity>();

        this.batch = batch;

        cam = new OrthographicCamera(WORLD_PIXEL_WIDTH, WORLD_PIXEL_HEIGHT);

        this.mapRenderer = new OrthogonalTiledMapRenderer(AssetLoader.map,this.batch);

        //For Debug Mode
        debugRenderer = new ShapeRenderer();
        font = new BitmapFont();
        debugBatch = new SpriteBatch();
        this.world = world;
        debug2dRenderer = new Box2DDebugRenderer();
    }

    @Override
    public void addedToEngine (Engine engine) {
        entities = engine.getEntitiesFor(family);
        players = engine.getEntitiesFor(Family.all(PlayerComponent.class).get());
    }

    @Override
    public void removedFromEngine (Engine engine) {
        entities = null;
        players = null;
    }

    @Override
    public void update(float deltaTime) {
        for (int i = 0; i < entities.size(); ++i) {
            processEntity(entities.get(i), deltaTime);
        }

        Entity player;
        if (players.get(0) != null) {
            player = players.get(0);
            TransformComponent tm = MappersHandler.transform.get(player);
            cam.position.set(tm.x, tm.y,0);
        }

        cam.update();
        mapRenderer.setView(cam);
        mapRenderer.render();
        batch.setProjectionMatrix(cam.combined);
        batch.enableBlending();
        batch.begin();

        for (Entity entity : renderQueue) {
            TextureComponent tex = MappersHandler.texture.get(entity);
            TransformComponent trans = MappersHandler.transform.get(entity);

            if (tex.region == null) {
                continue;
            }

            batch.draw(tex.region,trans.x - trans.originX,trans.y - trans.originY,
                    trans.originX, trans.originY, trans.width, trans.height,
                    trans.scaleX, trans.scaleY, trans.angle);
        }

        batch.end();
        if (GameWorld.DEBUGMODE)
            renderDebug();
        renderQueue.clear();
    }

    private void renderDebug() {
        //Render Physics World
        //Matrix4 debugMatrix = cam.combined.cpy().scale(PPM, PPM, 0);
        debug2dRenderer.render(world,cam.combined.scl(PPM));

        debugRenderer.setProjectionMatrix(cam.combined);
        debugRenderer.begin(ShapeType.Line);

        //Render Render Queue
        for (Entity e : renderQueue) {
            debugRenderer.setColor(Color.RED);
            Gdx.gl.glLineWidth(3);

            TransformComponent transformComponent = MappersHandler.transform.get(e);
            debugRenderer.circle(transformComponent.x,transformComponent.x,6);
        }

        //Render Map Objects
        debugRenderer.setColor(Color.YELLOW);
        MapObjects objects = AssetLoader.map.getLayers().get("collision").getObjects();
        Gdx.gl.glLineWidth(3);
        for (MapObject obj: objects) {
            if (obj instanceof PolygonMapObject)
                debugRenderer.polygon(((PolygonMapObject) obj).getPolygon().getTransformedVertices());
            if (obj instanceof RectangleMapObject) {
                Rectangle r = ((RectangleMapObject) obj).getRectangle();
                debugRenderer.rect(r.x,r.y,r.width,r.height);
            }
        }
        debugRenderer.end();

        //Debug Overlay HUD
        debugBatch.begin();
        font.setColor(Color.RED);
        font.draw(debugBatch,"DEBUG MODE",10, 20);
        Entity e = players.get(0);
        font.draw(debugBatch, "Player Body: x = " + e.getComponent(PhysicsComponent.class).body.getPosition().x + ", y = " + e.getComponent(PhysicsComponent.class).body.getPosition().y, 10, 40);
        font.draw(debugBatch, "Player Transform: x = " + e.getComponent(TransformComponent.class).x + ", y = " + e.getComponent(TransformComponent.class).y, 10, 60);
        font.draw(debugBatch, "Drive Component: DriveDir = " + e.getComponent(DriveComponent.class).mDriveDirection + ", TurnDir = " + e.getComponent(DriveComponent.class).mTurnDirection, 10, 80);
        debugBatch.end();
    }

    public void processEntity(Entity entity, float deltaTime) {
        renderQueue.add(entity);
    }

}
