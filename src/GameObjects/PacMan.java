package GameObjects;

import Coords.MyCoords;
import Geom.Point3D;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;

public class PacMan implements GameElement
{
    private static int ID = -1;

    private Point3D location;
    private Point3D startingPos;
    private double speed;
    private double radius;

    private Path path;

    /**
     * Manual constructor.
     */
    public PacMan(double lat, double lon, double alt, double speed, double radius)
    {
        String location_string = "(" + lat + "," + lon + "," + alt + ")";
        this.location = new Point3D(location_string);
        String startingString = "(" + lat + "," + lon + "," + alt + ")";
        this.startingPos = new Point3D(startingString);
        ID++;
        this.speed = speed;
        this.radius = radius;
        path = new Path(ID);
    }

    /**
     * String constructor
     */
    public PacMan(String id, String lat, String lon, String alt, String speed, String radius)
    {
        String location_string = "(" + lat + "," + lon + "," + alt + ")";
        this.location = new Point3D(location_string);
        String startingString = "(" + lat + "," + lon + "," + alt + ")";
        this.startingPos = new Point3D(startingString);
        ID = (int) Double.parseDouble(id);
        this.speed = Double.parseDouble(speed);
        this.radius = Double.parseDouble(radius);
        path = new Path(ID);
    }

    /**
     * Copy constructor.
     */
    public PacMan(PacMan n)
    {
        this.location = new Point3D(n.getLocation());
        this.startingPos = new Point3D(n.startingPos);
        this.speed = n.getSpeed();
        this.radius = n.getRadius();
        this.path = new Path(n.getPath());
        this.ID = n.getId();
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

    @Override
    public int getId()
    {
        return ID;
    }

    @Override
    public double getSpeed()
    {
        return speed;
    }

    @Override
    public double getRadius()
    {
        return radius;
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

    @Override
    public String toString()
    {
        StringBuilder csvFormat = new StringBuilder();
        csvFormat.append(getType() + ",");
        csvFormat.append(getId() + ",");
        csvFormat.append(this.location.x() + ",");
        csvFormat.append(this.location.y() + ",");
        csvFormat.append(this.location.z() + ",");
        csvFormat.append(getSpeed() + ",");
        csvFormat.append(getRadius());
        return csvFormat.toString();
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

    /**
     * Raw distance between target and player
     */
    public double rawDistance(Fruit target)
    {
        return (MyCoords.distance3d(this.location, target.getLocation()));
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

    public void restartPath()
    {
        this.path.getPathList().clear();
    }

    public void resetToStart()
    {
        this.updateLocation(new Point3D(startingPos));
    }

    public static void resetCounter()
    {
        ID = -1;
    }
}
