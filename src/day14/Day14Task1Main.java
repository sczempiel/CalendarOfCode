package day14;

import java.io.IOException;

import util.AdventUtils;
import util.LoopingList;

public class Day14Task1Main {

	private static LoopingList<Integer> scores = new LoopingList<>();

	public static void main(String[] args) {

		try {

			int termination = AdventUtils.getIntegerInput(14).get(0) + 10;
			addScores("37");

			int elv1 = 0;
			int elv2 = 1;

			AdventUtils.eraseExtraFile(14, 1, "generation");
			// printGeneration(elv1, elv2);

			while (termination > scores.size()) {
				int current1 = scores.get(elv1);
				int current2 = scores.get(elv2);

				addScores(String.valueOf(current1 + current2));

				elv1 = scores.getRealIndex(elv1 + current1 + 1);
				elv2 = scores.getRealIndex(elv2 + current2 + 1);
				// printGeneration(elv1, elv2);
			}

			StringBuilder sb = new StringBuilder();
			for (int i = termination - 10; i < termination; i++) {
				sb.append(scores.get(i));
			}

			AdventUtils.publishResult(14, 1, sb.toString());

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unused")
	private static void printGeneration(int elv1, int elv2) throws IOException {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < scores.size(); i++) {
			if (i == elv1) {
				sb.append("(" + scores.get(i) + ")");
			} else if (i == elv2) {
				sb.append("[" + scores.get(i) + "]");
			} else {
				sb.append(" " + scores.get(i) + " ");
			}
		}
		sb.append("\n");
		AdventUtils.publishNewExtraLine(14, 1, sb.toString(), "generation");
	}

	private static void addScores(String digits) {
		for (char digit : digits.toCharArray()) {
			scores.add(charToInt(digit));
		}
	}

	private static int charToInt(char digit) {
		return Integer.parseInt(String.valueOf(digit));
	}

}
