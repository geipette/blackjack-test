package no.finn.blackjack;

import java.util.*;

import static java.util.stream.IntStream.range;

public class Deck implements Iterable<Card> {
    private List<Card> cards = new ArrayList<>(52);

    public Deck() {
        Arrays.asList('d', 'c', 's', 'h').stream().forEach(suit ->
                range(1, 14).forEach(value -> cards.add(new Card(value, suit)))
        );
        Collections.shuffle(cards);
    }

    Deck(List<Card> cards) {
        this.cards.addAll(cards);
    }

    @Override
    public Iterator<Card> iterator() {
        return cards.iterator();
    }

    public int size() {
        return cards.size();
    }

    public Card dealCard() {
        return cards.remove(0);
    }

}
