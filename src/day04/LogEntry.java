package day04;

public class LogEntry {
	private String time;
	private int hour;
	private int minute;
	private String event;

	public static LogEntry parseString(String raw) {
		LogEntry entry = new LogEntry();
		String[] parts = raw.split("\\] ");

		entry.setTime(parts[0].substring(1, parts[0].length()));
		entry.setHour(Integer
				.parseInt(entry.getTime().substring(entry.getTime().length() - 5, entry.getTime().length() - 3)));
		entry.setMinute(
				Integer.parseInt(entry.getTime().substring(entry.getTime().length() - 2, entry.getTime().length())));

		entry.setEvent(parts[1]);

		return entry;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public int getHour() {
		return hour;
	}

	public void setHour(int hour) {
		this.hour = hour;
	}

	public int getMinute() {
		return minute;
	}

	public void setMinute(int minute) {
		this.minute = minute;
	}

	public String getEvent() {
		return event;
	}

	public void setEvent(String event) {
		this.event = event;
	}

}
