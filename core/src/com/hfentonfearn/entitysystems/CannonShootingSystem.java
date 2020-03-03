package com.hfentonfearn.entitysystems;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.hfentonfearn.components.PhysicsComponent;
import com.hfentonfearn.components.PlayerComponent;
import com.hfentonfearn.components.ShipStatisticComponent;
import com.hfentonfearn.ecs.EntityFactory;
import com.hfentonfearn.utils.Components;

import static com.hfentonfearn.utils.Constants.CANNONBALL_DYING_VELOCITY;

public class CannonShootingSystem extends EntitySystem {

    private Vector2 mousePos;
    private CameraSystem cam;
    private boolean mouseDown;
    private int timer = 0;
    private ImmutableArray<Entity> players;
    private Array<Entity> ballArray = new Array<>();

    @Override
    public void addedToEngine (Engine engine) {
        cam = engine.getSystem(CameraSystem.class);
        players = engine.getEntitiesFor(Family.all(PlayerComponent.class, PhysicsComponent.class, ShipStatisticComponent.class).get());
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        if (mouseDown && timer == 0) {
            //Spawn Cannon ball
            Entity player = players.get(0);
            PhysicsComponent physics = Components.PHYSICS.get(player);
            ShipStatisticComponent stats = Components.STATS.get(player);
            Vector2 dir = mousePos.cpy().sub(physics.getPosition());
            Vector2 cannonBallPos = getCannonBallPos(Components.SPRITE.get(player).getSprites(),
                    physics.getPosition(),
                    physics.getBody().getAngle(),
                    dir);
            ballArray.add(EntityFactory.createCannonBall(cannonBallPos.cpy(), dir.nor().scl(stats.firerange)));
            EntityFactory.createExplosion(cannonBallPos);
            timer = stats.firerate;
        }
        if (timer > 0) timer--;
        for (Entity e: ballArray) {
            PhysicsComponent physics = Components.PHYSICS.get(e);
            if (physics.getBody().getLinearVelocity().len() < CANNONBALL_DYING_VELOCITY) {
                EntityFactory.createCannonBallSplash(physics.getPosition());
                Components.KILL.get(e).kill = true;
                ballArray.removeValue(e,false);
            }
        }
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

    public void setMousePos(float screenX, float screenY) {
        this.mousePos = cam.screenToWorldCords(screenX,screenY);
    }

    public void setMouseDown(boolean down) {
        mouseDown = down;
    }
}
