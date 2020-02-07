package com.hfentonfearn.entitysystems;

import com.badlogic.ashley.core.*;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.hfentonfearn.components.*;

public class RenderingSystem extends EntitySystem {

    private Family family;
    private ImmutableArray<Entity> entities;

    static final float WINDOW_WIDTH = Gdx.graphics.getWidth();
    static final float WINDOW_HEIGHT = Gdx.graphics.getHeight();

    private SpriteBatch batch;
    private Array<Entity> renderQueue;
    private OrthographicCamera cam;
    private ImmutableArray<Entity> players;

    private ComponentMapper<TextureComponent> textureM;
    private ComponentMapper<TransformComponent> positionM;

    public RenderingSystem(SpriteBatch batch) {
        this.family = Family.all(TransformComponent.class, TextureComponent.class).get();

        textureM = ComponentMapper.getFor(TextureComponent.class);
        positionM = ComponentMapper.getFor(TransformComponent.class);

        renderQueue = new Array<Entity>();

        this.batch = batch;

        cam = new OrthographicCamera(WINDOW_WIDTH, WINDOW_HEIGHT);
        cam.position.set(WINDOW_WIDTH / 2f, WINDOW_HEIGHT / 2f, 0);
    }

    @Override
    public void addedToEngine (Engine engine) {
        entities = engine.getEntitiesFor(family);
        players = engine.getEntitiesFor(Family.all(PlayerComponent.class).get());
    }

    @Override
    public void removedFromEngine (Engine engine) {
        entities = null;
    }

    @Override
    public void update(float deltaTime) {
        for (int i = 0; i < entities.size(); ++i) {
            processEntity(entities.get(i), deltaTime);
        }

        Entity player;
        if (players.get(0) != null) {
            player = players.get(0);
            ComponentMapper<TransformComponent> trans = ComponentMapper.getFor(TransformComponent.class);
            TransformComponent tm = trans.get(player);
            cam.position.set(tm.getX(), tm.getY(),0);
        }

        cam.update();
        batch.setProjectionMatrix(cam.combined);
        batch.enableBlending();
        batch.begin();

        for (Entity entity : renderQueue) {
            TextureComponent tex = textureM.get(entity);
            TransformComponent pos = positionM.get(entity);

            if (tex.region == null) {
                continue;
            }

            float width = tex.region.getRegionWidth();
            float height = tex.region.getRegionHeight();

            float originX = width/2f;
            float originY = height/2f;

            batch.draw(tex.region,pos.getX()-originX, pos.getY() - originY, originX, originY, width, height, pos.getScaleX(), pos.getScaleY(), pos.getAngle());
        }

        batch.end();
        renderQueue.clear();
    }

    public void processEntity(Entity entity, float deltaTime) {
        renderQueue.add(entity);
    }

}
