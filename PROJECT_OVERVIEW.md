# Project Overview: Flow Demo

## Introduction
Welcome to the **Flow Demo** project! This application is designed as an educational tool to demonstrate and practice **Kotlin Flow** concepts in a modern Android environment. It serves as a reference for handling asynchronous data streams, UI state management, and reactive programming patterns.

## Tech Stack
*   **Language**: [Kotlin](https://kotlinlang.org/)
*   **UI Framework**: [Jetpack Compose](https://developer.android.com/jetpack/compose) (Material 3)
*   **Architecture**: MVVM (Model-View-ViewModel) with Clean Architecture principles.
*   **Asynchronous Programming**: [Kotlin Coroutines](https://kotlinlang.org/docs/coroutines-overview.html) & [Flow](https://kotlinlang.org/docs/flow.html)
*   **Navigation**: [Navigation Compose](https://developer.android.com/guide/navigation/navigation-compose)
*   **Testing**: JUnit 4, [Turbine](https://github.com/cashapp/turbine) (for Flow testing), Espresso.

## Architecture & Project Structure
The project follows a layered architecture to separate concerns and make the code testable and maintainable.

### Root Directory
*   `app/`: The main Android application module.
*   `gradle/`: Gradle wrapper files.
*   `build.gradle.kts`: Root build configuration.

### Source Code (`app/src/main/java/com/demotest/demo`)
The code is organized by feature and layer:

*   **`data/`**: The Data Layer. Responsible for data retrieval and storage.
    *   `models/`: Data transfer objects (DTOs) or entity classes.
    *   `repository/`: Implementation of repositories (handling data sources).

*   **`domain/`**: The Domain Layer. Contains business logic.
    *   `model/`: Domain models (independent of frameworks).
    *   `repository/`: Repository interfaces (defining the contract for data access).
    *   `usecase/`: Encapsulates specific business rules or actions.

*   **`ui/`**: The UI Layer. Built with Jetpack Compose.
    *   `screens/`: Composable functions representing full screens (e.g., `BasicFlowScreen`, `SearchScreen`).
    *   `components/`: Reusable UI components.
    *   `theme/`: Theme definitions (Color, Type, Shape).

*   **`viewmodel/`**: The Presentation Layer logic.
    *   Contains `ViewModel` classes (e.g., `BasicFlowViewModel`) that hold `StateFlow` for the UI and interact with the Domain/Data layers.

*   **`navigation/`**:
    *   Contains navigation graphs and destination definitions.

## Key Concepts Demonstrated
This project is a practical guide to:
*   **Basic Flows**: Cold flows, multiple collectors.
*   **Flow Operators**: `map`, `filter`, `take`, `drop`, etc.
*   **State Management**: Using `StateFlow` for UI state.
*   **Events**: Using `SharedFlow` for one-time events (like navigation or snackbars).
*   **Combining Flows**: `combine`, `zip`, `merge`, `flatMapLatest`.
*   **Search**: Implementing debounce and search functionality.

## Getting Started

### Prerequisites
*   Android Studio (latest stable version recommended).
*   JDK 11 or higher (configured in Gradle).

### Build and Run
1.  **Clone the repository**.
2.  **Open in Android Studio**.
3.  **Sync Gradle** to download dependencies.
4.  **Run the app** (`Shift + F10` or the Run button) on an emulator or device.

### Running Tests
To run unit tests (including Flow tests with Turbine):
```bash
./gradlew test
```
