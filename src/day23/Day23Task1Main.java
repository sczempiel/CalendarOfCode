package day23;

import java.io.IOException;
import java.util.List;

import util.AdventUtils;

public class Day23Task1Main {

	public static void main(String[] args) {

		try {
			List<String> input = AdventUtils.getStringInput(23);

			Nanobot[] nanobots = new Nanobot[input.size()];

			Nanobot maxRangeBot = null;
			int pointer = 0;
			for (String line : input) {
				String[] parts = line.substring(5).split(">, r=");
				String[] coords = parts[0].split(",");

				int range = Integer.parseInt(parts[1]);
				Nanobot bot = new Nanobot();
				bot.setX(Integer.parseInt(coords[0]));
				bot.setY(Integer.parseInt(coords[1]));
				bot.setZ(Integer.parseInt(coords[2]));
				bot.setRange(range);

				nanobots[pointer++] = bot;

				if (maxRangeBot == null || maxRangeBot.getRange() < range) {
					maxRangeBot = bot;
				}
			}

			int botsInRange = 0;
			for (Nanobot bot : nanobots) {
				int dist = Math.abs(bot.getX() - maxRangeBot.getX()) + Math.abs(bot.getY() - maxRangeBot.getY())
						+ Math.abs(bot.getZ() - maxRangeBot.getZ());
				if (dist <= maxRangeBot.getRange()) {
					botsInRange++;
				}
			}

			AdventUtils.publishResult(23, 1, botsInRange);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
