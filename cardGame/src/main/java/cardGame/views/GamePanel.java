package cardGame.views;

import cardGame.controllers.CardButton;
import cardGame.game.Game;
import cardGame.models.Card;
import cardGame.models.FaceDiscardPile;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

public class GamePanel extends JPanel implements Observer {

    private static final int MIN_MARGIN_X = 30; //px
    private static final int MIN_MARGIN_Y = 50; //px
    private static final int MARGIN_TOP_Y = 200; //px
    private static final int CARDS_ON_FIRST_ROW = 7;
    private static final int CARDS_ON_SECOND_ROW = 6;
    private static final int SPACING_DISCARD = 8; //px

    private Game game;
    private List<CardButton> buttons = new ArrayList<>();

    public GamePanel(Game game) {
        this.game = game;
        game.addObserver(this);
        setBackground(new Color(30, 126, 17));
        this.setVisible(true);
        this.setLayout(null);
        this.setOpaque(true);
        this.createButtons();
    }

    private void createButtons() {
        for (Card.Face face : Card.Face.values()) {
            if (face != Card.Face.JOKER) {
                CardButton button = new CardButton(game, face);
                buttons.add(button);
                this.add(button);
            }
        }
    }

    private Dimension getCardDimension() {
        double maxWidth = Math.max(0, (getWidth() - (CARDS_ON_FIRST_ROW + 1) * MIN_MARGIN_X) / (double) CARDS_ON_FIRST_ROW);
        double maxHeight = Math.max(0, (getHeight() - 2 * MIN_MARGIN_Y - MARGIN_TOP_Y) / (double) 2);

        if (maxHeight * 436.0 / (maxWidth * 600.0) > 1.0) {
            // height too big
            maxHeight = maxWidth / 436.0 * 600.0;
        } else {
            // width too big
            maxWidth = maxHeight / 600.0 * 436.0;
        }
        return new Dimension((int) Math.round(maxWidth), (int) Math.round(maxHeight));
    }

    private void paintDeck(Graphics g) {
        // TODO
    }

    // draws the cards & returns the position of the last card that was drawn (or the default position)
    private Point paintPile(int x, int y, int width, int height, FaceDiscardPile pile, Graphics g) {
        List<Card> contents = pile.getContents();
        int lastX = x;
        int lastY = y;
        for (int i = 0; i < contents.size(); i++) {
            BufferedImage texture;
            if (pile.isFull()) {
                texture = CardBackTextures.getTexture(CardBack.CARD_BACK_BLUE);
            } else {
                texture = CardTextures.getTexture(contents.get(i));
            }
            int xPos = x + i * SPACING_DISCARD;
            int yPos = y - i * SPACING_DISCARD;
            g.drawImage(texture, xPos, yPos, width, height, this);
            g.drawRect(xPos, yPos, width, height);
            lastX = xPos;
            lastY = yPos;
        }
        return new Point(lastX, lastY);
    }

    private void paintDiscardPiles(Graphics g) {
        assert (Card.Face.values().length - 1 == CARDS_ON_FIRST_ROW + CARDS_ON_SECOND_ROW);
        // handle font
        g.setColor(new Color(0x000000));
        Font currentFont = g.getFont();
        Font newFont = currentFont.deriveFont(currentFont.getSize() * 1.5f);
        g.setFont(newFont);

        // calculate start of rows
        Dimension cardSize = getCardDimension();
        int yStartTop = MARGIN_TOP_Y;
        int xStartTop = (int) ((getWidth() - CARDS_ON_FIRST_ROW * cardSize.width - (CARDS_ON_FIRST_ROW - 1) * MIN_MARGIN_X) / 2.0);

        int yStartBottom = yStartTop + MIN_MARGIN_Y + cardSize.height;
        int xStartBottom = (int) ((getWidth() - CARDS_ON_SECOND_ROW * cardSize.width - (CARDS_ON_SECOND_ROW - 1) * MIN_MARGIN_X) / 2.0);

        // get piles to draw
        List<FaceDiscardPile> piles = game.getDiscardPiles();

        // draw piles
        for (int pileNum = 0; pileNum < CARDS_ON_FIRST_ROW + CARDS_ON_SECOND_ROW; pileNum++) {
            int posX = xStartTop + pileNum * (cardSize.width + MIN_MARGIN_X);
            int posY = yStartTop;
            if (pileNum >= CARDS_ON_FIRST_ROW) {
                // adjust location for bottom row
                posY = yStartBottom;
                posX = xStartBottom + (pileNum - CARDS_ON_FIRST_ROW) * (cardSize.width + MIN_MARGIN_X);
            }

            g.drawRect(posX, posY, cardSize.width, cardSize.height);
            // draw string inside
            String string = Card.Face.values()[pileNum].toHumanString();
            int stringX = posX + 2;
            int stringY = (posY + g.getFontMetrics().getAscent());
            g.drawString(string, stringX, stringY);
            Point buttonLocation = paintPile(posX, posY, cardSize.width, cardSize.height, piles.get(pileNum), g);
            if (buttons.size() >= CARDS_ON_FIRST_ROW + CARDS_ON_SECOND_ROW) {
                CardButton button = buttons.get(pileNum);
                button.setBounds(buttonLocation.x, buttonLocation.y, cardSize.width, cardSize.height);
            }
        }
        g.setFont(currentFont);
    }


    private void paintString(Graphics g) {
        Font currentFont = g.getFont();
        Font newFont = currentFont.deriveFont(Font.BOLD, 32.0f);
        g.setFont(newFont);
        double width = g.getFontMetrics().stringWidth(game.getStatus());
        g.drawString(game.getStatus(),
                (int) (getWidth() - width) / 2,
                (MARGIN_TOP_Y  - g.getFontMetrics().getAscent()) / 2);
        g.setFont(currentFont);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        paintDiscardPiles(g);
        paintDeck(g);
        paintString(g);
    }

    @Override
    public void update(Observable o, Object arg) {
        repaint();
    }
}
