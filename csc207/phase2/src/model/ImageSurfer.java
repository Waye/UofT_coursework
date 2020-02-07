package model;

import java.util.List;

/**
 * This is image surfer class. is inherited from imageiterator interface. it is iterator design
 * pattern.
 */
public class ImageSurfer implements ImageIterator {
  /** the maximum number of images. */
  private int maxNum;

  /**
   * The image surfer constructor.
   *
   * @param num the number wants to set.
   */
  public ImageSurfer(int num) {
    this.maxNum = num;
  }

  /**
   * The getter of max number.
   *
   * @return the max number.
   */
  public int getMaxNum() {
    return maxNum;
  }

  /**
   * inherited from interface. view next image in certain directory.
   *
   * @param currentImage the index of current image.
   * @return the images.
   */
  public Images nextImage(int currentImage) {
    int next = currentImage;
    if (currentImage != maxNum - 1) {
      next = currentImage + 1;
    }
    return StatusObserver.getFinder().images.get(next);
  }
  /**
   * inherited from interface. view prev image in certain directory.
   *
   * @param currentImage the index of current image.
   * @return the images.
   */
  public Images prevImage(int currentImage) {
    int prev = currentImage;
    if (currentImage != 0) {
      prev = currentImage - 1;
    }
    return StatusObserver.getFinder().images.get(prev);
  }

  /**
   * This is a special methods made for SortImagesByTagsController. Use this method to find next
   * image under certain tags.
   *
   * @param currentImage the index of current image.
   * @param imagesList the list of images can choose from.
   */
  public Images nexttagImage(int currentImage, List<Images> imagesList) {
    int next = 0;
    if (currentImage != maxNum - 1) {
      next = currentImage + 1;
    }
    return imagesList.get(next);
  }
}
