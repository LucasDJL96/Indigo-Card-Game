package indigo

/**
 * Class for controlling the game
 */
class GameController(private val player1: Player, private val player2: Player) {
    /** Whether the game is finished */
    var finished = false
    /** A TurnDecider to control whose turn comes next */
    private val turnDecider = getTurnDecider(player1, player2)
    /** The deck to use in the game */
    private val deck = Deck()
    /** The table of the game */
    val table = Table(deck.getTopCards(4))
    /** The player who won cards from the table last, or the first player if no one */
    private var lastWinner = turnDecider.first
    /** The player who currently has the turn */
    var currentPlayer = turnDecider.second // because we will do nextPlayer() as the game starts

    /**
     * Object for computing the score
     */
    object GameScore {
        /** The ranks that give points */
        private val pointRanks = listOf(Card.Rank.ACE, Card.Rank.TEN, Card.Rank.JACK, Card.Rank.QUEEN, Card.Rank.KING)

        /**
         * Computes de score of a player
         *
         * @param player: the player whose score is to be returned
         * @param isFirstPlayer: whether the player was the first player
         * @param finished: whether the game is finished
         *
         * @return the score of the player
         */
        fun getScore(player: Player, isFirstPlayer: Boolean, finished: Boolean): Int {
            var score = player.wonCards.count { it.rank in pointRanks }
            if (!finished) return score
            if (player.wonCards.size > 26 || player.wonCards.size == 26 && isFirstPlayer) {
                score += 3
            }
            return score
        }
    }

    /**
     * Class for deciding whose turn comes next
     *
     * @param firstPlayer: the player with the next turn
     * @param secondPlayer: the player with the current turn
     */
    data class TurnDecider(var firstPlayer: Player, var secondPlayer: Player) {
        /** The player that started */
        val first = firstPlayer
        /** The player that didn't start */
        val second = secondPlayer

        /**
         * Computes whose turn is next
         */
        fun nextPlayer(): Player {
            val next = firstPlayer
            firstPlayer = secondPlayer
            secondPlayer = next
            return next
        }
    }

    /**
     * Constructs a turn decider
     */
    private fun getTurnDecider(player: Player, cpu: Player): TurnDecider {
        while (true) {
            println("Play first?")
            when (readln()) {
                "yes" -> {
                    return TurnDecider(firstPlayer = player, secondPlayer = cpu)
                }
                "no" -> {
                    return TurnDecider(firstPlayer = cpu, secondPlayer = player)
                }
            }
        }
    }

    /**
     * Starts the round by moving on to the next player and dealing hands if necessary
     */
    fun startRound() {
        currentPlayer = turnDecider.nextPlayer()
        if (player1.handSize == 0 && player2.handSize == 0) {
            dealHands()
        }
    }

    /**
     * Deals new cards to both players
     */
    private fun dealHands() {
        player1.newCards(deck.getTopCards(6))
        player2.newCards(deck.getTopCards(6))
    }

    /**
     * Controls what happens when a player plays a card
     *
     * @param player: the player that plays a card
     * @param card: the card to be played
     */
    fun playCard(player: Player, card: Card) {
        if (table.stackSize > 0 && (card.rank == table.topCard.get().rank || card.suit == table.topCard.get().suit)) {
            table.addCard(card)
            player.winCards(table.cards)
            lastWinner = player
            table.clear()
            println("${player.name} wins cards")
            printStatus()
        } else {
            table.addCard(card)
        }
    }

    /**
     * Prints the current status of the game (scores and cards won)
     */
    private fun printStatus() {
        println("Score: ${player1.name} ${getScore(player1)} - ${player2.name} ${getScore(player2)}")
        println("Cards: ${player1.name} ${player1.wonCards.size} - ${player2.name} ${player2.wonCards.size}")
    }

    /**
     * Computes the score of a player
     *
     * @param player: the player
     */
    private fun getScore(player: Player) = GameScore.getScore(player, player === turnDecider.first,  finished)

    /**
     * Ends the current round by printing the state of the table and by ending the game if necessary
     */
    fun endRound() {
        println()
        table.printStatus()
        if(deck.isEmpty() && player1.handSize == 0 && player2.handSize == 0) {
            endGame()
        }
    }

    /**
     * Ends the game
     */
    private fun endGame() {
        finished = true
        lastWinner.winCards(table.cards)
        printStatus()
        println("Game Over")
    }
}
