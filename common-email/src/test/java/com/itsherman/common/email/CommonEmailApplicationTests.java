package com.itsherman.common.email;

import com.itsherman.common.email.example.EmailExample;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CommonEmailApplicationTests {

    @Autowired
    private EmailExample emailExample;

    @Test
    public void contextLoads() {
        emailExample.sendSimpleEmail();
    }

}
