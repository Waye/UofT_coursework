package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SelectionMode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import model.*;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
/**
 * This class display images by tags. able to view all images under certain tags. is controller of
 * ViewImagesofTag.fxml
 */
public class SortImagesByTagsController extends ImageDisplayController implements Initializable {
  /** The vbox to show all images under tags. */
  private VBox vb = new VBox();

  @FXML private ListView<String> taglistview;

  private TagFinder tagFinder = new TagFinder();
  private ImageSurfer imageSurfer = new ImageSurfer(1000);
  /** The scrollpaneimage to show all images under tag. scorllpanelist shows all list. */
  @FXML private ScrollPane scrollPaneImage;

  @FXML private ScrollPane scrollPaneList;
  /** THe initializer use to initialize availabletags and imagemappingtags. */
  private Initializer initializer = new Initializer();

  @Override
  public void initialize(URL location, ResourceBundle resources) {

    initializer.createImageMappingTags();
    listView();
  }
  /** Show all existing tags on window. */
  private void listView() {
    taglistview.getItems().addAll(Tags.availableTags);
    ObservableList<String> tas = FXCollections.observableArrayList(Tags.availableTags);
    taglistview.setItems(tas);

    taglistview.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
  }
  /** listview listener, click certain tag and response. */
  @FXML
  public void handleListview() {

    ObservableList<String> tag1 = taglistview.getSelectionModel().getSelectedItems();
    String tagBy = tag1.get(0);
    List<Images> allim = new ArrayList<>();
    tagFinder.allImages = new ArrayList<>(Tags.imageMappingTag.keySet());
    allim = tagFinder.imagesTaggedBy(tagBy, tagFinder.allImages);
    refreshImage(allim.size(), allim);
  }
  /**
   * show images under tags when new tags listener hit.
   *
   * @param numImage the number of images.
   * @param allimages1 the list contains all images under this tag.
   */
  private void refreshImage(int numImage, List<Images> allimages1) {

    ImageSurfer imageSurfer = new ImageSurfer(numImage);
    int count = imageSurfer.getMaxNum();
    HBox hb = new HBox();

    for (int i = 0; i < count; i = i + 1) {
      File file1 = imageSurfer.nexttagImage(i - 1, allimages1).getFile();

      super.displayImageOperation(file1);
      vb.getChildren().add(view);
      scrollPaneImage.setContent(vb);
    }
    hb.getChildren().addAll(taglistview);
    scrollPaneList.setContent(hb);
  }
}
