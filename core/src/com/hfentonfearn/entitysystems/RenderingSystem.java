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
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.objects.PolygonMapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.hfentonfearn.components.*;
import com.hfentonfearn.gameworld.GameWorld;
import com.hfentonfearn.helpers.AssetLoader;
import com.hfentonfearn.helpers.Constants;
import com.hfentonfearn.helpers.CustomTiledMapRenderer;
import com.hfentonfearn.helpers.MappersHandler;

import static com.hfentonfearn.helpers.Constants.*;

public class RenderingSystem extends EntitySystem {

    private ImmutableArray<Entity> entities;

    private SpriteBatch batch;
    private Array<Entity> renderQueue;
    private OrthographicCamera cam;
    private ImmutableArray<Entity> players;
    private TiledMapRenderer mapRenderer;
    private MapProperties mapProps;

    public RenderingSystem(SpriteBatch batch, OrthographicCamera camera) {
        renderQueue = new Array<Entity>();
        this.batch = batch;
        cam = camera;
        this.mapRenderer = new CustomTiledMapRenderer(AssetLoader.map,this.batch);
        mapProps = AssetLoader.map.getProperties();
    }

    @Override
    public void addedToEngine (Engine engine) {
        entities = engine.getEntitiesFor(Family.all(TransformComponent.class, TextureComponent.class).get());
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

        // Set the camera position to the player
        Entity player;
        if (players.get(0) != null) {
            player = players.get(0);
            TransformComponent tm = MappersHandler.transform.get(player);
            cam.position.set(tm.position.x,tm.position.y,0);

            // Clamp the camera to the map size
            float mapWidth = mapProps.get("width", Integer.class) * mapProps.get("tilewidth", Integer.class);
            float mapHeight = mapProps.get("height", Integer.class) * mapProps.get("tileheight", Integer.class);
            cam.position.x = MathUtils.clamp(cam.position.x, cam.viewportWidth/2, mapWidth - (cam.viewportWidth/2));
            cam.position.y = MathUtils.clamp(cam.position.y, cam.viewportHeight/2, mapHeight - (cam.viewportHeight/2));
        }

        cam.update();

        //Renders the tiled map
        mapRenderer.setView(cam);
        mapRenderer.render();

        //Setup the sprite batch for drawing entities
        batch.setProjectionMatrix(cam.combined);
        batch.enableBlending();
        batch.begin();

        for (Entity entity : renderQueue) {
            TextureComponent tex = MappersHandler.texture.get(entity);
            TransformComponent trans = MappersHandler.transform.get(entity);

            if (tex.region == null) {
                continue;
            }
            //Draw each entity
            batch.draw(tex.region,trans.position.x - trans.origin.x,trans.position.y - trans.origin.y,
                    trans.origin.x, trans.origin.y, tex.region.getRegionWidth(), tex.region.getRegionHeight(),
                    trans.scale.x, trans.scale.y, trans.rotation);
        }

        batch.end();
        renderQueue.clear();
    }

    public void processEntity(Entity entity, float deltaTime) {
        renderQueue.add(entity);
    }

}
