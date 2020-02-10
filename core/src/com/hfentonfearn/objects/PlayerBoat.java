package com.hfentonfearn.objects;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.hfentonfearn.components.*;
import com.hfentonfearn.helpers.AssetLoader;
import com.hfentonfearn.helpers.Constants;
import com.hfentonfearn.helpers.MappersHandler;

import static com.hfentonfearn.helpers.Constants.*;

public class PlayerBoat extends Entity {

    public PlayerBoat(World world, int xPos, int yPos) {

        this.add(new TextureComponent(AssetLoader.shipWhite));

        //Sets the Position, Size and Origin of the boat
        TextureComponent tex = MappersHandler.texture.get(this);
        float width = tex.region.getRegionWidth();
        float height = tex.region.getRegionHeight();
        TransformComponent tranComp = new TransformComponent();
        tranComp.position = new Vector2(xPos,yPos);
        tranComp.origin = new Vector2(width/2,height/2);
        tranComp.scale = new Vector2(1,1);
        this.add(tranComp);

        this.add(new PlayerComponent());
        this.add(new PhysicsComponent());
        this.add(new VelocityComponent());

        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(xPos* MPP,yPos*MPP);
        Body body = world.createBody(bodyDef);

        PolygonShape shape = new PolygonShape();
        float[] verts = {
                -32,4,
                0,56,
                32,4,
                0,-56,
                -32,4
        };
        for (int i = 0; i < verts.length; i++) {
            verts[i] *= MPP;
        }
        shape.set(verts);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 1f;

        body.createFixture(fixtureDef);

        MappersHandler.physics.get(this).body = body;

        shape.dispose();
    }
}
