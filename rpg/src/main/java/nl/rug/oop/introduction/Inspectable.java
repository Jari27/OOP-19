package nl.rug.oop.introduction;

import nl.rug.oop.introduction.characters.Player;

public interface Inspectable {

  /**
   * Executes the inspection action for this object. This is completely object dependent and no
   * default implementation is provided.
   *
   * @param player the player object
   */
  void inspect(Player player);
}
