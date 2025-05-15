package serie2.problema

import serie2.problema.point.Point
import serie2.problema.point.PointUtils

/**
 * Implementation1 performs set operations on 2D points using Kotlin
 * standard library collections such as HashMap and Set.
 *
 * This class provides:
 * - `loadDocuments1`: Loads two point sets from files.
 * - `union1`: Returns all unique points from both sets.
 * - `intersection1`: Returns points common to both sets.
 * - `difference1`: Returns points in the first set not in the second.
 *
 * The identity of a point is determined by its `id`.
 */
class Implementation1 {
    private var points1 = PointUtils() // Stores points from the first input file
    private var points2 = PointUtils() // Stores points from the second input file

    /**
     * Loads two point sets from the specified input files.
     *
     * @param file1 Path to the first .co file
     * @param file2 Path to the second .co file
     */
    fun loadDocuments1(file1: String, file2: String) {
        points1 = PointUtils.readPointsFromFile(file1)
        points2 = PointUtils.readPointsFromFile(file2)
    }

    /**
     * Returns the union of both point sets.
     * Only unique points are included (based on ID).
     *
     * @return A PointList containing all unique points from both sets
     */
    fun union1(): PointUtils {
        // Combine both point sets into a HashMap to eliminate duplicates by ID
        val map = HashMap<String, Point>()

        // Add points from the first set
        for (p in points1.points) map[p.id] = p

        // Add/overwrite points from the second set
        for (p in points2.points) map[p.id] = p

        // Return the values (unique points) as a new PointList
        return PointUtils(map.values.toList())
    }

    /**
     * Returns the intersection of both point sets.
     * Only points that exist in both sets (by ID) are included.
     *
     * @return A PointList of common points
     */
    fun intersection1(): PointUtils {
        // Build a set of IDs from the second point set
        val set2 = points2.points.map { it.id }.toSet()

        // Filter points from the first set that are also in set2
        val result = points1.points.filter { it.id in set2 }

        return PointUtils(result)
    }

    /**
     * Returns the difference between the first and second point sets.
     * Only points that exist in the first set and not in the second are included.
     *
     * @return A PointList containing points only in the first set
     */
    fun difference1(): PointUtils {
        // Build a set of IDs from the second point set
        val set2 = points2.points.map { it.id }.toSet()

        // Filter points from the first set whose ID is not in set2
        val result = points1.points.filter { it.id !in set2 }

        return PointUtils(result)
    }
}
