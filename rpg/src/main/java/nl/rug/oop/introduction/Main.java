package nl.rug.oop.introduction;

import nl.rug.oop.introduction.characters.NPC;
import nl.rug.oop.introduction.characters.Player;
import nl.rug.oop.introduction.helpers.InputHelper;
import nl.rug.oop.introduction.objects.Door;
import nl.rug.oop.introduction.objects.Room;

public class Main {

  private static Player player;

  public static void main(String[] args) {
    System.out.println("Initializing game...");
    init();
    while (true) {
      System.out.println("You are in " + player.getLocation().getDescription().toLowerCase());
      Action a = InputHelper.getAction(player.getLocation().getDefaultActions());
      player.getLocation().doAction(a, player);
    }
  }

  private static void init() {
    // setup a few rooms and doors
    Room r1 = new Room("an old, wooden room");
    Room r2 = new Room("a cheerful, yellow room");
    Room r3 = new Room(
        "A grassy field... wait... It's a room painted to look like a grassy field!");
    Door d12 = new Door("a black door");
    d12.setConnectedRooms(r1, r2);
    r1.getContents().add(d12);
    r2.getContents().add(d12);
    Door d23 = new Door("a green door");
    d23.setConnectedRooms(r2, r3);
    r2.getContents().add(d23);
    r3.getContents().add(d23);

    NPC npc1 = new NPC("Warner", "An econometrics student!!");
    r2.getContents().add(npc1);

    // setup the player
    player = new Player("John Doe", r1);
    player.askAndSetName();
    System.out.println("Welcome, " + player.getName());

  }
}
