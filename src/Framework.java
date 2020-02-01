package src;

import src.scenes.Game;
import src.scenes.Scene;

import java.awt.Graphics2D;
import java.awt.Point;
import java.util.HashMap;

/**
 * @author Harrison Fenton-Fearn
 * Framework that controls the game (Game.java) that created it, update it and draw it on the screen.
 */

public class Framework extends Canvas
{

    //Width of the frame.
    public static int frameWidth;
    //Height of the frame.
    public static int frameHeight;

    public static final long secInNanosec = 1000000000L;

    public static final long milisecInNanosec = 1000000L;

    private final int GAME_FPS = 60;

    //The amount of time to pause to ensure it stays at set FPS
    private final long GAME_UPDATE_PERIOD = secInNanosec / GAME_FPS;

    public static enum GameState{STARTING, VISUALIZING, GAME_CONTENT_LOADING, MAIN_MENU, OPTIONS, PLAYING, GAMEOVER, DESTROYED}

    //Publicly accessible game state
    public static GameState gameState;

    private long gameTime;
    //Used for calculating elapsed time.
    private long lastTime;

    private HashMap<String, Scene> scenes = new HashMap<String, Scene>();

    public Framework ()
    {
        super();
        
        gameState = GameState.VISUALIZING;
        
        //We start game in new thread.
        Thread gameThread = new Thread() {
            @Override
            public void run(){
                GameLoop();
            }
        };
        gameThread.start();
    }
    
    
   /**
     * Declare variables and objects.
     * This method is intended to declare the variables and objects for this class (not the game).
     */
    private void Initialize()
    {
        scenes.put("Menu", new MainMenu(this));
    }
    
    /**
     * Load files - images, sounds, ...
     * This method is intended to load files for this class (not the game).
     */
    private void LoadContent()
    {
    
    }
    
    
    /**
     * The main game loop making sure there are a set number of updates per second.
     */
    private void GameLoop()
    {
        // These two variables are used in VISUALIZING state of the game to make sure the size is set properly on all Operating Systems.
        long visualizingTime = 0, lastVisualizingTime = System.nanoTime();
        
        // These variables are used for calculating the time that defines for how long we should put the thread to sleep to meet the GAME_FPS.
        long beginTime, timeTaken, timeLeft;
        
        while(true)
        {
            beginTime = System.nanoTime();
            
            switch (gameState)
            {
                case PLAYING:
                    gameTime += System.nanoTime() - lastTime;

                    scenes.get("Game").Update(gameTime, mousePosition());
                    
                    lastTime = System.nanoTime();
                break;
                case GAMEOVER:
                    //...
                break;
                case MAIN_MENU:
                    //...
                    scenes.get("Menu").Update(gameTime, mousePosition());
                break;
                case OPTIONS:
                    //...
                break;
                case GAME_CONTENT_LOADING:
                    //...
                break;
                case STARTING:
                    // Declare, then load, then go to the main menu.
                    Initialize();
                    LoadContent();
                    gameState = GameState.MAIN_MENU;
                break;
                case VISUALIZING:
                    // On Linux this.getWidth() method doesn't return the correct value immediately.
                    // So we wait one second for the window/frame to be set to its correct size.
                    if(this.getWidth() > 1 && visualizingTime > secInNanosec)
                    {
                        frameWidth = this.getWidth();
                        frameHeight = this.getHeight();

                        // When the size is correct we change the game state.
                        gameState = GameState.STARTING;
                    }
                    else
                    {
                        visualizingTime += System.nanoTime() - lastVisualizingTime;
                        lastVisualizingTime = System.nanoTime();
                    }
                break;
            }
            
            // Repaint the screen.
            repaint();
            
            // Calculate the thread sleep time to enforce a set FPS
            timeTaken = System.nanoTime() - beginTime;
            timeLeft = (GAME_UPDATE_PERIOD - timeTaken) / milisecInNanosec; // In milliseconds
            // Set a minimum time (un-noticeable) to make the thread sleep so other threads can run.
            if (timeLeft < 10) 
                timeLeft = 10;
            try {
                 //Provides the necessary delay and allows other threads to run.
                 Thread.sleep(timeLeft);
            } catch (InterruptedException ex) { }
        }
    }
    
    /**
     * Draw the game to the screen. It is called through the repaint() method in the game loop.
     */
    @Override
    public void Draw(Graphics2D g2d)
    {
        switch (gameState)
        {
            case PLAYING:
                scenes.get("Game").Draw(g2d, mousePosition());
            break;
            case GAMEOVER:
                //...
            break;
            case MAIN_MENU:
                scenes.get("Menu").Draw(g2d, mousePosition());
            break;
            case OPTIONS:
                //...
            break;
            case GAME_CONTENT_LOADING:
                //...
            break;
        }
    }

    public void newGame()
    {
        // We set the gameTime to 0 and the lastTime to current time for later calculations.
        gameTime = 0;
        lastTime = System.nanoTime();

        if (scenes.get("Game") != null)
            scenes.remove("Game");

        scenes.put("Game", new Game());
    }

    public void restartGame()
    {
        // We set gameTime to zero and lastTime to current time for later calculations.
        gameTime = 0;
        lastTime = System.nanoTime();
        
        scenes.get("Game").Reset();
        
        // We change game status so that the game can start.
        gameState = GameState.PLAYING;
    }
    
    
    /**
     * Returns the position of the mouse pointer in game frame/window.
     * @return Point of mouse coordinates.
     */
    private Point mousePosition()
    {
        try
        {
            Point mp = this.getMousePosition();
            
            if(mp != null)
                return this.getMousePosition();
            else
                return new Point(0, 0);
        }
        catch (Exception e)
        {
            return new Point(0, 0);
        }
    }
}
