package com.hfentonfearn.objects;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;

public class Cloud extends Sprite {

    private Vector2 movement;

    public Cloud(TextureAtlas.AtlasRegion region) {
        super(region);
    }

    public Vector2 getMovement() {
        return movement;
    }

    public void reset(Vector2 movement, Vector2 position) {
        this.movement = movement;
        this.setCenter(position.x,position.y);
    }
}
