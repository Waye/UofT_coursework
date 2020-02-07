package test;

import model.Images;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class ImagesTest {
  /**
   * Tests the case when adding tag to an Images object.
   *
   * @throws IOException when the image does not exist
   */
  @Test
  void testAddTag() throws IOException {
    Images image = new Images(System.getProperty("user.dir") + "/image_test/dog.jpg");
    image.addTag("apple");
    assertTrue(image.getCurrentTags().size() == 1 && image.getCurrentTags().get(0).equals("apple"));
    image.deleteTag("apple");
  }

  /**
   * Tests the case when deleting tag from an Images object.
   *
   * @throws IOException when the image does not exist
   */
  @Test
  void testDeleteTag() throws IOException {
    Images image = new Images(System.getProperty("user.dir") + "/image_test/dog.jpg");
    image.addTag("apple");
    image.deleteTag("apple");
    assertTrue(image.getCurrentTags().size() == 0);
  }

  /**
   * Tests the case when deleting tag that does not exist in the image name.
   *
   * @throws IOException when the image does not exist
   */
  @Test
  void testDeleteNotExistingTag() throws IOException {
    Images image = new Images(System.getProperty("user.dir") + "/image_test/dog.jpg");
    image.addTag("apple");
    image.deleteTag("banana");
    assertTrue(image.getCurrentTags().size() == 1);
    assertEquals("apple", image.getCurrentTags().get(0));

    image.deleteTag("apple");
  }

  /**
   * Tests the case when renaming an image file.
   *
   * @throws IOException when the image specified does not exist or the new name already exists
   */
  @Test
  void testRename() throws IOException {
    Images image = new Images(System.getProperty("user.dir") + "/image_test/dog.jpg");
    File oldImage = new File(System.getProperty("user.dir") + "/image_test/dog.jpg");
    File newImage = new File(System.getProperty("user.dir") + "/image_test/newDog.jpg");

    image.rename("newDog.jpg");

    assertEquals("newDog.jpg", image.getName());
    assertTrue(!oldImage.exists() && newImage.exists());

    image.rename("dog.jpg");
  }

  /** Tests the case when the specific image file does not exist. */
  @Test
  void testImageNotFoundException() {
    assertThrows(
        IOException.class, () -> new Images(System.getProperty("user.dir") + "wrong path"));
  }

  /** Tests the case when the new name specified already exists. */
  @Test
  void testRenameException() throws Exception {
    Images image = new Images(System.getProperty("user.dir") + "/image_test/dog.jpg");
    assertThrows(IOException.class, () -> image.rename("cat.jpg"));
  }
}
