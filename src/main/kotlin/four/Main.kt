package four

import kotlin.io.path.forEachLine


const val INPUT_FILE = "four.txt"

fun main() {
    one()
    two()
}

fun one() {
    var score = 0
    common.inputPath(INPUT_FILE).forEachLine {
        val pairs = it.split(',')
        val range1 = getRange(pairs[0])
        val range2 = getRange(pairs[1])
        if (containsRange(range1, range2) || containsRange(range2, range1)) {
            ++score
        }
    }
    println(score)
}


fun two() {
    var score = 0
    common.inputPath(INPUT_FILE).forEachLine {
        val pairs = it.split(',')
        val range1 = getRange(pairs[0])
        val range2 = getRange(pairs[1])
        // If this intersect call is not smart and iterates over all values in the range then this might be much slower that an optimal solution ;)
        if (range1.intersect(range2).isNotEmpty()) {
            ++score
        }
    }
    println(score)
}

fun containsRange(range1: IntRange, range2: IntRange): Boolean {
    return range1.contains(range2.first) && range1.contains(range2.last)
}

fun getRange(pair: String): IntRange {
    val split = pair.split('-').map { it.toInt() }
    return (split[0]..split[1])
}