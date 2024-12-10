package hu.domparse.g50nco;

import org.w3c.dom.*;
import javax.xml.parsers.*;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;

public class DOMReadG50NCO {
    public static void main(String[] args) {
        try {
            // Beolvasás
            File xmlFile = new File("XML_G50NCO.xml");

            if (!xmlFile.exists()) {
                System.out.println("The file not found.");
                return;
            }

            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(xmlFile);
            doc.getDocumentElement().normalize();

            Element rootElement = doc.getDocumentElement();
            System.out.println("Root element: " + rootElement.getNodeName());
            System.out.println();

            // Blokkos formátum konzolra és fájlba
            try (PrintWriter writer = new PrintWriter(new FileWriter("Formatted_XML_Output.txt"))) {
                WriteOutContent(rootElement, "", writer);
                System.out.println("\nData saved to 'Formatted_XML_Output.txt'.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void WriteOutContent(Node node, String indent, PrintWriter writer) {
        if (node.getNodeType() == Node.ELEMENT_NODE) {
            // Kiírás a konzolra és a fájlba
            String elementStart = indent + "Element: " + node.getNodeName();
            System.out.println(elementStart);
            writer.println(elementStart);

            // Attribútumok kiírása
            if (node.hasAttributes()) {
                NamedNodeMap attrib = node.getAttributes();
                for (int i = 0; i < attrib.getLength(); i++) {
                    Node attribute = attrib.item(i);
                    String attributeData = indent + "  Attribute: " + attribute.getNodeName() + " = " + attribute.getNodeValue();
                    System.out.println(attributeData);
                    writer.println(attributeData);
                }
            }

            // Gyermekelemek és szöveg kiírása
            if (node.hasChildNodes()) {
                NodeList childList = node.getChildNodes();
                for (int i = 0; i < childList.getLength(); i++) {
                    Node child = childList.item(i);
                    WriteOutContent(child, indent + "  ", writer);
                }
            }
        } else if (node.getNodeType() == Node.TEXT_NODE) {
            String data = node.getNodeValue().trim();
            if (!data.isEmpty()) {
                String textData = indent + "Text: " + data;
                System.out.println(textData);
                writer.println(textData);
            }
        }
    }
}