package com.hfentonfearn.components;

import com.badlogic.ashley.core.Component;

public class AccelerationComponent implements Component {
    public float angular;
    public float linear;

    public AccelerationComponent() {
        angular = 0f;
        linear = 0f;
    }
}
