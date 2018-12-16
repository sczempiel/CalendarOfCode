package day15;

public class Tile implements Comparable<Tile> {
	private final int y;
	private final int x;
	private final boolean isWall;
	private Fighter fighter;

	public Tile(int y, int x, boolean isWall) {
		this.y = y;
		this.x = x;
		this.isWall = isWall;
	}

	@Override
	public int compareTo(Tile that) {
		if (this.y < that.y) {
			return -1;
		}
		if (this.y > that.y) {
			return 1;
		}
		if (this.x < that.x) {
			return -1;
		}
		if (this.x > that.x) {
			return 1;
		}
		return 0;
	}

	public int getY() {
		return y;
	}

	public int getX() {
		return x;
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
		builder.append(y);
		builder.append(", x=");
		builder.append(x);
		builder.append(", isWall=");
		builder.append(isWall);
		builder.append(", ");
		if (fighter != null) {
			builder.append("fighter=");
			builder.append(fighter.getId());
		}
		builder.append("]");
		return builder.toString();
	}

}
