package nl.rug.oop.introduction;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import nl.rug.oop.introduction.characters.Player;

/**
 * A generic class that describes objects that can interacted with in the game. This includes (for
 * example) locations and NPCs.
 */
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

  /**
   * Executes the selected <code>Action</code>. Developers have to override this with a valid
   * implementation of all available actions.
   *
   * @param a the action to execute
   * @param player the player object
   */
  public abstract void doAction(Action a, Player player);
}
