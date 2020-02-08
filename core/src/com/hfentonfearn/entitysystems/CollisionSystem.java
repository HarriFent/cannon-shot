package com.hfentonfearn.entitysystems;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.PolygonMapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.FloatArray;
import com.hfentonfearn.components.CollisionComponent;
import com.hfentonfearn.components.TransformComponent;
import com.hfentonfearn.components.VelocityComponent;
import com.hfentonfearn.helpers.AssetLoader;
import com.hfentonfearn.helpers.MappersHandler;
import com.hfentonfearn.helpers.MathsHandler;

public class CollisionSystem extends EntitySystem {

    private ImmutableArray<Entity> entities;
    private MapObjects objects;

    public CollisionSystem() {
        objects = AssetLoader.map.getLayers().get("collision").getObjects();
    }

    public void addedToEngine(Engine engine) {
        entities = engine.getEntitiesFor(Family.all(CollisionComponent.class).get());
    }

    public void update(float deltaTime) {
        for (Entity e : entities) {
            CollisionComponent cComp =  MappersHandler.collision.get(e);
            TransformComponent tComp = MappersHandler.transform.get(e);
            VelocityComponent vComp = MappersHandler.velocity.get(e);

            cComp.collisionShape.setPosition(tComp.getX(), tComp.getY());
            cComp.collisionShape.setRotation(tComp.getAngle());
            cComp.collisionShape.setScale(tComp.getScaleX(),tComp.getScaleY());

            Polygon targetPoly = cComp.collisionShape;
            float[] target = MathsHandler.getEntityTarget(e);
            targetPoly.translate(target[0],target[1]);
            cComp.isColliding = false;
            for (MapObject object: objects) {

                if (object instanceof PolygonMapObject)
                    if (Intersector.intersectPolygons(targetPoly,((PolygonMapObject) object).getPolygon(),null))
                        cComp.isColliding = true;
                if (object instanceof RectangleMapObject) {
                    Rectangle r = ((RectangleMapObject) object).getRectangle();
                    Polygon rPoly = new Polygon(new float[] { 0, 0, r.width, 0, r.width, r.height, 0, r.height });
                    rPoly.setPosition(r.x, r.y);
                    if (Intersector.intersectPolygons(targetPoly, rPoly, null))
                        cComp.isColliding = true;
                }
            }
        }
    }
}
