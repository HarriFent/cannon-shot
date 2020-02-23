package com.hfentonfearn.helpers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;

public class AssetLoader implements Disposable {

    public static AssetManager manager;

    public static AssetManager getManager () {
        if (manager == null) {
            manager = new AssetManager();
        }
        return manager;
    }

    public static final String TEXTURE_ATLAS_OBJECTS = "assets.atlas";
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
        fonts = new AssetFonts();
        map = new AssetMap();
        ship = new AssetShip(atlas);
        ui = new AssetsUI(atlas);
    }

    @Override
    public void dispose () {
        manager.dispose();
    }

    public static class AssetShip {
        public final Array<AtlasRegion> ships;
        public final AtlasRegion playerShip;

        public AssetShip(TextureAtlas atlas) {
            ships = atlas.findRegions("ship");
            playerShip = atlas.findRegion("playerShip");
        }
    }

    public static class AssetFonts {

        public final BitmapFont font;

        public AssetFonts () {
            font = new BitmapFont(Gdx.files.internal("skin/font-export.fnt"));
        }

    }

    public static class AssetsUI {

        public AssetsUI (TextureAtlas atlas) {

        }
    }

    public static class AssetMap {

        public final TiledMap map;

        public AssetMap () {
            map = new TmxMapLoader().load(MAP);
        }
    }

    public static class AssetHotkey {
        public final AtlasRegion left;
        public final NinePatch button;
        public AtlasRegion middle;
        public AtlasRegion right;

        public AssetHotkey (TextureAtlas atlas) {
            left = atlas.findRegion("hotkeyleft");
            button = atlas.createPatch("hotkey");
            right = atlas.findRegion("hotkeyright");
            middle = atlas.findRegion("middlehotkey");
        }
    }

    public static class AssetProjectile {
        public final Array<AtlasRegion> projectiles;

        public AssetProjectile (TextureAtlas atlas) {
            projectiles = atlas.findRegions("projectile");
        }
    }

}
