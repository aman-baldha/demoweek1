package com.demotest.demo

import app.cash.turbine.test
import com.demotest.demo.data.repository.FlowRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class FlowRepositoryTest {

    private lateinit var repository: FlowRepository

    @Before
    fun setup() {
        repository = FlowRepository()
    }

    @Test
    fun `basic flow emits correct number of items`() = runTest {
        // Given & When
        val items = mutableListOf<Int>()
        repository.getBasicFlow().collect { item ->
            items.add(item.id)
        }

        // Then
        assertEquals(10, items.size)
        assertEquals(listOf(0, 1, 2, 3, 4, 5, 6, 7, 8, 9), items)
    }

    @Test
    fun `cold flow emits independently for each collector`() = runTest {
        // Given
        val flow = repository.getColdFlow()
        val firstCollector = mutableListOf<Int>()
        val secondCollector = mutableListOf<Int>()

        // When
        flow.collect { firstCollector.add(it) }
        flow.collect { secondCollector.add(it) }

        // Then
        assertEquals(firstCollector, secondCollector)
        assertEquals(5, firstCollector.size)
    }

    @Test
    fun `numbers flow emits sequential numbers`() = runTest {
        // Given & When
        repository.getNumbersFlow().test {
            // Then
            assertEquals(1, awaitItem())
            assertEquals(2, awaitItem())
            assertEquals(3, awaitItem())
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `flow with operations emits in correct sequence`() = runTest {
        // Given & When
        repository.getFlowWithOperations().test {
            // Then
            val first = awaitItem()
            assertEquals("LOADING", first.stage)
            
            val second = awaitItem()
            assertEquals("EMITTING", second.stage)
            
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `real-time data stream emits incrementing values`() = runTest {
        // Given & When
        repository.getRealTimeDataStream().test {
            // Then
            assertEquals(0, awaitItem())
            assertEquals(1, awaitItem())
            assertEquals(2, awaitItem())
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `temperature flow emits temperature objects`() = runTest {
        // Given
        val location = "TestCity"

        // When
        repository.getTemperatureFlow(location).test {
            // Then
            val temp = awaitItem()
            assertEquals(location, temp.location)
            assertTrue(temp.value in 15.0..35.0)
            assertEquals("°C", temp.unit)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `search flow returns filtered results`() = runTest {
        // Given
        val query = "Result"

        // When
        repository.searchFlow(query).test {
            // Then
            val result = awaitItem()
            assertEquals(query, result.query)
            assertTrue(result.results.isNotEmpty())
            assertTrue(result.results.all { it.contains(query, ignoreCase = true) })
            awaitComplete()
        }
    }

    @Test
    fun `hot flow broadcasts to multiple collectors`() = runTest {
        // Given
        val value = "test-value"
        val results = mutableListOf<String>()

        // When
        repository.emitToHotFlow(value)
        
        repository.hotFlow.test {
            // Then
            assertEquals(value, awaitItem())
            cancelAndIgnoreRemainingEvents()
        }
    }
}

