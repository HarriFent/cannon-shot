package com.hfentonfearn.helpers;

import com.badlogic.ashley.core.Entity;
import com.hfentonfearn.components.TransformComponent;
import com.hfentonfearn.components.VelocityComponent;

public class MathsHandler {

    public static float[] getEntityTarget(Entity e) {
        TransformComponent tComp = MappersHandler.transform.get(e);
        VelocityComponent vComp = MappersHandler.velocity.get(e);
        float[] target = {
                (float) (vComp.getTangentVel() * Math.sin(Math.toRadians(tComp.getAngle()))),
                (float) (-vComp.getTangentVel() * Math.cos(Math.toRadians(tComp.getAngle())))
        };
        return target;
    }
}
