package com.hfentonfearn.objects;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;
import com.hfentonfearn.components.*;
import com.hfentonfearn.helpers.AssetLoader;
import com.hfentonfearn.helpers.PhysicsBodyFactory;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import static com.hfentonfearn.helpers.Constants.MPP;

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

        JsonReader json = new JsonReader();
        JsonValue base = json.parse(Gdx.files.internal("objects/ships/shipCollision.json"));
        JsonValue vertArray = base.get("collisionPoly");
        float[] verts = new float[vertArray.size];
        for (int i = 0; i < vertArray.size; i++) {
            verts[i] = vertArray.getFloat(i);
        }

        Polygon p = new Polygon(verts);
        p.translate(-0.32f,-0.56f);
        p.setScale(MPP,MPP);
        PolygonShape shape = new PolygonShape();
        shape.set(p.getTransformedVertices());

        phys.body.createFixture(PhysicsBodyFactory.createFixture(shape,PhysicsBodyFactory.WOOD));
        phys.body.setUserData(this);
        this.add(phys);

        shape.dispose();
    }
}
