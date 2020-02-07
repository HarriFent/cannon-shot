package com.hfentonfearn.objects;

import com.badlogic.ashley.core.Entity;
import com.hfentonfearn.components.*;
import com.hfentonfearn.helpers.AssetLoader;

public class PlayerBoat extends Entity {

    public PlayerBoat() {
        this.add(new TransformComponent(1000,1000));
        this.add(new VelocityComponent(10,1));
        this.add(new AccelerationComponent());
        this.add(new PlayerComponent());
        this.add(new TextureComponent(AssetLoader.shipWhite));
    }
}
