package com.hfentonfearn;

import com.badlogic.gdx.Game;
import com.hfentonfearn.ecs.EntityManager;

public class GameManager {

    private static Game game;
    private static EntityManager engine;

    private static boolean paused = false;

    public static void init(Game game) {
        GameManager.game = game;
    }

    public static EntityManager initEngine() {
        if (engine != null) {
            disposeEngine();
        }
        engine = new EntityManager();
        //Add Engine Listeners
        return engine;
    }

    private static void disposeEngine() {
        if (engine != null) {
            engine.dispose();
        }
        engine = null;
    }
}
