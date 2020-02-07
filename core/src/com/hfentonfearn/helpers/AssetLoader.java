package com.hfentonfearn.helpers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.*;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class AssetLoader {

    private static Texture texture;
    public static TextureRegion splash, bgMainMenu, shipWhite, shipBlack;

    public static void load() {

        texture = new Texture(Gdx.files.internal("flipflopSplash.png"));
        texture.setFilter(TextureFilter.Nearest, TextureFilter.Nearest);
        splash = new TextureRegion(texture);

        bgMainMenu = getTexture("backgrounds/MainMenu.png");

        shipWhite = getTexture("objects/shipWhite.png");
        shipBlack = getTexture("objects/shipBlack.png");
    }

    private static TextureRegion getTexture(String imgDir) {
        return new TextureRegion(new Texture(Gdx.files.internal(imgDir)));
    }

    public static void dispose() {
    }
}
