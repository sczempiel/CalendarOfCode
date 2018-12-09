package day07;

import java.util.HashSet;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

public class Tree {

	public enum State {
		none, working, finish;
	}

	private Character key;
	private Set<Tree> parents = new HashSet<>();
	private final SortedSet<Tree> childs = new TreeSet<>((t1, t2) -> t1.getKey().compareTo(t2.getKey()));
	private State state = State.none;

	public Character getKey() {
		return key;
	}

	public void setKey(Character key) {
		this.key = key;
	}

	public Set<Tree> getParents() {
		return parents;
	}

	public void setParent(Set<Tree> parents) {
		this.parents = parents;
	}

	public SortedSet<Tree> getChilds() {
		return childs;
	}

	public State getState() {
		return state;
	}

	public void setState(State state) {
		this.state = state;
	}

	public void setParents(Set<Tree> parents) {
		this.parents = parents;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Tree [");
		if (key != null) {
			builder.append("key=");
			builder.append(key);
			builder.append(", ");
		}
		if (parents != null) {
			builder.append("parents={");
			for (Tree parent : parents) {
				builder.append(parent.getKey() + ", ");
			}
			builder.append("}, ");
		}
		if (childs != null) {
			builder.append("childs={");
			for (Tree child : childs) {
				builder.append(child.getKey() + ", ");
			}
			builder.append("}, ");
		}
		if (state != null) {
			builder.append("state=");
			builder.append(state);
		}
		builder.append("]");
		return builder.toString();
	}

}
