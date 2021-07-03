package Core;

import org.openqa.selenium.By;
import org.openqa.selenium.InvalidArgumentException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;


import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;
import java.io.File;

public class ReadXML {

    private static String locator;
    private static String selector;

    public static String getSelector() {
        return selector;
    }

    public static void setSelector(String selector) {
        ReadXML.selector = selector;
    }

    public static String getLocator() {
        return locator;
    }

    public static void setLocator(String loc) {
        ReadXML.locator = loc;
    }

    public static void fetchElementsFromXML(String page, String element) {

        try {
            File file = new File(System.getProperty("user.dir") + "/src/test/resources" +
                    File.separatorChar + "/ObjectRepository.xml");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dbBuilder;

            dbBuilder = dbFactory.newDocumentBuilder();

            Document doc = dbBuilder.parse(file);
            doc.getDocumentElement().normalize();

            XPath xPath = XPathFactory.newInstance().newXPath();

            String expression = "/Application/Page[@name='" + page + "']/Element[@name='" + element + "']";
            NodeList nodeList = (NodeList) xPath.compile(expression).evaluate(doc, XPathConstants.NODESET);

            for (int i = 0; i < nodeList.getLength(); i++) {
                Node nNode = nodeList.item(i);

                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;
                    setLocator(eElement.getAttribute("locator"));
                    setSelector(eElement.getAttribute("selector"));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static By getElementLocator(String page, String element) {
        fetchElementsFromXML(page, element);

        switch (getLocator().toUpperCase()) {
            case "ID":
                return By.id(getSelector());
            case "CLASS":
                return By.className(getSelector());
            case "TAG":
                return By.tagName(getSelector());
            case "LINKTEXT":
                return By.linkText(getSelector());
            case "PARTIALLINKTEXT":
                return By.partialLinkText(getSelector());
            case "NAME":
                return By.name(getSelector());
            case "XPATH":
                return By.xpath(getSelector());
            case "CSS":
                return By.cssSelector(getSelector());
            default:
                System.out.println("Incorrect Locator for element : " + element + " on Page : " + page);
                throw new InvalidArgumentException("Incorrect locator found");
        }
    }

}
