package util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.net.URL;
import java.util.Collections;
import java.util.Formatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class AdventUtils {
	private static final String EXTRA_FILE_FORMAT = "extra_task%d_%s.txt";
	private static final String RESULT_FILE_FORMAT = "result_task%d.txt";
	private static final String INPUT_FILE_FORMAT = "day%s/input.txt";

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

	public static void writeResult(int day, int task, String result) throws IOException {
		writeFile(result, getResultFilePath(day, task));
	}

	public static void publishExtra(int day, int task, String result, String extraName) throws IOException {
		System.out.println(result);
		writeExtra(day, task, result, extraName);
	}

	public static void writeExtra(int day, int task, String result, String extraName) throws IOException {
		writeFile(result, getExtraFilePath(day, task, extraName));
	}

	public static void eraseExtraFile(int day, int task, String extraName) throws IOException {
		writeExtra(day, task, "", extraName);
	}

	public static void writeNewExtraLine(int day, int task, String result, String extraName) throws IOException {
		Writer w = null;
		try {
			w = new BufferedWriter(new FileWriter(new File(getExtraFilePath(day, task, extraName)), true));
			w.write(result);
		} finally {
			if (w != null) {
				w.close();
			}
		}
	}

	private static void writeFile(String result, String filePath) throws IOException {
		OutputStream out = null;
		Writer w = null;

		try {
			out = new FileOutputStream(filePath);
			w = new OutputStreamWriter(out);
			w.write(result);
		} finally {
			if (w != null) {
				w.close();
			}
		}
	}

	private static String getResultFilePath(int day) {
		URL url = AdventUtils.class.getResource("../" + AdventUtils.getInputFileName(day));
		String path = url.getPath().replaceAll("/bin/", "/src/");
		return path.substring(0, path.lastIndexOf("/") + 1);
	}

	private static String getResultFilePath(int day, int task) {
		String path = getResultFilePath(day);

		StringBuilder sb = new StringBuilder();
		Formatter formatter = new Formatter(sb);
		formatter.format(RESULT_FILE_FORMAT, task);
		formatter.close();
		return path + sb.toString();
	}

	private static String getExtraFilePath(int day, int task, String extraName) {
		String path = getResultFilePath(day);

		StringBuilder sb = new StringBuilder();
		Formatter formatter = new Formatter(sb);
		formatter.format(EXTRA_FILE_FORMAT, task, extraName);
		formatter.close();
		return path + sb.toString();
	}

	public static BufferedReader getBufferedReader(int day) throws IOException {
		InputStream in = null;
		Reader r = null;

		URL url = AdventUtils.class.getResource("../" + AdventUtils.getInputFileName(day));
		in = new FileInputStream(url.getPath().replaceAll("/bin/", "/src/"));
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
				br = getBufferedReader(day);
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
		formatter.format(INPUT_FILE_FORMAT, printNum(day, 2));
		formatter.close();
		return sb.toString();
	}

	public static String printNum(int num, int length) {
		String formated = String.valueOf(num);

		while (formated.length() < length) {
			formated = " " + formated;
		}

		return formated;
	}

}
