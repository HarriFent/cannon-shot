package com.hfentonfearn.helpers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;

public class AssetLoader {

    private static Texture texture;
    public static TextureRegion splash, bgMainMenu, shipWhite, shipBlack, bgSea;
    public static TiledMap map;

    public static void load() {

        texture = new Texture(Gdx.files.internal("flipflopSplash.png"));
        texture.setFilter(TextureFilter.Nearest, TextureFilter.Nearest);
        splash = new TextureRegion(texture);

        bgMainMenu = getTexture("backgrounds/MainMenu.png");

        shipWhite = getTexture("objects/ships/shipWhite.png");
        shipBlack = getTexture("objects/shipBlack.png");

        //Load Tiled Map
        map = new TmxMapLoader().load("tiledMap/world1.tmx");
    }

    private static TextureRegion getTexture(String imgDir) {
        return new TextureRegion(new Texture(Gdx.files.internal(imgDir)));
    }

    public static void dispose() {
    }
}
