package no.finn.blackjack;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.stream.IntStream;

public class Deck implements Iterable<Integer> {
    private List<Integer> cards = new ArrayList<>(52);

    public Deck() {
        IntStream.range(1, 53).forEach(value -> cards.add(value % 13 + 1));
        Collections.shuffle(cards);
    }

    Deck(List<Integer> cards) {
        this.cards.addAll(cards);
    }

    @Override
    public Iterator<Integer> iterator() {
        return cards.iterator();
    }

    public int size() {
        return cards.size();
    }

    public int dealCard() {
        return cards.remove(0);
    }
}
