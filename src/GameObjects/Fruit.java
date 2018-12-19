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
    private Metadata information;

    boolean NotEaten = true;

    public Fruit(double lat, double lon, double alt, double weight)
    {
        String location_string = "(" + lat + "," + lon + "," + alt + ")";
        this.location = new Point3D(location_string);
        ID++;
        this.information = new Metadata("F", String.valueOf(ID), String.valueOf(weight));
    }

    public Fruit(String id, String lat, String lon, String alt, String weight)
    {
        String location_string = "(" + lat + "," + lon + "," + alt + ")";
        this.location = new Point3D(location_string);
        ID = Integer.parseInt(id);
        this.information = new Metadata("F", String.valueOf(ID), weight);
    }

    @Override
    public Point3D getLocation()
    {
        return this.location;
    }

    @Override
    public Metadata getData()
    {
        return this.information;
    }


    @Override
    public String toString()
    {
        StringBuilder csvFormat = new StringBuilder();
        csvFormat.append(this.information.getType() + ",");
        csvFormat.append(this.information.getId() + ",");
        csvFormat.append(this.location.x() + ",");
        csvFormat.append(this.location.y() + ",");
        csvFormat.append(this.location.z() + ",");
        csvFormat.append(this.information.getSpeed());
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
