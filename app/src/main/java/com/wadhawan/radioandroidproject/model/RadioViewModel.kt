package com.wadhawan.radioandroidproject.model

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wadhawan.radioandroidproject.data.network.NetworkModule
import com.wadhawan.radioandroidproject.data.network.api.RadioApiService
import kotlinx.coroutines.launch

/**
 * ViewModel class for handling radio station data and UI state.
 *
 * @property apiService Injected dependency providing access to the RadioApiService.
 */
class RadioViewModel(private val apiService: RadioApiService = NetworkModule.apiService) : ViewModel() {

    /**
     * A mutable list of [RadioStation] objects representing the available stations.
     */
    var stations = mutableStateListOf<RadioStation>()

    /**
     * A mutable map that stores the availability status (online or offline) for each radio station identified by its UUID.
     */
    var availability = mutableMapOf<String, Boolean>()

    /**
     * A mutable state variable indicating whether data is currently being loaded.
     */
    var isLoading = mutableStateOf(false)

    /**
     * A mutable state variable to hold any error message encountered during data fetching or status checks.
     */
    var error = mutableStateOf("")

    /**
     * Initializes the ViewModel and fetches the list of radio stations on startup.
     */
    init {
        fetchStations()
    }

    /**
     * Fetches a list of all available stations using the injected apiService and updates the internal state.
     * Shows a loading indicator while data is being fetched and sets an error message if any exception occurs.
     */
    private fun fetchStations() {
        isLoading.value = true
        viewModelScope.launch {
            try {
                val allStations = apiService.getAllStations()
                stations.addAll(allStations)
                allStations.forEach {
                    checkAvailability(it.stationuuid)
                }
            } catch (e: Exception) {
                error.value = "Failed to load stations: ${e.localizedMessage}"
            } finally {
                isLoading.value = false
            }
        }
    }

    /**
     * Checks the availability of a radio station identified by its UUID using the injected apiService.
     * Updates the internal availability map with the station's online status and sets an error message if any exception occurs.
     *
     * @param uuid The unique identifier of the radio station.
     */
    private suspend fun checkAvailability(uuid: String) {
        try {
            val status = apiService.checkStationAvailability(uuid)
            availability[uuid] = status.is_online
        } catch (e: Exception) {
            error.value = "Error checking station status: ${e.localizedMessage}"
        }
    }
}
