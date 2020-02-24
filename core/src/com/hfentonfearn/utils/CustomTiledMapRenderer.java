package com.hfentonfearn.utils;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapGroupLayer;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapLayers;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapImageLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.scenes.scene2d.utils.TiledDrawable;

public class CustomTiledMapRenderer extends OrthogonalTiledMapRenderer {

    private int movingTileCount;

    public CustomTiledMapRenderer(TiledMap map, SpriteBatch batch) {
        super(map, batch);
        movingTileCount = 0;
    }

    @Override
    protected void renderMapLayer (MapLayer layer) {
        if (!layer.isVisible()) return;
        if (layer instanceof MapGroupLayer) {
            MapLayers childLayers = ((MapGroupLayer)layer).getLayers();
            for (int i = 0; i < childLayers.size(); i++) {
                MapLayer childLayer = childLayers.get(i);
                if (!childLayer.isVisible()) continue;
                renderMapLayer(childLayer);
            }
        } else {
            if (layer instanceof TiledMapTileLayer) {
                if (layer.getProperties().containsKey("repeating")) {
                    if (layer.getProperties().get("repeating", Boolean.class)) {
                        renderRepeatingLayer((TiledMapTileLayer) layer);
                    } else {
                        renderTileLayer((TiledMapTileLayer) layer);
                    }
                } else {
                    renderTileLayer((TiledMapTileLayer) layer);
                }
            } else if (layer instanceof TiledMapImageLayer) {
                renderImageLayer((TiledMapImageLayer)layer);
            } else {
                renderObjects(layer);
            }
        }
    }

    private void renderRepeatingLayer(TiledMapTileLayer layer) {
        int tileWidth = (int) layer.getTileWidth();
        int tileHeight = (int) layer.getTileHeight();
        float offsetX = 0;
        float offsetY = 0;
        if (isLayerMoving(layer)) {
            offsetX = getMovingVelocityX(layer);
            offsetY = getMovingVelocityY(layer);
            movingTileCount++;
        }

        final TiledMapTileLayer.Cell cell = layer.getCell(0, 0);
        final TiledMapTile tile = cell.getTile();

        TiledDrawable tiledDrawable = new TiledDrawable(tile.getTextureRegion());
        tiledDrawable.draw(batch, offsetX, offsetY,layer.getWidth() * tileWidth, layer.getHeight() * tileHeight);
    }

    private boolean isLayerMoving(TiledMapTileLayer layer) {
        if (layer.getProperties().containsKey("moving")) {
            return layer.getProperties().get("moving", Boolean.class);
        }
        return false;
    }
    private float getMovingVelocityX(TiledMapTileLayer layer) {
        int tileWidth = (int) layer.getTileWidth();
        float vel = layer.getProperties().get("movingVelocityX", Float.class);
        if (movingTileCount * vel >= tileWidth)
            movingTileCount = 0;
        return movingTileCount * vel;
    }
    private float getMovingVelocityY(TiledMapTileLayer layer) {
        int tileHeight = (int) layer.getTileHeight();
        float vel = layer.getProperties().get("movingVelocityY", Float.class);
        if (movingTileCount * vel >= tileHeight)
            movingTileCount = 0;
        return movingTileCount * vel;
    }
}
