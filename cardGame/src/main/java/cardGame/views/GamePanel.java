package cardGame.views;

import cardGame.game.Game;
import cardGame.models.Card;
import cardGame.models.FaceDiscardPile;

import javax.swing.*;
import java.awt.*;
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

    public GamePanel(Game game) {
        this.game = game;
        game.addObserver(this);
        setBackground(new Color(30, 126, 17));
        this.setVisible(true);
        this.setOpaque(true);
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

    private void paintPile(int x, int y, int width, int height, FaceDiscardPile pile, Graphics g) {
        List<Card> contents = pile.getContents();
        if (contents.size() == 4) {
            for (int i = 0; i < contents.size(); i++) {
                Card card = contents.get(i);
                int xPos = x + i * SPACING_DISCARD;
                int yPos = y - i * SPACING_DISCARD;
                g.drawImage(CardBackTextures.getTexture(CardBack.CARD_BACK_BLUE), xPos, yPos, width, height, this);
                g.drawRect(xPos, yPos, width, height);
            }
        } else if (contents.size() > 0) {
            // draw normal pile
            for (int i = 0; i < contents.size(); i++) {
                Card card = contents.get(i);
                int xPos = x + i * SPACING_DISCARD;
                int yPos = y - i * SPACING_DISCARD;
                g.drawImage(CardTextures.getTexture(card), xPos, yPos, width, height, this);
                g.drawRect(xPos, yPos, width, height);
            }
        }
    }

    private void paintDecksAndOutlines(Graphics g) {
        assert (Card.Face.values().length - 1 == CARDS_ON_FIRST_ROW + CARDS_ON_SECOND_ROW);
        // handle font
        g.setColor(new Color(0x000000));
        Font currentFont = g.getFont();
        Font newFont = currentFont.deriveFont(currentFont.getSize() * 1.5f);
        g.setFont(newFont);

        // calculate start of rows
        Dimension cardSize = getCardDimension();
//        int yStartTop = (int) ((getHeight() - 2 * cardSize.height - MIN_MARGIN_Y) / 2.0);
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

            paintPile(posX, posY, cardSize.width, cardSize.height, piles.get(pileNum), g);
        }

        g.setFont(currentFont);
    }



    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        paintDecksAndOutlines(g);
        paintDeck(g);
    }

    @Override
    public void update(Observable o, Object arg) {
        repaint();
    }
}
