package nl.rug.oop.introduction;

import nl.rug.oop.introduction.characters.Player;
import nl.rug.oop.introduction.helpers.InputHelper;
import nl.rug.oop.introduction.objects.Room;

import java.io.Serializable;
import java.util.List;

public class GameSession implements Serializable {
    private Player player;
    private List<Room> map;

    public GameSession(Player player, List<Room> map) {
        this.player = player;
        this.map = map;
    }

    public void doRound() {
        System.out.println("You are in " + player.getLocation().getDescription().toLowerCase());
        Action a = InputHelper.getAction(player.getLocation().getDefaultActions());
        player.getLocation().doAction(a, player);
    }
}
