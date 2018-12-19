package GameObjects;

import Geom.Point3D;

import java.awt.image.BufferedImage;


public interface GameElement
{
    public Point3D getLocation();

    public Metadata getData();

    public String toString();

    public BufferedImage getIcon();
}
