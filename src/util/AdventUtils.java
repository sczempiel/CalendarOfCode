package util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.util.Collections;
import java.util.Formatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class AdventUtils {

	private static final File BASE_DIR = new File(System.getProperty("user.home") + "/Desktop/adventOfCode");
	private static final String RESULT_FILE_FORMAT = "day%d/task%d.txt";
	private static final String INPUT_FILE_FORMAT = "day%d/input.txt";

	private static Map<Integer, List<String>> inputs = new HashMap<>();

	private AdventUtils() {

	}

	public static void publishResult(int day, int task, int result) throws IOException {
		publishResult(day, task, String.valueOf(result));
	}

	public static void publishResult(int day, int task, String result) throws IOException {
		System.out.println(result);
		writeResult(day, task, result);
	}

	private static void writeResult(int day, int task, String result) throws IOException {
		BASE_DIR.mkdir();

		OutputStream out = null;
		Writer w = null;

		try {
			File resultFile = new File(BASE_DIR, getResultFileName(day, task));
			out = new FileOutputStream(resultFile);
			w = new OutputStreamWriter(out);
			w.write(result);
			System.out.println("Result wrote to: " + resultFile.getAbsolutePath());
		} finally {
			if (w != null) {
				w.close();
			}
		}
	}

	public static int readResult(int day, int task) throws IOException {
		BufferedReader br = null;

		try {
			br = getBufferedReader(new File(BASE_DIR, getResultFileName(day, task)));
			return Integer.valueOf(br.readLine());
		} finally {
			if (br != null) {
				br.close();
			}
		}
	}

	private static String getResultFileName(int day, int task) {
		StringBuilder sb = new StringBuilder();
		Formatter formatter = new Formatter(sb);
		formatter.format(RESULT_FILE_FORMAT, day, task);
		formatter.close();
		return sb.toString();
	}

	public static BufferedReader getBufferedReader(File file) throws IOException {
		InputStream in = null;
		Reader r = null;

		in = new FileInputStream(file);
		r = new InputStreamReader(in);
		return new BufferedReader(r);
	}

	public static List<Integer> getIntegerInput(int day) throws IOException {
		List<Integer> input = getStringInput(day).stream().map(line -> Integer.valueOf(line))
				.collect(Collectors.toList());
		input = Collections.unmodifiableList(input);
		return input;
	}

	public static List<String> getStringInput(int day) throws IOException {
		List<String> input = inputs.get(day);
		if (input == null) {
			BufferedReader br = null;
			try {
				br = getBufferedReader(new File(BASE_DIR, getInputFileName(day)));
				List<String> result = br.lines().collect(Collectors.toList());
				input = Collections.unmodifiableList(result);
				inputs.put(day, input);
			} finally {
				if (br != null) {
					br.close();
				}
			}
		}
		return input;
	}

	private static String getInputFileName(int day) {
		StringBuilder sb = new StringBuilder();
		Formatter formatter = new Formatter(sb);
		formatter.format(INPUT_FILE_FORMAT, day);
		formatter.close();
		return sb.toString();
	}

}
