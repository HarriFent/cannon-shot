package com.hfentonfearn.entitysystems;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.ParticleEffectPool;
import com.badlogic.gdx.graphics.g2d.ParticleEffectPool.PooledEffect;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.hfentonfearn.components.ParticleComponent;
import com.hfentonfearn.utils.Components;

public class ParticleSystem extends IteratingSystem {

    private static final String ROOT_DIR = "particles/";

    public enum ParticleType {
        WATER("water.p"), SMOKE("smoke.p"), CANNON_FIRE("cannon_fire.p"), CANNON_TRAIL("cannon_trail.p");

        public String file;

        private ParticleType (String file) {
            this.file = file;
        }
    }

    private Array<ParticleEffect> effectTemplates = new Array<ParticleEffect>();
    private Array<ParticleEffectPool> effectPools = new Array<ParticleEffectPool>();

    private OrthographicCamera camera;
    private SpriteBatch batch;

    private PooledEngine engine;

    @SuppressWarnings("unchecked")
    public ParticleSystem () {
        super(Family.all(ParticleComponent.class).get());
        loadTemplates();
    }

    public void loadTemplates () {
        for (int i = 0; i < ParticleType.values().length; i++) {
            ParticleEffect template = new ParticleEffect();
            template.load(Gdx.files.internal(ROOT_DIR + ParticleType.values()[i].file), Gdx.files.internal(ROOT_DIR));
            effectTemplates.add(template);

            ParticleEffectPool pool = new ParticleEffectPool(template, 4, 20);
            effectPools.add(pool);
        }
    }

    public PooledEffect createEffect (Vector2 position, ParticleType type) {
        PooledEffect effect = effectPools.get(type.ordinal()).obtain();
        effect.setPosition(position.x, position.y);
        return effect;
    }

    @Override
    public void addedToEngine (Engine engine) {
        super.addedToEngine(engine);
        this.engine = (PooledEngine)engine;
        batch = new SpriteBatch();
        camera = engine.getSystem(CameraSystem.class).getCamera();
    }

    @Override
    public void removedFromEngine(Engine engine) {
        super.removedFromEngine(engine);
        effectTemplates.forEach((e) -> {e.dispose();});
    }

    @Override
    protected void processEntity (Entity entity, float deltaTime) {
        ParticleComponent particle = Components.PARTICLE.get(entity);
        if (particle.follow)
            if (Components.PHYSICS.has(entity)) {
                Vector2 pos = Components.PHYSICS.get(entity).getPosition();
                particle.effect.setPosition(pos.x, pos.y);
            }
        particle.effect.update(deltaTime);
        particle.effect.draw(batch);

        if (particle.effect.isComplete()) {
            particle.effect.free();
            engine.removeEntity(entity);
        }
    }

    @Override
    public void update (float deltaTime) {
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        super.update(deltaTime);
        batch.end();
    }

}