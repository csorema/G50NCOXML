package src;

import java.io.File;
import org.w3c.dom.*;
import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

public class DOMWrite_G50NCO {
    public static void main(String[] args) {
        try {

            Document doc = createSampleXML();

            Element rootElement = doc.getDocumentElement();
            System.out.println("Root element: " + rootElement.getNodeName());
            WriteOutContent(rootElement, "");

            writeToFile(doc, "XMLTaskG50NCO/2_feladat/src/XML_G50NCO.xml"); // Create new xml file

            System.out.println("Updated version of XML saved into XML_G50NCO.xml file");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static Document createSampleXML() {
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder;
        Document doc = null;

        try {
            dBuilder = dbFactory.newDocumentBuilder();
            doc = dBuilder.newDocument();

            Element rootElement = doc.createElement("employees");
            doc.appendChild(rootElement);

            Element employee = createEmployeeElement(doc, "E001", "R001", "Miklós", "M123", "ABC123456", "1990-05-15", "No");
            rootElement.appendChild(employee);

        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
        return doc;
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

    private static Element createEmployeeElement(Document doc, String id, String work, String fullName, String medicalNumber,
                                                 String bankAccountNumber, String dateOfBirth, String isLeader) {
        Element employee = doc.createElement("employee");
        employee.setAttribute("id", id);
        employee.setAttribute("work", work);

        createElementWithValue(doc, employee, "fullName", fullName);
        createElementWithValue(doc, employee, "medicalNumber", medicalNumber);
        createElementWithValue(doc, employee, "bankAccountNumber", bankAccountNumber);
        createElementWithValue(doc, employee, "dateOfBirth", dateOfBirth);
        createElementWithValue(doc, employee, "isLeader", isLeader);

        return employee;
    }

    private static void createElementWithValue(Document doc, Element parentElement, String elementName, String value) {
        Element element = doc.createElement(elementName);
        element.appendChild(doc.createTextNode(value));
        parentElement.appendChild(element);
    }
}