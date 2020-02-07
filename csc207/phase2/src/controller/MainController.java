package controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import model.ImageFinder;
import model.Serializer;
import model.StatusObserver;

import java.io.File;
import java.util.Observable;

/**
 * De-serializes the data from file or creates the file if it doesn't exist and stores the core
 * information of the application in run time. This is superclass of all controller.
 */
public class MainController extends Observable {
  /** Make sure the de-serialize process only proceeds once. */
  private static boolean ifFirst = true;

  /** Current working directory of the application. */
  private static File directory;
  /** true if the user wants to search through all sub-directories of specified folder. */
  private static boolean ifAll;
  /** The ImageFinder to initiate the Images objects. */
  private static ImageFinder finder;
  /** The index of currently viewing image in Images list of ImageFinder. */
  private static int index;
  /** De-serializes the data from file or creates the file if it doesn't exist. */
  MainController() {
    if (ifFirst) {
      try {
        String serializedTags = System.getProperty("user.dir") + "/config/serializedTagMap.ser";
        String serializedFinderMap =
            System.getProperty("user.dir") + "/config/serializedFinder.ser";
        // Reads serializable objects from file.
        // Populates the tags record using stored data, if it exists.
        File tagsFile = new File(serializedTags);
        File finderFile = new File(serializedFinderMap);
        if (tagsFile.exists() && finderFile.exists()) {
          Serializer.readFrom("tags");
          Serializer.readFrom("finder");
        } else if (!tagsFile.exists() && finderFile.exists()) {
          tagsFile.createNewFile();
          Serializer.readFrom("finder");
        } else if (!finderFile.exists() && tagsFile.exists()) {
          finderFile.createNewFile();
          Serializer.readFrom("tags");
        } else {
          finderFile.createNewFile();
          tagsFile.createNewFile();
        }
      } catch (Exception ex) {
        ex.printStackTrace();
      } finally {
        ifFirst = false;
      }
    }
  }

  /**
   * Getter for directory.
   *
   * @return current working directory
   */
  public static File getDirectory() {
    return directory;
  }

  /** Sets the currently working directory. */
  public static void setDirectory(File directory) {
    MainController.directory = directory;
  }

  /**
   * Getter for ifAll.
   *
   * @return value of ifAll
   */
  public static boolean isIfAll() {
    return ifAll;
  }

  /**
   * Setter for ifAll.
   *
   * @param ifAll the desired value for ifAll
   */
  public static void setIfAll(boolean ifAll) {
    MainController.ifAll = ifAll;
  }

  /**
   * Getter for finder.
   *
   * @return current working ImageFinder
   */
  public static ImageFinder getFinder() {
    return finder;
  }

  /**
   * Setter for current finder
   *
   * @param finder desired ImageFinder Object
   */
  public static void setFinder(ImageFinder finder) {
    MainController.finder = finder;
  }

  /**
   * Getter for index of current viewing image.
   *
   * @return corresponding index of Image
   */
  public static int getIndex() {
    return index;
  }

  /**
   * Setter for index of current viewing image.
   *
   * @param index desired index value
   */
  public static void setIndex(int index) {
    MainController.index = index;
  }

  /**
   * All subclass use this methods to skip to next fxml.
   *
   * @param fxPath The path of fxml
   * @param title the title of stage
   */
  void nextFXML(String fxPath, String title) {
    try {
      FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(fxPath));
      Parent root = fxmlLoader.load();
      Scene scene = new Scene(root);
      Stage stage = new Stage();
      stage.setTitle(title);
      stage.setScene(scene);
      stage.show();
    } catch (Exception ex) {
      ex.printStackTrace();
    }
  }
  /**
   * pop up alert windows.
   *
   * @param alertInfo alert information wants to show.
   */
  void alertMessage(String alertInfo) {
    Alert alert = new Alert(Alert.AlertType.INFORMATION);
    alert.setTitle("Alert!");
    alert.setHeaderText("Invalid Operation");
    alert.setContentText(alertInfo);
    alert.show();
  }

  /**
   * Reports current status to StatusObserver, namely changes to the variables: directory, ifAll,
   * finder, and index.
   *
   * @param obj the Object that should be updated
   */
  void reportStatus(Object obj) {
    addObserver(new StatusObserver());
    setChanged();
    notifyObservers(obj);
    deleteObservers();
  }
}
