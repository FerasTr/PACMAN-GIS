package GameObjects;

import Coords.MyCoords;
import Geom.Point3D;

import java.awt.Color;
import java.util.*;

public class Path
{
    private ArrayList<Point3D> path_in_points;
    private ArrayList<Point3D> fruitsInPath;
    private Color color;
    private double timeToComplete = 0;

    int parentID;

    public Path(int ID)
    {
        path_in_points = new ArrayList<Point3D>();
        color = randomColor();
        parentID = ID;
    }

    public void addPointToPath(Point3D p)
    {
        path_in_points.add(p);
    }


    private Color randomColor()
    {
        Random rand = new Random();
        float r = rand.nextFloat();
        float g = rand.nextFloat();
        float b = rand.nextFloat();
        return new Color(r, g, b);
    }

    public double length()
    {
        double length = 0;
        if (path_in_points.size() <= 1)
        {
            return 0;
        }
        Iterator<Point3D> iter = this.path_in_points.iterator();
        Point3D currentPoint = iter.next();
        while (iter.hasNext())
        {
            Point3D nextPoint = iter.next();
            length += MyCoords.distance3d(currentPoint, nextPoint);
            currentPoint = nextPoint;
        }

        return length;
    }

    public Color getColor()
    {
        return color;
    }


    public String toString()
    {
        String toPrint = "";
        for (Point3D point : path_in_points)
        {
            toPrint += point.toFile() + '\n';
        }
        return toPrint + " time: " + timeToComplete;
    }

    public void setTimeForPath(double time)
    {
        timeToComplete += time;
    }

    public double getTimeForPath()
    {
        return timeToComplete;
    }

    public ArrayList<Point3D> getPathList()
    {
        return path_in_points;
    }

}
