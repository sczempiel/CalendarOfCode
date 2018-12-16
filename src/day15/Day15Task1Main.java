package day15;

import java.io.IOException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.stream.Collectors;

import util.AdventUtils;

// error after turn 23 when the first elf is killed
public class Day15Task1Main {

	private static Tile[][] tiles;
	private static List<Fighter> fighters = new ArrayList<>();
	private static int rounds = 0;
	private static int goblinCount = 0;
	private static int elfCount = 0;

	public static void main(String[] args) {

		try {
			initTiles(AdventUtils.getStringInput(15));

			AdventUtils.eraseExtraFile(15, 1, "rounds");

			printTiles(true);

			int currentFighter = 0;

			while (goblinCount > 0 && elfCount > 0) {
				turn(fighters.get(currentFighter));
				currentFighter++;
				if (currentFighter == fighters.size()) {
					currentFighter = 0;
					rounds++;
					printTiles(true);
					fighters = fighters.stream().filter(Fighter::isAlive).sorted().collect(Collectors.toList());
				}
			}

			if (currentFighter != fighters.size()) {
				rounds++;
				printTiles(false);
				rounds--;
			}

			int totalHp = fighters.stream().filter(Fighter::isAlive).mapToInt(Fighter::getHitpoints).sum();

			AdventUtils.publishResult(15, 1, totalHp * rounds);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void initTiles(List<String> input) {
		tiles = new Tile[input.size()][input.get(0).length()];
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
				tiles[y][x] = tile;
			}
		}
	}

	private static void printTiles(boolean completed) throws IOException {
		StringBuilder sb = new StringBuilder();

		sb.append(rounds);
		if (!completed) {
			sb.append("(not completed)");
		}
		sb.append("\n");
		for (int y = 0; y < tiles.length; y++) {
			List<Fighter> fightersInRow = new ArrayList<>();
			for (int x = 0; x < tiles[y].length; x++) {
				Tile tile = tiles[y][x];
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
		AdventUtils.publishNewExtraLine(15, 1, sb.toString(), "rounds");
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

	private static void move(Fighter fighter) {
		Move move = calculateMove(fighter);
		Tile current = fighter.getTile();
		if (move != null) {
			Tile newTile = null;
			switch (move) {
			case left:
				newTile = tiles[current.getY()][current.getX() - 1];
				break;
			case up:
				newTile = tiles[current.getY() - 1][current.getX()];
				break;
			case right:
				newTile = tiles[current.getY()][current.getX() + 1];
				break;
			case down:
				newTile = tiles[current.getY() + 1][current.getX()];
				break;
			}
			newTile.setFighter(fighter);
			fighter.setTile(newTile);
			current.setFighter(null);
		}
	}

	private static Move calculateMove(Fighter fighter) {
		Tile currentTile = fighter.getTile();

		Move directMove = tryDirectPath(fighter);
		if (directMove != null) {
			return directMove;
		}

		Turn turn = findPossibleTiles(currentTile.getY(), currentTile.getX(), !fighter.isGoblin(), 0,
				new Turn(fighter, currentTile), new ArrayList<>(), new ArrayList<>());

		if (turn.getPaths().isEmpty()) {
			return null;
		}

		Set<Tile> tiles = turn.getPaths().keySet();

		Tile closestTile = null;

		for (Tile tile : tiles) {
			if (closestTile == null || tile.compareTo(closestTile) == -1) {
				closestTile = tile;
			}
		}

		turn.setTargetTile(closestTile);

		List<Move> bestMoves = null;

		for (List<Move> moves : turn.getPossibleMovesToTarget()) {
			if (bestMoves != null) {
				int movesSize = moves.size();
				int bestMovesSize = bestMoves.size();
				if (movesSize < bestMovesSize) {
					bestMoves = moves;
				} else if (movesSize == bestMovesSize && movesSize != 0
						&& Move.compare(bestMoves.get(0), moves.get(0)) < 0) {
					bestMoves = moves;
				}
			} else {
				bestMoves = moves;
			}
		}

		turn.setBestPath(bestMoves);

		if (bestMoves.isEmpty()) {
			return null;
		}

		return bestMoves.get(0);

	}

	private static Move tryDirectPath(Fighter fighter) {
		Tile currentTile = fighter.getTile();
		Fighter closest = null;
		for (Fighter enemy : fighters) {
			if (fighter.isGoblin() != enemy.isGoblin()) {
				int y = fighter.getTile().getY();
				int x = fighter.getTile().getX();
				boolean hasFreeTiles = tiles[y - 1][x].canWalkOnto() || tiles[y + 1][x].canWalkOnto()
						|| tiles[y][x - 1].canWalkOnto() || tiles[y][x + 1].canWalkOnto();
				if (hasFreeTiles && (closest == null || enemy.compareTo(closest) == -1)) {
					closest = enemy;
				}
			}
		}

		int yMove = currentTile.getY() - closest.getTile().getY();
		int xMove = currentTile.getX() - closest.getTile().getX();
		Integer lastYMove = null;
		Integer lastXMove = null;
		List<Move> directMoves = new ArrayList<>();
		List<Tile> visitedTiles = new ArrayList<>();
		Tile tile = fighter.getTile();
		while (lastYMove == null || lastYMove != yMove || lastXMove == null || lastXMove != xMove) {
			lastYMove = yMove;
			lastXMove = xMove;
			while (yMove > 0 && tiles[tile.getY() - 1][tile.getX()].canWalkOnto()) {
				tile = tiles[tile.getY() - 1][tile.getX()];
				yMove--;
				directMoves.add(Move.up);
				visitedTiles.add(tile);
			}
			while (xMove > 0 && tiles[tile.getY()][tile.getX() - 1].canWalkOnto()) {
				tile = tiles[tile.getY()][tile.getX() - 1];
				xMove--;
				directMoves.add(Move.left);
				visitedTiles.add(tile);
			}
			while (xMove < 0 && tiles[tile.getY()][tile.getX() + 1].canWalkOnto()) {
				tile = tiles[tile.getY()][tile.getX() + 1];
				xMove++;
				directMoves.add(Move.right);
				visitedTiles.add(tile);
			}
			while (yMove < 0 && tiles[tile.getY() + 1][tile.getX()].canWalkOnto()) {
				tile = tiles[tile.getY() + 1][tile.getX()];
				yMove++;
				directMoves.add(Move.down);
				visitedTiles.add(tile);
			}
		}

		if (!getNearEnemies(tile.getY(), tile.getX(), closest.isGoblin()).isEmpty()) {
			return directMoves.get(0);
		}

		return null;
	}

	private static List<Fighter> getNearEnemies(int y, int x, boolean enemyIsGoblin) {
		List<Fighter> enemies = new ArrayList<>();
		if (y - 1 > 0) {
			addEnemy(getEnemy(y - 1, x, enemyIsGoblin), enemies);
		}
		if (y + 1 < tiles.length) {
			addEnemy(getEnemy(y + 1, x, enemyIsGoblin), enemies);
		}
		if (x - 1 > 0) {
			addEnemy(getEnemy(y, x - 1, enemyIsGoblin), enemies);
		}
		if (x + 1 < tiles[y].length) {
			addEnemy(getEnemy(y, x + 1, enemyIsGoblin), enemies);
		}
		return enemies;
	}

	private static void addEnemy(Fighter enemy, List<Fighter> enemies) {
		if (enemy != null) {
			enemies.add(enemy);
		}
	}

	private static Turn findPossibleTiles(int y, int x, boolean enemyIsGoblin, int currentSteps, Turn turn,
			List<Tile> visitedTiles, List<Move> moves) {
		if (visitedTiles.contains(tiles[y][x])) {
			return turn;
		}
		visitedTiles.add(tiles[y][x]);

		if (!getNearEnemies(y, x, enemyIsGoblin).isEmpty()) {
			if (turn.getShortestSteps() != null && turn.getShortestSteps() > currentSteps) {
				turn.setPaths(new HashMap<>());
			}
			if (turn.getShortestSteps() == null || turn.getShortestSteps() >= currentSteps) {

				Map<Tile, List<List<Move>>> paths = turn.getPaths();
				List<List<Move>> pathes = paths.get(tiles[y][x]);
				if (pathes == null) {
					pathes = new ArrayList<>();
					paths.put(tiles[y][x], pathes);
				}
				pathes.add(moves);
				turn.setShortestSteps(currentSteps);
			}
		} else {
			currentSteps++;
			if (turn.getShortestSteps() != null && currentSteps > turn.getShortestSteps()) {
				return turn;
			}

			if (y - 1 > 0 && tiles[y - 1][x].canWalkOnto()) {
				List<Move> newMoves = new ArrayList<>(moves);
				newMoves.add(Move.up);
				findPossibleTiles(y - 1, x, enemyIsGoblin, currentSteps, turn, new ArrayList<>(visitedTiles), newMoves);
			}
			if (x - 1 > 0 && tiles[y][x - 1].canWalkOnto()) {
				List<Move> newMoves = new ArrayList<>(moves);
				newMoves.add(Move.left);
				findPossibleTiles(y, x - 1, enemyIsGoblin, currentSteps, turn, new ArrayList<>(visitedTiles), newMoves);
			}
			if (x + 1 < tiles[y].length && tiles[y][x + 1].canWalkOnto()) {
				List<Move> newMoves = new ArrayList<>(moves);
				newMoves.add(Move.right);
				findPossibleTiles(y, x + 1, enemyIsGoblin, currentSteps, turn, new ArrayList<>(visitedTiles), newMoves);
			}
			if (y + 1 < tiles.length && tiles[y + 1][x].canWalkOnto()) {
				List<Move> newMoves = new ArrayList<>(moves);
				newMoves.add(Move.down);
				findPossibleTiles(y + 1, x, enemyIsGoblin, currentSteps, turn, new ArrayList<>(visitedTiles), newMoves);
			}
		}

		return turn;
	}

	private static Fighter getEnemy(int y, int x, boolean isGoblin) {
		Fighter fighter = tiles[y][x].getFighter();
		if (fighter != null && fighter.isGoblin() == isGoblin) {
			return fighter;
		}
		return null;
	}
}
