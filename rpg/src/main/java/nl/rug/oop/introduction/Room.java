package nl.rug.oop.introduction;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Room extends GameObject implements Inspectable, Interactable {

    String description;

    List<GameObject> contents = new ArrayList<>();

    public Room(String description, GameObject... contents) {
        super(description);
        this.contents.addAll(Arrays.asList(contents));
    }

    public String getDescription() {
        return description;
    }

    @Override
    public void inspect() {
        System.out.println("You are in  " + getDescription().toLowerCase() + ".");
        if (this.contents.size() > 0) {
            GameObject object = InputHelper.getObject(contents);
            // TODO handle object interaction
        }
    }

    @Override
    public void interact() {
        System.out.println("You jump up and down, but there is no reaction.");
    }
}
