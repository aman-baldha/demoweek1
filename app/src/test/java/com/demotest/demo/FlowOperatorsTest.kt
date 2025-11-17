package com.demotest.demo

import app.cash.turbine.test
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class FlowOperatorsTest {

    @Test
    fun `map operator transforms values correctly`() = runTest {
        // Given
        val flow = flowOf(1, 2, 3, 4, 5)

        // When
        flow.map { it * 2 }.test {
            // Then
            assertEquals(2, awaitItem())
            assertEquals(4, awaitItem())
            assertEquals(6, awaitItem())
            assertEquals(8, awaitItem())
            assertEquals(10, awaitItem())
            awaitComplete()
        }
    }

    @Test
    fun `filter operator filters values correctly`() = runTest {
        // Given
        val flow = flowOf(1, 2, 3, 4, 5, 6)

        // When
        flow.filter { it % 2 == 0 }.test {
            // Then
            assertEquals(2, awaitItem())
            assertEquals(4, awaitItem())
            assertEquals(6, awaitItem())
            awaitComplete()
        }
    }

    @Test
    fun `take operator limits emissions`() = runTest {
        // Given
        val flow = flowOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)

        // When
        flow.take(3).test {
            // Then
            assertEquals(1, awaitItem())
            assertEquals(2, awaitItem())
            assertEquals(3, awaitItem())
            awaitComplete()
        }
    }

    @Test
    fun `drop operator skips first N items`() = runTest {
        // Given
        val flow = flowOf(1, 2, 3, 4, 5)

        // When
        flow.drop(2).test {
            // Then
            assertEquals(3, awaitItem())
            assertEquals(4, awaitItem())
            assertEquals(5, awaitItem())
            awaitComplete()
        }
    }

    @Test
    fun `transform operator can emit multiple values`() = runTest {
        // Given
        val flow = flowOf(1, 2, 3)

        // When
        flow.transform { value ->
            emit("Before: $value")
            emit("After: $value")
        }.test {
            // Then
            assertEquals("Before: 1", awaitItem())
            assertEquals("After: 1", awaitItem())
            assertEquals("Before: 2", awaitItem())
            assertEquals("After: 2", awaitItem())
            assertEquals("Before: 3", awaitItem())
            assertEquals("After: 3", awaitItem())
            awaitComplete()
        }
    }

    @Test
    fun `combine operator combines latest values`() = runTest {
        // Given
        val flow1 = flowOf("A", "B", "C")
        val flow2 = flowOf(1, 2, 3)

        // When
        combine(flow1, flow2) { a, b -> "$a$b" }.test {
            // Then
            assertNotNull(awaitItem())
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `zip operator pairs values`() = runTest {
        // Given
        val flow1 = flowOf("A", "B", "C")
        val flow2 = flowOf(1, 2, 3)

        // When
        flow1.zip(flow2) { a, b -> "$a$b" }.test {
            // Then
            assertEquals("A1", awaitItem())
            assertEquals("B2", awaitItem())
            assertEquals("C3", awaitItem())
            awaitComplete()
        }
    }

    @Test
    fun `merge operator merges multiple flows`() = runTest {
        // Given
        val flow1 = flowOf(1, 2)
        val flow2 = flowOf(3, 4)

        // When
        val results = mutableListOf<Int>()
        merge(flow1, flow2).collect { results.add(it) }

        // Then
        assertEquals(4, results.size)
        assertTrue(results.containsAll(listOf(1, 2, 3, 4)))
    }

    @Test
    fun `reduce operator accumulates values`() = runTest {
        // Given
        val flow = flowOf(1, 2, 3, 4, 5)

        // When
        val sum = flow.reduce { acc, value -> acc + value }

        // Then
        assertEquals(15, sum)
    }

    @Test
    fun `fold operator accumulates with initial value`() = runTest {
        // Given
        val flow = flowOf(1, 2, 3, 4, 5)

        // When
        val sum = flow.fold(10) { acc, value -> acc + value }

        // Then
        assertEquals(25, sum)
    }
}

