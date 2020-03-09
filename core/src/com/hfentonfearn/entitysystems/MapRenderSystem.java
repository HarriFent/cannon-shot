package com.hfentonfearn.entitysystems;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Disposable;
import com.hfentonfearn.components.PlayerComponent;
import com.hfentonfearn.objects.Cloud;
import com.hfentonfearn.utils.AssetLoader;
import com.hfentonfearn.utils.Components;

public class MapRenderSystem extends EntitySystem implements Disposable {

    private SpriteBatch batch;
    private CameraSystem cameraSystem;
    private ZoomSystem zoomSystem;

    public MapRenderSystem() {
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

            float mapHeight = viewHeight * 0.9f;

            Sprite map = new Sprite(AssetLoader.minimap.mapOverview);
            map.setSize(mapHeight, mapHeight);
            map.setCenter(viewWidth/2,viewHeight/2);

            Sprite cross = new Sprite(AssetLoader.minimap.cross);
            cross.setScale(0.5f);
            if (PlayerComponent.player != null) {
                Vector2 pos = Components.PHYSICS.get(PlayerComponent.player).getPosition();
                pos = positionToMiniMap(pos.cpy(), map.getX(), map.getY(), mapHeight, mapHeight);
                cross.setCenter(pos.x, pos.y);
            }

            //Set Map Alpha
            float alpha = getAlpha();

            map.setAlpha(alpha);
            mapbg.setAlpha(alpha);
            cross.setAlpha(alpha);

            //Draw sprites
            batch.begin();
            mapbg.draw(batch);
            map.draw(batch);
            cross.draw(batch);

            //Draw the clouds
            if (zoomSystem.isZooming())
                for ( Cloud cloud : zoomSystem.getClouds())
                    drawCloud(cloud);
            batch.end();
        }
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

    public Vector2 positionToMiniMap(Vector2 pos, float x, float y, float width, float height) {
        int border = 60;
        width -= border*2;
        height -= border*2;
        float newx = ((width * pos.x) / AssetLoader.map.width) + x + border;
        float newy = ((height * pos.y) / AssetLoader.map.height) + y + border;
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
