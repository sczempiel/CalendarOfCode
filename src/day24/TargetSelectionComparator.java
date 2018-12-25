package day24;

import java.util.Comparator;

public class TargetSelectionComparator implements Comparator<Units> {

	@Override
	public int compare(Units u1, Units u2) {
		int dmg = u1.getEffectivePower();
		int u2Dmg = u2.getEffectivePower();
		if (dmg < u2Dmg) {
			return 1;
		}
		if (dmg > u2Dmg) {
			return -1;
		}
		if (u1.getInit() < u2.getInit()) {
			return 1;
		}
		if (u1.getInit() > u2.getInit()) {
			return -1;
		}
		return 0;
	}
}
