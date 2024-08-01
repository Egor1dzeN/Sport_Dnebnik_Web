package com.example.main.Service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class EmailService {
    private final TemplateEngine templateEngine;
    private final JavaMailSender mailSender;
    private final GenerateRandomStringService randomStringService;

    public void sendVerifyLink(String email) throws MessagingException {
        Context context = new Context();
        context.setVariable("access_token", randomStringService.generateRandomString());
        sendMessage(email, "Сonfirm your email",context, "mailMessage");
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

    public boolean isValidEmail(String email) {
        Pattern pattern = Pattern.compile("\\w+([\\.-]?\\w+)*@\\w+([\\.-]?\\w+)*\\.\\w{2,4}");

        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
}
