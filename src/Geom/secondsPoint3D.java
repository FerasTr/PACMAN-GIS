package Geom;

public class secondsPoint3D
{
    private Point3D point;
    private  int seconds;

    public secondsPoint3D(Point3D p, int sec)
    {
        this.point = p;
        this.seconds = sec;
    }

    public Point3D getPoint()
    {
        return point;
    }

    public void setPoint(Point3D point)
    {
        this.point = point;
    }

    public int getSeconds()
    {
        return seconds;
    }

    public void setSeconds(int seconds)
    {
        this.seconds = seconds;
    }
}
