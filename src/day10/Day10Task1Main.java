package day10;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

import util.AdventUtils;

public class Day10Task1Main {

	public static void main(String[] args) {

		try {
			List<LightPoint> points = AdventUtils.getStringInput(10).stream().map(line -> LightPoint.parseString(line))
					.collect(Collectors.toList());

			Scanner sc = new Scanner(System.in);

			boolean resolved = false;

			StringBuilder sb = null;

			resolve: while (!resolved) {
				int minX = 0;
				int maxX = 0;
				int minY = 0;
				int maxY = 0;
				for (LightPoint point : points) {
					point.getPosition().add(point.getVelocity());
					int x = point.getPosition().getLeft();
					int y = point.getPosition().getRight();
					if (x < minX) {
						minX = x;
					} else if (x > maxX) {
						maxX = x;
					}
					if (y < minY) {
						minY = y;
					} else if (y > maxY) {
						maxY = y;
					}
				}

				for (LightPoint point : points) {
					boolean hasNeighbour = false;
					for (LightPoint point2 : points) {
						if (point != point2 && point.getPosition().isNeighbourOf(point2.getPosition())) {
							hasNeighbour = true;
							break;
						}
					}
					if (hasNeighbour == false) {
						continue resolve;
					}
				}

				int height = 0;
				int width = 0;

				if (minX < 0 && maxX < 0 || minX > 0 && maxX > 0) {
					width = maxX - minX;
				} else {
					width = Math.abs(minX) + Math.abs(maxX);
				}
				width++;

				if (minY < 0 && maxY < 0 || minY > 0 && maxY > 0) {
					height = minY - maxY;
				} else {
					height = Math.abs(minY) + Math.abs(maxY);
				}
				height++;

				sb = new StringBuilder();
				for (int y = 0; y < height; y++) {
					for (int x = 0; x < width; x++) {
						boolean found = false;
						for (LightPoint point : points) {
							int xP = point.getPosition().getLeft();
							int yP = point.getPosition().getRight();

							if (minX < 0) {
								xP -= minX;
							}

							if (minY < 0) {
								yP -= minY;
							}

							if (xP == x && yP == y) {
								found = true;
								break;
							}
						}
						if (found) {
							sb.append("#");
						} else {
							sb.append(".");
						}
					}
					sb.append("\n");
				}

				System.out.println(sb.toString());
				if (sc.hasNext()) {
					String in = sc.next();
					resolved = in.equals("1") || in.equals("true") ? true : false;
				}
			}

			sc.close();

			AdventUtils.writeResult(10, 1, sb.toString());

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
