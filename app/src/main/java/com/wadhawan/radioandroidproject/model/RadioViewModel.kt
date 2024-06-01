package com.wadhawan.radioandroidproject.model

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wadhawan.radioandroidproject.data.network.NetworkModule
import com.wadhawan.radioandroidproject.data.network.api.RadioApiService
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit

open class RadioViewModel(private val apiService: RadioApiService = NetworkModule.apiService) : ViewModel() {
    var stations = mutableStateListOf<RadioStation>()
    var availability = mutableStateMapOf<String, Int>()
    var isLoading = mutableStateOf(true)
    var error = mutableStateOf("")

    init {
        fetchStations()
    }

    private fun fetchStations() {
        viewModelScope.launch {
            try {
                stations.addAll(apiService.getAllStations())
            } catch (e: Exception) {
                error.value = "Failed to load stations: ${e.localizedMessage}"
            } finally {
                isLoading.value = false
            }
        }
    }

    fun requestAvailabilityCheck(uuid: String) {
        viewModelScope.launch {
            if (!availability.containsKey(uuid)) { // Only check if not already loaded or checking
                checkAvailability(uuid)
            }
        }
    }

    private suspend fun checkAvailability(uuid: String) {
        try {
            val status = apiService.checkStationAvailability(uuid)
            availability[uuid] = status[0].ok
        } catch (e: Exception) {
            error.value = "Error checking station status: ${e.localizedMessage}"
        }
    }
}
