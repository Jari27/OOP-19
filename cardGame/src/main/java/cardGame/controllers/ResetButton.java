package cardGame.controllers;

import cardGame.game.Game;

import javax.swing.*;

/**
 * Button that resets the game and shuffles all cards back into the deck
 */
class ResetButton extends JButton {

    /**
     * Create reset button
     */
    public ResetButton(Game game) {
        super(new ResetAction(game));
        setToolTipText("Reset the game!");
    }
}
