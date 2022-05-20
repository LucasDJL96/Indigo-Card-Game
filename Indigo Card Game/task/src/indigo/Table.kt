package indigo

import java.util.*

/**
 * Represents the game table
 */
class Table(initialCards: MutableList<Card>) {
    /**
     * Current cards on the table
     */
    var cards = initialCards

    /**
     * Number of cards on the table
     */
    var stackSize = cards.size
        get() {
            field = cards.size
            return field
        }

    /**
     * The card at the top of the table
     */
    var topCard = Optional.of(cards[cards.lastIndex])
        get() {
            field = if (cards.isNotEmpty()) Optional.of(cards[cards.lastIndex]) else Optional.empty()
            return field
        }

    /**
     * Adds a card to the top of the table
     *
     * @param card: the card to add
     */
    fun addCard(card: Card) {
        cards.add(card)
    }

    /**
     * Removes all cards from the table
     */
    fun clear() {
        cards = mutableListOf()
    }

    /**
     * Prints the current table information
     */
    fun printStatus() {
        println(
            if (stackSize > 0) "$stackSize cards on the table, and the top card is ${topCard.get()}"
            else "No cards on the table"
        )
    }

}
