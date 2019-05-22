package cardGame.models;

import cardGame.exceptions.WrongFaceException;

import java.util.ArrayList;
import java.util.List;
/**
 * Create a discard pile for every face
 */
public class FaceDiscardPile {

    private List<Card> contents;

    private Card.Face type;


    public FaceDiscardPile(Card.Face type) {
        this.type = type;
        this.contents = new ArrayList<>();
    }

    /**
     * Removes all elements from list 'contents'
     */
    public void clear() {
        this.contents.clear();
    }

    /**
     * Add a card to the discard pile which corresponds to its face
     */
    public void addCard(Card card) throws WrongFaceException {
        if (card.getFace() != type) {
            throw new WrongFaceException("This card has the wrong face!\nThe pile accepts "
                    + this.type
                    + " but you tried to add a "
                    + card.getFace());
        }
        contents.add(card);
    }

    /**
     * Check whether discard pile is full
     */
    public boolean isFull() {
        return this.contents.size() >= 4;
    }

    /**
     * Getter for the type, which corresponds to the face
     */
    public Card.Face getType() {
        return type;
    }

    /**
     * Getter for the list of cards 'contents'.
     */
    public List<Card> getContents() {
        return contents;
    }
}
