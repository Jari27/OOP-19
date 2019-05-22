package cardGame.models;

/**
 * A deck with no jokers
 */
public class NoJokerDeck extends AbstractDeck {

    /**
     * Create deck with no jokers and shuffles it.
     */
    @Override
    protected void addCards() {
        for (Card card : Card.values()) {
            if (card.getFace() == Card.Face.JOKER) {
                continue;
            }
            addOnTop(card);
        }
        this.shuffle();
    }


}
