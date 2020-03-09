package com.hfentonfearn.objects;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;

public class Cloud extends Sprite {

    private Vector2 movement;

    public Cloud(TextureAtlas.AtlasRegion region,Vector2 movement) {
        super(region);
        this.movement = movement;
    }

    public Vector2 getMovement() {
        return movement;
    }
}
