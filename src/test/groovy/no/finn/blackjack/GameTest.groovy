package no.finn.blackjack

import spock.lang.Specification
import spock.lang.Unroll

import static no.finn.blackjack.Game.DEALER
import static no.finn.blackjack.Game.SAM
import static no.finn.blackjack.Game.score

class GameTest extends Specification {
    def "Initial hands are empty"() {
        given:
          def game = new Game()

        expect:
          game.getSamHand().empty
          game.getDealerHand().empty
    }

    def "deal deals two cards for each player"() {
        given:
          def game = new Game()

        when:
          game.deal()

        then:
          game.getSamHand().size() == 2
          game.getDealerHand().size() == 2
    }

    @Unroll
    def "score is #expectedScore for cards #card1 and #card2"() {
        expect:
          score([card1, card2]) == expectedScore

        where:
          card1 | card2 | expectedScore
          2     | 9     | 11
          1     | 1     | 22
          1     | 11    | 21
          13    | 12    | 20
    }

    def "No cards are drawn when one or more of the players has blackjack"() {
        given:
          def game = new Game(makeTestDeck(samCards, dealerCards))
          game.deal()

        when:
          game.play()

        then:
          game.samHand.size() == 2
          game.dealerHand.size() == 2

        where:
          samCards | dealerCards
          [10, 1]  | [5, 5]
          [2, 2]   | [12, 1]
          [11, 1]  | [1, 13]

    }

    def "Sam draws cards until he has 17"() {
        given:
          def game = new Game(makeTestDeck(samCards, [5, 5]))
          game.deal()
        when:

          game.play()

        then:
          score(game.samHand) >= 17

        where:
          samCards << [
                  [2, 2, 2, 2, 3, 3, 3],
                  [5, 5, 8],
                  [1, 13]
          ]
    }

    def "Dealer does not draw cards when Sam has busted"() {
        given:
          def game = new Game(makeTestDeck([6, 10, 10], [5, 5]))
          game.deal()

        when:
          game.play()

        then:
          game.dealerHand.size() == 2

    }

    def "Dealer draw cards until he has higher that Sam"() {
        given:
          def game = new Game(makeTestDeck(samCards, dealerCards))
          game.deal()

        when:
          game.play()

        then:
          score(game.dealerHand) > score(game.samHand)

        where:
          samCards | dealerCards
          [7, 10]  | [5, 5, 5, 5]
    }

    def "Sam wins when his initial cards is blackjack"() {
        given:
          def game = new Game(makeTestDeck([1, 10], [10, 8]))
          game.deal()

        when:
          game.play()

        then:
          game.winner() == SAM

    }

    def "Dealer wins when his initial cards is blackjack"() {
        given:
          def game = new Game(makeTestDeck([1, 5], [1, 10]))
          game.deal()

        when:
          game.play()

        then:
          game.winner() == DEALER

    }

    def "Sam wins when Dealer busts"() {
        given:
          def game = new Game(makeTestDeck([10, 9], [10, 8, 5]))
          game.deal()

        when:
          game.play()

        then:
          game.winner() == SAM
    }

    def "Dealer wins when Sam busts"() {
        given:
          def game = new Game(makeTestDeck([10, 6, 10], [10, 8]))
          game.deal()

        when:
          game.play()

        then:
          game.winner() == DEALER

    }

    def "Dealer wins when he has higher score than Sam"() {
        given:
          def game = new Game(makeTestDeck([10, 5, 2], [10, 5, 2, 2]))
          game.deal()

        when:
          game.play()

        then:
          game.winner() == DEALER

    }

    Deck makeTestDeck(def samCards, def dealerCards) {
        List<Integer> cards = [
                samCards.first(),
                dealerCards.first(),
                samCards.get(1),
                dealerCards.get(1)
        ] as List<Integer>

        if (samCards.size() > 2) {
            cards.addAll(samCards[2..samCards.size() - 1])
        }

        if (dealerCards.size() > 2) {
            cards.addAll(dealerCards[2..dealerCards.size() - 1])
        }

        cards.addAll(new Deck())

        new Deck(cards)
    }
}