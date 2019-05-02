package nl.rug.oop.introduction.interfaces;

import nl.rug.oop.introduction.characters.Player;

public interface Interactable {

    /**
     * Executes the interaction action for this object. This is completely object dependent and no
     * default implementation is provided.
     *
     * @param player the player object
     */
    void interact(Player player);
}
