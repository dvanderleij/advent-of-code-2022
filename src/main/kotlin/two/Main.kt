package two

import java.io.File

const val INPUT_PATH = "src/main/resources/two.txt"


fun main() {
    one()
    two()
}

//Could put these and their logic in an enum and associate the winner enum to it, but I think its simple enough to stay like this for advent of code
const val ROCK_PLAYER = "X"
const val PAPER_PLAYER = "Y"
const val SCISSORS_PLAYER = "Z"

const val ROCK_OPPONENT = "A"
const val PAPER_OPPONENT = "B"
const val SCISSORS_OPPONENT = "C"

// If I am really nitpicky the increment of the scores could be done by a function win(), draw().
const val WIN_SCORE = 6
const val DRAW_SCORE = 3

const val ROCK_SCORE = 1
const val PAPER_SCORE = 2
const val SCISSORS_SCORE = 3
fun one() {
    var score = 0
    File(INPUT_PATH).forEachLine {
        val x = it.split(' ')
        val opponentMove = x[0]
        val playerMove = x[1]

        when (playerMove) {
            ROCK_PLAYER -> {
                score += ROCK_SCORE
                when (opponentMove) {
                    ROCK_OPPONENT -> score += DRAW_SCORE
                    SCISSORS_OPPONENT -> score += WIN_SCORE
                }
            }

            PAPER_PLAYER -> {
                score += PAPER_SCORE
                when (opponentMove) {
                    ROCK_OPPONENT -> score += WIN_SCORE
                    PAPER_OPPONENT -> score += DRAW_SCORE
                }
            }

            SCISSORS_PLAYER -> {
                score += SCISSORS_SCORE
                when (opponentMove) {
                    PAPER_OPPONENT -> score += WIN_SCORE
                    SCISSORS_OPPONENT -> score += DRAW_SCORE
                }
            }
        }
    }

    println(score)
}


const val HAVE_TO_LOSE = "X"
const val HAVE_TO_DRAW = "Y"
const val HAVE_TO_WIN = "Z"

fun two() {
    var score = 0
    File(INPUT_PATH).forEachLine {
        val x = it.split(' ')
        val opponentMove = x[0]
        val goalIs = x[1]

        when (goalIs) {
            HAVE_TO_LOSE -> {
                when (opponentMove) {
                    ROCK_OPPONENT -> score += SCISSORS_SCORE
                    PAPER_OPPONENT -> score += ROCK_SCORE
                    SCISSORS_OPPONENT -> score += PAPER_SCORE

                }
            }

            HAVE_TO_DRAW -> {
               score += DRAW_SCORE
                when (opponentMove) {
                    ROCK_OPPONENT -> score += ROCK_SCORE
                    PAPER_OPPONENT -> score += PAPER_SCORE
                    SCISSORS_OPPONENT -> score += SCISSORS_SCORE
                }
            }

            HAVE_TO_WIN -> {
                score += WIN_SCORE
                when (opponentMove) {
                    ROCK_OPPONENT -> score += PAPER_SCORE
                    PAPER_OPPONENT -> score += SCISSORS_SCORE
                    SCISSORS_OPPONENT -> score += ROCK_SCORE
                }
            }
        }
    }

    println(score)
}