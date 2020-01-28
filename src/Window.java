package src;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import java.awt.*;

/**
 * @author Harrison Fenton-Fearn
 * Blank basic game framework
 */

public class Window extends JFrame
{
    //Set whether it is full screen
    private final boolean FULLSCREEN = false;

    private Window()
    {
        // Sets the window title label
        this.setTitle("Game title");
        // Exit the application when user close frame.
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // Creates the instance of the Framework.java that extends the Canvas.java and puts it on the frame.
        this.setContentPane(new Framework());
        // Sets size of the frame.
        if(FULLSCREEN) //Full screen mode
        {
            // Disables decorations for this frame.
            this.setUndecorated(true);
            // Puts the frame to full screen.
            this.setExtendedState(this.MAXIMIZED_BOTH);
        }
        else // Window mode
        {
            // Size of the frame.
            this.setPreferredSize(new Dimension(1024, 768));
            //Resize the window to fit around the content
            this.pack();
            // Puts frame to center of the screen.
            this.setLocationRelativeTo(null);
            // So that frame cannot be resizable by the user.
            this.setResizable(false);
        }
        
        this.setVisible(true);
    }

    public static void main(String[] args)
    {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Window();
            }
        });
    }
}
