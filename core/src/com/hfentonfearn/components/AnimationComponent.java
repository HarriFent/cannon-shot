package com.hfentonfearn.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Pool.Poolable;

public class AnimationComponent implements Component, Poolable {
    public Animation<TextureRegion> animation;
    public float stateTime;
    public float scale;

    private AnimationComponent () {}

    public AnimationComponent init (Animation<TextureRegion> animation, float scale) {
        this.animation = animation;
        stateTime = 0f;
        this.scale = scale;
        return this;
    }

    @Override
    public void reset() {
        animation = null;
        stateTime = 0f;
    }
}
