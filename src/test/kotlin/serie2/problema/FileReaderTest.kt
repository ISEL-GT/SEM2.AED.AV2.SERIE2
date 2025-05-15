package serie2.problema

import serie2.problema.point.Point
import serie2.problema.point.PointUtils2
import kotlin.test.*

class FileReaderTest {

    @Test
    fun testReadPoints() {
        val pointMap = PointUtils2.readPointsFromFile("Test1.co")

        val expectedPoints = setOf(
            Point("1", 1, 2),
            Point("2", 2, 3),
            Point("3", 4, 5)
        )

        assertEquals(expectedPoints, pointMap.toSet())
    }
}
