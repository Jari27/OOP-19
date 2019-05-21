package cardGame.controllers;

import cardGame.game.Game;

import javax.swing.*;

public class ButtonBar extends JMenuBar {

    public ButtonBar(Game game) {
        ResetButton reset = new ResetButton(game);
        FinishButton finish = new FinishButton(game);
        this.add(reset);
        this.add(finish);
    }
}
