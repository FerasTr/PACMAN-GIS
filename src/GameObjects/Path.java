package GameObjects;

import Coords.MyCoords;
import Geom.Point3D;
import Geom.secondsPoint3D;

import java.awt.Color;
import java.util.*;

/**
 * Path class, used for building the path of one pacman in the game.
 */
public class Path
{
    private ArrayList<secondsPoint3D> path_in_points;
    private Color color;
    private double timeToComplete = 0;
    private double len = 0;
    int parentID;

    public Path(int ID)
    {
        path_in_points = new ArrayList<>();
        color = randomColor();
        parentID = ID;
    }

    public Path(Path n)
    {
        this.path_in_points = new ArrayList<>();
        for (int i = 0; i < n.path_in_points.size(); i++)
        {
            int sec = n.path_in_points.get(i).getSeconds();
            Point3D loc = new Point3D(n.path_in_points.get(i).getPoint());
            this.path_in_points.add(new secondsPoint3D(loc, sec));
        }
        this.color = n.getColor();
        parentID = n.getParentID();
    }

    public void addPointToPath(Point3D p, int second)
    {
        path_in_points.add(new secondsPoint3D(p, second));
    }

    public void addPointToPath(Point3D p)
    {
        path_in_points.add(new secondsPoint3D(p, 0));
    }

    /**
     * Get a color for the path.
     */
    private Color randomColor()
    {
        Random rand = new Random();
        float r = rand.nextFloat();
        float g = rand.nextFloat();
        float b = rand.nextFloat();
        return new Color(r, g, b);
    }

    /**
     * Length of the path
     */
    public double length()
    {
        double length = 0;
        if (path_in_points.size() <= 1)
        {
            return 0;
        }
        Iterator<secondsPoint3D> iter = this.path_in_points.iterator();
        Point3D currentPoint = iter.next().getPoint();
        while (iter.hasNext())
        {
            Point3D nextPoint = iter.next().getPoint();
            length += MyCoords.distance3d(currentPoint, nextPoint);
            currentPoint = nextPoint;
        }
        len = length;
        return length;
    }

    public Color getColor()
    {
        return color;
    }

    public String toString()
    {
        StringBuilder toPrint = new StringBuilder();
        for (secondsPoint3D point : path_in_points)
        {
            toPrint.append(point.getPoint().toFile() + '\n');
        }
        return toPrint.toString() + " time: " + timeToComplete;
    }

    public void setTimeForPath(double time)
    {
        timeToComplete += time;
    }

    public double getTimeForPath()
    {
        return timeToComplete;
    }

    public int getParentID()
    {
        return parentID;
    }

    public ArrayList<secondsPoint3D> getPathList()
    {
        return path_in_points;
    }
}
