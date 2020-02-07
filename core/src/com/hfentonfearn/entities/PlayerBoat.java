package com.hfentonfearn.entities;

import com.badlogic.ashley.core.Entity;
import com.hfentonfearn.components.*;

public class PlayerBoat extends Entity {

    public PlayerBoat() {
        this.add(new PositionComponent());
        this.add(new VelocityComponent());
    }
}
