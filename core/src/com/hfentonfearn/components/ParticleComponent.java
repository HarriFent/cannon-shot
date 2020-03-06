package com.hfentonfearn.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.graphics.g2d.ParticleEffectPool.PooledEffect;
import com.badlogic.gdx.utils.Pool.Poolable;

public class ParticleComponent implements Component, Poolable {
    public PooledEffect effect;
    public boolean follow;

    /** Can only be created by PooledEngine */
    private ParticleComponent () {
        // private constructor
    }
    public ParticleComponent init (PooledEffect effect, boolean follow) {
        this.effect = effect;
        this.follow = follow;
        return this;
    }

    @Override
    public void reset () {
        effect.free();
    }

}
