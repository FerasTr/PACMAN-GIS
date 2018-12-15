package GameObjects;

import GameObjects.*;;
import Geom.Point3D;

public class PacMan implements GameElement
{
    private static int ID = -1;

    private Point3D location;
    private Metadata information;

    public PacMan(String lat, String lon, String alt, String speed, String radius)
    {
        String location_string = "(" + lat + "," + lon + "," + alt + ")";
        this.location = new Point3D(location_string);
        ID++;
        this.information = new Metadata("P", String.valueOf(ID), speed, radius);
    }

    public PacMan(String id, String lat, String lon, String alt, String speed, String radius)
    {
        String location_string = "(" + lat + "," + lon + "," + alt + ")";
        this.location = new Point3D(location_string);
        ID = Integer.parseInt(id);
        this.information = new Metadata("P", String.valueOf(ID), speed, radius);
    }

    @Override
    public Point3D getLocation()
    {
        return this.location;
    }

    @Override
    public Meta_data getData()
    {
        return this.information;
    }

    @Override
    public String toString()
    {
        return information.toString() + " " + location.toFile() + '\n';
    }

}
