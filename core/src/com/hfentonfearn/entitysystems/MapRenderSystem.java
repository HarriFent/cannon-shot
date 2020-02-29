package com.hfentonfearn.entitysystems;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import com.hfentonfearn.components.MapDrawComponent;
import com.hfentonfearn.components.SpriteComponent;
import com.hfentonfearn.components.StaticMovementComponent;
import com.hfentonfearn.utils.AssetLoader;
import com.hfentonfearn.utils.Components;

public class MapRenderSystem extends IteratingSystem implements Disposable {

    private SpriteBatch batch;
    private CameraSystem cameraSystem;
    private ZoomSystem zoomSystem;

    public MapRenderSystem() {
        super(Family.all(SpriteComponent.class, MapDrawComponent.class).get());
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

            //Create map sprite
            Sprite mapbg = new Sprite(AssetLoader.map.mapBackground);
            mapbg.setSize(viewWidth,viewHeight);
            Sprite map = new Sprite(AssetLoader.map.mapOverview);
            map.setSize((float) (viewHeight * 0.9), (float) (viewHeight * 0.9));
            map.setOriginCenter();
            map.setOriginBasedPosition(viewWidth/2,viewHeight/2);


            //Set Map Alpha
            if(!zoomSystem.isZooming()){
                map.setAlpha(1f);
                mapbg.setAlpha(1f);
            } else {
                if (zoomSystem.getProgress() > 0.5) {
                    float alpha = (float) ((zoomSystem.getProgress() - 0.5) * 2);
                    map.setAlpha(alpha);
                    mapbg.setAlpha(alpha);
                } else {
                    map.setAlpha(0f);
                    mapbg.setAlpha(0f);
                }
            }

            //Draw sprites
            batch.begin();
            mapbg.draw(batch);
            map.draw(batch);
            super.update(deltaTime);
            batch.end();
        }
    }



    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        Array<Sprite> sprites = Components.SPRITE.get(entity).getSprites();
        for (Sprite sprite : sprites) {
            if (zoomSystem.getProgress() < 0.5) {
                sprite.setAlpha(zoomSystem.getProgress() * 2);
            } else {
                StaticMovementComponent movementComponent = Components.STATIC_MOVEMENT.get(entity);
                sprite.translate(movementComponent.movement.x,movementComponent.movement.y);
                sprite.setAlpha((float) (1 - (zoomSystem.getProgress()-0.5)*2));
            }
            sprite.draw(batch);
        }
    }

    @Override
    public void dispose() {

    }
}
