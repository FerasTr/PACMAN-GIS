package GameObjects;

import Geom.Point3D;

import java.awt.image.BufferedImage;


public interface GameElement
{
    public Point3D getLocation();

    public Meta_data getData();

    public String toString();

    public BufferedImage getIcon();
}
