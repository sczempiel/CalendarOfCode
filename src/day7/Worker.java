package day7;

import day7.Tree.State;

public class Worker {

	private Tree currentItem;
	private int secoundsWorked;
	private int secoundsNeeded;

	public void updateCurrentItem(Tree item) {
		currentItem = item;
		if (item != null) {
			currentItem.setState(State.working);
			secoundsNeeded = 60 + Character.getNumericValue(item.getKey()) - Character.getNumericValue('A') + 1;
		} else {
			secoundsNeeded = 60;
		}
		secoundsWorked = 1;
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

	public Tree getCurrentItem() {
		return currentItem;
	}
}
