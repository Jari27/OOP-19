package nl.rug.oop.introduction;

import nl.rug.oop.introduction.characters.Player;

public class Door extends GameObject implements Interactable {

  private Room firstRoom, secondRoom;

  public Door(String description) {
    super(description);
  }

  @Override
  public void doAction(Action a, Player player) {
    switch (a) {
      case DO_NOTHING:
        System.out.println("You do nothing. The door stays closed...");
        break;
      case INTERACT:
        interact(player);
        break;
      case NEVERMIND:
        System.out.println("You back off...");
        break;
      case QUIT:
        SaveLoadQuitHandler.handleQuit();
        break;
      default:
        System.err.println("Invalid action...");
        break;
    }
  }

  public void setConnectedRooms(Room r1, Room r2) {
    if (r1 != r2) {
      this.firstRoom = r1;
      this.secondRoom = r2;
    } else {
      System.err.println(r1.toString() + "is the same as " + r2.toString());
    }
  }

  @Override
  public void interact(Player player) {
    System.out.println("You move through the door.");
    if (player.getLocation() == firstRoom) {
      player.setLocation(secondRoom);
    } else if (player.getLocation() == secondRoom) {
      player.setLocation(firstRoom);
    } else {
      System.err.println("You cannot use this door!");
    }
  }
}
