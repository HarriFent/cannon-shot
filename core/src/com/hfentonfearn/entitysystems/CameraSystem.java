package com.hfentonfearn.entitysystems;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.viewport.ScalingViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.hfentonfearn.ecs.Components;

public class CameraSystem extends EntitySystem {

    private OrthographicCamera camera;
    private Viewport viewport;
    private Vector2 target;
    boolean smooth = false;

    private Entity targetEntity;

    private float minZoom;
    private float maxZoom;

    private float zoomScalar = 0.25f;

    private Rectangle worldBounds;
    private boolean clampToBounds = true;

    public CameraSystem(float viewportWidth, float viewportHeight) {
        camera = new OrthographicCamera(viewportWidth, viewportHeight);
        viewport = new ScalingViewport(Scaling.stretch, viewportWidth, viewportHeight, camera);
    }

    public void setWorldBounds(float worldWidth, float worldHeight) {
        worldBounds = new Rectangle(0, 0, worldWidth, worldHeight);
        maxZoom = (1.0f / worldWidth) * camera.viewportWidth;
        minZoom = worldWidth / camera.viewportWidth;
        updateCameras();
    }

    @Override
    public void addedToEngine(Engine engine) {
        super.addedToEngine(engine);
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        if (targetEntity != null)
            followTargetEntity();
        if (smooth && target != null) {
            camera.position.add(camera.position.cpy().scl(-1)
                    .add(target.x, target.y, 0).scl(0.1f));
        }
    }

    public void followTargetEntity() {
        Vector2 pos = Components.PHYSICS.get(targetEntity).getPosition();
        if (!pos.epsilonEquals(target))
            smoothFollow(pos);
        if (!pos.epsilonEquals(camera.position.x,camera.position.y)) {
            clamp();
            updateCameras();
        }
    }

    public OrthographicCamera getCamera() {
        return camera;
    }

    public Vector2 screenToWorldCords(float screenX, float screenY) {
        Vector3 pos = new Vector3(screenX, screenY, 0);
        pos.set(camera.unproject(pos));
        return new Vector2(pos.x, pos.y);
    }

    public void zoom(float amount) {
        amount *= (camera.zoom / maxZoom) * zoomScalar;
        camera.zoom += amount;
        clamp();
        updateCameras();
    }

    private void updateCameras(){
        camera.update();
    }

    private void clamp(){
        if(clampToBounds){
            camera.zoom = MathUtils.clamp(camera.zoom, maxZoom, minZoom);

            float xMin = (camera.viewportWidth * camera.zoom * 0.5f);
            float xMax = worldBounds.width - xMin;
            float yMin = (camera.viewportHeight * camera.zoom * 0.5f);
            float yMax = worldBounds.height - yMin;

            float x = MathUtils.round(MathUtils.clamp(camera.position.x, xMin, xMax));
            float y = MathUtils.round(MathUtils.clamp(camera.position.y, yMin, yMax));

            camera.position.set(x, y, 0);
        }
    }

    public void setTarget(Vector2 target) {
        this.target = target;
    }

    public void smoothFollow(Vector2 target) {
        smooth = true;
        this.target = target;
    }

    public void goTo(float posX, float posY) {
        target = null;
        camera.position.set(posX, posY, 0);
    }

    public void goToSmooth(Vector2 position) {
        target = position.cpy();
        smooth = true;
    }

    public void setTargetEntity(Entity targetEntity) {
        this.targetEntity = targetEntity;
    }

    public void resetTargetEntity() {
        this.targetEntity = null;
    }
}
