package src.scenes;

import java.awt.*;

public abstract class Scene
{

    /**
     * Set variables and objects for the game.
     */
    protected abstract void Initialize();

    /**
     * Load game files - images, sounds, ...
     */
    protected abstract void LoadContent();

    /**
     * Update game logic.
     *
     * @param gameTime gameTime of the game.
     * @param mousePosition current mouse position.
     */
    public abstract void Update(long gameTime, Point mousePosition);

    /**
     * Draw the game to the screen.
     *
     * @param g2d Graphics2D
     * @param mousePosition current mouse position.
     */
    public abstract void Draw(Graphics2D g2d, Point mousePosition);

    /**
     * Restart game - reset some variables.
     */
    public abstract void Reset();

}
