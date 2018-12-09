package day02;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import util.AdventUtils;

public class Day2Task2Main {

	public static void main(String[] args) {
		try {
			String result = null;
			List<String> codes = new ArrayList<>(AdventUtils.getStringInput(2));
			outer: for (Iterator<String> it = codes.iterator(); it.hasNext();) {
				String code1 = it.next();
				for (String code2 : codes) {
					result = findMatching(code1, code2);
					if (result != null) {
						break outer;
					}
				}
				it.remove();
			}
			AdventUtils.publishResult(2, 2, result);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	private static String findMatching(String code1, String code2) {
		if (code1.equals(code2)) {
			return null;
		}

		Integer diffIndex = null;

		char[] chars1 = code1.toCharArray();
		char[] chars2 = code2.toCharArray();

		for (int i = 0; i < chars1.length; i++) {
			if (chars1[i] != chars2[i]) {
				if (diffIndex != null) {
					return null;
				}
				diffIndex = i;
			}

		}

		return code1.substring(0, diffIndex) + code1.substring(diffIndex + 1, code1.length());
	}

}
