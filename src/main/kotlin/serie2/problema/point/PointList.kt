package serie2.problema.point

/**
 * A fixed-size FIFO (First-In, First-Out) list for storing integers with O(1) operations.
 *
 * This implementation uses a circular array and an offset value to efficiently support
 * appending, removing, retrieving, and bulk increment operations in constant time.
 *
 * @property capacity The maximum number of elements the list can store.
 */
class PointList(private val capacity: Int) : Iterable<Point> {
    private val array = emptyArray<Point>() // Stores the integer elements
    private var first = 0                   // Index of the first (oldest) element
    private var last = 0                    // Index of the last element
    private var count = 0                   // The amount of elements in the list

    /**
     * Appends an integer to the end of the list.
     *
     * The value is stored relative to the current offset so that addToAll can be O(1).
     *
     * @param x The integer to append.
     * @return True if the value was added, false if the list is full.
     */
    fun append(x: Point): Boolean {
        if (count == capacity) return false

        array[last + 1] = x
        last = (last + 1) % capacity
        count++
        return true
    }


    /**
     * Retrieves the nth element of the list (0-based index).
     *
     * The value is returned with the current offset applied.
     *
     * @param n The index of the element to retrieve.
     * @return The integer at position n, or null if the index is invalid.
     */
    fun get(n: Int): Point? {
        if (n < 0 || n > capacity) return null
        val index = (first + n) % capacity
        return array[index]
    }

    /**
     * Removes the first (oldest) element from the list.
     *
     * @return True if an element was removed, false if the list is empty.
     */
    fun remove(): Boolean {
        if (count == 0) return false
        first = (first + 1) % capacity // Move start index circularly
        count--
        return true
    }

    /**
     * Returns an iterator over the valid elements in the list in FIFO order.
     *
     * The iterator yields exactly the `size` elements currently stored,
     * starting from the logical start index and applying the current offset
     * to each value.
     *
     * This allows clients to traverse the list as if it were a standard FIFO queue,
     * abstracting away the internal circular array and offset logic.
     *
     * @return An iterator over the list elements in logical order.
     */
    override fun iterator(): Iterator<Point> {
        return object : Iterator<Point> {
            private var index = 0
            override fun hasNext(): Boolean = index < count

            override fun next(): Point {
                if (!hasNext()) throw NoSuchElementException()
                val value = array[(first + index) % capacity]
                index++
                return value
            }

        }
    }
}
