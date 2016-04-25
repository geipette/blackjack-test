package no.finn.blackjack;

import java.util.ArrayList;
import java.util.List;

import static java.util.Collections.unmodifiableList;

public class Game {

    public static int SAM = 0;
    public static int DEALER = 1;

    private Deck deck;
    private List<Integer> samHand = new ArrayList<>();
    private List<Integer> dealerHand = new ArrayList<>();

    public Game() {
        this(new Deck());
    }

    Game(Deck deck) {
        this.deck = deck;
    }

    public static int score(List<Integer> hand) {
        return hand.stream().reduce(0, (result, card) ->
                result + (card == 1 ? 11 : Math.min(card, 10))
        );
    }

    public List<Integer> getSamHand() {
        return unmodifiableList(samHand);
    }

    public List<Integer> getDealerHand() {
        return unmodifiableList(dealerHand);
    }

    public void deal() {
        for (int i = 0; i < 2; i++) {
            samHand.add(deck.dealCard());
            dealerHand.add(deck.dealCard());
        }
    }

    public void play() {
        if (noPlayerHasBlackjack()) {
            samPlay();
            dealerPlay();
        }
    }

    public int winner() {
        if (score(samHand) > 21 || (score(samHand) != 21 && score(dealerHand) <= 21)) {
            return DEALER;
        }
        return SAM;
    }

    private boolean noPlayerHasBlackjack() {
        return score(samHand) != 21 && score(dealerHand) != 21;
    }

    private void dealerPlay() {
        while (score(samHand) <= 21 && score(samHand) >= score(dealerHand)) {
            dealerHand.add(deck.dealCard());
        }
    }

    private void samPlay() {
        while (score(samHand) < 17) {
            samHand.add(deck.dealCard());
        }
    }

}
