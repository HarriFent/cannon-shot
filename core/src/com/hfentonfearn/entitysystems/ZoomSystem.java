package com.hfentonfearn.entitysystems;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Vector2;
import com.hfentonfearn.components.MapDrawComponent;
import com.hfentonfearn.components.SpriteComponent;
import com.hfentonfearn.components.StaticMovementComponent;
import com.hfentonfearn.ecs.EntityFactory;

public class ZoomSystem extends EntitySystem {


    public static final float ZOOM_CLOSE = 0.1f;
    public static final float ZOOM_FAR = 1.5f;
    public static final float ZOOM_MAP= 5f;

    OrthographicCamera camera;
    private float zoom;

    float timeToCameraZoomTarget, cameraZoomTarget, cameraZoomOrigin, cameraZoomDuration;
    private ImmutableArray<Entity> clouds;

    public ZoomSystem() {
        zoom = ZOOM_FAR;
    }

    @Override
    public void addedToEngine(Engine engine) {
        super.addedToEngine(engine);
        camera = engine.getSystem(CameraSystem.class).getCamera();
        zoomTo(zoom,0);
        clouds = engine.getEntitiesFor(Family.all(StaticMovementComponent.class,SpriteComponent.class, MapDrawComponent.class).get());
    }

    @Override
    public void update(float deltaTime) {
        timeToCameraZoomTarget -= deltaTime;
        camera.zoom = Interpolation.fade.apply(cameraZoomOrigin, cameraZoomTarget, getProgress());
        if (!isZooming()&& clouds.size() > 0){
            for (Entity e: clouds)
                getEngine().removeEntity(e);
        }
        DebugRendererSystem.addDebug("Zooming In: ", isZoomingIn());
        DebugRendererSystem.addDebug("Zooming Out: ", isZoomingOut());
        DebugRendererSystem.addDebug("Num of Clouds: ", clouds.size());
    }

    private void zoomTo (float newZoom, float duration){
        cameraZoomOrigin = camera.zoom;
        cameraZoomTarget = newZoom;
        timeToCameraZoomTarget = cameraZoomDuration = duration;
    }

    public void zoomOut() {
        if (zoom != ZOOM_MAP) {
            if (zoom == ZOOM_FAR)
                zoom = ZOOM_MAP;
            if (zoom == ZOOM_CLOSE)
                zoom = ZOOM_FAR;
            zoomTo(zoom, 2f);
            for (int i = 0; i < 4; i++) {
                Vector2 movement = new Vector2(3, 0);
                Vector2 position = new Vector2();
                float width = camera.viewportWidth;
                float height = camera.viewportHeight;
                switch (i) {
                    case 0:
                        movement.scl(-1);
                        position = new Vector2(width * 1 / 4, height * 1 / 4);
                        break;
                    case 1:
                        movement.scl(-1);
                        position = new Vector2(width * 1 / 4, height * 3 / 4);
                        break;
                    case 2:
                        position = new Vector2(width * 3 / 4, height * 1 / 4);
                        break;
                    case 3:
                        position = new Vector2(width * 3 / 4, height * 3 / 4);
                }
                EntityFactory.createCloud(position, movement);
            }
        }
    }

    public void zoomIn() {
        if (zoom == ZOOM_FAR)
            zoom = ZOOM_CLOSE;
        if (zoom == ZOOM_MAP)
            zoom = ZOOM_FAR;
        zoomTo(zoom, 2f);
    }

    public float getProgress() {
        return timeToCameraZoomTarget < 0 ? 1 : 1f - timeToCameraZoomTarget / cameraZoomDuration;
    }

    public boolean isZooming() {
        return getProgress() < 1;
    }

    public boolean isZoomingIn() {
        return cameraZoomOrigin > cameraZoomTarget && isZooming();
    }

    public boolean isZoomingOut() {
        return cameraZoomOrigin < cameraZoomTarget && isZooming();
    }

    public float getZoom() {
        return zoom;
    }
}
