package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import model.Logging;
import model.Tags;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.List;

/**
 * The NameHistoryController is a controller of namehistory.fxml
 * can view history of name of current image.
 * can changes image name.
 */
public class NameHistoryController extends MainController implements Initializable {
    /**
     * A reference to the current Images object's name history.
     */
    private List<String> nameHistory = finder.images.get(index).getNameHistory();
    /**
     * view list of tags name.
     */
    @FXML
    private ListView<String> names;
    /**
     * the display label.
     */
    @FXML
    private Label display;

    /**
     * Initializes the ListView with the current Images object's name history.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ObservableList<String> items = names.getItems();
        items.addAll(nameHistory);
        display.setText("Please select a name: ");
    }

    /**
     * Changes the current image file name to selected old name.
     */
    @FXML
    private void changeName() throws Exception {
        String select = names.getSelectionModel().getSelectedItem();

        if (finder.images.get(index).getName().equals(select)) {
            display.setText("name exists");
        } else {
            adjustTags(select);
        }
    }

    /**
     * Adjust current tags and available tags after rename.
     *
     * @param newName the selected name
     */
    private void adjustTags(String newName) throws IOException {
        String oldName = finder.images.get(index).getName();

        finder.images.get(index).rename(newName);
        for (String oldTag : finder.images.get(index).getCurrentTags()) {
            Tags.availableTags.add(oldTag);
            Tags.readTag();
        }
        finder.images.get(index).setCurrentTags(finder.images.get(index).tagsInName());
        display.setText(newName);
        ImageInfoController.getItems().clear();
        for (String newTag : finder.images.get(index).getCurrentTags()) {
            ImageInfoController.getItems().add(newTag);
        }
        Logging.recordLogInfo(oldName, newName);
    }
}
