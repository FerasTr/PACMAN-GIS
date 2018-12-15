package GameObjects;


import Geom.Point3D;

import java.awt.image.BufferedImage;

public class Map
{
    private BufferedImage map_path;
    double top_right_x;
    double top_right_y;
    double bot_left_x;
    double bot_left_y;

    public Map(BufferedImage base, double top_right_x, double top_right_y, double bot_left_x, double bot_left_y)
    {
        this.map_path = base;
        this.top_right_x = top_right_x;
        this.top_right_y = top_right_y;
        this.top_right_x = bot_left_x;
        this.top_right_y = bot_left_y;
    }

    public Point3D pixleToGPS(Point3D pixle, double width, double height)
    {
        Point3D point_in_gps = new Point3D(0, 0, 0);
        return point_in_gps;
    }

    public Point3D gpsToPixle(Point3D gps, double width, double height)
    {
        Point3D point_in_gps = new Point3D(0, 0, 0);
        return point_in_gps;
    }

    public double distBetweenPixles()
    {
        return 0;
    }
}