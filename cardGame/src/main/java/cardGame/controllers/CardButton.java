package cardGame.controllers;

import cardGame.game.Game;
import cardGame.models.Card;

import javax.swing.*;

public class CardButton extends JButton {

    /**
     * Set the properties of the card button: only the border is visible
     */
    private void setButtonProperties(Card.Face face) {
        setVisible(true);
        setContentAreaFilled(false);
        if (System.getProperty("os.name").contains("Mac")) {
            // hacky fix to prevent white buttons on Mac
            setBorderPainted(false);
        }
        setToolTipText("Select the " + face.toHumanString());
    }

    /**
     * If a pile is full, the border and tool tip text is made invisible
     */
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

    /**
     * Create a button with certain properties
     */
    public CardButton(Game game, Card.Face face) {
        super(new CardAction(face, game));
        setButtonProperties(face);
    }
}
