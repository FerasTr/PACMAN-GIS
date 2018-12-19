/*package Algorithms;

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
                    System.out.println("DEBUG ENDLESS LOOP HERE");
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
        }}
    }*/


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

    private static PacMan fastestToEat(Fruit target, ArrayList<PacMan> players)
    {
        PacMan close = null;
        double bestDist = Double.MAX_VALUE;

        for (PacMan canidate : players)
        {
            double currentDist = canidate.distanceToScore(target);
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
        Iterator<Fruit> itr = targets.iterator();
        while (itr.hasNext())
        {
            Fruit currentTarget = itr.next();

            PacMan close = fastestToEat(currentTarget, players);
            double timeToEatClose = calcTimeToEat(close, currentTarget);
            for (PacMan canidate : players)
            {
                double timeToEatCanidate = calcTimeToEat(canidate, currentTarget);
                if (timeToEatCanidate < timeToEatClose)
                {
                    close = canidate;
                    timeToEatClose = timeToEatCanidate;
                }
            }
            Path p = close.getPath();
            p.addPointToPath(close.getLocation());
            p.setTimeForPath(timeToEatClose);
            double steps = close.distanceToScore(currentTarget) / close.getSpeed();
            System.out.println(steps);
            for (int i = 0; i < steps; i++)
            {
                Point3D point = nextPointByRadius(close.getLocation(), currentTarget.getLocation(), close.getRadius());
                p.addPointToPath(point);
                close.updateLocation(point);
                System.out.println("DEBUG " + point.toFile());
            }
            currentTarget.setEaten();
            itr.remove();
        }
    }

    private static double calcTimeToEat(PacMan close, Fruit currentTarget)
    {
        Point3D fruitLocation = currentTarget.getLocation();
        Point3D closeLocation = close.getLocation();
        double speed = close.getSpeed();
        double radius = close.getRadius();
        Point3D current = MyCoords.vector3D(closeLocation, fruitLocation);
        double currentLength = current.distance3D(0, 0, 0);
        if (currentLength <= radius)
        {
            return 0;
        }

        return currentLength / speed;
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