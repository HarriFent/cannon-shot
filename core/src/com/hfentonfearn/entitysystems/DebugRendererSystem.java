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
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.hfentonfearn.components.PhysicsComponent;
import com.hfentonfearn.components.PlayerComponent;
import com.hfentonfearn.components.SpriteComponent;
import com.hfentonfearn.utils.Components;

import static com.hfentonfearn.utils.Constants.DEBUGMODE;
import static com.hfentonfearn.utils.Constants.WINDOW_HEIGHT;

public class DebugRendererSystem extends EntitySystem {

    private PhysicsSystem physicsSystem;
    private CameraSystem cameraSystem;
    private OrthographicCamera camera;

    private ShapeRenderer debugRenderer;
    private SpriteBatch debugBatch;
    private BitmapFont font;
    private ImmutableArray<Entity> renderEntities;

    private static Array<String> strings;

    public DebugRendererSystem() {
        debugRenderer = new ShapeRenderer();
        font = new BitmapFont();
        debugBatch = new SpriteBatch();
        strings = new Array<>();
    }

    public void addedToEngine (Engine engine) {
        cameraSystem = engine.getSystem(CameraSystem.class);
        physicsSystem = engine.getSystem(PhysicsSystem.class);
        renderEntities = engine.getEntitiesFor(Family.all(SpriteComponent.class, PhysicsComponent.class).get());
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

                PhysicsComponent physics = Components.PHYSICS.get(e);
                debugRenderer.circle(physics.getPosition().x, physics.getPosition().y, 5);
            }

            ImmutableArray<Entity> players = getEngine().getEntitiesFor(Family.all(PlayerComponent.class).get());
            Array<Sprite> sprites = Components.SPRITE.get(players.get(0)).getSprites();
            float width = 0;
            float height = 0;
            for (Sprite sprite: sprites){
                if (sprite.getWidth() > width)
                    width = sprite.getWidth();
                if (sprite.getHeight() > height)
                    height = sprite.getHeight();
            }
            Vector2 pos = Components.PHYSICS.get(players.get(0)).getPosition();
            float angle = (float) Math.toDegrees(Components.PHYSICS.get(players.get(0)).getBody().getAngle());
            debugRenderer.ellipse(pos.x - width/2,pos.y - height/2,width,height,angle);

            debugRenderer.end();

            //Debug Overlay HUD
            Array<String> debugs = updateDebugStrings();

            debugBatch.begin();
            font.setColor(Color.RED);
            float y = WINDOW_HEIGHT - 4;
            for (String str : debugs) {
                font.draw(debugBatch, str, 10, y);
                y -= 20;
            }
            debugBatch.end();
        }
    }

    private Array<String> updateDebugStrings() {
        Array<String> output = new Array<>();
        output.add("DEBUG MODE");
        //output.add("F1 = ");
        output.add("F1 = Increase player speed stat");
        output.add("F2 = Decrease player speed stat");
        output.add("F5 = Spawns an Enemy Ship at the mouse position");
        for (String str : strings)
            output.add(str);
        strings = new Array<>();
        return output;
    }

    public static void addDebug(String string) {
        strings.add(string);
    }

    public static void addDebug(String string, Object obj) {
        strings.add(string + obj.toString());
    }
}
