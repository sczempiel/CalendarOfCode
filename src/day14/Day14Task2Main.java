package day14;

import java.io.IOException;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import util.AdventUtils;

public class Day14Task2Main {

	public static void main(String[] args) {

		try {
			long start = System.currentTimeMillis();
			
			char[] input = AdventUtils.getStringInput(14).get(0).toCharArray();
			int lengthTerm = input.length;
			int[] termination = new int[lengthTerm];

			for (int i = 0; i < lengthTerm; i++) {
				termination[i] = Integer.parseInt(String.valueOf(input[i]));
			}

			List<Integer> scores = new ArrayList<>();
			scores.add(3);
			scores.add(7);

			int elv1 = 0;
			int elv2 = 1;

			int count = -1;

			int iterations = 0;

			while (count == -1) {

				int current1 = scores.get(elv1);
				int current2 = scores.get(elv2);

				int score = current1 + current2;
				int added = 1;

				if (score > 9) {
					scores.add(1);
					scores.add(score - 10);
					added++;
				} else {
					scores.add(score);
				}
				int length = scores.size();

				elv1 = getRealIndex(length, elv1 + current1 + 1);
				elv2 = getRealIndex(length, elv2 + current2 + 1);

				if (length > lengthTerm) {
					for (int a = 0; a < added; a++) {

						int scoresPointer = length - lengthTerm - a;
						boolean match = true;

						for (int i = 0; i < lengthTerm; i++) {
							if (scores.get(scoresPointer + i) != termination[i]) {
								match = false;
								break;
							}
						}

						if (match) {
							count = scoresPointer;
							break;
						}
					}
				}

				iterations++;
			}
			System.out.println(NumberFormat.getInstance(Locale.GERMANY).format(iterations) + " iterations in "
					+ (System.currentTimeMillis() - start) + "ms");
			System.out.println("-----------------------------------------");
			AdventUtils.publishResult(14, 2, count);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static int getRealIndex(int length, int index) {
		while (index < 0) {
			index += length;
		}
		while (index >= length) {
			index -= length;
		}
		return index;
	}
}
