package cardGame.controllers;

import cardGame.game.Game;
import cardGame.models.Card;
import cardGame.models.FaceDiscardPile;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.util.Observable;
import java.util.Observer;

/**
 * Represents action to pick a card
 */
class CardAction extends AbstractAction implements Observer {

    private FaceDiscardPile associatedPile;
    private Game game;
    private Card.Face face;

    /**
     * Create action for a card to move to associated discard pile
     */
    public CardAction(Card.Face face, Game game) {
        super();
        this.game = game;
        this.face = face;
        game.addObserver(this);

        this.associatedPile = game.getDiscardPiles().get(face.ordinal());
        fixEnabled();
    }

    /**
     * If a pile is full, set enabled is set false
     */
    private void fixEnabled() {
        if (associatedPile.isFull()) {
            setEnabled(false);
        } else {
            setEnabled(true);
        }
    }

    /**
     * Pick a card
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        game.pickCard(face);
    }

    /**
     * Update the enabled, which implies that it updates if pile is full
     */
    @Override
    public void update(Observable o, Object arg) {
        fixEnabled();
    }
}
