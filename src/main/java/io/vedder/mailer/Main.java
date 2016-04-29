package io.vedder.mailer;

import java.util.List;
import java.util.Properties;

public class Main {

	public static final String CRED_USER_PROP = "username";
	public static final String CRED_PASS_PROP = "password";

	public static void main(String args[]) {

		args = new String[] { "creds.properties", "message.txt", "personal.txt", "5000" };

		if (args.length < 4) {
			printArgsAndExit();
		}

		// Grab all CLI params
		String credentialsFile = args[0];
		String messageFile = args[1];
		String personalFile = args[2];
		long timeDelay = Long.parseLong(args[3]);
		String mimeType = (args.length == 5) ? args[4] : "text/plain";

		// Validate CLI params
		if (!FileUtils.fileExists(credentialsFile)) {
			printAndExit("Missing credentials file!");
		}
		if (!FileUtils.fileExists(messageFile)) {
			printAndExit("Missing message file!");
		}
		if (!FileUtils.fileExists(personalFile)) {
			printAndExit("Missing personalization file!");
		}

		// Read params
		Properties creds = FileUtils.readPropertiesFile(credentialsFile);
		String username = creds.getProperty(CRED_USER_PROP);
		String password = creds.getProperty(CRED_PASS_PROP);

		if (username == null) {
			printAndExit(CRED_USER_PROP + " field not found in " + credentialsFile);
		}
		if (password == null) {
			printAndExit(CRED_PASS_PROP + " field not found in " + credentialsFile);
		}

		Message email = Message.build(messageFile);

		List<Email> emailInfos = Email.buildAll(personalFile, email);

		// System.out.println(emailInfos);

		Mailer m = new Mailer(username, password, emailInfos, timeDelay, mimeType);

		// sends the emails
		 m.run();
	}

	private static void printAndExit(String message) {
		System.err.println(message);
		System.exit(-1);
	}

	private static void printArgsAndExit() {
		String message = "Usage:\n"
				+ "<credentials file> <message file> <personalization file> <minumum time delay in milliseconds>\n\n";
		System.out.println(message);
		System.exit(-1);
	}

}
