package GameObjects;

/**
 * This class holds the information about the element (Fruit, PACMAN) from one line of the CSV folder.
 */
public class Metadata implements Meta_data
{
    private String type;
    private String id;
    private String speed;
    private String radius;

    public Metadata(String type, String id, String speed, String radius)
    {
        this.type = type;
        this.id = id;
        this.speed = speed;
        this.radius = radius;
    }

    public Metadata(String type, String id, String speed)
    {
        this.type = type;
        this.id = id;
        this.speed = speed;
        this.radius = "X";
    }
    /**
     * Used for viewing the metadata
     *
     * @return String of the information
     */

    @Override
    public String toString()
    {
        String metadata_string = "[TYPE: " + this.type + ", ID: " + this.id + ", SPEED: " + this.speed + ", " +
                "RADIUS: " + this.radius + "]";
        return metadata_string;
    }

    public String getType()
    {
        return type;
    }

    public String getId()
    {
        return id;
    }

    public String getSpeed()
    {
        return speed;
    }

    public String getRadius()
    {
        return radius;
    }
}
