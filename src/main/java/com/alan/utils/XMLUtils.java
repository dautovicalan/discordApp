package com.alan.utils;

import com.alan.businessLayer.ConversationManager;
import com.alan.businessLayer.SettingsManager;
import com.alan.businessLayer.UserManager;
import com.alan.models.ResolutionType;
import com.alan.models.Settings;
import com.alan.models.TextMessage;
import com.alan.models.User;
import javafx.scene.image.Image;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

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
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public final class XMLUtils {

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
        userInfo.setAttribute("pid", String.valueOf(loggedInUser.getUserId()));

        Element firstName = xmlDocument.createElement("firstName");
        firstName.appendChild(xmlDocument.createTextNode(loggedInUser.getFirstName()));
        userInfo.appendChild(firstName);

        Element lastName = xmlDocument.createElement("lastName");
        lastName.appendChild(xmlDocument.createTextNode(loggedInUser.getLastName()));
        userInfo.appendChild(lastName);

        if (loggedInUser.getUserPicture() != null){
            Element userPicturePath = xmlDocument.createElement("userPicturePath");
            userPicturePath.appendChild(xmlDocument.createTextNode(loggedInUser.getUserPicture().getUrl()));
            userInfo.appendChild(userPicturePath);
        }

        Element loggedInDate = xmlDocument.createElement("loggedInDate");
        loggedInDate.appendChild(xmlDocument.createTextNode(LocalDateTime.now().toString()));
        userInfo.appendChild(loggedInDate);


        root.appendChild(userInfo);
    }

    private static void addUserMessagesNodes(Element root) {
        List<TextMessage> allMessages = ConversationManager.getConversation().getAllMessages();
        Element userMessages = xmlDocument.createElement("userMessages");

        allMessages.forEach(msg -> {
            Element singleMessage = xmlDocument.createElement("message");
            singleMessage.setAttribute("createdOn", msg.getCreatedOn().toString());

            Element content = xmlDocument.createElement("content");
            content.appendChild(xmlDocument.createTextNode(msg.getMessageContent()));

            singleMessage.appendChild(content);

            Element sentBy = xmlDocument.createElement("sentBy");
            Element firstName = xmlDocument.createElement("firstName");
            Element lastName = xmlDocument.createElement("lastName");

            firstName.appendChild(xmlDocument.createTextNode(msg.getMessageSender().getFirstName()));
            lastName.appendChild(xmlDocument.createTextNode(msg.getMessageSender().getLastName()));

            sentBy.appendChild(firstName);
            sentBy.appendChild(lastName);
            singleMessage.appendChild(sentBy);

            userMessages.appendChild(singleMessage);
        });

        root.appendChild(userMessages);
    }

    private static void addAppSettingsNode(Element root) {
        Settings currenSettings = SettingsManager.getCurrenSettings();
        Element appSettings = xmlDocument.createElement("appSettings");
        Element resolution = xmlDocument.createElement("resolution");
        resolution.appendChild(xmlDocument.createTextNode(currenSettings.getResolutionType().toString()));

        appSettings.appendChild(resolution);

        root.appendChild(appSettings);
    }

    public static void loadConfigurationFromXmlFile() throws IOException, SAXException {

        Document parsedDocument = documentBuilder.parse(new File("testXml.xml"));
        parsedDocument.getDocumentElement().normalize();

        parseUserInfo(parsedDocument);
        parseUserMessages(parsedDocument);
        parseAppSettings(parsedDocument);
    }

    private static void parseUserInfo(Document parsedDocument) throws IOException {

        NodeList list = parsedDocument.getElementsByTagName("userinfo");
        for (int temp = 0; temp < list.getLength(); temp++){
            Node node = list.item(temp);
            if (node.getNodeType() == Node.ELEMENT_NODE){
                Element element = (Element) node;
                String firstName = element.getElementsByTagName("firstName").item(0).getTextContent();
                String lastName = element.getElementsByTagName("lastName").item(0).getTextContent();
                String userPicturePath = element.getElementsByTagName("userPicturePath").item(0).getTextContent();
                if (userPicturePath.isEmpty()){
                    userPicturePath = "/Users/alandautovic/Development/Uni_Projects/discordApp/defaultPicture.jpg";
                }
                UserManager.setLoggedInUser(new User(firstName, lastName));
                UserManager.getLoggedInUser().setUserPicture(new Image(userPicturePath));
            }
        }
    }

    private static void parseUserMessages(Document parsedDocument) {

        List<TextMessage> textMessages = new ArrayList<>();
        NodeList list = parsedDocument.getElementsByTagName("message");

        for (int temp = 0; temp < list.getLength(); temp++){
            Node node = list.item(temp);
            if (node.getNodeType() == Node.ELEMENT_NODE){
                Element element = (Element) node;

                LocalDateTime createdOn = LocalDateTime.parse(element.getAttribute("createdOn"));
                String content = element.getElementsByTagName("content").item(0).getTextContent();

                NodeList childNodes = element.getChildNodes();
                String firstName = "";
                String lastName = "";
                for (int i = 0; i < childNodes.getLength(); i++) {
                    Node tempNode = list.item(temp);
                    if (node.getNodeType() == Node.ELEMENT_NODE){
                        Element sentBy = (Element) tempNode;
                        sentBy.getElementsByTagName("firstName").item(0).getTextContent();
                        sentBy.getElementsByTagName("lastName").item(0).getTextContent();
                    }
                }
                textMessages.add(new TextMessage(content, createdOn, new User(firstName, lastName)));
            }
        }

        textMessages.forEach(msg -> {
            ConversationManager.addMessage(msg);
        });

    }

    private static void parseAppSettings(Document parsedDocument) {
        NodeList list = parsedDocument.getElementsByTagName("appSettings");
        for (int temp = 0; temp < list.getLength(); temp++){
            Node node = list.item(temp);
            if (node.getNodeType() == Node.ELEMENT_NODE){
                Element element = (Element) node;
                ResolutionType resolution = ResolutionType
                        .valueOf(element.getElementsByTagName("resolution").item(0).getTextContent());
                SettingsManager.changeCurrentSettings(new Settings(resolution));
            }
        }
    }

}
