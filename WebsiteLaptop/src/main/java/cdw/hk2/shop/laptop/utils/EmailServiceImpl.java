package cdw.hk2.shop.laptop.utils;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

@Component
public class EmailServiceImpl   {
	@Autowired
	private JavaMailSender javaMailSender;
	public void sendMail(String content,String theme,String user) throws Exception {
		 MimeMessage message = javaMailSender.createMimeMessage();
	        MimeMessageHelper helper = new MimeMessageHelper(message);
	        helper.setTo(user);
	        helper.setText(content);
	        helper.setSubject(theme);
	        javaMailSender.send(message);
	}
}
