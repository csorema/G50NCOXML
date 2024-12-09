package src;

import org.w3c.dom.*;

import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import java.io.File;
import java.util.Scanner;

public class DOMModify_G50NCO {
    public static void main(String[] args) {

        try {
            File xmlFile = new File("XMLTaskG50NCO/2_feladat/src/XML_G50NCO.xml");

            if(!xmlFile.exists())
            {
                System.out.println("The file not found.");
                return;
            }

            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(xmlFile);
            doc.getDocumentElement().normalize();

            Scanner sc = new Scanner(System.in);

            System.out.println("Add the name of the element which you want to modify: ");
            String elementName = sc.nextLine();

            System.out.println("Add ID of the element: ");
            String elementID = sc.nextLine();

            System.out.println("Add the name of the attribute: ");
            String propertyName = sc.nextLine();

            System.out.println("Add new value: ");
            String newValue = sc.nextLine();

            if (!modifyElementByID(doc, elementName, elementID, propertyName, newValue))
            {
                System.out.println("You added wrong input!!!");
            }

            sc.close();

            Element rootElement = doc.getDocumentElement();

            WriteOutContent(rootElement, "");

            writeToFile(doc, "XMLTaskG50NCO/2_feladat/src/Modified_XML_G50NCO.xml");

            System.out.println("Data successfully modified.");

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
                if (element.getAttribute("id").equalsIgnoreCase(elementID))
                {
                    NodeList childNodes = element.getElementsByTagName(propertyName);
                    Node childNode = childNodes.item(0);
                    childNode.setTextContent(newValue);
                }

            }
            else {
                return false;
            }
        }
        return true;
    }

    private static void WriteOutContent(Node node, String indent) {
        if (node.getNodeType() == Node.ELEMENT_NODE) {
            System.out.print(indent + "<" + node.getNodeName());


            if (node.hasAttributes()) {
                NamedNodeMap attrib = node.getAttributes();
                for (int i = 0; i < attrib.getLength(); i++) {
                    Node attribute = attrib.item(i);
                    System.out.print(" " + attribute.getNodeName() + "=\"" + attribute.getNodeValue() + "\"");
                }
            }

            if (!node.hasChildNodes() || node.getFirstChild().getNodeType() == Node.TEXT_NODE) {

                System.out.print(">");

            } else {
                if(node.toString().startsWith("which_product"))
                {
                    System.out.println(">");

                }
            }

            if (node.hasChildNodes()) {
                NodeList childList = node.getChildNodes();
                boolean hasTextChild = false;
                for (int i = 0; i < childList.getLength(); i++) {
                    Node child = childList.item(i);
                    if (child.getNodeType() == Node.TEXT_NODE && !child.getNodeValue().trim().isEmpty()) {
                        System.out.print(indent + "  " + child.getNodeValue().trim());
                        hasTextChild = true;
                    } else if (child.getNodeType() == Node.ELEMENT_NODE) {
                        WriteOutContent(child, indent + "  ");
                    }
                }
                if (!hasTextChild) {
                    System.out.println(indent + "</" + node.getNodeName() + ">");
                } else {
                    System.out.println(indent + "</" + node.getNodeName() + ">");
                }
            }
        } else if (node.getNodeType() == Node.TEXT_NODE) {
            String data = node.getNodeValue().trim();
            if (!data.isEmpty()) {
                System.out.print(data);
            }
        }
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