package FileHandling;

import GameObjects.Game;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

final public class WriteCSV
{
    private WriteCSV()
    {
    }

    public static void WriteToCSV(Game game)
    {
        ArrayList pacman = game.listPacman();
        ArrayList fruit = game.listFruit();

        String csvHeader =
                "Type,id,Lat,Lon,Alt,Speed/Weight,Radius," + game.sizePacman() + "," + game.sizeFruit() + '\n';

        FileWriter fileWriter = null;

        try
        {
            fileWriter = new FileWriter(new File("./FilesOut/CSV", "game_" + getDateName() + ".csv"));
            fileWriter.append(csvHeader);

            // PACMAN

            for (int i = 0; i < pacman.size(); i++)
            {
                fileWriter.append(pacman.get(i).toString() + '\n');
            }

            // Fruit

            for (int i = 0; i < fruit.size(); i++)
            {
                fileWriter.append(fruit.get(i).toString() + '\n');

            }

            System.out.println("CSV file was created successfully.");

        }
        catch (IOException e)
        {
            System.out.println("ERROR: " + e);
            e.printStackTrace();
        } finally
        {
            try
            {
                fileWriter.flush();
                fileWriter.close();
            }
            catch (IOException e)
            {
                System.out.println("ERROR: " + e);
                e.printStackTrace();
            }

        }
    }

    /**
     * Helper method used for naming a folder.
     *
     * @return time in milli seconds as a string.
     */
    private static String getDateName()
    {
        Calendar whole_date = null;
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date_recorded = new Date();
        whole_date = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
        whole_date.setTime(date_recorded);
        return whole_date.getTimeInMillis() + "";
    }
}
