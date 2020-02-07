package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextField;
import model.Serializer;
import model.Tags;

import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

/** the ImageInfoController is controller of imageinfoview.fxml. */
public class ImageInfoController extends MainController implements Initializable {

  /** Lists for the two list views. */
  private static ObservableList<String> imageTags = FXCollections.observableArrayList();

  private static ObservableList<String> availableTags = FXCollections.observableArrayList();
  /** current directory */
  @FXML private Label currentDir;
  /** the tag you wants to add or remove. */
  @FXML private TextField tag;
  /** list of current tag on this image. */
  @FXML private ListView currentTags;
  /** list of tags you can select. */
  @FXML private ListView selectsTags;
  /** select tag. */
  @FXML private TextField tagsSelect;
  /** The references to the data of current tags and available tags. */
  private List<String> imageTagsData = getFinder().images.get(getIndex()).getCurrentTags();

  private List<String> availableTagsData = Tags.availableTags;

  /**
   * Getter for the imageTags.
   *
   * @return this imageTags
   */
  static ObservableList<String> getImageTags() {
    return imageTags;
  }

  /**
   * Getter for availableTags.
   *
   * @return this availableTags
   */
  static ObservableList<String> getAvailableTags() {
    return availableTags;
  }

  /** Initializes the two ListView using data from imageTagsData and availableTagsData. */
  @Override
  @SuppressWarnings("unchecked")
  public void initialize(URL url, ResourceBundle rb) {
    currentDir.setText(getFinder().images.get(getIndex()).getDir());
    // add item to ListView CurrentTags
    imageTags = currentTags.getItems();
    // add item to ListView selects
    availableTags = selectsTags.getItems();
    refreshList();

    selectsTags.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
  }

  /** Loads NameHistoryView.fxml when click the button. */
  @FXML
  private void handleNameHistory() {
    try {
      nextFXML("../view/NameHistoryView.fxml", "Name History");
    } catch (Exception e) {
      alertMessage("cannot load NameHistory window");
    }
  }

  /** Adds the tag to imageTags, availableTags and updates corresponding Images and Tags objects. */
  @FXML
  private void handleAdd() {
    try {
      String newTag = tag.getText();
      // add to image-view-list and image-store-tags-list
      getFinder().images.get(getIndex()).addTag(newTag);
      refreshList();
      tag.clear();
      Serializer.saveTo("tags");
      Serializer.saveTagsimage();
    } catch (Exception ex) {
      ex.printStackTrace();
    }
  }

  /** Deletes the tag from imageTags, availableTags and corresponding Images and Tags objects. */
  @FXML
  private void handleDelete() {
    try {
      String removeTag = tag.getText();
      getFinder().images.get(getIndex()).deleteTag(removeTag);
      tag.clear();
      refreshList();
      Serializer.saveTo("tags");
      Serializer.saveTagsimage();
    } catch (Exception ex) {
      ex.printStackTrace();
    }
  }

  /**
   * Selects the items specified by user from mouse click events. Also supports multiple selection.
   */
  @FXML
  @SuppressWarnings("unchecked")
  private void handleSelect() throws IOException {
    ObservableList<String> selected = selectsTags.getSelectionModel().getSelectedItems();
    String select = String.join(" ", selected);
    for (String tag : selected) {
      getFinder().images.get(getIndex()).addTag(tag);
    }
    tagsSelect.setText(select);
    refreshList();
  }

  /** Update the two ListView using imageTagsData and availableTagsData. */
  @FXML
  private void refreshList() {
    imageTags.clear();
    imageTags.addAll(imageTagsData);

    availableTags.clear();

    for (String tag : availableTagsData) {
      if (!availableTags.contains(tag)) {
        availableTags.add(tag);
      }
    }
  }

  /** Opens (directly in their OS's file viewer) the directory containing the current image file. */
  @FXML
  private void handleCurrentFolder() {
    try {
      Desktop.getDesktop().open(getFinder().images.get(getIndex()).getFile().getParentFile());
    } catch (Exception ex) {
      ex.printStackTrace();
    }
  }
}
