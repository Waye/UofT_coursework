package model;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.*;

/**
 * This is Logging class
 * The logging class handle image save problem
 * so that all information will be store after application exit.
 */
public class Logging implements Serializable {
    /**
     * This is for compiler to serialize/deserialize the file.
     */
    private static final long serialVersionUID = 123456;
    /**
     * Logging system for images.
     */
    private static final Logger logger =
            Logger.getLogger(Images.class.getName());
    /**
     * The handler of console.
     */
    private static final Handler consoleHandler = new ConsoleHandler();
    /**
     * The handler switch.
     */
    private static boolean handlerSwitch = true;

    /**
     * The constructor of logging.
     */
    Logging() {
        logger.setLevel(Level.ALL);

    }

    /**
     * set log, can save all necessary file after application closed.
     */
    void setLog() {
        if (handlerSwitch) {
            consoleHandler.setLevel(Level.ALL);
            logger.addHandler(consoleHandler);
            handlerSwitch = false;
            try {
                FileHandler fileHandler = new FileHandler(System.getProperty("user.dir") + "/config/NamingLogFile.log");
                SimpleFormatter formatter = new SimpleFormatter();
                fileHandler.setFormatter(formatter);
                logger.addHandler(fileHandler);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            // Write the log to file

        }
    }

    /**
     * Logs the renaming actions
     *
     * @param oldName the old name of the file
     * @param newName the new name of the file
     */
    public static void recordLogInfo(String oldName, String newName) {
        // Log the rename.
        String timeStamp = new SimpleDateFormat().format(new Date());
        logger.log(Level.FINE, timeStamp + " Old name: " + oldName + " New name: " + newName);
    }
}
