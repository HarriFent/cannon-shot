package src;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.geom.AffineTransform;

public class Ship extends Sprite {

    public Ship() {
        Initialize();
        LoadContent();
    }

    @Override
    protected void Initialize() {
        Animation ani = new Animation(ImageHandler.getImage("ship"),68,115,2,600,true, 0, 0,0);
        addAnimation("ship", ani);
        setAnimation("ship");
        x = 400;
        y = 400;
        w = 67;
        h = 115;
    }

    @Override
    protected void LoadContent() {

    }

    @Override
    public void Update(long gameTime, Point mousePosition) {
        if (Framework.keyboardKeyState(KeyEvent.VK_W)) {
            //Forward

        } else if (Framework.keyboardKeyState(KeyEvent.VK_S)) {
            //Back

        } else if (Framework.keyboardKeyState(KeyEvent.VK_D)) {
            //Clockwise
        } else if (Framework.keyboardKeyState(KeyEvent.VK_A)) {
            //Anticlockwise
        }
    }

    @Override
    public void Draw(Graphics2D g2d, Point mousePosition) {
        g2d.transform(AffineTransform.getTranslateInstance(x,y));
        g2d.transform(AffineTransform.getRotateInstance(Math.toRadians(45)));
        currentAni.Draw(g2d);
    }
}
