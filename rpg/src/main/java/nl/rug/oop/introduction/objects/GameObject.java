package nl.rug.oop.introduction.objects;

import java.io.Serializable;
import nl.rug.oop.introduction.Action;
import nl.rug.oop.introduction.characters.Player;
import nl.rug.oop.introduction.interfaces.Inspectable;
import nl.rug.oop.introduction.interfaces.Interactable;
import nl.rug.oop.introduction.interfaces.Saveable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * A generic class that describes objects that can interacted with in the game. This includes (for
 * example) locations and NPCs.
 */
public abstract class GameObject implements Serializable {

    private static final long serialVersionUID = 1L;

    private List<Action> defaultActions;
    private String description;

    protected GameObject(String description) {
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
        if (this instanceof Saveable) {
            defaultActions.add(Action.QUICKSAVE);
            defaultActions.add(Action.SAVE);
            defaultActions.add(Action.LOAD);
            defaultActions.add(Action.QUICKLOAD);
        }

        Collections.sort(defaultActions);
    }

    /**
     * Returns the description associated with this object
     *
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the description associated with this object
     *
     * @param description the new description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Returns the list of default actions associated with this object
     *
     * @return the list of default actions
     */
    public List<Action> getDefaultActions() {
        return defaultActions;
    }

    /**
     * Executes the selected <code>Action</code>. Developers have to override this with a valid
     * implementation of all available actions.
     *
     * @param a      the action to execute
     * @param player the player object
     */
    protected abstract void doAction(Action a, Player player);
}
