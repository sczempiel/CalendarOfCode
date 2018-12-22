package day22;

import util.Touple;
import util.pathfinding.Node;

public class Tile extends Node<Tile> {
	private int erosionLevel;
	private int geoloficIndex;
	private RegionType regionType;

	public Tile(int y, int x) {
		super(y, x);
	}

	public boolean suportSameTool(Tile that) {
		if (regionType == null || that.regionType == null) {
			return false;
		}
		if (regionType == that.regionType) {
			return true;
		}
		if (regionType == RegionType.rocky && that.regionType != RegionType.wet) {

		}
		if (regionType == RegionType.wet && that.regionType != RegionType.narrow) {

		}
		if (regionType == RegionType.narrow && that.regionType != RegionType.rocky) {

		}
		return false;
	}

	public Tile(Touple<Integer, Integer> coordinate) {
		super(coordinate);
	}

	public int getErosionLevel() {
		return erosionLevel;
	}

	public void setErosionLevel(int erosionLevel) {
		this.erosionLevel = erosionLevel;
	}

	public int getGeoloficIndex() {
		return geoloficIndex;
	}

	public void setGeoloficIndex(int geoloficIndex) {
		this.geoloficIndex = geoloficIndex;
	}

	public RegionType getRegionType() {
		return regionType;
	}

	public void setRegionType(RegionType regionType) {
		this.regionType = regionType;
	}

}
