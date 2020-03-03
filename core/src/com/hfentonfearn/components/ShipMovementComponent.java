package com.hfentonfearn.components;

import com.badlogic.ashley.core.Component;

public class ShipMovementComponent implements Component {

     public float impulseVel;
     public float impulseAngle;

     public ShipMovementComponent(float speed, float turningSpeed ) {
         impulseVel = speed;
         impulseAngle = turningSpeed;
     }

    /**
     * DEVELOPER TOOLS
     * Used for increasing the velocity of the ship in the developer tools
     */
     public void incVelocity(float impulse) {
         impulseVel += impulse;
     }
}
