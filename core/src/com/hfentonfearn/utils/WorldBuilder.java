package com.hfentonfearn.utils;

import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.PolygonMapObject;
import com.badlogic.gdx.math.Vector2;
import com.hfentonfearn.ecs.EntityFactory;

public class WorldBuilder {

    private float width;
    private float height;

    public WorldBuilder(float width, float height) {
        this.width = width;
        this.height = height;
    }

    public void createWorld() {
        EntityFactory.createPlayer(new Vector2(500,500));

        MapLayer layer = AssetLoader.map.map.getLayers().get("collision");
        for (MapObject object : layer.getObjects()) {
            switch (object.getProperties().get("type", String.class)) {
                case "ground":
                    //Create a ground object
                    EntityFactory.createLandMass(((PolygonMapObject)object).getPolygon());
                    break;
                case "rock":
                    //Create ocean rocks
                    EntityFactory.createRocks(((PolygonMapObject)object).getPolygon());
                    break;
                default:
                    //Error
                    System.out.println("Object Type not found for object: " + object.getName());
            }
        }
    }
}
