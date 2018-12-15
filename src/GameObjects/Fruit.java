package GameObjects;

import GameObjects.GameElement;
import GameObjects.Meta_data;
import GameObjects.Metadata;
import Geom.Point3D;

public class Fruit implements GameElement
{
    private static int ID = -1;

    private Point3D location;
    private Metadata information;

    public Fruit(String lat, String lon, String alt, String weight)
    {
        String location_string = "(" + lat + "," + lon + "," + alt + ")";
        this.location = new Point3D(location_string);
        ID++;
        this.information = new Metadata("F", String.valueOf(ID), weight);
    }

    public Fruit(String id, String lat, String lon, String alt, String weight)
    {
        String location_string = "(" + lat + "," + lon + "," + alt + ")";
        this.location = new Point3D(location_string);
        ID = Integer.parseInt(id);
        this.information = new Metadata("F", String.valueOf(ID), weight);
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
