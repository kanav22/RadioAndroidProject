package com.wadhawan.radioandroidproject

import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import com.wadhawan.radioandroidproject.model.RadioStation
import com.wadhawan.radioandroidproject.model.RadioViewModel
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever

class RadioStationListScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private lateinit var viewModel: RadioViewModel

    @Before
    fun setup() {
        // Mock the ViewModel or use a fake

        whenever(viewModel.stations).thenReturn(listOf(RadioStation("1", "101.1 RADIO TURKEY FM", "url", "uuid1")))
        viewModel = mock(RadioViewModel::class.java).apply {
            // Assuming `stations` is a LiveData or StateFlow you might need to set its value
            whenever(stations).thenReturn(listOf(RadioStation(
                "1",
                "101.1 RADIO TURKEY FM",
                "uuid1"
            )) as SnapshotStateList<RadioStation>?)
        }
    }

    @Test
    fun radioStationList_displaysStations() {
        composeTestRule.setContent {
            // Provide the ViewModel explicitly if possible, or use a Provider if the ViewModel is created inside
            RadioStationListScreen()
        }

        composeTestRule.onNodeWithText("101.1 RADIO TURKEY FM").assertIsDisplayed()
    }
}