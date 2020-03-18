package com.hfentonfearn.ui.actions;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public abstract class ActionButton {

    protected Sprite sprite;
    protected boolean hidden;

    protected ActionButton(Sprite sprite) {
        this.sprite = sprite;
        hidden = true;
    }

    public abstract void onClick();

    public void show() {
        hidden = false;
    }

    public void hide() {
        hidden = true;
    }

    public boolean isHidden() {
        return hidden;
    }

    public void draw(SpriteBatch batch, Vector2 pos) {
        sprite.setCenter(pos.x, pos.y);
        sprite.draw(batch);
    };
}
