package day4;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import util.AdventUtils;

public class Day4Task1Main {

	private static final Pattern PATTERN = Pattern.compile("#(\\d+)");

	public static void main(String[] args) {
		try {
			Map<Integer, List<LogEntry>> grouped = new HashMap<>();
			List<LogEntry> entries = AdventUtils.getStringInput(4).stream().map(line -> LogEntry.parseString(line))
					.sorted((e1, e2) -> e1.getTime().compareTo(e2.getTime())).collect(Collectors.toList());

			int currentGuard = -1;
			for (LogEntry entry : entries) {
				Matcher matcher = PATTERN.matcher(entry.getEvent());
				if (matcher.matches()) {
					currentGuard = Integer.valueOf(matcher.group(1));
				}

				List<LogEntry> e = grouped.get(currentGuard);

				if (e == null) {
					e = new ArrayList<>();
				}

				e.add(entry);
				grouped.put(currentGuard, e);
			}
			
			List<Shift> shifts = new ArrayList<>();

			for (Entry<Integer, List<LogEntry>> entry : grouped.entrySet()) {
				Shift shift = new Shift();
				shifts.add(shift);

				shift.setGuardId(entry.getKey());

				LogEntry last = null;
				LocalDateTime lastTime = null;
				Event lastEvent = null;
				for (LogEntry logEntry : entry.getValue()) {
					LocalDateTime time = logEntry.getTime().toInstant().atZone(ZoneId.systemDefault())
							.toLocalDateTime();
					Event event = new Event();
					if (last != null) {
						if (lastTime.getHour() != 0) {
							LocalDateTime from = LocalDateTime.of(time.getYear(), time.getMonth(), time.getDayOfMonth(),
									0, 0);
							lastEvent.setFrom(from);
						} else {
							lastEvent.setFrom(lastTime);
						}
						if (lastTime.getHour() < 1) {
							lastEvent.setTo(time);
						} else {
							LocalDateTime to = LocalDateTime.of(time.getYear(), time.getMonth(), time.getDayOfMonth(),
									0, 59);
							lastEvent.setTo(to);
							break;
						}

						shift.getEvents().add(event);

						last = null;
						lastTime = null;
						lastEvent = event;
						continue;
					}
					last = logEntry;
					lastTime = time;
					lastEvent = event;
				}
				System.out.println(shift);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
