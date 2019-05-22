package cardGame.controllers;

import cardGame.game.Game;

import javax.swing.*;

/**
 * Panel with the buttons reset and finish is created.
 */
public class ButtonBar extends JMenuBar {

    /**
     * Create the panel with buttons
     */
    public ButtonBar(Game game) {
        ResetButton reset = new ResetButton(game);
        FinishButton finish = new FinishButton(game);
        this.add(reset);
        this.add(finish);
    }
}
