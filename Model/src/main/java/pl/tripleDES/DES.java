package pl.tripleDES;

public class DES {
    private byte[] key;
    final byte[] pBox = {16, 7, 20, 21, 29, 12, 28, 17, 1, 15, 23, 26, 5, 18, 31, 10,
                            2, 8, 24, 14, 32, 27, 3, 9, 19, 13, 30, 6, 22, 11, 4, 25
    };

    final byte[][] sBoxes = {
            //S1
            { 14,  4, 13, 1,  2, 15, 11,  8,  3, 10,  6, 12,  5,  9, 0,  7,
               0, 15,  7, 4, 14,  2, 13,  1, 10,  6, 12, 11,  9,  5, 3,  8,
               4,  1, 14, 8, 13,  6,  2, 11, 15, 12,  9,  7,  3, 10, 5,  0,
              15, 12,  8, 2,  4,  9,  1,  7,  5, 11,  3, 14, 10,  0, 6, 13},
            //S2
            { 15,  1,  8, 14,  6, 11,  3,  4,  9, 7,  2, 13, 12, 0, 5,  10,
               3, 13,  4,  7, 15,  2,  8, 14, 12, 0,  1, 10,  6, 9, 11,  5,
               0, 14,  7, 11, 10,  4, 13,  1,  5, 8, 12,  6,  9, 3,  2, 15,
              13,  8, 10,  1,  3, 15,  4,  2, 11, 6,  7, 12,  0, 5, 14,  9},
            //S3
            { 10,  0,  9, 14, 6,  3, 15,  5,  1, 13, 12,  7, 11,  4,  2,  8,
              13,  7,  0,  9, 3,  4,  6, 10,  2,  8,  5, 14, 12, 11, 15,  1,
              13,  6,  4,  9, 8, 15,  3,  0, 11,  1,  2, 12,  5, 10, 14,  7,
               1, 10, 13,  0, 6,  9,  8,  7,  4, 15, 14,  3, 11,  5,  2, 12},
            //S4
            { 7, 13, 14, 3,  0,  6,  9, 10,  1, 2, 8,  5, 11, 12,  4, 15,
             13,  8, 11, 5,  6, 15,  0,  3,  4, 7, 2, 12,  1, 10, 14,  9,
             10,  6,  9, 0, 12, 11,  7, 13, 15, 1, 3, 14,  5,  2,  8,  4,
              3,  15,  0, 6, 10,  1, 13,  8,  9, 4, 5, 11, 12,  7,  2, 14},
            //S5
            { 2, 12,  4,  1,  7, 10, 11,  6,  8,  5,  3, 15, 13, 0, 14,  9,
             14, 11,  2, 12,  4,  7, 13,  1,  5,  0, 15, 10,  3, 9,  8,  6,
              4,  2,  1, 11, 10, 13,  7,  8, 15,  9, 12,  5,  6, 3,  0, 14,
             11,  8, 12,  7,  1, 14,  2, 13,  6, 15,  0,  9, 10, 4,  5,  3},
            //S6
            { 12,  1, 10, 15, 9,  2,  6,  8,  0, 13,  3,  4, 14,  7,  5, 11,
              10, 15,  4,  2, 7, 12,  9,  5,  6,  1, 13, 14,  0, 11,  3,  8,
               9, 14, 15,  5, 2,  8, 12,  3,  7,  0,  4, 10,  1, 13, 11,  6,
               4,  3,  2, 12, 9,  5, 15, 10, 11, 14,  1,  7,  6,  0,  8, 13},
            //S7
            { 4, 11,  2, 14, 15, 0,  8, 13,  3, 12, 9,  7,  5, 10, 6,  1,
             13,  0, 11,  7,  4, 9,  1, 10, 14,  3, 5, 12,  2, 15, 8,  6,
              1,  4, 11, 13, 12, 3,  7, 14, 10, 15, 6,  8,  0,  5, 9,  2,
              6, 11, 13,  8,  1, 4, 10,  7,  9,  5, 0, 15, 14,  2, 3, 12},
            //S8
            { 13,  2,  8, 4,  6, 15, 11,  1, 10,  9,  3, 14,  5,  0, 12,  7,
               1, 15, 13, 8, 10,  3,  7,  4, 12,  5,  6, 11,  0, 14,  9,  2,
               7, 11,  4, 1,  9, 12, 14,  2,  0,  6, 10, 13, 15,  3,  5,  8,
               2,  1, 14, 7,  4, 10,  8, 13, 15, 12,  9,  0,  3,  5,  6, 11}
    };

    private final static byte[] shifts = {1, 1, 2, 2, 2, 2, 2, 2, 1, 2, 2, 2, 2, 2, 2, 1};

    private final static byte[] PC1 = {
            57, 49, 41, 33, 25, 17,  9,
            1, 58, 50, 42, 34, 26, 18,
            10,  2, 59, 51, 43, 35, 27,
            19, 11,  3, 60, 52, 44, 36,
            63, 55, 47, 39, 31, 23, 15,
            7, 62, 54, 46, 38, 30, 22,
            14,  6, 61, 53, 45, 37, 29,
            21, 13,  5, 28, 20, 12,  4
    };

    private final static byte[] PC2 = {
            14, 17, 11, 24,  1,  5,  3, 28,
            15,  6, 21, 10, 23, 19, 12,  4,
            26,  8, 16,  7, 27, 20, 13,  2,
            41, 52, 31, 37, 47, 55, 30, 40,
            51, 45, 33, 48, 44, 49, 39, 56,
            34, 53, 46, 42, 50, 36, 29, 32
    };

    final byte[] initialPermutation = {
            58, 50, 42, 34, 26, 18, 10, 2,
            60, 52, 44, 36, 28, 20, 12, 4,
            62, 54, 46, 38, 30, 22, 14, 6,
            64, 56, 48, 40, 32, 24, 16, 8,
            57, 49, 41, 33, 25, 17, 9, 1,
            59, 51, 43, 35, 27, 19, 11, 3,
            61, 53, 45, 37, 29, 21, 13, 5,
            63, 55, 47, 39, 31, 23, 15, 7
    };
    final byte[] finalPermutation = {
            40, 8, 48, 16, 56, 24, 64, 32,
            39, 7, 47, 15, 55, 23, 63, 31,
            38, 6, 46, 14, 54, 22, 62, 30,
            37, 5, 45, 13, 53, 21, 61, 29,
            36, 4, 44, 12, 52, 20, 60, 28,
            35, 3, 43, 11, 51, 19, 59, 27,
            34, 2, 42, 10, 50, 18, 58, 26,
            33, 1, 41,  9, 49, 17, 57, 25
    };

    final byte[] E = {
            32,  1,  2,  3,  4,  5,
            4,  5,  6,  7,  8,  9,
            8,  9, 10, 11, 12, 13,
            12, 13, 14, 15, 16, 17,
            16, 17, 18, 19, 20, 21,
            20, 21, 22, 23, 24, 25,
            24, 25, 26, 27, 28, 29,
            28, 29, 30, 31, 32,  1
    };

    public DES(byte[] key) {
        this.key = key;
    }

    //pobieramy wartość bitu z tablicy input z pozycji pos
    private int getBit(byte[] input, int pos) {
        int byteIndex = pos / 8;
        int bitIndex = pos % 8;
        byte mask = (byte) (1 << (7 - bitIndex));

        return (input[byteIndex] & mask) == 0 ? 0 : 1;
    }

    //ustawiamy wartość bitu w pozycji pos w tablicy input
    private void setBit(byte[] input, int pos, int value) {
        int byteIndex = pos / 8;
        int bitIndex = pos % 8;
        byte mask = (byte) (1 << (7 - bitIndex));

        if (value == 0) {
            input[byteIndex] &= (byte) ~mask;
        } else {
            input[byteIndex] |= mask;
        }
    }

    //wykonuje permutację na tablicy input zgodnie z tablicą permutacji
    private byte[] permutation(byte[] input, byte[] permutation, int outputLength) {
        byte[] output = new byte[outputLength];

        for (int i = 0; i < permutation.length; i++) {
            setBit(output, i, getBit(input, permutation[i] - 1));
        }

        return output;
    }

    //przesuwa bity w tablicy input cyklicznie o jedną pozycję w lewo
    private byte[] shiftBits(byte[] input) {
        byte[] shifted = new byte[input.length];
        if (input.length - 1 >= 0) System.arraycopy(input, 1, shifted, 0, input.length - 1);
        shifted[input.length - 1] = input[0];
        return shifted;
    }

    //łączy dwie tablice
    private byte[] mergeArrays(byte[] left, byte[] right) {
        byte[] merged = new byte[left.length + right.length];
        System.arraycopy(left, 0, merged, 0, left.length);
        System.arraycopy(right, 0, merged, left.length, right.length);
        return merged;
    }

    //kopiuje określoną liczbę bitów z tablicy input od określonej pozycji
    private byte[] copyBits(byte[] input, int from, int count, int outputLength) {
        byte[] output = new byte[outputLength];

        for (int i = 0; i < count; i++) {
            int inputBitIndex = from + i;
            int outputByteIndex = i / 8;
            int outputBitIndex = i % 8;

            int bitValue = getBit(input, inputBitIndex);
            output[outputByteIndex] |= (byte) (bitValue << (7 - outputBitIndex));
        }

        return output;
    }

    //generuje podklucze dla każdej rundy DES-a
    public byte[][] generateSubKeys() {
        byte[][] subKeys = new byte[16][48];
        byte[] key56 = permutation(key, PC1, 7);
        byte[] leftHalf = copyBits(key56, 0, 28, 4);
        byte[] rightHalf = copyBits(key56, 28, 28, 4);

        for (int i = 0; i < 16; i++) {
            for (int j = 0; j < shifts[i]; j++) {
                leftHalf = shiftBits(leftHalf);
                rightHalf = shiftBits(rightHalf);
            }

            byte[] mergedKey = mergeArrays(leftHalf, rightHalf);

            subKeys[i] = permutation(mergedKey, PC2, 6);
        }

       return subKeys;
    }

    //szyfrowanie wiadomości za pomocą algorytmu DES
    public byte[] encrypt(byte[] message) {
        byte[][] subKeys = generateSubKeys();
        byte[] IPResult = permutation(message, initialPermutation, 8);
        byte[] left = copyBits(IPResult, 0, 32, 4);
        byte[] right = copyBits(IPResult, 32, 32, 4);

        for (int i = 0; i < 16; i++) {
            //początek funkcji f
            byte[] EPResult = permutation(right, E, 6);
            byte[] xorResult = xor(EPResult, subKeys[i], 6);
            byte[] s = sBoxOperation(xorResult);
            byte[] PPResult = permutation(s, pBox, 4);
            //koniec funkcji f
            byte[] result = xor(PPResult, left, 4);
            left = right;
            right = result;
        }

        byte[] merged = mergeArrays(right, left);

        return permutation(merged, finalPermutation, 8);
    }

    //wykonuje operacje xor na dwóch tablicach bajtów
    private byte[] xor(byte[] bytes1, byte[] bytes2, int byteCount) {
        byte[] output = new byte[byteCount];

        for (int i = 0; i < byteCount; i++) {
            output[i] = (byte) (bytes1[i] ^ bytes2[i]);
        }

        return output;
    }

    //zwraca 6 bitów z tablicy input z określonym indexem bitów, które mają zostać zwrócone
    private byte[] return6Bits(byte[] input, int number){
        byte[] output = {0};

        for(int i = 0; i < input.length; i++){
            setBit(output, i + 2, getBit(input, i + (number * 6)));
        }

        return output;
    }

    //dokonuje operacji na S-boxach
    private byte[] sBoxOperation(byte[] input) {
        byte[] output = new byte[4];
        byte[] column = {0};
        byte[] row = {0};

        for(int i = 0; i < 8; i++) {
            byte[] sixBits = return6Bits(input, i);
            byte[] fourBits = new byte[1];

            int bit1 = getBit(sixBits, 2);
            int bit6 = getBit(sixBits, 7);
            int bit2 = getBit(sixBits, 3);
            int bit3 = getBit(sixBits, 4);
            int bit4 = getBit(sixBits, 5);
            int bit5 = getBit(sixBits, 6);

            setBit(row, 6, bit1);
            setBit(row, 7, bit6);
            setBit(column, 4, bit2);
            setBit(column, 5, bit3);
            setBit(column, 6, bit4);
            setBit(column, 7, bit5);

            fourBits[0] = sBoxes[i][16 * row[0] + column[0]];

            int outputBit1 = getBit(fourBits, 4);
            int outputBit2 = getBit(fourBits, 5);
            int outputBit3 = getBit(fourBits, 6);
            int outputBit4 = getBit(fourBits, 7);

            setBit(output, (i * 4), outputBit1);
            setBit(output, 1 + (i * 4), outputBit2);
            setBit(output, 2 + (i * 4), outputBit3);
            setBit(output, 3 + (i * 4), outputBit4);
        }

        return output;
    }

    //odszyfrowuje wiadomość za pomocą algorytmu DES
    public byte[] decrypt(byte[] message) {
        byte[][] subKeys = generateSubKeys();
        byte[] FPResult = permutation(message, initialPermutation, 8);
        byte[] left = copyBits(FPResult, 0, 32, 4);
        byte[] right = copyBits(FPResult, 32, 32, 4);

        for (int i = 15; i >= 0; i--) {
            byte[] EPResult = permutation(right, E, 6);
            byte[] xorResult = xor(EPResult, subKeys[i], 6);
            byte[] s = sBoxOperation(xorResult);
            byte[] PPResult = permutation(s, pBox, 4);
            byte[] result = xor(PPResult, left, 4);
            left = right;
            right = result;
        }
        byte[] merged = mergeArrays(right, left);

        return permutation(merged, finalPermutation, 8);
    }
}
