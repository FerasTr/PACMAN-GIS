package FileHandling;

import GameObjects.Game;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * This class writes a game state into a CSV file.
 */
final public class WriteCSV
{
    private WriteCSV()
    {
    }

    public static void WriteToCSV(Game game)
    {
        ArrayList pacmanList = game.listPacman();
        ArrayList fruitsList = game.listFruit();

        String csvHeader =
                "Type,id,Lat,Lon,Alt,Speed/Weight,Radius," + game.sizePacman() + "," + game.sizeFruit() + '\n';

        FileWriter fileWriter = null;

        try
        {
            fileWriter = new FileWriter(new File("./FilesOut/CSV", "game_" + getDateName() + ".csv"));
            fileWriter.append(csvHeader);

            // PACMAN

            for (int i = 0; i < pacmanList.size(); i++)
            {
                fileWriter.append(pacmanList.get(i).toString() + '\n');
            }

            // Fruit

            for (int i = 0; i < fruitsList.size(); i++)
            {
                fileWriter.append(fruitsList.get(i).toString() + '\n');

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
            catch (IOException | NullPointerException e)
            {
                System.out.println("ERROR: " + e);
                e.printStackTrace();
            }

        }
    }

    /**
     * Helper method used for naming a file.
     *
     * @return time in milli seconds as a string.
     */
    private static String getDateName()
    {
        Date date_recorded = new Date();
        Calendar whole_date = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
        whole_date.setTime(date_recorded);
        return whole_date.getTimeInMillis() + "";
    }
}
