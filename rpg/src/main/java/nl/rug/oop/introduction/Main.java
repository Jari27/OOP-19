package nl.rug.oop.introduction;

import nl.rug.oop.introduction.characters.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {

    private static List<Action> defaultActions;

    private static Player player;

    public static void main(String[] args) {
        System.out.println("Initializing game...");
        init();
        while (true) {
            System.out.println("You see: " + player.getLocation().getDescription().toLowerCase());
            Action a = InputHelper.getAction(defaultActions);
            doAction(a);
        }
    }

    private static void doAction(Action a) {
        switch (a) {
            case DO_NOTHING:
                System.out.println("You do nothing. Nothing happens...");
                break;
            case INSPECT:
                player.getLocation().inspect();
                break;
            case INTERACT:
                player.getLocation().interact();
                break;
            case NEVERMIND:
                System.out.println("Nevermind what??");
                break;
            case QUIT:
                handleQuit();
                break;
        }
    }

    private static void handleQuit() {
        System.out.println("Are you use you want to exit?");
        String result = InputHelper.getString("Enter 'Y' to exit.");
        if (result.toLowerCase().equals("y")) {
            System.out.println("Thank you for playing. Bye bye...");
            System.exit(69);
        } else {
            System.out.println("One. More. Turn.");
        }
    }

    private static void init() {
        // setup default actions
        defaultActions = new ArrayList<>();
        defaultActions.addAll(Arrays.asList(Action.values()));
        defaultActions.remove(Action.NEVERMIND);
        // setup a few rooms
        Room r1 = new Room("an old, wooden room");
        Room r2 = new Room("a cheerful, yellow room");
        // setup the player
        player = new Player("John Doe", r1);
        player.askAndSetName();
        System.out.println("Welcome, " + player.getName());

    }
}
