package nl.rug.oop.introduction.characters;

import nl.rug.oop.introduction.helpers.InputHelper;
import nl.rug.oop.introduction.objects.Room;

public class Player {

    private Room currentLocation;
    private String name;

    public Player(String name, Room currentLocation) {
        this.name = name;
        this.currentLocation = currentLocation;
    }

    /**
     * Asks the player his or her name and stores it.
     */
    public void askAndSetName() {
        this.name = InputHelper.getString("What is your name?");
    }

    public String getName() {
        return name;
    }

    public Room getLocation() {
        return currentLocation;
    }

    public void setLocation(Room newLocation) {
        currentLocation = newLocation;
    }
}
