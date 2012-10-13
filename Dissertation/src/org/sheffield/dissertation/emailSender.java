/*
 * Author : Omar Manuel Mercado Casillas
 * Date: 06/08/2012
 * Project: An XML Minute Database
 */
package org.sheffield.dissertation;

import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 * SendEmail Class will send an email to a user using sendEmail()
 * 
 */
public class emailSender {

	private static final String EMAIL_USERNAME = "email";
	private static final String EMAIL_PASSWORD = "password";

	public void newMail() {
		emailSender e = new emailSender();
		System.out.println(e.sendEmail("email",
				"this is a test email", "this is a very basic body"));
	}

	/**
	 * Send an email to one recipient at a time
	 * 
	 * @param toEmailAddress
	 * @param EmailSubject
	 * @param EmailBody
	 * @return
	 */
	public boolean sendEmail(String toEmailAddress, String EmailSubject,
			String EmailBody) {
		boolean ret = false;
		// Set general properties
		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "587");

		// initial the authentication
		Session session = Session.getInstance(props,
				new javax.mail.Authenticator() {
					protected PasswordAuthentication getPasswordAuthentication() {
						return new PasswordAuthentication(EMAIL_USERNAME,
								EMAIL_PASSWORD);
					}
				});

		try {

			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(EMAIL_USERNAME));
			message.setRecipients(Message.RecipientType.TO,
					InternetAddress.parse(toEmailAddress));
			message.setSubject(EmailSubject);
			message.setText(EmailBody);

			Transport.send(message);

			ret = true;

		} catch (MessagingException e) {
			// throw new RuntimeException(e);
			ret = false;
		}
		return ret;
	}
}