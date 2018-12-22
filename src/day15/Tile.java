package day15;

import util.Touple;
import util.pathfinding.Node;

public class Tile extends Node<Tile> implements Comparable<Tile> {
	private final boolean isWall;
	private Fighter fighter;

	public Tile(int y, int x, boolean isWall) {
		super(y, x);
		this.isWall = isWall;
	}

	public Tile(Touple<Integer, Integer> coordinate, boolean isWall) {
		super(coordinate);
		this.isWall = isWall;
	}

	@Override
	public int compareTo(Tile that) {
		if (this.getY() < that.getY()) {
			return -1;
		}
		if (this.getY() > that.getY()) {
			return 1;
		}
		if (this.getX() < that.getX()) {
			return -1;
		}
		if (this.getX() > that.getX()) {
			return 1;
		}
		return 0;
	}

	public boolean isWall() {
		return isWall;
	}

	public boolean canWalkOnto() {
		return fighter == null && !isWall;
	}

	public Fighter getFighter() {
		return fighter;
	}

	public void setFighter(Fighter fighter) {
		this.fighter = fighter;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Tile [y=");
		builder.append(getY());
		builder.append(", x=");
		builder.append(getX());
		builder.append(", isWall=");
		builder.append(isWall);
		if (fighter != null) {
			builder.append(", ");
			builder.append("fighter=");
			builder.append(fighter.getId());
		}
		builder.append("]");
		return builder.toString();
	}

}
