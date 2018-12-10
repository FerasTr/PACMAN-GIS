package GIS;

import Coords.*;
import Geom.Geom_element;
import Geom.Point3D;

/**
 * Implementing GIS_element, which is one line of a CSV file, extracts metadata and coordinates.
 */
public class Element_GIS implements GIS_element
{
    // Params for storing information from one line.
    private Point3D geo_location;
    private Metadata metadata;

    /**
     * Store the info from the given line,
     *
     * @param mac
     * @param ssid
     * @param auth_mode
     * @param first_seen
     * @param channel
     * @param rssi
     * @param current_lat
     * @param current_lon
     * @param current_alt
     * @param accuracy_meters
     * @param type
     */
    public Element_GIS(String mac, String ssid, String auth_mode, String first_seen, String channel, String rssi,
                       String current_lat, String current_lon, String current_alt, String accuracy_meters, String type)
    {
        String point = "(" + current_lat + "," + current_lon + "," + current_alt + ")";
        this.geo_location = new Point3D(point);
        this.metadata = new Metadata(mac, ssid, auth_mode, first_seen, channel, rssi, accuracy_meters, type);
    }

    /**
     * Location of using Point3D
     *
     * @return coordinates
     */
    @Override
    public Geom_element getGeom()
    {
        return geo_location;
    }

    /**
     * Get data about the point.
     *
     * @return metadata object
     */
    @Override
    public Meta_data getData()
    {
        return metadata;
    }

    /**
     * Move the point using the given vector.
     *
     * @param vec
     */
    @Override
    public void translate(Point3D vec)
    {
        this.geo_location = new MyCoords().add(this.geo_location, vec);
    }

    /**
     * Prints the line in a sorted way.
     *
     * @return formated info from the line.
     */
    public String toString()
    {
        String point_string = "Location: " + geo_location.toFile();
        String metadata_string = "Metadata: " + metadata.toString();
        return point_string + " " + metadata_string;
    }
}
