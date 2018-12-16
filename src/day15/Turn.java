package day15;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Turn {
	private final Fighter fighter;
	private final Tile currentTile;
	private Tile targetTile;
	private Integer shortestSteps;
	private List<Move> bestPath;
	private Map<Tile, List<List<Move>>> paths = new HashMap<>();

	public Turn(Fighter fighter, Tile currentTile) {
		this.fighter = fighter;
		this.currentTile = currentTile;
	}

	public List<List<Move>> getPossibleMovesToTarget() {
		if (targetTile == null) {
			return null;
		}
		return paths.get(targetTile);
	}

	public Tile getTargetTile() {
		return targetTile;
	}

	public void setTargetTile(Tile targetTile) {
		this.targetTile = targetTile;
	}

	public Integer getShortestSteps() {
		return shortestSteps;
	}

	public void setShortestSteps(Integer shortestSteps) {
		this.shortestSteps = shortestSteps;
	}

	public List<Move> getBestPath() {
		return bestPath;
	}

	public void setBestPath(List<Move> bestPath) {
		this.bestPath = bestPath;
	}

	public Fighter getFighter() {
		return fighter;
	}

	public Tile getCurrentTile() {
		return currentTile;
	}

	public Map<Tile, List<List<Move>>> getPaths() {
		return paths;
	}

	public void setPaths(Map<Tile, List<List<Move>>> paths) {
		this.paths = paths;
	}

}
