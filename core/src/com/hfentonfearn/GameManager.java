package com.hfentonfearn;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.hfentonfearn.ecs.EntityFactory;
import com.hfentonfearn.ecs.EntityManager;

public class GameManager {

    private static Game game;
    private static EntityManager engine;

    private static boolean paused;

    public static void init(Game game) {
        GameManager.game = game;
    }

    public static EntityManager initEngine() {
        if (engine != null) {
            disposeEngine();
        }
        engine = new EntityManager();
        //Add Engine Helpers (EntityFactory)
        EntityFactory.setEngine(engine);
        paused = false;
        return engine;
    }

    public static void setScreen(Screen screen) {
        if (game.getScreen() != null) {
            game.getScreen().dispose();
        }
        game.setScreen(screen);
    }

    public static void disposeEngine() {
        if (engine != null) {
            engine.dispose();
        }
        engine = null;
    }

    public static boolean isPaused() {
        return paused;
    }

    public static void pause(){
        paused = true;
    }

    public static void resume(){
        paused = false;
    }

    public static void exit () {
        disposeEngine();
        game.getScreen().dispose();
        Gdx.app.exit();
    }
}
