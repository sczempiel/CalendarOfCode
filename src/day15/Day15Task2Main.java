package day15;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import util.AdventUtils;
import util.Touple;

// 19
public class Day15Task2Main {
	private static boolean debug = false;

	private static Graph graph;
	private static Tile[][] grid;
	private static List<Fighter> fighters;
	private static int round = 0;
	private static int goblinCount = -1;
	private static int elfCount = 0;
	private static int initialElfCount = 0;
	private static int maxX;
	private static int maxY;

	private static int elfDmg = 3;

	public static void main(String[] args) {
		StringBuilder sb = new StringBuilder();

		try {
			if (debug) {
				AdventUtils.eraseExtraFile(15, 2, "rounds");
			}
			sim: while (goblinCount != 0) {
				elfCount = 0;
				goblinCount = 0;
				fighters = new ArrayList<>();
				graph = new Graph();
				elfDmg++;

				initTiles(AdventUtils.getStringInput(15));
				if (debug) {
					AdventUtils.eraseExtraFile(15, 2, "rounds");
					sb.append(round);
					sb.append("\n");
					sb.append(printTiles());
					AdventUtils.publishNewExtraLine(15, 2, sb.toString(), "rounds");
				}

				int currentFighter = 0;
				round = 0;

				while (goblinCount > 0 && elfCount > 0) {
					if (currentFighter == 0) {
						round++;
						if (debug) {
							sb = new StringBuilder();
							sb.append("\n");
							sb.append(round);
						}
					}
					turn(fighters.get(currentFighter));
					if (initialElfCount != elfCount) {
						if (debug) {
							System.out.println("damage: " + elfDmg + " initial: " + initialElfCount + " now: "
									+ elfCount + " rounds: " + round);
						}
						goblinCount = -1;
						continue sim;
					}
					currentFighter++;
					if (currentFighter == fighters.size()) {
						if (debug) {
							sb.append("\n");
							sb.append(printTiles());
							AdventUtils.publishNewExtraLine(15, 2, sb.toString(), "rounds");
						}
						currentFighter = 0;
						fighters = fighters.stream().filter(Fighter::isAlive).sorted().collect(Collectors.toList());
					}
				}

				if (currentFighter != fighters.size() && currentFighter != 0) {
					if (debug) {
						sb = new StringBuilder();
						sb.append((round) + " (not completed)");
						sb.append("\n");
						sb.append(printTiles());
						AdventUtils.publishNewExtraLine(15, 2, sb.toString(), "rounds");
					}
					round--;
				}
			}

			int totalHp = fighters.stream().filter(Fighter::isAlive).mapToInt(Fighter::getHitpoints).sum();
			if (debug) {
				System.out.println(totalHp + " * " + round);
			}
			AdventUtils.publishResult(15, 2, totalHp * round);

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
		initGraph();
	}

//////////////
//attack
//////////////

	private static void attack(Fighter fighter) {
		Fighter enemy = findTargetToAttack(fighter);

		if (enemy == null) {
			return;
		}

		enemy.hit(fighter.getDamage());

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

	private static void move(Fighter fighter) {
		Set<Tile> openEnemyTiles = findOpenEnemyTiles(fighter);

		if (openEnemyTiles.isEmpty()) {
			return;
		}

		Integer closestDist = null;
		Tile choosenTarget = null;
		graph.calculateShortestPathFromSource(fighter.getTile(), openEnemyTiles, false);
		for (Tile target : openEnemyTiles) {
			int tDist = target.getDistance();
			if (closestDist == null || closestDist > tDist
					|| (closestDist == tDist && target.compareTo(choosenTarget) == -1)) {
				choosenTarget = target;
				closestDist = target.getDistance();
			}
		}
		Tile newTile = null;
		if (choosenTarget.getShortestPath().size() > 1) {
			newTile = choosenTarget.getShortestPath().get(1);
		} else if (choosenTarget.getShortestPath().size() == 1) {
			newTile = choosenTarget;
		}

		if (newTile != null) {
			Tile current = fighter.getTile();

			newTile.setFighter(fighter);
			fighter.setTile(newTile);
			current.setFighter(null);
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
						fighter.setDamage(elfDmg);
						elfCount++;
					}
				}
				grid[y][x] = tile;
				graph.addNode(tile);
			}
		}
		initialElfCount = elfCount;
		initGraph();
	}

	private static void initGraph() {
		for (Tile tile : graph.getNodes().values()) {
			if (tile.isWall()) {
				continue;
			}
			tile.setAdjacentNodes(new HashMap<>());
			tile.setDistance(Integer.MAX_VALUE);
			tile.setShortestPath(new LinkedList<>());
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

	private static String printTiles() throws IOException {
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
				sb.append("[" + fighter.getId() + "](" + fighter.getHitpoints() + ")<" + fighter.getTile().getY() + ","
						+ fighter.getTile().getX() + ">");
				if (it.hasNext()) {
					sb.append(", ");
				}
			}
			sb.append("\n");
		}
		return sb.toString();
	}
}
