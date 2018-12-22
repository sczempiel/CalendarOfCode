package day15;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import util.AdventUtils;
import util.Touple;
import util.pathfinding.Graph;

// error after turn 23 when the first elf is killed
public class Day15Task1Main {

	private static Graph<Tile> graph = new Graph<>();
	private static Tile[][] grid;
	private static List<Fighter> fighters = new ArrayList<>();
	private static int rounds = 0;
	private static int goblinCount = 0;
	private static int elfCount = 0;
	private static int maxX;
	private static int maxY;

	public static void main(String[] args) {

		try {
			initTiles(AdventUtils.getStringInput(15));

			System.out.println(rounds);
			printTiles();

			int currentFighter = 0;

			while (goblinCount > 0 && elfCount > 0) {
				if (currentFighter == 0) {
					rounds++;
					System.out.println(rounds);
				}
				System.out.print(fighters.get(currentFighter).getId());
				turn(fighters.get(currentFighter));
				currentFighter++;
				if (currentFighter == fighters.size()) {
					System.out.println("");
					currentFighter = 0;
					printTiles();
					fighters = fighters.stream().filter(Fighter::isAlive).sorted().collect(Collectors.toList());
				}
			}

			if (currentFighter != fighters.size()) {
				System.out.println(rounds + 1);
				System.out.println("(not completed)");
				printTiles();
			}

			int totalHp = fighters.stream().filter(Fighter::isAlive).mapToInt(Fighter::getHitpoints).sum();

			AdventUtils.publishResult(15, 1, totalHp * rounds);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void turn(Fighter fighter) {
		if (!fighter.isAlive()) {
			return;
		}

		if (getNearEnemies(fighter.getTile().getY(), fighter.getTile().getX(), !fighter.isGoblin()).isEmpty()) {
			move(fighter);
		}
		attack(fighter);
	}

//////////////
//attack
//////////////

	private static void attack(Fighter fighter) {
		Fighter enemy = findTargetToAttack(fighter);

		if (enemy == null) {
			return;
		}

		enemy.hit();

		if (!enemy.isAlive()) {
			enemy.getTile().setFighter(null);
			enemy.setTile(null);
			if (enemy.isGoblin()) {
				goblinCount--;
			} else {
				elfCount--;
			}
		}
	}

	private static Fighter findTargetToAttack(Fighter fighter) {
		List<Fighter> enemies = getNearEnemies(fighter.getTile().getY(), fighter.getTile().getX(), !fighter.isGoblin());

		if (enemies.isEmpty()) {
			return null;
		}

		if (enemies.size() == 1) {
			return enemies.get(0);
		}

		List<Fighter> lowstHpFighter = new ArrayList<>();
		Integer lowestHP = null;

		for (Fighter enemy : enemies) {
			if (lowestHP != null && lowestHP > enemy.getHitpoints()) {
				lowstHpFighter = new ArrayList<>();
			}
			if (lowestHP == null || lowestHP >= enemy.getHitpoints()) {
				lowstHpFighter.add(enemy);
				lowestHP = enemy.getHitpoints();
			}
		}

		if (lowstHpFighter.size() == 1) {
			return lowstHpFighter.get(0);
		}

		Collections.sort(lowstHpFighter);

		return lowstHpFighter.get(0);
	}

	private static List<Fighter> getNearEnemies(int y, int x, boolean enemyIsGoblin) {
		List<Fighter> enemies = new ArrayList<>();
		if (y - 1 > 0) {
			addEnemy(getEnemy(y - 1, x, enemyIsGoblin), enemies);
		}
		if (y + 1 < maxY) {
			addEnemy(getEnemy(y + 1, x, enemyIsGoblin), enemies);
		}
		if (x - 1 > 0) {
			addEnemy(getEnemy(y, x - 1, enemyIsGoblin), enemies);
		}
		if (x + 1 < maxX) {
			addEnemy(getEnemy(y, x + 1, enemyIsGoblin), enemies);
		}
		return enemies;
	}

	private static void addEnemy(Fighter enemy, List<Fighter> enemies) {
		if (enemy != null) {
			enemies.add(enemy);
		}
	}

	private static Fighter getEnemy(int y, int x, boolean isGoblin) {
		Fighter fighter = graph.getNode(y, x).getFighter();
		if (fighter != null && fighter.isGoblin() == isGoblin) {
			return fighter;
		}
		return null;
	}

//////////////
// move
//////////////

	// FIXME the edges from 2,5 to 1,5 are missing @ turn 24, they were set initally
	private static void move(Fighter fighter) {
		Set<Tile> openEnemyTiles = findOpenEnemyTiles(fighter);

		Integer closestDist = null;
		Tile newTile = null;
		Tile forTile = null;
		graph.calculateShortestPathFromSource(fighter.getTile());
		for (Tile target : openEnemyTiles) {
			int tDist = target.getDistance();
			if (closestDist == null || closestDist >= tDist) {
				for (List<Tile> m : target.getShortestPaths()) {
					Tile tile = null;
					if (m.size() > 1) {
						tile = m.get(1);
					} else {
						tile = target;
					}

					if (closestDist == null || closestDist > tDist || newTile == null || target.compareTo(forTile) == -1
							|| tile.compareTo(newTile) == -1) {
						newTile = tile;
						forTile = target;
					}
					closestDist = target.getDistance();
				}
			}
		}

		if (newTile != null) {
			Tile current = fighter.getTile();

			newTile.setFighter(fighter);
			fighter.setTile(newTile);
			current.setFighter(null);

			reAddEdges(current);
			removeEdges(newTile);
		}
	}

	private static void removeEdges(Tile tile) {
		for (Tile adjacent : tile.getAdjacentNodes().keySet()) {
			adjacent.getAdjacentNodes().remove(tile);
		}
	}

	private static void reAddEdges(Tile tile) {
		int y = tile.getY();
		int x = tile.getX();
		if (y - 1 > 0) {
			reAddEdge(graph.getNode(y - 1, x), tile);
		}
		if (y + 1 < maxY) {
			reAddEdge(graph.getNode(y + 1, x), tile);
		}
		if (x - 1 > 0) {
			reAddEdge(graph.getNode(y, x - 1), tile);
		}
		if (x + 1 < maxX) {
			reAddEdge(graph.getNode(y, x + 1), tile);
		}
	}

	private static void reAddEdge(Tile tile, Tile toAdd) {
		if (!tile.isWall()) {
			tile.addDestination(toAdd, 1);
		}
	}

	private static Set<Tile> findOpenEnemyTiles(Fighter fighter) {
		Set<Tile> openEnemyTiles = new HashSet<>();

		for (Fighter enemy : fighters) {
			if (enemy.isAlive() && enemy.isGoblin() != fighter.isGoblin()) {
				int y = enemy.getTile().getY();
				int x = enemy.getTile().getX();
				Tile tile;
				if (y - 1 > 0) {
					tile = graph.getNode(y - 1, x);
					if (tile.canWalkOnto()) {
						openEnemyTiles.add(tile);
					}
				}
				if (y + 1 < maxY) {
					tile = graph.getNode(y + 1, x);
					if (tile.canWalkOnto()) {
						openEnemyTiles.add(tile);
					}
				}
				if (x - 1 > 0) {
					tile = graph.getNode(y, x - 1);
					if (tile.canWalkOnto()) {
						openEnemyTiles.add(tile);
					}
				}
				if (x + 1 < maxX) {
					tile = graph.getNode(y, x + 1);
					if (tile.canWalkOnto()) {
						openEnemyTiles.add(tile);
					}
				}
			}
		}
		return openEnemyTiles;
	}

//////////////
//init
//////////////

	private static void initTiles(List<String> input) {
		maxY = input.size();
		maxX = input.get(0).length();
		grid = new Tile[maxY][maxX];
		int id = 0;
		for (int y = 0; y < input.size(); y++) {
			char[] row = input.get(y).toCharArray();
			for (int x = 0; x < row.length; x++) {
				Tile tile = new Tile(y, x, row[x] == '#');
				if (row[x] != '.' && row[x] != '#') {
					Fighter fighter = new Fighter(id++, row[x] == 'G');
					tile.setFighter(fighter);
					fighter.setTile(tile);
					fighters.add(fighter);

					if (fighter.isGoblin()) {
						goblinCount++;
					} else {
						elfCount++;
					}
				}
				grid[y][x] = tile;
				graph.addNode(tile);
			}
		}

		for (Tile tile : graph.getNodes().values()) {
			if (tile.isWall()) {
				continue;
			}
			int y = tile.getY();
			int x = tile.getX();
			if (y - 1 > 0) {
				addTile(tile, graph.getNode(new Touple<>(y - 1, x)));
			}
			if (x - 1 > 0) {
				addTile(tile, graph.getNode(new Touple<>(y, x - 1)));
			}
			if (x + 1 < maxX) {
				addTile(tile, graph.getNode(new Touple<>(y, x + 1)));
			}
			if (y + 1 < maxY) {
				addTile(tile, graph.getNode(new Touple<>(y + 1, x)));
			}
		}
	}

	private static void addTile(Tile tile, Tile adjacent) {
		if (adjacent.canWalkOnto()) {
			tile.addDestination(adjacent, 1);
		}
	}

//////////////
//print
//////////////

	private static void printTiles() throws IOException {
		StringBuilder sb = new StringBuilder();

		for (int y = 0; y < grid.length; y++) {
			List<Fighter> fightersInRow = new ArrayList<>();
			for (int x = 0; x < grid[y].length; x++) {
				Tile tile = grid[y][x];
				if (tile.isWall()) {
					sb.append("#");
				} else if (tile.getFighter() == null) {
					sb.append(".");
				} else if (tile.getFighter().isGoblin()) {
					sb.append("G");
					fightersInRow.add(tile.getFighter());
				} else {
					sb.append("E");
					fightersInRow.add(tile.getFighter());
				}
			}
			sb.append("   ");
			for (Iterator<Fighter> it = fightersInRow.iterator(); it.hasNext();) {
				Fighter fighter = it.next();
				if (fighter.isGoblin()) {
					sb.append("G");
				} else {
					sb.append("E");
				}
				sb.append("[" + fighter.getId() + "](" + fighter.getHitpoints() + ")");
				if (it.hasNext()) {
					sb.append(", ");
				}
			}
			sb.append("\n");
		}
		System.out.println(sb.toString());
	}
}
