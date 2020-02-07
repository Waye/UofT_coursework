package test;

import model.ImageFinder;
import model.Images;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertTrue;

class ImageFinderTest {
  /**
   * Test the case when the user does not want to search images in subdirectories of current folder.
   *
   * @throws IOException when the image does not exist
   */
  @Test
  void testNoSubdirectory() throws IOException {
    ImageFinder finder = new ImageFinder(System.getProperty("user.dir") + "/image_test", false);
    ArrayList<Images> allImages = new ArrayList<>();
    allImages.add(new Images(System.getProperty("user.dir") + "/image_test/cat.jpg"));
    allImages.add(new Images(System.getProperty("user.dir") + "/image_test/dog.jpg"));
    for (Images image : allImages) {
      assertTrue(finder.images.contains(image));
    }
    for (Images image : finder.images) {
      assertTrue(allImages.contains(image));
    }
  }

  /**
   * Test the case when the user wants to search all images, including those in the subdirectories.
   *
   * @throws IOException when the image does not exist
   */
  @Test
  void testSubdirectory() throws IOException {
    ImageFinder finder = new ImageFinder(System.getProperty("user.dir") + "/image_test", true);
    ArrayList<Images> allImages = new ArrayList<>();
    allImages.add(new Images(System.getProperty("user.dir") + "/image_test/cat.jpg"));
    allImages.add(new Images(System.getProperty("user.dir") + "/image_test/dog.jpg"));
    allImages.add(new Images(System.getProperty("user.dir") + "/image_test/sub/lamb.png"));
    for (Images image : allImages) {
      assertTrue(finder.images.contains(image));
    }
    for (Images image : finder.images) {
      assertTrue(allImages.contains(image));
    }
  }
}
