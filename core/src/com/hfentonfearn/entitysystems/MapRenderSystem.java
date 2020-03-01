package com.hfentonfearn.entitysystems;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import com.hfentonfearn.components.MapDrawComponent;
import com.hfentonfearn.components.PlayerComponent;
import com.hfentonfearn.components.SpriteComponent;
import com.hfentonfearn.components.StaticMovementComponent;
import com.hfentonfearn.utils.AssetLoader;
import com.hfentonfearn.utils.Components;

public class MapRenderSystem extends IteratingSystem implements Disposable {

    private SpriteBatch batch;
    private CameraSystem cameraSystem;
    private ZoomSystem zoomSystem;
    private ImmutableArray<Entity> players;

    public MapRenderSystem() {
        super(Family.all(SpriteComponent.class, MapDrawComponent.class).get());
        batch = new SpriteBatch();
    }

    @Override
    public void addedToEngine (Engine engine) {
        super.addedToEngine(engine);
        cameraSystem = engine.getSystem(CameraSystem.class);
        zoomSystem = engine.getSystem(ZoomSystem.class);
        players = engine.getEntitiesFor(Family.all(PlayerComponent.class).get());
    }

    @Override
    public void update (float deltaTime) {
        if (zoomSystem.getZoom() == ZoomSystem.ZOOM_MAP || (zoomSystem.isZoomingIn() && zoomSystem.getZoom() == ZoomSystem.ZOOM_FAR)) {
            OrthographicCamera camera = cameraSystem.getCamera();
            float viewWidth = camera.viewportWidth;
            float viewHeight = camera.viewportHeight;

            //Create tiledMap sprite
            Sprite mapbg = new Sprite(AssetLoader.minimap.mapBackground);
            mapbg.setSize(viewWidth,viewHeight);

            Sprite map = new Sprite(AssetLoader.minimap.mapOverview);
            map.setSize((float) (viewHeight * 0.9), (float) (viewHeight * 0.9));
            map.setCenter(viewWidth/2,viewHeight/2);

            Sprite cross = new Sprite(AssetLoader.minimap.cross);
            cross.setScale(0.5f);
            for (Entity player : players) {
                Vector2 pos = Components.PHYSICS.get(player).getPosition();
                pos = positionToMiniMap(pos.cpy(), map.getX(), map.getY(),(float) (viewHeight * 0.9), (float) (viewHeight * 0.9));
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
            super.update(deltaTime);
            batch.end();
        }
    }

    private float getAlpha() {
        float alpha = zoomSystem.getZoom() == ZoomSystem.ZOOM_MAP ? 1 : 0;
        if(zoomSystem.isZooming()) {
            if (zoomSystem.isZoomingIn()){
                if (zoomSystem.getProgress() > 0.5) {
                    alpha = 0;
                } else {
                    alpha = (1 -zoomSystem.getProgress()) * 2;
                }
            }else {
                if (zoomSystem.getProgress() > 0.5) {
                    alpha = (float) ((zoomSystem.getProgress() - 0.5) * 2);
                } else {
                    alpha = 0;
                }
            }
        }
        return alpha;
    }

    public Vector2 positionToMiniMap(Vector2 pos, float x, float y, float width, float height) {
        width -= 120;
        height -= 120;
        float newx = ((width * pos.x) / AssetLoader.map.width) + x + 60;
        float newy = ((height * pos.y) / AssetLoader.map.height) + y + 60;
        pos.set(newx, newy);
        return pos;
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        Array<Sprite> sprites = Components.SPRITE.get(entity).getSprites();
        for (Sprite sprite : sprites) {
            if (zoomSystem.isZoomingIn()) {
                if (zoomSystem.getProgress() < 0.5) {
                    sprite.setAlpha(zoomSystem.getProgress() * 2);
                } else {
                    StaticMovementComponent movementComponent = Components.STATIC_MOVEMENT.get(entity);
                    sprite.translate(movementComponent.movement.x, movementComponent.movement.y);
                    sprite.setAlpha((float) (1 - (zoomSystem.getProgress() - 0.5) * 2));
                }
            } else if (zoomSystem.isZoomingOut()) {
                if (zoomSystem.getProgress() < 0.5) {
                    StaticMovementComponent movementComponent = Components.STATIC_MOVEMENT.get(entity);
                    sprite.translate(movementComponent.movement.x, movementComponent.movement.y);
                    sprite.setAlpha(zoomSystem.getProgress() * 2);
                } else {
                    sprite.setAlpha((float) (1 - (zoomSystem.getProgress() - 0.5) * 2));
                }
            }
            sprite.draw(batch);
        }
    }

    @Override
    public void dispose() {

    }
}
