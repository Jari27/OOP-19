package nl.rug.oop.introduction;

import nl.rug.oop.introduction.characters.Player;

public class Main {

//    private static List<Action> defaultActions;

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
    // setup default actions
//        defaultActions = new ArrayList<>();
//        defaultActions.addAll(Arrays.asList(Action.values()));
//        defaultActions.remove(Action.NEVERMIND);
    // setup a few rooms
    Room r1 = new Room("an old, wooden room");
    Room r2 = new Room("a cheerful, yellow room");
    Door d12 = new Door("a black door");
    d12.setConnectedRooms(r1, r2);
    r1.contents.add(d12);
    r2.contents.add(d12);
    // setup the player
    player = new Player("John Doe", r1);
    player.askAndSetName();
    System.out.println("Welcome, " + player.getName());

  }
}
