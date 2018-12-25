package day25;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import util.AdventUtils;

public class Day25Task1Main {

	private static List<List<Point>> constellations = new ArrayList<>();

	public static void main(String[] args) {

		try {
			List<String> input = AdventUtils.getStringInput(25);

			for (String line : input) {
				String[] parts = line.split(",");
				Point point = new Point();
				point.setW(Integer.parseInt(parts[0]));
				point.setX(Integer.parseInt(parts[1]));
				point.setY(Integer.parseInt(parts[2]));
				point.setZ(Integer.parseInt(parts[3]));

				boolean matched = false;

				constell: for (List<Point> constellation : constellations) {
					for (Point p : constellation) {
						int dist = Math.abs(point.getW() - p.getW()) + Math.abs(point.getX() - p.getX())
								+ Math.abs(point.getY() - p.getY()) + Math.abs(point.getZ() - p.getZ());
						if (dist < 4) {
							constellation.add(point);
							matched = true;
							break constell;
						}
					}
				}

				if (!matched) {
					List<Point> constellation = new ArrayList<>();
					constellation.add(point);
					constellations.add(constellation);
				}
			}

			boolean changed = true;

			while (changed) {
				changed = false;

				List<List<Point>> toRemove = new ArrayList<>();
				for (List<Point> toCheck : constellations) {
					if (toRemove.contains(toCheck)) {
						continue;
					}
					c2: for (List<Point> against : constellations) {
						if (toCheck == against || toRemove.contains(against)) {
							continue;
						}
						for (Point p1 : toCheck) {
							for (Point p2 : against) {
								int dist = Math.abs(p1.getW() - p2.getW()) + Math.abs(p1.getX() - p2.getX())
										+ Math.abs(p1.getY() - p2.getY()) + Math.abs(p1.getZ() - p2.getZ());
								if (dist < 4) {
									toCheck.addAll(against);
									toRemove.add(against);
									changed = true;
									continue c2;
								}
							}
						}
					}
				}
				for (List<Point> r : toRemove) {
					constellations.remove(r);
				}
			}

			AdventUtils.publishResult(19, 1, constellations.size());

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
