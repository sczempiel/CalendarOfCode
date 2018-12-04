package day4;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import util.AdventUtils;

public class Day4Task2Main {

	private static final Pattern PATTERN = Pattern.compile("#(\\d+)");

	public static void main(String[] args) {
		try {
			List<LogEntry> entries = AdventUtils.getStringInput(4).stream().map(line -> LogEntry.parseString(line))
					.sorted((e1, e2) -> e1.getTime().compareTo(e2.getTime())).collect(Collectors.toList());

			List<Shift> shifts = new ArrayList<>();

			Shift shift = null;
			int lastState = 0;
			int nextStart = 0;
			for (Iterator<LogEntry> it = entries.iterator(); it.hasNext();) {
				LogEntry entry = it.next();
				Matcher matcher = PATTERN.matcher(entry.getEvent());

				if (matcher.find()) {
					if (nextStart != 60 && shift != null) {
						for (int i = nextStart; i < 60; i++) {
							shift.getAwakeStatus()[i] = lastState;
						}
					}

					lastState = 0;
					nextStart = 0;

					shift = new Shift();
					shifts.add(shift);
					shift.setGuardId(Integer.parseInt(matcher.group(1)));
				}

				if (nextStart == 60) {
					continue;
				}

				if (entry.getHour() == 0 || nextStart != 0) {
					int till = entry.getMinute();

					if (entry.getHour() != 0) {
						till = 60;
					}

					for (int i = nextStart; i < till; i++) {
						shift.getAwakeStatus()[i] = lastState;
					}
					nextStart = till;
				}
				if (entry.getEvent().endsWith("asleep")) {
					lastState = 1;
				} else {
					lastState = 0;
				}

				if (!it.hasNext()) {
					for (int i = nextStart; i < 60; i++) {
						shift.getAwakeStatus()[i] = lastState;
					}
				}
			}

			Map<Integer, int[]> sleepyMinutesPerGuard = new HashMap<>();

			for (Shift s : shifts) {
				int[] sleepyMinutes = sleepyMinutesPerGuard.get(s.getGuardId());

				if (sleepyMinutes == null) {
					sleepyMinutes = new int[60];
				}

				for (int i = 0; i < 60; i++) {
					sleepyMinutes[i] += s.getAwakeStatus()[i];
				}

				sleepyMinutesPerGuard.put(s.getGuardId(), sleepyMinutes);
			}

			int mostSleepyGuard = -1;
			int mostSleepyGuardMinuteCounter = -1;
			int mostSleepyGuardMinute = -1;

			for (Entry<Integer, int[]> entry : sleepyMinutesPerGuard.entrySet()) {
				for (int i = 0; i < 60; i++) {
					if (mostSleepyGuardMinuteCounter < entry.getValue()[i]) {
						mostSleepyGuardMinute = i;
						mostSleepyGuardMinuteCounter = entry.getValue()[i];
						mostSleepyGuard = entry.getKey();
					}
				}
			}

			AdventUtils.publishResult(4, 2, mostSleepyGuard * mostSleepyGuardMinute);

		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}
