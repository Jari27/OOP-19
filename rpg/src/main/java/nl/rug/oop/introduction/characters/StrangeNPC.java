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
        System.out.println(getName() + " asks: can you help me find birds?");
        String answer = InputHelper.getString();
        if(answer.toLowerCase().contains("no") || answer.toLowerCase().contains("not")){
            System.out.println(getName() + " yells: why am I such an idiot?!");
        } else{
            System.out.println(getName() + " does not respond");
        }
        System.out.println("You move away slowly..");
    }
}
