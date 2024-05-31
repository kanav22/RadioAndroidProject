package com.wadhawan.radioandroidproject.data.network.api

import com.wadhawan.radioandroidproject.model.RadioStation
import com.wadhawan.radioandroidproject.model.StationStatus
import retrofit2.http.GET
import retrofit2.http.Path

interface RadioApiService {

    /**
     * Fetches a list of all available radio stations broadcasting in English.
     * This function uses a GET request to the "/stations/bylanguage/english" endpoint.
     *
     * @return A list of [RadioStation] objects representing the available stations.
     *
     * @throws [Exception] if network request fails or the server responds with an error.
     */
    @GET("stations/bylanguage/english")
    suspend fun getAllStations(): List<RadioStation>

    /**
     * Checks the availability of a radio station given its UUID.
     * This function uses a GET request to the "/checks/{stationuuid}" endpoint,
     * where "{stationuuid}" is a path parameter that will be replaced with the actual station UUID.
     *
     * @param stationUuid The unique identifier of the radio station.
     *
     * @return A [StationStatus] object indicating whether the station is available or not.
     *
     * @throws [Exception] if network request fails or the server responds with an error.
     */
    @GET("checks/{stationuuid}")
    suspend fun checkStationAvailability(@Path("stationuuid") stationUuid: String): StationStatus
}
