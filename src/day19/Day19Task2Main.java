package day19;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import util.AdventUtils;
import util.opcode.Opcode;

// 1806 l
// 0 nr
// 1 nr
// 107231714 nr
// 107231712 nr
public class Day19Task2Main {

	public static void main(String[] args) {

		Set<Long> nums = new HashSet<>();
		for (int i = 1; i <= 10551428; i++) {
			double result = 10551428 / i;
			if (Math.round(result) == result) {
				nums.add(Math.round(result));
			}
		}

		try {
			long[] register = new long[6];
//			register[0] = 0;
//			register[1] = 1;
//			register[2] = 10551428;
//			register[3] = 10551428;
//			register[4] = 10551428;
//			register[5] = 4;
			register[0] = 1;
			register[1] = 2;
			register[2] = 10551428;
			register[3] = 10551428 * 2;
			register[4] = 10551428;
			register[5] = 4;

			List<String> input = AdventUtils.getStringInput(19);
			List<Execution> executions = new ArrayList<>();

			// instructionPointer
			int ip = Integer.parseInt(input.get(0).substring(4, input.get(0).length()));
			for (int i = 1; i < input.size(); i++) {

				String[] parts = input.get(i).split(" ");
				Execution execution = new Execution();
				execution.setOpcode(Opcode.getOpcodeByName(parts[0]));
				execution.setA(Integer.parseInt(parts[1]));
				execution.setB(Integer.parseInt(parts[2]));
				execution.setC(Integer.parseInt(parts[3]));
				executions.add(execution);
			}

			// System.out.println(Arrays.toString(register));
			while (register[ip] < executions.size()) {
				if (register[5] == 4) {
					if (nums.contains(register[1])) {
						register[0] += register[1];
					}
					register[4] = register[2];
				}
				Execution execution = executions.get(Long.valueOf(register[ip]).intValue());
				register = execution.getOpcode().execute(execution.getA(), execution.getB(), execution.getC(),
						register);
				register[ip] += 1;

				System.out.println(Arrays.toString(register));
			}

			AdventUtils.publishResult(19, 2, Long.valueOf(register[0]).intValue());

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
