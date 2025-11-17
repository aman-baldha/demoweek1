# Flow Demo

Android app practicing Kotlin Flow concepts.

## Features

- Basic Flow (cold flow, multiple collectors)
- Flow operators (map, filter, take, drop, etc)
- StateFlow for UI state
- SharedFlow for events  
- Combined flows (combine, zip, merge, flatMapLatest)
- Real-time data streams
- Debounce search

## Build

```bash
./gradlew assembleDebug
```

## Tests

Using Turbine for flow testing:

```bash
./gradlew test
```

## Structure

- `data/` - Repository and models
- `viewmodel/` - ViewModels for each screen
- `ui/screens/` - Compose UI screens  
- `navigation/` - Nav setup

Based on Kotlin coroutines guide and Flow docs.
