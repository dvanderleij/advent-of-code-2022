import java.io.File

const val INPUT_PATH = "src/main/resources/1.txt"

fun main() {
    one()
    two()
}


fun one() {
    var currentCalories = 0;
    var maxCalories = Int.MIN_VALUE
    File(INPUT_PATH).forEachLine {
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
    File(INPUT_PATH).forEachLine {
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