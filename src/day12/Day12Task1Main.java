package day12;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import util.AdventUtils;

public class Day12Task1Main {
	private static final int GENERATIONS = 20;

	public static void main(String[] args) {

		try {
			List<String> input = AdventUtils.getStringInput(12);

			List<Pot> lastGen = new ArrayList<>();
			List<Pot> currentGen = new ArrayList<>();

			Rule[] rules = new Rule[input.size() - 2];

			String initialPots = input.get(0).split(": ")[1];

			int potPos = -3 * GENERATIONS;
			int maxPotPos = 3 * GENERATIONS + initialPots.length();
			while (potPos < 0) {
				currentGen.add(new Pot(potPos++, false));
			}
			for (char initial : initialPots.toCharArray()) {
				Pot pot = new Pot();
				pot.setPosition(potPos++);
				pot.setPlanted(isPlanted(initial));
				currentGen.add(pot);
			}
			while (potPos < maxPotPos) {
				currentGen.add(new Pot(potPos++, false));
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

			printPlants(currentGen);

			for (int i = 0; i < GENERATIONS; i++) {
				lastGen = currentGen;
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
				printPlants(currentGen);
			}

			int sum = 0;
			for (Pot pot : currentGen) {
				if (pot.isPlanted()) {
					sum += pot.getPosition();
				}
			}

			AdventUtils.publishResult(12, 1, sum);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void printPlants(List<Pot> pots) {
		StringBuilder sb = new StringBuilder();
		for (Pot pot : pots) {
			if (pot.isPlanted()) {
				sb.append("#");
			} else {
				sb.append(".");
			}
		}
		System.out.println(sb.toString());
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
