package day4;

import java.util.Arrays;

public class Shift {
	private int guardId;
	private Integer[] awakeStatus = new Integer[60];

	@Override
	public String toString() {
		return "Shift [guardId=" + guardId + ", awakeStatus=" + Arrays.toString(awakeStatus) + "]";
	}

	public int getGuardId() {
		return guardId;
	}

	public void setGuardId(int guardId) {
		this.guardId = guardId;
	}

	public Integer[] getAwakeStatus() {
		return awakeStatus;
	}

	public void setAwakeStatus(Integer[] awakeStatus) {
		this.awakeStatus = awakeStatus;
	}

}
