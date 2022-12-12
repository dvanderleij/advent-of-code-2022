package eight

const val INPUT_FILE = "eight.txt"

// Trying to experiment a bit with lambdas and such maybe a bit unreadable
fun main() {
    one()
    two()
}

fun one() {
    val inputForest = common.readAllInputLines(INPUT_FILE).stream().map { line ->
        line.toCharArray().map { Tree(it.digitToInt()) }
    }.toList()

    val rowColumnIndexing = { forest: List<List<Tree>>, row: Int, column: Int -> forest[row][column] }
    val columnRowIndexing = { forest: List<List<Tree>>, row: Int, column: Int -> forest[column][row] }


    val ascending = 0 until inputForest.size
    val descending = inputForest.size - 1 downTo 0
    val leftToRight = Triple(ascending, ascending, rowColumnIndexing)
    val rightToLeft = Triple(ascending, descending, rowColumnIndexing)
    val upToDown = Triple(ascending, ascending, columnRowIndexing)
    val downToUp = Triple(ascending, descending, columnRowIndexing)

    val amountOfVisibleTrees = listOf(leftToRight, rightToLeft, upToDown, downToUp).sumOf { (first, second, third) ->
        countVisibleTreesForDirection(
            inputForest, first, second, third
        )
    }
    println(amountOfVisibleTrees)
}

fun countVisibleTreesForDirection(
    forest: List<List<Tree>>,
    progression1: IntProgression,
    progression2: IntProgression,
    treeRetrieval: (forest: List<List<Tree>>, row: Int, column: Int) -> Tree
): Int {

    var visibleTreesNumber = 0

    for (i in progression1) {
        var highest = Integer.MIN_VALUE
        for (j in progression2) {
            val tree = treeRetrieval(forest, i, j)
            if (tree.size > highest) {
                highest = tree.size
                if (!tree.isVisible) {
                    tree.isVisible = true
                    ++visibleTreesNumber
                }
            }
        }
    }
    return visibleTreesNumber
}

data class Tree(val size: Int, var isVisible: Boolean = false)

fun two() {
    val forest = common.readAllInputLines(INPUT_FILE).stream().map { line ->
        line.toCharArray().map { Tree(it.digitToInt()) }
    }.toList()


    var max = 0
    for (row in 0 until forest.size) {
        for (column in 0 until forest.size) {
            val score = getVisibility(forest, row, column)
            if (score > max) {
                max = score
            }
        }
    }
    println(max)
}

fun getVisibility(forest: List<List<Tree>>, treeRow: Int, treeColumn: Int): Int {
    val treeSize = forest[treeRow][treeColumn].size

    val vertical = { row: Int -> forest[row][treeColumn].size }
    val horizontal = { column: Int -> forest[treeRow][column].size }

    val columnSize = forest.size;
    val rowSize = forest[0].size

    val nextColumn = treeColumn + 1
    val previousColumn = treeColumn - 1

    val nextRow = treeRow + 1
    val previousRow = treeRow - 1

    val leftToRight = Pair(
        nextColumn until columnSize, horizontal
    )
    val rightToLeft = Pair(previousColumn downTo 0, horizontal)
    val upToDown = Pair(nextRow until rowSize, vertical)
    val downToUp = Pair(previousRow downTo 0, vertical)

    return listOf(downToUp, rightToLeft, upToDown, leftToRight).map { (range, retrievalFunction) ->
        lookInDirection(
            treeSize, range, retrievalFunction
        )
    }.reduce { one, two -> one * two }

}

fun lookInDirection(treeToCheck: Int, range: IntProgression, treeRetrieval: (index: Int) -> Int): Int {
    var value = 0
    for (index in range) {
        val treeInDirection = treeRetrieval(index)
        ++value
        if (treeInDirection >= treeToCheck) {
            break
        }

    }
    return value
}
