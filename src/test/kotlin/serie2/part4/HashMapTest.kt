package serie2.part4

import kotlin.test.*

class HashMapTest {

    // Test put one entry and get value
    @Test
    fun testEmpty() {
        val map = HashMapCustom<Int, String>()
        assertEquals(0, map.size)
        assertNull(map[0])
    }

    // Test put one entry and get value
    @Test
    fun testPutAndGetOneEntry() {
        val map = HashMapCustom<Int, String>()
        assertNull(map.put(1, "one"))
        assertEquals(1, map.size)
        assertEquals("one", map[1])
    }

    // Test put and get
    @Test
    fun testPutAndGet() {
        val map = HashMapCustom<Int, String>()
        assertNull(map.put(1, "one"))
        assertNull(map.put(2, "two"))
        assertNull(map.put(3, "three"))
        assertEquals(3, map.size)
        assertEquals("one", map[1])
        assertEquals("three", map[3])
        assertEquals("two", map[2])
    }

    // Test put and get with same key
    @Test
    fun testPutAndGetWithSameKey() {
        val map = HashMapCustom<Int, String>()
        assertNull(map.put(1, "one"))
        assertNull(map.put(2, "three"))
        assertEquals(2, map.size)

        assertEquals("one", map[1])
        assertEquals("one", map.put(1, "two"))
        assertEquals("two", map[1])
        assertEquals(2, map.size)
    }

    // Test Iterator with empty map
    @Test
    fun testIteratorWithEmptyMap() {
        val map = HashMapCustom<Int, String>()
        assertFalse(map.iterator().hasNext())
        var count = 0
        for (entry in map) {
            ++count
        }
        assertEquals(0, count)
    }

    // Test Iterator
    @Test
    fun testIterator() {
        val map = HashMapCustom<Int, String>()
        assertNull(map.put(1, "one"))
        assertNull(map.put(2, "two"))
        assertNull(map.put(3, "three"))
        assertTrue(map.iterator().hasNext())
        val entries = mutableListOf<CustomMutableMap.MutableEntry<Int, String>>()
        for (entry in map) {
            entries.add(entry)
        }
        entries.sortBy { it.key }
        assertEquals(3, entries.size)
        for (i in entries.indices) {
            assertEquals(i + 1, entries[i].key)
            assertEquals(map[i + 1], entries[i].value)
        }
    }

    // Test expand
    @Test
    fun testExpand() {
        val initCap = 5
        val map = HashMapCustom<Int, String>(initCap, 0.25F)
        for (i in 1..initCap * 5 + 1) {
            val cap = map.capacity
            assertNull(map.put(i, i.toString()))
            assertEquals(i.toString(), map[i])
            if (cap + 1 == map.size) {
                assertEquals(cap * 2, map.capacity)
                println("Size ${map.size}: $cap - Capacity doubled to ${map.capacity}")
            }
        }
    }
}
