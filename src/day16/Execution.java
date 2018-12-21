package day16;

import java.util.Arrays;

public class Execution {

	private int[] registersBefore = new int[4];
	private int[] registersAfter = new int[4];
	private int[] instructions = new int[4];

	public int[] getRegistersBefore() {
		return registersBefore;
	}

	public void setRegistersBefore(int[] registersBefore) {
		this.registersBefore = registersBefore;
	}

	public int[] getRegistersAfter() {
		return registersAfter;
	}

	public void setRegistersAfter(int[] registersAfter) {
		this.registersAfter = registersAfter;
	}

	public int[] getInstructions() {
		return instructions;
	}

	public void setInstructions(int[] instructions) {
		this.instructions = instructions;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Execution [");
		if (registersBefore != null) {
			builder.append("registersBefore=");
			builder.append(Arrays.toString(registersBefore));
			builder.append(", ");
		}
		if (registersAfter != null) {
			builder.append("registersAfter=");
			builder.append(Arrays.toString(registersAfter));
			builder.append(", ");
		}
		if (instructions != null) {
			builder.append("instructions=");
			builder.append(Arrays.toString(instructions));
		}
		builder.append("]");
		return builder.toString();
	}

}
