package nl.rug.oop.introduction;

public class GameSession {

    Player currentPlayer;
    Location currentLocation;

    public GameSession(Player currentPlayer, Location currentLocation) {
        this.currentPlayer = currentPlayer;
        this.currentLocation = currentLocation;
    }
}
