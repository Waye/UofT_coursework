package controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.ImageFinder;
import javafx.scene.control.ScrollPane;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * The ImageGalleryController class is a controller of image gallery
 * can view all images in a specific directory.
 * can view single image by clicking on it.
 */
public class ImageGalleryController extends MainController implements Initializable {
    /**
     * The width and height of each image
     * and whether the ImageGalleryController runs for the first time
     */
    private final double WIDTH = 650 / 2;
    private final double HEIGHT = 670 / 2;
    /**
     * if this is first directory.
     */
    private boolean isFirst = true;
    /**
     * Ths scroll pane.
     */
    @FXML
    private ScrollPane scrollPane;

    /**
     * Initiates the ImageFinder of this program.
     *
     * @param url the absolute path of the desired directory
     */
    private void convertImage(String url) {
        finder = new ImageFinder(url, ifAll);
    }

    /**
     * Initializes the images found in the directory
     * and evokes PhotoView.fxml on mouse clicking event
     *
     * @param location  The location used to resolve relative paths for the root object, or
     *                  <tt>null</tt> if the location is not known.
     * @param resources The resources used to localize the root object, or <tt>null</tt> if
     *                  the root object was not localized.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            if (isFirst) {
                convertImage(directory.getAbsolutePath());
                isFirst = false;
            }
            ImageFinder pointer = finder;
            int count = pointer.images.size();
            VBox vb = new VBox();
            for (int i = 0; i < count; i = i + 2) {
                HBox hb = new HBox();
                for (int j = 0; j < 2 && i + j < count; ++j) {
                    if (j == 0) {
                        vb.getChildren().add(hb);
                    }
                    File file = pointer.images.get(i + j).getFile();
                    Image image = new Image(file.toURI().toString());
                    ImageView view = new ImageView(image);
                    view.setFitWidth(WIDTH);
                    view.setFitHeight(HEIGHT);
                    view.setPreserveRatio(true);
                    view.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
                        try {
                            PhotoViewController.setCurrentDir(file);
                            Parent pane = FXMLLoader.load(getClass().getResource("../view/PhotoView.fxml"));
                            Scene scene = new Scene(pane);
                            Stage stage = (Stage) ((ImageView) event.getSource()).getScene().getWindow();
                            stage.setScene(scene);
                            event.consume();
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    });

                    hb.getChildren().add(view);
                    scrollPane.setContent(vb);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
