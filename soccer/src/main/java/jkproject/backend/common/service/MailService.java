package jkproject.backend.common.service;

import java.io.UnsupportedEncodingException;

import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import jkproject.backend.common.exception.ApplicationException;
import jkproject.backend.common.exception.enums.ErrorCode;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MailService {
	private final JavaMailSender javaMailSender;
	private String ePw;

	//TODO Async로 작동하게 하면 좋을듯
	public MimeMessage createMessage(String to) throws UnsupportedEncodingException, MessagingException {
		MimeMessage message = javaMailSender.createMimeMessage();
		message.addRecipients(Message.RecipientType.TO, to);
		message.setSubject("SFN 임시 비밀번호입니다.");

		String msg = "";
		msg += "<div style='margin:100px;'>";
		msg +=
			"<div align='center' style='border:1px solid black; font-family:verdana';>";
		msg += "<h3 style='color:blue;'>임시 비밀번호입니다.</h3>";
		msg += "<div style='font-size:130%'>";
		msg += "CODE : <strong>";
		msg += ePw + "</strong><div><br/> ";
		msg += "</div>";
		message.setText(msg, "utf-8", "html");
		message.setFrom(new InternetAddress("jolri24@gmail.com", "SFN_admin"));

		return message;
	}

	public void sendMessage(String to, String pw) {
		this.ePw = pw;
		MimeMessage message;
		try {
			message = createMessage(to);
		} catch (UnsupportedEncodingException | MessagingException e) {
			e.printStackTrace();
			throw new ApplicationException(ErrorCode.INVALID_CREATE_MAIL);
		}
		try {
			javaMailSender.send(message);
		} catch (MailException e) {
			e.printStackTrace();
			throw new ApplicationException(ErrorCode.INVALID_SEND_MAIL);
		}
	}
}
