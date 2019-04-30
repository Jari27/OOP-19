package nl.rug.oop.introduction.characters;

import nl.rug.oop.introduction.*;
import nl.rug.oop.introduction.helpers.InputHelper;
import nl.rug.oop.introduction.helpers.SaveLoadQuitHelper;
import nl.rug.oop.introduction.interfaces.Inspectable;
import nl.rug.oop.introduction.interfaces.Interactable;
import nl.rug.oop.introduction.objects.GameObject;

public class NPC extends GameObject implements Interactable, Inspectable {

    private String name;

    public NPC(String name, String description) {
        super(description);
        this.name = name;
    }

    @Override
    public void inspect(Player player) {
        System.out.println("You look at " + getName() + ". He looks like " + getDescription().toLowerCase());
    }

    @Override
    public void interact(Player player) {
        System.out.println("You interact with " + getName());
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
                SaveLoadQuitHelper.handleQuit();
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
