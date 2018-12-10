package util;

public class Touple<R, L> {
	private L left;
	private R right;

	public Touple() {

	}

	public Touple(L left, R right) {
		this.right = right;
		this.left = left;
	}

	public R getRight() {
		return right;
	}

	public void setRight(R right) {
		this.right = right;
	}

	public L getLeft() {
		return left;
	}

	public void setLeft(L left) {
		this.left = left;
	}

}
