package nl.rug.oop.introduction.characters;

import nl.rug.oop.introduction.*;

public class NPC extends GameObject implements Interactable, Inspectable {

    private String name;

    public NPC(String name, String description) {
        super(description);
        this.name = name;
    }

    @Override
    public void inspect(Player player) {
        System.out.println("You look at " + getName() + ". He looks like " + getDescription().toLowerCase());
        System.out.println("He looks back at you and says: 'Stop looking at me!'");
    }

    @Override
    public void interact(Player player) {
        System.out.println("You interact with " + getName());
        System.out.println(getName() + " says: 'Hello, how are you??'");
        String answer = InputHelper.getString();
        if (answer.toLowerCase().contains("good") || answer.toLowerCase().contains("well")) {
            System.out.println(getName() + " says: 'Very nice!'");
        } else if (answer.toLowerCase().contains("bad") || answer.toLowerCase().contains("terrible")) {
            System.out.println(getName() + " says: 'Oh, I'm sad to hear that.'");
        } else {
            System.out.println(getName() + " says: 'I don't understand you!'");
        }
        System.out.println(getName() + " says: 'It was nice talking to you, goodbye!'");
    }

    @Override
    public void doAction(Action a, Player player) {
        switch (a) {
            case DO_NOTHING:
                System.out.println("You do nothing. "  + getName() + " stares at you.");
                break;
            case INSPECT:
                inspect(player);
                break;
            case INTERACT:
                interact(player);
                break;
            case QUIT:
                SaveLoadQuitHandler.handleQuit();
                break;

            default:
                System.err.println("This is not a valid option!!");
                break;
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
