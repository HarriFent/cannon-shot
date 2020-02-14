package com.hfentonfearn.objects;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.maps.objects.PolygonMapObject;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;
import com.hfentonfearn.components.PhysicsComponent;
import com.hfentonfearn.components.TypeComponent;
import com.hfentonfearn.helpers.PhysicsBodyFactory;

import static com.hfentonfearn.components.TypeComponent.LAND;

public class Ground extends Entity {

    public Ground(World world, PolygonMapObject polygon) {
        PhysicsComponent phys = new PhysicsComponent();
        PhysicsBodyFactory bodyFactory = new PhysicsBodyFactory(world);

        phys.body = bodyFactory.createBodyFromMapObject(polygon, BodyDef.BodyType.StaticBody, PhysicsBodyFactory.DEFAULT,true);
        this.add(phys);

        TypeComponent type = new TypeComponent();
        type.type = LAND;
        this.add(new TypeComponent());
    }

}
