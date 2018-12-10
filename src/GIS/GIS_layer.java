package GIS;

import java.util.Set;

/**
 * This interface is holds elemnts (line) in a layer (filer).
 */
public interface GIS_layer extends Set<GIS_element>
{
    public Meta_data get_Meta_data();
}
