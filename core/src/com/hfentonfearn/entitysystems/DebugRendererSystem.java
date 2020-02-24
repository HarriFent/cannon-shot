package com.hfentonfearn.entitysystems;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Array;
import com.hfentonfearn.components.SpriteComponent;
import com.hfentonfearn.ecs.Components;

import static com.hfentonfearn.utils.Constants.DEBUGMODE;
import static com.hfentonfearn.utils.Constants.WORLD_PIXEL_HEIGHT;

public class DebugRendererSystem extends EntitySystem {

    private PhysicsSystem physicsSystem;
    private CameraSystem cameraSystem;
    private OrthographicCamera camera;

    private ShapeRenderer debugRenderer;
    private SpriteBatch debugBatch;
    private BitmapFont font;
    private ImmutableArray<Entity> renderEntities;

    private Array<String> strings;

    public DebugRendererSystem() {
        debugRenderer = new ShapeRenderer();
        font = new BitmapFont();
        debugBatch = new SpriteBatch();
    }

    public void addedToEngine (Engine engine) {
        cameraSystem = engine.getSystem(CameraSystem.class);
        physicsSystem = engine.getSystem(PhysicsSystem.class);
        renderEntities = engine.getEntitiesFor(Family.all(SpriteComponent.class).get());
    }

    public void update(float deltaTime) {

        if (Gdx.input.isKeyJustPressed(Input.Keys.B))
            DEBUGMODE = !DEBUGMODE;

        if (DEBUGMODE) {
            //Render Physics World
            physicsSystem.drawDebug();
            camera = cameraSystem.getCamera();

            debugRenderer.setProjectionMatrix(camera.combined);
            debugRenderer.begin(ShapeRenderer.ShapeType.Line);
            //Render Render Queue
            for (Entity e : renderEntities) {
                debugRenderer.setColor(Color.RED);
                Gdx.gl.glLineWidth(3);

                SpriteComponent spriteComponent = Components.SPRITE.get(e);
                debugRenderer.circle(spriteComponent.getSprite().getX(), spriteComponent.getSprite().getY(), 5);
            }
            debugRenderer.end();

            //Debug Overlay HUD
            updateDebugStrings();

            debugBatch.begin();
            font.setColor(Color.RED);
            float y = WORLD_PIXEL_HEIGHT - 20;
            for (String str : strings) {
                font.draw(debugBatch, str, 10, y);
                y -= 20;
            }
            debugBatch.end();
        }
    }

    private void updateDebugStrings() {
        strings = new Array<>();
        strings.add("DEBUG MODE");
        strings.add("Cam X Pos: " + Math.round(camera.position.x));
        strings.add("Cam Y Pos: " + Math.round(camera.position.y));
    }
}
