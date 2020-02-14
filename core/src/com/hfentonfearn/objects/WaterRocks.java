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
import com.hfentonfearn.helpers.PhysicsBodyFactory;

import static com.hfentonfearn.components.TypeComponent.LAND;
import static com.hfentonfearn.components.TypeComponent.SCENERY;
import static com.hfentonfearn.helpers.Constants.MPP;

public class WaterRocks extends Entity {

    public WaterRocks(World world, EllipseMapObject circle) {
        PhysicsComponent phys = new PhysicsComponent();
        PhysicsBodyFactory bodyFactory = new PhysicsBodyFactory(world);

        phys.body = bodyFactory.createBodyFromMapObject(circle, BodyDef.BodyType.StaticBody, PhysicsBodyFactory.DEFAULT,true);
        this.add(phys);

        TypeComponent type = new TypeComponent();
        type.type = SCENERY;
        this.add(new TypeComponent());
    }
}
