package nl.rug.oop.introduction.objects;

import nl.rug.oop.introduction.Action;
import nl.rug.oop.introduction.interfaces.Interactable;
import nl.rug.oop.introduction.characters.Player;
import nl.rug.oop.introduction.helpers.SaveLoadQuitHelper;

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
        SaveLoadQuitHelper.handleQuit();
        break;
      default:
        System.err.println("Invalid action...");
        break;
    }
  }

  /**
   * Sets the rooms that are connected by this door. Does not automatically add the door to the
   * rooms. Rooms must be different. Prints an error to to System.err if they are not.
   *
   * @param r1 the first room
   * @param r2 the second room
   */
  public void setConnectedRooms(Room r1, Room r2) {
    if (r1 != r2) {
      this.firstRoom = r1;
      this.secondRoom = r2;
    } else {
      System.err.println(r1.toString() + "is the same as " + r2.toString());
    }
  }

  /**
   * Moves the player through the door to the other room. Note: doors are NOT automatically added to
   * all connected rooms.
   *
   * @param player the player object
   */
  @Override
  public void interact(Player player) {
    if (firstRoom == null || secondRoom == null) {
      System.err.println("Invalid rooms!");
    }
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
