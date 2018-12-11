package day11;

import java.io.IOException;

import util.AdventUtils;

public class Day11Task1Main {

	public static void main(String[] args) {

		try {
			int serial = AdventUtils.getIntegerInput(11).get(0);

			Integer[][] battery = new Integer[300][300];

			int lagestX = 0;
			int lagestY = 0;
			int largestPower = 0;

			for (int y = 0; y < 297; y++) {
				for (int x = 0; x < 297; x++) {
					int power = 0;
					for (int y2 = 0; y2 < 3; y2++) {
						for (int x2 = 0; x2 < 3; x2++) {
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
							}
							power += powerLevel;
						}
					}
					if (power > largestPower) {
						largestPower = power;
						lagestX = x;
						lagestY = y;
					}
				}
			}

			AdventUtils.publishResult(11, 1, (lagestX + 1) + "," + (lagestY + 1));

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
