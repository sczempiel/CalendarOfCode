package day15;

import java.util.Iterator;
import java.util.LinkedList;

public class Graph extends util.pathfinding.Graph<Tile> {
	@Override
	protected void calculateMinimumDistance(Tile evaluationNode, Integer edgeWeigh, Tile sourceNode) {
		int dist = sourceNode.getDistance() + edgeWeigh;

		if (dist <= evaluationNode.getDistance()) {
			LinkedList<Tile> shortestPath = new LinkedList<>(sourceNode.getShortestPath());
			shortestPath.add(sourceNode);

			if (dist < evaluationNode.getDistance()) {
				evaluationNode.setShortestPath(shortestPath);
			} else {
				Iterator<Tile> it1 = evaluationNode.getShortestPath().iterator();
				Iterator<Tile> it2 = shortestPath.iterator();
				int compare = 0;
				while (it1.hasNext() && compare == 0) {
					Tile t1 = it1.next();
					Tile t2 = it2.next();
					compare = t2.compareTo(t1);
				}

				if (compare == -1) {
					evaluationNode.setShortestPath(shortestPath);
				}
			}
			evaluationNode.setDistance(dist);
		}
	}
}
