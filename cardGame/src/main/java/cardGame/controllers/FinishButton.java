package cardGame.controllers;

import cardGame.game.Game;

import javax.swing.*;

class FinishButton extends JButton {

    /**
     //Create a finish button, such that the cards will move to the right.
    */

    public FinishButton(Game game) {
        super(new FinishAction(game));
        setToolTipText("Finish the game!");
    }
}
