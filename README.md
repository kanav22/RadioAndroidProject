# Radio Station Checker App

This Android application allows users to view a list of radio stations and check their current availability (online/offline). It leverages modern Android development tools, including Jetpack Compose for the UI, ViewModel for managing UI-related data in a lifecycle-conscious way, and Retrofit for making network requests.

## Features

- **List Radio Stations**: Displays a list of radio stations fetched from the Radio Browser API.
- **Check Availability**: Users can check if a radio station is currently online or offline.
- **Modern Architecture**: Utilizes MVVM architecture, Jetpack Compose, and state management via ViewModel and LiveData.

## Getting Started

These instructions will get you a copy of the project up and running on your local machine for development and testing purposes.

### Prerequisites

- Android Studio Arctic Fox | 2020.3.1 or newer
- Minimum SDK API 21 (Lollipop)

### Installation

1. **Clone the repository**:

```bash
git clone https://github.com/kanav22/RadioAndroidProject.git
```

2. **Open the project in Android Studio**:

   - Launch Android Studio.
   - Click on "Open an existing project".
   - Navigate to the directory where you cloned the repo and click OK.

3. **Build the project**:

   - Click on 'Build' in the menu, then select 'Rebuild Project'.
   - Wait for the build to complete.

4. **Run the application**:

   - Setup an Android emulator or connect an Android device.
   - Click 'Run' in the toolbar.
   - Select the device you want to run the app on.

## Technology Stack

- **Kotlin** - Primary programming language.
- **Jetpack Compose** - Modern toolkit for building native UI.
- **ViewModel** - Manages UI-related data in a lifecycle-conscious way.
- **LiveData** - Lifecycle-aware data holder class.
- **Retrofit** - Type-safe HTTP client for Android and Java.

## Architecture

The application follows the **MVVM (Model-View-ViewModel)** architecture. Here is how the responsibilities are distributed:

- **View**: Defined by composables in Jetpack Compose.
- **ViewModel**: Holds the logic for fetching data from the network and managing UI-related data.
- **Model**: Represents the structure of the radio stations and the responses from the API.

## Testing

This project includes unit tests for the ViewModel and integration tests for the UI components:

- **ViewModel Tests**: Ensure the ViewModel handles API responses correctly.
- **UI Tests**: Verify the UI behaves as expected using Jetpack Compose testing tools.

## Contributing

We welcome contributions from the community, whether it's improving documentation, fixing bugs, or adding new features. Please read [CONTRIBUTING.md](https://github.com/yourusername/radio-station-checker-app/blob/main/CONTRIBUTING.md) for details on our code of conduct and the process for submitting pull requests.

## License

This project is licensed under the MIT License - see the [LICENSE.md](https://github.com/yourusername/radio-station-checker-app/blob/main/LICENSE) file for details.

## Acknowledgments

- Radio Browser API for providing access to their radio station data.
- Jetpack Compose samples from the Android team.

---

This README template provides a comprehensive overview that you can adapt based on the specifics of your project, such as actual links to CONTRIBUTING and LICENSE files, your GitHub username, and any additional details or dependencies your project might have.
