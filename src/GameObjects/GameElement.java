package GameObjects;

import Geom.Point3D;

import java.awt.image.BufferedImage;


public interface GameElement
{
    public Point3D getLocation();

    public String getType();

    public double getSpeed();

    public double getRadius();

    public int getId();

    public String toString();

    public BufferedImage getIcon();
}
