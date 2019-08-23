package com.itsherman.common.email;

import com.itsherman.common.email.example.EmailExample;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.IOException;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CommonEmailApplicationTests {

    @Autowired
    private EmailExample emailExample;

    @Test
    public void sendSimpleEmail() {
        emailExample.sendSimpleEmail();
    }

    @Test
    public void sendTemplateEmail() {
        try {
            emailExample.sendTemplateEmail();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (TransformerException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
