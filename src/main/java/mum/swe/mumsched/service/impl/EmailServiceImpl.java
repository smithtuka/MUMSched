package mum.swe.mumsched.service.impl;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import mum.swe.mumsched.service.EmailService;

@Component
public class EmailServiceImpl implements EmailService {

    @Autowired
    public JavaMailSender emailSender;

	@Value("${spring.mail.username}")
	private String fromAddress;
	
	@Override
	public void sendMessage(String to, String subject, String text) {
        try {

            MimeMessage message = emailSender.createMimeMessage();

            message.setSubject(subject);
            MimeMessageHelper helper;
            helper = new MimeMessageHelper(message, true);
            helper.setFrom(fromAddress);
            helper.setTo(to);
            helper.setText(text, true);

            emailSender.send(message);
        } catch (MailException exception) {
            exception.printStackTrace();
        } catch (MessagingException e) {
			e.printStackTrace();
		}		
	}

}
