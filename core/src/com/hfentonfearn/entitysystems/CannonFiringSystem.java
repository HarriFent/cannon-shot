package com.hfentonfearn.entitysystems;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.hfentonfearn.components.CannonFiringComponent;
import com.hfentonfearn.components.PhysicsComponent;
import com.hfentonfearn.components.ShipStatisticComponent;
import com.hfentonfearn.ecs.EntityFactory;
import com.hfentonfearn.utils.Components;

public class CannonFiringSystem extends IteratingSystem {

    private CameraSystem cam;

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
        if (Components.PLAYER.has(entity)){
            //Player Firing
            if (fireComp.firing && fireComp.timer == 0) {
                //Spawn Cannon ball
                PhysicsComponent physics = Components.PHYSICS.get(entity);
                Vector2 dir = getFireDirection(physics.getPosition());
                Vector2 cannonBallPos = getCannonBallPos(Components.SPRITE.get(entity).getSprites(),
                        physics.getPosition(),
                        physics.getBody().getAngle(),
                        dir);
                EntityFactory.createCannonBall(cannonBallPos.cpy(), dir.nor().scl(fireComp.range));
                EntityFactory.createExplosion(cannonBallPos);
                fireComp.timer = fireComp.firerate;
            }
            if (fireComp.timer > 0) fireComp.timer--;
        } else {
            //Non Player firing
        }
    }

    private Vector2 getFireDirection(Vector2 pos) {
        return cam.screenToWorldCords(Gdx.input.getX(),Gdx.input.getY()).sub(pos);
    }

    private Vector2 getCannonBallPos(Array<Sprite> sprites, Vector2 playerPos, float playerAngle, Vector2 dir) {
        float angle = dir.cpy().rotateRad(-playerAngle).angleRad();
        float width = 0;
        float height = 0;
        for (Sprite sprite: sprites){
            if (sprite.getWidth() > width)
                width = sprite.getWidth();
            if (sprite.getHeight() > height)
                height = sprite.getHeight();
        }
        int ePX = (int) ( width/1.9  * Math.cos(angle));
        int ePY = (int) ( height/1.9 * Math.sin(angle));
        Vector2 pos = new Vector2(ePX,ePY);
        pos.rotateRad(playerAngle);
        pos.add(playerPos);
        return pos;
        //return playerPos;
    }

}
