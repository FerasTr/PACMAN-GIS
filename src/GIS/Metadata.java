package GIS;

import Geom.Point3D;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * This class holds the information about the WIFI network from one line of the CSV folder.
 */
public class Metadata implements Meta_data
{
    private String mac;
    private String ssid;
    private String auth_mode;
    private String first_seen;
    private String channel;
    private String rssi;
    private String accuracy_meters;
    private String type;

    public Metadata(String mac, String ssid, String auth_mode, String first_seen, String channel, String rssi,
                    String accuracy_meters, String type)
    {
        this.mac = mac;
        this.ssid = ssid;
        this.auth_mode = auth_mode;
        this.first_seen = first_seen;
        this.channel = channel;
        this.rssi = rssi;
        this.accuracy_meters = accuracy_meters;
        this.type = type;
    }

    /**
     * Use the first-seen as the date of creation for viewing on the placemark
     *
     * @return First-seen time in milli seconds.
     */
    @Override
    public long getUTC()
    {
        Calendar whole_date = null;
        try
        {
            // Format the date into YYYY-MM-DD HH:MM:SS
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date_recorded = format.parse(this.first_seen);
            whole_date = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
            whole_date.setTime(date_recorded);
        }
        catch (ParseException e)
        {
            e.printStackTrace();
        }
        return whole_date.getTimeInMillis();
    }

    /**
     * Used for viewing the metadata
     *
     * @return String of the information
     */
    @Override
    public String toString()
    {
        String metadata_string = "[MAC: " + this.mac + ", SSID: " + this.ssid + ", AuthMode: " + this.auth_mode + ", "
                + "FirstSeen: " + this.first_seen + ", Channel: " + this.channel + ", RSSI: " + this.rssi + ", " +
                "AccuracyMeters: " + this.accuracy_meters + ", Type: " + this.type + "]";
        return metadata_string;
    }

    public String getMAC()
    {
        return mac;
    }

    public String getSSID()
    {
        return ssid;
    }

    public String getAuthMode()
    {
        return auth_mode;
    }

    public String getFirstSeen()
    {
        return first_seen;
    }

    public String getChannel()
    {
        return channel;
    }

    public String getRSSI()
    {
        return rssi;
    }

    public String getAccuracyMeters()
    {
        return accuracy_meters;
    }

    public String getType()
    {
        return type;
    }

    @Override
    public Point3D get_Orientation()
    {
        return null;
    }
}
