package day15;

import java.util.Comparator;

public class MoveComparator implements Comparator<Tile> {

	@Override
	public int compare(Tile t1, Tile t2) {
		if (t1.getY() < t2.getY()) {
			return -1;
		}
		if (t1.getY() > t2.getY()) {
			return 1;
		}
		if (t1.getY() < t2.getY()) {
			return -1;
		}
		if (t1.getY() > t2.getY()) {
			return 1;
		}
		
		return 0;
	}

}
