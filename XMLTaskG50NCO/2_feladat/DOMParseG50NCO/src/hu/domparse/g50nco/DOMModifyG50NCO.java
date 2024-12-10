package hu.domparse.g50nco;

import org.w3c.dom.*;

import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.util.Scanner;

public class DOMModifyG50NCO {
    public static void main(String[] args) {

        try {
            File xmlFile = new File("XML_G50NCO.xml");

            if (!xmlFile.exists()) {
                System.out.println("The file not found.");
                return;
            }

            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(xmlFile);
            doc.getDocumentElement().normalize();

            Scanner sc = new Scanner(System.in);

            for (int i = 0; i < 4; i++) {
                System.out.println("Modification " + (i + 1) + ":");
                
                System.out.println("Add the name of the element which you want to modify: ");
                String elementName = sc.nextLine();

                System.out.println("Add ID of the element: ");
                String elementID = sc.nextLine();

                System.out.println("Add the name of the attribute: ");
                String propertyName = sc.nextLine();

                System.out.println("Add new value: ");
                String newValue = sc.nextLine();

                if (!modifyElementByID(doc, elementName, elementID, propertyName, newValue)) {
                    System.out.println("Invalid input for modification " + (i + 1) + "!");
                } else {
                    System.out.println("Modification " + (i + 1) + " completed.");
                }
            }

            sc.close();

            Element rootElement = doc.getDocumentElement();
            writeToFile(doc, "C:/Users/exam03/Desktop/2_feladat/DOMParseG50NCO/src/Modified_XML_G50NCO.xml");

            System.out.println("All modifications successfully completed and saved.");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean modifyElementByID(Document doc, String elementName, String elementID, String propertyName, String newValue) {
        NodeList nodeList = doc.getElementsByTagName(elementName);

        for (int i = 0; i < nodeList.getLength(); i++) {
            Node node = nodeList.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element element = (Element) node;
                if (element.getAttribute("id").equalsIgnoreCase(elementID)) {
                    NodeList childNodes = element.getElementsByTagName(propertyName);
                    if (childNodes.getLength() > 0) {
                        Node childNode = childNodes.item(0);
                        childNode.setTextContent(newValue);
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public static void writeToFile(Document doc, String filename) {
        try {
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");

            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File(filename));
            transformer.transform(source, result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}