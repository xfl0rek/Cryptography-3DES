package pl.tripleDES;

import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        // Tworzymy przykładowy klucz początkowy
        byte[] initialKey = new byte[]{0x01, 0x23, 0x45, 0x67, (byte) 0x89, (byte) 0xAB, (byte) 0xCD, (byte) 0xEF};

        DES des = new DES(initialKey);

        // Wywołujemy metodę generateSubkeys, aby wygenerować podklucze DES
        byte[][] subKeys = des.generateSubKeys();

        // Wyświetlamy wygenerowane podklucze
        for (int i = 0; i < 16; i++) {
            System.out.println("Subkey " + (i + 1) + ": " + Arrays.toString(subKeys[i]));
        }
    }
}