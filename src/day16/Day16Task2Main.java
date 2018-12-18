package day16;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import day16.opcode.Opcode;
import util.AdventUtils;

public class Day16Task2Main {

	public static void main(String[] args) {

		try {
			List<String> input = AdventUtils.getStringInput(16);

			List<Execution> executions = new ArrayList<>();
			List<int[]> instructionsList = new ArrayList<>();

			Map<Integer, List<Opcode>> opcodes = new HashMap<>();

			for (int i = 0; i < 16; i++) {
				opcodes.put(i, new ArrayList<>(Opcode.OPCODES));
			}

			int emptyLineCount = 0;
			boolean isTestData = false;
			for (int i = 0; i < input.size(); i++) {
				String line = input.get(i);

				if (line.isEmpty()) {
					emptyLineCount++;
					if (emptyLineCount == 3) {
						isTestData = true;
					}
					continue;
				}
				emptyLineCount = 0;

				if (!isTestData) {
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
				} else {
					int[] instructions = new int[4];
					String[] parts = line.split(" ");
					for (int j = 0; j < 4; j++) {
						instructions[j] = Integer.parseInt(parts[j]);
					}
					instructionsList.add(instructions);
				}
			}
			for (Execution execution : executions) {
				int[] instructions = execution.getInstructions();
				for (Opcode opcode : Opcode.OPCODES) {
					int[] result = opcode.execute(instructions[1], instructions[2], instructions[3],
							execution.getRegistersBefore());
					if (!Arrays.equals(result, execution.getRegistersAfter())) {
						opcodes.get(instructions[0]).remove(opcode);
					}
				}
			}

			boolean removed = true;
			while (removed) {
				removed = false;
				for (int i = 0; i < 16; i++) {
					List<Opcode> ops = opcodes.get(i);
					if (ops.size() == 1) {
						for (int j = 0; j < 16; j++) {
							if (j != i) {
								if (opcodes.get(j).remove(ops.get(0))) {
									removed = true;
								}
							}
						}
					}
				}
			}

			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < 16; i++) {
				sb.append("opcode: ");
				sb.append(i);
				sb.append(" [");
				List<Opcode> ops = opcodes.get(i);
				for (int j = 0; j < ops.size(); j++) {

					sb.append(ops.get(j).getClass().getSimpleName());
					if (j < ops.size() - 1) {
						sb.append(", ");
					}
				}
				sb.append("]");
				if (i < 15) {
					sb.append("\n");
				}
			}

			AdventUtils.writeExtra(16, 2, sb.toString(), "opcodes");

			int[] register = new int[4];

			for (int[] ins : instructionsList) {
				register = opcodes.get(ins[0]).get(0).execute(ins[1], ins[2], ins[3], register);
			}

			AdventUtils.publishResult(16, 2, register[0]);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
