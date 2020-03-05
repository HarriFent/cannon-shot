package com.hfentonfearn.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Pool.Poolable;

public class AnimationComponent implements Component, Poolable {
    public Animation<TextureRegion> animation;
    public float stateTime;

    private AnimationComponent () {}

    public AnimationComponent init (Animation<TextureRegion> animation) {
        this.animation = animation;
        stateTime = 0f;
        return this;
    }

    @Override
    public void reset() {
        animation = null;
        stateTime = 0f;
    }
}
