package day16;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import util.AdventUtils;
import util.opcode.Opcode;

// 239 l
public class Day16Task1Main {

	public static void main(String[] args) {

		try {
			List<String> input = AdventUtils.getStringInput(16);

			List<Execution> executions = new ArrayList<>();

			int emptyLineCount = 0;
			for (int i = 0; i < input.size(); i++) {
				String line = input.get(i);

				if (line.isEmpty()) {
					emptyLineCount++;
					if (emptyLineCount == 3) {
						break;
					}
					continue;
				}
				emptyLineCount = 0;

				String[] line2 = input.get(++i).split(" ");
				String line3 = input.get(++i);

				String[] r1 = line.substring(9, line.length() - 1).split(", ");
				String[] r2 = line3.substring(9, line3.length() - 1).split(", ");

				Execution execution = new Execution();

				for (int j = 0; j < 4; j++) {
					execution.getRegistersBefore()[j] = Integer.parseInt(r1[j]);
					execution.getRegistersAfter()[j] = Integer.parseInt(r2[j]);
					execution.getInstructions()[j] = Integer.parseInt(line2[j]);
				}

				executions.add(execution);
			}
			int matching = 0;
			for (Execution execution : executions) {
				int matchingOpcode = 0;
				for (Opcode opcode : Opcode.OPCODES) {
					int[] instructions = execution.getInstructions();
					long[] result = opcode.execute(instructions[1], instructions[2], instructions[3],
							execution.getRegistersBefore());
					if (Arrays.equals(result, execution.getRegistersAfter())) {
						matchingOpcode++;
					}
				}
				if (matchingOpcode > 2) {
					matching++;
				}
			}

			AdventUtils.publishResult(16, 1, matching);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
