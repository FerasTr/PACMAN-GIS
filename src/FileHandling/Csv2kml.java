package FileHandling;

import GameObjects.Game;

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
    public static void toKML(Game game)
    {
        WriteToKML.FromFile(game);
    }
}
