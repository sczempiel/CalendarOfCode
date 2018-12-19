package day19;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import util.AdventUtils;
import util.opcode.Opcode;

public class Day19Task1Main {

	public static void main(String[] args) {

		try {
			long[] register = new long[6];
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

			while (register[ip] < executions.size()) {
				Execution execution = executions.get(Long.valueOf(register[ip]).intValue());
				register = execution.getOpcode().execute(execution.getA(), execution.getB(), execution.getC(),
						register);
				register[ip] += 1;
			}

			AdventUtils.publishResult(19, 1, Long.valueOf(register[0]).intValue());

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
