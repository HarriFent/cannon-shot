package src;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ImageHandler {

    private final static String baseImgURL = "/resources/images/";

    public static BufferedImage getImage(String imgName)
    {
        if (!imgName.endsWith(".png"))
            imgName = baseImgURL + imgName + ".png";

        try
        {
            URL url = ImageHandler.class.getResource(imgName);
            if (url == null) {
                System.out.println("Cannot find URL from image name: \"" + imgName + "\"");
                return null;
            }
            return ImageIO.read(url);
        }
        catch (IOException ex)
        {
            Logger.getLogger(Framework.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
}
