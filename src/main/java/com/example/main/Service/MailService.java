package com.example.main.Service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Service
@RequiredArgsConstructor
public class MailService {
    private final TemplateEngine templateEngine;
    private final JavaMailSender mailSender;
    private final GenerateRandomStringService randomStringService;

    public void sendVerifyLink(String email) throws MessagingException {
        sendMessage(email, "Сonfirm your email", new Context(), "mailMessage");
    }
    private void sendMessage(String to, String subject, Context context, String htmlFile) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setFrom("egoridzeN@yandex.ru");
        helper.setTo(to);
        helper.setSubject(subject);

        String htmlElement = templateEngine.process(htmlFile, context);
        helper.setText(htmlElement, true);
        mailSender.send(message);
    }
}
