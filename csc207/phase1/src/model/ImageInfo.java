package model;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

/**
 * This is ImageInfo class.
 * it is superclass of images, contains some methods image will be used.
 */
public class ImageInfo implements Serializable {
    /**
     * Current name history of the image.
     */
    List<String> nameHistory;
    /**
     * Current tags of this image.
     */
    List<String> currentTags;
    /**
     * This Images File.
     */
    File file;

    /**
     * getter of name history.
     *
     * @return the name history.
     */
    public List<String> getNameHistory() {
        return nameHistory;
    }

    /**
     * the getter of name.
     *
     * @return the name.
     */
    public String getName() {
        return file.getName();
    }

    /**
     * getter of current tags.
     *
     * @return the list of current tags.
     */
    public List<String> getCurrentTags() {
        return currentTags;
    }

    public void setCurrentTags(List<String> currentTags) {
        this.currentTags = currentTags;
    }

    /**
     * getter of file.
     *
     * @return the file.
     */
    public File getFile() {
        return file;
    }

    /**
     * set the file.
     *
     * @param file the file.
     */
    public void setFile(File file) {
        this.file = file;
    }

    /**
     * find the absolute path of file.
     *
     * @return
     */
    public String getDir() {
        return file.getAbsolutePath();
    }

    /**
     * Resets the name to its root name, eg. cat @John.jpg -> cat.jpg.
     */
    void resetName() throws IOException {
        try {
            String[] splitByTag = getName().split(" @");
            String[] extensionArr = splitByTag[splitByTag.length - 1].split("[.]");
            String extension = "";
            for (int i = 1; i < extensionArr.length; ++i) {
                extension += "." + extensionArr[i];
            }
            String newName = splitByTag[0] + extension;

            Files.move(file.toPath(), file.toPath().resolveSibling(file.getParent() + "/" + newName));
            file = new File(file.getParent() + "/" + newName);
        } catch (NullPointerException ex) {
            System.out.println("No name to be reset to!");
            ex.printStackTrace();
        }
    }

    /**
     * Finds the tags in a file name, eg. cat @John @Allen.jpg -> [John, Jason].
     *
     * @return list of tags in the file name
     */
    public List<String> tagsInName() {
        ArrayList<String> result = new ArrayList<>();
        String name = this.file.getName();
        String[] namePieces = name.split(" @");
        if (namePieces.length != 1) {
            for (int i = 1; i < namePieces.length; ++i) {
                result.add(namePieces[i].split("[.]")[0]);
            }
        }
        return result;
    }

    /**
     * to string methods
     *
     * @return absolute path of file.
     */
    @Override
    public String toString() {
        return file.getAbsolutePath();
    }

    /**
     * Two ImageInfo are equal if their absolute path are the same.
     *
     * @param obj the object to be compared with
     * @return true if they have the same absolute path
     */
    @Override
    public boolean equals(Object obj) {
        return (obj instanceof ImageInfo) && (((ImageInfo) obj).getFile().equals(file));
    }

}
