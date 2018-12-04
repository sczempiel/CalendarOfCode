package day4;

import java.util.List;

public class Shift {
	private int guardId;
	private List<Event> events;

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Shift [guardId=");
		builder.append(guardId);
		builder.append(", ");
		if (events != null) {
			builder.append("events=");
			builder.append(events);
		}
		builder.append("]");
		return builder.toString();
	}

	public int getGuardId() {
		return guardId;
	}

	public void setGuardId(int guardId) {
		this.guardId = guardId;
	}

	public List<Event> getEvents() {
		return events;
	}

	public void setEvents(List<Event> events) {
		this.events = events;
	}

}
