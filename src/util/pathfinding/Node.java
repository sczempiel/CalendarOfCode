package util.pathfinding;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import util.Touple;

public class Node<T extends Node<T>> {

	private final Touple<Integer, Integer> coordinate;

	private List<T> shortestPath = new LinkedList<>();

	private int distance = Integer.MAX_VALUE;

	Map<T, Integer> adjacentNodes = new HashMap<>();

	public void addDestination(T destination, int distance) {
		adjacentNodes.put(destination, distance);
	}

	public Node(Touple<Integer, Integer> coordinate) {
		this.coordinate = coordinate;
	}

	public Node(int y, int x) {
		this.coordinate = new Touple<Integer, Integer>(y, x);
	}

	public int getY() {
		return this.coordinate.getLeft();
	}

	public int getX() {
		return this.coordinate.getRight();
	}

	public Touple<Integer, Integer> getCoordinate() {
		return coordinate;
	}

	public List<T> getShortestPath() {
		return shortestPath;
	}

	public void setShortestPath(List<T> shortestPath) {
		this.shortestPath = shortestPath;
	}

	public int getDistance() {
		return distance;
	}

	public void setDistance(int distance) {
		this.distance = distance;
	}

	public Map<T, Integer> getAdjacentNodes() {
		return adjacentNodes;
	}

	public void setAdjacentNodes(Map<T, Integer> adjacentNodes) {
		this.adjacentNodes = adjacentNodes;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((coordinate == null) ? 0 : coordinate.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		@SuppressWarnings("unchecked")
		T other = (T) obj;
		if (coordinate == null) {
			if (other.getCoordinate() != null)
				return false;
		} else if (!coordinate.equals(other.getCoordinate()))
			return false;
		return true;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Node [");
		if (coordinate != null) {
			builder.append("coordinate=");
			builder.append(getY() + "," + getX());
			builder.append(", ");
		}
		if (shortestPath != null) {
			builder.append("shortestPath={");
			for (int i = 0; i < shortestPath.size(); i++) {
				T node = shortestPath.get(i);
				builder.append(node.getY() + "," + node.getX());
				if (i < shortestPath.size() - 1) {
					builder.append(", ");
				}
			}
			builder.append("}, ");
		}
		builder.append("distance=");
		builder.append(distance);
		builder.append(", ");
		builder.append("]");
		return builder.toString();
	}
}