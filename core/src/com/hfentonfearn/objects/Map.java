package com.hfentonfearn.objects;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.hfentonfearn.components.TextureComponent;
import com.hfentonfearn.components.TransformComponent;
import com.hfentonfearn.helpers.AssetLoader;
import com.hfentonfearn.helpers.MappersHandler;

public class Map extends Entity {

    public Map() {
        this.add(new TextureComponent(AssetLoader.bgSea));
        this.add(new TransformComponent());

        TextureRegion tr = MappersHandler.texture.get(this).region;
        tr.setRegionHeight(3000);
        tr.setRegionWidth(3000);
    }
}
