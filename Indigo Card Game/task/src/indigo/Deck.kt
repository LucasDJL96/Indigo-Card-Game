package indigo

import kotlin.random.Random

/**
 * Represents a deck of cards
 */
class Deck {
    /**
     * The cards on the deck
     */
    private var cards = newCards()

    companion object {
        private val random = Random.Default

        private fun shuffle(cards: MutableList<Card>): MutableList<Card> {
            val shuffledCards = mutableListOf<Card>()
            val cardsCopy = cards.toMutableList()
            while (cardsCopy.isNotEmpty()) {
                val r = random.nextInt(cardsCopy.size)
                shuffledCards.add(cardsCopy[r])
                cardsCopy.removeAt(r)
            }
            return shuffledCards
        }

        fun newCards(): MutableList<Card> {
            val cards = mutableListOf<Card>()
            for (suit in Card.Suit.values()) {
                for (rank in Card.Rank.values()) {
                    cards.add(Card(suit, rank))
                }
            }
            return shuffle(cards)
        }
    }

    /**
     * Finds the cards at the top of the deck
     *
     * @param number: number of cards to find
     *
     * @return the cards at the top of the deck
     */
    fun getTopCards(number: Int): MutableList<Card> {
        if (number > cards.size) throw IllegalArgumentException("Not enough cards")
        val topCards = mutableListOf<Card>()
        repeat(number) {
            val topCard = cards.removeAt(0)
            topCards.add(topCard)
        }
        return topCards
    }

    /**
     * Checks if the deck has no cards left
     */
    fun isEmpty(): Boolean = cards.isEmpty()
}
