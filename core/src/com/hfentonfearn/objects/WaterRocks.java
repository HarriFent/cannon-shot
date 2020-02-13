package com.hfentonfearn.objects;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.maps.objects.EllipseMapObject;
import com.badlogic.gdx.math.Ellipse;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.hfentonfearn.components.PhysicsComponent;
import com.hfentonfearn.components.TypeComponent;

import static com.hfentonfearn.components.TypeComponent.LAND;
import static com.hfentonfearn.components.TypeComponent.SCENERY;
import static com.hfentonfearn.helpers.Constants.MPP;

public class WaterRocks extends Entity {

    public WaterRocks(World world, EllipseMapObject circle) {
        PhysicsComponent phys = new PhysicsComponent();

        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.position.set(circle.getProperties().get("x", Float.class) * MPP, circle.getProperties().get("y", Float.class) * MPP);
        phys.body = world.createBody(bodyDef);

        Ellipse c = circle.getEllipse();
        CircleShape shape = new CircleShape();
        shape.setPosition(new Vector2(c.x * MPP,c.y * MPP));
        shape.setRadius(c.height*MPP);

        System.out.println(shape.getPosition().toString());
        System.out.println(shape.getRadius());

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 1f;

        phys.body.createFixture(fixtureDef);
        this.add(phys);

        TypeComponent type = new TypeComponent();
        type.type = SCENERY;
        this.add(new TypeComponent());
    }
}
