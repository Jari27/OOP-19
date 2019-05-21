package cardGame.controllers;

import cardGame.game.Game;
import cardGame.models.Card;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.util.Observable;
import java.util.Observer;

public class FinishAction extends AbstractAction implements Observer {

    private Game game;

    public FinishAction(Game game) {
        super("Finish the game");
        this.game = game;
        game.addObserver(this);
    }

    private void fixEnabled() {
        if (game.isFinished()) {
            setEnabled(false);
        } else {
            setEnabled(true);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        while (!game.isFinished()) {
            game.pickCard(Card.Face.ACE);
        }
    }

    @Override
    public void update(Observable observable, Object o) {
        fixEnabled();
    }
}
