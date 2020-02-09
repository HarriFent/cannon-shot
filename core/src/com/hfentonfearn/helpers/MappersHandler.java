package com.hfentonfearn.helpers;

import com.badlogic.ashley.core.ComponentMapper;
import com.hfentonfearn.components.*;

public class MappersHandler {

    public static final ComponentMapper<TransformComponent> transform = ComponentMapper.getFor(TransformComponent.class);
    public static final ComponentMapper<TextureComponent> texture = ComponentMapper.getFor(TextureComponent.class);
    public static final ComponentMapper<PhysicsComponent> physics = ComponentMapper.getFor(PhysicsComponent.class);
    public static final ComponentMapper<DriveComponent> drive = ComponentMapper.getFor(DriveComponent.class);

}
