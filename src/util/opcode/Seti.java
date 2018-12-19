package util.opcode;

import java.util.Arrays;

public class Seti extends Opcode {

	@Override
	public long[] execute(int i1, int i2, int i3, long[] register) {
		long[] result = Arrays.copyOf(register, register.length);

		result[i3] = i1;

		return result;
	}

}