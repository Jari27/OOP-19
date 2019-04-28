package nl.rug.oop.introduction;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import nl.rug.oop.introduction.characters.Player;

public class Room extends GameObject implements Inspectable, Interactable {

  List<GameObject> contents = new ArrayList<>();

  public Room(String description, GameObject... contents) {
    super(description);
    this.contents.addAll(Arrays.asList(contents));
  }

  @Override
  public void inspect(Player player) {
//    System.out.println("You are in " + getDescription().toLowerCase() + ".");
    if (this.contents.size() > 0) {

      GameObject object = InputHelper.getObject(contents);
      System.out.println("You have selected: " + object.getDescription());
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
        SaveLoadQuitHandler.handleQuit();
        break;
    }
  }
}
