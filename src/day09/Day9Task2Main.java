package day09;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

import util.AdventUtils;

public class Day9Task2Main {

	public static void main(String[] args) {

		try {
			List<String> input = AdventUtils.getStringInput(9);

			String[] parts = input.get(0).split(" ");

			int playerCount = Integer.parseInt(parts[0]);
			int lastMarble = Integer.parseInt(parts[parts.length - 2]) * 100;

			Player[] players = new Player[playerCount];

			for (int i = 0; i < playerCount; i++) {
				players[i] = new Player(i + 1);
			}

			LinkedList<Integer> circle = new LinkedList<>();
			circle.add(0);

			ListIterator<Integer> it = circle.listIterator();

			int currentPlayer = 0;

			for (int currentMarble = 1; currentMarble <= lastMarble; currentMarble++) {

				if (currentMarble % 23 == 0) {
					int pickedMarble = 0;
					for (int i = 0; i < 8; i++) {
						if (!it.hasPrevious()) {
							it = circle.listIterator(circle.size());
						}
						pickedMarble = it.previous();
					}
					players[currentPlayer].increaseScore(currentMarble + pickedMarble);
					it.remove();
					it.next();
				} else {
					if (!it.hasNext()) {
						it = circle.listIterator();
					}
					it.next();
					it.add(currentMarble);
				}

				currentPlayer++;
				if (currentPlayer == playerCount) {
					currentPlayer = 0;
				}
			}

			StringBuilder sbPlayer = new StringBuilder();
			Player bestPlayer = null;
			for (Player player : players) {
				if (bestPlayer == null || player.getScore().compareTo(bestPlayer.getScore()) > 0) {
					bestPlayer = player;
				}
				sbPlayer.append(player + "\n");
			}

			AdventUtils.writeExtra(9, 2, sbPlayer.toString(), "player");
			AdventUtils.publishResult(9, 2, bestPlayer.getScore().toString());

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
