package nl.rug.oop.introduction.objects;

import nl.rug.oop.introduction.Action;
import nl.rug.oop.introduction.Main;
import nl.rug.oop.introduction.characters.NPC;
import nl.rug.oop.introduction.characters.Player;
import nl.rug.oop.introduction.helpers.InputHelper;
import nl.rug.oop.introduction.helpers.SaveLoadQuitHelper;
import nl.rug.oop.introduction.interfaces.Inspectable;
import nl.rug.oop.introduction.interfaces.Interactable;
import nl.rug.oop.introduction.interfaces.Saveable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Room extends GameObject implements Inspectable, Interactable, Saveable {

    private List<GameObject> contents = new ArrayList<>();

    public Room(String description, GameObject... contents) {
        super(description);
        this.contents.addAll(Arrays.asList(contents));
    }

    /**
     * Inspects the contents of the room and allows the player to select an object for further
     * interaction. Further (inter)actions are handled by the selected object.
     *
     * @param player the player object
     */
    @Override
    public void inspect(Player player) {
        System.out.println("You are in " + getDescription().toLowerCase() + ".");
        if (this.contents.size() > 0) {

            GameObject object = InputHelper.getObject(contents);
            // display name for NPCs, descriptions for other objects
            String objectString = object.getDescription();
            if (object instanceof NPC) {
                NPC npc = (NPC) object;
                objectString = npc.getName();
            }
            System.out.println("You have selected: " + objectString);
            Action a = InputHelper.getAction(object.getDefaultActions());
            object.doAction(a, player);

        }
    }

    @Override
    public void interact(Player player) {
        System.out.println("You jump up and down, but there is no reaction.");
    }

    @Override
    public void doAction(Action a, Player player) {
        switch (a) {
            case DO_NOTHING:
            case NEVERMIND:
                System.out.println("You do nothing. Nothing happens...");
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
            case SAVE:
                SaveLoadQuitHelper.save(Main.getSession());
                break;
            case LOAD:
                SaveLoadQuitHelper.load();
                break;
            case QUICKSAVE:
                SaveLoadQuitHelper.quickSave(Main.getSession());
                break;
            case QUICKLOAD:
                SaveLoadQuitHelper.quickLoad();
                break;
        }
    }

    public List<GameObject> getContents() {
        return contents;
    }
}
