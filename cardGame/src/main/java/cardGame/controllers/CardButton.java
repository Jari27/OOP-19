package cardGame.controllers;

import cardGame.game.Game;
import cardGame.models.Card;

import javax.swing.*;

public class CardButton extends JButton {

    private void setButtonProperties(Card.Face face) {
        setVisible(true);
        setContentAreaFilled(false);
        if (System.getProperty("os.name").contains("Mac")) {
            // hacky fix to prevent white buttons
            setBorderPainted(false);
        }
        setToolTipText("Select the " + face.toHumanString());
    }

    @Override
    protected void actionPropertyChanged(Action action, String s) {
        super.actionPropertyChanged(action, s);
        if (!action.isEnabled()){
            setToolTipText("");
            setBorderPainted(false);
        } else {
            setBorderPainted(false);
        }
    }

    public CardButton(Game game, Card.Face face) {
        super(new CardAction(face, game));
        setButtonProperties(face);
    }
}
