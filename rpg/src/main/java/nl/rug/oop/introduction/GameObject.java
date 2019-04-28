package nl.rug.oop.introduction;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import nl.rug.oop.introduction.characters.Player;

public abstract class GameObject {

  protected List<Action> defaultActions;
  private String description;

  public GameObject(String description) {
    this.description = description;
    this.defaultActions = new ArrayList<>();
    defaultActions.add(Action.DO_NOTHING);
    defaultActions.add(Action.QUIT);
    if (this instanceof Interactable) {
      defaultActions.add(Action.INTERACT);
    }
    if (this instanceof Inspectable) {
      defaultActions.add(Action.INSPECT);
    }

    Collections.sort(defaultActions);
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public List<Action> getDefaultActions() {
    return defaultActions;
  }

  public void setDefaultActions(List<Action> defaultActions) {
    this.defaultActions = defaultActions;
  }

  public abstract void doAction(Action a, Player player);
}
