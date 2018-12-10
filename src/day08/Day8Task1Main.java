package day08;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import util.AdventUtils;

public class Day8Task1Main {

	private static int total = 0;
	private static int id = 0;
	private static int i = 0;

	public static void main(String[] args) {

		try {
			List<Integer> input = AdventUtils.getStringInput(8).stream()
					.flatMap(line -> Arrays.asList(line.split(" ")).stream()).map(Integer::parseInt)
					.collect(Collectors.toList());

			Node root = buildNode(input);

			AdventUtils.writeExtra(8, 1, printNode(root, new StringBuilder(), 0).toString(), "tree");
			AdventUtils.publishResult(8, 1, total);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static StringBuilder printNode(Node node, StringBuilder sb, int depth) {
		for (int x = 0; x < depth; x++) {
			sb.append("\t");
		}
		sb.append(node);
		sb.append("\n");
		depth++;
		for (Node child : node.getChilds()) {
			printNode(child, sb, depth);
		}
		return sb;
	}

	private static Node buildNode(List<Integer> input) {
		Node node = new Node();
		node.setKey(id++);
		int childs = input.get(i);
		i++;
		int metadataEntries = input.get(i);
		i++;
		for (int x = 0; x < childs; x++) {
			node.getChilds().add(buildNode(input));
		}
		for (int x = 0; x < metadataEntries; x++) {
			node.getMetadata().add(input.get(i));
			total += input.get(i);
			i++;
		}

		return node;
	}

}
