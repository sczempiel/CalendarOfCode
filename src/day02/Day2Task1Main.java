package day02;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import util.AdventUtils;
import util.Touple;

public class Day2Task1Main {

	public static void main(String[] args) {
		try {
			int twos = 0;
			int threes = 0;
			List<String> codes = AdventUtils.getStringInput(2);
			for (String code : codes) {
				Touple<Boolean, Boolean> result = countLetters(code);
				if (result.getLeft()) {
					twos++;
				}
				if (result.getRight()) {
					threes++;
				}
			}
			AdventUtils.publishResult(2, 1, twos * threes);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	private static Touple<Boolean, Boolean> countLetters(String code) {
		Map<Character, Integer> counts = new HashMap<>();
		final Touple<Boolean, Boolean> result = new Touple<>(false, false);

		for (char letter : code.toCharArray()) {
			Integer count = counts.get(letter);
			if (count == null) {
				count = 1;
			} else {
				count++;
			}
			counts.put(letter, count);
		}

		counts.values().stream().forEach(count -> {
			if (count == 2) {
				result.setLeft(true);
			} else if (count == 3) {
				result.setRight(true);
			}
		});
		return result;
	}

}
