package cardGame.controllers;

import cardGame.game.Game;

import javax.swing.*;

public class FinishButton extends JButton {

    public FinishButton(Game game) {
        super(new FinishAction(game));
        setToolTipText("Finish the game!");
    }
}
