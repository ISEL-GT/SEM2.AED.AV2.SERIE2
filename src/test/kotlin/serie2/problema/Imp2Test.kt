package serie2.problema

import serie2.problema.point.Point
import kotlin.test.*

class Implementation2Test {

    @Test
    fun testUnion2Operation() {
        val file1 = "Test1.co"
        val file2 = "Test2.co"

        val impl = Implementation2()
        impl.loadDocuments2(file1, file2)

        val expectedUnion = setOf(
            Point("1", 1, 2),
            Point("2", 2, 3),
            Point("3", 4, 5),
            Point("4", 5, 6),
            Point("5", 7, 8)
        )

        val unionResult = impl.union2().points.toSet()
        assertEquals(expectedUnion, unionResult)
    }

    @Test
    fun testIntersection2Operation() {
        val file1 = "Test1.co"
        val file2 = "Test2.co"

        val impl = Implementation2()
        impl.loadDocuments2(file1, file2)

        val expectedIntersection = setOf(
            Point("2", 2, 3)
        )

        val intersectionResult = impl.intersection2().points.toSet()
        assertEquals(expectedIntersection, intersectionResult)
    }

    @Test
    fun testDifference2Operation() {
        val file1 = "Test1.co"
        val file2 = "Test2.co"

        val impl = Implementation2()
        impl.loadDocuments2(file1, file2)

        val expectedDifference = setOf(
            Point("1", 1, 2),
            Point("3", 4, 5)
        )

        val differenceResult = impl.difference2().points.toSet()
        assertEquals(expectedDifference, differenceResult)
    }
}
