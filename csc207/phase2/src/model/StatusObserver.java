package model;

import java.io.File;
import java.util.Observable;
import java.util.Observer;
/**
 * This is a oberserver class implement default java observer interface. if variables in this class
 * changed, it will notified other class to change these variables correspondingly.
 */
public class StatusObserver implements Observer {

  /** Current working directory of the application. */
  private static File directory;
  /** true if the user wants to search through all sub-directories of specified folder. */
  private static boolean ifAll;
  /** The ImageFinder to initiate the Images objects. */
  private static ImageFinder finder;
  /** The index of currently viewing image in Images list of ImageFinder. */
  private static int index;

  /**
   * Getter for directory.
   *
   * @return current working directory
   */
  public static File getDirectory() {
    return directory;
  }

  /**
   * Getter for ifAll.
   *
   * @return whether search for subdirectory
   */
  public static boolean isIfAll() {
    return ifAll;
  }

  /**
   * Getter for finder
   *
   * @return current ImageFinder for searching Images
   */
  public static ImageFinder getFinder() {
    return finder;
  }

  /**
   * Getter for index
   *
   * @return index of currently viewing image
   */
  public static int getIndex() {
    return index;
  }

  /**
   * Updates the static variables: directory, ifAll, finder and index.
   *
   * @param observable the Observable Object that calls this method
   * @param obj the Object value to be updated
   */
  @Override
  public void update(Observable observable, Object obj) {
    if (obj instanceof File) {
      directory = (File) obj;
    } else if (obj instanceof Boolean) {
      ifAll = (Boolean) obj;
    } else if (obj instanceof ImageFinder) {
      finder = (ImageFinder) obj;
    } else if (obj instanceof Integer) {
      index = (Integer) obj;
    }
  }
}
