package controller;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import model.Logging;
import model.Tags;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Scanner;

/**
 * The NameHistoryController is a controller of namehistory.fxml can view history of name of current
 * image. can changes image name.
 */
public class NameHistoryController extends MainController implements Initializable {
  /** A reference to the current Images object's name history. */
  private List<String> nameHistory = getFinder().images.get(getIndex()).getNameHistory();

  /** The log text for fxml. */
  @FXML private TextArea logText;

  /** view list of tags name. */
  @FXML private ListView<String> names;
  /** the display label. */
  @FXML private Label display;

  /** Initializes the ListView with the current Images object's name history. */
  @Override
  public void initialize(URL location, ResourceBundle resources) {
    ObservableList<String> items = names.getItems();
    items.addAll(nameHistory);
    display.setText("Please select a name: ");
    try {
      logText.setEditable(false);
      Scanner sc =
          new Scanner(new File(System.getProperty("user.dir") + "/config/NamingLogFile.log"));
      while (sc.hasNext()) {
        logText.appendText(sc.nextLine() + "\n");
      }
    } catch (Exception ex) {
      ex.printStackTrace();
    }
  }

  /** Changes the current image file name to selected old name. */
  @FXML
  private void changeName() throws Exception {
    String select = names.getSelectionModel().getSelectedItem();

    if (getFinder().images.get(getIndex()).getName().equals(select)) {
      display.setText("name exists");
    } else {
      adjustTags(select);
      Scanner sc =
          new Scanner(new File(System.getProperty("user.dir") + "/config/NamingLogFile.log"));
      logText.clear();
      while (sc.hasNext()) {
        logText.appendText(sc.nextLine() + "\n");
      }
    }
  }

  /**
   * Adjust current tags and available tags after rename.
   *
   * @param newName the selected name
   */
  private void adjustTags(String newName) throws IOException {
    String oldName = getFinder().images.get(getIndex()).getName();

    getFinder().images.get(getIndex()).rename(newName);
    Tags.availableTags.removeAll(getFinder().images.get(getIndex()).getCurrentTags());
    getFinder()
        .images
        .get(getIndex())
        .setCurrentTags(getFinder().images.get(getIndex()).tagsInName());
    Tags.availableTags.addAll(getFinder().images.get(getIndex()).getCurrentTags());
    display.setText(newName);
    ImageInfoController.getImageTags().clear();
    ImageInfoController.getImageTags().addAll(getFinder().images.get(getIndex()).getCurrentTags());
    ImageInfoController.getAvailableTags().clear();
    ImageInfoController.getAvailableTags().addAll(Tags.availableTags);

    Logging.recordLogInfo(oldName, newName);
  }
}
