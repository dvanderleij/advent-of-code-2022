package one

import kotlin.io.path.forEachLine

const val INPUT_FILE = "one.txt"

fun main() {
    one()
    two()
}

fun one() {
    var currentCalories = 0;
    var maxCalories = Int.MIN_VALUE
    common.inputPath(INPUT_FILE).forEachLine {
        if (it == "") {
            if (currentCalories > maxCalories) {
                maxCalories = currentCalories
            }
            currentCalories = 0
        } else {
            currentCalories += it.toInt()
        }
    }
    println(maxCalories)
}

// We could just get the top 3 from a list, but where is the fun in that?
// This implementation does not require keeping all values in memory having minimal space complexity
fun two() {
    val largestCalorieCounts = IntArray(3) { Int.MIN_VALUE }
    var currentCalories = 0;
    common.inputPath(INPUT_FILE).forEachLine {
        if (it.isEmpty()) {
            val smallestLargest = largestCalorieCounts.min()
            if (currentCalories > smallestLargest) {
                largestCalorieCounts[largestCalorieCounts.indexOf(smallestLargest)] = currentCalories
            }
            currentCalories = 0
        } else {
            currentCalories += it.toInt()
        }
    }
    println(largestCalorieCounts.sum())
}