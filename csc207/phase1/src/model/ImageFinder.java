package model;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This is ImageFinder class.
 * ImageGalleryController class use this class to find image and
 * display all images to GUI.
 */
public class ImageFinder {
    /**
     * Stores the names of the images under specified directory.
     * Map between absolute directory and filename.
     */
    private Map<String, String> fileNames;

    /**
     * Number of images inside the specified folder by user.
     */
    public static int imageNum = -1;
    /**
     * The specified directory.
     */
    private File directory;

    /**
     * the the directory.
     *
     * @return the directory.
     */
    public File getDirectory() {
        return directory;
    }

    /**
     * The image objects of the program.
     */
    public List<Images> images;

    /**
     * Constructs a image finder that searches image files in current directory
     * with an option to search sub-directory
     * and create a list of corresponding Images objects
     *
     * @param directory the directory of this Image
     * @param ifAll     true if the user want to search the subdirectory
     */
    public ImageFinder(String directory, boolean ifAll) {

        this.images = new ArrayList<>();
        this.fileNames = new HashMap<>();
        this.directory = new File(directory);
        createImage(directory, ifAll);
        imageNum = images.size();
    }

    /**
     * Searches for image files and stores the names in fileNames.
     *
     * @param directory the specified directory to search for image files
     * @param ifAll     true if the user want to search the subdirectory
     */
    private void setNames(String directory, boolean ifAll) throws IOException, NullPointerException {
        // Possible NullPointerException here.
        File folder = new File(directory);
        File[] listOfFiles = folder.listFiles();
        if (listOfFiles == null) {
            return;
        }
        for (File file : listOfFiles) {
            if (ifAll) {
                if (file.isDirectory()) {
                    this.setNames(file.getAbsolutePath(), true);
                }
            }
            if (file.isFile()) {
                // Potential IOException site.
                BufferedImage image = ImageIO.read(file);
                if (image != null) {
                    this.fileNames.put(file.getAbsolutePath(), file.getName());
                }
            }
        }
    }

    /**
     * Creates Images objects from specified directory.
     *
     * @param directory the required directory to find image files
     * @param ifAll     true if the user want to search the subdirectory
     */
    private void createImage(String directory, boolean ifAll) {
        try {
            this.setNames(directory, ifAll);
            for (Map.Entry<String, String> entry : this.fileNames.entrySet()) {
                this.images.add(new Images(entry.getKey()));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}