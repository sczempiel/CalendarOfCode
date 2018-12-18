package day16.opcode;

import java.util.Arrays;

public class Gtrr extends Opcode {

	@Override
	public int[] execute(int i1, int i2, int i3, int[] register) {
		int[] result = Arrays.copyOf(register, register.length);

		if (register[i1] > register[i2]) {
			result[i3] = 1;
		} else {
			result[i3] = 0;
		}
		return result;
	}

}