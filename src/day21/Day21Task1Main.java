package day21;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import util.AdventUtils;
import util.opcode.Eqrr;
import util.opcode.Execution;
import util.opcode.Opcode;

public class Day21Task1Main {

	private static List<Execution> executions = new ArrayList<>();
	private static int ip;

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

			while (register[ip] < executions.size()) {
				Execution execution = executions.get(Long.valueOf(register[ip]).intValue());
				register = execution.getOpcode().execute(execution.getA(), execution.getB(), execution.getC(),
						register);
				register[ip] += 1;
				if (execution.getOpcode().getClass() == Eqrr.class) {
					break;
				}
			}
			AdventUtils.publishResult(21, 1, register[1]);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
