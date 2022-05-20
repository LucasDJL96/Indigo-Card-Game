package indigo

import java.util.Optional

/**
 * Represents a player of the game
 *
 * @param name: the name of the player
 */
open class Player(val name: String) {

    /**
     * The cards the player has on his hand
     */
    protected var hand = mutableListOf<Card>()

    /**
     * The cards the player has won
     */
    val wonCards = mutableListOf<Card>()

    /**
     * The number of cards the player has on his hand
     */
    var handSize = hand.size
        get() {
            field = hand.size
            return field
        }

    /**
     * Replaces the player's cards in hand with new cards
     *
     * @param newCards: the new cards for the player
     */
    fun newCards(newCards: MutableList<Card>) {
        hand = newCards
    }

    /**
     * Returns a string representation of the player's hand
     */
    protected open fun printHand(): String {
        return hand.mapIndexed { index, card -> "${index + 1})$card"}
            .joinToString(" ")
    }

    /**
     * Computes the next play of this player based on the current top card
     *
     * @param topCard: the current top card on the table
     *
     * @return Optional<Card>: the card this player wants to put in play
     */
    open fun getPlay(topCard: Optional<Card>): Optional<Card> {
        println("Cards in hand: ${printHand()}")
        while (true) {
            println("Choose a card to play (1-$handSize):")
            val input = readln()
            if (input == "exit") {
                println("Game Over")
                return Optional.empty()
            }
            if (!input.all { it.isDigit() } || input.toInt() !in 1..handSize) {
                continue
            }
            val card = playCardAt(input.toInt() - 1)
            return Optional.of(card)
        }
    }

    /**
     * Removes a card from the hand by index
     *
     * @param n: the index of the card to play
     *
     * @return the card that is played
     */
    private fun playCardAt(n: Int): Card {
        return hand.removeAt(n)
    }

    /**
     * Removes a card from the hand by card
     *
     * @param card: the card to play
     */
    fun playCardAt(card: Card) {
        hand.remove(card)
    }

    /**
     * Adds cards to the cards won by this player
     *
     * @param cards: the cards that have been won
     */
    fun winCards(cards: MutableList<Card>) {
        wonCards.addAll(cards)
    }
}
