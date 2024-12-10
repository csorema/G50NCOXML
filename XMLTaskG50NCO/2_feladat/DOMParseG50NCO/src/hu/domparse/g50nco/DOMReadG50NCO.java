package hu.domparse.g50nco; 
 
import org.w3c.dom.*;  import javax.xml.parsers.*; import java.io.File;  
 
public class DOMReadG50NCO {  
    public static void main(String[] args) {  
          try {  
  
            File xmlFile = new File("XML_G50NCO.xml");  
  
            if(!xmlFile.exists())  
            {  
                System.out.println("The file not found.");                 
                return;  
            }  
  
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();  
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();  
            Document doc = dBuilder.parse(xmlFile);    
            doc.getDocumentElement().normalize();  
  
            Element rootElement = doc.getDocumentElement();  
            System.out.println("Root    element:    "   + rootElement.getNodeName());  
  
            WriteOutContent(rootElement, "");  
  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
      }   
    
    private static void WriteOutContent(Node node, String indent) {
    	if (node.getNodeType() == Node.ELEMENT_NODE) { 
            System.out.println(indent + node.getNodeName());  
  
        if (node.hasAttributes()) {  
            NamedNodeMap attrib = node.getAttributes();             
            for (int i = 0; i < attrib.getLength(); i++) {  
                Node attribute = attrib.item(i);  
                System.out.println(indent + attribute.getNodeName() + " = " + attribute.getNodeValue());  
                }  
            }  
  
            if (node.hasChildNodes()) {  
                NodeList childList = node.getChildNodes();                 
                for (int i = 0; i < childList.getLength(); i++) {  
                    Node child = childList.item(i);  
                    WriteOutContent(child, indent + "  ");  
                }  
            }  
        } else if (node.getNodeType() == Node.TEXT_NODE) {             
        	String data = node.getNodeValue().trim();             
        	if (!data.isEmpty()) { 
                System.out.println(indent + data);  
            }  
        }  
    }  
  
}  
