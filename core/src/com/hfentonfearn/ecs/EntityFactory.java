package com.hfentonfearn.ecs;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.hfentonfearn.components.*;
import com.hfentonfearn.entitysystems.PhysicsSystem;
import com.hfentonfearn.entitysystems.ZoomSystem;
import com.hfentonfearn.utils.AssetLoader;
import com.hfentonfearn.utils.BodyEditorLoader;
import com.hfentonfearn.utils.Components;

import static com.hfentonfearn.entitysystems.ZoomSystem.ZOOM_FAR;
import static com.hfentonfearn.entitysystems.ZoomSystem.ZOOM_MAP;
import static com.hfentonfearn.utils.Constants.*;

public class EntityFactory {

    private static PooledEngine engine;
    private static PhysicsSystem physicsSystem;
    private static EntityBuilder builder = new EntityBuilder();

//    private static PhysicsBuilder physicsBuilder = new PhysicsBuilder();
//    private static FixtureBuilder fixtureBuilder = new FixtureBuilder();

    public static void setEngine (PooledEngine engine) {
        EntityFactory.engine = engine;
        physicsSystem = engine.getSystem(PhysicsSystem.class);
    }

    public static void createPlayer(Vector2 position) {
        AssetLoader.playerShip.loadLoader();
        Entity entity = builder.createEntity(position)
                .physicsBody(BodyDef.BodyType.DynamicBody)
                .bodyLoader(AssetLoader.playerShip.loader,1)
                .damping(DAMPING_ANGULAR,DAMPING_LINEAR)
                .sprite(AssetLoader.playerShip.ship)
                .sprite(AssetLoader.playerShip.sail)
                .type(TypeComponent.PLAYER)
                .drawDistance(ZOOM_FAR)
                .getWithoutAdding();
        entity.add(new PlayerComponent());
        entity.add(new VelocityComponent());
        engine.addEntity(entity);
    }

    public static void createLandMass(Polygon poly) {
        poly.setPosition(poly.getX()*MPP,poly.getY()*MPP);
        poly.setScale(MPP,MPP);
        Entity entity = builder.createEntity(new Vector2(0,0))
                .physicsBody(BodyDef.BodyType.StaticBody)
                .chainPolyCollider(poly.getTransformedVertices(),1f)
                .type(TypeComponent.LAND)
                .addToEngine();
    }

    public static void createRocks(Polygon poly) {
        poly.setPosition(poly.getX()*MPP,poly.getY()*MPP);
        poly.setScale(MPP,MPP);
        Entity entity = builder.createEntity(new Vector2(0,0))
                .physicsBody(BodyDef.BodyType.StaticBody)
                .chainPolyCollider(poly.getTransformedVertices(),1f)
                .type(TypeComponent.SCENERY)
                .addToEngine();
    }

    public static Entity createCloud(Vector2 position, Vector2 movement) {
        Sprite cloudSprite = new Sprite(AssetLoader.clouds.getRandomCloud());
        cloudSprite.setCenter(position.x,position.y);
        cloudSprite.setScale(3);
        Entity entity = builder.createEntity(position)
                .sprite(cloudSprite)
                .type(TypeComponent.CLOUD)
                .staticMovement(movement)
                .drawDistance(ZOOM_MAP)
                .addToEngine();
        return entity;
    }

    public static Entity createCannonBall(Vector2 position, Vector2 linearVel) {
        Entity entity = builder.createEntity(position)
                .sprite(AssetLoader.projectiles.cannonBall)
                .physicsBody(BodyDef.BodyType.DynamicBody)
                .circleCollider(0.05f,3f)
                .velocity(linearVel)
                .damping(0f, CANNONBALL_DAMPING)
                .type(TypeComponent.CANNONBALL)
                .drawDistance(ZOOM_FAR)
                .addToEngine();
        return entity;
    }

    public static Entity createCannonBallSplash(Vector2 position) {
        Entity entity = builder.createEntity(position)
                .animation(AssetLoader.effects.cannonSplash)
                .physicsBody(BodyDef.BodyType.DynamicBody)
                .drawDistance(ZOOM_FAR)
                .addToEngine();
        return entity;
    }

    /**
     *   Entity Builder Class
     * */
    public static class EntityBuilder {
        private static final BodyDef.BodyType DEFAULT_BODY = BodyDef.BodyType.DynamicBody;

        public Vector2 position;
        public Entity entity;

        public EntityBuilder createEntity (Vector2 position) {
            entity = engine.createEntity();
            this.position = position;
            return this;
        }

        /*public PhysicsBuilder buildPhysics (BodyDef.BodyType type) {
            return physicsBuilder.reset(type, position, entity);
        }*/

        public EntityBuilder physicsBody (BodyDef.BodyType type) {
            BodyDef def = new BodyDef();
            def.type = type;
            def.position.set(position.scl(MPP));
            Body body = physicsSystem.createBody(def);
            body.setUserData(entity);

            PhysicsComponent physics = engine.createComponent(PhysicsComponent.class).init(body);
            entity.add(physics);
            return this;
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

        public EntityBuilder velocity (Vector2 linear) {
            if (Components.PHYSICS.has(entity)) {
                PhysicsComponent physics = Components.PHYSICS.get(entity);
                physics.getBody().setLinearVelocity(linear);
            }
            return this;
        }

        public EntityBuilder polyCollider(float[] polygon, float density) {
            PolygonShape poly = new PolygonShape();
            poly.set(polygon);
            PhysicsComponent physics = Components.PHYSICS.get(entity);
            if (physics == null) {
                physicsBody(DEFAULT_BODY);
            }

            physics.getBody().createFixture(poly, density);
            return this;
        }

        public EntityBuilder chainPolyCollider (float[] polygon, float density) {
            ChainShape poly = new ChainShape();
            poly.createLoop(polygon);
            PhysicsComponent physics = Components.PHYSICS.get(entity);
            if (physics == null) {
                physicsBody(DEFAULT_BODY);
            }

            physics.getBody().createFixture(poly, density);
            return this;
        }

        public EntityBuilder bodyLoader (BodyEditorLoader loader, float density) {
            FixtureDef fd = new FixtureDef();
            fd.density = density;
            loader.attachFixture(Components.PHYSICS.get(entity).getBody(), "playership", fd, 0.5f);
            return this;
        }

        public EntityBuilder type (String typeString) {
            TypeComponent type = new TypeComponent(typeString);
            entity.add(type);
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

        public EntityBuilder animation (Animation<TextureRegion> animation) {
            entity.add(new AnimationComponent(animation));
            return this;
        }

        public EntityBuilder staticMovement(Vector2 movement) {
            entity.add(new StaticMovementComponent(movement));
            return this;
        }

        public EntityBuilder circleCollider (float radius, float density) {
            CircleShape shape = new CircleShape();
            shape.setRadius(radius);
            PhysicsComponent physics = Components.PHYSICS.get(entity);
            if (physics == null) {
                physicsBody(DEFAULT_BODY);
            }

            physics.getBody().createFixture(shape, density);
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
    /*public static class PhysicsBuilder {
        private Body body;

        public PhysicsBuilder reset (BodyDef.BodyType type, Vector2 position, Entity entity) {
            BodyDef def = new BodyDef();
            def.type = type;
            def.position.set(position);
            body = physicsSystem.createBody(def);
            body.setUserData(entity);
            return this;
        }

        public FixtureBuilder addFixture () {
            return fixtureBuilder.reset(body);
        }

        public EntityBuilder getBody () {
            return builder;
        }

        public static class FixtureBuilder {
            private Body body;
            private FixtureDef def = new FixtureDef();

            public FixtureBuilder reset (Body body) {
                this.body = body;
                def = new FixtureDef();
                return this;
            }

            public FixtureBuilder circle (float radius) {
                CircleShape circle = new CircleShape();
                circle.setRadius(radius);
                def.shape = circle;
                return this;
            }

            public PhysicsBuilder create () {
                body.createFixture(def);
                return physicsBuilder;
            }

        }

    }*/
}
