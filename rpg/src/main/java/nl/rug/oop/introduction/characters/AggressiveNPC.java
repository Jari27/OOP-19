package nl.rug.oop.introduction.characters;

import nl.rug.oop.introduction.helpers.InputHelper;

public class AggressiveNPC extends NPC {
    public AggressiveNPC(String name, String description) {
        super(name, description);
    }

    @Override
    public void inspect(Player player){
        super.inspect(player);
        System.out.println("He looks back at you and says: 'Stop looking at me or I will punch you'");
    }
    @Override
    public void interact(Player player){
        super.interact(player);
        System.out.println(getName() + " says: What do you want from me?");
        String answer = InputHelper.getString();
        if(answer.toLowerCase().contains("nothing") || answer.toLowerCase().contains("nevermind")){
            System.out.println(getName() + " yells: Ok, move your ass away from me");
        } else{
            System.out.println(getName() + " screams: I have my own problems, stop bothering me with yours");
        }
    }
}
