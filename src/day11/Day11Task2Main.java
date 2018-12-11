package day11;

import java.io.IOException;

import util.AdventUtils;

public class Day11Task2Main {

	public static void main(String[] args) {

		try {
			int serial = AdventUtils.getIntegerInput(11).get(0);

			Integer[][] battery = new Integer[300][300];

			int lagestX = 0;
			int lagestY = 0;
			int largestPower = 0;
			int largestSquareSize = 0;

			int biggestNumLength = 0;

			for (int y = 0; y < 300; y++) {
				for (int x = 0; x < 300; x++) {
					int maxSquareSideSize;
					if (y > x) {
						maxSquareSideSize = 300 - y;
					} else {
						maxSquareSideSize = 300 - x;
					}
					for (int s = 1; s <= maxSquareSideSize; s++) {
						int power = 0;
						for (int y2 = 0; y2 < s; y2++) {
							for (int x2 = 0; x2 < s; x2++) {
								int yP = y + y2;
								int xP = x + x2;
								Integer powerLevel = battery[yP][xP];
								if (powerLevel == null) {
									int id = (xP + 1 + 10);

									powerLevel = id * (yP + 1);
									powerLevel += serial;
									powerLevel *= id;
									String level = powerLevel.toString();
									if (level.length() < 3) {
										powerLevel = 0;
									} else {
										powerLevel = Integer.parseInt(String.valueOf(level.charAt(level.length() - 3)));
										powerLevel -= 5;
									}

									battery[yP][xP] = powerLevel;
									biggestNumLength = powerLevel.toString().length();
								}
								power += powerLevel;
							}
						}
						if (power > largestPower) {
							largestPower = power;
							largestSquareSize = s;
							lagestX = x + 1;
							lagestY = y + 1;
						}
					}
				}
				System.out.println(y);
			}
			System.out.println("-------------------------------------------------------");
			System.out.println("");

			AdventUtils.publishResult(11, 2, lagestX + "," + lagestY + "," + largestSquareSize);

			StringBuilder sb = new StringBuilder();

			if (biggestNumLength < 3) {
				biggestNumLength = 3;
			}

			sb.append("   | ");

			for (int x = 1; x <= battery[0].length; x++) {
				sb.append(" " + AdventUtils.printNum(x, biggestNumLength) + " ");
			}

			sb.append("\n");

			sb.append("---+-");

			for (int x = 1; x <= battery[0].length; x++) {
				sb.append("------");
			}

			for (int y = 0; y < battery.length; y++) {
				sb.append(AdventUtils.printNum(y + 1, biggestNumLength) + "| ");
				for (int x = 0; x < battery[y].length; x++) {
					sb.append(" " + AdventUtils.printNum(battery[y][x], biggestNumLength) + " ");
				}
				if (y < battery.length - 1) {
					sb.append("\n");
				}
			}
			AdventUtils.writeExtra(11, 2, sb.toString(), "battery");
			AdventUtils.publishExtra(11, 2, String.valueOf(largestPower), "largestPower");

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
