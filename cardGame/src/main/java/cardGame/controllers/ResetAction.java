package cardGame.controllers;

import cardGame.game.Game;

import javax.swing.*;
import java.awt.event.ActionEvent;

/**
 *Action that shuffles all cards and get them back in the deck.
 */
class ResetAction extends AbstractAction {

    private Game game;

    /**
     *Creates the action
     */

    public ResetAction(Game game) {
        super("Reset game");
        this.game = game;
    }

    /**
    //Reset the game
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        game.reset();
    }

}
