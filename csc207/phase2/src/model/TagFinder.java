package model;

import java.util.ArrayList;
import java.util.List;
/**
 * This is Tag finder class. this class use dependency injection design pattern to show all images
 * under certain tags.
 */
public class TagFinder implements Finder {
  public List<Images> allImages = new ArrayList<>();
  /** To store all images under some tag. */
  private List<Images> selectedImages = new ArrayList<>();
  // private Initializer initializer = new Initializer();
  public TagFinder() {}

  /**
   * find all images existing in all
   *
   * @return all images.
   */
  public List<Images> findAll() {
    return allImages = new ArrayList<>(Tags.imageMappingTag.keySet());
  }
  /**
   * collect all images that has certain tag.
   *
   * @param tag the tag you selected.
   * @param images the images under tag.
   * @return list of image has this tag.
   */
  public List<Images> imagesTaggedBy(String tag, List<Images> images) {
    selectedImages.clear();
    for (Images img : images) {
      String str = img.getFile().toString();
      String[] strArr = str.split("@");
      if (strArr.length != 1) {
        ArrayList<String> imageTags = Tags.imageMappingTag.get(img);
        if (imageTags.size() != 0 && containTag(imageTags, tag)) {
          selectedImages.add(img);
        }
      }
    }
    return selectedImages;
  }

  /**
   * Check it this tag list of some image has this tag.
   *
   * @param listOfTags the list of tag wants to check.
   * @param tag the tag.
   * @return true the taglist have this tag.
   */
  private boolean containTag(ArrayList<String> listOfTags, String tag) {
    boolean contains = false;
    for (String t : listOfTags) {
      if (t.equals(tag)) {
        contains = true;
      }
    }
    return contains;
  }

  private String imageName(Images image) {
    String path = image.getFile().toString();
    String[] fullname = path.split("/");
    String name = fullname[fullname.length - 1];
    return name;
  }
}
