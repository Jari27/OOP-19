package nl.rug.oop.introduction.helpers;

import nl.rug.oop.introduction.GameSession;
import nl.rug.oop.introduction.Main;

import java.io.*;

public class SaveLoadQuitHelper {

    private static final String QUICKSAVE_FILENAME = "quicksave.ser";
    private static final String DIRECTORY = "saves" + File.separatorChar;

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

    /**
     * Saves the game session by asking the player for a savename. The save is stored inside <code>DIRECTORY</code>
     *
     * @param session the session to save
     */
    public static void save(GameSession session) {
        // get a save name
        String saveName = InputHelper.getString("Enter a name for your save:");
        // verify that we do not have a save called that already
        File directory = new File(DIRECTORY);
        if (directory.isDirectory()) {
            // warn if overwriting existing save
            String[] filenames = directory.list();
            if (filenames == null) {
                System.out.println("There was a problem saving your game.");
                return;
            }
            for (String existing : filenames) {
                if (existing.equals(saveName + ".ser")) {
                    String overwriteResponse = InputHelper.getString("There already exists a save called "
                            + saveName
                            + "\nAre you sure you want to continue? (Enter 'y' to continue)");
                    if (!overwriteResponse.toLowerCase().equals("y")) {
                        System.out.println("Your game was not saved.");
                        return;
                    }
                }
            }
        }
        // we can now save
        doSave(session, saveName + ".ser");
    }

    /**
     * Quickly saves the game as <code>QUICKSAVE_FILENAME</code>.ser
     */
    public static void quickSave(GameSession session) {
        doSave(session, QUICKSAVE_FILENAME);
    }

    /**
     * Displays a list of saved files, prompts the user for one and tries to load it.
     */
    public static void load() {
        String filename = selectFileFromSaveFolder();
        if (filename != null) {
            GameSession session = doLoad(filename);
            Main.setSession(session);
            System.out.println("Your game was successfully loaded.");
        } else {
            System.out.println("There was a problem loading your game.");
        }
    }

    /**
     * Quickly loads the save file called <code>QUICKSAVE_FILENAME</code>, if it exists.
     */
    public static void quickLoad() {
        if (canLoadFile(QUICKSAVE_FILENAME)) {
            GameSession session = doLoad(QUICKSAVE_FILENAME);
            Main.setSession(session);
            System.out.println("Quicksave restored.");
        } else {
            System.out.println("No quicksave available.");
        }
    }

    /**
     * Convenience method that handles the actual saving. The extension should be part of the filename (e.g.
     * <code>quicksave.ser</code>, not <code>quicksave</code>), since it is not automatically appended.
     *
     * @param session  the session to save
     * @param filename the filename under which to save the session.
     */
    private static void doSave(GameSession session, String filename) {
        // create the directory if it does not exist
        // (actually does not need the check, since mkdir returns false if the directory already exists)
        File directory = new File(DIRECTORY);
        if (!directory.exists()) {
            if (directory.mkdirs()) {
                System.out.println("Created a directory to store your savefiles.");
            }
        }
        try {
            FileOutputStream fileOut = new FileOutputStream(DIRECTORY + filename);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(session);
            out.close();
            fileOut.close();
            System.out.println("Your data was successfully saved in " + DIRECTORY + filename);
        } catch (IOException i) {
            System.out.println("There was a problem saving your game. Your game is *not* saved!");
        }
    }

    /**
     * Helper method that handles the loading of files in <code>DIRECTORY</code>
     *
     * @param filename the name (inc. extension) of the file to be loaded
     * @return the session retrieved from the savefile
     */
    private static GameSession doLoad(String filename) {
        // create the directory if it does not exist
        GameSession session;
        try {
            FileInputStream fileIn = new FileInputStream(new File(DIRECTORY + filename));
            ObjectInputStream in = new ObjectInputStream(fileIn);
            session = (GameSession) in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Could not load your game. Is this a valid savefile?");
            return null;
        }
        return session;
    }

    /**
     * Verifies that a file called <code>filename</code> can be loaded,
     * i.e. exists in <code>DIRECTORY</code> and that we can read it.
     *
     * @param filename the filename to check
     * @return true if the file exists and can be read
     */
    private static boolean canLoadFile(String filename) {
        File dir = new File(DIRECTORY);
        if (dir.isDirectory()) { // also checks existence
            File saveFile = new File(DIRECTORY + filename);
            return saveFile.exists() && saveFile.canRead();
        }
        return false;
    }

    /**
     * Helper method that allows the user to select a file from <code>DIRECTORY</code>
     * by choosing a number associated with a file. The method verifies that the file is actually a savefile
     * (but not that it is a valid savefile for this program)
     *
     * @return the filename of the selected file
     */
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
}
