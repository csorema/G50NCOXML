package hu.domparse.g50nco;

import java.io.File;
import org.w3c.dom.*;
import javax.xml.parsers.*;

public class DOMWriteG50NCO {
    public static void main(String[] args) {
        try {
            // Beolvassuk az XML fájlt
            File xmlFile = new File("XML_G50NCO.xml");

            if (!xmlFile.exists()) {
                System.out.println("The file not found.");
                return;
            }

            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(xmlFile);
            doc.getDocumentElement().normalize();

            // Gyökérelem kiírása
            Element rootElement = doc.getDocumentElement();
            System.out.println("Root element: " + rootElement.getNodeName());
            System.out.println();

            // Tartalom feldolgozása és kiírása
            writeContent(rootElement, "\t");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void writeContent(Node node, String indent) {
        if (node.getNodeType() == Node.ELEMENT_NODE) {
            System.out.println(indent + "<" + node.getNodeName() + ">");

            // Attribútumok kiírása
            if (node.hasAttributes()) {
                NamedNodeMap attribs = node.getAttributes();
                for (int i = 0; i < attribs.getLength(); i++) {
                    Node attribute = attribs.item(i);
                    System.out.println(indent + "\t@" + attribute.getNodeName() + " = " + attribute.getNodeValue());
                }
            }

            // Gyermekek feldolgozása
            if (node.hasChildNodes()) {
                NodeList childNodes = node.getChildNodes();
                for (int i = 0; i < childNodes.getLength(); i++) {
                    Node child = childNodes.item(i);
                    writeContent(child, indent + "\t");
                }
            }

            System.out.println(indent + "</" + node.getNodeName() + ">");
        } else if (node.getNodeType() == Node.TEXT_NODE) {
            String textContent = node.getNodeValue().trim();
            if (!textContent.isEmpty()) {
                System.out.println(indent + textContent);
            }
        }
    }
}