package com.hfentonfearn.entitysystems;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.hfentonfearn.components.CannonFiringComponent;
import com.hfentonfearn.components.PhysicsComponent;
import com.hfentonfearn.components.ShipStatisticComponent;
import com.hfentonfearn.ecs.EntityFactory;
import com.hfentonfearn.utils.Components;

public class CannonFiringSystem extends IteratingSystem {

    private CameraSystem cam;
    private Vector2 playerPos;

    public CannonFiringSystem() {
        super(Family.all(PhysicsComponent.class, ShipStatisticComponent.class, CannonFiringComponent.class).get());
    }

    @Override
    public void addedToEngine (Engine engine) {
        super.addedToEngine(engine);
        cam = engine.getSystem(CameraSystem.class);
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        CannonFiringComponent fireComp = Components.CANNON_FIRE.get(entity);
        PhysicsComponent physics = Components.PHYSICS.get(entity);
        if (Components.PLAYER.has(entity)){
                //Player Firing
                playerPos = physics.getPosition();
                fireComp.direction = cam.screenToWorldCords(Gdx.input.getX(),Gdx.input.getY()).sub(playerPos);
        } else {
            //Non Player firing
            if (playerPos != null) {
                //DebugRendererSystem.addDebug("Distance between ships", playerPos.cpy().sub(physics.getPosition()).len());
                fireComp.firing = playerPos.cpy().sub(physics.getPosition()).len() < fireComp.range * 60;
                fireComp.direction = playerPos.cpy().sub(physics.getPosition());
            }
        }

        //Debug Radius around ship
        /*Vector2 size = Components.SPRITE.get(entity).getSize().scl(2).scl(0.6f);
        Ellipse firingEllipse = new Ellipse(physics.getPosition().cpy().sub(size.cpy().scl(0.5f)),size.cpy());
        DebugRendererSystem.addShape(firingEllipse, Color.RED, physics.getBody().getAngle());*/

        if (fireComp.firing && fireComp.timer == 0) {
            Vector2 cannonBallPos = getCannonBallPos(Components.SPRITE.get(entity).getSize().cpy().scl(0.6f),
                    physics.getPosition(),
                    physics.getBody().getAngle(),
                    fireComp.direction);
            EntityFactory.createCannonBall(cannonBallPos.cpy(), fireComp.direction.nor().scl(fireComp.range));
            EntityFactory.createExplosion(cannonBallPos.cpy(),0.7f);
            EntityFactory.createParticle(cannonBallPos.cpy(), ParticleSystem.ParticleType.SMOKE, 0);
            EntityFactory.createParticle(cannonBallPos.cpy(), ParticleSystem.ParticleType.SPARK, 0);
            fireComp.timer = fireComp.firerate;
        }
        if (fireComp.timer > 0) fireComp.timer--;
    }

    private Vector2 getCannonBallPos(Vector2 size, Vector2 shipPos, float playerAngle, Vector2 dir) {
        float angle = dir.cpy().rotateRad(-playerAngle).angleRad();
        int ePX = (int) ( size.x * Math.cos(angle));
        int ePY = (int) ( size.y * Math.sin(angle));
        Vector2 pos = new Vector2(ePX,ePY);
        pos.rotateRad(playerAngle);
        pos.add(shipPos);
        return pos;
        //return playerPos;
    }

}
