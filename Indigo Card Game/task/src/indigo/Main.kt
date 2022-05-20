package indigo

fun main() {
    println("Indigo Card Game")
    val gameController = GameController(Player("Player"), Cpu("Computer"))
    println("Initial cards on the table: ${gameController.table.cards.joinToString(" ")}")
    gameController.endRound()
    while (!gameController.finished) {
        gameController.startRound()
        val card = gameController.currentPlayer.getPlay(gameController.table.topCard)
        if (card.isEmpty) return
        gameController.playCard(gameController.currentPlayer, card.get())
        gameController.endRound()
    }
}
