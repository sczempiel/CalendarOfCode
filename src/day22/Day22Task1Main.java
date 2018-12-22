package day22;

import java.io.IOException;
import java.util.List;

import util.AdventUtils;

public class Day22Task1Main {

	private static Tile[][] grid;
	private static int riskLevel;
	private static int caveDepth;
	private static int yTarget;
	private static int xTarget;

	public static void main(String[] args) {

		try {
			List<String> input = AdventUtils.getStringInput(22);

			caveDepth = Integer.parseInt(input.get(0).substring(7, input.get(0).length()));
			String[] parts = input.get(1).substring(8, input.get(1).length()).split(",");
			xTarget = Integer.parseInt(parts[0]);
			yTarget = Integer.parseInt(parts[1]);

			grid = new Tile[yTarget + 1][xTarget + 1];

			for (int y = 0; y < grid.length; y++) {
				for (int x = 0; x < grid[y].length; x++) {
					Tile tile = new Tile(y, x);
					if (x == 0 && y == 0 || x == xTarget && y == yTarget) {
						tile.setErosionLevel(0);
					} else if (y == 0) {
						tile.setErosionLevel(x * 16807);
					} else if (x == 0) {
						tile.setErosionLevel(y * 48271);
					} else {
						tile.setErosionLevel(grid[y][x - 1].getGeoloficIndex() * grid[y - 1][x].getGeoloficIndex());
					}
					tile.setGeoloficIndex((tile.getErosionLevel() + caveDepth) % 20183);
					switch (tile.getGeoloficIndex() % 3) {
					case 2:
						tile.setRegionType(RegionType.narrow);
						break;
					case 1:
						tile.setRegionType(RegionType.wet);
						break;
					case 0:
						tile.setRegionType(RegionType.rocky);
						break;
					default:
						throw new IllegalStateException();
					}
					grid[y][x] = tile;
					riskLevel += tile.getRegionType().getRisk();
				}
			}
			printGrid();
			AdventUtils.publishResult(22, 1, riskLevel);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void printGrid() throws IOException {
		StringBuilder sb = new StringBuilder();

		for (int y = 0; y < grid.length; y++) {
			for (int x = 0; x < grid[y].length; x++) {
				if (y == 0 && x == 0) {
					sb.append("M");
				} else if (y == yTarget && x == xTarget) {
					sb.append("T");
				} else {
					switch (grid[y][x].getRegionType()) {
					case rocky:
						sb.append(".");
						break;
					case wet:
						sb.append("=");
						break;
					case narrow:
						sb.append("|");
						break;
					}
				}
			}
			if (y < grid.length - 1) {
				sb.append("\n");
			}
		}
		AdventUtils.writeExtra(22, 1, sb.toString(), "grid");
	}
}
