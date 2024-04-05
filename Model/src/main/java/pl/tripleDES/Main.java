package pl.tripleDES;

import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        byte[] initialKey = new byte[]{0x12, 0x34, 0x56, 0x78, (byte)0xAB, (byte)0xCD, (byte)0xEF, 0x09};

        System.out.println("Klucz binarnie:");
        for (byte b : initialKey) {
            System.out.print(String.format("%8s", Integer.toBinaryString(b & 0xFF)).replace(' ', '0') + " ");
        }
        System.out.println();
        System.out.println();

        DES des = new DES(initialKey);

        byte[][] subKeys = des.generateSubKeys();

        System.out.println();
        System.out.println("Podklucze:");
        for (int i = 0; i < 16; i++) {
            System.out.println("Subkey " + (i + 1) + ": " + Arrays.toString(subKeys[i]));
        }

        System.out.println();
        System.out.println("Podklucze binarnie:");
        for (int i = 0; i < 16; i++) {
            System.out.print("Subkey " + (i + 1) + ": ");
            for (byte b : subKeys[i]) {
                System.out.print(String.format("%8s", Integer.toBinaryString(b & 0xFF)).replace(' ', '0') + " ");
            }
            System.out.println();
        }
    }
}