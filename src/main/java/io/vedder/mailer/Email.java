package io.vedder.mailer;

import java.text.MessageFormat;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Email {
	private final String toEmailAddress;
	private final Message message;

	public Email(String toEmailAddress, Message email) {
		if (!toEmailAddress.contains("@")) {
			throw new IllegalArgumentException("Invalid Email passed: \"" + toEmailAddress + "\"");
		}
		this.toEmailAddress = toEmailAddress;
		this.message = email;
	}

	public static List<Email> buildAll(String personalInfoFilePath, Message unformattedEmail) {
		return FileUtils.readFileLines(personalInfoFilePath).stream().filter(l -> !l.isEmpty()).map(l -> {
			List<String> personalLines = Arrays.asList(l.split(","));
			String toEmailAddress = personalLines.get(0);
			List<String> replacements = personalLines.subList(1, personalLines.size()).stream().map(String::trim)
					.collect(Collectors.toList());
			Message formattedEmail = unformattedEmail.format(replacements.toArray());
			return new Email(toEmailAddress, formattedEmail);
		}).collect(Collectors.toList());
	}

	public String getToEmailAddress() {
		return toEmailAddress;
	}

	public Message getMessage() {
		return message;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("\n======\n");
		sb.append("To: " + this.getToEmailAddress() + "\n");
		sb.append("Subj: " + this.message.getSubject() + "\n");
		sb.append(this.message.getBody() + "\n");
		sb.append("======\n");
		return sb.toString();
	}

}
