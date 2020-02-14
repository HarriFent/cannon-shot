package com.hfentonfearn.objects;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.maps.objects.EllipseMapObject;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;
import com.hfentonfearn.components.PhysicsComponent;
import com.hfentonfearn.components.TypeComponent;
import com.hfentonfearn.helpers.PhysicsBodyFactory;

import static com.hfentonfearn.components.TypeComponent.SCENERY;

public class WaterRocks extends Entity {

    public WaterRocks(World world, EllipseMapObject circle) {
        PhysicsComponent phys = new PhysicsComponent();
        PhysicsBodyFactory bodyFactory = new PhysicsBodyFactory(world);

        phys.body = bodyFactory.createBodyFromMapObject(circle, BodyDef.BodyType.StaticBody, PhysicsBodyFactory.DEFAULT,true);
        phys.body.setUserData(this);
        this.add(phys);

        TypeComponent type = new TypeComponent();
        type.type = SCENERY;
        this.add(new TypeComponent());
    }
}
