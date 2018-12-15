package GameObjects;

import Coords.MyCoords;
import Geom.Point3D;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;

public class Path implements Set<Point3D>
{
    private Set<Point3D> path_in_points;

    public Path()
    {
        path_in_points = new LinkedHashSet<Point3D>();
    }

    public double length()
    {
        double length = 0;
        if (path_in_points.size() <= 1)
        {
            return 0;
        }
        Iterator<Point3D> iter = this.iterator();
        Point3D currentPoint = iter.next();
        while (iter.hasNext())
        {
            Point3D nextPoint = iter.next();
            length += MyCoords.distance3d(currentPoint, nextPoint);
            currentPoint = nextPoint;
        }

        return length;
    }

    @Override
    public int size()
    {
        return this.path_in_points.size();
    }

    @Override
    public boolean isEmpty()
    {
        return this.path_in_points.isEmpty();
    }

    @Override
    public boolean contains(Object o)
    {
        return this.path_in_points.contains(o);
    }

    @Override
    public Iterator<Point3D> iterator()
    {
        return this.path_in_points.iterator();
    }

    @Override
    public Object[] toArray()
    {
        return this.path_in_points.toArray();
    }

    @Override
    public <T> T[] toArray(T[] a)
    {
        return this.path_in_points.toArray(a);
    }

    @Override
    public boolean add(Point3D Point3D)
    {
        return this.path_in_points.add(Point3D);
    }

    @Override
    public boolean remove(Object o)
    {
        return this.path_in_points.remove(o);
    }

    @Override
    public boolean containsAll(Collection<?> c)
    {
        return this.path_in_points.containsAll(c);
    }

    @Override
    public boolean addAll(Collection<? extends Point3D> c)
    {
        return this.path_in_points.addAll(c);
    }

    @Override
    public boolean retainAll(Collection<?> c)
    {
        return this.path_in_points.retainAll(c);
    }

    @Override
    public boolean removeAll(Collection<?> c)
    {
        return this.path_in_points.removeAll(c);
    }

    @Override
    public void clear()
    {
        this.path_in_points.clear();
    }

    public String toString()
    {
        String toPrint = "";
        for (Point3D point : path_in_points)
        {
            toPrint += point.toFile() + '\n';
        }
        return toPrint;
    }
}
