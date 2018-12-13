package day13;

import util.Touple;

public class Cart implements Comparable<Cart> {

	public enum Turn {
		left, straight, right
	}

	private Touple<Integer, Integer> position;
	private Turn nextTurn = Turn.left;
	private char tileBelow;
	private boolean isRemoved;

	public Cart(int y, int x) {
		this.position = new Touple<>(y, x);
	}

	public Touple<Integer, Integer> getPosition() {
		return position;
	}

	public void setPosition(Touple<Integer, Integer> position) {
		this.position = position;
	}

	public Turn getNextTurn() {
		return nextTurn;
	}

	public void setNextTurn(Turn nextTurn) {
		this.nextTurn = nextTurn;
	}

	public char getTileBelow() {
		return tileBelow;
	}

	public void setTileBelow(char tileBelow) {
		this.tileBelow = tileBelow;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Cart [");
		if (position != null) {
			builder.append("position=");
			builder.append(position);
			builder.append(", ");
		}
		if (nextTurn != null) {
			builder.append("nextTurn=");
			builder.append(nextTurn);
			builder.append(", ");
		}
		builder.append("tileBelow=");
		builder.append(tileBelow);
		builder.append(", isRemoved=");
		builder.append(isRemoved);
		builder.append("]");
		return builder.toString();
	}

	@Override
	public int compareTo(Cart that) {
		if (this.position.getLeft() > that.position.getLeft()) {
			return 1;
		} else if (this.position.getLeft() < that.position.getLeft()) {
			return -1;
		} else if (this.position.getRight() > that.position.getRight()) {
			return 1;
		} else if (this.position.getRight() < that.position.getRight()) {
			return -1;
		}
		return 0;
	}

	public boolean isRemoved() {
		return isRemoved;
	}

	public void setRemoved(boolean isRemoved) {
		this.isRemoved = isRemoved;
	}
}
