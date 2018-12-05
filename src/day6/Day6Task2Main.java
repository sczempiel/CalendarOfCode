package day6;

import java.io.IOException;
import java.util.List;

import util.AdventUtils;

public class Day6Task2Main {

	public static void main(String[] args) {

		try {
			List<String> input = AdventUtils.getStringInput(6);

			AdventUtils.publishResult(6, 2, input.size());

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
