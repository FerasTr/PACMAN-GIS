package File_format;

import GIS.*;
import Geom.Point3D;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.TimeZone;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * Static class used for building a KML from a GIS project or layer (FOLDER OR FILE).
 * NOTE: FILE OUTPUT IS IN THE MAIN DIRECTORY OF THE PROJECT.
 * KML file is an XML file, therefore using javax.xml library we can build feed it elements such as header and
 * TextNodes.
 * Building the XML file was done using the following links:
 * https://docs.oracle.com/javase/8/docs/technotes/guides/xml/index.html
 * https://developers.google.com/kml/documentation/kml_tut
 * http://www.mkyong.com/java/how-to-create-xml-file-in-java-dom/
 * https://stackoverflow.com/questions/23520208/how-to-create-xml-file-with-specific-structure-in-java
 * https://stackoverflow.com/questions/1937684/java-saving-streamresult-to-a-file
 * https://stackoverflow.com/questions/12120055/conversion-of-csv-to-xml-with-java
 */
public final class WriteToKML
{
    // Params used for building each element.
    private static DocumentBuilderFactory dom_factory;
    private static DocumentBuilder dom_builder;
    private static Document dom_document;
    private static Element xml_element;
    private static GIS_layer file;

    private WriteToKML()
    {
    }

    /**
     * Converts project (MULTIPLE FILES) to one KML file.
     *
     * @param project Object of many files.
     */
    public static void FromFolder(GIS_project project)
    {
        init();
        try
        {
            Convert(project);
        }
        catch (TransformerException e)
        {
            e.printStackTrace();
        }
    }

    /**
     * Converts layer (ONE FILE) to one KML file.
     *
     * @param layer Object of one file.
     */
    public static void FromFile(GIS_layer layer)
    {
        file = layer;
        init();
        Convert(layer);
        try
        {
            output(false);
        }
        catch (TransformerException e)
        {
            e.printStackTrace();
        }
    }

    /**
     * Used for initializing the building blocks of the XML file.
     */
    private static void init()
    {
        dom_factory = DocumentBuilderFactory.newInstance();
        try
        {
            dom_builder = dom_factory.newDocumentBuilder();
        }
        catch (ParserConfigurationException e)
        {
            e.printStackTrace();
        }
        dom_document = dom_builder.newDocument();
        BuildXMLHeader();
    }

    /**
     * Iterate over the project and call layer converter on each file.
     *
     * @param project Contains layers.
     * @throws TransformerException
     */
    private static void Convert(GIS_project project) throws TransformerException
    {
        Iterator<GIS_layer> iter_folder = project.iterator();
        while (iter_folder.hasNext())
        {
            GIS_layer layer = iter_folder.next();
            Convert(layer);
        }
        output(true);
    }

    /**
     * Builds the XML elements for one file.
     *
     * @param layer
     */
    private static void Convert(GIS_layer layer)
    {
        Iterator<GIS_element> iter_file = layer.iterator();
        while (iter_file.hasNext())
        {
            create_kml((Element_GIS) iter_file.next());
        }
    }

    /**
     * Creates KML by building specific XML elements such as Placemark, name, description, TimeStamp, styleUrl,
     * Point, coordinates.
     *
     * @param line One element which contains one point and its metadata.
     */
    private static void create_kml(Element_GIS line)
    {
        // USED THE PROVIDED KML EXAMPLE FILE AS A STARTING POINT \\

        Element placemark, name, desc, time, format, point, coor;
        // Take the information from the GIS element object.
        Metadata metadata = (Metadata) line.getData();
        Point3D location = (Point3D) line.getGeom();
        // Builds the placemark element (USED FOR VIEWING ONE MARK IN GOOGLE EARTH)
        placemark = dom_document.createElement("Placemark");
        xml_element.appendChild(placemark);
        name = dom_document.createElement("name");
        // Using CDATA instead of comments
        name.appendChild(dom_document.createCDATASection(metadata.getSSID()));
        placemark.appendChild(name);
        // Filling the description, TimeStamp, styleUrl, Point and coordinates from the data.
        desc = dom_document.createElement("description");
        desc.appendChild(dom_document.createCDATASection("MAC: <b>" + metadata.getMAC() + "</b><br/>Capabilities" +
                ": " + "<b>" + metadata.getAuthMode() + "</b><br/>Channel: <b>" + metadata.getChannel() + "</b><br" + "/>Timestamp: " + "<b>" + metadata.getUTC() + "</b><br/>Date: <b>" + metadata.getFirstSeen() + "</b>"));
        placemark.appendChild(desc);
        time = dom_document.createElement("TimeStamp");
        placemark.appendChild(time);
        format = dom_document.createElement("styleUrl");
        format.appendChild(dom_document.createTextNode(colorByChannel(metadata.getChannel())));
        placemark.appendChild(format);
        point = dom_document.createElement("Point");
        placemark.appendChild(point);
        coor = dom_document.createElement("coordinates");
        point.appendChild(coor);
        coor.appendChild(dom_document.createTextNode(location.y() + "," + location.x()));
    }

    /**
     * Build the header, set the icon, name and first line as provided in the KML documintation, see:
     * https://developers.google.com/kml/documentation/kml_tut
     */
    private static void BuildXMLHeader()
    {
        Element header, header_doc, header_name;
        header = dom_document.createElement("kml");
        header.setAttribute("xmlns", "http://www.opengis.net/kml/2.2");
        dom_document.appendChild(header);
        header_doc = dom_document.createElement("Document");
        header.appendChild(header_doc);
        style("green", "http://maps.google.com/mapfiles/ms/icons/green-dot.png", header_doc);
        style("red", "http://maps.google.com/mapfiles/ms/icons/red-dot.png", header_doc);
        style("blue", "http://maps.google.com/mapfiles/ms/icons/blue-dot.png", header_doc);
        xml_element = dom_document.createElement("Folder");
        header_doc.appendChild(xml_element);
        header_name = dom_document.createElement("name");
        header_name.appendChild(dom_document.createTextNode("Wifi Networks"));
        xml_element.appendChild(header_name);
    }

    /**
     * Creates a style element which defines the #color for a placemark.
     *
     * @param name_of_color
     * @param icon_src
     * @param header_style_element
     */
    private static void style(String name_of_color, String icon_src, Element header_style_element)
    {

        Element style, icon_look, icon, url;
        style = dom_document.createElement("Style");
        style.setAttribute("id", name_of_color);
        header_style_element.appendChild(style);
        icon_look = dom_document.createElement("IconStyle");
        style.appendChild(icon_look);
        icon = dom_document.createElement("Icon");
        icon_look.appendChild(icon);
        url = dom_document.createElement("href");
        url.appendChild(dom_document.createTextNode(icon_src));
        icon.appendChild(url);
    }

    /**
     * The color of each mark was decided by the WIFI channel (2.4 or 5 or others GHz)
     *
     * @param channel
     * @return
     */
    private static String colorByChannel(String channel)
    {
        double chan = Double.parseDouble(channel);
        if (chan <= 11)
        {
            // For 2.4.
            return "#red";
        }
        else if (chan <= 50)
        {
            // For 5.
            return "#green";
        }
        else
        {
            // Almost no point is blue because most places only use 2.4 or 5.
            return "#blue";
        }
    }

    /**
     * This method is responsible for creating the file from the XML elements, therefore it uses TransformerFactory.
     *
     * @param folder Checks if the current caller is for a project (FOLDER) or layer (FILE).
     *               The output is in the project directory.
     * @throws TransformerException
     */
    private static void output(boolean folder) throws TransformerException
    {
        TransformerFactory tranFacory = TransformerFactory.newInstance();
        Transformer aTransformer = tranFacory.newTransformer();
        DOMSource src = new DOMSource(dom_document);
        aTransformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
        aTransformer.setOutputProperty(OutputKeys.INDENT, "yes");
        StreamResult result = null;
        if (folder)
        {
            // Naming the folder using the WigleWifi_CurrentTimeInMilSec
            String date_name = getDateName();
            result = new StreamResult(new File("WigleWifi_" + date_name + ".kml"));
        }
        else
        {
            // Use passed name as the new name.
            String fileName = ((Layer_GIS) file).getFilename().replace(".csv", ".kml");
            result = new StreamResult(new File(fileName));
        }
        aTransformer.transform(src, result);
    }

    /**
     * Helper method used for naming a folder.
     *
     * @return time in milli seconds as a string.
     */
    private static String getDateName()
    {
        Calendar whole_date = null;
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date_recorded = new Date();
        whole_date = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
        whole_date.setTime(date_recorded);
        return whole_date.getTimeInMillis() + "";
    }
}
