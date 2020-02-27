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
import com.hfentonfearn.components.PhysicsComponent;
import com.hfentonfearn.components.SpriteComponent;
import com.hfentonfearn.utils.AssetLoader;
import com.hfentonfearn.utils.Components;
import com.hfentonfearn.utils.CustomTiledMapRenderer;

public class FarRenderSystem extends IteratingSystem implements Disposable {

    private static final int spriteRotationOffset = -0;
    private final CustomTiledMapRenderer mapRenderer;

    private SpriteBatch batch;
    private ShapeRenderer shapeRenderer;
    private CameraSystem cameraSystem;
    private ZoomSystem zoomSystem;

    public FarRenderSystem() {
        super(Family.one(SpriteComponent.class).get());
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
        }
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        Array<Sprite> sprites = Components.SPRITE.get(entity).getSprites();
        OrthographicCamera camera = cameraSystem.getCamera();
        batch.setProjectionMatrix(camera.combined);

        for (Sprite sprite : sprites) {
            if (Components.PHYSICS.has(entity)) {
                PhysicsComponent physics = Components.PHYSICS.get(entity);
                Vector2 pos = physics.getPosition();
                sprite.setCenter(pos.x, pos.y);
                sprite.setRotation((MathUtils.radiansToDegrees * physics.getBody().getAngle()) + spriteRotationOffset);
            }
            sprite.draw(batch);
        }
        //Render health bar and stuff with the shapeRenderer
    }

    @Override
    public void dispose() {

    }
}
