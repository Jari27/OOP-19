package nl.rug.oop.introduction;

public class Player {

    private double hitpoints = 100;
    private String name;

    public Player(String name) {
        this.name = name;
    }

    public double getHitpoints() {
        return hitpoints;
    }

    public void setHitpoints(double hitpoints) {
        this.hitpoints = hitpoints;
    }
}
