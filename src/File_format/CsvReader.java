package File_format;

import GIS.Element_GIS;
import GIS.GIS_layer;
import GIS.Layer_GIS;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * Static class used for reading one CSV file, returns GIS layer object.
 */
final public class CsvReader
{
    private CsvReader()
    {
    }

    /**
     * Static method used for reading a CSV file.
     *
     * @param name name of file to be read.
     * @return GIS_layer object which has the all of the file's data stored separately.
     */
    public static GIS_layer parsing(String name)
    {
        Layer_GIS file = new Layer_GIS(name);
        try (BufferedReader br = new BufferedReader(new FileReader(name)))
        {
            // Skip the first two lines in the csv file (HEADER AND TITLES).
            br.readLine();
            br.readLine();
            // Read the buffer in (LINES).
            String line;
            while ((line = br.readLine()) != null)
            {
                String[] info = line.split(",");
                file.add(new Element_GIS(info[0], info[1], info[2], info[3], info[4], info[5], info[6], info[7],
                        info[8], info[9], info[10]));
            }

        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return file;
    }
}
