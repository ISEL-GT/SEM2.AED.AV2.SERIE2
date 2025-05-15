package serie2.part4

/**
 * A basic implementation of a hash table using separate chaining (linked lists in each bucket)
 * to handle collisions. It implements the MutableMap interface.
 *
 * @param initialCapacity The initial number of buckets in the hash table.
 * @param loadFactor The maximum load factor before the table is resized (expanded).
 */
class HashMapCustom<K, V>(
    initialCapacity: Int = 16,
    private val loadFactor: Float = 0.75f
) : CustomMutableMap<K, V> {

    /**
     * Internal node used to store key-value pairs in the hash table.
     * Each node is linked to the next one on the same bucket.
     */
    private class HashNode<K, V>(
        override val key: K,
        override var value: V,
        var next: HashNode<K, V>? = null
    ) : CustomMutableMap.MutableEntry<K, V> {

        val hashcode = key.hashCode()

        /**
         * Updates the value associated with this node.
         *
         * @param newValue The new value to be set.
         * @return The old value that was replaced.
         */
        override fun setValue(newValue: V): V {
            val old = value
            value = newValue
            return old
        }
    }

    // Array of buckets (each bucket is a singly linked list of HashNodes)
    private var table: Array<HashNode<K, V>?> = arrayOfNulls(initialCapacity)
    private var _size = 0

    /**
     * Gets the number of elements in the map.
     *
     * @return The size of the map.
     */
    override val size: Int get() = _size

    /**
     * Gets the current capacity (number of buckets) in the table.
     *
     * @return The current capacity.
     */
    override val capacity: Int get() = table.size

    /**
     * Retrieves the value associated with the given key, or null if the key is not found.
     *
     * @param key The key to search for in the map.
     * @return The value associated with the key, or null if not found.
     */
    override fun get(key: K): V? {
        val hashcode = key.hashCode()                  // Compute the hash code of the key
        val index = indexFor(hashcode)                 // Compute the index in the bucket array
        val node = findNode(index, key, hashcode)      // Search for the node
        return node?.value                             // Return its value or null
    }

    /**
     * Finds a node in the specified bucket index that matches the given key and hash code.
     *
     * @param index The index of the bucket to search.
     * @param key The key to search for.
     * @param hashcode The hash code of the key.
     * @return The node containing the key, or null if not found.
     */
    private fun findNode(index: Int, key: K, hashcode: Int = key.hashCode()): HashNode<K, V>? {
        var node = table[index]
        while (node != null) {
            if (node.hashcode == hashcode && node.key == key) return node
            node = node.next
        }
        return null
    }

    /**
     * Inserts a new key-value pair into the map, or updates the value if the key already exists.
     *
     * @param key The key to be inserted or updated.
     * @param value The value associated with the key.
     * @return The old value if the key was already present, otherwise null.
     */
    override fun put(key: K, value: V): V? {
        val hc = key.hashCode()                        // Compute hash code
        val index = indexFor(hc)                       // Get the index for the bucket
        val existingNode = findNode(index, key, hc)    // Look for an existing node

        if (existingNode != null) {
            // Key exists: update value and return old value
            return existingNode.setValue(value)
        }

        // Key not found: insert a new node at the head of the list
        val newNode = HashNode(key, value, table[index])
        table[index] = newNode
        _size++                                        // Increase element count

        // Resize the table if the current load exceeds the threshold
        if (_size > capacity * loadFactor) expand()

        return null
    }

    /**
     * Removes the entry for the given key and returns its value, or null if not found.
     *
     * @param key The key to remove from the map.
     * @return The value associated with the key, or null if the key was not found.
     */
    override fun remove(key: K): V? {
        val hc = key.hashCode()                        // Compute hash code
        val index = indexFor(hc)                       // Locate the bucket
        var node = table[index]                        // Current node in the list
        var prev: HashNode<K, V>? = null               // Previous node (for unlinking)

        while (node != null) {
            if (node.hashcode == hc && node.key == key) {
                // Remove node from list
                if (prev == null) {
                    table[index] = node.next
                } else {
                    prev.next = node.next
                }
                _size--                                // Decrease element count
                return node.value                      // Return the removed value
            }
            prev = node
            node = node.next
        }
        return null
    }

    /**
     * Checks whether the map contains the given key.
     *
     * @param key The key to check for.
     * @return True if the map contains the key, false otherwise.
     */
    override fun containsKey(key: K): Boolean {
        val index = indexFor(key.hashCode())            // Get the index for the key
        return findNode(index, key) != null             // Return true if the key is found in the bucket
    }

    /**
     * Checks whether the map contains the given value.
     *
     * @param value The value to check for.
     * @return True if the map contains the value, false otherwise.
     */
    override fun containsValue(value: V): Boolean {
        for (bucket in table) {
            var node = bucket
            // Traverse the linked list in each bucket to find the value
            while (node != null) {
                if (node.value == value) return true // Return true if the value is found
                node = node.next // Move to the next node in the list
            }
        }
        return false // Return false if the value is not found in any bucket
    }

    /**
     * Clears the map by removing all elements.
     * This operation resets the map to its initial state.
     */
    override fun clear() {
        table = arrayOfNulls(capacity)                 // Reset buckets
        _size = 0                                      // Reset size
    }

    /**
     * Computes the index in the table array for a given hash code.
     * Ensures the index is non-negative and within bounds.
     *
     * @param hc The hash code of the key.
     * @return The computed index for the hash code.
     */
    private fun indexFor(hc: Int): Int = hc.mod(capacity)

    /**
     * Doubles the size of the table and rehashes all existing entries into the new table.
     * This operation is called when the map's load factor exceeds the threshold.
     */
    private fun expand() {
        val oldTable = table
        table = arrayOfNulls(oldTable.size * 2)    // Create larger table
        reinsertAll(oldTable)                          // Reinsert all existing nodes
    }

    /**
     * Reinserts all nodes from the old table into the new (resized) table.
     * Preserves key-value associations with updated indexes.
     *
     * @param oldTable The old table to rehash from.
     */
    private fun reinsertAll(oldTable: Array<HashNode<K, V>?>) {
        for (bucket in oldTable) {
            var node = bucket
            while (node != null) {
                val next = node.next                   // Store reference to next node
                val index = indexFor(node.hashcode)    // Recompute index for new table
                node.next = table[index]               // Insert at head of new bucket
                table[index] = node
                node = next                            // Continue with next node
            }
        }
    }

    /**
     * Returns an iterator over all entries in the map.
     * It goes through all buckets and all nodes in each bucket.
     *
     * @return An iterator over all entries in the map.
     */
    override fun iterator(): Iterator<CustomMutableMap.MutableEntry<K, V>> =
        object : Iterator<CustomMutableMap.MutableEntry<K, V>> {
            var bucketIndex = 0                        // Index of the current bucket
            var current: HashNode<K, V>? = null        // Current node in the bucket

            override fun hasNext(): Boolean {
                // Move to the next non-empty bucket if needed
                while (current == null && bucketIndex < table.size) {
                    current = table[bucketIndex++]
                }
                return current != null
            }

            override fun next(): CustomMutableMap.MutableEntry<K, V> {
                val result = current!!                 // Return the current node
                current = current!!.next               // Advance to the next node
                return result
            }
        }
    /**
     * Retrieves all the values stored in the map.
     *
     * This function iterates through all the buckets of the internal hash table
     * and collects the values of each key-value pair into a list.
     *
     * @return A list containing all values currently stored in the map.
     */
    fun values(): List<V> {
        val values = mutableListOf<V>()
        for (bucket in table) {
            var node = bucket
            while (node != null) {
                values.add(node.value) // Add the value of the current node
                node = node.next       // Move to the next node in the chain
            }
        }
        return values
    }

}
