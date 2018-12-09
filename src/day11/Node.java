package day11;

import java.util.ArrayList;
import java.util.List;

public class Node extends util.Node<Integer, Node> {

	private List<Integer> metadata = new ArrayList<>();
	private int sum;

	public List<Integer> getMetadata() {
		return metadata;
	}

	public void setMetadata(List<Integer> metadata) {
		this.metadata = metadata;
	}

	public int getSum() {
		return sum;
	}

	public void setSum(int sum) {
		this.sum = sum;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Node [");
		builder.append(super.toString());
		if (metadata != null) {
			builder.append("metadata={");
			for (Integer meta : metadata) {
				builder.append(meta + ", ");
			}
			builder.append("}, ");
		}
		builder.append("sum=");
		builder.append(sum);
		builder.append("]");
		return builder.toString();
	}

}
