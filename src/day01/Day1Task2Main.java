package day01;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import util.AdventUtils;

public class Day1Task2Main {

	public static void main(String[] args) {

		Set<Integer> results = new HashSet<>();

		try {
			List<Integer> frequence = AdventUtils.getIntegerInput(1);
			int result = 0;
			int i = 0;
			do {
				results.add(result);
				result += frequence.get(i);
				i++;
				if (i == frequence.size()) {
					i = 0;
				}
			} while (!results.contains(result));
			AdventUtils.publishResult(1, 2, result);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
