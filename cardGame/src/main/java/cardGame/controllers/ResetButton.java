package cardGame.controllers;

import cardGame.game.Game;

import javax.swing.*;

public class ResetButton extends JButton {

    public ResetButton(Game game) {
        super(new ResetAction(game));
        setToolTipText("Reset the game!");
    }
}
