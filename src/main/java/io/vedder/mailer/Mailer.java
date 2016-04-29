package io.vedder.mailer;

import java.util.List;
import java.util.Properties;
import java.util.Random;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class Mailer implements Runnable {
	private final List<Email> personalInfos;
	private final long timeDelay;

	private final String username;
	private final String password;

	private final Random r = new Random();

	private final String mimeType;

	public Mailer(String username, String password, List<Email> personalInfos, long timeDelay, String mimeType) {
		this.username = username;
		this.password = password;

		this.personalInfos = personalInfos;
		this.timeDelay = timeDelay;

		this.mimeType = mimeType;

	}

	@Override
	public void run() {

		System.out.println("Authenticating...");

		Properties props = new Properties();

		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.socketFactory.port", "465");
		props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.port", "465");

		Session session = Session.getDefaultInstance(props, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		});

		System.out.println("Authentication complete!");

		for (Email info : personalInfos) {
			try {

				Message message = new MimeMessage(session);
				message.setFrom(new InternetAddress(username));
				message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(info.getToEmailAddress()));
				message.setSubject(info.getMessage().getSubject());
				message.setContent(info.getMessage().getBody(), this.mimeType);

				Transport.send(message);

				System.out.println("Sent message to: " + info.getToEmailAddress());

				try {
					Thread.sleep(this.timeDelay + (long) r.nextInt(2000));
				} catch (Exception e) {
					e.printStackTrace();
				}

			} catch (MessagingException e) {
				throw new RuntimeException(e);
			}
		}

	}
}
