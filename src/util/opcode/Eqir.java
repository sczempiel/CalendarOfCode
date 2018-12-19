package util.opcode;

import java.util.Arrays;

public class Eqir extends Opcode {

	@Override
	public long[] execute(int i1, int i2, int i3, long[] register) {
		long[] result = Arrays.copyOf(register, register.length);

		if (i1 == register[i2]) {
			result[i3] = 1;
		} else {
			result[i3] = 0;
		}
		return result;
	}

}