package GIS;

import java.util.Set;

/**
 * This class holds layers (files) in a project (folder).
 */
public interface GIS_project extends Set<GIS_layer>
{
    public Meta_data get_Meta_data();

}
