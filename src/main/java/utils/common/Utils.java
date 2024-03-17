package utils.common;

import org.apache.commons.io.FileUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.regex.Pattern;

/**
 * The Utils class provides utility methods for various operations.
 */
public final class Utils {
    
    private static volatile Utils instance;
    
    /**
     * Private constructor to prevent instantiation from outside the class.
     */
    private Utils() {
    }
    
    /**
     * Returns the instance of Utils using double-checked locking for thread safety.
     *
     * @return The instance of Utils.
     */
    public static Utils getInstance() {
        if (instance == null) {
            synchronized (Utils.class) {
                if (instance == null) {
                    instance = new Utils();
                }
            }
        }
        return instance;
    }
    
    /**
     * Generates a random string of the specified length.
     *
     * @param length The length of the random string.
     * @return The generated random string.
     */
    public String generateRandomString(int length) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            sb.append((char) ('a' + (int) (Math.random() * 26)));
        }
        return sb.toString();
    }
    
    /**
     * Generates a random number of the specified length.
     *
     * @param length The length of the random number.
     * @return The generated random number.
     */
    public String generateRandomNumber(int length) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            sb.append((int) (Math.random() * 10));
        }
        return sb.toString();
    }
    
    /**
     * Reads the content of an XML file.
     *
     * @param filePath The path of the XML file.
     * @return The content of the XML file.
     * @throws IOException If an I/O error occurs.
     */
    public String getTheXmlFileContent(String filePath) throws IOException {
        File file = new File(filePath);
        return FileUtils.readFileToString(file, "UTF-8");
    }
    
    /**
     * Reads the content of an XML file and replaces a placeholder with a value.
     *
     * @param filePath    The path of the XML file.
     * @param placeholder The placeholder to be replaced.
     * @param value       The value to replace the placeholder.
     * @return The content of the XML file with the placeholder replaced.
     * @throws IOException If an I/O error occurs.
     */
    public String getTheXmlFileContentWithPlaceholderReplaced(String filePath, String placeholder, String value) throws IOException {
        String content = getTheXmlFileContent(filePath);
        content = content.replaceAll(placeholder, value);
        return content;
    }
    
    /**
     * Replaces a value in an XML file.
     *
     * @param filePath The path of the XML file.
     * @param oldValue The value to be replaced.
     * @param newValue The new value to replace the old value.
     * @throws IOException If an I/O error occurs.
     */
    public void replaceValueInXmlFile(String filePath, String oldValue, String newValue) throws IOException {
        File file = new File(filePath);
        if (file.exists()) {
            String content = FileUtils.readFileToString(file, "UTF-8");
            content = content.replaceAll(oldValue, newValue);
            FileUtils.writeStringToFile(file, content, "UTF-8");
        }
    }
    
    /**
     * Sets a value inside XML tags in an XML file.
     *
     * @param filePath The path of the XML file.
     * @param tagName  The name of the XML tag.
     * @param value    The value to be set inside the XML tag.
     * @throws IOException                  If an I/O error occurs.
     * @throws ParserConfigurationException If a parser configuration error occurs.
     * @throws SAXException                 If a SAX error occurs.
     * @throws TransformerException         If a transformer error occurs.
     */
    public void setValueInsideXmlTags(String filePath, String tagName, String value) throws IOException, ParserConfigurationException, SAXException, TransformerException {
        try {
            File xmlFile = new File(filePath);
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            Document doc = docBuilder.parse(xmlFile);
            NodeList nodeList = doc.getElementsByTagName(tagName);
            for (int i = 0; i < nodeList.getLength(); i++) {
                Node node = nodeList.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) node;
                    // Set the new value inside the XML tag
                    element.setTextContent(value);
                }
            }
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(xmlFile);
            transformer.transform(source, result);
        } catch (ParserConfigurationException | SAXException | TransformerException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Returns the current timestamp in the format "yyyy-MM-dd_HH-mm-ss".
     *
     * @return the current timestamp
     */
    public String getTimestamp() {
        return new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss").format(new Date());
    }
    
    /**
     * A function to get the formatted date with hours, minutes, and seconds.
     *
     * @param OffsetDateTime the OffsetDateTime to format
     * @return the formatted date with hours, minutes, and seconds
     */
    public String getFormattedDateWithHoursMinutesSeconds(OffsetDateTime OffsetDateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'");
        return OffsetDateTime.format(formatter);
    }
    
    /**
     * A function to format the given OffsetDateTime to display only the date.
     *
     * @param OffsetDateTime the OffsetDateTime to be formatted
     * @return the formatted date string
     */
    public String getFormattedOnlyDate(OffsetDateTime OffsetDateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return OffsetDateTime.format(formatter);
    }
    
    /**
     * Check if the input string is numeric.
     *
     * @param str the input string to be checked
     * @return true if the input string is numeric, false otherwise
     */
    public boolean isNumeric(String str) {
        Pattern pattern = Pattern.compile("-?\\d+(\\.\\d+)?");
        return pattern.matcher(str).matches();
    }
}
