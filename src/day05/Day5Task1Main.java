package day05;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import util.AdventUtils;

public class Day5Task1Main {

	public static void main(String[] args) {

		try {
			char[] input = AdventUtils.getStringInput(5).get(0).toCharArray();

			List<Character> chars = new ArrayList<>(input.length);

			for (int i = 0; i < input.length; i++) {
				chars.add(input[i]);
			}

			boolean removed = false;
			int i = 0;
			do {

				if (i >= chars.size()) {
					i = 0;
					removed = false;
				}

				boolean result = false;
				if (i - 1 > -1) {
					result = checkForRemoval(chars.get(i - 1), chars.get(i));
					if (result) {
						chars.remove(i);
						chars.remove(i - 1);
					}
				}

				if (!result && i + 1 < chars.size()) {
					result = checkForRemoval(chars.get(i + 1), chars.get(i));
					if (result) {
						chars.remove(i + 1);
						chars.remove(i);
					}
				}
				if (result) {
					removed = true;
				}
				i++;

			} while (i < chars.size() || removed);

			AdventUtils.publishResult(5, 1, chars.size());

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static boolean checkForRemoval(Character c1, Character c2) {
		if (Character.toUpperCase(c1) == Character.toUpperCase(c2)
				&& (Character.isUpperCase(c1) && Character.isLowerCase(c2)
						|| Character.isUpperCase(c2) && Character.isLowerCase(c1))) {
			return true;
		}
		return false;
	}

}
