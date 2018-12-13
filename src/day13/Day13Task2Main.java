package day13;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import day13.Cart.Turn;
import util.AdventUtils;
import util.Touple;

//32,132
public class Day13Task2Main {

	public static void main(String[] args) {

		try {
			List<String> input = AdventUtils.getStringInput(13);

			char[][] grid = new char[input.size()][input.get(0).length()];
			List<Cart> carts = new ArrayList<>();

			for (int y = 0; y < grid.length; y++) {
				for (int x = 0; x < grid[y].length; x++) {
					char part = input.get(y).charAt(x);
					grid[y][x] = part;
					if (part == '^' || part == '>' || part == '<' || part == 'v') {
						carts.add(new Cart(y, x));
					}
				}
			}

			for (Cart cart : carts) {
				initBelow(cart, grid);
			}

			while (carts.size() != 1) {
				for (Cart cart : carts) {
					if (cart.isRemoved()) {
						continue;
					}
					Touple<Integer, Integer> crashPosition = moveCart(cart, grid);

					if (crashPosition != null) {
						cart.setRemoved(true);
						for (Cart crashedInto : carts) {
							if (crashPosition.equals(crashedInto.getPosition())) {
								crashedInto.setRemoved(true);
								grid[crashPosition.getLeft()][crashPosition.getRight()] = crashedInto.getTileBelow();
								break;
							}
						}
					}
				}
				carts = carts.stream().filter(cart -> !cart.isRemoved()).sorted().collect(Collectors.toList());
			}
			printGrid(grid);
			AdventUtils.publishResult(13, 2,
					carts.get(0).getPosition().getRight() + "," + carts.get(0).getPosition().getLeft());

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void printGrid(char[][] grid) throws IOException {
		StringBuilder sb = new StringBuilder();

		for (int y = 0; y < grid.length; y++) {
			for (int x = 0; x < grid[y].length; x++) {
				sb.append(grid[y][x]);
			}
			if (y < grid.length - 1) {
				sb.append("\n");
			}
		}
		AdventUtils.writeExtra(13, 2, sb.toString(), "grid");
		// AdventUtils.publishExtra(13, 2, sb.toString(), "grid");
	}

	private static Touple<Integer, Integer> moveCart(Cart cart, char[][] grid) {
		int y = cart.getPosition().getLeft();
		int x = cart.getPosition().getRight();

		Touple<Integer, Integer> newPos = getNextPosition(x, y, grid);

		char nextTile = grid[newPos.getLeft()][newPos.getRight()];

		turnCart(newPos.getLeft(), newPos.getRight(), cart, grid);
		cart.setPosition(newPos);

		grid[y][x] = cart.getTileBelow();
		cart.setTileBelow(nextTile);

		if (nextTile == '>' || nextTile == '^' || nextTile == '<' || nextTile == 'v') {
			return newPos;
		}

		return null;
	}

	private static Touple<Integer, Integer> getNextPosition(int x, int y, char[][] grid) {
		char dir = grid[y][x];
		if (dir == '>') {
			return new Touple<>(y, x + 1);
		}
		if (dir == '^') {
			return new Touple<>(y - 1, x);
		}
		if (dir == '<') {
			return new Touple<>(y, x - 1);
		}
		if (dir == 'v') {
			return new Touple<>(y + 1, x);
		}

		throw new IllegalStateException("Cart not moved");
	}

	private static void turnCart(int y, int x, Cart cart, char[][] grid) {
		char dir = grid[cart.getPosition().getLeft()][cart.getPosition().getRight()];
		char tile = grid[y][x];

		if (tile == '+') {
			if (cart.getNextTurn() == Turn.left) {
				cart.setNextTurn(Turn.straight);
				if (dir == '>') {
					grid[y][x] = '^';
				} else if (dir == '^') {
					grid[y][x] = '<';
				} else if (dir == '<') {
					grid[y][x] = 'v';
				} else if (dir == 'v') {
					grid[y][x] = '>';
				}

			} else if (cart.getNextTurn() == Turn.straight) {
				grid[y][x] = dir;
				cart.setNextTurn(Turn.right);
			} else if (cart.getNextTurn() == Turn.right) {
				cart.setNextTurn(Turn.left);
				if (dir == '>') {
					grid[y][x] = 'v';
				} else if (dir == '^') {
					grid[y][x] = '>';
				} else if (dir == '<') {
					grid[y][x] = '^';
				} else if (dir == 'v') {
					grid[y][x] = '<';
				}
			}
		} else if (tile == '/') {
			if (dir == '>') {
				grid[y][x] = '^';
			} else if (dir == 'v') {
				grid[y][x] = '<';
			} else if (dir == '<') {
				grid[y][x] = 'v';
			} else if (dir == '^') {
				grid[y][x] = '>';
			}

		} else if (tile == '\\') {
			if (dir == 'v') {
				grid[y][x] = '>';
			} else if (dir == '<') {
				grid[y][x] = '^';
			} else if (dir == '^') {
				grid[y][x] = '<';
			} else if (dir == '>') {
				grid[y][x] = 'v';
			}
		} else {
			grid[y][x] = dir;
		}
	}

	private static void initBelow(Cart cart, char[][] grid) {
		int x = cart.getPosition().getRight();
		int y = cart.getPosition().getLeft();

		char left = x - 1 > 0 ? grid[y][x - 1] : ' ';
		char top = y - 1 > 0 ? grid[y - 1][x] : ' ';
		char right = x + 1 < grid[y].length ? grid[y][x + 1] : ' ';
		char bottom = y + 1 < grid.length ? grid[y + 1][x] : ' ';

		if (right != ' ' && right != '|' && top != ' ' && top != '-' && left != ' ' && left != '|' && bottom != ' '
				&& bottom != '-') {
			cart.setTileBelow('+');
		} else if (right != ' ' && right != '|' && left != ' ' && left != '|') {
			cart.setTileBelow('-');
		} else if (top != ' ' && top != '-' && bottom != ' ' && bottom != '-') {
			cart.setTileBelow('|');
		} else if ((right != ' ' && right != '|' && bottom != ' ' && bottom != '-')
				|| (top != ' ' && top != '-' && left != ' ' && left != '|')) {
			cart.setTileBelow('/');
		} else {
			cart.setTileBelow('-');
		}
	}

}
