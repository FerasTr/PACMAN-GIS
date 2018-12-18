package GameObjects;

import FileHandling.ReadCSV;
import FileHandling.WriteCSV;

import java.io.File;
import java.util.*;

public class Game implements Set<GameElement>
{
    private Set<GameElement> objects;

    public Game()
    {
        objects = new LinkedHashSet<GameElement>();
    }

    public Game(String csv_file)
    {
        objects = ReadCSV.parsing(csv_file);
    }

    public Game(File csv_file)
    {
        objects = ReadCSV.parsing(csv_file.getAbsolutePath());
    }

    public void saveToCSV()
    {
        WriteCSV.WriteToCSV(this);
    }

    @Override
    public int size()
    {
        return this.objects.size();
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

    @Override
    public boolean isEmpty()
    {
        return this.objects.isEmpty();
    }

    @Override
    public boolean contains(Object o)
    {
        return this.objects.contains(o);
    }

    @Override
    public Iterator<GameElement> iterator()
    {
        return this.objects.iterator();
    }

    @Override
    public Object[] toArray()
    {
        return this.objects.toArray();
    }

    @Override
    public <T> T[] toArray(T[] a)
    {
        return this.objects.toArray(a);
    }

    @Override
    public boolean add(GameElement gameElement)
    {
        return this.objects.add(gameElement);
    }

    @Override
    public boolean remove(Object o)
    {
        return this.objects.remove(o);
    }

    @Override
    public boolean containsAll(Collection<?> c)
    {
        return this.objects.containsAll(c);
    }

    @Override
    public boolean addAll(Collection<? extends GameElement> c)
    {
        return this.objects.addAll(c);
    }

    @Override
    public boolean retainAll(Collection<?> c)
    {
        return this.objects.retainAll(c);
    }

    @Override
    public boolean removeAll(Collection<?> c)
    {
        return this.objects.removeAll(c);
    }

    @Override
    public void clear()
    {
        this.objects.clear();
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
}
