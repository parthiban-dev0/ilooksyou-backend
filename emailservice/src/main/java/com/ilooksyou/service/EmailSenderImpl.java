package com.ilooksyou.service;

import com.ilooksyou.model.Template;
import com.ilooksyou.model.dto.EmailSendRequest;
import com.ilooksyou.repository.TemplateRepository;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMailMessage;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;

@FunctionalInterface
interface ThrowingConsumer<T, E extends Exception>{
    void accept(T t) throws E;
}

@Service
@Slf4j
public class EmailSenderImpl implements EmailSender{

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private TemplateRepository templateRepository;

    @Override
    public void sendMail() {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);
        try{
            helper.setTo("parthiban.geetham@gmail.com");
            helper.setText("Hello world");
        }catch (MessagingException messagingException){
            log.error(messagingException.getMessage());
        }
        javaMailSender.send(message);
    }

    @Override
    public void sendMail(EmailSendRequest request) {
        final Template template = templateRepository.findById(request.templateId()).orElseThrow(()->new RuntimeException("Template is not found"));
        var data = Optional.ofNullable(request.data());
        final StringBuilder htmlContent = new StringBuilder(template.getHtmlContent());
        data.ifPresent(d -> {
            d.forEach((key,value) -> {
                int start = htmlContent.indexOf(key);
                if(start == -1){
                    return;
                }
                int end = key.length() + start;
                htmlContent.replace(start,end,value.toString());
            });
        });

        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);
        try {
            helper.setTo(request.to().toArray(new String[]{}));
            Optional.ofNullable(request.bcc()).ifPresent(messageExceptionLambda(bcc -> helper.setBcc(bcc.toArray(new String[]{}))));
            Optional.ofNullable(request.cc()).ifPresent(messageExceptionLambda(cc -> helper.setCc(cc.toArray(new String[]{}))));
            Optional.ofNullable(request.replyTo()).ifPresent(messageExceptionLambda(helper::setReplyTo));
            helper.setText(htmlContent.toString(),true);
            helper.setSubject(request.subject());
        }catch (MessagingException messagingException){

        }
    }

    private <T> Consumer<T> messageExceptionLambda(ThrowingConsumer<T, MessagingException> consumer){
        return i -> {
            try{
                consumer.accept(i);
            }catch (MessagingException messagingException){

            }
        };
    }
}
