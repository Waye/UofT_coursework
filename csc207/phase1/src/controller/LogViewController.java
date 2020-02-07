package controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Scanner;

/**
 * The LogViewController is controller for logview.fxml
 * can see all name changes in this gui.
 */
public class LogViewController extends MainController implements Initializable {
    /**
     * The log text for fxml.
     */
    @FXML
    private TextArea logText;

    /**
     * Initializes the scene by filling the TextArea with logging history from specified file.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            logText.setEditable(false);
            Scanner sc = new Scanner(new File(System.getProperty("user.dir") + "/config/NamingLogFile.log"));
            while (sc.hasNext()) {
                logText.appendText(sc.nextLine() + "\n");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
