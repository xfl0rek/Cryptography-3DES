package pl.tripledes.view;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import pl.tripleDES.*;

import java.io.UnsupportedEncodingException;

public class DESController {
    TripleDES tripleDES;
    String charset = "UTF-8";
    byte[] text;
    byte[] encryptedText;

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
    private Button encryptButton;

    @FXML
    private Button decryptButton;

    @FXML
    private Button clearButton;

    @FXML
    private Button encryptFileButton;

    @FXML
    private Button decryptFileButton;

    public DESController() {
        this.tripleDES = new TripleDES();
    }

    public void encrypt() {
        try {
            String keyStr1 = key1.getText();
            String keyStr2 = key2.getText();
            String keyStr3 = key3.getText();

            byte[] key1Bytes = keyStr1.getBytes(charset);
            byte[] key2Bytes = keyStr2.getBytes(charset);
            byte[] key3Bytes = keyStr3.getBytes(charset);

            this.tripleDES.setKeys(key1Bytes, key2Bytes, key3Bytes);

            text = writeText.getText().getBytes(charset);
            encryptedText = tripleDES.encryptMessage(text);
            readText.setText(new String(encryptedText, charset));
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }
}