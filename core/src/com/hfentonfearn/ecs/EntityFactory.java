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
import com.hfentonfearn.utils.AssetLoader;
import com.hfentonfearn.utils.BodyEditorLoader;
import com.hfentonfearn.utils.Components;

import static com.badlogic.gdx.physics.box2d.BodyDef.BodyType.DynamicBody;
import static com.badlogic.gdx.physics.box2d.BodyDef.BodyType.StaticBody;
import static com.hfentonfearn.ecs.EntityFactory.PhysicsBuilder.FixtureBuilder;
import static com.hfentonfearn.entitysystems.ParticleSystem.ParticleType;
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
    private static KillingBuilder killingBuilder = new KillingBuilder();
    private static ShipStatisticBuilder shipStatisticBuilder = new ShipStatisticBuilder();

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
                .shipStats().currency(500).create()
                .getWithoutAdding();
        entity.add(new PlayerComponent());
        PlayerComponent.player = entity;
        engine.addEntity(entity);
    }

    public static Entity createEnemyShip(Vector2 position, int hull) {
        AssetLoader.enemyShip.loadLoader();
        Entity entity = builder.createEntity(EntityCategory.ENEMY,position)
                .buildPhysics(DynamicBody).addFixture(MATERIAL_WOOD).bodyLoader(AssetLoader.enemyShip.loader, "enemyship", 0.65f).create().getBody()
                .damping(DAMPING_ANGULAR, DAMPING_LINEAR)
                .sprite(AssetLoader.enemyShip.ship)
                .shipStats().currency(0).hull(hull).cannonFire(200,6f).create()
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

    public static Entity createCannonBall(Vector2 position, Vector2 linearVel) {
        Entity entity = builder.createEntity(EntityCategory.CANNONBALL,position)
                .sprite(AssetLoader.projectiles.cannonBall)
                .buildPhysics(DynamicBody).addFixture(MATERIAL_STEEL).circle(0.05f).create().getBody()
                .setInitVelocity(linearVel)
                .damping(0f, CANNONBALL_DAMPING)
                .killable().create()
                .particle(ParticleType.CANNON_TRAIL,true, 0)
                .addToEngine();
        return entity;
    }

    public static Entity createCannonBallSplash(Vector2 position) {
        Entity entity = builder.createEntity(EntityCategory.EFFECT,position)
                .animation(AssetLoader.effects.cannonSplash, true)
                .buildPhysics(StaticBody).getBody()
                .addToEngine();
        return entity;
    }

    public static Entity createExplosion(Vector2 position, float scale) {
        Entity entity = builder.createEntity(EntityCategory.EFFECT,position)
                .buildPhysics(StaticBody).getBody()
                .animation(AssetLoader.effects.cannonExplosion, true, scale)
                .addToEngine();
        return entity;
    }

    public static Entity createParticle(Vector2 position, ParticleType type, float angle) {
        Entity entity = builder.createEntity(EntityCategory.EFFECT, position).particle(type, false, angle).addToEngine();
        return entity;
    }

    public static Entity createDyingShip(Vector2 position, int currency) {
        Entity entity = builder.createEntity(EntityCategory.DYINGSHIP,position)
                .buildPhysics(DynamicBody).addFixture(-1).circle(0.5f).isSensor().create().getBody()
                .sprite(AssetLoader.enemyShip.deadShip)
                .killable().killAfterDuration(10).explode(20).fade().create()
                .currency(currency)
                .damping(DAMPING_ANGULAR,DAMPING_LINEAR)
                .addToEngine();
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

        public EntityBuilder animation(Animation<TextureRegion> animation, boolean killAfterAnimation, float scale) {
            entity.add(engine.createComponent(AnimationComponent.class).init(animation, scale));
            if (killAfterAnimation)
                killable().killAfterAnimation().create();
            return this;
        }

        public EntityBuilder animation(Animation<TextureRegion> animation, boolean killAfterAnimation) {
            return animation(animation,killAfterAnimation,1f);
        }

        public EntityBuilder particle(ParticleType type, boolean follow, float angle) {
            PooledEffect effect = engine.getSystem(ParticleSystem.class).createEffect(position, type);
            entity.add(engine.createComponent(ParticleComponent.class).init(effect, follow, angle));
            return this;
        }

        public EntityBuilder acceleration() {
            entity.add(engine.createComponent(AccelerationComponent.class).init());
            return this;
        }

        public ShipStatisticBuilder shipStats() {
            return shipStatisticBuilder.reset(entity);
        }

        public KillingBuilder killable() {
            return killingBuilder.reset(entity);
        }

        public EntityBuilder currency(int currency) {
            entity.add(engine.createComponent(CurrencyComponent.class).init(currency));
            return this;
        }

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
            entity.add(engine.createComponent(CollisionComponent.class).init());
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

            public FixtureBuilder isSensor() {
                def.isSensor = true;
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

    private static class KillingBuilder {
        Entity entity;
        KillComponent component;

        public KillingBuilder reset (Entity entity) {
            component = engine.createComponent(KillComponent.class);
            this.entity = entity;
            return this;
        }

        public KillingBuilder killAfterAnimation() {
            component.afterAnimation = true;
            return this;
        }

        public KillingBuilder killAfterDuration(int duration) {
            component.timed = true;
            component.timer = component.starttime = duration * 60;
            return this;
        }

        public KillingBuilder fade() {
            component.fade = true;
            return this;
        }

        public KillingBuilder explode (float radius) {
            component.exploding = true;
            component.explodingRadius = radius;
            return this;
        }

        public EntityBuilder create() {
            entity.add(component);
            return builder;
        }
    }

    private static class ShipStatisticBuilder {
        private Entity entity;

        ShipStatisticComponent shipStats;

        public ShipStatisticBuilder reset (Entity entity) {
            this.entity = entity;
            entity.add(engine.createComponent(CannonFiringComponent.class));
            entity.add(engine.createComponent(InventoryComponent.class).init());
            entity.add(engine.createComponent(HealthComponent.class).init(DEFAULT_HULL));
            entity.add(engine.createComponent(KillComponent.class));
            shipStats = engine.createComponent(ShipStatisticComponent.class).init(DEFAULT_SPEED, DEFAULT_STEERING, DEFAULT_HULL, DEFAULT_FIRERATE, DEFAULT_FIRERANGE, DEFAULT_INVENTORY_SIZE);
            return this;
        }

        public ShipStatisticBuilder inventory(int inventorySize) {
            shipStats.setInventorySize(inventorySize);
            Components.INVENTORY.get(entity).setSize(inventorySize);
            return this;
        }

        public ShipStatisticBuilder currency(int currency) {
            entity.add(engine.createComponent(CurrencyComponent.class).init(currency));
            return this;
        }

        public ShipStatisticBuilder cannonFire(int firerate, float range) {
            shipStats.setFirerange(range);
            shipStats.setFirerate(firerate);
            return this;
        }

        public ShipStatisticBuilder hull(float hull) {
            Components.HEALTH.get(entity).value = hull;
            shipStats.setMaxHull(hull);
            return this;
        }

        public ShipStatisticBuilder speed(float speed) {
            shipStats.setSpeed(speed);
            return this;
        }

        public ShipStatisticBuilder steeringSpeed(float speed) {
            shipStats.setSteering(speed);
            return this;
        }

        public EntityBuilder create() {
            entity.add(shipStats);
            return builder;
        }
    }
}