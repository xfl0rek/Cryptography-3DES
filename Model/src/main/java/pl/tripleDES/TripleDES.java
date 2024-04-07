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

        for (int i = 0; i <= maxIndex; i++) {
            if (i == maxIndex && isMultipleOf8) {
                break;
            }

            byte[] firstRound = DES1.encrypt(Arrays.copyOfRange(input, i * 8, (i + 1) * 8));
            byte[] secondRound = DES2.decrypt(firstRound);
            byte[] thirdRound = DES3.encrypt(secondRound);

            System.arraycopy(thirdRound, 0, output, i * 8, 8);
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

        for (int i = 0; i < maxIndex; i++) {
            byte[] firstRound = DES3.decrypt(Arrays.copyOfRange(input, i * 8, (i + 1) * 8));
            byte[] secondRound = DES2.encrypt(firstRound);
            byte[] thirdRound = DES1.decrypt(secondRound);

            if (i == maxIndex - 1 && !isMultipleOf8) {
                System.arraycopy(thirdRound, 0, output, i * 8, 8 - (padding == 0 ? 8 : padding));
            } else {
                System.arraycopy(thirdRound, 0, output, i * 8, 8);
            }
        }

        return output;
    }
}
