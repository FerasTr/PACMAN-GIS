package FileHandling;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedHashSet;
import java.util.Set;

import GameObjects.Fruit;
import GameObjects.GameElement;
import GameObjects.PacMan;

/**
 * Static class used for reading one CSV file, returns GameObjects layer object.
 */
final public class ReadCSV
{
    private ReadCSV()
    {
    }

    public static Set<GameElement> parsing(String name)
    {
        Set<GameElement> state = new LinkedHashSet<GameElement>();
        try (BufferedReader br = new BufferedReader(new FileReader(name)))
        {
            // Skip the first line in the csv file (HEADER AND TITLES).
            br.readLine();
            // Read the buffer in (LINES).
            String line;
            while ((line = br.readLine()) != null)
            {
                String[] info = line.split(",");
                if (info[0].equals("P"))
                {
                    state.add(new PacMan(info[1], info[2], info[3], info[4], info[5], info[6]));
                }
                else
                {
                    state.add(new Fruit(info[1], info[2], info[3], info[4], info[5]));
                }
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return state;
    }
}
