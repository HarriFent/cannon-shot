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
import com.badlogic.gdx.math.*;
import com.badlogic.gdx.utils.Array;
import com.hfentonfearn.components.PhysicsComponent;
import com.hfentonfearn.components.SpriteComponent;
import com.hfentonfearn.utils.Components;

import static com.hfentonfearn.GameManager.GameConfig.BUILD;
import static com.hfentonfearn.GameManager.GameConfig.build;
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
    private static Array<DebugShape> shapes;

    public DebugRendererSystem() {
        debugRenderer = new ShapeRenderer();
        font = new BitmapFont();
        debugBatch = new SpriteBatch();
        strings = new Array<>();
        shapes = new Array<>();
    }

    public void addedToEngine (Engine engine) {
        cameraSystem = engine.getSystem(CameraSystem.class);
        physicsSystem = engine.getSystem(PhysicsSystem.class);
        renderEntities = engine.getEntitiesFor(Family.all(SpriteComponent.class, PhysicsComponent.class).get());
    }

    public void update(float deltaTime) {

        if (Gdx.input.isKeyJustPressed(Input.Keys.B) && build == BUILD.DEV)
            DEBUGMODE = !DEBUGMODE;

        if (DEBUGMODE) {
            //Render Physics World
            physicsSystem.drawDebug();
            camera = cameraSystem.getCamera();

            //Render Render Queue
            for (Entity e : renderEntities) {
                Vector2 pos = Components.PHYSICS.get(e).getPosition();
                shapes.add(new DebugShape(new Circle(pos.x, pos.y, 5), Color.RED, 0f));
            }

            debugRenderer.setProjectionMatrix(camera.combined);
            debugRenderer.begin(ShapeRenderer.ShapeType.Line);
            Gdx.gl.glLineWidth(3);
            shapes.forEach((d) -> {
                debugRenderer.setColor(d.color);
                if (d.shape instanceof Circle) {
                    debugRenderer.circle(((Circle) d.shape).x, ((Circle) d.shape).y, ((Circle) d.shape).radius);
                } else if (d.shape instanceof Ellipse) {
                    debugRenderer.ellipse(((Ellipse) d.shape).x,((Ellipse) d.shape).y,((Ellipse) d.shape).width, ((Ellipse) d.shape).height, (float) Math.toDegrees(d.angle));
                } else if (d.shape instanceof Rectangle) {
                    debugRenderer.rect(((Rectangle) d.shape).x, ((Rectangle) d.shape).y,
                            ((Rectangle) d.shape).width/2, ((Rectangle) d.shape).height/2,
                            ((Rectangle) d.shape).width, ((Rectangle) d.shape).height,
                            1, 1, d.angle);
                } else if (d.shape instanceof Polygon) {
                    debugRenderer.polygon(((Polygon) d.shape).getTransformedVertices());
                } else if (d.shape instanceof Polyline) {
                    debugRenderer.polyline(((Polyline) d.shape).getTransformedVertices());
                }
            });
            debugRenderer.end();
            shapes = new Array<>();

            //Debug Overlay HUD
            Array<String> debugs = updateDebugStrings();

            debugBatch.begin();
            font.setColor(Color.FIREBRICK);
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
        output.add("CONTROLS    B = Toggle Debug mode, Q = Zoom in, E = Zoom out, Esc = Pause (Breaks Game atm)");
        output.add("F1 = Increase player speed stat");
        output.add("F2 = Decrease player speed stat");
        output.add("F4 = Spawns an Dead Enemy Ship at the mouse position");
        output.add("F5 = Spawns an Enemy Ship at the mouse position");
        for (String str : strings)
            output.add(str);
        strings = new Array<>();
        return output;
    }

    public static void addDebug(String string) {
        if (build == BUILD.DEV)
            strings.add(string);
    }

    public static void addDebug(String string, Object obj) {
        if (build == BUILD.DEV)
            strings.add(string + obj.toString());
    }

    public static void addDebug(Object object) {
        if (build == BUILD.DEV)
            strings.add(object.toString());
    }

    public static void addShape(Shape2D shape, Color color, float angle) {
        if (build == BUILD.DEV)
            shapes.add(new DebugShape(shape, color, angle));
    }
    public static class DebugShape {


        public final Shape2D shape;
        public final Color color;
        public final float angle;

        public DebugShape(Shape2D shape, Color color, float Angle) {
            this.shape = shape;
            this.color = color;
            angle = Angle;
        }
    }
}
