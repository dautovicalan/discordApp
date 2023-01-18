package com.alan.utils;

import com.alan.businessLayer.ConversationManager;
import com.alan.businessLayer.UserManager;
import com.alan.models.TextMessage;
import com.alan.models.User;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

public class XMLUtils {

    private static DocumentBuilderFactory documentBuilderFactory
            = DocumentBuilderFactory.newInstance();
    private static DocumentBuilder documentBuilder;

    static {
        try {
            documentBuilder = documentBuilderFactory.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            throw new RuntimeException(e);
        }
    }

    private static Document xmlDocument = documentBuilder.newDocument();
    public static void saveConfigurationToXmlFile() throws ParserConfigurationException, TransformerException {
        List<TextMessage> allLoggedInUserMessages =
                ConversationManager.getConversation().getAllMessages();


        Element root = xmlDocument.createElement("fakediscord");

        xmlDocument.appendChild(root);

        addUserInfoNode(root);
        addUserMessagesNodes(root);
        addAppSettingsNode(root);


        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        DOMSource domSource = new DOMSource(xmlDocument);
        StreamResult streamResult = new StreamResult(new File("testXml.xml"));

        transformer.transform(domSource, streamResult);

    }

    private static void addUserInfoNode(Element root) {
        User loggedInUser = UserManager.getLoggedInUser();
        Element userInfo = xmlDocument.createElement("userinfo");
        userInfo.setAttribute("id", String.valueOf(loggedInUser.getUserId()));

        Element firstName = xmlDocument.createElement("firstName");
        firstName.appendChild(xmlDocument.createTextNode(loggedInUser.getFirstName()));
        userInfo.appendChild(firstName);

        Element lastName = xmlDocument.createElement("lastName");
        lastName.appendChild(xmlDocument.createTextNode(loggedInUser.getLastName()));
        userInfo.appendChild(lastName);

        Element loggedInDate = xmlDocument.createElement("loggedInDate");
        loggedInDate.appendChild(xmlDocument.createTextNode(LocalDateTime.now().toString()));
        userInfo.appendChild(loggedInDate);

        root.appendChild(userInfo);
    }

    private static void addUserMessagesNodes(Element root) {

    }

    private static void addAppSettingsNode(Element root) {
    }

    public static void loadConfigurationFromXmlFile(){

    }

}
