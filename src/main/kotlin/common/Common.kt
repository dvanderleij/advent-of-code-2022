package common

import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths

val INPUT_DIRECTORY: Path = Paths.get("src")
    .resolve("main")
    .resolve("resources")


fun readAllInputLines(fileName: String): List<String> {
    return Files.readAllLines(inputPath(fileName));
}

fun inputPath(fileName: String): Path {
    return INPUT_DIRECTORY.resolve(fileName)
}