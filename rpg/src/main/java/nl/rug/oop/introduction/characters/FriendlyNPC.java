package nl.rug.oop.introduction.characters;

import nl.rug.oop.introduction.helpers.InputHelper;

public class FriendlyNPC extends NPC {
    public FriendlyNPC(String name, String description) {
        super(name, description);
    }

    @Override
    public void inspect(Player player) {
        super.inspect(player);
        System.out.println(getName() + " says: 'Hey " + player.getName() + " come closer!'");
        System.out.println("You wonder how he knows your name...");
    }

    @Override
    public void interact(Player player) {
        super.interact(player);
        System.out.println(getName() + " says: 'Hello, how are you??'");
        String answer = InputHelper.getString();
        if (answer.toLowerCase().contains("good") || answer.toLowerCase().contains("well") || answer.toLowerCase().contains("fine")) {
            System.out.println(getName() + " says: 'Very nice!'");
        } else if (answer.toLowerCase().contains("bad") || answer.toLowerCase().contains("terrible")) {
            System.out.println(getName() + " says: 'Oh, I'm sad to hear that.'");
        } else {
            System.out.println(getName() + " says: 'I don't understand you!'");
        }
        System.out.println(getName() + " says: 'It was nice talking to you, goodbye!'");
    }
}
