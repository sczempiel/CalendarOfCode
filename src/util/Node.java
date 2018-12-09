package util;

import java.util.ArrayList;
import java.util.List;

public abstract class Node<T, S extends Node<T, S>> {

	private T key;
	private List<S> parents = new ArrayList<>();
	private List<S> childs = new ArrayList<>();

	public T getKey() {
		return key;
	}

	public void setKey(T key) {
		this.key = key;
	}

	public List<S> getParents() {
		return parents;
	}

	public void setParents(List<S> parents) {
		this.parents = parents;
	}

	public List<S> getChilds() {
		return childs;
	}

	public void setChilds(List<S> childs) {
		this.childs = childs;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		if (key != null) {
			builder.append("key=");
			builder.append(key);
			builder.append(", ");
		}
		if (parents != null) {
			builder.append("parents={");
			for (S parent : parents) {
				builder.append(parent.getKey() + ", ");
			}
			builder.append("}, ");
		}
		if (childs != null) {
			builder.append("childs={");
			for (S child : childs) {
				builder.append(child.getKey() + ", ");
			}
			builder.append("}, ");
		}
		return builder.toString();
	}

}
