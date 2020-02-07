package model;

import controller.MainController;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
/** This initializer class used to initialize imagemappingtags. */
public class Initializer implements Serializable {
  private static final long serialVersionUID = 123456;
  private TagFinder tagFinder = new TagFinder();
  /** The directory of image, retrieve this from maincontroller. */
  private File imageDir = MainController.getDirectory();
  /** Create imagemappingtags if serialized file do not exist. otherwise read tagimage.ser file. */
  public void createImageMappingTags() {
    String serializedImageMappingTags =
        System.getProperty("user.dir") + "/config/serializedImageMappingTags.ser";
    File imageMapping = new File(serializedImageMappingTags);
    if (imageMapping.exists()) {
      Serializer.readTagsimage();
      Serializer.readFrom("tags");
      tagFinder.allImages = new ArrayList<Images>(Tags.imageMappingTag.keySet());
    } else {
      try {
        imageMapping.createNewFile();
        initImageMappingTags(imageDir, tagFinder.allImages);

      } catch (Exception ex) {
        ex.printStackTrace();
      }
    }
  }
  /**
   * Check if this file is image(ending with jped, png or jpg).
   *
   * @param file1 the file.
   * @return true if file is image.
   */
  private boolean isImage(File file1) {
    return (file1.getName().endsWith(".jpg")
        | file1.getName().endsWith(".png")
        | file1.getName().endsWith(".jpeg"));
  }
  /**
   * Initialize image mapping tags, called this methods only if serialize file do not exist.
   *
   * @param file1 the file.
   * @param allimages1 the list save all images.
   * @throws IOException the io exception.
   */
  private void initImageMappingTags(File file1, List<Images> allimages1) throws IOException {
    Tags.availableTags.clear();
    for (File f : file1.listFiles()) {
      if (isImage(f)) {
        Images image = new Images(f.toString());
        allimages1.add(image);
        String[] tags = f.toString().split("@");
        int taglength = tags.length;
        ArrayList<String> allTags = new ArrayList<>();
        if (taglength != 1) {
          for (int i = 1; i < taglength; i++) {
            if (i == taglength - 1) {
              String[] temp = tags[i].split("[/.]");
              allTags.add(temp[0]);
              if (isTagDuplicate(temp[0])) {
                Tags.availableTags.add(temp[0]);
              }
            } else {
              allTags.add(tags[i]);
              if (isTagDuplicate(tags[i])) {
                Tags.availableTags.add(tags[i]);
              }
            }
          }
        }
        Tags.imageMappingTag.put(image, allTags);

      } else if (f.isDirectory() && StatusObserver.isIfAll()) {
        initImageMappingTags(f, allimages1);
      }
    }

    Serializer.saveTagsimage();
    Serializer.saveTo("tags");
  }

  /**
   * Check if tag is duplicated.
   *
   * @param tg the tag
   * @return true if duplicated.
   */
  private boolean isTagDuplicate(String tg) {
    boolean ifD = true;
    if (Tags.availableTags.contains(tg)) {
      ifD = false;
    }
    return ifD;
  }
}
