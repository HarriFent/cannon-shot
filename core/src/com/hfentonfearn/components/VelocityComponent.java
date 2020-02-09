package com.hfentonfearn.components;

import com.badlogic.ashley.core.Component;

public class VelocityComponent implements Component {
    private final int velLimit;
    private final int angleLimit;
    private float tangentVel = 0.0f;
    private float angleVel = 0.0f;

    public VelocityComponent(int velLimit, int angleLimit) {
        this.velLimit = velLimit;
        this.angleLimit = angleLimit;
    }

    public void clear() {
        tangentVel = 0;
        angleVel = 0;
    }

    public void incVelocity(float dvel) {
        if (Math.abs(tangentVel + dvel) <= velLimit) {
            this.tangentVel += dvel;
        } else {
            this.tangentVel = dvel > 0 ? velLimit : -velLimit;
        }
        if  (Math.abs(tangentVel) < 0.01) tangentVel = 0;
    }

    public void setVelocity(float vel) {
        this.tangentVel = vel;
    }

    public float getTangentVel() {
        return tangentVel;
    }

    public void incAngle(float dvel) {
        if (Math.abs(angleVel + dvel) <= angleLimit) {
            this.angleVel += dvel;
        } else {
            this.angleVel = dvel > 0 ? angleLimit : -angleLimit;
        }
        if  (Math.abs(angleVel) < 0.01) angleVel = 0;
    }

    public void setAngle(float vel) {
        this.angleVel = vel;
    }

    public float getAngleVel() {
        return angleVel;
    }
}