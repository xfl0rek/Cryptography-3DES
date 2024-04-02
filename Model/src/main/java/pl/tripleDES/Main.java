package pl.tripleDES;

public class Main {
    public static void main(String[] args) {
        byte[] key = {0, 0, 0, 0, 0, 0, 0, 0};
       DES des = new DES(key);

        byte[][] subKeys = des.generateSubKeys();

        for (int i = 0; i < 16; i++) {
            System.out.println("Subkey " + (i+1) + ": ");
            for (int j = 0; j < 48; j++) {
                System.out.print(subKeys[i][j] + " ");
            }
            System.out.println();
        }
    }
}