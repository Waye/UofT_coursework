package model;
/** This is iterator interface design for iterator design pattern. */
public interface ImageIterator {
  /**
   * return image of next index.
   *
   * @param currentImage the current images' index.
   * @return the next image.
   */
  public Images nextImage(int currentImage);
  /**
   * return image of prev index.
   *
   * @param currentImage the current images' index.
   * @return the prev image.
   */
  public Images prevImage(int currentImage);
}
