package day1;

import java.io.IOException;

import util.AdventUtils;

public class Day1Task1Main {

	public static void main(String[] args) {
		try {
			int startValue = AdventUtils.getIntegerInput(1).stream().mapToInt(Integer::intValue).sum();
			AdventUtils.publishResult(1, 1, startValue);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
