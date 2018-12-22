package GameObjects;

import FileHandling.ReadCSV;
import FileHandling.WriteCSV;

import java.io.File;
import java.util.*;

/**
 * Game object, holds player and target objects.
 */
public class Game
{
    private ArrayList<GameElement> objects;
    private double timeForGame = 0;
    private double longestTime = -1;

    public Game()
    {
        objects = new ArrayList<>();
    }

    /**
     * Build a game from a csv file name.
     */
    public Game(String csv_file)
    {
        objects = ReadCSV.parsing(csv_file);
    }

    /**
     * Build a game from a csv file.
     */
    public Game(File csv_file)
    {
        objects = ReadCSV.parsing(csv_file.getAbsolutePath());
    }

    /**
     * Copy constructor.
     */
    public Game(Game game)
    {
        this.objects = new ArrayList<>();
        Iterator<GameElement> itr = game.iterator();
        while (itr.hasNext())
        {
            GameElement n = itr.next();
            if (n instanceof PacMan)
            {
                this.add(new PacMan((PacMan) n));

            }
            else
            {
                this.add(new Fruit((Fruit) n));
            }
        }

        this.timeForGame = game.timeForGame;
        this.longestTime = game.longestTime;
    }

    /**
     * Save game to a CSV file.
     */
    public void saveToCSV()
    {
        WriteCSV.WriteToCSV(this);
    }

    public void setTimeForGame(double timeForGame)
    {
        this.timeForGame = timeForGame;
    }

    public void setLongestTime(double longestTime)
    {
        this.longestTime = longestTime;
    }

    public void updateLongestTime()
    {
        longestTime = Integer.MIN_VALUE;
        PacMan toPrint = null;
        for (PacMan p : listPacman())
        {
            double time = p.getPathTime();
            if (time > longestTime)
            {
                longestTime = time;
                toPrint = p;
            }
        }
        if (toPrint != null)
        {
            System.out.println("The path with the longest time is by pacman " + toPrint.getId() + " and it is " + longestTime + " seconds.");
        }
    }

    public double getLongestTime()
    {
        return longestTime;
    }

    public void updateTimeForGame(double seconds)
    {
        timeForGame += seconds;
    }

    public double getTimeForGame()
    {
        return timeForGame;
    }

    public Iterator<GameElement> iterator()
    {
        return objects.iterator();
    }

    public void add(GameElement o)
    {
        objects.add(o);
    }

    public void remove(GameElement o)
    {
        objects.remove(o);
    }


    public int sizePacman()
    {
        int numPacman = 0;
        Iterator<GameElement> itr = this.iterator();
        while (itr.hasNext())
        {
            GameElement gameElement = itr.next();
            if (gameElement instanceof PacMan)
            {
                numPacman++;
            }
        }

        return numPacman;
    }

    public int sizeFruit()
    {
        int numFruit = 0;
        Iterator<GameElement> itr = this.iterator();
        while (itr.hasNext())
        {
            GameElement gameElement = itr.next();
            if (gameElement instanceof Fruit)
            {
                numFruit++;
            }
        }

        return numFruit;
    }

    public ArrayList<PacMan> listPacman()
    {
        ArrayList<PacMan> pacman = new ArrayList();
        Iterator<GameElement> itr = this.iterator();
        while (itr.hasNext())
        {
            GameElement gameElement = itr.next();
            if (gameElement instanceof PacMan)
            {
                pacman.add((PacMan) gameElement);
            }
        }

        return pacman;
    }

    public ArrayList<Fruit> listFruit()
    {
        ArrayList<Fruit> fruit = new ArrayList();
        Iterator<GameElement> itr = this.iterator();
        while (itr.hasNext())
        {
            GameElement gameElement = itr.next();
            if (gameElement instanceof Fruit)
            {
                fruit.add((Fruit) gameElement);
            }
        }

        return fruit;
    }

    public String toString()
    {
        String toPrint = "";
        for (GameElement element : objects)
        {
            toPrint += element.toString() + '\n';
        }
        return toPrint;
    }


    public void resetPaths()
    {
        for (GameElement pacman : objects)
        {

            if (pacman instanceof PacMan)
            {
                ((PacMan) pacman).restartPath();
            }
        }
    }

    /**
     * Reset game.
     */
    public void resetGame()
    {
        objects.clear();
        timeForGame = 0;
        longestTime = -1;
        Fruit.resetCounter();
        PacMan.resetCounter();
    }

    /**
     * Reset players to the starting position.
     */
    public void resetPacPost()
    {
        ArrayList<PacMan> pacs = listPacman();
        for (PacMan pac : pacs)
        {
            pac.resetToStart();
        }
    }
}
