package com.wadhawan.radioandroidproject

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.hilt.navigation.compose.hiltViewModel


import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import kotlinx.coroutines.flow.Flow
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.viewmodel.compose.viewModel
import com.wadhawan.radioandroidproject.model.RadioStation
import com.wadhawan.radioandroidproject.model.RadioViewModel
import com.wadhawan.radioandroidproject.ui.theme.RadioAndroidProjectTheme

// Main Activity that sets up the Compose UI
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Sets the content of this activity to use Jetpack Compose
        setContent {
            // Applies the overall theme to the app content
            AppTheme {
                // This is the main screen displaying a list of radio stations
                RadioStationListScreen()
            }
        }
    }
}

// Defines the theming and layout constraints for the entire app content
@Composable
fun AppTheme(content: @Composable () -> Unit) {
    // Apply Material Design theming
    MaterialTheme {
        // Create a surface that fills the entire screen
        Surface(modifier = Modifier.fillMaxSize()) {
            // Inject the content into the Surface
            content()
        }
    }
}


@Composable
fun RadioStationListScreen() {
    val viewModel: RadioViewModel = hiltViewModel() // Retrieving the ViewModel instance

        Box(modifier = Modifier.padding(16.dp)) {
            // Displays the list of radio stations
            RadioStationList(viewModel)
        }
    }


// Composable function that displays a list of radio stations
@Composable
fun RadioStationList(viewModel: RadioViewModel) {
    val listState = rememberLazyListState() // Keeps track of the list's scroll position
    val stations = viewModel.stations

    // A lazy column that efficiently displays a list of items
    LazyColumn(state = listState) {
        items(items = stations, key = { it.stationuuid }) { station ->
            // Individual radio station card view
            RadioStationItem(station, viewModel, listState)
        }
    }
}

// Composable that defines the UI structure for each radio station item
@Composable
fun RadioStationItem(station: RadioStation, viewModel: RadioViewModel, listState: LazyListState) {
    // Remembers and recalculates if the item is visible in the list
    val isVisible = remember(station, listState) {
        derivedStateOf {
            listState.layoutInfo.visibleItemsInfo.any { it.key == station.stationuuid }
        }
    }

    // Effects to check station availability when the item becomes visible
    LaunchedEffect(isVisible.value) {
        if (isVisible.value) {
            viewModel.requestAvailabilityCheck(station.stationuuid)
        }
    }

    // Card view for each station with padding and elevation
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        // Row layout for station content
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Icon representing the station's online/offline status
            Icon(
                imageVector = if (viewModel.availability[station.stationuuid] == 1) Icons.Outlined.LocationOn else Icons.Filled.Close,
                contentDescription = if (viewModel.availability[station.stationuuid] == 1) "Online" else "Offline",
                tint = if (viewModel.availability[station.stationuuid] == 1) Color.Green else Color.Red,
                modifier = Modifier.size(40.dp)
            )
            Spacer(modifier = Modifier.width(16.dp))
            // Column for text details
            Column {
                Text(
                    text = "Station: " + station.name,
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = if (viewModel.availability[station.stationuuid] == 1) "Online" else "Offline",
                    style = MaterialTheme.typography.bodyLarge,
                    color = if (viewModel.availability[station.stationuuid] == 1) Color.Green else Color.Red
                )
            }
        }
    }
}
