package Core;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.ws.Response;
import java.io.IOException;
import java.io.StringReader;

public class ReadXML {

    public static void fetchElementsFromXML(Response response) throws ParserConfigurationException, IOException, SAXException {

        Document document = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(
                new InputSource(new StringReader(response.toString())));

        NodeList nodeList = document.getDocumentElement().getChildNodes();

        for (int i = 0; i < nodeList.getLength(); i++) {
            Node node = nodeList.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element element = (Element) node;
                if (element.getNodeName().contains("Response")) {
                    System.out.println(element.getElementsByTagName("Savings").item(0).getTextContent());
                    System.out.println(element.getElementsByTagName("NAME").item(0).getTextContent());
                }
            }
        }
    }
}
