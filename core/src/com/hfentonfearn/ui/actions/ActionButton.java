package com.hfentonfearn.ui.actions;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public abstract class ActionButton {

    protected Sprite sprite;
    protected boolean hidden;

    private boolean mouseOver;
    private boolean mouseDown;

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

    public void draw(SpriteBatch batch, Vector2 pos, Vector2 mousePos) {
        mouseOver = false;
        if (getBoundingCircle().contains(mousePos)) {
            mouseOver = true;
            if (Gdx.input.isTouched()) {
                if (!mouseDown) {
                    onClick();
                    mouseDown = true;
                }
            } else {
                mouseDown = false;
            }
        } else {
            mouseDown = false;
            mouseOver = false;
        }

        sprite.setCenter(pos.x, pos.y);
        if (mouseDown) {
            sprite.setScale(0.9f);
        } else if (mouseOver) {
            sprite.setScale(1.1f);
        } else {
            sprite.setScale(1f);
        }
        sprite.draw(batch);
    }

    private Circle getBoundingCircle() {
        Rectangle rect = sprite.getBoundingRectangle();
        Vector2 pos = rect.getCenter(new Vector2(rect.x, rect.y));
        return new Circle(pos.x, pos.y, rect.width/2);
    }

    public boolean isMouseOver() {
        return mouseOver;
    }
}
