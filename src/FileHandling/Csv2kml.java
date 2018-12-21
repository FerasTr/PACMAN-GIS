package FileHandling;

import GameObjects.Game;

/**
 * Static class used for reading Game object file and converting to KML.
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
     * Convert to KML using WriteToKML class.
     * @param game
     */
    public static void toKML(Game game)
    {
        WriteToKML.FromGame(game);
    }
}
