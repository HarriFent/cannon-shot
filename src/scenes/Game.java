package src.scenes;

import org.json.JSONArray;
import org.json.JSONException;
import src.Framework;
import src.ImageHandler;
import src.JSONFileHandler;
import src.Ship;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.BufferedImage;

/**
 * Game class that does most of the mechanics.
 * @author Harrison Fenton-Fearn
 */

public class Game extends Scene
{

    BufferedImage bgImage;
    Ship ship;

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
        ship = new Ship();
    }

    @Override
    protected void LoadContent()
    {
        BufferedImage grass = ImageHandler.getImage("textureGrass");
        BufferedImage sea = ImageHandler.getImage("textureSea");

        bgImage = new BufferedImage(2000,2000, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = bgImage.createGraphics();
        JSONFileHandler levelFileHandler = new JSONFileHandler("world1");
        try {
           int levelSize = levelFileHandler.getObject().getInt("size");
           JSONArray levelData = (JSONArray) levelFileHandler.getObject().get("levelData");
           for (int i = 0; i < levelSize; i++){
               for (int j = 0; j < levelSize; j++){
                   switch (levelData.getJSONArray(i).getInt(j)) {
                       case 0:
                           //Water
                           g2d.drawImage(sea,j*100,i*100,null);
                           break;
                       case 1:
                           //Land
                           g2d.drawImage(grass,j*100,i*100,null);
                           break;
                       default:
                           break;
                   };
               }
           }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        g2d.dispose();
    }

    @Override
    public void Update(long gameTime, Point mousePosition)
    {
        ship.Update(gameTime,mousePosition);
    }

    @Override
    public void Draw(Graphics2D g2d, Point mousePosition)
    {
        moveCamera(g2d);
        g2d.drawImage(bgImage,0,0,null);
        ship.Draw(g2d,mousePosition);
    }

    @Override
    public void Reset()
    {

    }

    private void moveCamera(Graphics2D g2d) {
        final int offsetX = 512;
        final int offsetY = 384;
        g2d.translate(offsetX-ship.getCenterX(),offsetY-ship.getCenterY());
    }

}
