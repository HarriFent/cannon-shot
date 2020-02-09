package com.hfentonfearn.objects;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.physics.box2d.*;
import com.hfentonfearn.components.*;
import com.hfentonfearn.helpers.AssetLoader;
import com.hfentonfearn.helpers.MappersHandler;

public class PlayerBoat extends Entity {

    public PlayerBoat(World world, int xPos, int yPos) {
        this.add(new TextureComponent(AssetLoader.shipWhite));

        //Sets the Position, Size and Origin of the boat
        TextureComponent tex = MappersHandler.texture.get(this);
        float width = tex.region.getRegionWidth();
        float height = tex.region.getRegionHeight();
        TransformComponent tranComp = new TransformComponent(xPos,yPos,width,height);
        tranComp.setOrigin(width/2,height/2);
        this.add(tranComp);

        this.add(new VelocityComponent(1000000,100000));
        this.add(new AccelerationComponent());
        this.add(new PlayerComponent());
        this.add(new PhysicsComponent());

        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(xPos,yPos);
        Body body = world.createBody(bodyDef);

        PolygonShape shape = new PolygonShape();
        float[] verts = {
                -32,4,
                0,56,
                32,4,
                0,-56,
                -32,4
        };
        shape.set(verts);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 1f;

        body.createFixture(fixtureDef);

        MappersHandler.physics.get(this).body = body;

        shape.dispose();
    }
}
