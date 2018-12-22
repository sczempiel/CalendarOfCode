package day21;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import util.AdventUtils;
import util.opcode.Eqrr;
import util.opcode.Execution;
import util.opcode.Opcode;

public class Day21Task2Main {

	private static List<Execution> executions = new ArrayList<>();
	private static int ip;
	private static List<Integer> nums = new ArrayList<>();

	public static void main(String[] args) {

		try {
			List<String> input = AdventUtils.getStringInput(21);

			// instructionPointer
			ip = Integer.parseInt(input.get(0).substring(4, input.get(0).length()));
			for (int i = 1; i < input.size(); i++) {
				String[] parts = input.get(i).split(" ");
				Execution execution = new Execution();
				execution.setOpcode(Opcode.getOpcodeByName(parts[0]));
				execution.setA(Integer.parseInt(parts[1]));
				execution.setB(Integer.parseInt(parts[2]));
				execution.setC(Integer.parseInt(parts[3]));
				executions.add(execution);
			}

			int[] register = new int[6];
			register[0] = 1;

			int lastR1 = 0;

			List<Integer> hits = new ArrayList<>();
			List<Integer> diffs = new ArrayList<>();
			int hit = 0;

			while (register[ip] < executions.size()) {
				Execution execution = executions.get(Long.valueOf(register[ip]).intValue());
				register = execution.getOpcode().execute(execution.getA(), execution.getB(), execution.getC(),
						register);
				register[ip] += 1;

				if (execution.getOpcode().getClass() == Eqrr.class && register[1] >= 0 && register[1] != lastR1) {
					int index = nums.indexOf(register[1]);
					nums.add(register[1]);
					if (index != -1) {
						if (hits.size() == 0) {
							hits.add(index);
							hit = register[1];
						}
						if (register[1] == hit) {
							hits.add(nums.size() - 1);
							int diff = hits.get(hits.size() - 1) - hits.get(hits.size() - 2);
							if (diffs.contains(diff)) {
								break;
							}
							diffs.add(diff);
						}
					}
					lastR1 = register[1];
				}
			}

			Integer longestRun = null;
			for (int i = nums.size() - 1; i > -1; i--) {
				Integer num = nums.get(i);
				if (nums.indexOf(num) == i) {
					longestRun = num;
					break;
				}
			}

			AdventUtils.publishResult(21, 2, longestRun);

		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}
