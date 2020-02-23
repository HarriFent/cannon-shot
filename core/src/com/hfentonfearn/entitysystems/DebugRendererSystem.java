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
import com.badlogic.gdx.utils.Array;
import com.hfentonfearn.components.PlayerComponent;
import com.hfentonfearn.components.TextureComponent;
import com.hfentonfearn.components.TransformComponent;
import com.hfentonfearn.gameworld.ZoomLevel;

import static com.hfentonfearn.utils.Constants.*;

public class DebugRendererSystem extends EntitySystem {

    private OrthographicCamera cam;
    private ShapeRenderer debugRenderer;
    private Box2DDebugRenderer debug2dRenderer;
    private SpriteBatch debugBatch;
    private BitmapFont font;
    private World world;
    private ZoomLevel zoom;
    private ImmutableArray<Entity> renderEntities;
    private ImmutableArray<Entity> players;

    private Array<String> strings;

    public DebugRendererSystem(World world, OrthographicCamera camera, ZoomLevel zoomLevel) {
        cam = camera;
        debugRenderer = new ShapeRenderer();
        font = new BitmapFont();
        debugBatch = new SpriteBatch();
        this.world = world;
        debug2dRenderer = new Box2DDebugRenderer();
        zoom = zoomLevel;
    }

    public void addedToEngine (Engine engine) {
        renderEntities = engine.getEntitiesFor(Family.all(TransformComponent.class, TextureComponent.class).get());
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

                /*TransformComponent transformComponent = Components.transform.get(e);
                debugRenderer.circle(transformComponent.position.x, transformComponent.position.y, 5);*/
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
        strings.add("Cam X Pos: " + Math.round(cam.position.x));
        strings.add("Cam Y Pos: " + Math.round(cam.position.y));
        strings.add("Zooming in: " + zoom.isZoomingIn() + " Zooming out: " + zoom.isZoomingOut());
        strings.add("Zoom Level: " + zoom.getZoomValue() + ", " + zoom.toString() + ", CurrentZoom: " + zoom.getCurrentZoom());
        //strings.add("Player Entities: " + players.get(0).getComponent(CollisionComponent.class).collisionEntities.toString());
    }
}
