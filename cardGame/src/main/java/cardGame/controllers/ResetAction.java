package cardGame.controllers;

import cardGame.game.Game;
import cardGame.models.Card;
import cardGame.models.FaceDiscardPile;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.util.Observable;
import java.util.Observer;

public class ResetAction extends AbstractAction {

    Game game;

    public ResetAction(Game game) {
        super("Reset game");
        this.game = game;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        game.reset();
    }

}
