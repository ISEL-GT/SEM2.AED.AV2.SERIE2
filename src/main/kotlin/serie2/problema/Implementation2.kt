package serie2.problema

import serie2.part4.HashMapCustom
import serie2.problema.point.Point
import serie2.problema.point.PointList
import serie2.problema.point.PointUtils2

/**
 * Implementation2 performs operations on sets of 2D points using
 * a custom hash map implementation ("HashMapCustom").
 *
 * This class mimics the standard map-based behavior from Implementation1,
 * but relies entirely on custom internal data structures.
 *
 * Provides:
 * - `loadDocuments2`: Loads two sets of points into custom maps.
 * - `union2`: All unique points from both sets.
 * - `intersection2`: Points common to both sets.
 * - `difference2`: Points in the first set not in the second.
 */
class Implementation2 {
    private var map1 = HashMapCustom<String, Point>() // First point set (ID → Point)
    private var map2 = HashMapCustom<String, Point>() // Second point set (ID → Point)

    /**
     * Loads two point sets from .co files into custom hash maps.
     *
     * @param file1 Name of the first .co file
     * @param file2 Name of the second .co file
     */
    fun loadDocuments2(file1: String, file2: String) {
        map1 = HashMapCustom()
        map2 = HashMapCustom()

        // Load points from the first file
        for (p in PointUtils2.readPointsFromFile(file1))
            map1.put(p.id, p)

        // Load points from the second file
        for (p in PointUtils2.readPointsFromFile(file2))
            map2.put(p.id, p)
    }

    /**
     * Returns the union of both point sets (all unique points).
     *
     * @return A PointList containing all unique points
     */
    fun union2(): PointList {
        val pointMap = HashMapCustom<String, Point>()

        // Insert all entries from the first map
        for (entry in map1) pointMap.put(entry.key, entry.value)

        // Insert entries from the second map (overwrites duplicates)
        for (entry in map2) pointMap.put(entry.key, entry.value)

        val result = PointList(pointMap.size)
        for (entry in pointMap) result.append(entry.value)

        return result // Convert map values to a PointList
    }

    /**
     * Returns the intersection of the two point sets
     * (only points that exist in both maps by ID).
     *
     * @return A PointList of common points
     */
    fun intersection2(): PointList {
        val result = PointList(map1.size)

        // Check each key from map1 and add only if present in map2
        for (entry in map1) {
            if (map2.containsKey(entry.key)) {
                result.append(entry.value)
            }
        }

        return result
    }

    /**
     * Returns the difference between the first and second point sets
     * (points in map1 that are not in map2).
     *
     * @return A PointList of unique points from the first map
     */
    fun difference2(): PointList {
        val result = PointList(map1.size)

        // Add points from map1 only if the key is not found in map2
        for (entry in map1) {
            if (!map2.containsKey(entry.key)) {
                result.append(entry.value)
            }
        }

        return result
    }
}
