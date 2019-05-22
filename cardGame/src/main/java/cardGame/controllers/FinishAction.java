package cardGame.controllers;

import cardGame.game.Game;
import cardGame.models.Card;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.util.Observable;
import java.util.Observer;

/**
 * Represents action of finish game
 */
class FinishAction extends AbstractAction implements Observer {

    private Game game;

    /**
     * Create action for a finished game
     */
    public FinishAction(Game game) {
        super("Finish the game");
        this.game = game;
        game.addObserver(this);
    }


    /**
     * Checks whether game is finished
     */
    private void fixEnabled() {
        if (game.isFinished()) {
            setEnabled(false);
        } else {
            setEnabled(true);
        }
    }

    /**
     * Force picking an arbitrary card until the game is finished.
     * Note that this is always valid, even when the card cannot be selected by the player.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        while (!game.isFinished()) {
            game.pickCard(Card.Face.ACE);
        }
    }

    /**
     * Update whether the game is finished
     */
    @Override
    public void update(Observable observable, Object o) {
        fixEnabled();
    }
}
