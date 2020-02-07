package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ResourceBundle;

/**
 * This is MoveImageController class the controller of moveimage.fxml can move image to another
 * directory.
 */
public class MoveImageController extends MainController implements Initializable {
  /** Stores the target file directory. */
  private File moveTo;
  /** The path of directory you wants to move. */
  @FXML private TextField textField;

  /** Sets the destination directory. */
  @FXML
  private void selectDir() {
    try {
      DirectoryChooser directoryChooser = new DirectoryChooser();
      moveTo = directoryChooser.showDialog(new Stage());
      textField.setText(moveTo.getAbsolutePath());
    } catch (Exception ex) {
      alertMessage("Not a Valid Directory!");
    }
  }

  /**
   * Moves the image file. If the directory does not change or the target directory is a
   * sub-directory of current directory when ifAll is true, the image list in finder does not
   * change. Otherwise, removes the image from current image list since the image is no longer under
   * consideration by the program.
   */
  @FXML
  private void handleMove(ActionEvent event) {
    try {
      File from = getFinder().images.get(getIndex()).getFile();

      Path source = from.toPath();
      Path target = Paths.get(moveTo.getAbsolutePath() + "/" + from.getName());
      // move file
      Files.move(source, target);
      getFinder()
          .images
          .get(getIndex())
          .setFile(new File(moveTo.getAbsolutePath() + "/" + from.getName()));
      // set stage
      Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
      stage.close();

      if (!(moveTo.toString().contains(getDirectory().toString()) && isIfAll()
          || (moveTo.toString().equals(getDirectory().toString())))) {
        getFinder().images.remove(getIndex());
      }
    } catch (IOException ex) {
      ex.printStackTrace();
    }
  }

  /** Unable users to change the textField by keyboard. */
  @Override
  public void initialize(URL location, ResourceBundle resources) {
    textField.setEditable(false);
  }
}
