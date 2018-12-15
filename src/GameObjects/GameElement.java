package GameObjects;

import Geom.Geom_element;

public interface GameElement
{
    public Geom_element getLocation();

    public Meta_data getData();

    public String toString();
}
