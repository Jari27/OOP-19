package cardGame.controllers;

import cardGame.game.Game;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class ResetAction extends AbstractAction {

    private Game game;

    public ResetAction(Game game) {
        super("Reset game");
        this.game = game;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        game.reset();
    }

}
