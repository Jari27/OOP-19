package cardGame.controllers;

import cardGame.game.Game;
import cardGame.models.Card;
import cardGame.models.FaceDiscardPile;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.util.Observable;
import java.util.Observer;

public class CardAction extends AbstractAction implements Observer {

    private FaceDiscardPile associatedPile;
    private Game game;
    private Card.Face face;

    public CardAction(Card.Face face, Game game) {
        super();
        this.game = game;
        this.face = face;
        game.addObserver(this);

        this.associatedPile = game.getDiscardPiles().get(face.ordinal());
        fixEnabled();
    }

    private void fixEnabled() {
        if (associatedPile.isFull()) {
            setEnabled(false);
        } else {
            setEnabled(true);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        game.pickCard(face);
    }

    @Override
    public void update(Observable o, Object arg) {
        fixEnabled();
    }
}
