package Coords;

import Geom.Point3D;

/**
 * This class implements coords_converter interface, it is used for basic calculations on points.
 */

public final class MyCoords
{
    public final static double EARTH_RADIUS = 6371000;

    private MyCoords()
    {
    }

    /**
     * Will convert to degree then add the points.
     *
     * @param gps                   Point in degree as used in GameObjects.
     * @param local_vector_in_meter vector used for calculations.
     * @return new gps point after transformation.
     */
    public static Point3D add(Point3D gps, Point3D local_vector_in_meter)
    {
        // Longitude normal:
        double longitude_normal = Math.cos(Math.toRadians(gps.x()));
        // Converting vector from Cartesian to Degree:
        // To Radian:
        double x_to_r = Math.asin(local_vector_in_meter.x() / EARTH_RADIUS);
        double y_to_theta = Math.asin(local_vector_in_meter.y() / (EARTH_RADIUS * longitude_normal));
        // To Degree:
        x_to_r = Math.toDegrees(x_to_r);
        y_to_theta = Math.toDegrees(y_to_theta);
        // Moving the point:
        double latitude = gps.x() + x_to_r;
        double longitude = gps.y() + y_to_theta;
        double altitude = gps.z() + local_vector_in_meter.z();
        // Returning new gps coordinates:
        return new Point3D(latitude, longitude, altitude);
    }

    /**
     * Calculates the distance between two gps (degree) points, uses vector3D to calculate the vector between them.
     *
     * @param gps0 point 1 in degree
     * @param gps1 point 2 in degree
     * @return the distance in meters between the two points
     */

    public static double distance3d(Point3D gps0, Point3D gps1)
    {
        // Using the built-in 3D distance function from Point3D:
        Point3D distance_3d = vector3D(gps0, gps1);
        double distance = distance_3d.distance3D(new Point3D(0, 0, 0));
        return distance;
    }

    /**
     * Calculates the vector between gps0 and gps1.
     *
     * @param gps0 point 1 in degree
     * @param gps1 point 2 in degree
     * @return new point which represents the vector,
     */

    public static Point3D vector3D(Point3D gps0, Point3D gps1)
    {
        // Longitude normal:
        double longitude_normal = Math.cos(Math.toRadians(gps0.x()));
        // Calculating delta X and delta Y:
        double diff_x = gps1.x() - gps0.x();
        double diff_y = gps1.y() - gps0.y();
        double diff_z = gps1.z() - gps0.z();
        // Converting to Cartesian coordinates:
        // To Radian:
        diff_x = Math.toRadians(diff_x);
        diff_y = Math.toRadians(diff_y);
        // To Cartesian:
        diff_x = Math.sin(diff_x) * EARTH_RADIUS;
        diff_y = Math.sin(diff_y) * EARTH_RADIUS * longitude_normal;
        // Return in meters:
        return new Point3D(diff_x, diff_y, diff_z);
    }

    /**
     * Calculates the yaw, elevation, distance of gps1 relative to gps0
     *
     * @param gps0 point 1 in degree to be taken as anchor
     * @param gps1 point 2 in degree
     * @return array of size 3 which holds the values.
     */

    public static double[] azimuth_elevation_dist(Point3D gps0, Point3D gps1)
    {
        // Building array [YAW, PITCH, DISTANCE]
        double[] yaw_pitch_dist = new double[3];
        // Calculating the yaw:
        double lat0_radian = Math.toRadians(gps0.x());
        double lat1_radian = Math.toRadians(gps1.x());
        double diff_lon_radian = Math.toRadians((gps1.y() - gps0.y()));
        double num = Math.sin(diff_lon_radian) * Math.cos(lat1_radian);
        double den =
                (Math.cos(lat0_radian) * Math.sin(lat1_radian)) - (Math.sin(lat0_radian) * Math.cos(lat1_radian) * Math.cos(diff_lon_radian));
        double bearing_radian = Math.atan2(num, den);
        double bearing_degree = Math.toDegrees(bearing_radian);
        yaw_pitch_dist[0] = (bearing_degree + 360) % 360;
        // Calculating the distance:
        yaw_pitch_dist[2] = distance3d(gps0, gps1);
        // Calculating the pitch:
        yaw_pitch_dist[1] = Math.toDegrees(Math.asin((gps1.z() - gps0.z()) / yaw_pitch_dist[2]));
        return yaw_pitch_dist;
    }

    /**
     * Check if the gps point (in degree) is correct i.e: Latitude = [-180,+180], Longitude = [-90,+90], Latitude =
     * [-450, +inf].
     *
     * @param p point in degree to be checked
     * @return true if valid, false otherwise.
     */

    public static boolean isValid_GPS_Point(Point3D p)
    {
        // Checking the following : Latitude = [-180,+180], Longitude = [-90,+90], Latitude = [-450, +inf]
        if (p.x() >= -180 && p.x() <= 180)
        {
            if (p.y() >= -90 && p.y() <= 90)
            {
                if (p.z() >= -450)
                {
                    return true;
                }
            }
        }
        // Return false if one violets the limit.
        return false;
    }


}
