package com.hfentonfearn.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pool.Poolable;

public class SpriteComponent implements Component, Poolable {

    private Array<Sprite> sprites;

    /** Can only be created by PooledEngine */
    private SpriteComponent () {
        // private constructor
    }

    private Sprite createSprite(TextureRegion region, float x, float y, float width, float height) {
        Sprite sprite = new Sprite(region);
        sprite.setBounds(x, y, width, height);
        return sprite;
    }

    public SpriteComponent init(TextureRegion region, float x, float y, float width, float height) {
        sprites = new Array<>();
        Sprite sprite = createSprite(region,x,y,width,height);
        sprite.setOriginCenter();
        sprites.add(sprite);
        return this;
    }

    public SpriteComponent init(Sprite sprite) {
        sprites = new Array<>();
        sprites.add(sprite);
        return this;
    }

    public void addSprite(TextureRegion region, float x, float y, float width, float height) {
        Sprite sprite = createSprite(region,x,y,width,height);
        sprite.setOriginCenter();
        sprites.add(sprite);
    }

    public void addSprite(Sprite sprite) {
        sprites.add(sprite);
    }

    public Array<Sprite> getSprites () {
        return sprites;
    }

    @Override
    public void reset () {
        sprites = null;
    }
}
