package day12;

public class Rule {
	private boolean twoLeft;
	private boolean left;
	private boolean toCheck;
	private boolean right;
	private boolean twoRight;
	private boolean outcome;

	public boolean isTwoLeft() {
		return twoLeft;
	}

	public void setTwoLeft(boolean twoLeft) {
		this.twoLeft = twoLeft;
	}

	public boolean isLeft() {
		return left;
	}

	public void setLeft(boolean left) {
		this.left = left;
	}

	public boolean isToCheck() {
		return toCheck;
	}

	public void setToCheck(boolean toCheck) {
		this.toCheck = toCheck;
	}

	public boolean isRight() {
		return right;
	}

	public void setRight(boolean right) {
		this.right = right;
	}

	public boolean isTwoRight() {
		return twoRight;
	}

	public void setTwoRight(boolean twoRight) {
		this.twoRight = twoRight;
	}

	public boolean isOutcome() {
		return outcome;
	}

	public void setOutcome(boolean outcome) {
		this.outcome = outcome;
	}

}
