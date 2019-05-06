package nl.rug.oop.introduction;

public enum Action {
    DO_NOTHING("Do nothing"),
    INSPECT("Take a look"),
    INTERACT("Interact"),
    NEVERMIND("Nevermind"),
    NEW("Start a new game"),
    SAVE("Save a game"),
    LOAD("Load a game"),
    QUICKSAVE("Quicksave"),
    QUICKLOAD("Quickload"),
    QUIT("Quit game");

    // TODO add new actions when applicable

    private String humanString;

    Action(String humanString) {
        this.humanString = humanString;
    }

    /**
     * Proves a human readable String of the action.
     *
     * @return the human readable String
     */
    public String toHumanString() {
        return this.humanString;
    }
}
