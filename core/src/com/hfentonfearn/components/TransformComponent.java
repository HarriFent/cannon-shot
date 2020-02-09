package com.hfentonfearn.components;

import com.badlogic.ashley.core.Component;

public class TransformComponent implements Component {
    public float x = 0.0f;
    public float y = 0.0f;
    public float angle = 0.0f;
    public float scaleX = 1.0f;
    public float scaleY = 1.0f;
    public float width = 0.0f;
    public float height = 0.0f;
    public float originX = 0.0f;
    public float originY = 0.0f;

    public TransformComponent() {}

    public TransformComponent(float x, float y, float w, float h) {
        this.x = x;
        this.y = y;
        this.width = w;
        this.height = h;
    }

    public void setPosition(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public void setOrigin(float originX, float originY) {
        this.originX = originX;
        this.originY = originY;
    }
}
