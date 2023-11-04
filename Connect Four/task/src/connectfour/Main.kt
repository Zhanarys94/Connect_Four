package connectfour

import kotlin.system.exitProcess

private val board = Board()
private val firstPlayer = Player()
private val secondPlayer = Player()
private var activePlayer = firstPlayer
private var singleGame = true
private var totalGames = 1
private var gameNumber = 0
private var newRound = false
private var firstRound = true

class Player(var name: String = "", var char: Char = ' ', var wins: Int = 0)

class Board {
    var rows: Int = 6
    var columns: Int = 7
}

fun main() {
    println("Connect Four")
    println("First player's name:")
    firstPlayer.name = readln()
    println("Second player's name:")
    secondPlayer.name = readln()
    firstPlayer.char = 'o'
    secondPlayer.char = '*'
    gameIntro()
    val gameMap = MutableList(board.rows) {MutableList(board.columns) {' '} }
    val walls = MutableList(board.rows) { MutableList(board.columns + 1) {"\u2551"} }
    while (totalGames >= gameNumber) {
        if (newRound || firstRound) gameBoard(gameMap, walls)
        game(gameMap, walls)
        activePlayer = if (activePlayer == firstPlayer) secondPlayer else firstPlayer
    }
    println("Game over!")
}

fun gameIntro() {
    println("Set the board dimensions (Rows x Columns)\nPress Enter for default (6 x 7)")
    val dimensionsInput = readln().filter { !it.isWhitespace() }
    if (dimensionsInput != "") {
        checkDimensions(dimensionsInput)
        rounds()
        println("${firstPlayer.name} VS ${secondPlayer.name}")
        println("${board.rows} X ${board.columns} board")
        println(if (singleGame) "Single game" else "Total $totalGames games")
    } else {
        rounds()
        println("${firstPlayer.name} VS ${secondPlayer.name}")
        println("${board.rows} X ${board.columns} board")
        println(if (singleGame) "Single game" else "Total $totalGames games")
    }
}

fun rounds() {
    val regex = Regex("[1-9]+")
    println("""Do you want to play single or multiple games?
For a single game, input 1 or press Enter
Input a number of games:""".trimIndent())
    val games = readln()
    if (!regex.matches(games) && games.isNotEmpty()) {
        println("Invalid input")
        rounds()
    } else if (games.isEmpty() || games == "1") {
        singleGame = true
        return
    } else {
        totalGames = games.toInt()
        singleGame = false
    }
}

fun checkDimensions(input: String) {
    val regex = Regex("""^\d+[xX]\d+$""")
    val rows = Character.getNumericValue(input.first())
    val columns = Character.getNumericValue(input.last())
    if (!regex.matches(input)) {
        println("Invalid input")
        gameIntro()
    } else if (rows !in 5..9) {
            println("Board rows should be from 5 to 9")
            gameIntro()
    } else if (columns !in 5..9) {
            println("Board columns should be from 5 to 9")
            gameIntro()
    } else {
        board.rows = rows
        board.columns = columns
    }
}

fun gameBoard(gameMap: MutableList<MutableList<Char>>, walls: MutableList<MutableList<String>>) {
    if (gameNumber == 0) gameNumber++
    if (newRound && !singleGame || firstRound && !singleGame) {
        println("Game #$gameNumber")
        firstRound = false
        for (row in gameMap.indices) {
            gameMap[row].replaceAll { ' ' }
        }
    }
    print(" ")
    for (columns in 1..board.columns) {
        print("$columns ")
    }
    println()
    for (rows in 0..walls.lastIndex) {
        for (columns in 0..walls[0].lastIndex) {
            print(walls[rows][columns])
            if (columns <= gameMap[0].lastIndex) print(gameMap[rows][columns])
        }
        println()
    }
    print("\u255A")
    for (columns in 1 until board.columns * 2) {
        when {
            columns % 2 == 0 -> print("\u2569")
            else -> print("\u2550")
        }
    }
    println("\u255D")
    newRound = false
}

fun game(gameMap: MutableList<MutableList<Char>>, walls: MutableList<MutableList<String>>) {
    val regex = Regex("""\d+""")
    println("${activePlayer.name}'s turn")
    val input = readln()
    if (input == "end") {
        println("Game over!")
        exitProcess(0)
    } else if (!regex.matches(input)) {
        println("Incorrect column number")
        game(gameMap, walls)
    } else if (input.toInt() !in 1..board.columns) {
        println("The column number is out of range (1 - ${board.columns})")
        game(gameMap, walls)
    } else {
        loop@for (rows in gameMap.lastIndex downTo 0) {
            if (gameMap[rows][input.toInt() - 1] == ' ') {
                gameMap[rows][input.toInt() - 1] = activePlayer.char
                gameBoard(gameMap, walls)
                when (check(gameMap)) {
                    "winner" -> {
                        if (singleGame) {
                            println("Player ${activePlayer.name} won\nGame over!")
                            exitProcess(0)
                        } else {
                            println("Player ${activePlayer.name} won\nScore")
                            println("${firstPlayer.name}: ${firstPlayer.wins} ${secondPlayer.name}: ${secondPlayer.wins}")
                            gameNumber++
                            newRound = true
                            return
                        }
                    }
                    "draw" -> {
                        if (singleGame) {
                            println("It is a draw\nGame over!")
                            exitProcess(0)
                        } else {
                            println("It is a draw\nScore")
                            println("${firstPlayer.name}: ${firstPlayer.wins} ${secondPlayer.name}: ${secondPlayer.wins}")
                            gameNumber++
                            newRound = true
                            return
                        }
                    }
                    "continue" -> {
                        return
                    }
                }
            } else if (gameMap[rows][input.toInt() - 1] != ' ') continue@loop
            else {
                println("Column ${input.toInt()} is full")
                return
            }
        }
    }
}

fun check(gameMap: MutableList<MutableList<Char>>): String {
    for (row in gameMap.lastIndex downTo 0) {                              //check for horizontal win
        for (column in 0..gameMap[0].lastIndex - 3) {
            if (gameMap[row][column] == activePlayer.char &&
                gameMap[row][column + 1] == gameMap[row][column] &&
                gameMap[row][column + 2] == gameMap[row][column] &&
                gameMap[row][column + 3] == gameMap[row][column]) {
                when (activePlayer) {
                    firstPlayer -> firstPlayer.wins += 2
                    secondPlayer -> secondPlayer.wins += 2
                }
                return "winner"
            }
        }
    }
    for (row in gameMap.lastIndex downTo 3) {                               //check for vertical win
        for (column in 0..gameMap[0].lastIndex) {
            if (gameMap[row][column] == activePlayer.char &&
                gameMap[row - 1][column] == gameMap[row][column] &&
                gameMap[row - 2][column] == gameMap[row][column] &&
                gameMap[row - 3][column] == gameMap[row][column]) {
                when (activePlayer) {
                    firstPlayer -> firstPlayer.wins += 2
                    secondPlayer -> secondPlayer.wins += 2
                }
                return "winner"
            }
        }
    }
    for (row in gameMap.lastIndex downTo 3) {                               //check for diagonal win
        for (column in 0..gameMap[0].lastIndex - 3) {
            if (gameMap[row][column] == activePlayer.char &&
                gameMap[row - 1][column + 1] == gameMap[row][column] &&
                gameMap[row - 2][column + 2] == gameMap[row][column] &&
                gameMap[row - 3][column + 3] == gameMap[row][column]) {
                when (activePlayer) {
                    firstPlayer -> firstPlayer.wins += 2
                    secondPlayer -> secondPlayer.wins += 2
                }
                return "winner"
            }
        }
    }
    for (row in gameMap.lastIndex downTo 3) {                               //check for reverse diagonal win
        for (column in gameMap[0].lastIndex downTo 3) {
            if (gameMap[row][column] == activePlayer.char &&
                gameMap[row - 1][column - 1] == gameMap[row][column] &&
                gameMap[row - 2][column - 2] == gameMap[row][column] &&
                gameMap[row - 3][column - 3] == gameMap[row][column]) {
                when (activePlayer) {
                    firstPlayer -> firstPlayer.wins += 2
                    secondPlayer -> secondPlayer.wins += 2
                }
                return "winner"
            }
        }
    }
    return if (!gameMap[0].contains(' ')) {
        firstPlayer.wins++
        secondPlayer.wins++
        "draw"
    } else "continue"
}