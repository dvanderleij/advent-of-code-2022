package seven

import java.util.regex.Pattern

const val INPUT_FILE = "seven.txt"

// Got a bit bored with this one and tried to do it quickly, so it might be a bit ugly.

fun main() {
    one()
    two()
}

fun one() {
    val rootDirectory = Directory("/", null)

    FileSystem(rootDirectory, common.readAllInputLines(INPUT_FILE)).getSum()
}

fun two() {
    val rootDirectory = Directory("/", null)
    FileSystem(rootDirectory, common.readAllInputLines(INPUT_FILE)).smallestToDelete()
}

val dirPattern: Pattern = Pattern.compile("(\\d+) (.+)")


private class FileSystem(
    private val rootDirectory: Directory,
    private var currentDirectory: Directory,
    private val commands: List<String>,
    private var currentCommandIndex: Int,
    private var sizeNeeded: Int = 0,
    private var sumOfDirsBelow100000: Int = 0,
    private var smallestSizeOfFileToDeleteToAllowUpdate: Int = Integer.MAX_VALUE
) {

    constructor(rootDirectory: Directory, commands: List<String>) : this(
        rootDirectory,
        rootDirectory,
        commands,
        0
    ) {
        while (currentCommandIndex < commands.size) {
            val it = commands[currentCommandIndex]

            when (it.substring(2, 4)) {
                "cd" -> handleCd(it.substring(5))
                "ls" -> handleLs()
            }
        }
        val unused = 70000000 - rootDirectory.size
        sizeNeeded = 30000000 - unused
        print("")
    }


    fun getSum() {
        findSumOfDirsBelow100000(rootDirectory)
        println(sumOfDirsBelow100000)
    }


    private fun findSumOfDirsBelow100000(dir: Directory) {
        dir.files.values.forEach {
            if (it is Directory) {
                val fileSize = it.size
                findSumOfDirsBelow100000(it)
                if (fileSize < 100000) {
                    sumOfDirsBelow100000 += fileSize
                }
            }


        }
    }

    private fun findSmallestSizeOfFileToDeleteToAllowUpdate(dir: Directory) {
        dir.files.values.forEach {
            if (it is Directory) {
                val fileSize = it.size
                findSmallestSizeOfFileToDeleteToAllowUpdate(it)
                if (fileSize in sizeNeeded until smallestSizeOfFileToDeleteToAllowUpdate) {
                    smallestSizeOfFileToDeleteToAllowUpdate = fileSize
                }
            }
        }
    }

    private fun handleCd(directory: String) {
        currentCommandIndex++
        currentDirectory = when (directory) {
            "/" -> rootDirectory
            ".." -> currentDirectory.parentDirectory
            else -> currentDirectory[directory] as Directory
        }
    }

    private fun handleLs() {
        currentCommandIndex++
        var sizeAdded = 0
        while (currentCommandIndex < commands.size && !commands[currentCommandIndex].startsWith("$")) {
            val input = commands[currentCommandIndex]
            val matcher = dirPattern.matcher(input)
            if (matcher.matches()) {
                val name = matcher.group(2)
                val amount = matcher.group(1).toInt()
                currentDirectory[name] = File(name, amount)
                sizeAdded += amount
                currentDirectory.size += amount
            } else {
                val name = input.substring(4)
                currentDirectory[name] = Directory(name, currentDirectory)
            }
            currentCommandIndex++
        }
        var dir = currentDirectory
        while (dir != rootDirectory) {
            dir.parentDirectory.size += sizeAdded
            dir = dir.parentDirectory
        }
    }

    fun smallestToDelete() {
        findSmallestSizeOfFileToDeleteToAllowUpdate(rootDirectory)
        println(smallestSizeOfFileToDeleteToAllowUpdate)
    }


}


private class Directory(name: String, private val _parentDirectory: Directory?) : File(name, 0) {

    val parentDirectory
        get() = _parentDirectory ?: this

    val files: MutableMap<String, File> = mutableMapOf()


    operator fun get(fileName: String): File {
        return files[fileName]!!
    }

    operator fun set(group: String, value: File) {
        files[group] = value
    }
}

private open class File(val name: String, var size: Int) {
    override fun toString(): String {
        return "$name: $size"
    }
}