package pl.tripleDES;

import java.io.*;

public class FileOperations {
    public byte[] readFromFile(String fileName) {
        try (InputStream inputStream = new FileInputStream(fileName)) {

            long fileSize = new File(fileName).length();
            byte[] output = new byte[(int) fileSize];

            inputStream.read(output);

            return output;

        } catch (IOException ex) {
            ex.printStackTrace();
        }

        return null;
    }

    public void writeToFile(String file, byte[] input) {
        try (OutputStream outputStream = new FileOutputStream(file)) {
            outputStream.write(input);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
