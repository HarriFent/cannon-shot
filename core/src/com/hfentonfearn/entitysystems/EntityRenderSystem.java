package com.hfentonfearn.entitysystems;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import com.hfentonfearn.components.*;
import com.hfentonfearn.utils.AssetLoader;
import com.hfentonfearn.utils.Components;
import com.hfentonfearn.utils.CustomTiledMapRenderer;

public class EntityRenderSystem extends IteratingSystem implements Disposable {

    private static final int spriteRotationOffset = -0;
    private final CustomTiledMapRenderer mapRenderer;

    private SpriteBatch batch;
    private ShapeRenderer shapeRenderer;
    private CameraSystem cameraSystem;
    private ZoomSystem zoomSystem;

    public EntityRenderSystem() {
        super(Family.all(SpriteComponent.class).get());
        batch = new SpriteBatch();
        shapeRenderer = new ShapeRenderer();
        mapRenderer = new CustomTiledMapRenderer(AssetLoader.map.map,this.batch);
    }

    @Override
    public void addedToEngine (Engine engine) {
        super.addedToEngine(engine);
        cameraSystem = engine.getSystem(CameraSystem.class);
        zoomSystem = engine.getSystem(ZoomSystem.class);
    }

    @Override
    public void update (float deltaTime) {
        if (zoomSystem.isZooming() || zoomSystem.getZoom() == ZoomSystem.ZOOM_FAR) {
            //Render Tiled Map
            mapRenderer.setView(cameraSystem.getCamera());
            mapRenderer.render();

            batch.setProjectionMatrix(cameraSystem.getCamera().combined);
            batch.begin();
            shapeRenderer.setProjectionMatrix(cameraSystem.getCamera().combined);
            shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
            super.update(deltaTime);
            batch.end();
            shapeRenderer.end();
        } else if (zoomSystem.getZoom() == ZoomSystem.ZOOM_MAP) {
            float viewWidth = cameraSystem.getCamera().viewportWidth;
            float viewHeight = cameraSystem.getCamera().viewportHeight;

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
        OrthographicCamera camera = cameraSystem.getCamera();
        batch.setProjectionMatrix(camera.combined);

        for (Sprite sprite : sprites) {
            if (Components.TYPE.get(entity).type == TypeComponent.CLOUD){
                if (zoomSystem.isZoomingIn()){
                    if (zoomSystem.getProgress() < 0.5) {
                        sprite.setAlpha(zoomSystem.getProgress() * 2);
                    } else {
                        StaticMovementComponent movementComponent = Components.STATIC_MOVEMENT.get(entity);
                        sprite.translate(movementComponent.movement.x, movementComponent.movement.y);
                        sprite.setAlpha((float) (1 - (zoomSystem.getProgress() - 0.5) * 2));
                    }
                } else {
                    if (zoomSystem.getProgress() < 0.5) {
                        sprite.setAlpha(zoomSystem.getProgress() * 2);
                    } else {
                        StaticMovementComponent movementComponent = Components.STATIC_MOVEMENT.get(entity);
                        sprite.translate(movementComponent.movement.x, movementComponent.movement.y);
                        sprite.setAlpha((float) (1 - (zoomSystem.getProgress() - 0.5) * 2));
                    }
                }
            } else {
                if (Components.PHYSICS.has(entity)) {
                    PhysicsComponent physics = Components.PHYSICS.get(entity);
                    Vector2 pos = physics.getPosition();
                    sprite.setCenter(pos.x, pos.y);
                    sprite.setRotation((MathUtils.radiansToDegrees * physics.getBody().getAngle()) + spriteRotationOffset);
                }
            }
            sprite.draw(batch);
        }
        //Render health bar and stuff with the shapeRenderer
    }

    @Override
    public void dispose() {

    }
}
