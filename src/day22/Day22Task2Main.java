package day22;

import java.io.IOException;
import java.util.List;

import util.AdventUtils;
import util.Touple;
import util.pathfinding.Graph;

public class Day22Task2Main {

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

			Graph<Tile> graph = new Graph<>();
			for (int y = 0; y <= yTarget; y++) {
				for (int x = 0; x <= xTarget; x++) {
					Tile tile = new Tile(y, x);
					if (x == 0 && y == 0 || x == xTarget && y == yTarget) {
						tile.setErosionLevel(0);
					} else if (y == 0) {
						tile.setErosionLevel(x * 16807);
					} else if (x == 0) {
						tile.setErosionLevel(y * 48271);
					} else {
						tile.setErosionLevel(graph.getNode(new Touple<>(y, x - 1)).getGeoloficIndex()
								* graph.getNode(new Touple<>(y - 1, x)).getGeoloficIndex());
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
					graph.addNode(tile);
				}
			}

			for (Tile tile : graph.getNodes().values()) {
				int y = tile.getY();
				int x = tile.getX();
				if (y - 1 > 0) {
					addTile(tile, graph.getNode(new Touple<>(y - 1, x)));
				}
				if (x - 1 > 0) {
					addTile(tile, graph.getNode(new Touple<>(y, x - 1)));
				}
				if (x + 1 <= xTarget) {
					addTile(tile, graph.getNode(new Touple<>(y, x + 1)));
				}
				if (y + 1 <= yTarget) {
					addTile(tile, graph.getNode(new Touple<>(y + 1, x)));
				}
			}

			graph.calculateShortestPathFromSource(graph.getNode(new Touple<>(0, 0)));

			Tile tile = graph.getNode(yTarget, yTarget);
			AdventUtils.publishResult(22, 2, tile.getDistance());

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void addTile(Tile tile, Tile adjacent) {
		tile.getAdjacentNodes().put(adjacent, 8);
		if (tile.getRegionType() == adjacent.getRegionType()) {
			tile.getAdjacentNodes().put(adjacent, 1);
		}
	}
}
