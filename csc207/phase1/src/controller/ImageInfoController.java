package controller;

import javafx.scene.control.SelectionMode;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import model.Tags;

/**
 * the ImageInfoController is controller of imageinfoview.fxml.
 */
public class ImageInfoController extends MainController implements Initializable {
    /**
     * the tag you wants to add or remove.
     */
    @FXML
    private TextField tag;
    /**
     * list of current tag on this image.
     */
    @FXML
    private ListView currentTags;
    /**
     * list of tags you can select.
     */
    @FXML
    private ListView selectsTags;
    /**
     * select tag.
     */
    @FXML
    private TextField tagsSelect;
    /**
     * Lists for the two list views.
     */
    private static ObservableList<String> imageTags = FXCollections.observableArrayList();
    private static ObservableList<String> availableTags = FXCollections.observableArrayList();

    /**
     * The references to the data of current tags and available tags.
     */
    private List<String> imageTagsData = finder.images.get(index).getCurrentTags();
    private List<String> availableTagsData = Tags.availableTags;

    /**
     * Getter for the imageTags.
     */
    static ObservableList<String> getItems() {
        return imageTags;
    }

    /**
     * Initializes the two ListView using data from imageTagsData and availableTagsData.
     */
    @Override
    @SuppressWarnings("unchecked")
    public void initialize(URL url, ResourceBundle rb) {
        //add item to ListView CurrentTags
        imageTags = currentTags.getItems();
        //add item to ListView selects
        availableTags = selectsTags.getItems();
        refreshList();

        selectsTags.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    }

    /**
     * Loads NameHistoryView.fxml when click the button.
     */
    @FXML
    private void handleNameHistory() {
        try {
            nextFXML("../view/NameHistoryView.fxml", "Name History");
        } catch (Exception e) {
            System.out.println("cannot load NameHistory window");
        }
    }

    /**
     * Adds the tag to imageTags, availableTags and updates corresponding Images and Tags objects.
     */
    @FXML
    private void handleAdd() {
        try {
            String newTag = tag.getText();
            //add to image-view-list and image-store-tags-list
            finder.images.get(index).addTag(newTag);
            refreshList();
            tag.clear();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Deletes the tag from imageTags, availableTags and corresponding Images and Tags objects.
     */
    @FXML
    private void handleDelete() {
        try {
            String removeTag = tag.getText();
            finder.images.get(index).deleteTag(removeTag);
            tag.clear();
            refreshList();
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
            finder.images.get(index).addTag(tag);
        }
        tagsSelect.setText(select);
        refreshList();
    }

    /**
     * Update the two ListView using imageTagsData and availableTagsData.
     */
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
}
