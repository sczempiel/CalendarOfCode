package day09;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

import util.AdventUtils;

public class Day9Task1Main {

	public static void main(String[] args) {

		try {
			List<String> input = AdventUtils.getStringInput(9);

			String[] parts = input.get(0).split(" ");

			int playerCount = Integer.parseInt(parts[0]);
			int lastMarble = Integer.parseInt(parts[parts.length - 2]);

			Player[] players = new Player[playerCount];

			for (int i = 0; i < playerCount; i++) {
				players[i] = new Player(i + 1);
			}

			LinkedList<Integer> circle = new LinkedList<>();
			circle.add(0);

			ListIterator<Integer> it = circle.listIterator();

			int currentPlayer = 0;

			// AdventUtils.eraseExtraFile(9, 1, "rounds");

			// AdventUtils.writeNewExtraLine(9, 1, printCircle(circle, 0, players, -1,
			// lastMarble), "rounds");

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

//				AdventUtils.writeNewExtraLine(9, 1,
//						printCircle(circle, currentMarble, players, currentPlayer, lastMarble), "rounds");

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
			AdventUtils.writeExtra(9, 1, sbPlayer.toString(), "player");
			AdventUtils.publishResult(9, 1, bestPlayer.getScore().toString());

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unused")
	private static String printCircle(List<Integer> circle, int currentMarble, Player[] players, int currentPlayer,
			int lastMarble) {
		int maxDigits = String.valueOf(lastMarble).length();
		int maxPlayerIdDigits = String.valueOf(players[players.length - 1].getId()).length();

		StringBuilder sb = new StringBuilder();
		if (currentPlayer == -1) {
			String placeholder = "";
			while (placeholder.length() < maxPlayerIdDigits) {
				placeholder += "-";
			}
			sb.append("[" + placeholder + "] ");
		} else {
			sb.append("[" + AdventUtils.printNum(players[currentPlayer].getId(), maxPlayerIdDigits) + "] ");
		}
		for (Integer marble : circle) {
			String formatted = AdventUtils.printNum(marble, maxDigits);
			if (marble == currentMarble) {
				sb.append(" (" + formatted + ") ");
			} else {
				sb.append("  " + formatted + "  ");
			}
		}

		sb.append("\n");

		return sb.toString();
	}

}
