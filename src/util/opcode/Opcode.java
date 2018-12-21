package util.opcode;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class Opcode {

	public final static List<? extends Opcode> OPCODES;

	static {
		List<Opcode> opcodes = new ArrayList<>();
		opcodes.add(new Addr());
		opcodes.add(new Addi());
		opcodes.add(new Mulr());
		opcodes.add(new Muli());
		opcodes.add(new Banr());
		opcodes.add(new Bani());
		opcodes.add(new Borr());
		opcodes.add(new Bori());
		opcodes.add(new Setr());
		opcodes.add(new Seti());
		opcodes.add(new Gtir());
		opcodes.add(new Gtri());
		opcodes.add(new Gtrr());
		opcodes.add(new Eqir());
		opcodes.add(new Eqri());
		opcodes.add(new Eqrr());

		OPCODES = Collections.unmodifiableList(opcodes);
	}

	public static Opcode getOpcodeByName(String name) {
		switch (name) {
		case "addr":
			return OPCODES.get(0);
		case "addi":
			return OPCODES.get(1);
		case "mulr":
			return OPCODES.get(2);
		case "muli":
			return OPCODES.get(3);
		case "banr":
			return OPCODES.get(4);
		case "bani":
			return OPCODES.get(5);
		case "borr":
			return OPCODES.get(6);
		case "bori":
			return OPCODES.get(7);
		case "setr":
			return OPCODES.get(8);
		case "seti":
			return OPCODES.get(9);
		case "gtir":
			return OPCODES.get(10);
		case "gtri":
			return OPCODES.get(11);
		case "gtrr":
			return OPCODES.get(12);
		case "eqir":
			return OPCODES.get(13);
		case "eqri":
			return OPCODES.get(14);
		case "eqrr":
			return OPCODES.get(15);
		default:
			throw new IllegalArgumentException("No valid opcode name");
		}
	}

	public abstract int[] execute(int i1, int i2, int i3, int[] register);

}
