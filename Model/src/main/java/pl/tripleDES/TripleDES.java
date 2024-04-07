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
        int maxIndex = input.length / 8;
        boolean isMultipleOf8 = input.length % 8 == 0;
        byte[] output;

        if (isMultipleOf8) {
            output = new byte[input.length];
        } else {
            output = new byte[maxIndex * 8 + 8];
            output[output.length - 1] = (byte) (8 - (input.length % 8));
        }

        byte[] block;

        for (int i = 0; i <= maxIndex; i++) {
            if (i == maxIndex && isMultipleOf8) {
                break;
            }

            block = DES1.encrypt(Arrays.copyOfRange(input, i * 8, (i + 1) * 8));
            block = DES2.decrypt(block);
            block = DES3.encrypt(block);

            System.arraycopy(block, 0, output, i * 8, 8);
        }

        return output;
    }

    public byte[] decryptMessage(byte[] input) {
        int maxIndex = input.length / 8;
        boolean isMultipleOf8 = input.length % 8 == 0;
        byte[] output;
        int padding = 0;

        if (isMultipleOf8) {
            output = new byte[input.length];
        } else {
            padding = input[input.length - 1];
            output = new byte[input.length - (padding == 0 ? 8 : padding)];
        }

        byte[] block;

        for (int i = 0; i < maxIndex; i++) {
            block = DES3.decrypt(Arrays.copyOfRange(input, i * 8, (i + 1) * 8));
            block = DES2.encrypt(block);
            block = DES1.decrypt(block);

            if (i == maxIndex - 1 && !isMultipleOf8) {
                System.arraycopy(block, 0, output, i * 8, 8 - (padding == 0 ? 8 : padding));
            } else {
                System.arraycopy(block, 0, output, i * 8, 8);
            }
        }

        return output;
    }
}
