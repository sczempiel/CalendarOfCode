package day17;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import util.AdventUtils;
import util.Touple;

//30904 h
//30389 h

// 33728 h
// 33713 l
public class Day17Task1Main {

	private static int minX = 500;
	private static int maxX = 500;
	private static Integer minY = null;
	private static Integer maxY = null;
	private static char[][] grid;

	private static int waterCount = 0;

	public static void main(String[] args) {

		try {
			List<String> input = AdventUtils.getStringInput(17);
			List<Touple<Integer, Integer>> clay = new ArrayList<>();
			for (String line : input) {
				boolean fixIsX = false;

				String[] parts = line.split(", ");
				int fix = Integer.parseInt(parts[0].substring(2));

				if (parts[0].startsWith("x")) {
					fixIsX = true;
				}

				String[] range = parts[1].substring(2).split("\\.\\.");
				int from = Integer.parseInt(range[0]);
				int to = Integer.parseInt(range[1]);

				for (int i = from; i <= to; i++) {
					if (fixIsX) {
						clay.add(new Touple<>(i, fix));
						findBounds(i, fix);
					} else {
						clay.add(new Touple<>(fix, i));
						findBounds(fix, i);
					}
				}
			}

			minX--;

			grid = new char[maxY + 1][maxX - minX + 2];
			grid[0][500 - minX] = '+';

			clay.stream().forEach(point -> grid[point.getLeft()][point.getRight() - minX] = '#');

			flowDown(0, 500 - minX);

			printGrid();

			for (int y = minY; y < grid.length; y++) {
				for (int x = 0; x < grid[y].length; x++) {
					if (grid[y][x] == '~' || grid[y][x] == '|') {
						waterCount++;
					}
				}
			}

			AdventUtils.publishResult(17, 1, waterCount);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void flowDown(int y, int x) {
		int below = y + 1;
		while (below <= maxY && grid[below][x] == '\u0000') {
			markSpot(below, x, '|');
			below++;
		}
		--below;
		if (below < grid.length - 1 && (grid[below + 1][x] == '#' || grid[below + 1][x] == '~')) {
			flowVertically(below, x);
		}
	}

	private static void flowVertically(int y, int x) {
		// grid[y-1] [x] == '#' && (grid[y-1] [x-1] != '#' || grid[y-1] [x+1] != '#')
		if (y == grid.length - 1) {
			return;
		}
		int row = y;

		Touple<Boolean, Integer> left = new Touple<>(true, 0);
		Touple<Boolean, Integer> right = new Touple<>(true, 0);
		while (row > 0 && left.getLeft() && right.getLeft()) {
			left = flowLeft(row, x);
			right = flowRight(row, x);
			if (left.getLeft() && right.getLeft()) {
				markSpot(row, x, '~');
			}
			row--;
		}
		row++;
		for (int i = left.getRight(); i <= right.getRight(); i++) {
			if (grid[row - 1][i] == '\u0000' || grid[row - 1][i] == '|') {
				markSpot(row, i, '|');
			}
		}
	}

	private static void markSpot(int y, int x, char character) {
		if (grid[y][x] == '#') {
			throw new IllegalStateException();
		}
		grid[y][x] = character;
	}

	private static Touple<Boolean, Integer> flowLeft(int y, int x) {
		int left = x - 1;

		while (left >= 0 && grid[y][left] != '#' && (grid[y + 1][left] == '#' || grid[y + 1][left] == '~')) {
			markSpot(y, left, '~');
			left--;
		}

		if (left >= 0 && grid[y + 1][left] == '\u0000') {
			markSpot(y, left, '|');
			flowDown(y, left);
			return new Touple<>(false, left);
		}
		return new Touple<>(left == 0 || grid[y][left] != '|', left + 1);
	}

	private static Touple<Boolean, Integer> flowRight(int y, int x) {
		int right = x + 1;

		while (right < grid[y].length && grid[y][right] != '#'
				&& (grid[y + 1][right] == '#' || grid[y + 1][right] == '~')) {
			markSpot(y, right, '~');
			right++;
		}

		if (right < grid[y].length && grid[y + 1][right] == '\u0000') {
			markSpot(y, right, '|');
			flowDown(y, right);
			return new Touple<>(false, right);
		}
		return new Touple<>(right >= grid[y].length || grid[y][right] != '|', right - 1);
	}

	private static void printGrid() throws IOException {
		StringBuilder sb = new StringBuilder();

		for (int y = 0; y < grid.length; y++) {
			for (int x = 0; x < grid[y].length; x++) {
				sb.append(grid[y][x]);
			}
			if (y < grid.length - 1) {
				sb.append("\n");
			}
		}
		AdventUtils.writeExtra(17, 1, sb.toString(), "grid");
	}

	private static void findBounds(int y, int x) {
		if (x < minX) {
			minX = x;
		}
		if (x > maxX) {
			maxX = x;
		}
		if (minY == null || y < minY) {
			minY = y;
		}
		if (maxY == null || y > maxY) {
			maxY = y;
		}
	}

}
