package model;

import java.io.*;
import java.util.*;

/**
 * This is a tag class.
 * tag can be add to an image, an image can have multiple tags.
 * can read tags from current file and save it if any changes.
 */
public class Tags implements Serializable {
    /**
     * This is for compiler to serialize/deserialize the file.
     */
    private static final long serialVersionUID = 123456;
    /**
     * Current Available Tags.
     */
    public static List<String> availableTags = new ArrayList<>();

    /**
     * Save tags to file.
     *
     * @throws IOException the io exception.
     */
    static void saveTag() throws IOException {
        OutputStream file = new FileOutputStream(System.getProperty("user.dir") + "/config/serializedTagMap.ser");
        OutputStream buffer = new BufferedOutputStream(file);
        ObjectOutput output = new ObjectOutputStream(buffer);
        // serialize the Map
        output.writeObject(availableTags);
        output.close();
    }

    /**
     * Read the tag information from file.
     */
    @SuppressWarnings("unchecked")
    public static void readTag() {
        try {
            InputStream file = new FileInputStream(System.getProperty("user.dir") + "/config/serializedTagMap.ser");
            InputStream buffer = new BufferedInputStream(file);
            ObjectInput input = new ObjectInputStream(buffer);

            //deserialize the Map
            availableTags = (List<String>) input.readObject();
            input.close();
        } catch (Exception ex) {
            System.out.println("Tag history empty!");
        }
    }

    /**
     * To string methods.
     */
    @Override
    public String toString() {
        return String.join(" ", availableTags);
    }
}
