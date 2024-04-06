package pl.tripledes.view;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.apache.commons.io.FileUtils;
import pl.tripleDES.*;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class DESController {
    TripleDES tripleDES;

    @FXML
    private TextField key1;

    @FXML
    private TextField key2;

    @FXML
    private TextField key3;

    @FXML
    private TextArea writeText;

    @FXML
    private TextArea readText;

    @FXML
    private Label fileStatus;

    public DESController() {
        this.tripleDES = new TripleDES();
    }

    @FXML
    public void encrypt() {
        try {
            byte[] key1Bytes = Arrays.copyOf(key1.getText().getBytes(StandardCharsets.UTF_8), 8);
            byte[] key2Bytes = Arrays.copyOf(key2.getText().getBytes(StandardCharsets.UTF_8), 8);
            byte[] key3Bytes = Arrays.copyOf(key3.getText().getBytes(StandardCharsets.UTF_8), 8);

            tripleDES.setKeys(key1Bytes, key2Bytes, key3Bytes);

            byte[] text = writeText.getText().getBytes(StandardCharsets.UTF_8);
            byte[] encryptedText = tripleDES.encryptMessage(text);
            readText.setText(bytesToHex(encryptedText));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void decrypt() {
        try {
            byte[] key1Bytes = Arrays.copyOf(key1.getText().getBytes(StandardCharsets.UTF_8), 8);
            byte[] key2Bytes = Arrays.copyOf(key2.getText().getBytes(StandardCharsets.UTF_8), 8);
            byte[] key3Bytes = Arrays.copyOf(key3.getText().getBytes(StandardCharsets.UTF_8), 8);

            tripleDES.setKeys(key1Bytes, key2Bytes, key3Bytes);

            byte[] encryptedText = hexToBytes(readText.getText());
            byte[] decryptedText = tripleDES.decryptMessage(encryptedText);
            writeText.setText(new String(decryptedText, StandardCharsets.UTF_8));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String bytesToHex(byte[] bytes) {
        StringBuilder hexString = new StringBuilder();
        for (byte b : bytes) {
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1) hexString.append('0');
            hexString.append(hex);
        }
        return hexString.toString();
    }

    private byte[] hexToBytes(String hex) {
        int len = hex.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(hex.charAt(i), 16) << 4)
                    + Character.digit(hex.charAt(i + 1), 16));
        }
        return data;
    }

    @FXML
    public void encryptFile() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Proszę wybrać plik");
        File selectedFile = fileChooser.showOpenDialog(new Stage());

        try {
            byte[] key1Bytes = Arrays.copyOf(key1.getText().getBytes(StandardCharsets.UTF_8), 8);
            byte[] key2Bytes = Arrays.copyOf(key2.getText().getBytes(StandardCharsets.UTF_8), 8);
            byte[] key3Bytes = Arrays.copyOf(key3.getText().getBytes(StandardCharsets.UTF_8), 8);

            tripleDES.setKeys(key1Bytes, key2Bytes, key3Bytes);

            byte[] fileBytes = FileUtils.readFileToByteArray(selectedFile);
            byte[] encodedBytes = tripleDES.encryptMessage(fileBytes);

            File destination = fileChooser.showSaveDialog(new Stage());
            FileUtils.writeByteArrayToFile(destination, encodedBytes);

            fileStatus.setText("Zaszyfrowano plik.");

        } catch (IOException e) {
            e.printStackTrace();
            fileStatus.setText("Nie udało się zaszyfrować");
        }
    }

    @FXML
    public void decryptFile() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Proszę wybrać plik");
        File selectedFile = fileChooser.showOpenDialog(new Stage());

        try {
            byte[] key1Bytes = Arrays.copyOf(key1.getText().getBytes(StandardCharsets.UTF_8), 8);
            byte[] key2Bytes = Arrays.copyOf(key2.getText().getBytes(StandardCharsets.UTF_8), 8);
            byte[] key3Bytes = Arrays.copyOf(key3.getText().getBytes(StandardCharsets.UTF_8), 8);

            tripleDES.setKeys(key1Bytes, key2Bytes, key3Bytes);

            byte[] fileBytes = FileUtils.readFileToByteArray(selectedFile);
            byte[] decodedBytes = tripleDES.decryptMessage(fileBytes);

            File destination = fileChooser.showSaveDialog(new Stage());
            FileUtils.writeByteArrayToFile(destination, decodedBytes);

            fileStatus.setText("Odszyfrowano plik.");

        } catch (IOException e) {
            e.printStackTrace();
            fileStatus.setText("Nie udało się odszyfrować.");
        }
    }
}