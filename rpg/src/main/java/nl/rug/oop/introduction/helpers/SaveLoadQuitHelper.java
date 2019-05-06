package nl.rug.oop.introduction.helpers;

import nl.rug.oop.introduction.GameSession;

import java.io.*;
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

    public static void quickLoad(GameSession session) {
        load(QUICKSAVE_FILENAME);
    }


    private static String selectFileFromSaveFolder(List<File> filelist) {

    }

    private static GameSession load(String filename) {
        // create the directory if it does not exist
        GameSession session;
        File directory = new File(DIRECTORY);
        if (!directory.exists()) {
            System.out.println("There is no file saved file, start a new game.");
            return null;
        }
        try {
            FileInputStream fileIn = new FileInputStream(DIRECTORY + filename);
            ObjectInputStream in = new ObjectInputStream(fileIn);
            session = (GameSession) in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Could not load your game");
            return null;
        }
        return session;
    }

}
