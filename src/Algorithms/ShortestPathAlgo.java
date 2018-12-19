package Algorithms;

import GameObjects.*;
import Geom.Point3D;

import java.util.ArrayList;
import java.util.Iterator;

final public class ShortestPathAlgo
{
    private ShortestPathAlgo()
    {
    }

    private static PacMan fastestToEat(Fruit currentFruit, Game gameState)
    {
        PacMan close = null;
        ArrayList<PacMan> players = gameState.listPacman();
        double bestTime = Integer.MAX_VALUE;
        for (PacMan canidate : players)
        {
            double currentDist = canidate.distanceToScore(currentFruit);
            if (currentDist == -1)
            {
                return canidate;
            }
            double currentTime = currentDist / Double.parseDouble(canidate.getData().getSpeed());
            if (currentTime < bestTime)
            {
                bestTime = currentTime;
                close = canidate;
            }
        }
        return close;
    }

    public static void algorithm(Game game)
    {
        Game temp = new Game(game);
        ArrayList<Fruit> targets = temp.listFruit();
        Iterator<Fruit> itr = targets.iterator();

        while (itr.hasNext())
        {
            Fruit currentTarget = itr.next();

            PacMan close = fastestToEat(currentTarget, temp);
            updateTime(close, currentTarget);
            if (currentTarget.isNotEaten())
            {
                ArrayList<Point3D> paths = close.getPath().getPathList();
                if (paths.isEmpty())
                {
                    paths.add(close.getLocation());
                }
                close.updateLocation(currentTarget.getLocation());
                paths.add(close.getLocation());
                currentTarget.setEaten();
                itr.remove();
            }
            else
            {
                System.out.println(currentTarget.getLocation().toFile() + " is eaten");
            }
        }
    }

    private static void updateTime(PacMan pacman, Fruit target)
    {
        double distance = pacman.distanceToScore(target);
        if (distance == -1)
        {
            pacman.getPath().setTimeForPath(0);
            target.setEaten();
        }
        else
        {
            double currentTime = distance / Double.parseDouble(pacman.getData().getSpeed());
            pacman.getPath().setTimeForPath(currentTime);
        }
    }
}
