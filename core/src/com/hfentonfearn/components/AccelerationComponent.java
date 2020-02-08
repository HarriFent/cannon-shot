package com.hfentonfearn.components;

import com.badlogic.ashley.core.Component;

public class AccelerationComponent implements Component {
    private float tangentAcc;
    private float angleAcc;

    public void clear() {
        tangentAcc = 0;
        angleAcc = 0;
    }

    public float getTangentAcc() {
        return tangentAcc;
    }

    public void setTangentAcc(float tangentAcc) {
        this.tangentAcc = tangentAcc;
    }

    public float getAngleAcc() {
        return angleAcc;
    }

    public void setAngleAcc(float angleAcc) {
        this.angleAcc = angleAcc;
    }
}
