package com.hfentonfearn.entitysystems;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import com.hfentonfearn.components.PlayerComponent;
import com.hfentonfearn.components.TextureComponent;
import com.hfentonfearn.components.TransformComponent;
import com.hfentonfearn.helpers.AssetLoader;
import com.hfentonfearn.helpers.CustomTiledMapRenderer;
import com.hfentonfearn.helpers.MappersHandler;

public class RenderingSystem extends EntitySystem {

    private ImmutableArray<Entity> entities;

    private SpriteBatch batch;
    private Array<Entity> renderQueue;
    private OrthographicCamera cam;
    private TiledMapRenderer mapRenderer;

    public RenderingSystem(SpriteBatch batch, OrthographicCamera camera) {
        renderQueue = new Array<Entity>();
        this.batch = batch;
        cam = camera;
        this.mapRenderer = new CustomTiledMapRenderer(AssetLoader.map,this.batch);
    }

    @Override
    public void addedToEngine (Engine engine) {
        entities = engine.getEntitiesFor(Family.all(TransformComponent.class, TextureComponent.class).get());
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
