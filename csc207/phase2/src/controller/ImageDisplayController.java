package controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.File;
/**
 * this is imageDisplay controller class. SortimagesByTagsController and ImageGalleryController
 * inherited this class. Use display ImageOperation methods to display images.
 */
class ImageDisplayController extends MainController {

  /** The width and height of each image */
  private final double WIDTH = 650 / 2;

  private final double HEIGHT = 670 / 2;

  ImageView view;
  /**
   * display images. Subclass use this methods to implement its own display image method.
   *
   * @param file1 the file of image.
   */
  void displayImageOperation(File file1) {
    Image image = new Image(file1.toURI().toString());
    view = new ImageView(image);
    view.setFitWidth(WIDTH);
    view.setFitHeight(HEIGHT);
    view.setPreserveRatio(true);
    view.addEventHandler(
        MouseEvent.MOUSE_CLICKED,
        event -> {
          try {
            PhotoViewController.setCurrentDir(file1);
            Parent pane = FXMLLoader.load(getClass().getResource("../view/PhotoView.fxml"));
            Scene scene = new Scene(pane);
            Stage stage = (Stage) ((ImageView) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            event.consume();
          } catch (Exception ex) {
            ex.printStackTrace();
          }
        });
  }
}
