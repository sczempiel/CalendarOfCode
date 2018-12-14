package util;

import java.util.ArrayList;
import java.util.List;

public class LoopingList<E> extends ArrayList<E> {

	private static final long serialVersionUID = 1L;

	@Override
	public E get(int index) {
		return super.get(getRealIndex(index));
	}

	@Override
	public E set(int index, E element) {
		return super.set(getRealIndex(index), element);
	}

	@Override
	public void add(int index, E element) {
		super.add(getRealIndex(index), element);
	}

	@Override
	public E remove(int index) {
		return super.remove(getRealIndex(index));
	}

	@Override
	public List<E> subList(int fromIndex, int toIndex) {
		int realFrom = getRealIndex(fromIndex);
		int realTo = getRealIndex(toIndex);

		if (realFrom < realTo) {
			return super.subList(realTo, realFrom);
		}

		return super.subList(realFrom, realTo);
	}

	public int getRealIndex(int index) {
		while (index < 0) {
			index += this.size();
		}
		while (index >= this.size()) {
			index -= this.size();
		}
		return index;
	}

}
