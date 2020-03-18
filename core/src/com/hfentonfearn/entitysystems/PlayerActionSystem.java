package com.hfentonfearn.entitysystems;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.hfentonfearn.GameManager;
import com.hfentonfearn.components.PlayerComponent;
import com.hfentonfearn.ui.actions.ActionButton;
import com.hfentonfearn.ui.actions.DockActionButton;
import com.hfentonfearn.utils.Components;

public class PlayerActionSystem extends EntitySystem {

    private GUISystem guiSystem;
    private CameraSystem cameraSystem;
    private OrthographicCamera camera;

    private SpriteBatch batch;
    private ShapeRenderer shapeRenderer;
    private static Array<ActionButton> actions = new Array<>();

    private final int radius = 80;

    public PlayerActionSystem() {
        batch = new SpriteBatch();
        shapeRenderer = new ShapeRenderer();
        actions.add(new DockActionButton());
    }

    public static void hideButton(Class<DockActionButton> actionButtonClass) {
        for (ActionButton a : actions )
            if (actionButtonClass.isInstance(a))
                a.hide();
    }

    public void addedToEngine (Engine engine) {
        cameraSystem = engine.getSystem(CameraSystem.class);
        guiSystem = engine.getSystem(GUISystem.class);
    }

    public static <T extends ActionButton> void showButton(Class<T> actionButtonClass) {
        for (ActionButton a : actions )
            if (actionButtonClass.isInstance(a))
                a.show();
    }

    @Override
    public void update(float deltaTime) {
        if (numOfActions() > 0) {
            camera = cameraSystem.getCamera();
            Vector2 pos = Components.PHYSICS.get(PlayerComponent.player).getPosition();

            shapeRenderer.setProjectionMatrix(camera.combined);
            shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
            shapeRenderer.setColor(0.5f,0.5f,0.5f,0.1f);
            shapeRenderer.circle(pos.x, pos.y, radius);
            shapeRenderer.end();

            batch.setProjectionMatrix(camera.combined);
            batch.begin();
            drawActionButtons(pos);
            batch.end();
        }
    }

    private int numOfActions() {
        int count = 0;
        for (ActionButton a : actions) {
            if (!a.isHidden()) count++;
        }
        return count;
    }

    private void drawActionButtons(Vector2 center) {
        int angle = 0;
        for ( ActionButton a : actions) {
            DebugRendererSystem.addDebug(a.getClass().getSimpleName() + " isHidden: ", a.isHidden());
            if (!a.isHidden()) {
                float posX = (float) (center.x + (radius * Math.cos(angle)));
                float posY = (float) (center.y + (radius * Math.sin(angle)));
                a.draw(batch, new Vector2(posX, posY));
                angle += 60;
            }
        }
    }

    @Override
    public boolean checkProcessing () {
        return !GameManager.isPaused();
    }
}
