package serie2.part3

/**
 * Auxiliary function that removes the node.
 */
fun <T> removeNode(node: Node<T>) {
    node.previous!!.next = node.next
    node.next!!.previous = node.previous
}

/**
 * Auxiliary function that adds the  after the reference.
 */
fun <T> addAfter(reference: Node<T>, node: Node<T>) {
    node.next = reference.next
    node.previous = reference
    reference.next!!.previous = node
    reference.next = node
}

/**
 * Splits the doubly linked circular list with sentinel so that all even numbers
 * are moved to the front of the list, grouped consecutively in any order.
 * The list structure is preserved and no new nodes are created.
 *
 * @param list The circular doubly linked list with sentinel node.
 */
fun splitEvensAndOdds(list: Node<Int>) {
    // Start from the first real node (immediately after the sentinel)
    var current = list.next

    // Traverse the list until we circle back to the sentinel node
    while (current != list) {
        // Save the next node before modifying the list
        val next = current!!.next

        // Check if the current node's value is even
        if (current.value % 2 == 0) {
            // Detach the current node from its current position
            // Example: List = [Sentinel ⇄ A ⇄ B ⇄ C ⇄ Sentinel] with current = B
            // After this line, B will be removed from the list, and the list will look like:
            // [Sentinel ⇄ A ⇄ C ⇄ Sentinel]
            removeNode(current)

            // Move the current node right after the sentinel (at the front of the list)
            // Example: After detaching B, the new list will be:
            // [Sentinel ⇄ B ⇄ A ⇄ C ⇄ Sentinel]
            // B is now placed right after the sentinel, and A is still next to it
            addAfter(list, current)
        }

        // Move to the next node (as stored before any modification)
        current = next
    }
}

/**
 * Finds the intersection between two sorted circular doubly linked lists with sentinels.
 * Removes the intersected nodes from list1 and list2, reuses those nodes to build a new
 * non-circular doubly linked list without sentinel and without duplicates.
 *
 * @param list1 First sorted list with sentinel
 * @param list2 Second sorted list with sentinel
 * @param cmp Comparator for elements of type T
 * @return A new list (no sentinel, not circular) containing the common elements
 */
fun <T> intersection(list1: Node<T>, list2: Node<T>, cmp: Comparator<T>): Node<T>? {
    // Initialize the head and tail of the result list (a non-circular list without a sentinel)
    var resultHead: Node<T>? = null
    var resultTail: Node<T>? = null

    // Start from the first actual nodes (skip sentinels) in each circular list
    var current1 = list1.next
    var current2 = list2.next

    // Traverse both lists as long as neither reaches back to its sentinel
    while (current1 != list1 && current2 != list2) {
        // Compare current values using the provided comparator
        val comparison = cmp.compare(current1!!.value, current2!!.value)

        if (comparison == 0) {
            // The values are equal, we found a common element
            val commonValue = current1.value

            // Save next pointers before detaching the nodes
            var next1 = current1.next
            var next2 = current2.next

            // Detach current1 from list1
            removeNode(current1)

            // Detach current2 from list2
            removeNode(current2)

            // Reuse current1 node and append it to the result list
            current1.previous = resultTail
            current1.next = null

            // If result list is empty, initialize head and tail
            if (resultHead == null) {
                resultHead = current1
                resultTail = current1
            } else {
                // Otherwise, append to the end and update tail
                resultTail!!.next = current1
                resultTail = current1
            }

            // Skip any duplicate values in list1
            while (next1 != list1 && cmp.compare(next1!!.value, commonValue) == 0) {
                next1 = next1.next
            }

            // Skip any duplicate values in list2
            while (next2 != list2 && cmp.compare(next2!!.value, commonValue) == 0) {
                next2 = next2.next
            }

            // Move to the next non-duplicate elements in both lists
            current1 = next1
            current2 = next2
        } else if (comparison < 0) {
            // current1 is smaller, advance in list1
            current1 = current1.next
        } else {
            // current2 is smaller, advance in list2
            current2 = current2.next
        }
    }

    // Return the head of the result list (can be null if no intersection)
    return resultHead
}

// Node class definition provided by the assignment
@Suppress("UNCHECKED_CAST")
class Node<T> (
    var value: T = Any() as T,
    var next: Node<T>? = null,
    var previous: Node<T>? = null
)

// Test function to create a circular list with sentinel so we can use the
fun createCircularListWithSentinel(values: List<Int>): Node<Int> {
    val sentinel = Node<Int>() // Sentinel node
    sentinel.next = sentinel
    sentinel.previous = sentinel

    var last = sentinel
    for (v in values) {
        val newNode = Node(v)
        newNode.previous = last
        newNode.next = sentinel
        last.next = newNode
        sentinel.previous = newNode
        last = newNode
    }
    return sentinel
}

// Auxiliary function to print circular list without sentinel
fun <T> printCircularList(list: Node<T>): String {
    val result = mutableListOf<T>()
    var current = list.next
    while (current != list) {
        result.add(current!!.value)
        current = current.next
    }
    return result.toString()
}

fun randomIntList(size: Int, max: Int, min: Int = 0): List<Int> {
    return List(size) { (min until max).random() }
}

fun <T> printLinearList(list: Node<T>?): String {
    val result = mutableListOf<T>()
    var current = list
    while (current != null) {
        result.add(current.value)
        current = current.next
    }
    return result.toString()
}

fun main() {
    println("Test splitEvens")
    val randomList = randomIntList(size = 10, max = 100)
    println("Random List:\n $randomList")
    val circularList = createCircularListWithSentinel(randomList)
    splitEvensAndOdds(circularList)

    println("After splitEvensAndOdds():\n ${printCircularList(circularList)}")

    println("\nTest intersection")
    val list1 = listOf(11, 6, 15, 10, 1, 7, 15, 12, 0, 13).sorted()
    val list2 = listOf(5, 17, 7, 12, 18, 8, 11, 13, 11, 19).sorted()

    val circularList1 = createCircularListWithSentinel(list1)
    val circularList2 = createCircularListWithSentinel(list2)

    val intersectionList = intersection(circularList1, circularList2, Comparator.naturalOrder())

    println("List1: $list1")
    println("List2: $list2")
    println("Intersection: ${printLinearList(intersectionList)}")
}
