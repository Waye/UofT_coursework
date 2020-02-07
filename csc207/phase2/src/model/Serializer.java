package model;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
/** The serializer class use to serialize all necessary data for future use. */
public class Serializer {

  /** HashMap to store url->finder key-value set. */
  private static Map<String, ImageFinder> finderMap = new HashMap<>();

  /**
   * Getter for finderMap
   *
   * @return this finderMap
   */
  public static Map<String, ImageFinder> getFinderMap() {
    return finderMap;
  }

  /**
   * Save finderMap/availableTags to file.
   *
   * @param option "finder" or "tags"
   */
  public static void saveTo(String option) {
    try {
      if (option.equals("finder")) {
        saveFile(System.getProperty("user.dir") + "/config/serializedFinder.ser", finderMap);
      } else if (option.equals("tags")) {
        saveFile(
            System.getProperty("user.dir") + "/config/serializedTagMap.ser", Tags.availableTags);
      }
    } catch (Exception ex) {
      System.out.println("Serialize process failed!");
    }
  }

  /**
   * Read finderMap/availableTags from file.
   *
   * @param option "finder" or "tags"
   */
  @SuppressWarnings("unchecked")
  public static void readFrom(String option) {
    try {
      if (option.equals("finder")) {
        finderMap = readFile(System.getProperty("user.dir") + "/config/serializedFinder.ser");
      } else if (option.equals("tags")) {
        Tags.availableTags =
            readFile(System.getProperty("user.dir") + "/config/serializedTagMap.ser");
      }
    } catch (Exception ex) {
      System.out.println("Deserialize process failed!");
    }
  }

  /** temp methods. */
  public static void saveTagsimage() {
    saveFile(
        System.getProperty("user.dir") + "/config/serializedImageMappingTags.ser",
        Tags.imageMappingTag);
  }
  /**
   * The general methods to save serialize file.
   *
   * @param serializedFilePath The serialized file path
   * @param <T> the type of data.
   */
  private static <T> void saveFile(String serializedFilePath, T item) {
    try {
      OutputStream file = new FileOutputStream(serializedFilePath);
      OutputStream buffer = new BufferedOutputStream(file);
      ObjectOutput output = new ObjectOutputStream(buffer);
      // serialize the Map
      output.writeObject(item);
      // output.writeObject(Tags.availableTags);
      output.close();
    } catch (Exception ex) {
      ex.printStackTrace();
    }
  }
  /**
   * The general methods to read serialize file.
   *
   * @param serializedFilePath The serialized file path
   * @param <T> the type of data.
   * @return null
   * @throws ClassNotFoundException class may not found exception.
   * @throws IOException the io exception.
   */
  @SuppressWarnings("unchecked")
  private static <T> T readFile(String serializedFilePath)
      throws ClassNotFoundException, IOException {
    try {
      InputStream file = new FileInputStream(serializedFilePath);
      InputStream buffer = new BufferedInputStream(file);
      ObjectInput input = new ObjectInputStream(buffer);
      // deserialize the Map
      T item = (T) input.readObject();
      input.close();
      return item;
    } catch (Exception ex) {
      System.out.println("Deserialize process failed!");
    }
    return null;
  }
  /** A temp methods, alter laterd. */
  static void readTagsimage() {
    try {
      Tags.imageMappingTag =
          readFile(System.getProperty("user.dir") + "/config/serializedImageMappingTags.ser");
    } catch (Exception ex) {
      ex.printStackTrace();
    }
  }
}
