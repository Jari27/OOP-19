package nl.rug.oop.introduction.helpers;

import nl.rug.oop.introduction.GameSession;
import nl.rug.oop.introduction.Main;

import java.io.*;
import java.util.Arrays;
import java.util.List;

public class SaveLoadQuitHelper {

    private static final String QUICKSAVE_FILENAME = "quicksave.ser";
    private static final String DIRECTORY = File.separatorChar + "saves" + File.separatorChar;

    /**
     * Asks the player for confirmation before exiting the game.
     * <p>
     * Note: if the players exits the game via this method, the game exits with exit code 69
     */
    public static void handleQuit() {
        System.out.println("Are you sure you want to exit?");
        String result = InputHelper.getString("Enter 'Y' to exit.");
        if (result.toLowerCase().equals("y")) {
            System.out.println("Thank you for playing. Bye bye...");
            System.exit(69);
        } else {
            System.out.println("One. More. Turn.");
        }
    }

    public static void save(GameSession session) {
        // get a save name
        String saveName = InputHelper.getString("Enter a name for your save:");
        // verify that we do not have a save called that already
        File directory = new File(DIRECTORY);
        if (directory.exists() && directory.isDirectory()) {
            // warn if overwriting existing save
            for (String existing : directory.list()) {
                if (existing.equals(saveName + ".ser")) {
                    String overwriteResponse = InputHelper.getString("There already exists a save called "
                            + saveName
                            + "\nAre you sure you want to continue? (Enter 'y' to continue)");
                    if (!overwriteResponse.toLowerCase().contains("y")) {
                        System.out.println("Your game was not saved.");
                        return;
                    }
                }
            }
        }
        // we can now save
        doSave(session, saveName + ".ser");
    }

    public static void quickSave(GameSession session) {
        doSave(session, QUICKSAVE_FILENAME);
    }

    private static void doSave(GameSession session, String filename) {
        // create the directory if it does not exist
        // (actually does not need the check, since mkdir returns false if the directory already exists)
        File directory = new File(DIRECTORY);
        if (!directory.exists()) {
            System.out.println("Created directory");
            directory.mkdir();
        }
        try {
            FileOutputStream fileOut = new FileOutputStream(DIRECTORY + filename);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(session);
            out.close();
            fileOut.close();
            System.out.println("Your data was successfully saved in " + DIRECTORY + filename);
        } catch (IOException i) {
            System.err.println("There was a problem saving your file. Detailed information printed below.");
            i.printStackTrace();
        }
    }

    public static void quickLoad() {
        if (canLoadFile(QUICKSAVE_FILENAME)) {
            GameSession session = doLoad(QUICKSAVE_FILENAME);
            Main.setSession(session);
        } else {
            System.out.println("No quicksave available.");
        }
    }

    private static boolean canLoadFile(String filename) {
        File dir = new File(DIRECTORY);
        if (dir.isDirectory()) { // also checks existence
            File saveFile = new File(DIRECTORY + filename);
            return saveFile.exists() && saveFile.canRead();
        }
        return false;
    }

    public static void load() {
        String filename = selectFileFromSaveFolder();
        if (filename != null) {
            GameSession session = doLoad(filename);
            Main.setSession(session);
        }
    }


    private static String selectFileFromSaveFolder() {
        File dir = new File(DIRECTORY);
        if (dir.isDirectory()) {
            String[] fileStrings = dir.list();
            if (fileStrings != null && fileStrings.length > 0) {
                for (int i = 0; i < fileStrings.length; i++) {
                    System.out.println(" (" + i + ") " + fileStrings[i]);
                }
                int input = InputHelper.getValidInputInt(fileStrings.length);
                String filename = fileStrings[input];
                if (filename.endsWith(".ser")) {
                    return filename;
                } else {
                    System.out.println("That is not a valid savefile");
                    return null;
                }

            } else {
                System.out.println("Could not read files or no savefiles available.");
                return null;
            }
        } else {
            System.out.println("No savefiles available.");
            return null;
        }
    }

    private static GameSession doLoad(String filename) {
        // create the directory if it does not exist
        GameSession session;
        try {
            FileInputStream fileIn = new FileInputStream(new File(DIRECTORY + filename));
            ObjectInputStream in = new ObjectInputStream(fileIn);
            session = (GameSession) in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Could not load your game");
            return null;
        }
        return session;
    }
}
