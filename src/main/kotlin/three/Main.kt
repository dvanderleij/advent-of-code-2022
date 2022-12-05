package three

import kotlin.io.path.forEachLine


const val INPUT_FILE = "three.txt"

fun main() {
    one()
    two()
}

fun one() {
    var score = 0
    common.inputPath(INPUT_FILE).forEachLine {
        val (firstHalf, secondHalf) = splitInHalf(it)
        var found = false;
        for (first in firstHalf.toCharArray()) {
            for (second in secondHalf.toCharArray()) {
                if (first == second) {
                    score += charToScore(first)
                    found = true
                    break
                }

            }
            // Yuck, at the amount of break statements, could probably be nicer
            if (found) {
                break
            }
        }
    }
    println(score)
}


fun two() {
    var score = 0
    val lines = common.readAllInputLines(INPUT_FILE)
    for (i in lines.indices step 3) {
        val charsInAllThreeRucksacks =
            (0..2).map { lines[i + it].toSet() }
                .reduce { charsInRucksack, charsInOtherRucksack -> charsInRucksack.intersect(charsInOtherRucksack) }
        score += charsInAllThreeRucksacks.sumOf { charToScore(it) }
    }
    println(score)
}

const val ASCII_CODE_POINT_START_LOWERCASE = 96
const val ASCII_CODE_POINT_CAPITAL_OFFSET = 64;
const val UPPERCASE_SCORE_OFFSET = 26;

fun splitInHalf(line: String): Pair<String, String> {
    val halfway = line.length / 2
    val firstHalf = line.substring(0 until halfway)
    val secondHalf = line.substring(halfway)
    return firstHalf to secondHalf
}

fun charToScore(inputChar: Char): Int {
    val input = inputChar.code
    if (input <= ASCII_CODE_POINT_START_LOWERCASE) {
        return input - ASCII_CODE_POINT_CAPITAL_OFFSET + UPPERCASE_SCORE_OFFSET
    }
    return input - ASCII_CODE_POINT_START_LOWERCASE;
}