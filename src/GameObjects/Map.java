package GameObjects;


import Coords.MyCoords;
import Geom.Point3D;

import java.awt.image.BufferedImage;

public class Map
{
    private BufferedImage map_path;
    private Range mapRange;

    public Map(BufferedImage base, Range mapRange)
    {
        map_path = base;
        this.mapRange = mapRange;

    }

    public Point3D pixleToGPS(Point3D pixelPoint)
    {
        double realW = pixelPoint.x() / map_path.getWidth();
        double realH = pixelPoint.y() / map_path.getHeight();

        double deltaX = mapRange.getDeltaX();
        double deltaY = mapRange.getMidDeltaY();

        double x = mapRange.getTopX() - (realH * deltaX);
        double y = mapRange.getLeftMid().y() + (realW * deltaY);

        return new Point3D(x, y, 0);
    }

    public Point3D gpsToPixle(Point3D gpsPoint)
    {
        double rel_x = mapRange.getTopX() - gpsPoint.x();
        double rel_y = gpsPoint.y() - mapRange.getLeftMid().y();

        double deltaX = mapRange.getDeltaX();
        double deltaY = mapRange.getMidDeltaY();

        double realW = rel_x / deltaX;
        double realH = rel_y / deltaY;

        int lat = (int) (realH * map_path.getWidth());
        int lon = (int) (realW * map_path.getHeight());

        return new Point3D(lat, lon, 0);
    }

    public double distBetweenPixles(Point3D pixle1, Point3D pixle2)
    {
        Point3D pixle1ToGPS = pixleToGPS(pixle1);
        Point3D pixle2ToGPS = pixleToGPS(pixle2);
        return MyCoords.distance3d(pixle1ToGPS, pixle2ToGPS);
    }

    public BufferedImage getMapPath()
    {
        return map_path;
    }
}