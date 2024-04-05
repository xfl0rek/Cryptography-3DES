package pl.tripleDES;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        TripleDES tripleDES = new TripleDES();

        byte[] firstKey = {0x00, 0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07};
        byte[] secondKey = {0x08, 0x09, 0x0A, 0x0B, 0x0C, 0x0D, 0x0E, 0x0F};
        byte[] thirdKey = {0x10, 0x11, 0x12, 0x13, 0x14, 0x15, 0x16, 0x17};
        tripleDES.setKeys(firstKey, secondKey, thirdKey);

        String originalMessage = "Hello, world!";
        byte[] message = originalMessage.getBytes();

        System.out.println("Original message: " + originalMessage);

        System.out.print("Original message (binary): ");
        for (byte b : message) {
            System.out.print(String.format("%8s", Integer.toBinaryString(b & 0xFF)).replace(' ', '0'));
        }
        System.out.println();

        byte[] encryptedMessage = tripleDES.encryptMessage(message);

        System.out.println("Encrypted message: " + Arrays.toString(encryptedMessage));

        System.out.print("Encrypted message (binary): ");
        for (byte b : encryptedMessage) {
            System.out.print(String.format("%8s", Integer.toBinaryString(b & 0xFF)).replace(' ', '0'));
        }
        System.out.println();

        System.out.print("Encrypted message (hex): ");
        for (byte b : encryptedMessage) {
            System.out.print(String.format("0x%02X", b & 0xFF));
        }
        System.out.println();

        byte[] decryptedMessage = tripleDES.decryptMessage(encryptedMessage);

        String decryptedString = new String(decryptedMessage);

        System.out.println("Decrypted message: " + decryptedString);
    }
}