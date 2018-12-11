package day11;

import java.io.IOException;

import util.AdventUtils;

public class Day11Task2Main {
	private static int biggestNumLength = 0;

	public static void main(String[] args) {

		try {
			int serial = AdventUtils.getIntegerInput(11).get(0);

			Integer[][] battery = new Integer[300][300];

			int largestX = 0;
			int largestY = 0;
			int largestPower = 0;
			int largestSquareSize = 0;

			for (int y = 0; y < 300; y++) {
				for (int x = 0; x < 300; x++) {
					int maxSquareSideSize;
					if (y > x) {
						maxSquareSideSize = 300 - y;
					} else {
						maxSquareSideSize = 300 - x;
					}
					int lastPower = 0;
					for (int s = 0; s < maxSquareSideSize; s++) {
						int power = lastPower;
						for (int y2 = 0; y2 <= s; y2++) {
							int yP = y + y2;
							int xP = x + s;
							power += getPowerLevel(battery, xP, yP, serial);
						}
						for (int x2 = 0; x2 <= s; x2++) {
							int yP = y + s;
							int xP = x + x2;
							power += getPowerLevel(battery, xP, yP, serial);
						}
						lastPower = power;
						if (power > largestPower) {
							largestPower = power;
							largestSquareSize = s + 1;
							largestX = x + 1;
							largestY = y + 1;
						}
					}
				}
			}

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

			for (int x = 0; x < battery[0].length; x++) {
				sb.append("-----");
			}

			sb.append("\n");

			for (int y = 0; y < battery.length; y++) {
				sb.append("   | ");
				for (int x = 0; x < battery[y].length; x++) {
					if ((y == largestY - 1 || y == largestY + largestSquareSize - 1) && x >= largestX - 1
							&& x < largestX + largestSquareSize - 1) {
						sb.append("-----");
					} else {
						sb.append("     ");
					}
				}
				sb.append("\n");
				sb.append(AdventUtils.printNum(y + 1, biggestNumLength) + "| ");
				for (int x = 0; x < battery[y].length; x++) {
					boolean drawY = y >= largestY - 1 && y < largestY + largestSquareSize - 1;
					if (drawY && x == largestX - 1) {
						sb.append("|");
					} else {
						sb.append(" ");
					}
					sb.append(AdventUtils.printNum(battery[y][x], biggestNumLength));
					if (drawY && x == largestX + largestSquareSize - 2) {
						sb.append("|");
					} else {
						sb.append(" ");
					}
				}
				if (y < battery.length - 1) {
					sb.append("\n");
				}
			}

			AdventUtils.writeExtra(11, 2, sb.toString(), "battery");
			AdventUtils.publishExtra(11, 2, String.valueOf(largestPower), "largestPower");
			AdventUtils.publishResult(11, 2, largestX + "," + largestY + "," + largestSquareSize);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static int getPowerLevel(Integer[][] battery, int xP, int yP, int serial) {
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
		return powerLevel;
	}

}
