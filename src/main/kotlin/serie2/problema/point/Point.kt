package serie2.problema.point

/**
 * Represents a 2D point in the plane with an identifier and coordinates.
 *
 * @property id the unique identifier of the point.
 * @property x the x-coordinate of the point.
 * @property y the y-coordinate of the point.
 */
data class Point(
    val id: String,  // Unique identifier for the point
    val x: Int,      // X-coordinate of the point
    val y: Int       // Y-coordinate of the point
)

