package cardGame.controllers;

import cardGame.game.Game;

import javax.swing.*;

class FinishButton extends JButton {

    public FinishButton(Game game) {
        super(new FinishAction(game));
        setToolTipText("Finish the game!");
    }
}
