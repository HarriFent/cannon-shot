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
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.hfentonfearn.components.CollisionComponent;
import com.hfentonfearn.components.PlayerComponent;
import com.hfentonfearn.components.TextureComponent;
import com.hfentonfearn.components.TransformComponent;
import com.hfentonfearn.helpers.MappersHandler;

import static com.hfentonfearn.helpers.Constants.DEBUGMODE;
import static com.hfentonfearn.helpers.Constants.PPM;

public class DebugRendererSystem extends EntitySystem {

    private OrthographicCamera cam;
    private ShapeRenderer debugRenderer;
    private Box2DDebugRenderer debug2dRenderer;
    private SpriteBatch debugBatch;
    private BitmapFont font;
    private World world;
    private ImmutableArray<Entity> renderEntities;
    private ImmutableArray<Entity> collisionEntities;
    private ImmutableArray<Entity> players;

    public DebugRendererSystem(World world, OrthographicCamera camera) {
        cam = camera;
        debugRenderer = new ShapeRenderer();
        font = new BitmapFont();
        debugBatch = new SpriteBatch();
        this.world = world;
        debug2dRenderer = new Box2DDebugRenderer();
    }

    public void addedToEngine (Engine engine) {
        renderEntities = engine.getEntitiesFor(Family.all(TransformComponent.class, TextureComponent.class).get());
        collisionEntities = engine.getEntitiesFor(Family.all(CollisionComponent.class).get());
        players = engine.getEntitiesFor(Family.all(PlayerComponent.class).get());
    }

    public void update(float deltaTime) {

        if (Gdx.input.isKeyJustPressed(Input.Keys.B))
            DEBUGMODE = !DEBUGMODE;

        if (DEBUGMODE) {
            //Render Physics World
            debug2dRenderer.render(world, new Matrix4(cam.combined).scl(PPM));

            debugRenderer.setProjectionMatrix(cam.combined);
            debugRenderer.begin(ShapeRenderer.ShapeType.Line);

            //Render Render Queue
            for (Entity e : renderEntities) {
                debugRenderer.setColor(Color.RED);
                Gdx.gl.glLineWidth(3);

                TransformComponent transformComponent = MappersHandler.transform.get(e);
                debugRenderer.circle(transformComponent.position.x, transformComponent.position.y, 5);
            }
            debugRenderer.end();

            //Debug Overlay HUD
            debugBatch.begin();
            font.setColor(Color.RED);
            font.draw(debugBatch, "DEBUG MODE", 10, 20);
            Entity player = players.get(0);
            /*font.draw(debugBatch, "Player Body: x = " + e.getComponent(PhysicsComponent.class).body.getPosition().x + ", y = " + e.getComponent(PhysicsComponent.class).body.getPosition().y, 10, 40);
            font.draw(debugBatch, "Player Transform: x = " + e.getComponent(TransformComponent.class).position.x + ", y = " + e.getComponent(TransformComponent.class).position.y, 10, 60);
            font.draw(debugBatch, "Body Angle: " + e.getComponent(PhysicsComponent.class).body.getAngle() + ", Player Angle: " + e.getComponent(TransformComponent.class).rotation, 10, 80);
            font.draw(debugBatch, "Cam Pos: " + cam.position.toString(), 10, 100);*/

            if (player.getComponent(CollisionComponent.class).collisionEntities != null)
                font.draw(debugBatch, "Player Entities: " + player.getComponent(CollisionComponent.class).collisionEntities.toString(), 10, 40);
            debugBatch.end();
        }
    }
}
