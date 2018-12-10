package day06;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.stream.Collectors;

import util.AdventUtils;
import util.Touple;

public class Day6Task1Main {

	public static void main(String[] args) {

		try {
			List<Touple<Integer, Integer>> input = AdventUtils.getStringInput(6).stream().map(line -> {
				String[] splitted = line.split(", ");
				return new Touple<Integer, Integer>(Integer.parseInt(splitted[0]), Integer.parseInt(splitted[1]));
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
					String cord = grid[y][x];

					Integer distance = null;
					String closest = null;
					boolean sameDist = false;

					if (cord != null && cord.startsWith("_")) {
						continue;
					}
					for (Entry<Integer, Touple<Integer, Integer>> entry : keys.entrySet()) {
						Touple<Integer, Integer> c = entry.getValue();
						int dist = Math.abs(x - c.getLeft()) + Math.abs(y - c.getRight());
						if (distance == null || dist <= distance) {
							if (distance != null) {
								if (dist == distance) {
									sameDist = true;
								}
								if (dist < distance) {
									sameDist = false;
								}
							}
							distance = dist;
							closest = String.valueOf(entry.getKey());
						}
					}
					if (sameDist) {
						grid[y][x] = ".";
					} else {
						grid[y][x] = closest;
					}
				}
			}

			Map<Integer, Integer> size = new HashMap<>();
			Set<Integer> blackList = new HashSet<>();

			for (int y = 0; y < largestY; y++) {
				for (int x = 0; x < largestX; x++) {
					String cord = grid[y][x];
					if (cord.equals(".")) {
						continue;
					}
					int key = Integer.parseInt(cord.replaceAll("_", ""));
					if (y == 0 || x == 0 || y == endY || x == endX) {
						blackList.add(key);
						continue;
					}
					Integer count = size.get(key);
					if (count == null) {
						count = 0;
					}
					count++;
					size.put(key, count);
				}
			}

			for (Integer key : blackList) {
				size.remove(key);
			}

			int maxSize = 0;

			for (Integer s : size.values()) {
				if (s > maxSize) {
					maxSize = s;
				}
			}

			printGrid(grid);

			AdventUtils.publishResult(6, 1, maxSize);

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
						sb.append("00" + grid[y][x] + " ");
					}
				} else if (grid[y][x].length() == 2) {
					if (grid[y][x].startsWith("_")) {
						sb.append("_" + grid[y][x].replaceAll("_", "0") + " ");
					} else {
						sb.append("0" + grid[y][x] + " ");
					}
				} else {
					sb.append(grid[y][x] + " ");
				}
			}
			sb.append("\n");
		}

		AdventUtils.writeExtra(6, 1, sb.toString(), "grid");
	}

}
