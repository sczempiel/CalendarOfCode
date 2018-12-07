package day7;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import day7.Tree.State;
import util.AdventUtils;

public class Day7Task2Main {

	public static void main(String[] args) {

		try {
			List<String> input = AdventUtils.getStringInput(7);

			Map<Character, Tree> items = new HashMap<>();

			input.stream().forEach(entry -> {

				String key = entry.substring(5, 6);

				Tree parent = items.get(key.charAt(0));
				if (parent == null) {
					parent = new Tree();
					parent.setKey(key.charAt(0));
				}

				Tree child = items.get(entry.substring(36, 37).charAt(0));
				if (child == null) {
					child = new Tree();
					child.setKey(entry.substring(36, 37).charAt(0));
				}
				child.getParents().add(parent);
				parent.getChilds().add(child);

				items.put(parent.getKey(), parent);
				items.put(child.getKey(), child);
			});

			Tree root = new Tree();
			root.setState(State.finish);

			final StringBuilder sbTree = new StringBuilder();
			items.values().stream().forEach(tree -> sbTree.append(tree.toString() + "\n"));

			AdventUtils.writeExtra(7, 2, sbTree.toString(), "tree");

			root.getChilds().addAll(
					items.values().stream().filter(item -> item.getParents().isEmpty()).collect(Collectors.toList()));

			Tree result = getNext(root, null);

			StringBuilder sbSteps = new StringBuilder();
			StringBuilder sbOrder = new StringBuilder();

			Worker[] workers = new Worker[5];

			sbSteps.append("time |");

			for (int i = 0; i < workers.length; i++) {
				workers[i] = new Worker(i);
				sbSteps.append(" w" + i + "|");
			}

			sbSteps.append(" finished\n-----+---+---+---+---+---+--------------\n");

			int total = 0;
			while (sbOrder.length() < items.size()) {
				total++;

				String currentlyFinished = sbOrder.toString();

				for (Worker worker : workers) {
					if (worker.isAvailable()) {
						if (result != null) {
							worker.updateCurrentItem(result);
							result = getNext(root, null);
						} else {
							worker.updateCurrentItem(null);
						}
					}
					if (worker.updateWorkForce()) {
						sbOrder.append(worker.getCurrentItem().getKey());
					}
				}
				for (Worker worker : workers) {
					if (worker.isAvailable()) {
						result = getNext(root, null);
						break;
					}
				}
				sbSteps.append(formatNumber(total - 1) + " |");

				for (int i = 0; i < workers.length; i++) {
					printWorker(workers[i], sbSteps);
				}

				sbSteps.append(" " + currentlyFinished + "\n");
			}
			sbSteps.append(formatNumber(total) + " |");

			for (int i = 0; i < workers.length; i++) {
				sbSteps.append(" . |");
			}
			sbSteps.append(" " + sbOrder.toString());

			AdventUtils.writeExtra(7, 2, sbSteps.toString(), "steps");
			AdventUtils.writeExtra(7, 2, sbOrder.toString(), "order");
			AdventUtils.publishResult(7, 2, total);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static String formatNumber(int num) {
		String result = String.valueOf(num);
		while (result.length() < 4) {
			result = " " + result;
		}
		return result;
	}

	private static void printWorker(Worker worker, StringBuilder sb) {
		Tree item = worker.getCurrentItem();
		if (item == null) {
			sb.append(" . |");
		} else {
			sb.append(" " + worker.getCurrentItem().getKey() + " |");
		}
	}

	private static Tree getNext(Tree tree, Tree result) {
		if (tree.getState() == State.working) {
			return result;
		} else if (tree.getState() == State.finish) {
			for (Tree child : tree.getChilds()) {
				result = getNext(child, result);
			}
		} else if (result == null || result.getKey() == null || result.getKey().compareTo(tree.getKey()) > 0) {
			for (Tree parent : tree.getParents()) {
				if (parent.getState() != State.finish) {
					return result;
				}
			}
			result = tree;
		}
		return result;
	}

}
