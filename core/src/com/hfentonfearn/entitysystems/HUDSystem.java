package com.hfentonfearn.entitysystems;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.hfentonfearn.components.HealthComponent;
import com.hfentonfearn.components.PlayerComponent;
import com.hfentonfearn.utils.Components;

import static com.hfentonfearn.utils.Constants.WINDOW_HEIGHT;
import static com.hfentonfearn.utils.Constants.WINDOW_WIDTH;

public class HUDSystem extends IteratingSystem {

    private SpriteBatch batch;
    private ShapeRenderer shapeRenderer;

    public HUDSystem() {
        super(Family.all(PlayerComponent.class).get());
        batch = new SpriteBatch();
        shapeRenderer = new ShapeRenderer();
    }

    @Override
    public void addedToEngine (Engine engine) {
        super.addedToEngine(engine);
    }

    @Override
    public void update (float deltaTime) {
        batch.begin();
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        super.update(deltaTime);
        batch.end();
        shapeRenderer.end();
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        //Default health = 80
        HealthComponent health = Components.HEALTH.get(entity);
        float barWidth = 7;
        float width = health.max * barWidth + 4;
        shapeRenderer.setColor(new Color(0.5f,0.5f,0.5f,1));
        shapeRenderer.rect(WINDOW_WIDTH - width - 10,WINDOW_HEIGHT - 30, width, 20 );
        shapeRenderer.setColor(Color.RED);
        float x = 1;
        while (x <= health.value) {
            shapeRenderer.rect(WINDOW_WIDTH - (x * barWidth) - 10,WINDOW_HEIGHT - 28, barWidth - 2, 16 );
            x++;
        }
    }
}
