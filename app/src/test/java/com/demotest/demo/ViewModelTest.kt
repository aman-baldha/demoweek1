package com.demotest.demo

import app.cash.turbine.test
import com.demotest.demo.viewmodel.BasicFlowViewModel
import com.demotest.demo.viewmodel.StateFlowViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class ViewModelTest {

    @Test
    fun `BasicFlowViewModel starts flow correctly`() = runTest {
        // Given
        val viewModel = BasicFlowViewModel()

        // Then - initial state
        assertFalse(viewModel.isCollecting.value)
        assertTrue(viewModel.flowItems.value.isEmpty())
    }

    @Test
    fun `BasicFlowViewModel clears results`() = runTest {
        // Given
        val viewModel = BasicFlowViewModel()

        // When
        viewModel.clearResults()

        // Then
        assertTrue(viewModel.flowItems.value.isEmpty())
        assertTrue(viewModel.coldFlowResults.value.isEmpty())
    }

    @Test
    fun `StateFlowViewModel increments counter`() = runTest {
        // Given
        val viewModel = StateFlowViewModel()

        // When
        viewModel.incrementCounter()

        // Then
        val state = viewModel.counterState.value
        assertEquals(1, state.count)
        assertFalse(state.isEven)
    }

    @Test
    fun `StateFlowViewModel decrements counter`() = runTest {
        // Given
        val viewModel = StateFlowViewModel()

        // When
        viewModel.decrementCounter()

        // Then
        val state = viewModel.counterState.value
        assertEquals(-1, state.count)
        assertFalse(state.isEven)
    }

    @Test
    fun `StateFlowViewModel resets counter`() = runTest {
        // Given
        val viewModel = StateFlowViewModel()
        viewModel.incrementCounter()
        viewModel.incrementCounter()

        // When
        viewModel.resetCounter()

        // Then
        val state = viewModel.counterState.value
        assertEquals(0, state.count)
        assertTrue(state.isEven)
    }

    @Test
    fun `StateFlowViewModel updates first and last name`() = runTest {
        // Given
        val viewModel = StateFlowViewModel()

        // When
        viewModel.updateFirstName("John")
        viewModel.updateLastName("Doe")

        // Then - Check individual fields
        assertEquals("John", viewModel.firstName.value)
        assertEquals("Doe", viewModel.lastName.value)
    }

    @Test
    fun `StateFlowViewModel handles empty names`() = runTest {
        // Given
        val viewModel = StateFlowViewModel()

        // Initial state should be empty
        assertEquals("", viewModel.fullName.value)
    }

    @Test
    fun `counter state calculates isEven correctly`() = runTest {
        // Given
        val viewModel = StateFlowViewModel()

        // When & Then - Initial state
        assertTrue(viewModel.counterState.value.isEven)
        assertEquals(0, viewModel.counterState.value.count)

        // Increment to 1 (odd)
        viewModel.incrementCounter()
        assertFalse(viewModel.counterState.value.isEven)
        assertEquals(1, viewModel.counterState.value.count)

        // Increment to 2 (even)
        viewModel.incrementCounter()
        assertTrue(viewModel.counterState.value.isEven)
        assertEquals(2, viewModel.counterState.value.count)
    }
}

