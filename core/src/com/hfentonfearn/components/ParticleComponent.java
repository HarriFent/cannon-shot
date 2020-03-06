package com.hfentonfearn.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.graphics.g2d.ParticleEffectPool.PooledEffect;
import com.badlogic.gdx.graphics.g2d.ParticleEmitter;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pool.Poolable;

public class ParticleComponent implements Component, Poolable {
    public PooledEffect effect;
    public boolean follow;

    /** Can only be created by PooledEngine */
    private ParticleComponent () {
        // private constructor
    }
    public ParticleComponent init (PooledEffect effect, boolean follow, float angle) {
        this.effect = effect;
        this.follow = follow;
        rotateBy(angle);
        return this;
    }

    @Override
    public void reset () {
        follow = false;
        effect = null;
    }

    public void rotateBy(float amountInDegrees) {
        Array<ParticleEmitter> emitters = effect.getEmitters();
        for (int i = 0; i < emitters.size; i++) {
            ParticleEmitter.ScaledNumericValue val = emitters.get(i).getAngle();
            float amplitude = (val.getHighMax() - val.getHighMin()) / 2f;
            float h1 = amountInDegrees + amplitude;
            float h2 = amountInDegrees - amplitude;
            val.setHigh(h1, h2);
            val.setLow(amountInDegrees);
        }
    }
}
