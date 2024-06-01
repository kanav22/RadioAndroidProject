package com.wadhawan.radioandroidproject.model

import com.google.gson.annotations.SerializedName

/**
 * Data class representing a radio station with its name, URL, and unique identifier (UUID).
 */
data class RadioStation(
    val name: String,
    val url: String,
    val stationuuid: String
)

/**
 * Data class representing the availability status of a radio station.
 * It contains a single boolean property `is_online` indicating whether the station is currently broadcasting.
 */
data class StationStatus(
    @SerializedName("ok") val isOnline: Boolean,
    val message: String
)