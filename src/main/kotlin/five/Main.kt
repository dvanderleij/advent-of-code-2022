package five

import java.nio.file.Files
import java.util.regex.Pattern

// This one is quite ugly and a bit of a mess and i don't feel like cleaning it up :)

const val INPUT_FILE = "five.txt"
val MOVEMENT_INSTRUCTION_PATTERN: Pattern = Pattern.compile("move (\\d+) from (\\d+) to (\\d+)")
fun main() {
    one()
    println()
    two()
}

fun one() {
    val columnsOfCrates = mutableListOf<ArrayDeque<Char>>()

    Files.newBufferedReader(common.inputPath(INPUT_FILE)).use {
        val iterator = it.lineSequence().iterator()
        var line = iterator.next()
        while (line.contains("[")) {
            val chars = line.toCharArray()
            for ((column, i) in (chars.indices step 4).withIndex()) {
                val letter = chars[i + 1]
                if (columnsOfCrates.size <= column) {
                    columnsOfCrates.add(ArrayDeque())
                }
                if (letter != ' ') {
                    columnsOfCrates[column].add(letter)
                }
            }
            line = iterator.next()
        };
        iterator.next()

        iterator.forEachRemaining {
            val matcher = MOVEMENT_INSTRUCTION_PATTERN.matcher(it);
            matcher.matches()
            val amountToMove = matcher.group(1).toInt()
            val originColumnIndex = matcher.group(2).toInt() - 1
            val destinationColumnIndex = matcher.group(3).toInt() - 1
            for (i in 0 until amountToMove) {
                val originColumn = columnsOfCrates[originColumnIndex]
                val destinationColumn = columnsOfCrates[destinationColumnIndex]
                val topCrateOrigin = originColumn.removeFirst()
                destinationColumn.addFirst(topCrateOrigin)

            }


        }
        columnsOfCrates.forEach { print(it.first()) }

    }


}


fun two() {
    val columnsOfCrates = mutableListOf<ArrayDeque<Char>>()

    Files.newBufferedReader(common.inputPath(INPUT_FILE)).use {
        val iterator = it.lineSequence().iterator()
        var line = iterator.next()
        while (line.contains("[")) {

            val chars = line.toCharArray()
            for ((column, i) in (chars.indices step 4).withIndex()) {
                val letter = chars[i + 1]
                if (columnsOfCrates.size <= column) {
                    columnsOfCrates.add(ArrayDeque())
                }
                if (letter != ' ') {
                    columnsOfCrates[column].add(letter)
                }
            }
            line = iterator.next()
        };
        iterator.next()

        iterator.forEachRemaining {
            val matcher = MOVEMENT_INSTRUCTION_PATTERN.matcher(it);
            matcher.matches()
            val amountToMove = matcher.group(1).toInt()
            val originColumnIndex = matcher.group(2).toInt() - 1
            val destinationColumnIndex = matcher.group(3).toInt() - 1


            val originColumn = columnsOfCrates[originColumnIndex]
            val destinationColumn = columnsOfCrates[destinationColumnIndex]
            val topCratesOrigin = (0 until amountToMove).map { originColumn.removeFirst() }

            topCratesOrigin.reversed().forEach { destinationColumn.addFirst(it) }

        }

    }
    columnsOfCrates.forEach { print(it.first()) }
}
