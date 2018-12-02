package day2;

public class Touple<R, L> {
	private R right;
	private L left;

	public Touple() {

	}

	public Touple(R right, L left) {
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
