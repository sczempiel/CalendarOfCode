package day19;

import java.io.IOException;

import util.AdventUtils;

public class Day19Task2Main {

	public static void main(String[] args) {

		try {
			int added = 0;
			for (int i = 1; i <= 10551428; i++) {
				if (10551428 % i == 0) {
					added += i;
				}
			}
			AdventUtils.publishResult(19, 2, Long.valueOf(added).intValue());

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
