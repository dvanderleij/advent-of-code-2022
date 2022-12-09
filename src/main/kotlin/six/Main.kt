package six

import java.lang.IllegalArgumentException
import kotlin.io.path.readText

const val INPUT_FILE = "six.txt"
fun main() {
    one()
    two()
}

fun one() {
    println(findFirstMarker(4))

}

fun two() {
    println(findFirstMarker(14))
}


fun findFirstMarker(size: Int): Int {
    val input = common.inputPath(INPUT_FILE).readText()
    val sizeMinusOne = size - 1
    val queue = ArrayDeque<Char>(size)
    queue.addAll(input.substring(0, sizeMinusOne).toCharArray().toTypedArray())

    for ((index, marker) in input.toCharArray().asSequence().drop(sizeMinusOne).withIndex()) {
        queue.add(marker)
        val x = queue.distinct()
        if (x.size == size) {
            return index + size
        }
        queue.removeFirst()
    }
    throw IllegalArgumentException("Non valid input")
}