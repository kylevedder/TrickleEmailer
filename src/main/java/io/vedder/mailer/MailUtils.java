package io.vedder.mailer;

import java.util.Calendar;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class MailUtils {
	public static void sendEmail(String recipient, String emailBody) {
		final String sender = "iwritebadcode@gmail.com";
		final String passwd = "mealsonwheels";
		Properties props = new Properties();
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.socketFactory.port", "465");
		props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.port", "465");

		Session session = Session.getDefaultInstance(props, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(sender, passwd);
			}
		});

		Calendar c = Calendar.getInstance();
		int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);
		int monthN = c.get(Calendar.MONTH);
		int dayOfMonth = c.get(Calendar.DAY_OF_MONTH);

		String[] date = formatDates(dayOfWeek, monthN).split(" ");

		try {

			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(sender));
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipient));
			message.setSubject("Daily menu digest for " + date[0] + ", " + date[1] + " " + dayOfMonth);
			message.setContent(emailBody, "text/html");

			Transport.send(message);

			System.out.println("Done");

		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}

	}

	public static String formatDates(int dayOfW, int monthN) {
		String day = "";
		String month = "";
		switch (dayOfW) {
		case 1:
			day = "Sun";
			break;
		case 2:
			day = "Mon";
			break;
		case 3:
			day = "Tues";
			break;
		case 4:
			day = "Wed";
			break;
		case 5:
			day = "Thurs";
			break;
		case 6:
			day = "Fri";
			break;
		case 7:
			day = "Sat";
			break;
		default:
			break;
		}
		switch (monthN) {
		case 1:
			month = "January";
			break;
		case 2:
			month = "February";
			break;
		case 3:
			month = "March";
			break;
		case 4:
			month = "April";
			break;
		case 5:
			month = "May";
			break;
		case 6:
			month = "June";
			break;
		case 7:
			month = "July";
			break;
		case 8:
			month = "August";
			break;
		case 9:
			month = "September";
			break;
		case 10:
			month = "October";
			break;
		case 11:
			month = "November";
			break;
		case 12:
			month = "December";
			break;
		default:
			break;
		}
		return day + " " + month;
	}
}
