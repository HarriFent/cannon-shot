package src;

import java.awt.*;
import java.util.HashMap;

public abstract class Sprite {

    protected int x, y, w, h;
    protected float angle;
    protected HashMap<String, Animation> animations = new HashMap<String, Animation>();
    protected Animation currentAni;

    public void addAnimation(String key, Animation ani) {
        animations.put(key,ani);
    }

    public void setAnimation(String key) {
        currentAni = animations.get(key);
    }

    protected abstract void Initialize();

    protected abstract void LoadContent();

    public abstract void Update(long gameTime, Point mousePosition);

    public abstract void Draw(Graphics2D g2d, Point mousePosition);

    public int getCenterX() { return x + (w/2); };
    public int getCenterY() { return y + (h/2); };
}
