package GIS;

import Geom.Point3D;

import java.util.Date;

/**
 * This class implements the metadata interface which allows us to create metadata about the project (TIME OF CREATION)
 */
public class MetadataOfCollection_GIS implements Meta_data
{
    private long time_of_folder;

    public MetadataOfCollection_GIS()
    {
        Date date = new Date();
        time_of_folder = date.getTime();
    }

    @Override
    public long getUTC()
    {
        return time_of_folder;
    }

    @Override
    public Point3D get_Orientation()
    {
        return null;
    }

    @Override
    public String toString()
    {
        return "Time of creation: " + getUTC();
    }
}
