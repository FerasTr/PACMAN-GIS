package GameObjects;

import Coords.MyCoords;
import Geom.Point3D;

public class Range
{
    Point3D top_left;
    Point3D top_right;
    Point3D bot_left;
    Point3D bot_right;

    public Range(double top_left_x, double top_left_y, double bot_right_x, double bot_right_y)
    {
        top_left = new Point3D(top_left_x, top_left_y, 0);
        bot_right = new Point3D(bot_right_x, bot_right_y, 0);
        top_right = new Point3D(top_left_x, bot_right_y, 0);
        bot_left = new Point3D(bot_right_x, top_left_y, 0);
    }

    public double getTopX()
    {
        return top_left.x();
    }

    public double getBotX()
    {
        return bot_left.x();
    }

    public double getLeftY()
    {
        return top_left.y();
    }

    public double getRightY()
    {
        return bot_left.y();
    }

    public Point3D getTopLeftPoint()
    {
        return top_left;
    }

    public Point3D getTopRightPoint()
    {
        return top_right;
    }

    public Point3D getBotRightPoint()
    {
        return bot_right;
    }

    public Point3D getBotLeftPoint()
    {
        return bot_left;
    }

    public double getDeltaX()
    {
        return top_left.x() - bot_right.x();
    }

    public double getDeltaY()
    {
        return top_left.y() - bot_right.y();
    }

    public double getMidDeltaY()
    {
        Point3D gpsLeft = getLeftMid();
        Point3D gpsRight = getRightMid();
        return gpsRight.y() - gpsLeft.y();

    }

    public Point3D getLeftMid()
    {
        return MyCoords.midGPS(top_left, bot_left);

    }

    public Point3D getRightMid()
    {
        return MyCoords.midGPS(top_right, bot_right);
    }
}
