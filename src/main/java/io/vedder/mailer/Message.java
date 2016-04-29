package io.vedder.mailer;

import java.text.MessageFormat;
import java.util.List;
import java.util.StringJoiner;

public class Message {
	private final String subject;
	private final String body;

	private Message(String subject, String body) {
		this.subject = subject;
		this.body = body;
	}

	public static Message build(String filePath) {
		List<String> lines = FileUtils.readFileLines(filePath);
		String subject = lines.get(0);

		StringJoiner message = new StringJoiner("\n");
		lines.subList(1, lines.size()).forEach(l -> message.add(l));
		return new Message(subject, message.toString());
	}

	public Message format(Object params[]) {
		MessageFormat subjectFM = new MessageFormat(subject);
		MessageFormat bodyFM = new MessageFormat(body);

		String subject = subjectFM.format(params);
		String body = bodyFM.format(params);

		return new Message(subject, body);
	}

	public String getSubject() {
		return subject;
	}

	public String getBody() {
		return body;
	}

	@Override
	public String toString() {
		return "Email [subject=" + subject + ", body=" + body + "]";
	}
}
