package pl.tripleDES;

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
}
