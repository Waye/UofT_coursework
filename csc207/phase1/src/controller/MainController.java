package controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.ImageFinder;
import model.Tags;

import java.io.File;

/**
 * De-serializes the data from file or creates the file if it doesn't exist
 * and stores the core information of the application in run time.
 * This is superclass of all controller.
 */
public class MainController {

    /**
     * Current working directory of the application.
     */
    static File directory;
    /**
     * true if the user wants to search through all sub-directories of specified folder.
     */
    static boolean ifAll;
    /**
     * The ImageFinder to initiate the Images objects.
     */
    public static ImageFinder finder;
    /**
     * The index of currently viewing image in Images list of ImageFinder.
     */
    public static int index;

    /**
     * De-serializes the data from file or creates the file if it doesn't exist.
     */
    MainController() {
        try {
            String serializedFileName = System.getProperty("user.dir") + "/config/serializedTagMap.ser";
            // Reads serializable objects from file.
            // Populates the tags record using stored data, if it exists.
            File file = new File(serializedFileName);
            if (file.exists()) {
                Tags.readTag();
            } else {
                file.createNewFile();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * All subclass use this methods to skip to next fxml.
     *
     * @param fxPath The path of fxml
     * @param title  the title of stage
     */
    void nextFXML(String fxPath, String title) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(fxPath));
            Parent root = fxmlLoader.load();
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setTitle(title);
            stage.setScene(scene);
            stage.show();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Sets the currently working directory.
     */
    public static void setDirectory(File directory) {
        MainController.directory = directory;
    }

}
