package day4;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class LogEntry {
	private Date time;
	private String event;

	public static LogEntry parseString(String raw) {
		LogEntry entry = new LogEntry();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");

		String[] parts = raw.split("\\] ");
		try {
			entry.setTime(format.parse(parts[0].substring(1, parts[0].length())));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		entry.setEvent(parts[1]);

		return entry;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	public String getEvent() {
		return event;
	}

	public void setEvent(String event) {
		this.event = event;
	}

}
