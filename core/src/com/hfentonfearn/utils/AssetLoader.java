package com.hfentonfearn.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;

public class AssetLoader implements Disposable {

    public static AssetManager manager;

    public static AssetManager getManager () {
        if (manager == null) {
            manager = new AssetManager();
        }
        return manager;
    }

    public static final String TEXTURE_ATLAS_OBJECTS = "cannon-shot.atlas";
    public static final String SKIN = "skin/level-plane-ui.json";
    public static final String MAP = "tiledMap/world1.tmx";

    public static AssetHotkey hotkey;
    public static AssetFonts fonts;
    public static AssetMap map;
    public static AssetsUI ui;
    public static AssetShip ship;
    public static AssetProjectile projectile;

    public static Skin skin;

    public static void load () {
        getManager(); // Insure the manager exists
        manager.load(TEXTURE_ATLAS_OBJECTS, TextureAtlas.class);
        manager.load(SKIN, Skin.class);
    }

    public static void create () {
        TextureAtlas atlas = manager.get(TEXTURE_ATLAS_OBJECTS);

        skin = manager.get(SKIN);
        projectile = new AssetProjectile(atlas);
        hotkey = new AssetHotkey(atlas);
        fonts = new AssetFonts(skin);
        map = new AssetMap();
        ship = new AssetShip(atlas);
        ui = new AssetsUI();
    }

    @Override
    public void dispose () {
        manager.dispose();
    }

    public static class AssetShip {
        //public final Array<AtlasRegion> ships;
        public final AtlasRegion playerShip;
        public final float[] collisionPoly;

        public AssetShip(TextureAtlas atlas) {
            //ships = atlas.findRegions("ship");
            playerShip = atlas.findRegion("shipWhite");

            JsonReader json = new JsonReader();
            JsonValue base = json.parse(Gdx.files.internal("objects/ships/shipCollision.json"));
            JsonValue vertArray = base.get("collisionPoly");
            float[] verts = new float[vertArray.size];
            for (int i = 0; i < vertArray.size; i++) {
                verts[i] = vertArray.getFloat(i);
            }
            collisionPoly = verts;
        }
    }

    public static class AssetFonts {

        public final BitmapFont font;

        public AssetFonts (Skin skin) {
            TextureRegion region = skin.getAtlas().findRegion("font-export");
            font = new BitmapFont(Gdx.files.internal("skin/font-export.fnt"), region);
        }

    }

    public static class AssetsUI {

        public Texture mainMenu;

        public AssetsUI () {
            mainMenu = new Texture(Gdx.files.internal("ui/MainMenu.png"));
        }
    }

    public static class AssetMap {

        public final TiledMap map;

        public AssetMap () {
            map = new TmxMapLoader().load(MAP);
        }
    }

    public static class AssetHotkey {
        public final NinePatch button;

        public AssetHotkey (TextureAtlas atlas) {
            button = atlas.createPatch("button");
        }
    }

    public static class AssetProjectile {
        public final Array<AtlasRegion> projectiles;

        public AssetProjectile (TextureAtlas atlas) {
            projectiles = atlas.findRegions("projectile");
        }
    }

}
