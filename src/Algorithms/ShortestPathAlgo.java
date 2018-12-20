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
            game.updateTimeForGame(timeToEatClose);
            double speed = close.getSpeed();
            double steps = close.distanceToScore(currentTarget) / speed;
            System.out.println(steps);
            Point3D asd = new Point3D(close.getLocation());
            Point3D point;
            for (int i = 0; i < steps; i++)
            {
                point = nextPointByRadius(asd, close.getLocation(), currentTarget.getLocation(), speed);
                if (!point.equals(close.getLocation()))
                {
                    System.out.println("DEBUG " + point.toFile());
                    close.updateLocation(point);
                    p.addPointToPath(close.getLocation());
                }
                else
                {
                    System.out.println("OUT");
                    p.addPointToPath(currentTarget.getLocation());
                    break;
                }
            }
            currentTarget.setEaten();
            itr.remove();
        }
        game.resetPacPost();
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

    public static Point3D nextPointByRadius(Point3D asd, Point3D pac, Point3D fruit, double speed)
    {
        Point3D current = MyCoords.vector3D(pac, fruit);
        double currentLength = current.distance3D(0, 0, 0);
        double realLength = MyCoords.vector3D(asd, fruit).distance3D(0, 0, 0);
        double relative = (currentLength - speed) / realLength;
        System.out.println(relative);
        relative = 1 - relative;
        if (relative > 1)
        {
            return pac;
        }
        System.out.println(relative);
        Point3D ratioVector = new Point3D(current.x() * relative, current.y() * relative, current.z() * relative);

        return MyCoords.add(pac, ratioVector);
    }
}