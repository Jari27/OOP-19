package nl.rug.oop.introduction;

import nl.rug.oop.introduction.characters.*;
import nl.rug.oop.introduction.helpers.InputHelper;
import nl.rug.oop.introduction.helpers.SaveLoadQuitHelper;
import nl.rug.oop.introduction.objects.Door;
import nl.rug.oop.introduction.objects.Room;
import nl.rug.oop.introduction.objects.TalkingDoor;

import java.sql.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {


    private static GameSession session;


    public static void main(String[] args) {
        init();
        while (true) {
            session.doRound();
        }
    }

    private static void init() {
        List<Action> actions = new ArrayList<>();
        actions.addAll(Arrays.asList(Action.NEW, Action.LOAD, Action.QUICKLOAD));
        Action a = InputHelper.getAction(actions);
        switch (a) {
            case NEW:
                startNewGame();
                break;
            case LOAD:
                SaveLoadQuitHelper.load();
                break;
            case QUICKLOAD:
                SaveLoadQuitHelper.quickLoad();
                break;
            default:
                System.err.println("Invalid choice...");
        }
    }


    /**
     * Starts a new game with some default rooms and npcs.
     */
    private static void startNewGame() {
        System.out.println("Starting a new game!");
        // setup a few rooms
        Room r1 = new Room("an old, wooden room");
        Room r2 = new Room("a cheerful, yellow room");
        Room r3 = new Room(
                "A grassy field... wait... It's a room painted to look like a grassy field!");

        List<Room> map = new ArrayList<>();
        map.addAll(Arrays.asList(r1, r2, r3));

        // setup some doors
        Door d12 = new Door("a black door");
        d12.setConnectedRooms(r1, r2);
        r1.getContents().add(d12);
        r2.getContents().add(d12);
        Door d23 = new Door("a green door");
        d23.setConnectedRooms(r2, r3);
        r2.getContents().add(d23);
        r3.getContents().add(d23);
        TalkingDoor td12 = new TalkingDoor("a door with a big red mouth painted on", true);
        td12.setConnectedRooms(r1, r2);
        r1.getContents().add(td12);
        r2.getContents().add(td12);

        // add some npcs
        NPC npc1 = new StrangeNPC("Warner", "An econometrics student!!");
        NPC npc2 = new FriendlyNPC("Michiel", "A big fat gypsy man");
        NPC npc3 = new AggressiveNPC("Frans", "A small and ugly senior");
        r1.getContents().add(npc2);
        r2.getContents().add(npc3);
        r3.getContents().add(npc1);

        // setup the player
        Player player = new Player("John Doe", r1);
        player.askAndSetName();
        System.out.println("Welcome, " + player.getName());

        // start the session
        setSession(new GameSession(player, map));

    }

    /**
     * Sets the game session
     *
     * @param session the (loaded) session
     */
    public static void setSession(GameSession session) {
        Main.session = session;
    }
}
