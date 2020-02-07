package com.hfentonfearn.helpers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.*;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class AssetLoader {

    private static Texture texture;
    public static TextureRegion splash, bgMainMenu, shipWhite, shipBlack, bgSea;

    public static void load() {

        texture = new Texture(Gdx.files.internal("flipflopSplash.png"));
        texture.setFilter(TextureFilter.Nearest, TextureFilter.Nearest);
        splash = new TextureRegion(texture);

        bgMainMenu = getTexture("backgrounds/MainMenu.png");

        shipWhite = getTexture("objects/shipWhite.png");
        shipBlack = getTexture("objects/shipBlack.png");

        texture = new Texture(Gdx.files.internal("backgrounds/tiles/SeaTile.png"));
        texture.setWrap(TextureWrap.Repeat,TextureWrap.Repeat);
        bgSea = new TextureRegion(texture);
    }

    private static TextureRegion getTexture(String imgDir) {
        return new TextureRegion(new Texture(Gdx.files.internal(imgDir)));
    }

    public static void dispose() {
    }
}
