package SaxG0NCO1203;

import org.xml.sax.*;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;

public class SaxNEPTUNKOD {
    public static void main(String[] args) {
        try {
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser saxParser = factory.newSAXParser();

            SaxHandler handler = new SaxHandler();

            saxParser.parse(new File("G50NCO_kurzusfelvetel.xml"), handler);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static class SaxHandler extends DefaultHandler {
        @Override
        public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
            System.out.print("start " + qName);

            for (int i = 0; i < attributes.getLength(); i++) {
                System.out.print(" {" + attributes.getQName(i) + ":" + attributes.getValue(i) + "}");
            }

            System.out.println();
        }

        @Override
        public void endElement(String uri, String localName, String qName) throws SAXException {
            System.out.println("end " + qName);
        }

        @Override
        public void characters(char[] ch, int start, int length) throws SAXException {
            String content = new String(ch, start, length).trim();
            if (!content.isEmpty()) {
                System.out.println(content);
            }
        }
    }
}
