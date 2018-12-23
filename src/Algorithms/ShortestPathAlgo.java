package Algorithms;

import Coords.MyCoords;
import GameObjects.*;
import Geom.Point3D;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * This class calculates the path for each pacman
 */
final public class ShortestPathAlgo
{
    private ShortestPathAlgo()
    {
    }

    /**
     * Best pacman to move
     */
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

    /**
     * Main alg, calc close pacman then eat fruit
     */
    public static void algorithm(Game game)
    {
        ArrayList<Fruit> targets = game.listFruit();
        ArrayList<PacMan> players = game.listPacman();
        Iterator<Fruit> itr = targets.iterator();
        while (itr.hasNext())
        {
            Fruit currentTarget = itr.next();

            PacMan close = fastestToEat(currentTarget, players);
            double timeToEatClose = calcTimeToEat(close, currentTarget.getLocation());
            for (PacMan canidate : players)
            {
                double timeToEatCanidate = calcTimeToEat(canidate, currentTarget.getLocation());
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

            Point3D asd = new Point3D(close.getLocation());
            PacMan temp = new PacMan(close);
            Point3D point;

            for (int i = 0; i < steps; i++)
            {
                point = nextPointByRadius(asd, close.getLocation(), currentTarget.getLocation(), speed);
                if (!point.equals(close.getLocation()))
                {
                    close.updateLocation(point);
                    p.addPointToPath(close.getLocation(), (int) calcTimeToEat(temp, point));
                }
                else
                {
                    p.addPointToPath(currentTarget.getLocation(), (int) timeToEatClose);
                    break;
                }
            }
            currentTarget.setEaten();
            itr.remove();
        }
        System.out.println("Total game time: " + game.getTimeForGame());
        game.updateLongestTime();
        game.resetPacPost();
    }

    /**
     * Time to eat for each pac
     */
    private static double calcTimeToEat(PacMan close, Point3D fruitLocation)
    {
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

    /**
     * Where to move the next pacman
     */
    public static Point3D nextPointByRadius(Point3D asd, Point3D pac, Point3D fruit, double speed)
    {
        Point3D current = MyCoords.vector3D(pac, fruit);
        double currentLength = current.distance3D(0, 0, 0);
        double realLength = MyCoords.vector3D(asd, fruit).distance3D(0, 0, 0);
        double relative = (currentLength - speed) / realLength;
        relative = 1 - relative;
        if (relative > 1)
        {
            return pac;
        }
        Point3D ratioVector = new Point3D(current.x() * relative, current.y() * relative, current.z() * relative);

        return MyCoords.add(pac, ratioVector);
    }
}