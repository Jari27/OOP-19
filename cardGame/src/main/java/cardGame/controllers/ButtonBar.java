package cardGame.controllers;

import cardGame.game.Game;

import javax.swing.*;

public class ButtonBar extends JMenuBar {

    public ButtonBar(Game game) {
        ResetButton reset = new ResetButton(game);
        this.add(reset);
    }
}
