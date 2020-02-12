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


        //Sets the Position, Size and Origin of the boat
        TextureComponent tex = new TextureComponent(AssetLoader.shipWhite);
        float width = tex.region.getRegionWidth();
        float height = tex.region.getRegionHeight();
        this.add(tex);

        TransformComponent tranComp = new TransformComponent();
        tranComp.position = new Vector2(xPos,yPos);
        tranComp.origin = new Vector2(width/2,height/2);
        tranComp.scale = new Vector2(1,1);
        this.add(tranComp);

        this.add(new PlayerComponent());
        this.add(new VelocityComponent());
        this.add(new CollisionComponent());
        this.add(new TypeComponent());

        PhysicsComponent phys = new PhysicsComponent();

        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(xPos* MPP,yPos*MPP);
        phys.body = world.createBody(bodyDef);

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

        phys.body.createFixture(fixtureDef);
        this.add(phys);

        shape.dispose();
    }
}
