package cardGame.views;

import cardGame.game.Game;

import javax.swing.*;
import java.awt.*;
import java.util.Observable;
import java.util.Observer;

public class GamePanel extends JPanel implements Observer {

    Game game;

    public GamePanel(Game game) {
        this.game = game;
        game.addObserver(this);
        setBackground(new Color(126, 53, 77));
        this.setVisible(true);
        this.setOpaque(true);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // TODO paint shit yo
    }

    @Override
    public void update(Observable o, Object arg) {
        repaint();
    }
}
