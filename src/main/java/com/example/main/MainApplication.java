package com.example.main;

import com.google.gson.Gson;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;
import java.util.logging.Logger;

@SpringBootApplication
public class MainApplication {

	public static void main(String[] args) {
		SpringApplication.run(MainApplication.class, args);
	}
	@Bean
	public JavaMailSender getJavaMailSender() {
		JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
//		mailSender.setHost("smtp.mail.ru");
		mailSender.setHost("smtp.yandex.ru");
//		mailSender.setPort(465);
		mailSender.setPort(587);

//		mailSender.setUsername("egormorozov-yandex@mail.ru");
		mailSender.setUsername("egoridzeN@yandex.ru");
//		mailSender.setPassword("tMrWg1GKqRgik4db19ne");
		mailSender.setPassword("ngtgfzuzdqkvfkfz");

		Properties props = mailSender.getJavaMailProperties();
		props.put("mail.transport.protocol", "smtp");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.debug", "true");

		return mailSender;
	}

}
