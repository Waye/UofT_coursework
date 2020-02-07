package controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import model.Initializer;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * FileChooserController class is controller of FileChooser.fxml can choose file from any directory.
 * Click checkbox if the user wants to search images under subdirectories.
 */
public class FileChooserController extends MainController implements Initializable {
  /** the root pane. */
  @FXML private AnchorPane rootPane;

  /**
   * the initialized methods.
   *
   * @param url the url
   * @param rb the resource bundle.
   */
  @Override
  public void initialize(URL url, ResourceBundle rb) {}

  /**
   * Evokes the system's directory chooser and wait for user to choose a directory and if the
   * directory is chosen successfully, ImageGallery.fxml will be loaded
   *
   * @throws Exception when the user failed to choose a directory
   */
  @FXML
  private void handleFileChooserButton() throws Exception {
    try {
      DirectoryChooser directoryChooser = new DirectoryChooser();
      File selectedDirectory = directoryChooser.showDialog(new Stage());
      setDirectory(selectedDirectory);
      reportStatus(getDirectory());
      System.out.println(selectedDirectory.getAbsolutePath());

      Initializer initializer = new Initializer();
      initializer.createImageMappingTags();
    } catch (Exception ex) {
      System.out.println("Not a Valid Directory!");
    }

    if (getDirectory() != null) {
      Parent pane = FXMLLoader.load(getClass().getResource("../view/ImageGallery.fxml"));
      rootPane.getChildren().setAll(pane);
    }
  }

  /**
   * Enables the program to search through all sub-directories.
   *
   * @throws Exception If any of the usual Input/Output related exceptions occur.
   */
  @FXML
  private void handleCheckBox() throws Exception {
    setIfAll(true);
    reportStatus(isIfAll());
  }
}
