package com.itsherman.common.email.example;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * @author yumiaoxia
 * created in 2019/8/20
 * auditor: /
 * audited in /
 */
public class DemoAssembler {

    private String templateUrl;
    private DemoMessageMeta demoMessageMeta;

    public String getTemplateUrl() {
        return templateUrl;
    }

    public void setTemplateUrl(String templateUrl) {
        this.templateUrl = templateUrl;
    }

    public DemoMessageMeta getDemoMessageMeta() {
        return demoMessageMeta;
    }

    public void setDemoMessageMeta(DemoMessageMeta demoMessageMeta) {
        this.demoMessageMeta = demoMessageMeta;
    }

    public String assembleEmailMessage() throws ParserConfigurationException, IOException, SAXException, TransformerException {
        DocumentBuilderFactory fc = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = fc.newDocumentBuilder();
        Document document = db.parse(getTemplateUrl());
        Element root = document.getDocumentElement();
        listAll(root);

        TransformerFactory tf = TransformerFactory.newInstance();
        Transformer t = tf.newTransformer();
        t.setOutputProperty("encoding", "UTF-8");
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        t.transform(new DOMSource(document), new StreamResult(bos));
        String xmlStr = bos.toString();
        return xmlStr;
    }

    private void listAll(Element element) {
        NodeList childNodes = element.getChildNodes();
        if (childNodes.getLength() > 1) {
            for (int i = 0; i < childNodes.getLength(); i++) {
                Node child = childNodes.item(i);
                if (child instanceof Element) {
                    Element childElement = (Element) child;
                    listAll(childElement);
                }
            }
        } else {
            String idVal = element.getAttribute("id");
            DemoMessageMeta messageMeta = getDemoMessageMeta();
            if (idVal.equals("name")) {
                element.setTextContent(messageMeta.getName());
            } else if (idVal.equals("message")) {
                element.setTextContent(messageMeta.getMessage());
            } else if (idVal.equals("time")) {
                element.setTextContent(messageMeta.getCreateTime());
            }
        }

    }
}
