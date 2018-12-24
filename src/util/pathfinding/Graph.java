package util.pathfinding;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

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
		return calculateShortestPathFromSource(source, new HashSet<>(), false);
	}

	public Graph<T> calculateShortestPathFromSource(T source, Set<T> tilesToCalc, boolean findFirst) {
		Set<T> settledNodes = new HashSet<>();
		Set<T> unsettledNodes = new HashSet<>();
		Set<T> toCalc = new HashSet<>(tilesToCalc);

		unsettledNodes.add(source);
		source.setDistance(0);

		while ((tilesToCalc.isEmpty() || !toCalc.isEmpty()) && unsettledNodes.size() != 0) {
			T currentNode = getLowestDistanceNode(unsettledNodes);
			unsettledNodes.remove(currentNode);
			for (Entry<T, Integer> adjacencyPair : currentNode.getAdjacentNodes().entrySet()) {
				T adjacentNode = adjacencyPair.getKey();
				Integer edgeWeight = adjacencyPair.getValue();
				if (!settledNodes.contains(adjacentNode)) {
					calculateMinimumDistance(adjacentNode, edgeWeight, currentNode);
					unsettledNodes.add(adjacentNode);
				}
			}
			if (findFirst && toCalc.contains(currentNode)) {
				return this;
			}
			toCalc.remove(currentNode);
			settledNodes.add(currentNode);
		}
		return this;
	}

	protected void calculateMinimumDistance(T evaluationNode, Integer edgeWeigh, T sourceNode) {
		int dist = sourceNode.getDistance() + edgeWeigh;
		if (dist < evaluationNode.getDistance()) {
			evaluationNode.setDistance(dist);
			LinkedList<T> shortestPath = new LinkedList<>(sourceNode.getShortestPath());
			shortestPath.add(sourceNode);
			evaluationNode.setShortestPath(shortestPath);
		}
	}

	protected T getLowestDistanceNode(Set<T> unsettledNodes) {
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