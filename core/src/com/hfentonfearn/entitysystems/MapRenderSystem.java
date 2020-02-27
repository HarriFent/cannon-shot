package com.hfentonfearn.entitysystems;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Disposable;
import com.hfentonfearn.components.SpriteComponent;
import com.hfentonfearn.utils.AssetLoader;

public class MapRenderSystem extends IteratingSystem implements Disposable {

    private SpriteBatch batch;
    private CameraSystem cameraSystem;
    private ZoomSystem zoomSystem;

    public MapRenderSystem() {
        super(Family.one(SpriteComponent.class).get());
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
        if (zoomSystem.getZoom() == ZoomSystem.ZOOM_MAP) {
            OrthographicCamera camera = cameraSystem.getCamera();
            float viewWidth = camera.viewportWidth;
            float viewHeight = camera.viewportHeight;
            Sprite map = new Sprite(AssetLoader.map.mapOverview);
            map.setSize((float) (viewHeight * 0.9), (float) (viewHeight * 0.9));
            map.setOriginCenter();
            map.setOriginBasedPosition(viewWidth/2,viewHeight/2);
            if(!zoomSystem.isZooming()){
                map.setAlpha(1f);
            } else {
                map.setAlpha(zoomSystem.getProgress());
            }
            batch.begin();
            map.draw(batch);
            batch.end();
        }
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {

    }

    @Override
    public void dispose() {

    }
}
