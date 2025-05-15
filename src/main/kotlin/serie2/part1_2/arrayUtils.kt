package serie2.part1_2

/**
 * Finds the minimum (smallest) element in a max heap.
 *
 * In a max heap, the smallest element is guaranteed to be in one of the leaf nodes.
 * Since the heap is stored as an array, the leaf nodes start at index heapSize / 2.
 *
 * This function efficiently searches only among the leaves to find the minimum value.
 *
 * @param maxHeap The array representing the max heap.
 * @param heapSize The number of valid elements in the heap (i.e., the heap's size).
 * @return The minimum element found among the leaves of the max heap.
 */
fun minimum(maxHeap: Array<Int>, heapSize: Int): Int {
    // Assume the first leaf is the minimum initially
    var min = maxHeap[heapSize / 2]

    // Iterate through all the remaining leaves (from heapSize/2 + 1 to heapSize - 1)
    for (i in (heapSize / 2 + 1) until heapSize) {
        // Update the minimum if a smaller leaf is found
        if (maxHeap[i] < min) {
            min = maxHeap[i]
        }
    }

    // Return the smallest value found among the leaves
    return min
}

