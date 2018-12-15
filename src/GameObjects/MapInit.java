package GameObjects;

import GameObjects.Map;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;


final public class MapInit
{
    private MapInit()
    {
    }

    public Map ArielMap()
    {
        double top_right_x = 32.105444;
        double top_right_y = 35.212496;
        double bot_left_x = 32.105670;
        double bot_left_y = 35.212133;
        BufferedImage ariel = null;
        try
        {
            ariel = ImageIO.read(new File("maps/Ariel1.png"));
        }
        catch (IOException e)
        {
            System.out.println(e);
        }
        return new Map(ariel, top_right_x, top_right_y, bot_left_x, bot_left_y);
    }
}