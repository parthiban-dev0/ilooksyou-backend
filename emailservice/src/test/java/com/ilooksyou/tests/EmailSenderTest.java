package com.ilooksyou.tests;

import com.ilooksyou.service.EmailSender;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
public class EmailSenderTest {

    @Autowired
    private EmailSender emailSender;

    @Test
    public void sendMailTest(){
        emailSender.sendMail();
        Assertions.assertTrue(true);
    }
}
