package com.hfentonfearn.helpers;

import com.badlogic.ashley.core.ComponentMapper;
import com.hfentonfearn.components.AccelerationComponent;
import com.hfentonfearn.components.TextureComponent;
import com.hfentonfearn.components.TransformComponent;
import com.hfentonfearn.components.VelocityComponent;

public class MappersHandler {

    public static final ComponentMapper<TransformComponent> transform = ComponentMapper.getFor(TransformComponent.class);
    public static final ComponentMapper<VelocityComponent> velocity = ComponentMapper.getFor(VelocityComponent.class);
    public static final ComponentMapper<AccelerationComponent> acceleration = ComponentMapper.getFor(AccelerationComponent.class);
    public static final ComponentMapper<TextureComponent> texture = ComponentMapper.getFor(TextureComponent.class);

}
