package hu.domparse.g50nco;
import org.w3c.dom.*;
import javax.xml.parsers.*;
import java.io.File;
import java.util.Scanner;
import javax.xml.xpath.*;

public class DOMQueryG50NCO {
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
            System.out.println("How many elements would you like to query?");
            int numQueries = sc.nextInt();
            sc.nextLine(); 

            for (int i = 1; i <= numQueries; i++) {
                System.out.println("Query " + i + ":");
                System.out.println("Add the name of the element: ");
                String elementName = sc.nextLine();
                System.out.println("Add ID of the element: ");
                String elementID = sc.nextLine();

                selectElementByID(doc, elementName, elementID);
            }
            sc.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void selectElementByID(Document doc, String elementName, String elementID) {
        String xpathQuery = "//" + elementName + "[@id='" + elementID + "']";

        XPathFactory xPathfactory = XPathFactory.newInstance();
        XPath xpath = xPathfactory.newXPath();
        XPathExpression expr = null;
        try {
            expr = xpath.compile(xpathQuery);
        } catch (XPathExpressionException e) {
            System.out.println("Element not found.");
            throw new RuntimeException(e);
        }

        Node result = null;
        try {
            result = (Node) expr.evaluate(doc, XPathConstants.NODE);
        } catch (XPathExpressionException e) {
            System.out.println("Element not found.");
            throw new RuntimeException(e);
        }

        if (result != null) {
            Element element = (Element) result;
            NodeList children = element.getChildNodes();
            System.out.println("Result for element ID " + elementID + ":");
            for (int i = 0; i < children.getLength(); i++) {
                Node child = children.item(i);
                if (child.getNodeType() == Node.ELEMENT_NODE) {
                    System.out.println(child.getNodeName() + ": " + child.getTextContent().trim());
                }
            }
        } else {
            System.out.println("Element not found.");
        }
    }
}