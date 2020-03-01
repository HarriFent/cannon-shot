package com.hfentonfearn.components;

import com.badlogic.ashley.core.Component;

public class VelocityComponent implements Component {
    public float angularVelocity;
    public float linearVelocity;

    public VelocityComponent() {
        angularVelocity = 0f;
        linearVelocity = 0f;
    }
}
