package GameObjects;

import Coords.MyCoords;
import Geom.Point3D;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class PacMan implements GameElement
{
    private static int ID = -1;


    private Point3D location;
    private Metadata information;

    public PacMan(double lat, double lon, double alt, double speed, double radius)
    {
        String location_string = "(" + lat + "," + lon + "," + alt + ")";
        this.location = new Point3D(location_string);
        ID++;
        this.information = new Metadata("P", String.valueOf(ID), String.valueOf(speed), String.valueOf(radius));
    }

    public PacMan(String id, String lat, String lon, String alt, String speed, String radius)
    {
        String location_string = "(" + lat + "," + lon + "," + alt + ")";
        this.location = new Point3D(location_string);
        ID = Integer.parseInt(id);
        this.information = new Metadata("P", String.valueOf(ID), speed, radius);
    }

    @Override
    public Point3D getLocation()
    {
        return this.location;
    }

    @Override
    public Meta_data getData()
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
        csvFormat.append(this.information.getSpeed() + ",");
        csvFormat.append(this.information.getRadius());
        return csvFormat.toString();
    }

    @Override
    public BufferedImage getIcon()
    {
        try
        {
            return ImageIO.read(new File("./Resources/icons/pacman.png"));
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return null;
    }

    public double distanceToScore(Fruit target)
    {
        double distance = MyCoords.distance3d(this.location, target.getLocation());
        if (distance <= 0)
        {
            return -1;
        }
        return distance;
    }

}
