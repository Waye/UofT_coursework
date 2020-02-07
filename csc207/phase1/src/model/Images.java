package model;


import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * This is a image class.
 * Image are created when read new image
 * Images can be stored after application closed.
 * Images can be renamed, add tag, delete tag, image
 * name will change follow with different tags.
 */
public class Images extends ImageInfo implements Serializable {
    /**
     * This is for compiler to serialize/deserialize the file.
     */
    private static final long serialVersionUID = 123456;

    /**
     * Constructs the image with its absolute path.
     *
     * @param directory the absolute path of the image file.
     * @throws IOException when the FileHandler cannot create the output file properly
     */
    public Images(String directory) throws IOException {
        File file = new File(directory);
        if (!file.exists()) {
            new IOException("Images not found!").printStackTrace();
        }
        this.currentTags = new ArrayList<>();
        this.nameHistory = new ArrayList<>();
        this.file = file;
        this.nameHistory.add(this.file.getName());

        currentTags.addAll(tagsInName());
        Logging logging = new Logging();
        logging.setLog();
    }

    /**
     * Deletes the tag from current tags, rename the file and delete from availableTags in Tags
     *
     * @param tag the String representation of the tag to be deleted
     * @throws IOException when tag cannot be saved to serialized file or deleted from file name properly
     */
    public void deleteTag(String tag) throws IOException {
        if (tag == null || tag.length() == 0) return;
        String oldName = getName();
        boolean success = this.currentTags.remove(tag);
        resetName();
        extendName(currentTags);
        String newName = getName();
        if (success) {
            Tags.availableTags.remove(tag);
            Tags.saveTag();
            if (!nameHistory.contains(getName())) {
                nameHistory.add(getName());
            }
            Logging.recordLogInfo(oldName, newName);
        }
    }

    /**
     * Adds the tag to current tags, rename the file and add to availableTag in Tags
     *
     * @param tag the String representation of the tag to be saved
     * @throws IOException when tag cannot be saved to serialized file or added to file name properly
     */
    public void addTag(String tag) throws IOException {
        if (tag == null || tag.length() == 0) return;
        String oldName = getName();
        this.currentTags.add(tag);

        Tags.availableTags.add(tag);
        Tags.saveTag();

        List<String> temp = new ArrayList<>();
        temp.add(tag);

        this.extendName(temp);
        String newName = getName();
        Logging.recordLogInfo(oldName, newName);
    }

    /**
     * Renames the Images file according to given new name.
     *
     * @param newName the required new name for the image file
     * @throws IOException throws exception when the new name already exists in the directory
     */
    public void rename(String newName) throws IOException {
        File oldFile = file.getAbsoluteFile();
        File newFile = new File(oldFile.getParent() + "/" + newName);
        if (newFile.exists()) {
            throw new IOException("This name exists!");
        }
        boolean success = oldFile.renameTo(newFile);
        if (success) {
            file = newFile;
            if (!nameHistory.contains(newName)) {
                nameHistory.add(newName);
            }
        }
    }

    /**
     * Adds list of tags to the name , eg. cat.jpg -> cat @Brian @Carol.jpg
     *
     * @param tags list of strings of tags
     * @throws IOException if the new file name already exists in the directory
     */
    private void extendName(List<String> tags) throws IOException {
        if (tags == null || tags.size() == 0) return;
        String[] currentName = this.getName().split("[.]");
        if (tags.size() == 1) {
            currentName[0] = currentName[0] + " @" + tags.get(0);
        } else {
            String tagsAppend = " @" + String.join(" @", tags);
            currentName[0] = currentName[0] + tagsAppend;
        }
        String fullName = String.join(".", currentName);
        rename(fullName);
    }
}
