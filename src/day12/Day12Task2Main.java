package day12;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import util.AdventUtils;

public class Day12Task2Main {
	private static final long GENERATIONS = 50000000000l;

	public static void main(String[] args) {

		try {
			List<String> input = AdventUtils.getStringInput(12);

			List<Pot> lastGen = new ArrayList<>();
			List<Pot> currentGen = new ArrayList<>();

			Rule[] rules = new Rule[input.size() - 2];

			String initialPots = input.get(0).split(": ")[1];

			int potPos = 0;
			for (char initial : initialPots.toCharArray()) {
				Pot pot = new Pot();
				pot.setPosition(potPos++);
				pot.setPlanted(isPlanted(initial));
				currentGen.add(pot);
			}

			for (int i = 2; i < input.size(); i++) {
				Rule rule = new Rule();
				String raw = input.get(i).replaceAll(" => ", "");
				rule.setTwoLeft(isPlanted(raw.charAt(0)));
				rule.setLeft(isPlanted(raw.charAt(1)));
				rule.setToCheck(isPlanted(raw.charAt(2)));
				rule.setRight(isPlanted(raw.charAt(3)));
				rule.setTwoRight(isPlanted(raw.charAt(4)));
				rule.setOutcome(isPlanted(raw.charAt(5)));
				rules[i - 2] = rule;
			}

			long growth = 0;

			for (int i = 0; i < GENERATIONS; i++) {
				lastGen = new ArrayList<>();

				potPos = currentGen.get(0).getPosition() - 3;
				while (potPos < 0) {
					lastGen.add(new Pot(potPos++, false));
				}
				lastGen.addAll(currentGen);
				int maxPotPos = currentGen.get(currentGen.size() - 1).getPosition() + 4;
				potPos = maxPotPos - 3;
				while (potPos < maxPotPos) {
					lastGen.add(new Pot(potPos++, false));
				}
				currentGen = new ArrayList<>();

				for (int j = 0; j < lastGen.size(); j++) {
					Pot lastGenPot = lastGen.get(j);
					Pot pot = new Pot();
					pot.setPosition(lastGenPot.getPosition());
					boolean twoLeft;
					boolean left;
					boolean right;
					boolean twoRight;

					if (j - 2 > 0) {
						twoLeft = lastGen.get(j - 2).isPlanted();
					} else {
						twoLeft = false;
					}
					if (j - 1 > 0) {
						left = lastGen.get(j - 1).isPlanted();
					} else {
						left = false;
					}
					if (j + 1 < lastGen.size()) {
						right = lastGen.get(j + 1).isPlanted();
					} else {
						right = false;
					}
					if (j + 2 < lastGen.size()) {
						twoRight = lastGen.get(j + 2).isPlanted();
					} else {
						twoRight = false;
					}
					boolean matched = false;
					for (Rule rule : rules) {
						if (rule.isTwoLeft() == twoLeft && rule.isLeft() == left
								&& rule.isToCheck() == lastGenPot.isPlanted() && rule.isRight() == right
								&& rule.isTwoRight() == twoRight) {
							pot.setPlanted(rule.isOutcome());
							matched = true;
							break;
						}
					}
					if (!matched) {
						throw new IllegalStateException();
					}
					currentGen.add(pot);
				}

				Integer move = checkMove(lastGen, currentGen);
				if (move != null) {
					growth = move * (GENERATIONS - (i + 1));
					break;
				}
			}

			Long sum = 0l;
			for (Pot pot : currentGen) {
				if (pot.isPlanted()) {
					sum += pot.getPosition();
					sum += growth;
				}
			}

			AdventUtils.publishResult(12, 2, sum.toString());

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static Integer checkMove(List<Pot> last, List<Pot> current) {
		Integer firstLast = null;
		Integer lastLast = null;
		Integer firstCurrent = null;
		Integer lastCurrent = null;
		for (int i = 0; i < last.size(); i++) {
			if (last.get(i).isPlanted() && firstLast == null) {
				firstLast = i;
			}
			if (last.get(i).isPlanted()) {
				lastLast = i;
			}
		}

		for (int i = 0; i < current.size(); i++) {
			if (current.get(i).isPlanted() && firstCurrent == null) {
				firstCurrent = i;
			}
			if (current.get(i).isPlanted()) {
				lastCurrent = i;
			}
		}

		if (firstLast - lastLast != firstCurrent - lastCurrent) {
			return null;
		}

		for (int i = firstLast; i <= lastLast; i++) {
			if (last.get(i).isPlanted() != current.get(i + firstCurrent - firstLast).isPlanted()) {
				return null;
			}
		}

		return current.get(firstCurrent).getPosition() - last.get(firstLast).getPosition();
	}

	private static boolean isPlanted(char pot) {
		if (pot == '#') {
			return true;
		} else if (pot == '.') {
			return false;
		}

		throw new IllegalArgumentException("Illegal input :\"" + pot + "\"");

	}

}
