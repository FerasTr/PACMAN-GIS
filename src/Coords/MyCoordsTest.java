package Coords;

import Geom.Point3D;

import static org.junit.Assert.*;

public class MyCoordsTest
{
    MyCoords coords;
    Point3D gps0;
    Point3D gps1;

    @org.junit.Before
    public void setUp() throws Exception
    {
        coords = new MyCoords();
        gps0 = new Point3D(32.103315, 35.209039, 670);
        gps1 = new Point3D(32.106352, 35.205225, 650);
    }

    @org.junit.Test
    public void add()
    {
        Point3D vector = new Point3D(337.6989921, -359.2492069, -20);
        Point3D actual = coords.add(gps0, vector);
        System.out.println("Should be gps1: " + actual.toFile());
        assertTrue(actual.close2equals(gps1, 1));
    }

    @org.junit.Test
    public void distance3d()
    {
        final double actual = 493.0523318;
        double dist = coords.distance3d(gps0, gps1);
        System.out.println("Should be equal within TOL of 0.1: " + dist);
        assertEquals(actual, dist, 1);
    }

    @org.junit.Test
    public void vector3D()
    {
        Point3D actual = new Point3D(337.6989920612881, -359.24920693881893, -20.0);
        Point3D vec = coords.vector3D(gps0, gps1);
        System.out.println("Both should be equal: " + vec);
        assertTrue(actual.equals(vec));
    }

    @org.junit.Test
    public void azimuth_elevation_dist()
    {
        double yaw, pitch, dist;
        double actual[] = coords.azimuth_elevation_dist(gps0, gps1);
        double[] expected = {313.16, -2.322852232927616, 493.0523318324134};

        yaw = Math.abs(actual[0] - expected[0]);
        pitch = Math.abs(actual[1] - expected[1]);
        dist = Math.abs(actual[2] - expected[2]);

        System.out.println("All should be within TOL of 0.1: " + yaw + "" + pitch + "" + dist);
        assertTrue(yaw <= 1);
        assertTrue(pitch <= 1);
        assertTrue(dist <= 1);
    }

    @org.junit.Test
    public void isValid_GPS_Point()
    {
        boolean isValid;
        System.out.println("Both are valid, checking: ");
        isValid = coords.isValid_GPS_Point(gps0);
        System.out.println("gps0: " + isValid);
        assertTrue(isValid);

        isValid = coords.isValid_GPS_Point(gps1);
        System.out.println("gps1: " + isValid);
        assertTrue(isValid);

        System.out.println("This is not valid, checking: ");
        isValid = coords.isValid_GPS_Point(new Point3D(181, 90, -500));
        System.out.println("gps2: " + isValid);
        assertFalse(isValid);
    }
}
