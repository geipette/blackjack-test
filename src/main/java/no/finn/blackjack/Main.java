package no.finn.blackjack;

import java.util.List;

import static no.finn.blackjack.Game.SAM;
import static no.finn.blackjack.Game.score;

public class Main {
    public static void main(String[] args) {
        Game game = new Game();
        game.deal();
        game.play();

        System.out.println(String.format(
                "Sam: %s - score: %s\nDealer: %s - score: %s\n%s is the winner.",
                game.getSamHand(),
                scoreText(game.getSamHand()),
                game.getDealerHand(),
                scoreText(game.getDealerHand()),
                winnerText(game)
        ));
    }

    private static String winnerText(Game game) {
        return game.winner() == SAM ? "Sam" : "Dealer";
    }

    private static String scoreText(List<Integer> hand) {
        return score(hand) <= 21 ? Integer.toString(score(hand)) : "BUST";
    }
}
