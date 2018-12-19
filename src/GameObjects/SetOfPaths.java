package GameObjects;

import java.util.*;

public class SetOfPaths implements Set<Path>
{
    ArrayList<Path> pathForGame;

    public SetOfPaths()
    {
        pathForGame = new ArrayList<>();
    }

    @Override
    public int size()
    {
        return pathForGame.size();
    }

    @Override
    public boolean isEmpty()
    {
        return pathForGame.isEmpty();
    }

    @Override
    public boolean contains(Object o)
    {
        return pathForGame.contains(o);
    }

    @Override
    public Iterator<Path> iterator()
    {
        return pathForGame.iterator();
    }

    @Override
    public Object[] toArray()
    {
        return pathForGame.toArray();
    }

    @Override
    public <T> T[] toArray(T[] a)
    {
        return pathForGame.toArray(a);
    }

    @Override
    public boolean add(Path point3DS)
    {
        return pathForGame.add(point3DS);
    }

    @Override
    public boolean remove(Object o)
    {
        return pathForGame.remove(o);
    }

    @Override
    public boolean containsAll(Collection<?> c)
    {
        return pathForGame.containsAll(c);
    }

    @Override
    public boolean addAll(Collection<? extends Path> c)
    {
        return pathForGame.addAll(c);
    }

    @Override
    public boolean retainAll(Collection<?> c)
    {
        return pathForGame.retainAll(c);
    }

    @Override
    public boolean removeAll(Collection<?> c)
    {
        return pathForGame.retainAll(c);
    }

    @Override
    public void clear()
    {
        pathForGame.clear();
    }
}
