package com.hfentonfearn.ecs;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.hfentonfearn.components.PhysicsComponent;
import com.hfentonfearn.components.PlayerComponent;
import com.hfentonfearn.components.SpriteComponent;
import com.hfentonfearn.components.VelocityComponent;
import com.hfentonfearn.entitysystems.PhysicsSystem;
import com.hfentonfearn.utils.AssetLoader;

import static com.hfentonfearn.ecs.EntityFactory.PhysicsBuilder.FixtureBuilder;
import static com.hfentonfearn.utils.Constants.MPP;

public class EntityFactory {

    private static PooledEngine engine;
    private static PhysicsSystem physicsSystem;
    private static EntityBuilder builder = new EntityBuilder();

    private static PhysicsBuilder physicsBuilder = new PhysicsBuilder();
    private static FixtureBuilder fixtureBuilder = new FixtureBuilder();

    public static void setEngine (PooledEngine engine) {
        EntityFactory.engine = engine;
        physicsSystem = engine.getSystem(PhysicsSystem.class);
    }

    public static Entity createPlayer(Vector2 position) {
        TextureRegion textureRegion = AssetLoader.ship.playerShip;
        float[] polygon = AssetLoader.ship.collisionPoly;
        Polygon p = new Polygon(polygon);
        p.translate(-textureRegion.getRegionWidth()/2 * MPP,-textureRegion.getRegionHeight()/2 * MPP);
        p.setScale(MPP,MPP);
        Entity entity = builder.createEntity(position)
                .physicsBody(BodyDef.BodyType.DynamicBody)
                .polyCollider(p.getTransformedVertices(),1f)
                .sprite(AssetLoader.ship.playerShip)
                .damping(1,0.2f)
                .velocity(0,0)
                .addToEngine();
        entity.add(new PlayerComponent());
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

        public PhysicsBuilder buildPhysics (BodyDef.BodyType type) {
            return physicsBuilder.reset(type, position, entity);
        }

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

        public EntityBuilder velocity (float angular, float linear) {
            VelocityComponent velocity = new VelocityComponent();
            velocity.angularVelocity = angular;
            velocity.linearVelocity = linear;
            entity.add(velocity);
            return this;
        }

        public EntityBuilder polyCollider (float[] polygon, float density) {
            PolygonShape poly = new PolygonShape();
            poly.set(polygon);
            PhysicsComponent physics = Components.PHYSICS.get(entity);
            if (physics == null) {
                physicsBody(DEFAULT_BODY);
            }

            physics.getBody().createFixture(poly, density);
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
        }

        public EntityBuilder sprite (TextureRegion region) {
            SpriteComponent spriteComp = engine.createComponent(SpriteComponent.class).init(region, position.x, position.y, region.getRegionWidth(),
                    region.getRegionHeight());
            entity.add(spriteComp);
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

    public static class PhysicsBuilder {
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

    }
}