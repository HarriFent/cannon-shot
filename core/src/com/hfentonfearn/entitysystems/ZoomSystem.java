package com.hfentonfearn.entitysystems;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.gdx.graphics.OrthographicCamera;

public class ZoomSystem extends EntitySystem {

    OrthographicCamera camera;

    private static final float CameraAcceleration = 0.03f;

    public static final float ZOOM_CLOSE = 0.2f;
    public static final float ZOOM_FAR = 1.5f;
    public static final float ZOOM_MAP = 3;

    private float zoom;

    public ZoomSystem() {
        zoom = ZOOM_FAR;
    }

    @Override
    public void addedToEngine(Engine engine) {
        super.addedToEngine(engine);
        camera = engine.getSystem(CameraSystem.class).getCamera();
    }

    @Override
    public void update(float deltaTime) {
        CameraSystem cameraSystem = getEngine().getSystem(CameraSystem.class);
        float currentZoom = camera.zoom;
        float cameraAcceleration = Math.abs(currentZoom - zoom) /10;
        if (currentZoom > zoom) {
            cameraSystem.zoom(-cameraAcceleration);
        }
        if (currentZoom < zoom) {
            cameraSystem.zoom(cameraAcceleration);
        }
        if (Math.abs(currentZoom - zoom) < 0.01) {
            camera.zoom = zoom;
        }
    }

    public void zoomOut() {
        if (zoom == ZOOM_FAR)
            zoom = ZOOM_MAP;
        if (zoom == ZOOM_CLOSE)
            zoom = ZOOM_FAR;
    }

    public void zoomIn() {
        if (zoom == ZOOM_FAR)
            zoom = ZOOM_CLOSE;
        if (zoom == ZOOM_MAP)
            zoom = ZOOM_FAR;
    }
}
