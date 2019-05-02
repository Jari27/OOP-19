package nl.rug.oop.introduction.characters;

import nl.rug.oop.introduction.Action;
import nl.rug.oop.introduction.helpers.SaveLoadQuitHelper;
import nl.rug.oop.introduction.interfaces.Inspectable;
import nl.rug.oop.introduction.interfaces.Interactable;
import nl.rug.oop.introduction.objects.GameObject;

public abstract class NPC extends GameObject implements Interactable, Inspectable {

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
                System.out.println("You do nothing. " + getName() + " stares at you.");
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

    /**
     * Finds out the name of this NPC
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of this NPC
     *
     * @param name the new name for this npc
     */
    public void setName(String name) {
        this.name = name;
    }
}
