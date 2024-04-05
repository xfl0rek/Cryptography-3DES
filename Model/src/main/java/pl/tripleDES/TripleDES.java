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
        byte[] correctSizeBlocks;

        if (input.length % 8 == 0) {
            correctSizeBlocks = Arrays.copyOf(input, input.length);
        } else {
            correctSizeBlocks = Arrays.copyOf(input, input.length + 8 - input.length % 8);
        }

        byte[] firstRound = DES1.encrypt(correctSizeBlocks);
        byte[] secondRound = DES2.decrypt(firstRound);
        byte[] thirdRound = DES3.encrypt(secondRound);

        return thirdRound;
    }

    public byte[] decryptMessage(byte[] input) {
        byte[] correctSizeBlocks;

        if (input.length % 8 == 0) {
            correctSizeBlocks = Arrays.copyOf(input, input.length);
        } else {
            correctSizeBlocks = Arrays.copyOf(input, input.length + 8 - input.length % 8);
        }

        byte[] firstRound = DES3.decrypt(correctSizeBlocks);
        byte[] secondRound = DES2.encrypt(firstRound);
        byte[] thirdRound = DES1.decrypt(secondRound);

        return thirdRound;
    }
}
