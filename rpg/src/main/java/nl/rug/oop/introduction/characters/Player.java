package nl.rug.oop.introduction.characters;

import nl.rug.oop.introduction.InputHelper;
import nl.rug.oop.introduction.Room;

public class Player {

  private Room currentLocation;
  private String name;

  public Player(String name, Room currentLocation) {
    this.name = name;
    this.currentLocation = currentLocation;
  }

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
