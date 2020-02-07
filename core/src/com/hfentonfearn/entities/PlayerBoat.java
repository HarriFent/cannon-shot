package com.hfentonfearn.entities;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.hfentonfearn.components.*;
import com.hfentonfearn.helpers.AssetLoader;

public class PlayerBoat extends Entity {

    private ComponentMapper<PositionComponent> pm = ComponentMapper.getFor(PositionComponent.class);

    public PlayerBoat() {
        this.add(new PositionComponent());
        this.add(new VelocityComponent());
        this.add(new AccelerationComponent());
        this.add(new PlayerComponent());
        this.add(new TextureComponent(AssetLoader.shipWhite));

        PositionComponent pos = pm.get(this);
        pos.setPosition(300,300);
    }
}
