package src.scenes;

import src.Framework;

import java.awt.Graphics2D;
import java.awt.Point;

/**
 * Game class that does most of the mechanics.
 * @author Harrison Fenton-Fearn
 */

public class Game extends Scene
{

    public Game()
    {
        Framework.gameState = Framework.GameState.GAME_CONTENT_LOADING;
        
        Thread threadForInitGame = new Thread() {
            @Override
            public void run(){
                Initialize();
                LoadContent();
                Framework.gameState = Framework.GameState.PLAYING;
            }
        };
        threadForInitGame.start();
    }

    @Override
    protected void Initialize()
    {

    }

    @Override
    protected void LoadContent()
    {

    }

    @Override
    public void Update(long gameTime, Point mousePosition)
    {

    }

    @Override
    public void Draw(Graphics2D g2d, Point mousePosition)
    {

    }

    @Override
    public void Reset()
    {

    }


}
