package util.pathfinding;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import util.Touple;

public class Graph<T extends Node<T>> {

	private Map<Touple<Integer, Integer>, T> nodes = new HashMap<>();

	public void addNode(T node) {
		nodes.put(node.getCoordinate(), node);
	}

	public T getNode(int y, int x) {
		return getNode(new Touple<>(y, x));
	}

	public T getNode(Touple<Integer, Integer> coordinate) {
		return nodes.get(coordinate);
	}

	public Map<Touple<Integer, Integer>, T> getNodes() {
		return nodes;
	}

	public void setNodes(Map<Touple<Integer, Integer>, T> nodes) {
		this.nodes = nodes;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Graph [");
		if (nodes != null) {
			builder.append("nodes=");
			builder.append(nodes);
		}
		builder.append("]");
		return builder.toString();
	}

	public Graph<T> calculateShortestPathFromSource(T source) {
		for (T node : nodes.values()) {
			node.setShortestPaths(new ArrayList<>());
			node.setDistance(Integer.MAX_VALUE);
		}
		Set<T> settledNodes = new HashSet<>();
		Set<T> unsettledNodes = new HashSet<>();

		unsettledNodes.add(source);
		source.setDistance(0);

		while (unsettledNodes.size() != 0) {
			T currentNode = getLowestDistanceNode(unsettledNodes);
			unsettledNodes.remove(currentNode);
			for (Entry<T, Integer> adjacencyPair : currentNode.getAdjacentNodes().entrySet()) {
				T adjacentNode = adjacencyPair.getKey();
				Integer edgeWeight = adjacencyPair.getValue();
				if (!settledNodes.contains(adjacentNode)) {
					unsettledNodes.add(adjacentNode);
					calculateMinimumDistance(adjacentNode, edgeWeight, currentNode);
				}
			}
			settledNodes.add(currentNode);
		}
		return this;
	}

	private void calculateMinimumDistance(T evaluationNode, Integer edgeWeigh, T sourceNode) {
		Integer sourceDistance = sourceNode.getDistance();
		if (sourceDistance + edgeWeigh <= evaluationNode.getDistance()) {
			if (sourceDistance + edgeWeigh < evaluationNode.getDistance()) {
				evaluationNode.setShortestPaths(new ArrayList<>());
				evaluationNode.setDistance(sourceDistance + edgeWeigh);
			}
			List<List<T>> shortestPaths = sourceNode.getShortestPaths();
			if (shortestPaths.size() != 0) {
				for (List<T> path : shortestPaths) {
					LinkedList<T> shortestPath = new LinkedList<>(path);
					shortestPath.add(sourceNode);
					evaluationNode.getShortestPaths().add(shortestPath);
				}
			} else {
				LinkedList<T> shortestPath = new LinkedList<>();
				shortestPath.add(sourceNode);
				evaluationNode.getShortestPaths().add(shortestPath);
			}
		}
	}

	private T getLowestDistanceNode(Set<T> unsettledNodes) {
		T lowestDistanceNode = null;
		int lowestDistance = Integer.MAX_VALUE;
		for (T node : unsettledNodes) {
			int nodeDistance = node.getDistance();
			if (nodeDistance < lowestDistance) {
				lowestDistance = nodeDistance;
				lowestDistanceNode = node;
			}
		}
		return lowestDistanceNode;
	}
}