package File_format;

import GIS.GIS_layer;
import GIS.GIS_project;

/**
 * Static class used for reading CSV file and converting to KML or converting a folder and its content (if valid) to
 * KML.
 */
public final class Csv2kml
{
    /**
     * Objects shouldn't be made from a static class (USED ONLY FOR ITS METHODS).
     */
    private Csv2kml()
    {
    }

    /**
     * Take the current file, read it, and call WriteToKML to convert it into a KML file.
     *
     * @param fileForConverting name of file (USE ABSOLUTE PATH).
     */
    public static void readCSVFile(String fileForConverting)
    {
        toKML(CsvReader.parsing(fileForConverting));
    }

    /**
     * Convert folder to KML.
     *
     * @param project multiple csv files to be converted.
     */
    public static void toKML(GIS_project project)
    {
        WriteToKML.FromFolder(project);
    }

    /**
     * Convert file to KML.
     *
     * @param layer one CSV file to be converted.
     */
    public static void toKML(GIS_layer layer)
    {
        WriteToKML.FromFile(layer);
    }
}
