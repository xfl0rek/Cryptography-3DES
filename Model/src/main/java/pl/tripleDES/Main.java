package pl.tripleDES;

import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        byte[] initialKey = new byte[]{0x12, 0x34, 0x56, 0x78, (byte)0xAB, (byte)0xCD, (byte)0xEF, 0x09};

        DES des = new DES(initialKey);

        byte[][] subKeys = des.generateSubKeys();

        for (int i = 0; i < 16; i++) {
            System.out.println("Subkey " + (i + 1) + ": " + Arrays.toString(subKeys[i]));
        }
    }
}