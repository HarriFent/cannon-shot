package com.hfentonfearn.entitysystems;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Vector2;
import com.hfentonfearn.components.SpriteComponent;
import com.hfentonfearn.components.StaticMovementComponent;
import com.hfentonfearn.ecs.EntityFactory;
import com.hfentonfearn.utils.Components;

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
        clouds = engine.getEntitiesFor(Family.all(StaticMovementComponent.class,SpriteComponent.class).get());
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
            createClouds(new Vector2(3, 0),
                    new Vector2(0,camera.viewportHeight * 1 / 4));
        }
    }

    public void zoomIn() {
        if (zoom == ZOOM_FAR)
            zoom = ZOOM_CLOSE;
        if (zoom == ZOOM_MAP)
            zoom = ZOOM_FAR;
        zoomTo(zoom, 2f);
        createClouds(new Vector2(-3, 0),
                new Vector2(camera.viewportWidth * 1 / 4,camera.viewportHeight * 1 / 4));
    }

    private void createClouds(Vector2 movement, Vector2 position) {
        for (int i = 0; i < 4; i++) {
            Vector2 newMovement = movement.cpy();
            Vector2 newPosition = position.cpy();
            switch (i) {
                case 1:
                    newPosition.scl(new Vector2(1,3));
                    break;
                case 2:
                    newMovement.scl(-1);
                    newPosition.set(camera.viewportWidth - newPosition.x,newPosition.y);
                    break;
                case 3:
                    newMovement.scl(-1);
                    newPosition.set(camera.viewportWidth - newPosition.x,newPosition.y * 3);
                default:
            }
            Entity cloud = EntityFactory.createCloud(newPosition, newMovement);
        }
    }

    public float getProgress() {
        return timeToCameraZoomTarget < 0 ? 1 : 1f - timeToCameraZoomTarget / cameraZoomDuration;
    }

    public boolean isZooming() {
        return getProgress() < 1 && getProgress() > 0;
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
