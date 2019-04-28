package nl.rug.oop.introduction;

public enum Action {
  DO_NOTHING("Do nothing"),
  INSPECT("Look around"),
  INTERACT("Interact"),
  NEVERMIND("Nevermind"),
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
