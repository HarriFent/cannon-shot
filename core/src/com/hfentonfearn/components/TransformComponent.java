package com.hfentonfearn.components;

import com.badlogic.ashley.core.Component;

public class TransformComponent implements Component {
    private float x = 0.0f;
    private float y = 0.0f;
    private float angle = 0.0f;
    private float scaleX = 1.0f;
    private float scaleY = 1.0f;
    private float width = 0.0f;
    private float height = 0.0f;
    private float originX = 0.0f;
    private float originY = 0.0f;

    public TransformComponent() {}

    public TransformComponent(float x, float y, float w, float h) {
        this.x = x;
        this.y = y;
        this.width = w;
        this.height = h;
    }

    public void incPosition(float x, float y) {
        this.x += x;
        this.y += y;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public float getAngle() {
        return angle;
    }

    public float getScaleX() {
        return scaleX;
    }

    public float getScaleY() {
        return scaleY;
    }

    public void incAngle(float angle) {
        this.angle +=  angle;
    }

    public void setOrigin(float originX, float originY) {
        this.originX = originX;
        this.originY = originY;
    }


    public float getOriginX() {
        return originX;
    }

    public float getOriginY() {
        return originY;
    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }
}
