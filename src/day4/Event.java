package day4;

import java.time.LocalDateTime;

public class Event {

	private LocalDateTime from;
	private LocalDateTime to;
	private Type type;

	public LocalDateTime getFrom() {
		return from;
	}

	public void setFrom(LocalDateTime from) {
		this.from = from;
	}

	public LocalDateTime getTo() {
		return to;
	}

	public void setTo(LocalDateTime to) {
		this.to = to;
	}

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}

	public enum Type {
		awake, asleep;
	}

}
