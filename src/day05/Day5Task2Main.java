package day05;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import util.AdventUtils;

public class Day5Task2Main {

	public static void main(String[] args) {

		char[] alpha = new char[] { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q',
				'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z' };

		try {

			char[] input = AdventUtils.getStringInput(5).get(0).toCharArray();

			Integer shortestLength = null;

			for (char letter : alpha) {

				char upperLetter = Character.toUpperCase(letter);

				List<Character> chars = new ArrayList<>(input.length);

				for (int i = 0; i < input.length; i++) {
					if (input[i] != upperLetter && input[i] != letter) {
						chars.add(input[i]);
					}
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

				if (shortestLength == null || shortestLength > chars.size()) {
					shortestLength = chars.size();
				}
			}

			AdventUtils.publishResult(5, 2, shortestLength);

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
