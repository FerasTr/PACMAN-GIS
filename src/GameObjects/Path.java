package GameObjects;

import Coords.MyCoords;
import Geom.Point3D;

import java.awt.Color;
import java.util.*;

public class Path
{
    private ArrayList<secondsPoint3D> path_in_points;
    private Color color;
    private double timeToComplete = 0;

    int parentID;

    public Path(int ID)
    {
        path_in_points = new ArrayList<secondsPoint3D>();
        color = randomColor();
        parentID = ID;
    }

    public void addPointToPath(Point3D p,int second)
    {
        path_in_points.add(new secondsPoint3D(p,second));
    }

    public void addPointToPath(Point3D p)
    {
        path_in_points.add(new secondsPoint3D(p,0));
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
        Iterator<secondsPoint3D> iter = this.path_in_points.iterator();
        Point3D currentPoint = iter.next().getPoint();
        while (iter.hasNext())
        {
            Point3D nextPoint = iter.next().getPoint();
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
        for (secondsPoint3D point : path_in_points)
        {
            toPrint += point.getPoint().toFile() + '\n';
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

    public ArrayList<secondsPoint3D> getPathList()
    {
        return path_in_points;
    }

}
