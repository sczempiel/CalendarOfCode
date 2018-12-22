package day22;

public enum RegionType {
	rocky(0), wet(1), narrow(2);

	private RegionType(int risk) {
		this.risk = risk;
	}

	private final int risk;

	public int getRisk() {
		return risk;
	}
}
