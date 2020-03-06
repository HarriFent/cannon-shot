package com.hfentonfearn.ecs;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.ParticleEffectPool.PooledEffect;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.hfentonfearn.components.*;
import com.hfentonfearn.entitysystems.ParticleSystem;
import com.hfentonfearn.entitysystems.PhysicsSystem;
import com.hfentonfearn.entitysystems.ZoomSystem;
import com.hfentonfearn.utils.AssetLoader;
import com.hfentonfearn.utils.BodyEditorLoader;
import com.hfentonfearn.utils.Components;

import static com.badlogic.gdx.physics.box2d.BodyDef.BodyType.DynamicBody;
import static com.badlogic.gdx.physics.box2d.BodyDef.BodyType.StaticBody;
import static com.hfentonfearn.ecs.EntityFactory.PhysicsBuilder.FixtureBuilder;
import static com.hfentonfearn.entitysystems.ParticleSystem.ParticleType;
import static com.hfentonfearn.entitysystems.ZoomSystem.ZOOM_FAR;
import static com.hfentonfearn.entitysystems.ZoomSystem.ZOOM_MAP;
import static com.hfentonfearn.utils.Constants.*;

public class EntityFactory {

    public static final int MATERIAL_STEEL = 0;
    public static final int MATERIAL_WOOD = 1;
    public static final int MATERIAL_RUBBER = 2;
    public static final int MATERIAL_STONE = 3;


    private static PooledEngine engine;
    private static PhysicsSystem physicsSystem;
    private static EntityBuilder builder = new EntityBuilder();

    private static PhysicsBuilder physicsBuilder = new PhysicsBuilder();
    private static FixtureBuilder fixtureBuilder = new FixtureBuilder();

    public static void setEngine (PooledEngine engine) {
        EntityFactory.engine = engine;
        physicsSystem = engine.getSystem(PhysicsSystem.class);
    }

    public static void createPlayer(Vector2 position) {
        AssetLoader.playerShip.loadLoader();
        Entity entity = builder.createEntity(EntityCategory.PLAYER, position)
                .buildPhysics(DynamicBody).addFixture(MATERIAL_WOOD).bodyLoader(AssetLoader.playerShip.loader, "playership", 0.5f).create().getBody()
                .damping(DAMPING_ANGULAR,DAMPING_LINEAR)
                .sprite(AssetLoader.playerShip.ship)
                .sprite(AssetLoader.playerShip.sail)
                .acceleration()
                .shipStats()
                .drawDistance(ZOOM_FAR)
                .getWithoutAdding();
        entity.add(new PlayerComponent());
        engine.addEntity(entity);
    }

    public static Entity createEnemyShip(Vector2 position, int health) {
        AssetLoader.enemyShip.loadLoader();
        Entity entity = builder.createEntity(EntityCategory.ENEMY,position)
                .buildPhysics(DynamicBody).addFixture(MATERIAL_WOOD).bodyLoader(AssetLoader.enemyShip.loader, "enemyship", 0.65f).create().getBody()
                .damping(DAMPING_ANGULAR, DAMPING_LINEAR)
                .sprite(AssetLoader.enemyShip.ship)
                .shipStats(DEFAULT_SPEED, DEFAULT_STEERING, health, 200, 6f, 1)
                .drawDistance(ZOOM_FAR)
                .addToEngine();
        Components.PHYSICS.get(entity).getBody().setTransform(position.cpy(), MathUtils.random(4));
        return entity;
    }

    public static void createLandMass(Polygon poly) {
        poly.setPosition(poly.getX()*MPP,poly.getY()*MPP);
        poly.setScale(MPP,MPP);
        Entity entity = builder.createEntity(EntityCategory.LAND,new Vector2(0,0))
                .buildPhysics(StaticBody).addFixture(MATERIAL_STONE).polyChain(poly.getTransformedVertices()).create().getBody()
                .addToEngine();
    }

    public static void createRocks(Polygon poly) {
        poly.setPosition(poly.getX()*MPP,poly.getY()*MPP);
        poly.setScale(MPP,MPP);
        Entity entity = builder.createEntity(EntityCategory.SCENERY,new Vector2(0,0))
                .buildPhysics(StaticBody).addFixture(MATERIAL_STONE).polyChain(poly.getTransformedVertices()).create().getBody()
                .addToEngine();
    }

    public static Entity createCloud(Vector2 position, Vector2 movement) {
        Sprite cloudSprite = new Sprite(AssetLoader.clouds.getRandomCloud());
        cloudSprite.setCenter(position.x,position.y);
        cloudSprite.setScale(3);
        Entity entity = builder.createEntity(EntityCategory.CLOUD,position)
                .sprite(cloudSprite)
                .staticMovement(movement)
                .drawDistance(ZOOM_MAP)
                .addToEngine();
        return entity;
    }

    public static Entity createCannonBall(Vector2 position, Vector2 linearVel) {
        Entity entity = builder.createEntity(EntityCategory.CANNONBALL,position)
                .sprite(AssetLoader.projectiles.cannonBall)
                .buildPhysics(DynamicBody).addFixture(MATERIAL_STEEL).circle(0.05f).create().getBody()
                .setInitVelocity(linearVel)
                .damping(0f, CANNONBALL_DAMPING)
                .killable()
                .drawDistance(ZOOM_FAR)
                .particle(ParticleType.CANNON_TRAIL,true)
                .addToEngine();
        return entity;
    }

    public static Entity createCannonBallSplash(Vector2 position) {
        Entity entity = builder.createEntity(EntityCategory.EFFECT,position)
                .animation(AssetLoader.effects.cannonSplash, true)
                .buildPhysics(StaticBody).getBody()
                .drawDistance(ZOOM_FAR)
                .addToEngine();
        return entity;
    }

    public static Entity createExplosion(Vector2 position) {
        Entity entity = builder.createEntity(EntityCategory.EFFECT,position)
                .animation(AssetLoader.effects.cannonExplosion, true)
                .buildPhysics(StaticBody).getBody()
                .drawDistance(ZOOM_FAR)
                .addToEngine();
        return entity;
    }

    public static Entity createParticle (Vector2 position, ParticleType type) {
        Entity entity = builder.createEntity(EntityCategory.EFFECT, position).particle(type, false).addToEngine();
        return entity;
    }

    public static Entity createDyingShip(Vector2 position) {
        Entity entity = builder.createEntity(EntityCategory.EFFECT,position)
                .sprite(AssetLoader.enemyShip.deadShip)
                .killAfterDuration(5)
                .buildPhysics(DynamicBody).getBody()
                .drawDistance(ZOOM_FAR)
                .addToEngine();
        Components.KILL.get(entity).fade = true;
        return entity;
    }

    /**
     *   Entity Builder Class
     * */
    public static class EntityBuilder {

        private static final BodyType DEFAULT_BODY = DynamicBody;
        public Vector2 position;
        public Entity entity;

        public EntityBuilder createEntity (int catergoryBit, Vector2 position) {
            entity = engine.createEntity();
            entity.flags = catergoryBit;
            this.position = position;
            return this;
        }

        public PhysicsBuilder buildPhysics (BodyType type) {
            return physicsBuilder.reset(type, position, entity);
        }

        public EntityBuilder damping (float angular, float linear) {
            if (Components.PHYSICS.has(entity)) {
                PhysicsComponent physics = Components.PHYSICS.get(entity);
                physics.getBody().setAngularDamping(angular);
                physics.getBody().setLinearDamping(linear);
            } else {
                Gdx.app.error("EntityFactory", "entity is missing physics component!");
            }
            return this;
        }

        public EntityBuilder setInitVelocity(Vector2 linear) {
            if (Components.PHYSICS.has(entity)) {
                PhysicsComponent physics = Components.PHYSICS.get(entity);
                physics.getBody().setLinearVelocity(linear);
            }
            return this;
        }

        public EntityBuilder drawDistance (float Zoom) {
            if (Zoom == ZoomSystem.ZOOM_MAP){
                entity.add(new MapDrawComponent());
            }
            if (Zoom == ZOOM_FAR){
                entity.add(new FarDrawComponent());
            }
            return this;
        }

        public EntityBuilder sprite (TextureRegion region) {
            SpriteComponent spriteComp = Components.SPRITE.get(entity);
            if (spriteComp == null) {
                spriteComp = engine.createComponent(SpriteComponent.class).init(region, position.x, position.y, region.getRegionWidth(),
                        region.getRegionHeight());
                entity.add(spriteComp);
            } else {
                spriteComp.addSprite(region, position.x, position.y, region.getRegionWidth(), region.getRegionHeight());
            }
            return this;
        }


        public EntityBuilder sprite (Sprite sprite) {
            SpriteComponent spriteComp = Components.SPRITE.get(entity);
            if (spriteComp == null) {
                spriteComp = engine.createComponent(SpriteComponent.class).init(sprite);
                entity.add(spriteComp);
            } else {
                spriteComp.addSprite(sprite);
            }
            return this;
        }

        public EntityBuilder animation(Animation<TextureRegion> animation, boolean killAfterAnimation) {
            entity.add(engine.createComponent(AnimationComponent.class).init(animation));
            if (killAfterAnimation)
                entity.add(engine.createComponent(KillComponent.class).init(true));
            return this;
        }

        public EntityBuilder particle(ParticleType type, boolean follow) {
            PooledEffect effect = engine.getSystem(ParticleSystem.class).createEffect(position, type);
            entity.add(engine.createComponent(ParticleComponent.class).init(effect, follow));
            return this;
        }

        public EntityBuilder acceleration() {
            entity.add(engine.createComponent(AccelerationComponent.class).init());
            return this;
        }

        public EntityBuilder shipStats() {
            shipStats(DEFAULT_SPEED, DEFAULT_STEERING, DEFAULT_HULL, DEFAULT_FIRERATE, DEFAULT_FIRERANGE, DEFAULT_INVENTORY_SIZE);
            return  this;
        }

        public EntityBuilder shipStats(float speed, float steering, float hull, int firerate, float firerange, int inventorySize) {
            entity.add(engine.createComponent(InventoryComponent.class).init());
            entity.add(engine.createComponent(CannonFiringComponent.class));
            entity.add(engine.createComponent(HealthComponent.class).init(hull));
            entity.add(engine.createComponent(KillComponent.class));
            entity.add(engine.createComponent(ShipStatisticComponent.class).init(speed, steering, hull, firerate, firerange, inventorySize));
            return  this;
        }

        public EntityBuilder killable() {
            entity.add(engine.createComponent(KillComponent.class));
            return this;
        }

        public EntityBuilder killAfterDuration(int seconds) {
            entity.add(engine.createComponent(KillComponent.class).init(seconds * 60));
            return this;
        }

        public EntityBuilder staticMovement(Vector2 movement) {
            entity.add(engine.createComponent(StaticMovementComponent.class).init(movement));
            return this;
        }

        /**
         *  Circle Sensor and Range Sensor
         */
        /*
        public EntityBuilder circleSensor (float radius) {
            CircleShape shape = new CircleShape();
            shape.setRadius(radius);

            PhysicsComponent physics = Components.PHYSICS.get(entity);
            if (physics == null) {
                physicsBody(DEFAULT_BODY);
            }

            FixtureDef fixtureDef = new FixtureDef();
            fixtureDef.isSensor = true;
            fixtureDef.shape = shape;

            physics.getBody().createFixture(fixtureDef);
            return this;
        }

        public EntityBuilder rangeSensor (float range, float arc) {
            Body body;
            if (Components.PHYSICS.has(entity)) {
                body = Components.PHYSICS.get(entity).getBody();
            } else {
                Gdx.app.error("Entity Factory", "can not add range sensor : entity does not have a physics component!");
                return this;
            }

            Vector2 vertices[] = new Vector2[8];

            for (int i = 0; i <= 7; i++) {
                vertices[i] = new Vector2(0, 0);
            }

            for (int i = 0; i < 7; i++) {
                float angle = (i / 6.0f * arc * MathUtils.degRad) - (90 * MathUtils.degRad);
                vertices[i + 1].set(range * MathUtils.cos(angle), range * MathUtils.sin(angle));
            }

            PolygonShape poly = new PolygonShape();
            poly.set(vertices);

            FixtureDef sensorDef = new FixtureDef();
            sensorDef.shape = poly;
            sensorDef.isSensor = true;
            body.createFixture(sensorDef);
            poly.dispose();
            return this;
        }*/

        public Entity addToEngine () {
            engine.addEntity(entity);
            return entity;
        }

        public Entity getWithoutAdding () {
            return entity;
        }
    }


    /**
     * Physics Builder and Fixture Builder
     */
    public static class PhysicsBuilder {
        private Body body;

        public PhysicsBuilder reset (BodyType type, Vector2 position, Entity entity) {
            BodyDef def = new BodyDef();
            def.type = type;
            def.position.set(position.scl(MPP));
            body = physicsSystem.createBody(def);
            body.setUserData(entity);
            entity.add(engine.createComponent(PhysicsComponent.class).init(body));
            return this;
        }

        public FixtureBuilder addFixture (int material) {
            return fixtureBuilder.reset(body, material);
        }

        public EntityBuilder getBody () {
            return builder;
        }

        public static class FixtureBuilder {
            private Body body;
            private FixtureDef def = new FixtureDef();

            public FixtureBuilder reset (Body body, int material) {
                this.body = body;
                def = new FixtureDef();
                setMaterial(material);
                return this;
            }

            private void setMaterial(int material) {
                switch(material){
                    case 0:
                        //STEEL
                        def.density = 1f;
                        def.friction = 0.3f;
                        def.restitution = 0.1f;
                        break;
                    case 1:
                        //WOOD
                        def.density = 0.5f;
                        def.friction = 0.7f;
                        def.restitution = 0.3f;
                        break;
                    case 2:
                        //RUBBER
                        def.density = 1f;
                        def.friction = 0f;
                        def.restitution = 1f;
                        break;
                    case 3:
                        //STONE
                        def.density = 1f;
                        def.friction = 0.9f;
                        def.restitution = 0.01f;
                    default:
                        def.density = 7f;
                        def.friction = 0.5f;
                        def.restitution = 0.3f;
                }
            }

            public FixtureBuilder circle (float radius) {
                CircleShape circle = new CircleShape();
                circle.setRadius(radius);
                def.shape = circle;
                return this;
            }

            public FixtureBuilder polyChain (float[] polygon) {
                ChainShape poly = new ChainShape();
                poly.createLoop(polygon);
                def.shape = poly;
                return this;
            }

            public FixtureBuilder bodyLoader (BodyEditorLoader loader, String name, float scale) {
                loader.attachFixture(body, name, def, scale);
                return this;
            }

            public PhysicsBuilder create () {
                body.createFixture(def);
                return physicsBuilder;
            }

        }

    }
}
