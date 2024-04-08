package pl.tripleDES;

import java.util.Arrays;

public class TripleDES {
    private DES DES1;
    private DES DES2;
    private DES DES3;

    public TripleDES() {}

    public void setKeys(byte[] firstKey, byte[] secondKey, byte[] thirdKey) {
        if (firstKey.length == 8 && secondKey.length == 8 && thirdKey.length == 8) {
            DES1 = new DES(firstKey);
            DES2 = new DES(secondKey);
            DES3 = new DES(thirdKey);
        }
    }

    public byte[] encryptMessage(byte[] input) {
        byte[] paddedInput = addPadding(input);
        int maxIndex = paddedInput.length / 8;
        byte[] output = new byte[paddedInput.length];

        for (int i = 0; i < maxIndex; i++) {
            byte[] firstRound = DES1.encrypt(Arrays.copyOfRange(paddedInput, i * 8, (i + 1) * 8));
            byte[] secondRound = DES2.decrypt(firstRound);
            byte[] thirdRound = DES3.encrypt(secondRound);

            System.arraycopy(thirdRound, 0, output, i * 8, 8);
        }

        return output;
    }

    public byte[] decryptMessage(byte[] input) {
        int maxIndex = input.length / 8;
        byte[] output = new byte[input.length];

        for (int i = 0; i < maxIndex; i++) {
            byte[] firstRound = DES3.decrypt(Arrays.copyOfRange(input, i * 8, (i + 1) * 8));
            byte[] secondRound = DES2.encrypt(firstRound);
            byte[] thirdRound = DES1.decrypt(secondRound);

            System.arraycopy(thirdRound, 0, output, i * 8, 8);
        }

        return removePadding(output);
    }

    private byte[] addPadding(byte[] input) {
        int lastBlockSize = input.length % 8;
        if (lastBlockSize != 0) {
            int paddingSize = 8 - lastBlockSize;
            byte[] paddedInput = Arrays.copyOf(input, input.length + paddingSize);
            Arrays.fill(paddedInput, input.length, paddedInput.length, (byte) paddingSize);
            return paddedInput;
        }
        return input;
    }

    private byte[] removePadding(byte[] input) {
        int paddingSize = input[input.length - 1];
        if (paddingSize > 0 && paddingSize <= 8) {
            return Arrays.copyOfRange(input, 0, input.length - paddingSize);
        }
        return input;
    }
}
