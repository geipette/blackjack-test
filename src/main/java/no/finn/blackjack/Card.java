package no.finn.blackjack;

/**
 * Responsibility:
 */
public class Card {
    int value;
    char suit;

    public Card(int value, char suit) {
        this.value = value;
        this.suit = suit;
    }

    public int getValue() {
        return value;
    }

    public char getSuit() {
        return suit;
    }
}
