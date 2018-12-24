package day23;

import java.io.IOException;
import java.util.List;

import util.AdventUtils;

public class Day23Task2Main {

	public static void main(String[] args) {

		try {
			List<String> input = AdventUtils.getStringInput(23);

			Nanobot[] nanobots = new Nanobot[input.size()];

			int pointer = 0;
			for (String line : input) {
				String[] parts = line.substring(5).split(">, r=");
				String[] coords = parts[0].split(",");

				Nanobot bot = new Nanobot();
				bot.setX(Integer.parseInt(coords[0]));
				bot.setY(Integer.parseInt(coords[1]));
				bot.setZ(Integer.parseInt(coords[2]));
				bot.setRange(Integer.parseInt(parts[1]));

				nanobots[pointer++] = bot;
			}

			for (Nanobot bot : nanobots) {
			}

			AdventUtils.publishResult(23, 1, "");

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
