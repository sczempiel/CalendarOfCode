package day15;

public enum Move {
	left(2), up(3), right(1), down(0);

	private final int priority;

	private Move(int priority) {
		this.priority = priority;
	}

	public static int compare(Move m1, Move m2) {
		return m1.priority - m2.priority;
	}
}
