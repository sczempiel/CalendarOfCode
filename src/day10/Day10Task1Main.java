package day10;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import util.AdventUtils;

public class Day10Task1Main {

	public static void main(String[] args) {

		try {
			List<LightPoint> points = AdventUtils.getStringInput(10).stream().map(line -> LightPoint.parseString(line))
					.collect(Collectors.toList());

			boolean unresolved = true;
			StringBuilder sb = null;
			Integer minX = null;
			Integer maxX = null;
			Integer minY = null;
			Integer maxY = null;

			do {
				minX = null;
				maxX = null;
				minY = null;
				maxY = null;

				for (LightPoint point : points) {
					point.getPosition().add(point.getVelocity());
					int x = point.getPosition().getLeft();
					int y = point.getPosition().getRight();
					if (minX == null || x < minX) {
						minX = x;
					}
					if (maxX == null || x > maxX) {
						maxX = x;
					}
					if (minY == null || y < minY) {
						minY = y;
					}
					if (maxY == null || y > maxY) {
						maxY = y;
					}
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

			int height = 0;
			int width = 0;

			if (minX < 0 && maxX < 0 || minX > 0 && maxX > 0) {
				width = maxX - minX;
			} else {
				width = Math.abs(minX) + Math.abs(maxX);
			}
			width++;

			if (minY < 0 && maxY < 0 || minY > 0 && maxY > 0) {
				height = maxY - minY;
			} else {
				height = Math.abs(minY) + Math.abs(maxY);
			}
			height++;

			boolean[][] grid = new boolean[height][width];

			for (LightPoint point : points) {
				int x = point.getPosition().getLeft();
				int y = point.getPosition().getRight();

				x -= minX;
				y -= minY;

				grid[y][x] = true;
			}

			sb = new StringBuilder();
			for (int y = 0; y < height; y++) {
				for (int x = 0; x < width; x++) {
					if (grid[y][x]) {
						sb.append("#");
					} else {
						sb.append(".");
					}
				}
				if (y < height - 1) {
					sb.append("\n");
				}
			}

			AdventUtils.publishResult(10, 1, sb.toString());

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
