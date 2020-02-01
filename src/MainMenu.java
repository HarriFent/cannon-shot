package src;

import src.components.ImageButton;

import java.awt.*;
import java.awt.image.BufferedImage;

public class MainMenu extends src.scenes.Scene {

    private ImageButton btnPlay;

    private BufferedImage bgImage;

    private Framework framework;

    public MainMenu(Framework f) {
        framework = f;
        Initialize();
        LoadContent();
    }

    @Override
    protected void Initialize()
    {
        btnPlay = new ImageButton(100,500,"buttons/btnPlay") {
            @Override
            protected void onClick() {
                framework.newGame();
            }
        };
    }

    @Override
    protected void LoadContent()
    {
        bgImage = ImageHandler.getImage("bgMainMenu");
    }

    @Override
    public void Update(long gameTime, Point mousePosition)
    {
        btnPlay.Update(mousePosition);
    }

    @Override
    public void Draw(Graphics2D g2d, Point mousePosition)
    {
        g2d.drawImage(bgImage,0,0,null);
        btnPlay.Draw(g2d,mousePosition);
    }

    @Override
    public void Reset()
    {

    }
}
