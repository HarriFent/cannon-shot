package src;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

/**
 * Class for basic animations.
 * @author Harrison Fenton-Fearn
 */

public class Animation {

    private BufferedImage animImage;

    private int frameWidth;
    private int frameHeight;

    private int numberOfFrames;

    // Amount of time between frames in milliseconds.
    private long frameTime;

    // Used to calculate the time between each frame
    private long startingFrameTime;
    private long timeForNextFrame;
    private int currentFrameNumber;

    private boolean loop;

    // Position on the screen
    public int x;
    public int y;

    // Starting x coordinate of the current frame in the animation image.
    private int startingXOfFrameInImage;

    // Ending x coordinate of the current frame in the animation image.
    private int endingXOfFrameInImage;

    // Used to dispose the animation after playing.
    public boolean active;
    
    // Delay to start the animation
    private long showDelay;
    
    // At what time was animation created.
    private long timeOfAnimationCreation;


    /**
     * Creates animation.
     * 
     * @param animImage Image of animation. Image should be 1 frame in height.
     * @param frameWidth Width of the frame.
     * @param frameHeight Height of the frame
     * @param numberOfFrames Number of frames in the animation.
     * @param frameTime Amount of time that each frame will be shown before moving to the next one in milliseconds.
     * @param loop Will the animation loop.
     * @param x x coordinate.
     * @param y y coordinate.
     * @param showDelay In milliseconds. The delay before starting the animation.
     */
    public Animation(BufferedImage animImage, int frameWidth, int frameHeight, int numberOfFrames, long frameTime, boolean loop, int x, int y, long showDelay)
    {
        this.animImage = animImage;
        this.frameWidth = frameWidth;
        this.frameHeight = frameHeight;
        this.numberOfFrames = numberOfFrames;
        this.frameTime = frameTime;
        this.loop = loop;

        this.x = x;
        this.y = y;
        
        this.showDelay = showDelay;
        
        timeOfAnimationCreation = System.currentTimeMillis();

        startingXOfFrameInImage = 0;
        endingXOfFrameInImage = frameWidth;

        startingFrameTime = System.currentTimeMillis() + showDelay;
        timeForNextFrame = startingFrameTime + this.frameTime;
        currentFrameNumber = 0;
        active = true;
    }


    /**
     * Set the position of the animation.
     * @param x x coordinate.
     * @param y y coordinate.
     */
    public void setPosition(int x, int y)
    {
        this.x = x;
        this.y = y;
    }


    /**
     * Checks if it's time to show the next frame of the animation and if the animation is finished.
     */
    private void Update()
    {
        if(timeForNextFrame <= System.currentTimeMillis())
        {
            currentFrameNumber++;

            // If the animation is finished set the current frame to 0.
            if(currentFrameNumber >= numberOfFrames)
            {
                currentFrameNumber = 0;

                // If the animation isn't looping then we set active to false.
                if(!loop)
                    active = false;
            }

            // Starting and ending coordinates for cutting the current frame image out of the animation image.
            startingXOfFrameInImage = currentFrameNumber * frameWidth;
            endingXOfFrameInImage = startingXOfFrameInImage + frameWidth;

            // Set time for the next frame.
            startingFrameTime = System.currentTimeMillis();
            timeForNextFrame = startingFrameTime + frameTime;
        }
    }

    /**
     * Draws current frame of the animation.
     * 
     * @param g2d Graphics2D
     */
    public void Draw(Graphics2D g2d)
    {
        this.Update();
        
        // Checks if show delay is over.
        if(this.timeOfAnimationCreation + this.showDelay <= System.currentTimeMillis())
            g2d.drawImage(animImage, x, y, x + frameWidth, y + frameHeight, startingXOfFrameInImage, 0, endingXOfFrameInImage, frameHeight, null);
    }
}