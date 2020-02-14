package com.hfentonfearn.helpers;

import com.badlogic.gdx.InputProcessor;

public class InputHandler implements InputProcessor {

    private static boolean[] keyboardState = new boolean[525];

    private static boolean[] mouseState = new boolean[5];

    public static boolean keyboardKeyState(int key)
    {
        return keyboardState[key];
    }

    public static boolean mouseButtonState(int button)
    {
        return mouseState[button - 1];
    }

    @Override
    public boolean keyDown(int keycode) {
        keyboardState[keycode] = true;
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        keyboardState[keycode] = false;
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        mouseState[button] = true;
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        mouseState[button] = false;
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}
