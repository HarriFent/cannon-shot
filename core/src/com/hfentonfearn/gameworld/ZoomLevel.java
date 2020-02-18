package com.hfentonfearn.gameworld;

public class ZoomLevel {

    private static final float ZOOM_CLOSE = 0.1f;
    private static final float ZOOM_FAR = 1.0f;
    private static final float ZOOM_MAP = 2.0f;

    private ZoomLevelEnum zoomLevel;
    private float currentZoom;
    private boolean zoomingIn;
    private boolean zoomingOut;

    public ZoomLevel(ZoomLevelEnum zoomLevel) {
        this.zoomLevel = zoomLevel;
        zoomingIn = zoomingOut = false;
    }

    public ZoomLevelEnum getZoomLevel() {
        return zoomLevel;
    }

    public void setZoomLevel(ZoomLevelEnum zoomLevel) {
        this.zoomLevel = zoomLevel;
    }

    public float getZoomValue() {
        switch (zoomLevel) {
            case CLOSE:
                return ZOOM_CLOSE;
            case FAR:
                return ZOOM_FAR;
            case MAP:
                return ZOOM_MAP;
        }
        return 0f;
    }

    @Override
    public String toString() {
        return zoomLevel.toString();
    }

    public float getCurrentZoom() {
        return currentZoom;
    }

    public void setCurrentZoom(float currentZoom) {
        this.currentZoom = currentZoom;
    }

    public void update(float deltaTime) {
        if (currentZoom > getZoomValue() + 0.01) {
            currentZoom -= deltaTime;
            zoomingIn = true;
        } else if (currentZoom < getZoomValue() - 0.01) {
            currentZoom += deltaTime;
            zoomingOut = true;
        } else {
            currentZoom = getZoomValue();
            zoomingOut = zoomingIn = false;
        }
    }

    public boolean isZoomingIn() {
        return zoomingIn;
    }

    public boolean isZoomingOut() {
        return zoomingOut;
    }

    public enum ZoomLevelEnum {
        CLOSE,
        FAR,
        MAP
    }
}
