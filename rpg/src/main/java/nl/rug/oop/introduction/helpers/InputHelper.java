package nl.rug.oop.introduction.helpers;

import nl.rug.oop.introduction.Action;
import nl.rug.oop.introduction.characters.NPC;
import nl.rug.oop.introduction.objects.GameObject;

import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class InputHelper {

    private static Scanner scanner = new Scanner(System.in);

    /**
     * Asks the user to select their next action from the given set of valid actions.
     *
     * @param validActions the list of valid actions
     * @return the selected action
     */
    public static Action getAction(List<Action> validActions) {
        if (validActions.size() == 0) {
            System.err.println("Invalid number of actions!");
            return null;
        }
        displayAction(validActions);
        int actionInt = getValidInputInt(validActions.size());
        return validActions.get(actionInt);
    }

    /**
     * Asks the user to select a GameObject from the list of given objects. Ensures that the user
     * selects a valid object. Prints an error if the number of object to interact with is 0 and
     * returns null.
     *
     * @param objects the selectable objects
     * @return the selected object or null if there are no valid objects.
     */
    public static GameObject getObject(List<GameObject> objects) {
        if (objects.size() == 0) {
            System.err.println("Invalid number of objects!");
            return null;
        }
        displayObjects(objects);
        int objectInt = getValidInputInt(objects.size());
        return objects.get(objectInt);
    }

    private static void displayObjects(List<GameObject> objects) {
        System.out.println("You see the following: ");
        for (int i = 0; i < objects.size(); i++) {
            GameObject object = objects.get(i);
            if (object instanceof NPC) {
                NPC npc = (NPC) object;
                System.out.println(" (" + i + ") " + npc.getName());
            } else {
                System.out.println(" (" + i + ") " + objects.get(i).getDescription());
            }
        }
    }

    /**
     * Displays what number the user needs to enter to select a certain action from the list of valid
     * actions
     *
     * @param validActions the list of valid actions
     */
    private static void displayAction(List<Action> validActions) {
        System.out.println("What action do you take?");
        Collections.sort(validActions);
        for (int i = 0; i < validActions.size(); i++) {
            System.out.println(" (" + i + ") " + validActions.get(i).toHumanString());
        }
    }

    /**
     * Retrieves a valid action as integer from the user (i.e. a number between 0 and
     * <code>numOptions</code>)
     *
     * @param numOptions the number of action the user can select from
     * @return the selected action
     */
    private static int getValidInputInt(int numOptions) {
        String result;
        int resultInt = -1;
        boolean hasValidInput = true; // to prevent the error message from showing up on the first round
        do {
            if (!hasValidInput) {
                System.out.println("That's not a valid option! Try again...");
            }
            result = scanner.nextLine();
            try {
                resultInt = Integer.parseInt(result);
                hasValidInput = resultInt >= 0 && resultInt < numOptions;
            } catch (NumberFormatException e) {
                hasValidInput = false;
            }
        } while (!hasValidInput);

        return resultInt;
    }

    /**
     * Asks the player to input a string with an included question <code>s</code>. No validation is performed.
     *
     * @return the string the player wrote down
     */
    public static String getString(String s) {
        System.out.println(s);
        return scanner.nextLine();
    }

    /**
     * Asks the player to input a string without an associated question. No validation is performed.
     *
     * @return the string the player wrote down
     */
    public static String getString() {
        return scanner.nextLine();
    }
}
