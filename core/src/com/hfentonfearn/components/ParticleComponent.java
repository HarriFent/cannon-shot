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
            ParticleEmitter emitter = emitters.get(i);
            ParticleEmitter.ScaledNumericValue val = emitter.getAngle();
            float amplitude = (val.getHighMax() - val.getHighMin()) / 2f;
            float h1 = amountInDegrees + amplitude;
            float h2 = amountInDegrees - amplitude;
            val.setHigh(h1, h2);
            val.setLow(amountInDegrees);
        }
    }

    public void setVelocity(float velocity) {
        Array<ParticleEmitter> emitters = effect.getEmitters();
        for (int i = 0; i < emitters.size; i++) {
            emitters.get(i).getVelocity().setHighMax(velocity);
        }
    }

    public void setAlpha(float alpha) {
        Array<ParticleEmitter> emitters = effect.getEmitters();
        for (int i = 0; i < emitters.size; i++) {
            emitters.get(i).getTransparency().setHighMin(alpha);
            emitters.get(i).getTransparency().setHighMax(alpha);
        }
    }
}
