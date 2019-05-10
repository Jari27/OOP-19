package cardGame.models;

public class NoJokerDeck extends AbstractDeck {
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
