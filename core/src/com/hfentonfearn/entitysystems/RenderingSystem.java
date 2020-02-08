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
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.hfentonfearn.components.*;
import com.hfentonfearn.gameworld.GameWorld;
import com.hfentonfearn.helpers.AssetLoader;
import com.hfentonfearn.helpers.MappersHandler;

public class RenderingSystem extends EntitySystem {

    private Family family;
    private ImmutableArray<Entity> entities;

    static final float WINDOW_WIDTH = Gdx.graphics.getWidth()*2;
    static final float WINDOW_HEIGHT = Gdx.graphics.getHeight()*2;

    private SpriteBatch batch;
    private Array<Entity> renderQueue;
    private OrthographicCamera cam;
    private ImmutableArray<Entity> players;
    private TiledMapRenderer mapRenderer;

    //For Debug Mode
    private ShapeRenderer debugRenderer;
    private SpriteBatch debugBatch;
    private BitmapFont font;

    public RenderingSystem(SpriteBatch batch) {
        this.family = Family.all(TransformComponent.class, TextureComponent.class).get();

        renderQueue = new Array<Entity>();

        this.batch = batch;

        cam = new OrthographicCamera(WINDOW_WIDTH, WINDOW_HEIGHT);

        this.mapRenderer = new OrthogonalTiledMapRenderer(AssetLoader.map,this.batch);

        //For Debug Mode
        debugRenderer = new ShapeRenderer();
        font = new BitmapFont();
        debugBatch = new SpriteBatch();
    }

    @Override
    public void addedToEngine (Engine engine) {
        entities = engine.getEntitiesFor(family);
        players = engine.getEntitiesFor(Family.all(PlayerComponent.class).get());
    }

    @Override
    public void removedFromEngine (Engine engine) {
        entities = null;
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
            cam.position.set(tm.getX(), tm.getY(),0);
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

            batch.draw(tex.region,
                    trans.getX() - trans.getOriginX(),
                    trans.getY() - trans.getOriginY(),
                    trans.getOriginX(),
                    trans.getOriginY(),
                    trans.getWidth(),
                    trans.getHeight(),
                    trans.getScaleX(),
                    trans.getScaleY(),
                    trans.getAngle());
        }

        batch.end();
        if (GameWorld.DEBUGMODE)
            renderDebug();
        renderQueue.clear();
    }

    private void renderDebug() {
        debugRenderer.setProjectionMatrix(cam.combined);
        debugRenderer.begin(ShapeType.Line);
        Gdx.gl.glLineWidth(3);

        debugRenderer.setColor(Color.RED);
        for (Entity e : renderQueue) {
            CollisionComponent colComponent = MappersHandler.collision.get(e);
            Polygon poly = colComponent.collisionShape;
            debugRenderer.polyline(poly.getTransformedVertices());

            TransformComponent transformComponent = MappersHandler.transform.get(e);
            debugRenderer.circle(transformComponent.getX(),transformComponent.getY(),3);
        }

        debugRenderer.setColor(Color.YELLOW);
        MapObjects objects = AssetLoader.map.getLayers().get("collision").getObjects();
        for (MapObject obj: objects) {
            if (obj instanceof PolygonMapObject)
                debugRenderer.polygon(((PolygonMapObject) obj).getPolygon().getTransformedVertices());
            if (obj instanceof RectangleMapObject) {
                Rectangle r = ((RectangleMapObject) obj).getRectangle();
                debugRenderer.rect(r.x,r.y,r.width,r.height);
            }
        }
        debugRenderer.end();
        debugBatch.begin();
        font.setColor(Color.RED);
        font.draw(debugBatch,"DEBUG MODE",10, 20);
        debugBatch.end();
    }

    public void processEntity(Entity entity, float deltaTime) {
        renderQueue.add(entity);
    }

}
