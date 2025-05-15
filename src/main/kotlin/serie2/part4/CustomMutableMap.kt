package serie2.part4

/**
 * Interface defining a mutable map data structure, supporting basic operations like
 * get, put, and iteration over entries.
 */
interface CustomMutableMap<K, V> : Iterable<CustomMutableMap.MutableEntry<K, V>> {

    /**
     * Represents a key-value pair in the map.
     * Allows reading the key, reading/modifying the value.
     */
    interface MutableEntry<K, V> {
        val key: K       // The key associated with this entry
        var value: V     // The current value associated with the key

        /**
         * Updates the value and returns the previous value.
         */
        fun setValue(newValue: V): V
    }

    val size: Int       // Number of key-value pairs in the map
    val capacity: Int   // Total number of buckets in the map's internal structure

    /**
     * Retrieves the value for the given key, or null if not found.
     */
    operator fun get(key: K): V?

    /**
     * Inserts a new key-value pair or updates the existing one.
     *
     * @return The old value if the key existed, or null otherwise.
     */
    fun put(key: K, value: V): V?
    fun remove(key: K): V?
    fun containsKey(key: K): Boolean
    fun containsValue(value: V): Boolean
    fun clear()
}
