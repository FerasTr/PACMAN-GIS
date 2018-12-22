package GameObjects;

import Geom.Point3D;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class MapTest
{
    Map m;
    Point3D ArielRoundAboutPixel;
    Point3D ArielBuilding9Pixel;
    Point3D ArielRoundAboutGPS;
    Point3D ArielBuilding9GPS;

    @Before
    public void setUp() throws Exception
    {
        m = MapInit.ArielMap();

        ArielRoundAboutPixel = new Point3D(727, 545);
        ArielRoundAboutGPS = new Point3D(32.102496, 35.207480);

        ArielBuilding9Pixel = new Point3D(961, 409);
        ArielBuilding9GPS = new Point3D(32.103292, 35.209104);
    }

    @Test
    public void pixleToGPS()
    {
        Point3D d = m.pixleToGPS(ArielBuilding9Pixel);
        Point3D p = m.pixleToGPS(ArielRoundAboutPixel);


        System.out.println("Building 9 in GPS: [32.103292, 35.209104] Actual: " + d.toFile());
        assertEquals(32.103292, d.x(), 0.0001);
        assertEquals(35.209104, d.y(), 0.0001);

        System.out.println("Roundabout in GPS: [32.102496, 35.207480] Actual: " + p.toFile());
        assertEquals(32.102496, p.x(), 0.0001);
        assertEquals(35.207480, p.y(), 0.0001);
    }

    @Test
    public void gpsToPixle()
    {
        Point3D d = m.gpsToPixle(ArielBuilding9GPS);
        Point3D p = m.gpsToPixle(ArielRoundAboutGPS);


        System.out.println("Building 9 in pixel: [961, 409] Actual: " + d.toFile());
        assertEquals(961, d.x(), 50);
        assertEquals(409, d.y(), 50);

        System.out.println("Roundabout in pixel: [727, 545] Actual: " + p.toFile());
        assertEquals(727, p.x(), 50);
        assertEquals(545, p.y(), 50);
    }

    @Test
    public void distBetweenPixles()
    {

        double len = m.distBetweenPixles(ArielRoundAboutPixel, ArielBuilding9Pixel);
        System.out.println("Approximated to be: " + 180 + " Actual: " + len);

        assertEquals(180, len, 20);
    }
}
