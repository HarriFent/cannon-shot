package com.hfentonfearn.entitysystems;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Interpolation;

public class ZoomSystem extends EntitySystem {

    OrthographicCamera camera;

    public static final float ZOOM_CLOSE = 0.1f;
    public static final float ZOOM_FAR = 1.5f;
    public static final float ZOOM_MAP = 3;

    private float zoom;

    float timeToCameraZoomTarget, cameraZoomTarget, cameraZoomOrigin, cameraZoomDuration;

    public ZoomSystem() {
        zoom = ZOOM_FAR;
    }

    @Override
    public void addedToEngine(Engine engine) {
        super.addedToEngine(engine);
        camera = engine.getSystem(CameraSystem.class).getCamera();
        zoomTo(zoom,0);
    }

    @Override
    public void update(float deltaTime) {
        timeToCameraZoomTarget -= deltaTime;
        camera.zoom = Interpolation.fade.apply(cameraZoomOrigin, cameraZoomTarget, getProgress());
    }

    private void zoomTo (float newZoom, float duration){
        cameraZoomOrigin = camera.zoom;
        cameraZoomTarget = newZoom;
        timeToCameraZoomTarget = cameraZoomDuration = duration;
    }

    public void zoomOut() {
        if (zoom == ZOOM_FAR)
            zoom = ZOOM_MAP;
        if (zoom == ZOOM_CLOSE)
            zoom = ZOOM_FAR;
        zoomTo(zoom, 2f);
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

    public float getZoom() {
        return zoom;
    }
}
