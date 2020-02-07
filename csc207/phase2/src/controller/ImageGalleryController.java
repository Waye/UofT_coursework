package controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import model.ImageFinder;
import model.ImageSurfer;
import model.Serializer;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * The ImageGalleryController class is a controller of image gallery can view all images in a
 * specific directory. can view single image by clicking on it.
 */
public class ImageGalleryController extends ImageDisplayController implements Initializable {
  /** if this is first directory. */
  private boolean isFirst = true;
  /** Ths scroll pane. */
  @FXML private ScrollPane scrollPane;
  /**
   * Initiates the ImageFinder of this program.
   *
   * @param url the absolute path of the desired directory
   */
  private void convertImage(String url) {
    if (Serializer.getFinderMap().containsKey(url)) {
      setFinder(Serializer.getFinderMap().get(url));
      reportStatus(getFinder());
    } else {
      setFinder(new ImageFinder(url, isIfAll()));
      reportStatus(getFinder());
      Serializer.getFinderMap().put(url, getFinder());
    }
  }

  /**
   * Initializes the images found in the directory and evokes PhotoView.fxml on mouse clicking event
   *
   * @param location The location used to resolve relative paths for the root object, or
   *     <tt>null</tt> if the location is not known.
   * @param resources The resources used to localize the root object, or <tt>null</tt> if the root
   *     object was not localized.
   */
  @Override
  public void initialize(URL location, ResourceBundle resources) {
    try {
      if (isFirst) {
        convertImage(getDirectory().getAbsolutePath());
        isFirst = false;
      }
      displayImages(getFinder().images.size());
    } catch (Exception ex) {
      ex.printStackTrace();
    }
  }
  /**
   * Display all images in certain directory.
   *
   * @param size1 the max number of images.
   */
  private void displayImages(int size1) {
    ImageSurfer imageSurfer = new ImageSurfer(size1);
    VBox vb = new VBox();
    for (int i = 0; i < size1; i = i + 2) {
      HBox hb = new HBox();
      for (int j = 0; j < 2 && i + j < size1; j++) {
        if (j == 0) {
          vb.getChildren().add(hb);
        }
        final int ind = i + j;
        File file = imageSurfer.nextImage(i + j - 1).getFile();
        super.displayImageOperation(file);
        reportStatus(ind);

        hb.getChildren().add(view);
        scrollPane.setContent(vb);
      }
    }
  }

  @FXML
  public void handleTags() {
    nextFXML("../view/ViewImagesofTag.fxml", "Images");
  }
}
