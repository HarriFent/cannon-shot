package com.hfentonfearn.helpers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.*;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class AssetLoader {

    public static Texture texture;
    public static TextureRegion splash;

    public static void load() {

        texture = new Texture(Gdx.files.internal("flipflopSplash.png"));
        texture.setFilter(TextureFilter.Nearest, TextureFilter.Nearest);

        splash = new TextureRegion(texture);
    }

    public static void dispose() {
    }
}
