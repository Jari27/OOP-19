package nl.rug.oop.introduction.characters;

import nl.rug.oop.introduction.helpers.InputHelper;

public class StrangeNPC extends NPC{

    public StrangeNPC(String name, String description) {
        super(name, description);
    }

    @Override
    public void inspect(Player player){
        super.inspect(player);
        System.out.println(getName() + " looks at the ceiling and mumbles: 'I can not see any birds'");
    }
    @Override
    public void interact(Player player){
        super.interact(player);
        System.out.println(getName() + " asks: can you help me find the birds?");
        String answer = InputHelper.getString();
        if(answer.toLowerCase().contains("nothing") || answer.toLowerCase().contains("nevermind")){
            System.out.println(getName() + " yells: Ok, move your ass away from me");
        } else{
            System.out.println(getName() + " screams: I have my own problems, stop bothering me with yours");
        }
    }
}
