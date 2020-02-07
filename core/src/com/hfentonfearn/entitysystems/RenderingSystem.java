package com.hfentonfearn.entitysystems;

import com.badlogic.ashley.core.*;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.hfentonfearn.components.*;

public class RenderingSystem extends IteratingSystem {

    static final float WINDOW_WIDTH = Gdx.graphics.getWidth();
    static final float WINDOW_HEIGHT = Gdx.graphics.getHeight();

    private SpriteBatch batch;
    private Array<Entity> renderQueue;
    private OrthographicCamera cam;

    private ComponentMapper<TextureComponent> textureM;
    private ComponentMapper<PositionComponent> positionM;

    public RenderingSystem(SpriteBatch batch) {
        super(Family.all(PositionComponent.class, TextureComponent.class).get());

        textureM = ComponentMapper.getFor(TextureComponent.class);
        positionM = ComponentMapper.getFor(PositionComponent.class);

        renderQueue = new Array<Entity>();

        this.batch = batch;

        cam = new OrthographicCamera(WINDOW_WIDTH, WINDOW_HEIGHT);
        cam.position.set(WINDOW_WIDTH / 2f, WINDOW_HEIGHT / 2f, 0);
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);

        cam.update();
        batch.setProjectionMatrix(cam.combined);
        batch.enableBlending();
        batch.begin();

        for (Entity entity : renderQueue) {
            TextureComponent tex = textureM.get(entity);
            PositionComponent pos = positionM.get(entity);

            if (tex.region == null) {
                continue;
            }

            float width = tex.region.getRegionWidth();
            float height = tex.region.getRegionHeight();

            float originX = width/2f;
            float originY = height/2f;

            batch.draw(tex.region,pos.x-originX, pos.y - originY, width, height);
        }

        batch.end();
        renderQueue.clear();
    }

    @Override
    public void processEntity(Entity entity, float deltaTime) {
        renderQueue.add(entity);
    }

}
