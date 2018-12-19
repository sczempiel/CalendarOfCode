package util.opcode;

import java.util.Arrays;

public class Setr extends Opcode {

	@Override
	public int[] execute(int i1, int i2, int i3, int[] register) {
		int[] result = Arrays.copyOf(register, register.length);

		result[i3] = register[i1];

		return result;
	}

}