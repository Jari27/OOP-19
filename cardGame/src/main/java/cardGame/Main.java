package cardGame;

import cardGame.controllers.ButtonBar;
import cardGame.game.Game;
import cardGame.views.GamePanel;

import javax.swing.*;
import java.awt.*;

public class Main {

    public static void main(String[] args) {
        Game game = new Game();
        JFrame frame = new JFrame("F*ck the dealer");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setJMenuBar(new ButtonBar(game));
        GamePanel panel = new GamePanel(game);
        frame.getContentPane().add(panel);
        frame.setPreferredSize(new Dimension(800, 600));
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}