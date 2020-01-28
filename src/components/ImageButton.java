package src.components;

import src.Canvas;
import src.ImageHandler;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

public abstract class ImageButton
{
    private static final int BUTTON_IDLE = 0;
    private static final int BUTTON_OVER = 1;
    private static final int BUTTON_DOWN = 2;

    private BufferedImage[] img = new BufferedImage[3];

    private int x, y, w, h;
    private boolean isDown;
    private boolean enabled = true;

    public ImageButton(int x, int y, String imgName)
    {
        for (int i = 0; i < 3; i++)
            img[i] = ImageHandler.getImage(imgName + i);
        this.x = x;
        this.y = y;
        this.w = img[0].getWidth();
        this.h = img[0].getHeight();
    }

    //To check is a point is within the buttons bounds
    private boolean inBounds(Point point)
    {
        if (point.x >= x && point.x <= x+w)
            if (point.y >= y && point.y <= y+h)
                return true;
        return false;
    }

    public void Update(Point mousePosition)
    {
        //If the left mouse button is pressed down and the click is on the button, call the click method
        if (Canvas.mouseButtonState(MouseEvent.BUTTON1))
        {
            if (inBounds(mousePosition) && !isDown && enabled)
            {
                onClick();
                // Boolean to make sure the onClick method is not fired continuously while the mouse button is held down
                isDown = true;
            }
        }
        else
        {
            isDown = false;
        }
    }

    public void Draw(Graphics2D g2d, Point mousePosition)
    {
        // Draw the correct button image depending on the mouse state
        BufferedImage displayImage = null;
        if (isDown)
        {
            //If the button is clicked down
            displayImage = img[BUTTON_DOWN];
        }
        else
        {
            if (inBounds(mousePosition) && enabled)
            {
                //If the mouse is hovering over the button
                displayImage = img[BUTTON_OVER];
            }
            else
            {
                //The normal idle state of the button
                displayImage = img[BUTTON_IDLE];
            }
        }
        //Draws the button image
        g2d.drawImage(displayImage, x, y, null);
    }

    protected abstract void onClick();

    /**
     * Used to enable or disable the button
     * @param enabled True to allow the button to be used
     */
    public void setEnabled(boolean enabled)
    {
        this.enabled = enabled;
    }
}
