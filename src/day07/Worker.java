package day07;

import day07.Tree.State;

public class Worker {

	private static final int MINUTE = 60;

	private final int id;
	private Tree currentItem;
	private int secoundsWorked;
	private int secoundsNeeded;

	public Worker(int id) {
		this.id = id;
	}

	public void updateCurrentItem(Tree item) {
		currentItem = item;
		if (item != null) {
			currentItem.setState(State.working);
			secoundsNeeded = MINUTE + Character.getNumericValue(item.getKey()) - Character.getNumericValue('A') + 1;
		} else {
			secoundsNeeded = MINUTE;
		}
		secoundsWorked = 0;
	}

	public boolean updateWorkForce() {
		if (currentItem == null) {
			return false;
		}
		secoundsWorked++;
		if (secoundsWorked == secoundsNeeded) {
			currentItem.setState(State.finish);
			return true;
		}
		return false;
	}

	public boolean isAvailable() {
		return currentItem == null || currentItem.getState() == State.finish;
	}

	public int getId() {
		return id;
	}

	public Tree getCurrentItem() {
		return currentItem;
	}
}
