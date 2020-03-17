package com.hfentonfearn.ui.actions;

import com.badlogic.gdx.graphics.g2d.Sprite;

public abstract class ActionButton {

    protected Sprite sprite;
    protected boolean hidden;

    protected ActionButton(Sprite sprite) {
        this.sprite = sprite;
        hidden = true;
    }

    public abstract void onClick();

    public void show() {
        hidden = true;
    }

    public void hide() {
        hidden = false;
    }
}
