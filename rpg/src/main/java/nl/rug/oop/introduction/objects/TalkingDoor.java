package nl.rug.oop.introduction.objects;

import nl.rug.oop.introduction.characters.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class TalkingDoor extends Door {

    private Random r = new Random();

    private final static String[] DEFAULT_WHISPERS = {
            "Swiggity swaggity swooty...\nI'm coming for da booty!",
            "Oh that feels nice, keep going...",
            "You like that??",
            "Bruh your hair looks a-ma-zing!",
            "Who you talking to?"
    };

    private List<String> whispers;

    public TalkingDoor(String description, boolean useDefaultWhispers) {
        super(description);
        whispers = new ArrayList<>();
        if (useDefaultWhispers) {
            whispers.addAll(Arrays.asList(DEFAULT_WHISPERS));
        }
    }

    @Override
    public void interact(Player player) {
        super.interact(player);
        if (whispers.size() > 0) {
            System.out.println("You hear a faint whispering voice. " +
                    "\nIt says: '" + whispers.get(r.nextInt(whispers.size())) + "'");
        }
    }

    /**
     * Returns the current <code>List</code> of possible whispers
     *
     * @return the list of whispers.
     */
    public List<String> getWhispers() {
        return whispers;
    }
}
