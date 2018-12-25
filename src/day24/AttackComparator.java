package day24;

import java.util.Comparator;

public class AttackComparator implements Comparator<Units> {

	@Override
	public int compare(Units u1, Units u2) {
		if (u1.getInit() < u2.getInit()) {
			return 1;
		}
		if (u1.getInit() > u2.getInit()) {
			return -1;
		}
		return 0;
	}
}
