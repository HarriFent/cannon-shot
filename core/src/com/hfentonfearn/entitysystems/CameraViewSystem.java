package com.hfentonfearn.entitysystems;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.MapProperties;
import com.hfentonfearn.components.PlayerComponent;
import com.hfentonfearn.gameworld.ZoomLevel;
import com.hfentonfearn.utils.AssetLoader;

import static com.hfentonfearn.gameworld.ZoomLevel.ZoomLevelEnum.*;

public class CameraViewSystem extends EntitySystem {

    private OrthographicCamera cam;
    private ZoomLevel zoom;
    private MapProperties mapProps;
    private ImmutableArray<Entity> players;

    public CameraViewSystem(OrthographicCamera cam, ZoomLevel zoomLevel) {
        this.cam = cam;
        zoom = zoomLevel;
        zoom.setCurrentZoom(cam.zoom);
        mapProps = AssetLoader.map.map.getProperties();
    }

    @Override
    public void addedToEngine (Engine engine) {
        players = engine.getEntitiesFor(Family.all(PlayerComponent.class).get());
    }

    @Override
    public void removedFromEngine (Engine engine) {
        players = null;
    }

    @Override
    public void update(float deltaTime) {
        if (Gdx.input.isKeyJustPressed(Input.Keys.Q)) {
            switch (zoom.getZoomLevel()) {
                case FAR:
                    zoom.setZoomLevel(MAP);
                    break;
                case CLOSE:
                    zoom.setZoomLevel(FAR);
            }
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.E)) {
            switch (zoom.getZoomLevel()) {
                case MAP:
                    zoom.setZoomLevel(FAR);
                    break;
                case FAR:
                    zoom.setZoomLevel(CLOSE);
            }
        }
        zoom.update(deltaTime);
        cam.zoom = zoom.getCurrentZoom();

        setCameraToPlayer();
    }

    private void setCameraToPlayer() {
        // Set the camera position to the player
        /*Entity player;
        if (players.get(0) != null) {
            player = players.get(0);
            TransformComponent tm = Components.transform.get(player);
            cam.position.set(tm.position.x,tm.position.y,0);

            // Clamp the camera to the map size
            float mapWidth = mapProps.get("width", Integer.class) * mapProps.get("tilewidth", Integer.class);
            float mapHeight = mapProps.get("height", Integer.class) * mapProps.get("tileheight", Integer.class);
            cam.position.x = MathUtils.clamp(cam.position.x, cam.viewportWidth/2 * zoom.getCurrentZoom(), mapWidth - (cam.viewportWidth/2) * zoom.getCurrentZoom());
            cam.position.y = MathUtils.clamp(cam.position.y, cam.viewportHeight/2 * zoom.getCurrentZoom(), mapHeight - (cam.viewportHeight/2) * zoom.getCurrentZoom());
        }
        cam.update();*/
    }
}
