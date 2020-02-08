package com.hfentonfearn.objects;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.math.Polygon;
import com.hfentonfearn.components.*;
import com.hfentonfearn.helpers.AssetLoader;
import com.hfentonfearn.helpers.MappersHandler;

public class PlayerBoat extends Entity {

    public PlayerBoat(int xPos, int yPos) {
        this.add(new TextureComponent(AssetLoader.shipWhite));

        //Sets the Position, Size and Origin of the boat
        TextureComponent tex = MappersHandler.texture.get(this);
        float width = tex.region.getRegionWidth();
        float height = tex.region.getRegionHeight();
        TransformComponent tranComp = new TransformComponent(xPos,yPos,width,height);
        tranComp.setOrigin(width/2,height/2);
        this.add(tranComp);

        this.add(new VelocityComponent(10,1));
        this.add(new AccelerationComponent());
        this.add(new PlayerComponent());
        float[] verts = {
                -32,4,
                0,56,
                32,4,
                0,-56,
                -32,4
        };
        this.add(new CollisionComponent(new Polygon(verts)));
    }
}
