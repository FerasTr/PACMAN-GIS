package GameObjects;

import Geom.Point3D;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;


public class Fruit implements GameElement
{
    private static int ID = -1;

    private Point3D location;
    private double weight;

    boolean NotEaten = true; // true not eaten, false eaten

    // For manual
    public Fruit(double lat, double lon, double alt, double weight)
    {
        String location_string = "(" + lat + "," + lon + "," + alt + ")";
        this.location = new Point3D(location_string);
        ID++;

        this.weight = weight;
    }

    // For csv
    public Fruit(String id, String lat, String lon, String alt, String weight)
    {
        String location_string = "(" + lat + "," + lon + "," + alt + ")";
        this.location = new Point3D(location_string);
        ID = Integer.parseInt(id);
        this.weight = Double.parseDouble(weight);
    }

    public Fruit(Fruit n)
    {
        this.location = new Point3D(n.getLocation());
        this.weight = n.getWeight();
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

    public double getWeight()
    {
        return weight;
    }

    public double getId()
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
