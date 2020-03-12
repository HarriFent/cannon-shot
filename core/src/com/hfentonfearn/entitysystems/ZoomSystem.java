package com.hfentonfearn.entitysystems;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Vector2;
import com.hfentonfearn.GameManager;
import com.hfentonfearn.objects.Cloud;
import com.hfentonfearn.utils.AssetLoader;

public class ZoomSystem extends EntitySystem {


    public static final float ZOOM_CLOSE = 0.1f;
    public static final float ZOOM_FAR = 1.5f;
    public static final float ZOOM_MAP= 5f;

    private OrthographicCamera camera;
    private float zoom;

    float timeToCameraZoomTarget, cameraZoomTarget, cameraZoomOrigin, cameraZoomDuration;
    private Cloud[] clouds;

    public ZoomSystem() {
        zoom = ZOOM_FAR;
    }

    @Override
    public void addedToEngine(Engine engine) {
        super.addedToEngine(engine);
        camera = engine.getSystem(CameraSystem.class).getCamera();
        zoomTo(zoom,0);
        createClouds();
    }

    @Override
    public void update(float deltaTime) {
        DebugRendererSystem.addDebug("Cloud num: ", clouds != null ? clouds.length : 0);
        timeToCameraZoomTarget -= deltaTime;
        camera.zoom = Interpolation.fade.apply(cameraZoomOrigin, cameraZoomTarget, getProgress());
    }

    private void zoomTo (float newZoom, float duration){
        cameraZoomOrigin = camera.zoom;
        cameraZoomTarget = newZoom;
        timeToCameraZoomTarget = cameraZoomDuration = duration;
    }

    public void zoomOut() {
        if (zoom != ZOOM_MAP) {
            if (zoom == ZOOM_FAR) {
                zoom = ZOOM_MAP;
                GameManager.pause();
            }
            if (zoom == ZOOM_CLOSE)
                zoom = ZOOM_FAR;
            zoomTo(zoom, 2f);
            resetClouds(new Vector2(3, 0),
                    new Vector2(0,camera.viewportHeight * 1 / 4));
        }
    }

    public void zoomIn() {
        if (zoom == ZOOM_FAR)
            zoom = ZOOM_CLOSE;
        if (zoom == ZOOM_MAP) {
            zoom = ZOOM_FAR;
            GameManager.resume();
        }
        zoomTo(zoom, 2f);
        resetClouds(new Vector2(-3, 0),
                new Vector2(camera.viewportWidth * 1 / 4,camera.viewportHeight * 1 / 4));
    }

    public void resetClouds(Vector2 movement, Vector2 position) {
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
            clouds[i].reset(newMovement, newPosition);
        }
    }

    private void createClouds() {
        clouds = new Cloud[4];
        for (int i = 0; i < 4; i++) {
            Cloud cloudSprite = new Cloud(AssetLoader.clouds.getRandomCloud());
            cloudSprite.setScale(3);
            clouds[i] = cloudSprite;
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

    public Cloud[] getClouds() { return clouds; }
}
