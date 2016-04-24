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

    @Unroll
    def "deck contains four #expectedValue's"() {
      given:
        def deck = new Deck();

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