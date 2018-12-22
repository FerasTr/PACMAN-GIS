package GameObjects;

import Geom.Point3D;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Fruit is the object of the game
 */
public class Fruit implements GameElement
{
    private static int ID = -1;
    private Point3D location;
    private double weight;

    boolean NotEaten = true; // true not eaten, false eaten

    /**
     * Manual constructor.
     */
    public Fruit(double lat, double lon, double alt, double weight)
    {
        String location_string = "(" + lat + "," + lon + "," + alt + ")";
        this.location = new Point3D(location_string);
        ID++;
        this.weight = weight;
    }

    /**
     * String constructor
     */
    public Fruit(String id, String lat, String lon, String alt, String weight)
    {
        String location_string = "(" + lat + "," + lon + "," + alt + ")";
        this.location = new Point3D(location_string);
        ID = (int) Double.parseDouble(id);
        this.weight = Double.parseDouble(weight);
    }

    /**
     * Copy constructor.
     */
    public Fruit(Fruit n)
    {
        this.location = new Point3D(n.getLocation());
        this.weight = n.getWeight();
        this.ID = n.getId();
    }

    public static void resetCounter() {
        ID = -1;
    }

    @Override
    public Point3D getLocation()
    {
        return this.location;
    }

    @Override
    public String getType()
    {
        return "F";
    }

    @Override
    public double getSpeed()
    {
        return weight;
    }

    @Override
    public double getRadius()
    {
        return -1;
    }

    public double getWeight()
    {
        return weight;
    }

    @Override
    public int getId()
    {
        return ID;
    }


    @Override
    public String toString()
    {
        StringBuilder csvFormat = new StringBuilder();
        csvFormat.append(getType() + ",");
        csvFormat.append(getId() + ",");
        csvFormat.append(this.location.x() + ",");
        csvFormat.append(this.location.y() + ",");
        csvFormat.append(this.location.z() + ",");
        csvFormat.append(getWeight());
        return csvFormat.toString();
    }

    @Override
    public BufferedImage getIcon()
    {
        try
        {
            return ImageIO.read(new File("./Resources/icons/fruit.png"));
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return null;
    }

    public void setEaten()
    {
        NotEaten = false;
    }

    public boolean isNotEaten()
    {
        return NotEaten;
    }
}
