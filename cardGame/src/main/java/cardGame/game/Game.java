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
    private boolean firstChance = true;
    private String status = "Pick a card!";

    public Game() {
        this.deck = new NoJokerDeck();
        this.discardPiles = new ArrayList<>();
        for (Card.Face face : Card.Face.values()) {
            if (face == Card.Face.JOKER) {
                continue;
            }
            FaceDiscardPile myPile = new FaceDiscardPile(face);
            discardPiles.add(myPile);
        }
    }

    public void pickCard(Card.Face face) {
        Card nextCard = deck.peek();
        if (nextCard.getFace() == face) {
            // correct
            firstChance = true;
            moveToDiscardPile(deck.draw());
            if (deck.isEmpty()) {
                status = "Correct! Dealer drinks & game over!";
            } else {
                status = "Correct! Dealer drinks & pick a new card!";
            }
        }
        else if (firstChance) {
            firstChance = false;
            if (nextCard.getFace().ordinal() < face.ordinal()) {
                status = "LOWER";
            } else {
                status = "HIGHER";
            }
        } else {
            // wrong card & last chance
            firstChance = true;
            moveToDiscardPile(deck.draw());
            if (deck.isEmpty()) {
                status = "Wrong! You drink & game over!";
            } else {
                status = "Wrong! You drink & pick a new card!";
            }
        }
        setChanged();
        notifyObservers();
    }

    private void moveToDiscardPile(Card card) {
        try {
            discardPiles.get(card.getFace().ordinal()).addCard(card);
        } catch (WrongFaceException e) {
            System.err.println("Something wrong. Verify you're adding this to the right deck!");
        }
    }

    public void reset() {
        this.deck = new NoJokerDeck();
        for (FaceDiscardPile pile : discardPiles) {
            pile.clear();
        }
        status = "Game reset. Please pick a card!";
        setChanged();
        notifyObservers();
    }

    public List<FaceDiscardPile> getDiscardPiles() {
        return discardPiles;
    }

    public NoJokerDeck getDeck() {
        return deck;
    }

    public String getStatus() {
        return status;
    }
}
