package cardGame.game;

import cardGame.exceptions.WrongFaceException;
import cardGame.models.Card;
import cardGame.models.FaceDiscardPile;
import cardGame.models.NoJokerDeck;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

public class Game extends Observable {

    private ArrayList<FaceDiscardPile> discardPiles;
    private NoJokerDeck deck;

    public Game() {
        this.deck = new NoJokerDeck();
        this.discardPiles = new ArrayList<>();
        for (Card.Face face : Card.Face.values()) {
            if (face == Card.Face.JOKER) {
                continue;
            }
            FaceDiscardPile myPile = new FaceDiscardPile(face);
            if (face == Card.Face.ACE) {
                try {
                    myPile.addCard(Card.ACE_DIAMONDS);
                    myPile.addCard(Card.ACE_CLUBS);
                }catch (WrongFaceException e) {
                    // do nothing woohoo
                }
            }
            discardPiles.add(myPile);
        }
    }

    public List<FaceDiscardPile> getDiscardPiles() {
        return discardPiles;
    }

    public NoJokerDeck getDeck() {
        return deck;
    }
}
