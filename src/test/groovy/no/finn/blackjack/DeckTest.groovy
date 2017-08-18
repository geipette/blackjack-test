package no.finn.blackjack

import spock.lang.Specification
import spock.lang.Unroll


class DeckTest extends Specification {

    def "deck contains 52 cards"() {
      given:
        def deck = new Deck()

      expect:
        deck.size() == 52
    }

    def "deck contains #card"() {
      given:
        def deck = new Deck()

      expect:
        deck.find {it == card}
        
      where:
        card << [
            "1d", "2d", "3d", "4d", "5d", "6d", "7d", "8d", "9d", "10d", "11d", "12d", "13d",
            "1s", "2s", "3s", "4s", "5s", "6s", "7s", "8s", "9s", "10s", "11s", "12s", "13s",
            "1c", "2c", "3c", "4c", "5c", "6c", "7c", "8c", "9c", "10c", "11c", "12c", "13c",
            "1h", "2h", "3h", "4h", "5h", "6h", "7h", "8h", "9h", "10h", "11h", "12h", "13h",
        ]
    }

    @Unroll
    def "deck contains four #expectedValue's"() {
      given:
        def deck = new Deck()

      when:
        def count = deck.inject(0) { result, value -> expectedValue == value ? result + 1 : result }

      then:
        count == 4

      where:
        expectedValue << [1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13]
    }

    def "deck is shuffled"() {
      given:
        def deck1It = new Deck().iterator()
        def deck2It = new Deck().iterator()

      when:
        def shuffled = false

        while (!shuffled && deck1It.hasNext() && deck2It.hasNext()) {
            shuffled = deck1It.next() != deck2It.next()
        }

      then:
        shuffled

    }

    def "dealCard removes one card from the deck and returns it"() {
      given:
        def deck = new Deck();

      when:
        int card = deck.dealCard()

      then:
        card > 0
        card < 14
        deck.size() == 51
    }
}