package com.hfentonfearn.objects;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;
import com.hfentonfearn.components.CollisionComponent;
import com.hfentonfearn.components.PhysicsComponent;
import com.hfentonfearn.components.TypeComponent;
import com.hfentonfearn.helpers.PhysicsBodyFactory;

import static com.hfentonfearn.components.TypeComponent.SCENERY;

public class WaterRocks extends Entity {

    public WaterRocks(World world, MapObject mapObject) {
        PhysicsComponent phys = new PhysicsComponent();
        PhysicsBodyFactory bodyFactory = new PhysicsBodyFactory(world);

        phys.body = bodyFactory.createBodyFromMapObject(mapObject, BodyDef.BodyType.StaticBody, PhysicsBodyFactory.DEFAULT,true);
        phys.body.setUserData(this);
        this.add(phys);

        this.add(new TypeComponent(SCENERY));
        this.add(new CollisionComponent());
    }
}
