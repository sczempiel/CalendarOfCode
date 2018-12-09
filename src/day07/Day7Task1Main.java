package day07;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import day07.Tree.State;
import util.AdventUtils;

public class Day7Task1Main {

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
			root.getChilds().addAll(
					items.values().stream().filter(item -> item.getParents().isEmpty()).collect(Collectors.toList()));

			Tree result = getNext(root, null);

			StringBuilder sb = new StringBuilder();

			while (result != null) {
				sb.append(result.getKey());
				result.setState(State.finish);
				result = getNext(root, null);
			}
			AdventUtils.publishResult(7, 1, sb.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static Tree getNext(Tree tree, Tree result) {
		if (tree.getState() == State.finish) {
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
