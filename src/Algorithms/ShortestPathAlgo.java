package Algorithms;

import Coords.MyCoords;
import GameObjects.*;
import Geom.Point3D;

import java.util.ArrayList;
import java.util.Iterator;

final public class ShortestPathAlgo
{
    private ShortestPathAlgo()
    {
    }

    private static Fruit fastestToEat(PacMan player, ArrayList<Fruit> targets)
    {
        Fruit close = null;
        double bestDist = Double.MAX_VALUE;

        for (Fruit canidate : targets)
        {
            double currentDist = player.distanceToScore(canidate);
            if (currentDist < bestDist)
            {
                bestDist = currentDist;
                close = canidate;
            }
        }
        return close;
    }

    public static void algorithm(Game game)
    {
        ArrayList<Fruit> targets = game.listFruit();
        ArrayList<PacMan> players = game.listPacman();
        double bestTime;
        Fruit bestTarget = null;
        PacMan bestPlayer = null;
        Point3D bestPoint;
        Path bestPath;

        while (!targets.isEmpty())
        {
            bestTime = Integer.MAX_VALUE;
            for (PacMan player : players)
            {
                System.out.println("DEBUG ENDLESS LOOP HERE");
                Point3D playerLocation = player.getLocation();
                double radius = player.getRadius();
                double speed = player.getSpeed();
                double currentPlayerTime = player.getPathTime();

                Fruit close = fastestToEat(player, targets);

                Point3D targetLocation = close.getLocation();
                bestPoint = nextPointByRadius(playerLocation, targetLocation, radius);
                double distanceToNextPoint = MyCoords.distance3d(player.getLocation(), bestPoint);
                double time = distanceToNextPoint / speed;
                double timeForPotintioalFruit = currentPlayerTime + time;
                if (timeForPotintioalFruit < bestTime)
                {
                    bestTime = timeForPotintioalFruit;
                    bestTarget = close;
                    bestPlayer = player;
                }
            }
            bestPoint = nextPointByRadius(bestPlayer.getLocation(), bestTarget.getLocation(), bestPlayer.getRadius());
            bestPath = bestPlayer.getPath();
            bestPath.addPointToPath(bestPoint);
            bestPath.setTimeForPath(bestTime);
            bestTarget.setEaten();
        }
    }

    public static Point3D nextPointByRadius(Point3D pac, Point3D fruit, double radius)
    {
        Point3D current = MyCoords.vector3D(pac, fruit);
        double currentLength = current.distance3D(0, 0, 0);
        if (currentLength <= radius)
        {
            return pac;
        }
        double relative = (currentLength - radius) / currentLength;
        Point3D ratioVector = new Point3D(current.x() * relative, current.y() * relative, current.z() * relative);

        return MyCoords.add(pac, ratioVector);
    }
}
