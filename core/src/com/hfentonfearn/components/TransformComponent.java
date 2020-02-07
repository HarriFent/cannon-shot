package com.hfentonfearn.components;

import com.badlogic.ashley.core.Component;

public class TransformComponent implements Component {
    private float x = 0.0f;
    private float y = 0.0f;
    private float angle = 0.0f;
    private float scaleX = 1.0f;
    private float scaleY = 1.0f;

    public TransformComponent() {}

    public TransformComponent(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void setPosition(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public void incPosition(float x, float y) {
        this.x += x;
        this.y += y;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public float getAngle() {
        return angle;
    }

    public void setAngle(float angle) {
        this.angle = angle;
    }

    public float getScaleX() {
        return scaleX;
    }

    public void setScaleX(float scaleX) {
        this.scaleX = scaleX;
    }

    public float getScaleY() {
        return scaleY;
    }

    public void setScaleY(float scaleY) {
        this.scaleY = scaleY;
    }

    public void incAngle(float angle) {
        this.angle +=  angle;
    }
}
