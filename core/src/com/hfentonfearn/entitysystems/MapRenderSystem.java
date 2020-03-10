package com.hfentonfearn.entitysystems;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Disposable;
import com.hfentonfearn.components.ShipStatisticComponent;
import com.hfentonfearn.ecs.EntityCategory;
import com.hfentonfearn.objects.Cloud;
import com.hfentonfearn.utils.AssetLoader;
import com.hfentonfearn.utils.Components;

public class MapRenderSystem extends IteratingSystem implements Disposable {

    private SpriteBatch batch;
    private CameraSystem cameraSystem;
    private ZoomSystem zoomSystem;
    private float alpha;
    private Sprite map;

    public MapRenderSystem() {
        super(Family.all(ShipStatisticComponent.class).get());
        batch = new SpriteBatch();
    }

    @Override
    public void addedToEngine (Engine engine) {
        super.addedToEngine(engine);
        cameraSystem = engine.getSystem(CameraSystem.class);
        zoomSystem = engine.getSystem(ZoomSystem.class);
    }

    @Override
    public void update (float deltaTime) {
        if (zoomSystem.getZoom() == ZoomSystem.ZOOM_MAP || (zoomSystem.isZoomingIn() && zoomSystem.getZoom() == ZoomSystem.ZOOM_FAR)) {
            OrthographicCamera camera = cameraSystem.getCamera();
            float viewWidth = camera.viewportWidth;
            float viewHeight = camera.viewportHeight;

            //Create map sprite
            Sprite mapbg = new Sprite(AssetLoader.minimap.mapBackground);
            mapbg.setSize(viewWidth,viewHeight);

            float mapSize = viewHeight * 0.9f;

            map = new Sprite(AssetLoader.minimap.mapOverview);
            map.setSize(mapSize, mapSize);
            map.setCenter(viewWidth/2,viewHeight/2);

            //Set Map Alpha
            alpha = getAlpha();

            map.setAlpha(alpha);
            mapbg.setAlpha(alpha);

            //Draw sprites
            batch.begin();
            mapbg.draw(batch);
            map.draw(batch);
            super.update(deltaTime);
            //Draw the clouds
            if (zoomSystem.isZooming())
                for ( Cloud cloud : zoomSystem.getClouds())
                    drawCloud(cloud);
            batch.end();
        }
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        Sprite cross;
        if (entity.flags == EntityCategory.PLAYER) {
            cross = new Sprite(AssetLoader.minimap.crossGreen);
        } else {
            cross = new Sprite(AssetLoader.minimap.crossRed);
        }
        cross.setScale(0.5f);
        Vector2 pos = positionToMiniMap(Components.PHYSICS.get(entity).getPosition());
        cross.setCenter(pos.x, pos.y);
        cross.setAlpha(alpha);
        cross.draw(batch);
    }

    private float getAlpha() {
        float alpha = zoomSystem.getZoom() == ZoomSystem.ZOOM_MAP ? 1 : 0;
        if (zoomSystem.isZoomingIn()) {
            if (zoomSystem.getProgress() > 0.5) {
                alpha = 0;
            } else {
                alpha = (1 -zoomSystem.getProgress()) * 2;
            }
        } else if (zoomSystem.isZoomingOut()) {
            if (zoomSystem.getProgress() > 0.5) {
                alpha = (float) ((zoomSystem.getProgress() - 0.5) * 2);
            } else {
                alpha = 0;
            }
        }
        return alpha;
    }

    public Vector2 positionToMiniMap(Vector2 pos) {
        int border = 60;
        float width = map.getWidth() - border*2;
        float height = map.getHeight() - border*2;
        float newx = ((width * pos.x) / AssetLoader.map.width) + map.getX() + border;
        float newy = ((height * pos.y) / AssetLoader.map.height) + map.getY() + border;
        pos.set(newx, newy);
        return pos;
    }

    protected void drawCloud(Cloud cloud) {
        if (zoomSystem.isZoomingIn()) {
            if (zoomSystem.getProgress() < 0.5) {
                cloud.setAlpha(zoomSystem.getProgress() * 2);
            } else {
                cloud.translate(cloud.getMovement().x, cloud.getMovement().y);
                cloud.setAlpha((float) (1 - (zoomSystem.getProgress() - 0.5) * 2));
            }
        } else if (zoomSystem.isZoomingOut()) {
            if (zoomSystem.getProgress() < 0.5) {
                cloud.translate(cloud.getMovement().x, cloud.getMovement().y);
                cloud.setAlpha(zoomSystem.getProgress() * 2);
            } else {
                cloud.setAlpha((float) (1 - (zoomSystem.getProgress() - 0.5) * 2));
            }
        }
        cloud.draw(batch);
    }

    @Override
    public void dispose() {

    }
}
