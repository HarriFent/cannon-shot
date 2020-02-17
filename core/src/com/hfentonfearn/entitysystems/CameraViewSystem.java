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
import com.badlogic.gdx.math.MathUtils;
import com.hfentonfearn.components.PlayerComponent;
import com.hfentonfearn.components.TextureComponent;
import com.hfentonfearn.components.TransformComponent;
import com.hfentonfearn.helpers.AssetLoader;
import com.hfentonfearn.helpers.MappersHandler;

import static com.hfentonfearn.entitysystems.CameraViewSystem.ZoomLevel.*;

public class CameraViewSystem extends EntitySystem {

    public enum ZoomLevel {
        CLOSE,
        FAR,
        MAP
    }

    private OrthographicCamera cam;
    private ZoomLevel zoom;
    private float currentZoom;
    private MapProperties mapProps;
    private ImmutableArray<Entity> players;

    public CameraViewSystem(OrthographicCamera cam) {
        this.cam = cam;
        zoom = FAR;
        currentZoom = cam.zoom;
        mapProps = AssetLoader.map.getProperties();
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
            switch (zoom) {
                case FAR:
                    zoom = MAP;
                    break;
                case CLOSE:
                    zoom = FAR;
            }
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.E)) {
            switch (zoom) {
                case MAP:
                    zoom = FAR;
                    break;
                case FAR:
                    zoom = CLOSE;
            }
        }

        switch (zoom) {
            case CLOSE:
                if (currentZoom > 0.1) {
                    cam.zoom -= deltaTime;
                }
                break;
            case FAR:
                if (currentZoom > 1.01) {
                    cam.zoom -= deltaTime;
                } else if (currentZoom < 0.99) {
                    cam.zoom += deltaTime;
                } else {
                    cam.zoom = 1;
                }
                break;
            case MAP:
                if (currentZoom < 1.5) {
                    cam.zoom += deltaTime;
                }
        }
        currentZoom = cam.zoom;

        // Set the camera position to the player
        Entity player;
        if (players.get(0) != null) {
            player = players.get(0);
            TransformComponent tm = MappersHandler.transform.get(player);
            cam.position.set(tm.position.x,tm.position.y,0);

            // Clamp the camera to the map size
            float mapWidth = mapProps.get("width", Integer.class) * mapProps.get("tilewidth", Integer.class);
            float mapHeight = mapProps.get("height", Integer.class) * mapProps.get("tileheight", Integer.class);
            cam.position.x = MathUtils.clamp(cam.position.x, cam.viewportWidth/2*currentZoom, mapWidth - (cam.viewportWidth/2)*currentZoom);
            cam.position.y = MathUtils.clamp(cam.position.y, cam.viewportHeight/2*currentZoom, mapHeight - (cam.viewportHeight/2)*currentZoom);
        }

        cam.update();
    }
}
