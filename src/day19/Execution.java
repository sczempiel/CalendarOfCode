package day19;

import util.opcode.Opcode;

public class Execution {
	private Opcode opcode;
	private int a;
	private int b;
	private int c;

	public Opcode getOpcode() {
		return opcode;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		if (opcode != null) {
			builder.append(opcode.getClass().getSimpleName());
			builder.append(" ");
		}
		builder.append(" ");
		builder.append(a);
		builder.append(" ");
		builder.append(b);
		builder.append(" ");
		builder.append(c);
		builder.append("");
		return builder.toString();
	}

	public void setOpcode(Opcode opcode) {
		this.opcode = opcode;
	}

	public int getA() {
		return a;
	}

	public void setA(int a) {
		this.a = a;
	}

	public int getB() {
		return b;
	}

	public void setB(int b) {
		this.b = b;
	}

	public int getC() {
		return c;
	}

	public void setC(int c) {
		this.c = c;
	}
}
