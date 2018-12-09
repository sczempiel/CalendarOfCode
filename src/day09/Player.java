package day09;

import java.math.BigInteger;

public class Player {

	private final int id;
	private BigInteger score = BigInteger.valueOf(0);

	public BigInteger increaseScore(int toAdd) {
		score = score.add(BigInteger.valueOf(toAdd));
		return score;
	}

	public Player(int id) {
		this.id = id;
	}

	public BigInteger getScore() {
		return score;
	}

	public void setScore(BigInteger score) {
		this.score = score;
	}

	public int getId() {
		return id;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Player [id=");
		builder.append(id);
		builder.append(", score=");
		builder.append(score);
		builder.append("]");
		return builder.toString();
	}

}
