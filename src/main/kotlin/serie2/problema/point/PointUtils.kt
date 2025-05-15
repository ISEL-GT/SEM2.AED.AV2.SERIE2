package serie2.problema.point

import java.io.File

/**
 * Represents a list of 2D points and provides methods to load from or save to a file.
 *
 * Expected file format (.co):
 * - Lines defining a point must start with 'v' and follow the format:
 *   `v <id> <x> <y>`
 * - Lines starting with 'c' or 'p' are ignored (comments or metadata).
 */
class PointUtils() {

    companion object {

        /**
         * Reads a .co file and extracts valid points.
         * Ignores lines that don't start with 'v' or are malformed.
         *
         * @param filename The name of the input file (from the data folder)
         * @return A PointList containing parsed points
         */
        fun readPointsFromFile(filename: String): PointList {
            val path = "src/main/resources/data/$filename"
            val lines = File(path).readLines()

            // Parse lines that start with 'v' and contain 3 values: id, x, y
            val points = lines.mapNotNull {
                val parts = it.trim().split(" ")
                if (parts.size == 4 && parts[0] == "v") {
                    val id = parts[1]
                    val x = parts[2].toIntOrNull()
                    val y = parts[3].toIntOrNull()

                    if (x != null && y != null) Point(id, x, y) else null
                } else null
            }

            val pointList = PointList(points.size)
            for (it in points) {
                pointList.append(it)
            }

            return pointList
        }

        /**
         * Writes the list of points to a .co output file in the format:
         * `<id> <x> <y>` (one per line).
         *
         * @param filename The name of the output file (written to outputs folder)
         */
        fun writePointsToFile(filename: String, points: PointList) {
            val outputPath = "src/main/resources/outputs/$filename"

            File(outputPath).printWriter().use { out ->
                for (point in points) {
                    // Write each point in the expected format
                    out.println("${point.id} ${point.x} ${point.y}")
                }
            }
        }
    }

}
