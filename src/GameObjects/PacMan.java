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
    private double speed;
    private double radius;

    private Path path;


    public PacMan(double lat, double lon, double alt, double speed, double radius)
    {
        String location_string = "(" + lat + "," + lon + "," + alt + ")";
        this.location = new Point3D(location_string);
        ID++;
        this.speed = speed;
        this.radius = radius;
        path = new Path(ID);
        path.addPointToPath(this.location);
    }

    public PacMan(String id, String lat, String lon, String alt, String speed, String radius)
    {
        String location_string = "(" + lat + "," + lon + "," + alt + ")";
        this.location = new Point3D(location_string);
        ID = Integer.parseInt(id);
        this.speed = Double.parseDouble(speed);
        this.radius = Double.parseDouble(radius);
        path = new Path(ID);
        path.addPointToPath(this.location);
    }

    public PacMan(PacMan n)
    {
        this.location = new Point3D(n.getLocation());
        this.speed = n.getSpeed();
        this.radius = n.getRadius();
        this.path = n.getPath();
    }

    @Override
    public Point3D getLocation()
    {
        return this.location;
    }

    @Override
    public String getType()
    {
        return "P";
    }

    public double getId()
    {
        return ID;
    }

    public double getSpeed()
    {
        return speed;
    }

    public double getRadius()
    {
        return radius;
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
        csvFormat.append(getSpeed());
        csvFormat.append(getRadius());
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
        double distance = (MyCoords.distance3d(this.location, target.getLocation()) - this.radius);
        if (distance <= 0)
        {
            return 0;
        }
        return distance;
    }

    public Path getPath()
    {
        return path;
    }

    public void setPath(Path yourPath)
    {
        path = yourPath;
    }

    public void updateLocation(Point3D location)
    {
        this.location = location;
    }

    public double getPathTime()
    {
        return this.path.getTimeForPath();
    }

    public double rawDistance(Fruit target)
    {
        return (MyCoords.distance3d(this.location, target.getLocation()));
    }

    public void restartPath()
    {
        this.path = new Path(ID);
    }
}
