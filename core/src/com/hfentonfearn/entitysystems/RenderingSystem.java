package com.hfentonfearn.entitysystems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.utils.Array;
import com.hfentonfearn.components.TextureComponent;
import com.hfentonfearn.components.TransformComponent;
import com.hfentonfearn.ecs.Components;
import com.hfentonfearn.gameworld.ZoomLevel;
import com.hfentonfearn.helpers.AssetLoader;
import com.hfentonfearn.helpers.CustomTiledMapRenderer;

import static com.hfentonfearn.helpers.Constants.WORLD_PIXEL_HEIGHT;
import static com.hfentonfearn.helpers.Constants.WORLD_PIXEL_WIDTH;

public class RenderingSystem extends IteratingSystem {

    private SpriteBatch batch;
    private Array<Entity> renderQueue;
    private OrthographicCamera cam;
    private TiledMapRenderer mapRenderer;
    private ZoomLevel zoom;

    public RenderingSystem(OrthographicCamera camera, ZoomLevel zoom) {
        super(Family.all(TransformComponent.class, TextureComponent.class).get());
        renderQueue = new Array<Entity>();
        this.batch = new SpriteBatch();
        cam = camera;
        this.zoom = zoom;
        this.mapRenderer = new CustomTiledMapRenderer(AssetLoader.map,this.batch);
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        if (zoom.isZooming()) {

        } else {
            switch (zoom.getZoomLevel()) {
                case CLOSE:

                    break;
                case FAR:
                    //Renders the tiled map
                    mapRenderer.setView(cam);
                    mapRenderer.render();

                    //Setup the sprite batch for drawing entities
                    batch.setProjectionMatrix(cam.combined);
                    batch.enableBlending();
                    batch.begin();

                    for (Entity entity : renderQueue) {
                        TextureComponent tex = Components.texture.get(entity);
                        TransformComponent trans = Components.transform.get(entity);

                        if (tex.region == null) {
                            continue;
                        }
                        //Draw each entity
                        batch.draw(tex.region, trans.position.x - trans.origin.x, trans.position.y - trans.origin.y,
                                trans.origin.x, trans.origin.y, tex.region.getRegionWidth(), tex.region.getRegionHeight(),
                                trans.scale.x, trans.scale.y, trans.rotation);
                    }

                    batch.end();
                    renderQueue.clear();
                    break;
                case MAP:
                    batch.begin();
                    TextureRegion tex = AssetLoader.bgMapView;
                    batch.draw(tex,0,0,WORLD_PIXEL_WIDTH,WORLD_PIXEL_HEIGHT);
                    batch.end();
            }
        }
    }

    @Override
    public void processEntity(Entity entity, float deltaTime) {
        renderQueue.add(entity);
    }
}
