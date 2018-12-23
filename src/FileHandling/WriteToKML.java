package FileHandling;

import GameObjects.*;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.awt.*;
import java.io.File;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.TimeZone;

import Geom.secondsPoint3D;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * Static class used for building a KML from a GameObjects project or layer (FOLDER OR FILE).
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
 * http://dagik.org/kml_intro/E/line.html
 */
public final class WriteToKML
{
    // Params used for building each element.
    private static DocumentBuilderFactory dom_factory;
    private static DocumentBuilder dom_builder;
    private static Document dom_document;
    private static Element xml_element;

    private WriteToKML()
    {
    }

    /**
     * Build the game.
     */
    public static void FromGame(Game game)
    {
        init();
        convertGame(game);
        try
        {
            output();
        }
        catch (TransformerException e)
        {
            e.printStackTrace();
        }
    }

    /**
     * Start building.
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
        BuildKMLHeader();
    }

    private static void convertGame(Game game)
    {
        Iterator<GameElement> iter_file = game.iterator();
        while (iter_file.hasNext())
        {
            create_kml(iter_file.next());
        }
    }

    /**
     * Create a placemark for the game object.
     */
    private static void create_kml(GameElement element)
    {

        // USED THE PROVIDED KML EXAMPLE FILE AS A STARTING POINT \\
        double speed = element.getSpeed();
        double radius = element.getRadius();
        String type = element.getType();
        int id = element.getId();
        Element placemark, name, desc, format;
        // Take the information from the Game object.
        // Builds the placemark element (USED FOR VIEWING ONE MARK IN GOOGLE EARTH)
        placemark = dom_document.createElement("Placemark");
        xml_element.appendChild(placemark);

        name = dom_document.createElement("name");
        // Using CDATA instead of comments
        name.appendChild(dom_document.createCDATASection(type));
        placemark.appendChild(name);

        // Filling the description, TimeStamp, styleUrl, Point and coordinates from the data.
        desc = dom_document.createElement("description");
        desc.appendChild(dom_document.createCDATASection("ID: <b>" + id + "</b> <br/>Weight: " + "<b>" + speed + "</b"
                + ">" + "<br/>Radius: <b>" + radius + "</b>"));
        placemark.appendChild(desc);

        if (element instanceof PacMan)
        {
            Element pathPlacemark, pathName, lineString, coord, style, LineStyle, color, width;
            pathPlacemark = dom_document.createElement("Placemark");
            xml_element.appendChild(pathPlacemark);

            pathName = dom_document.createElement("name");
            pathName.appendChild(dom_document.createTextNode("PATH FOR PACMAN " + id));
            pathPlacemark.appendChild(pathName);

            lineString = dom_document.createElement("LineString");
            pathPlacemark.appendChild(lineString);

            coord = dom_document.createElement("coordinates");
            String PointsString = getPointsInPath((PacMan) element, "\n");
            coord.appendChild(dom_document.createTextNode(PointsString));
            lineString.appendChild(coord);

            style = dom_document.createElement("Style");
            pathPlacemark.appendChild(style);

            LineStyle = dom_document.createElement("LineStyle");
            style.appendChild(LineStyle);

            color = dom_document.createElement("color");
            LineStyle.appendChild(color);
            String hex = getColorString((PacMan) element);
            color.appendChild(dom_document.createTextNode(hex));

            width = dom_document.createElement("width");
            width.appendChild(dom_document.createTextNode("5"));
            LineStyle.appendChild(width);
        }

        format = dom_document.createElement("styleUrl");
        format.appendChild(dom_document.createTextNode(iconByType(type)));
        placemark.appendChild(format);

        pointForElement(element, placemark);
    }


    /**
     * Add point of element to the placemark
     */
    private static void pointForElement(GameElement element, Element placemark)
    {
        Element point;
        Element coor;
        point = dom_document.createElement("Point");
        placemark.appendChild(point);
        coor = dom_document.createElement("coordinates");
        point.appendChild(coor);
        coor.appendChild(dom_document.createTextNode(element.getLocation().y() + "," + element.getLocation().x()));
    }

    /**
     * Get the point from the path.
     *
     * @return Points as a string
     */
    private static String getPointsInPath(PacMan element, String split)
    {
        StringBuilder PointsString = new StringBuilder();
        secondsPoint3D p;
        Iterator<secondsPoint3D> points = element.getPath().getPathList().iterator();
        while (points.hasNext())
        {
            p = points.next();
            PointsString.append(p.getPoint().y() + "," + p.getPoint().x());
            PointsString.append(split);
        }
        return PointsString.toString();
    }

    /**
     * Build KML header.
     */
    private static void BuildKMLHeader()
    {
        Element header, header_doc, header_name;
        header = dom_document.createElement("kml");
        header.setAttribute("xmlns", "http://www.opengis.net/kml/2.2");
        dom_document.appendChild(header);
        header_doc = dom_document.createElement("Document");
        header.appendChild(header_doc);
        style("pacman", "https://i.imgur.com/61917e1.png", header_doc);
        style("fruit", "https://i.imgur.com/fkuoimy.png", header_doc);
        xml_element = dom_document.createElement("Folder");
        header_doc.appendChild(xml_element);
        header_name = dom_document.createElement("name");
        header_name.appendChild(dom_document.createTextNode("PACMAN GAME"));
        xml_element.appendChild(header_name);
    }

    /**
     * Build the style for the placemark
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
     * Build color using object type.
     *
     * @param type Object type
     * @return String representing color type
     */
    private static String iconByType(String type)
    {
        if (type.equals("P"))
        {
            // For PACMAN.
            return "#pacman";
        }
        else
        {
            // For Fruit.
            return "#fruit";
        }
    }

    /**
     * Build KML file from the document object.
     *
     * @throws TransformerException
     */
    private static void output() throws TransformerException
    {
        TransformerFactory tranFacory = TransformerFactory.newInstance();
        Transformer aTransformer = tranFacory.newTransformer();
        DOMSource src = new DOMSource(dom_document);
        aTransformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
        aTransformer.setOutputProperty(OutputKeys.INDENT, "yes");
        StreamResult result;
        // Use passed name as the new name.
        String fileName = "pacman_" + getDateName() + ".kml";
        result = new StreamResult(new File("./FilesOut/KML", fileName));
        aTransformer.transform(src, result);
    }

    /**
     * Helper function to color path.
     *
     * @return Color as string
     */
    private static String getColorString(PacMan element)
    {
        Color colorbypath = element.getPath().getColor();
        int r = colorbypath.getRed();
        int g = colorbypath.getGreen();
        int b = colorbypath.getBlue();
        return String.format("#ff%02x%02x%02x", r, g, b);
    }

    /**
     * Helper function used for naming the file.
     *
     * @return time as string
     */
    private static String getDateName()
    {
        Calendar whole_date;
        Date date_recorded = new Date();
        whole_date = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
        whole_date.setTime(date_recorded);
        return whole_date.getTimeInMillis() + "";
    }
}
