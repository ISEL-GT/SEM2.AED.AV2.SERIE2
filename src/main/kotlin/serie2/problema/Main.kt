package serie2.problema

import serie2.problema.point.PointUtils

/**
 * Main entry point for the ProcessPointsCollections application.
 *
 * The application supports the following commands (one per line):
 * - setimpl 1        => Uses Implementation1 (Standard Library)
 * - setimpl 2        => Uses Implementation2 (Custom HashMap)
 * - load file1.co file2.co
 * - union output.co
 * - intersection output.co
 * - difference output.co
 * - exit
 *
 * It performs point set operations using two different implementations.
 */

fun main() {
    val impl1 = Implementation1()
    val impl2 = Implementation2()
    var implementationToUse = 1
    var loaded = false

    println("Enter commands (type 'exit' to quit):")

    while (true) {
        val line = readlnOrNull()?.trim() ?: continue
        if (line.isBlank()) continue
        if (line == "exit") break

        val tokens = line.split(" ")
        when (tokens[0]) {
            "setimpl" -> {
                if (tokens.size != 2 || (tokens[1] != "1" && tokens[1] != "2")) {
                    println("Usage: setimpl 1 or setimpl 2")
                    continue
                }
                implementationToUse = tokens[1].toInt()
                println("Switched to Implementation $implementationToUse")
            }

            "load" -> {
                if (tokens.size != 3) {
                    println("Usage: load file1.co file2.co")
                    continue
                }
                val file1 = tokens[1]
                val file2 = tokens[2]
                impl1.loadDocuments1(file1, file2)
                impl2.loadDocuments2(file1, file2)
                loaded = true
                println("Files loaded successfully.")
            }

            "union", "intersection", "difference" -> {
                if (!loaded) {
                    println("Files not loaded. Use the 'load' command first.")
                    continue
                }
                if (tokens.size != 2) {
                    println("Usage: ${tokens[0]} output.co")
                    continue
                }
                val outputFile = tokens[1]

                val result = when (implementationToUse) {
                    1 -> impl1.run {
                        when (tokens[0]) {
                            "union" -> union1()
                            "intersection" -> intersection1()
                            "difference" -> difference1()
                            else -> PointUtils()
                        }
                    }

                    2 -> impl2.run {
                        when (tokens[0]) {
                            "union" -> union2()
                            "intersection" -> intersection2()
                            "difference" -> difference2()
                            else -> PointUtils()
                        }
                    }

                    else -> PointUtils()
                }

                result.writePointsToFile(outputFile)
                println("Result written to $outputFile")
            }

            else -> {
                println("Unknown command: ${tokens[0]}")
            }
        }
    }
}
