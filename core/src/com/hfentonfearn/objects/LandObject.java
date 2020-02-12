package com.hfentonfearn.objects;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.maps.objects.PolygonMapObject;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.physics.box2d.*;
import com.hfentonfearn.components.PhysicsComponent;
import com.hfentonfearn.components.TypeComponent;

import static com.hfentonfearn.components.TypeComponent.*;
import static com.hfentonfearn.helpers.Constants.MPP;

public class LandObject extends Entity {

    public LandObject(World world, PolygonMapObject polygon) {
        PhysicsComponent phys = new PhysicsComponent();

        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.position.set(polygon.getProperties().get("x", Float.class) * MPP, polygon.getProperties().get("y", Float.class) * MPP);
        phys.body = world.createBody(bodyDef);

        Polygon p = polygon.getPolygon();
        p.setPosition(0,0);
        p.setScale(MPP, MPP);
        float[] verts = p.getTransformedVertices();
        ChainShape shape = new ChainShape();
        shape.createLoop(verts);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 1f;

        phys.body.createFixture(fixtureDef);
        this.add(phys);

        TypeComponent type = new TypeComponent();
        type.type = LAND;
        this.add(new TypeComponent());
    }

}
