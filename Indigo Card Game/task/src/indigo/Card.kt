package indigo

/**
 * Represents a playing card
 */
data class Card(val suit: Suit, val rank: Rank) {
    override fun toString(): String {
        return rank.toString() + suit.toString()
    }

    /**
     * The possible ranks of the cards
     */
    enum class Rank(private val symbol: String) {
        ACE("A"),
        TWO("2"),
        THREE("3"),
        FOUR("4"),
        FIVE("5"),
        SIX("6"),
        SEVEN("7"),
        EIGHT("8"),
        NINE("9"),
        TEN("10"),
        JACK("J"),
        QUEEN("Q"),
        KING("K");

        override fun toString(): String {
            return symbol
        }
    }

    /**
     * The possible suits of the cards
     */
    enum class Suit(private val symbol: String) {
        DIAMONDS("\u2666"),
        HEARTS("\u2665"),
        SPADES("\u2660"),
        CLUBS("\u2663");

        override fun toString(): String {
            return symbol
        }
    }

}
