package day20;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import util.AdventUtils;
import util.Touple;

public class Day20Task1Main {

	private static char[] input;
	private static char[][] grid;
	private static int pointer;
	private static int maxPointer;

	private static int minX;
	private static int maxX;
	private static int minY;
	private static int maxY;

	private static List<Touple<Integer, Integer>> doors = new ArrayList<>();

	public static void main(String[] args) {

		try {
			input = AdventUtils.getStringInput(20).get(0).toCharArray();
			maxPointer = input.length - 1;

			walk(0, 0);

			minX -= 1;
			maxX += 2;
			minY -= 1;
			maxY += 2;

			grid = new char[minY * -1 + maxY][minX * -1 + maxX];

			for (int y = 0; y < grid.length; y++) {
				for (int x = 0; x < grid[y].length; x++) {
					if (y + minY == 0 && x + minX == 0) {
						grid[y][x] = 'X';
					} else if (y % 2 == 0 || x % 2 == 0) {
						grid[y][x] = '#';
					} else {
						grid[y][x] = '.';
					}
				}
			}

			for (Touple<Integer, Integer> door : doors) {
				if (door.getLeft() % 2 == 0) {
					grid[door.getLeft() - minY][door.getRight() - minX] = '|';
				} else {
					grid[door.getLeft() - minY][door.getRight() - minX] = '-';
				}
			}

			printGrid();

			AdventUtils.publishResult(20, 1, countDoors(minY * -1, minX * -1, minY * -1, minX * -1, 0));

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static int countDoors(int y, int x, int lY, int lX, int doors) {
		int[] steps = new int[4];
		steps[0] = doors;
		steps[1] = doors;
		steps[2] = doors;
		steps[3] = doors;
		if (x - 2 != lX && grid[y][x - 1] == '|') {
			steps[0] = countDoors(y, x - 2, y, x, doors + 1);
		}
		if (y - 2 != lY && grid[y - 1][x] == '-') {
			steps[1] = countDoors(y - 2, x, y, x, doors + 1);
		}
		if (x + 2 != lX && grid[y][x + 1] == '|') {
			steps[2] = countDoors(y, x + 2, y, x, doors + 1);
		}
		if (y + 2 != lY && grid[y + 1][x] == '-') {
			steps[3] = countDoors(y + 2, x, y, x, doors + 1);
		}
		Arrays.sort(steps);
		return steps[3];
	}

	private static void walk(int y, int x) {
		int oY = y;
		int oX = x;
		while (pointer < maxPointer) {
			pointer++;
			switch (input[pointer]) {
			case '(':
				walk(y, x);
				break;
			case ')':
				return;
			case '|':
				y = oY;
				x = oX;
				break;
			case 'W':
				doors.add(new Touple<>(y, --x));
				x--;
				break;
			case 'N':
				doors.add(new Touple<>(--y, x));
				y--;
				break;
			case 'E':
				doors.add(new Touple<>(y, ++x));
				x++;
				break;
			case 'S':
				doors.add(new Touple<>(++y, x));
				y++;
				break;
			}
			if (x < minX) {
				minX = x;
			}
			if (x > maxX) {
				maxX = x;
			}
			if (y < minY) {
				minY = y;
			}
			if (y > maxY) {
				maxY = y;
			}
		}
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
		AdventUtils.writeExtra(20, 1, sb.toString(), "grid");
	}
}
