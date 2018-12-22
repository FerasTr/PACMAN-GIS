package GameObjects;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * This class is used for choosing the playground.
 */
final public class MapInit
{
    private MapInit()
    {
    }

    public static Map ArielMap()
    {
        double top_left_x = 32.105689;
        double top_left_y = 35.202411;
        double bot_right_x = 32.101931;
        double bot_right_y = 35.21239;

        BufferedImage ariel = null;

        try
        {
            ariel = ImageIO.read(new File("./Resources/maps/Ariel1.png"));
        }
        catch (IOException e)
        {
            System.out.println(e);
        }

        Range arielRange = new Range(top_left_x, top_left_y, bot_right_x, bot_right_y);
        return new Map(ariel, arielRange);
    }


}