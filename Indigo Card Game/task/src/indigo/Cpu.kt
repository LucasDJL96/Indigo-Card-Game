package indigo

import java.util.*

/**
 * Represents a player controlled by the computer
 */
class Cpu(name: String) : Player(name) {

    /**
     * Returns a string representation of the player's hand
     */
    override fun printHand(): String {
        return hand.joinToString(" ")
    }

    /**
     * Computes the next play of this player based on the current top card
     *
     * @param topCard: the current top card on the table
     *
     * @return Optional<Card>: the card this player wants to put in play
     */
    override fun getPlay(topCard: Optional<Card>): Optional<Card> {
        println(printHand())
        val candidates = getCandidates(topCard)
        val card = if (hand.size == 1) {
            hand[0]
        } else if (candidates.size == 1) {
            candidates[0]
        } else if (topCard.isEmpty || candidates.isEmpty()) {
            cardFromStrategy1()
        } else {
            cardFromStrategy2(candidates, topCard.get())
        }

        println("$name plays $card")
        playCardAt(card)
        return Optional.of(card)
    }

    /**
     * Gets a list of the cards on the player's hand that can win the cards on the table
     *
     * @param topCard: the card at the top of the table
     *
     * @return List<Card> with the candidate cards
     */
    private fun getCandidates(topCard: Optional<Card>): List<Card> {
        if (topCard.isEmpty) return hand.toList()
        return hand.filter { it.suit == topCard.get().suit || it.rank == topCard.get().rank }
    }

    /**
     * Computes a card to play when there is no top card on the table or no candidate cards
     *
     * @return Card to play
     */
    private fun cardFromStrategy1(): Card {
        for (suit in Card.Suit.values()) {
            if (hand.count { it.suit == suit } > 1) return hand.find { it.suit == suit }!!
        }
        for (rank in Card.Rank.values()) {
            if (hand.count { it.rank == rank } > 1) return hand.find { it.rank == rank }!!
        }
        return hand[0]
    }

    /**
     * Computes a card to play when there is a top card on the table or several candidate cards
     *
     * @return Card to play
     */
    private fun cardFromStrategy2(candidates: List<Card>, topCard: Card): Card {
        if (candidates.count { it.suit == topCard.suit } > 1) return candidates.find { it.suit == topCard.suit }!!
        if (candidates.count { it.rank == topCard.rank } > 1) return candidates.find { it.rank == topCard.rank }!!
        return candidates[0]
    }
}
