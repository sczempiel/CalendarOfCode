package day10;

import util.Touple;

public class Point extends Touple<Integer, Integer> {

	public Point() {
		super();
	}

	public Point(Integer right, Integer left) {
		super(right, left);
	}

	public void add(Point that) {
		this.setLeft(this.getLeft() + that.getLeft());
		this.setRight(this.getRight() + that.getRight());
	}

	public boolean isNeighbourOf(Point that) {
		int ySpace = Math.abs(getRight()) - Math.abs(that.getRight());
		int xSpace = Math.abs(getLeft()) - Math.abs(that.getLeft());

		return ySpace <= 1 && ySpace >= -1 && xSpace <= 1 && xSpace >= -1;
	}
}
