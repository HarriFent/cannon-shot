package com.hfentonfearn.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
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

    public static final String TEXTURE_ATLAS_OBJECTS = "atlas/cannon-shot.atlas";
    public static final String TEXTURE_ATLAS_PARTICLES = "atlas/particles.atlas";
    public static final String SKIN = "skin/skin2.json";
    public static final String MAP = "tiledMap/world1.tmx";

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
    public static AssetActions actions;

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
        fonts = new AssetFonts(skin);
        map = new AssetMap();
        minimap = new AssetMiniMap();
        playerShip = new AssetPlayerShip(atlas);
        enemyShip = new AssetEnemyShip(atlas);
        ui = new AssetsUI(atlas);
        clouds = new AssetCloud(atlas);
        projectiles = new AssetProjectiles(atlas);
        effects = new AssetEffects(atlas);
        actions = new AssetActions(atlas);

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
            font = skin.getFont("default");
        }

    }

    public static class AssetsUI {

        public Texture mainMenu;
        public DockUI docks;

        public AssetsUI (TextureAtlas atlas) {
            mainMenu = new Texture(Gdx.files.internal("ui/MainMenu.png"));
            docks = new DockUI(atlas);
        }

        public static class DockUI {

            public final AtlasRegion[] upgBtnSpeed = new AtlasRegion[6];
            public final AtlasRegion[] upgBtnSteering = new AtlasRegion[6];

            public DockUI(TextureAtlas atlas) {
                for (int i = 0; i < upgBtnSpeed.length; i++)
                    upgBtnSpeed[i] = atlas.findRegion("dockSpeed" + (i+1));
                for (int i = 0; i < upgBtnSteering.length; i++)
                    upgBtnSteering[i] = atlas.findRegion("dockSteering" + (i+1));
            }
        }
    }

    public static class AssetMiniMap {
        public final Texture mapOverview;
        public final Texture mapBackground;
        public final Texture crossRed;
        public final Texture crossGreen;

        public AssetMiniMap() {
            mapOverview = new Texture(Gdx.files.internal("tiledMap/mapView.png"));
            mapBackground = new Texture(Gdx.files.internal("tiledMap/mapBackground.png"));
            crossRed = new Texture(Gdx.files.internal("tiledMap/cross_red.png"));
            crossGreen = new Texture(Gdx.files.internal("tiledMap/cross_green.png"));
        }
    }

    public static class AssetMap {

        public final TiledMap tiledMap;
        public final int width;
        public final int height;
        public final MapObjects zones;

        public AssetMap () {
            tiledMap = new TmxMapLoader().load(MAP);
            width = tiledMap.getProperties().get("width", Integer.class) * tiledMap.getProperties().get("tilewidth", Integer.class);
            height = tiledMap.getProperties().get("height", Integer.class) * tiledMap.getProperties().get("tileheight", Integer.class);
            zones = tiledMap.getLayers().get("zones").getObjects();
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
        public final AtlasRegion dockZone;

        public AssetEffects(TextureAtlas atlas) {
            cannonSplash = new Animation<TextureRegion>(0.06f, atlas.findRegions("waterSplash"));
            cannonExplosion = new Animation<TextureRegion>(0.08f, atlas.findRegions("explosion"));
            dockZone = atlas.findRegion("dockZone");
        }
    }

    public static class AssetParticles {
        public final TextureAtlas atlas;

        public AssetParticles(TextureAtlas atlas) {
            this.atlas = atlas;
        }
    }

    public static class AssetActions {
        public final AtlasRegion dock;

        public AssetActions(TextureAtlas atlas) {
            dock = atlas.findRegion("btnDock");
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
