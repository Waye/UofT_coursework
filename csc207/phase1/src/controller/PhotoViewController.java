package controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import model.ImageFinder;
import javafx.event.ActionEvent;
import model.Images;

/**
 * The PhotoViewController is controller of photoview.fxml
 * can view a single photo.
 * can view previous and next photo in this directory.
 * can view image information, move image to another directory.
 * can go back to home image gallery.
 */
public class PhotoViewController extends MainController implements Initializable {
    /**
     * Used to store the entry point of PhotoView.
     */
    private static File currentDir;
    /**
     * imageview to view current image.
     */
    @FXML
    private ImageView imageView;
    /**
     * name of the label.
     */
    @FXML
    private Label name;

    /**
     * Sets up the current viewing image information.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            ImageFinder pointer = finder;
            name.setText(currentDir.getName());
            index = pointer.images.indexOf(new Images(currentDir.getAbsolutePath()));
            displayImage(pointer.images.get(index).getDir());
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    /**
     * set the current directory file.
     *
     * @param currentDir the current directory file.
     */
    static void setCurrentDir(File currentDir) {
        PhotoViewController.currentDir = currentDir;
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
     * Increments/decrements the index when passing "next"/"prev" correspondingly
     * and updates the imageView and name Label
     *
     * @param order "next" or "prev"
     */
    private void refreshImage(String order) {
        if (order.equalsIgnoreCase("prev")) {
            if (index != 0) {
                index--;
            }
        } else if (order.equalsIgnoreCase("next")) {
            if (index != ImageFinder.imageNum - 1) {
                index++;
            }
        }
        File file = finder.images.get(index).getFile();
        Image image = new Image(file.toURI().toString());
        name.setText(file.getName());
        this.imageView.setImage(image);
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

    /**
     * Evokes MoveImageView.fxml on click action.
     */
    @FXML
    private void handleMove() {
        nextFXML("../view/MoveImageView.fxml", "Move Image");
    }

    /**
     * Evokes ImageInfoView.fxml on click action.
     */
    @FXML
    private void handleImageInfo() {
        nextFXML("../view/ImageInfoView.fxml", "Image Information");
    }

    /**
     * Go back to the ImageGallery view.
     */
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

    /**
     * Evokes LogView.fxml on click action.
     */
    @FXML
    private void handleLog() {
        nextFXML("../view/LogView.fxml", "Renaming Log History");
    }

}

