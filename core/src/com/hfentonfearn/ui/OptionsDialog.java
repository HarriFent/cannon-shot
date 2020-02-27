package com.hfentonfearn.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.*;

public class OptionsDialog extends Dialog {

    public OptionsDialog(Skin skin) {
        super("Options", skin);
        setPosition((Gdx.graphics.getWidth() * 0.5f) - (getWidth() * 0.5f), (Gdx.graphics.getHeight() * 0.5f)
                - (getHeight() * 0.5f));
        setMovable(false);
        setModal(true);

        TextButton closeButton = new TextButton("Close", skin);
        button(closeButton);
    }
}
