package GameObjects;

import FileHandling.ReadCSV;
import FileHandling.WriteCSV;

import java.io.File;
import java.util.*;

public class Game
{
    private ArrayList<GameElement> objects;
    private double timeForGame = 0;
    private double longestTime = -1;

    public Game()
    {
        objects = new ArrayList<GameElement>();
    }

    public Game(String csv_file)
    {
        objects = ReadCSV.parsing(csv_file);
    }

    public Game(File csv_file)
    {
        objects = ReadCSV.parsing(csv_file.getAbsolutePath());
    }

    public Game(Game game)
    {
        this.objects = new ArrayList<GameElement>();
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
        for (PacMan p : listPacman())
        {
            double time = p.getPathTime();
            if (time > longestTime)
            {
                longestTime = time;
            }
        }
    }

    public double getLongestTime()
    {
        return longestTime;
    }

    public void saveToCSV()
    {
        WriteCSV.WriteToCSV(this);
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

    public void updateTimeForGame(double seconds)
    {
        timeForGame += seconds;
    }

    public double getTimeForGame()
    {
        return timeForGame;
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

    public void resetGame()
    {
        objects.clear();
    }

    public void resetPacPost()
    {
        ArrayList<PacMan> pacs = listPacman();
        for (PacMan pac : pacs)
        {
            pac.resetToStart();
        }
    }
}
