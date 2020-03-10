package com.hfentonfearn.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Disposable;

public class AssetLoader implements Disposable {

    public static AssetManager manager;

    public static AssetManager getManager () {
        if (manager == null) {
            manager = new AssetManager();
        }
        return manager;
    }

    public static final String TEXTURE_ATLAS_OBJECTS = "cannon-shot.atlas";
    public static final String TEXTURE_ATLAS_PARTICLES = "particles.atlas";
    public static final String SKIN = "skin/level-plane-ui.json";
    public static final String MAP = "tiledMap/world1.tmx";

    public static AssetHotkey hotkey;
    public static AssetFonts fonts;
    public static AssetMap map;
    public static AssetMiniMap minimap;
    public static AssetsUI ui;
    public static AssetPlayerShip playerShip;
    public static AssetEnemyShip enemyShip;
    public static AssetCloud clouds;
    public static AssetProjectiles projectiles;
    public static AssetEffects effects;
    public static AssetParticles particles;

    public static Skin skin;

    public static void load () {
        getManager(); // Insure the manager exists
        manager.load(TEXTURE_ATLAS_OBJECTS, TextureAtlas.class);
        manager.load(TEXTURE_ATLAS_PARTICLES, TextureAtlas.class);
        manager.load(SKIN, Skin.class);
    }

    public static void create () {
        TextureAtlas atlas = manager.get(TEXTURE_ATLAS_OBJECTS);

        skin = manager.get(SKIN);
        hotkey = new AssetHotkey(atlas);
        fonts = new AssetFonts(skin);
        map = new AssetMap();
        minimap = new AssetMiniMap();
        playerShip = new AssetPlayerShip(atlas);
        enemyShip = new AssetEnemyShip(atlas);
        ui = new AssetsUI();
        clouds = new AssetCloud(atlas);
        projectiles = new AssetProjectiles(atlas);
        effects = new AssetEffects(atlas);

        particles = new AssetParticles(manager.get(TEXTURE_ATLAS_PARTICLES));
    }

    @Override
    public void dispose () {
        manager.dispose();
    }

    public static class AssetPlayerShip {
        public final AtlasRegion ship;
        public final AtlasRegion sail;
        public BodyEditorLoader loader;

        public AssetPlayerShip(TextureAtlas atlas) {
            //ships = atlas.findRegions("ship");
            ship = atlas.findRegion("hullLarge");
            sail = atlas.findRegion("sailWhite");
        }

        public void loadLoader() {
            if (loader != null)
                return;
            loader = new BodyEditorLoader(Gdx.files.internal("objects/ships/playerShip.json"));
        }
    }

    public static class AssetEnemyShip {
        public final AtlasRegion ship;
        public final AtlasRegion deadShip;
        public BodyEditorLoader loader;

        public AssetEnemyShip(TextureAtlas atlas) {
            //ships = atlas.findRegions("ship");
            ship = atlas.findRegion("shipBlack");
            deadShip = atlas.findRegion("shipBlackDead");
        }

        public void loadLoader() {
            if (loader != null)
                return;
            loader = new BodyEditorLoader(Gdx.files.internal("objects/ships/enemyShip.json"));
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

    public static class AssetMiniMap {
        public final Texture mapOverview;
        public final Texture mapBackground;
        public final Texture cross;

        public AssetMiniMap() {
            mapOverview = new Texture(Gdx.files.internal("tiledMap/mapView.png"));
            mapBackground = new Texture(Gdx.files.internal("tiledMap/mapBackground.png"));
            cross = new Texture(Gdx.files.internal("tiledMap/cross.png"));
        }
    }

    public static class AssetMap {

        public final TiledMap tiledMap;
        public final int width;
        public final int height;
        public final MapObjects spawnzones;

        public AssetMap () {
            tiledMap = new TmxMapLoader().load(MAP);
            width = tiledMap.getProperties().get("width", Integer.class) * tiledMap.getProperties().get("tilewidth", Integer.class);
            height = tiledMap.getProperties().get("height", Integer.class) * tiledMap.getProperties().get("tileheight", Integer.class);
            spawnzones = tiledMap.getLayers().get("spawnzones").getObjects();
        }
    }

    public static class AssetHotkey {
        public final NinePatch button;

        public AssetHotkey (TextureAtlas atlas) {
            button = atlas.createPatch("button");
        }
    }

    public static class AssetCloud {
        public final AtlasRegion[] clouds;

        public AssetCloud (TextureAtlas atlas) {
            clouds = new AtlasRegion[3];
            clouds[0] = atlas.findRegion("cloud1");
            clouds[1] = atlas.findRegion("cloud2");
            clouds[2] = atlas.findRegion("cloud3");
        }

        public AtlasRegion getRandomCloud() {
            return clouds[MathUtils.random(2)];
        }
    }

    public static class AssetProjectiles {
        public final AtlasRegion cannonBall;

        public AssetProjectiles (TextureAtlas atlas) {
            cannonBall = atlas.findRegion("cannonBall");
        }
    }

    public static class AssetEffects {
        public final Animation<TextureRegion> cannonSplash;
        public final Animation<TextureRegion> cannonExplosion;

        public AssetEffects(TextureAtlas atlas) {
            cannonSplash = new Animation<TextureRegion>(0.06f, atlas.findRegions("waterSplash"));
            cannonExplosion = new Animation<TextureRegion>(0.08f, atlas.findRegions("explosion"));
        }
    }

    public static class AssetParticles {
        public final TextureAtlas atlas;

        public AssetParticles(TextureAtlas atlas) {
            this.atlas = atlas;
        }
    }

    private static Animation<TextureRegion> getAnimationFromSheet(Texture spriteSheet, float fps, int cols, int rows) {
        TextureRegion[][] tmp = TextureRegion.split(spriteSheet,spriteSheet.getWidth() / cols,spriteSheet.getHeight() / rows);
        TextureRegion[] frames = new TextureRegion[cols * rows];
        int index = 0;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                frames[index++] = tmp[i][j];
            }
        }
        return new Animation<>(fps, frames);
    }
}
