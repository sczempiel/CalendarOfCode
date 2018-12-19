package day18;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import util.AdventUtils;

public class Day18Task2Main {

	private static char[][] grid;
	private static List<char[][]> previousGrids = new ArrayList<>();
	private static final int TOTAL_ROUNDS = 1000000000;

	public static void main(String[] args) {

		try {
			initGrid(AdventUtils.getStringInput(18));

			round: for (int i = 0; i < TOTAL_ROUNDS; i++) {
				char[][] newGrid = new char[grid.length][grid[0].length];
				for (int y = 0; y < grid.length; y++) {
					for (int x = 0; x < grid[y].length; x++) {
						char tile = grid[y][x];
						if (tile == '.') {
							newGrid[y][x] = handleOpen(y, x);
						} else if (tile == '|') {
							newGrid[y][x] = handleTree(y, x);
						} else {
							newGrid[y][x] = handleLumbyard(y, x);
						}
					}
				}

				grid = newGrid;
				previousGrids.add(grid);
				if (previousGrids.size() > 1) {
					check: for (int j = 0; j < previousGrids.size() - 1; j++) {
						char[][] previousGrid = previousGrids.get(j);
						for (int y = 0; y < grid.length; y++) {
							for (int x = 0; x < grid[y].length; x++) {
								if (grid[y][x] != previousGrid[y][x]) {
									continue check;
								}
							}
						}
						int repeatAfter = previousGrids.size() - 1 - j;
						int finalGridPointer = (TOTAL_ROUNDS - i - 1) % repeatAfter + j;
						grid = previousGrids.get(finalGridPointer);
						break round;
					}
				}
			}

			int trees = 0;
			int lumyards = 0;
			for (int y = 0; y < grid.length; y++) {
				for (int x = 0; x < grid[y].length; x++) {
					if (grid[y][x] == '|') {
						trees++;
					} else if (grid[y][x] == '#') {
						lumyards++;
					}
				}
			}
			AdventUtils.printGrid(18, 2, grid, true);

			AdventUtils.publishResult(18, 2, trees * lumyards);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static char handleLumbyard(int y, int x) {
		int lumbyards = 0;
		int trees = 0;

		if (isValidCoordinates(y - 1, x - 1)) {
			if (grid[y - 1][x - 1] == '#') {
				lumbyards++;

			} else if (grid[y - 1][x - 1] == '|') {
				trees++;
			}
		}
		if (isValidCoordinates(y - 1, x)) {
			if (grid[y - 1][x] == '#') {
				lumbyards++;

			} else if (grid[y - 1][x] == '|') {
				trees++;
			}
		}
		if (isValidCoordinates(y - 1, x + 1)) {
			if (grid[y - 1][x + 1] == '#') {
				lumbyards++;

			} else if (grid[y - 1][x + 1] == '|') {
				trees++;
			}
		}
		if (isValidCoordinates(y, x - 1)) {
			if (grid[y][x - 1] == '#') {
				lumbyards++;

			} else if (grid[y][x - 1] == '|') {
				trees++;
			}
		}
		if (isValidCoordinates(y, x + 1)) {
			if (grid[y][x + 1] == '#') {
				lumbyards++;

			} else if (grid[y][x + 1] == '|') {
				trees++;
			}
		}
		if (isValidCoordinates(y + 1, x - 1)) {
			if (grid[y + 1][x - 1] == '#') {
				lumbyards++;

			} else if (grid[y + 1][x - 1] == '|') {
				trees++;
			}
		}
		if (isValidCoordinates(y + 1, x)) {
			if (grid[y + 1][x] == '#') {
				lumbyards++;

			} else if (grid[y + 1][x] == '|') {
				trees++;
			}
		}
		if (isValidCoordinates(y + 1, x + 1)) {
			if (grid[y + 1][x + 1] == '#') {
				lumbyards++;

			} else if (grid[y + 1][x + 1] == '|') {
				trees++;
			}
		}

		if (lumbyards > 0 && trees > 0) {
			return '#';
		}
		return '.';
	}

	private static char handleTree(int y, int x) {
		int lumbyards = 0;

		if (isValidCoordinates(y - 1, x - 1) && grid[y - 1][x - 1] == '#') {
			lumbyards++;
		}
		if (isValidCoordinates(y - 1, x) && grid[y - 1][x] == '#') {
			lumbyards++;
		}
		if (isValidCoordinates(y - 1, x + 1) && grid[y - 1][x + 1] == '#') {
			lumbyards++;
		}
		if (isValidCoordinates(y, x - 1) && grid[y][x - 1] == '#') {
			lumbyards++;
		}
		if (isValidCoordinates(y, x + 1) && grid[y][x + 1] == '#') {
			lumbyards++;
		}
		if (isValidCoordinates(y + 1, x - 1) && grid[y + 1][x - 1] == '#') {
			lumbyards++;
		}
		if (isValidCoordinates(y + 1, x) && grid[y + 1][x] == '#') {
			lumbyards++;
		}
		if (isValidCoordinates(y + 1, x + 1) && grid[y + 1][x + 1] == '#') {
			lumbyards++;
		}

		if (lumbyards > 2) {
			return '#';
		}
		return '|';
	}

	private static char handleOpen(int y, int x) {
		int treeCount = 0;

		if (isValidCoordinates(y - 1, x - 1) && grid[y - 1][x - 1] == '|') {
			treeCount++;
		}
		if (isValidCoordinates(y - 1, x) && grid[y - 1][x] == '|') {
			treeCount++;
		}
		if (isValidCoordinates(y - 1, x + 1) && grid[y - 1][x + 1] == '|') {
			treeCount++;
		}
		if (isValidCoordinates(y, x - 1) && grid[y][x - 1] == '|') {
			treeCount++;
		}
		if (isValidCoordinates(y, x + 1) && grid[y][x + 1] == '|') {
			treeCount++;
		}
		if (isValidCoordinates(y + 1, x - 1) && grid[y + 1][x - 1] == '|') {
			treeCount++;
		}
		if (isValidCoordinates(y + 1, x) && grid[y + 1][x] == '|') {
			treeCount++;
		}
		if (isValidCoordinates(y + 1, x + 1) && grid[y + 1][x + 1] == '|') {
			treeCount++;
		}

		if (treeCount > 2) {
			return '|';
		}
		return '.';
	}

	private static void initGrid(List<String> input) {
		grid = new char[input.size()][input.get(0).length()];

		for (int y = 0; y < input.size(); y++) {
			char[] row = input.get(y).toCharArray();
			for (int x = 0; x < row.length; x++) {
				grid[y][x] = row[x];
			}
		}
	}

	private static boolean isValidCoordinates(int y, int x) {
		return y >= 0 && y < grid.length && x >= 0 && x < grid[y].length;
	}
}