package com.wadhawan.radioandroidproject

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.wadhawan.radioandroidproject.data.network.api.RadioApiService
import com.wadhawan.radioandroidproject.model.RadioStation
import com.wadhawan.radioandroidproject.model.RadioViewModel
import com.wadhawan.radioandroidproject.model.Response
import com.wadhawan.radioandroidproject.model.StationStatus
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertFalse
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever



@ExperimentalCoroutinesApi
class RadioViewModelTest {
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    private lateinit var viewModel: RadioViewModel
    private var apiService: RadioApiService = mock()

    @Before
    fun setup() {
        viewModel = RadioViewModel(apiService)
    }


    @Test
    fun `fetch stations on init, handles success`() = testCoroutineRule.testDispatcher.runBlockingTest {
        val fakeStations = listOf(RadioStation("1", "Station One", "uuid1"))
        whenever(apiService.getAllStations()).thenReturn(fakeStations)

        // Re-initialize viewModel here to ensure it uses the mock during initialization
        viewModel = RadioViewModel(apiService)

        // Assertions
        assertFalse(viewModel.stations.isEmpty())
        assertEquals(fakeStations, viewModel.stations)
        assertFalse(viewModel.isLoading.value)
        assertEquals("", viewModel.error.value)
    }

    @Test
    fun `fetch stations on init, handles error`() = testCoroutineRule.testDispatcher.runBlockingTest {
        whenever(apiService.getAllStations()).thenThrow(RuntimeException("Failed to fetch"))

        // Re-initialize viewModel here to ensure it uses the mock during initialization
        viewModel = RadioViewModel(apiService)

        // Assertions
        assertTrue(viewModel.stations.isEmpty())
        assertFalse(viewModel.isLoading.value)
        assertEquals("Failed to load stations: Failed to fetch", viewModel.error.value)
    }

}