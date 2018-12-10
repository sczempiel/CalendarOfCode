package day10;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import util.AdventUtils;

public class Day10Task2Main {

	public static void main(String[] args) {

		try {
			List<LightPoint> points = AdventUtils.getStringInput(10).stream().map(line -> LightPoint.parseString(line))
					.collect(Collectors.toList());

			boolean unresolved = true;
			int seconds = 0;

			do {
				seconds++;

				for (LightPoint point : points) {
					point.getPosition().add(point.getVelocity());
				}

				unresolved = false;
				check: for (LightPoint point : points) {
					boolean hasNeighbour = false;
					for (LightPoint point2 : points) {
						if (point != point2 && point.getPosition().isNeighbourOf(point2.getPosition())) {
							hasNeighbour = true;
							break;
						}
					}
					if (!hasNeighbour) {
						unresolved = true;
						break check;
					}
				}
			} while (unresolved);

			AdventUtils.publishResult(10, 2, seconds);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
