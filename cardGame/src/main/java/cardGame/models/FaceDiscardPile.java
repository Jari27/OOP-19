package cardGame.models;

import cardGame.exceptions.WrongFaceException;

import java.util.ArrayList;
import java.util.List;

public class FaceDiscardPile {

    private List<Card> contents;

    private Card.Face type;
    /**
     * Create 13 new piles for every face
     */

    public FaceDiscardPile(Card.Face type) {
        this.type = type;
        this.contents = new ArrayList<>();
    }

    public void addCard(Card card) throws WrongFaceException {
        if (card.getFace() != type) {
            throw new WrongFaceException("This card has the wrong face!\nThe pile accepts "
                    + this.type
                    + " but you tried to add a "
                    + card.getFace());
        }
        contents.add(card);
    }

    public Card.Face getType() {
        return type;
    }

    public List<Card> getContents() {
        return contents;
    }
}
