package day6;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import util.AdventUtils;
import util.Touple;

public class Day6Task2Main {

	public static void main(String[] args) {

		try {
			List<Touple<Integer, Integer>> input = AdventUtils.getStringInput(6).stream().map(line -> {
				String[] splitted = line.split(", ");
				return new Touple<Integer, Integer>(Integer.parseInt(splitted[1]), Integer.parseInt(splitted[0]));
			}).sorted((c1, c2) -> {
				if (c1.getLeft() - c2.getLeft() == 0) {
					return c1.getRight() - c2.getRight();
				}
				return c1.getLeft() - c2.getLeft();
			}).collect(Collectors.toList());

			int largestX = 0;
			int largestY = 0;

			int endX = 0;
			int endY = 0;

			for (Touple<Integer, Integer> coordinate : input) {
				if (endX < coordinate.getLeft()) {
					endX = coordinate.getLeft();
				}
				if (endY < coordinate.getRight()) {
					endY = coordinate.getRight();
				}
			}

			largestX = endX + 1;
			largestY = endY + 1;

			String[][] grid = new String[largestY][largestX];

			Map<Integer, Touple<Integer, Integer>> keys = new HashMap<>();

			int i = 0;
			for (Touple<Integer, Integer> coordinate : input) {
				grid[coordinate.getRight()][coordinate.getLeft()] = "_" + i;
				keys.put(i, coordinate);
				i++;
			}

			for (int y = 0; y < largestY; y++) {
				for (int x = 0; x < largestX; x++) {
					List<Integer> dists = new ArrayList<>();
					for (Entry<Integer, Touple<Integer, Integer>> entry : keys.entrySet()) {
						Touple<Integer, Integer> c = entry.getValue();
						int dist = Math.abs(x - c.getLeft()) + Math.abs(y - c.getRight());
						dists.add(dist);
					}

					int total = dists.stream().mapToInt(Integer::intValue).sum();
					if (total < 10000) {
						if (grid[y][x] != null) {
							grid[y][x] = grid[y][x].replace("_", "#");
						} else {
							grid[y][x] = "#";
						}
					} else if (grid[y][x] == null) {
						grid[y][x] = ".";
					}
				}
			}

			int maxSize = 0;

			for (int y = 0; y < largestY; y++) {
				for (int x = 0; x < largestX; x++) {
					String cord = grid[y][x];
					if (cord.startsWith("#")) {
						maxSize++;
					}
				}
			}

			printGrid(grid);

			AdventUtils.publishResult(6, 2, maxSize);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void printGrid(String[][] grid) throws IOException {
		StringBuilder sb = new StringBuilder();
		for (int y = 0; y < grid.length; y++) {
			for (int x = 0; x < grid[y].length; x++) {
				if (grid[y][x].length() == 1) {
					if (grid[y][x].equals(".")) {
						sb.append("... ");
					} else {
						sb.append("### ");
					}
				} else if (grid[y][x].length() == 2) {
					if (grid[y][x].startsWith("_")) {
						sb.append("_" + grid[y][x].replaceAll("_", "0") + " ");
					} else if (grid[y][x].startsWith("_")) {
						sb.append("#" + grid[y][x].replaceAll("#", "0") + " ");
					} else {
						sb.append("0" + grid[y][x] + " ");
					}
				} else {
					sb.append(grid[y][x] + " ");
				}
			}
			sb.append("\n");
		}

		AdventUtils.writeExtra(6, 2, sb.toString(), "grid");
	}

}
