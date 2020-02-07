package com.hfentonfearn.objects;

import com.badlogic.ashley.core.Entity;
import com.hfentonfearn.components.*;
import com.hfentonfearn.helpers.AssetLoader;

public class PlayerBoat extends Entity {

    public PlayerBoat() {
        this.add(new TransformComponent(300,300));
        this.add(new VelocityComponent(10,1));
        this.add(new AccelerationComponent());
        this.add(new PlayerComponent());
        this.add(new TextureComponent(AssetLoader.shipWhite));
    }
}
