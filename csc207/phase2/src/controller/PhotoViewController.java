package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import model.ImageFinder;
import model.ImageSurfer;
import model.Images;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * The PhotoViewController is controller of photoview.fxml can view a single photo. can view
 * previous and next photo in this directory. can view image information, move image to another
 * directory. can go back to home image gallery.
 */
public class PhotoViewController extends MainController implements Initializable {
  /** Used to store the entry point of PhotoView. */
  private static File currentDir;
  /** imageview to view current image. */
  @FXML private ImageView imageView;
  /** name of the label. */
  @FXML private Label name;

  private ImageSurfer surfer;
  // private static int index;

  /**
   * set the current directory file.
   *
   * @param currentDir the current directory file.
   */
  static void setCurrentDir(File currentDir) {
    PhotoViewController.currentDir = currentDir;
  }

  /** Sets up the current viewing image information. */
  @Override
  public void initialize(URL location, ResourceBundle resources) {
    surfer = new ImageSurfer(getFinder().images.size());
    try {
      ImageFinder pointer = getFinder();
      name.setText(currentDir.getName());
      setIndex(pointer.images.indexOf(new Images(currentDir.getAbsolutePath())));
      notifyObservers(getIndex());
      displayImage(pointer.images.get(getIndex()).getDir());
    } catch (Exception ex) {
      ex.printStackTrace();
    }
  }

  /**
   * Displays the image file according to the given path.
   *
   * @param imagePath path of the image file
   */
  private void displayImage(String imagePath) {
    File file = new File(imagePath);
    Image image = new Image(file.toURI().toString());
    this.imageView.setImage(image);
  }
  /**
   * return next images.
   *
   * @param imageSurfer the image surfer to return images in order.
   * @return the next images.
   */
  private Images getNextImage(ImageSurfer imageSurfer) {
    return imageSurfer.nextImage(getIndex());
  }

  /**
   * return previous images.
   *
   * @param imageSurfer the image surfer to return images in order.
   * @return the previous images.
   */
  private Images getPrevImage(ImageSurfer imageSurfer) {
    return imageSurfer.prevImage(getIndex());
  }
  /**
   * Increments/decrements the index when passing "next"/"prev" correspondingly and updates the
   * imageView and name Label
   *
   * @param order "next" or "prev"
   */
  private void refreshImage(String order) {
    File file;
    if (order.equalsIgnoreCase("prev")) {
      file = getPrevImage(surfer).getFile();
      if (getIndex() != 0) {
        setIndex(getIndex() - 1);
        reportStatus(getIndex());
      }
    } else if (order.equalsIgnoreCase("next")) {
      file = getNextImage(surfer).getFile();
      if (getIndex() != getFinder().images.size() - 1) {
        setIndex(getIndex() + 1);
        reportStatus(getIndex());
      }
    } else {
      file = null;
    }
    // finder.images.get(index) returns a Images.

    Image image = new Image(file.toURI().toString());
    name.setText(file.getName());
    this.imageView.setImage(image);
    imageView.setRotate(0);
  }

  /**
   * see the next image in directory.
   *
   * @throws IOException the io exception.
   */
  @FXML
  private void handleNextImage() throws IOException {
    refreshImage("next");
  }

  /**
   * see the previous image in directory.
   *
   * @throws IOException the io exception.
   */
  @FXML
  private void handlePrevImage() throws IOException {
    refreshImage("prev");
  }

  /** Evokes MoveImageView.fxml on click action. */
  @FXML
  private void handleMove() {
    nextFXML("../view/MoveImageView.fxml", "Move Image");
  }

  /** Evokes ImageInfoView.fxml on click action. */
  @FXML
  private void handleImageInfo() {
    nextFXML("../view/ImageInfoView.fxml", "Image Information");
  }

  /** Go back to the ImageGallery view. */
  @FXML
  private void handleHome(ActionEvent event) {
    try {
      Parent pane = FXMLLoader.load(getClass().getResource("../view/ImageGallery.fxml"));
      Scene scene = new Scene(pane);
      Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
      stage.setScene(scene);
      stage.setTitle("Image Manager 1.5");
    } catch (Exception ex) {
      ex.printStackTrace();
    }
  }

  /** Rotates the current image by 90 degrees clockwise. */
  @FXML
  private void handleRotate() {
    imageView.setRotate(imageView.getRotate() + 90);
  }
}
