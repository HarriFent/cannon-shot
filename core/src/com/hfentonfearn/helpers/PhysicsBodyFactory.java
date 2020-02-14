package com.hfentonfearn.helpers;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.EllipseMapObject;
import com.badlogic.gdx.maps.objects.PolygonMapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.math.Ellipse;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;

import static com.hfentonfearn.helpers.Constants.MPP;

public class PhysicsBodyFactory {

    public static final int DEFAULT = 0;
    public static final int WOOD = 1;

    private World world;

    public PhysicsBodyFactory(World world) {
        this.world = world;
    }

    public Body createBodyFromShape( float x, float y, Shape shape, BodyType type, int material, boolean fixedRotation) {
        Body body = world.createBody(createBodyDef(x * MPP,y * MPP,type,fixedRotation));
        body.createFixture(createFixtureDef(shape,material));
        return body;
    }

    public Body createBodyFromMapObject(MapObject object, BodyType type, int material, boolean fixedRotation) {

        Body body = world.createBody(createBodyDef(object.getProperties().get("x", Float.class) * MPP, object.getProperties().get("y", Float.class) * MPP, type, fixedRotation));

        Shape shape = null;
        if (object instanceof EllipseMapObject) {
            //Ellipse Map Object
            Ellipse c = ((EllipseMapObject)object).getEllipse();
            shape = new CircleShape();
            shape.setRadius(c.height/2*MPP);
            float width = object.getProperties().get("width", Float.class);
            float height = object.getProperties().get("height", Float.class);
            ((CircleShape) shape).setPosition(new Vector2(width/2 * MPP,height/2 * MPP));
        } else if (object instanceof PolygonMapObject) {
            //Polygon Map Object
            Polygon p = ((PolygonMapObject)object).getPolygon();
            p.setPosition(0,0);
            p.setScale(MPP, MPP);
            float[] verts = p.getTransformedVertices();
            shape = new ChainShape();
            ((ChainShape)shape).createLoop(verts);
        } else if (object instanceof RectangleMapObject) {
            //RectangleMapObejct
        }

        body.createFixture(createFixtureDef(shape,material));
        return body;
    }

    private BodyDef createBodyDef(float x, float y, BodyType type, boolean fixedRotation) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = type;
        bodyDef.fixedRotation = fixedRotation;
        bodyDef.position.set(x,y);
        return  bodyDef;
    }

    public static FixtureDef createFixtureDef(Shape shape, int material) {
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;

        switch (material) {
            default:
            case DEFAULT:
                fixtureDef.density = 1f;
                fixtureDef.friction = 0.6f;
                fixtureDef.restitution = 0.1f;
                break;
            case WOOD:
                fixtureDef.density = 0.5f;
                fixtureDef.friction = 0.7f;
                fixtureDef.restitution = 0.3f;
                break;
        }
        return fixtureDef;
    }
}
