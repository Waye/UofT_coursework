package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * This is a tag class. tag can be add to an image, an image can have multiple tags. can read tags
 * from current file and save it if any changes.
 */
public class Tags implements Serializable {
  /** This is for compiler to serialize/deserialize the file. */
  private static final long serialVersionUID = 123456;
  /** Current Available Tags. */
  public static List<String> availableTags = new ArrayList<>();

  public static Map<Images, ArrayList<String>> imageMappingTag = new LinkedHashMap<>();

  /** To string methods. */
  @Override
  public String toString() {
    return String.join(" ", availableTags);
  }
  /**
   * Given a map, a key and an item, you can add this item corresponding to the key in the map.
   *
   * @param maps The map you wants to operate.
   * @param key the key is a images.
   * @param item the tag you wants to add.
   */
  void addToList(Map<Images, ArrayList<String>> maps, Images key, String item) {
    ArrayList<String> list = new ArrayList<>();
    if (maps.containsKey(key)) {
      list = maps.get(key);
      maps.put(key, list);
    } else {
      maps.put(key, list);
    }
    list.add(item);
  }
  /**
   * Given a map, a key and an item, you can delete this item corresponding to the key in the map.
   *
   * @param maps The map you wants to operate.
   * @param key the key is a images.
   * @param item the tag you wants to del.
   */
  void delTolist(Map<Images, ArrayList<String>> maps, Images key, String item) {
    ArrayList<String> list = new ArrayList<>();
    if (maps.containsKey(key)) {
      list = maps.get(key);
      if (list.contains(item)) {
        if (list.size() == 1) {
          maps.remove(key);
        } else {
          list.remove(item);
        }
      } else {
        System.out.println("not such value");
      }
    }
  }
}
