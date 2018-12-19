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

	public abstract int[] execute(int i1, int i2, int i3, int[] register);

}
