# Architecture

Forkprint uses a conventional modern Android architecture optimized for long-term maintainability without over-engineering the MVP.

## Stack

- Kotlin
- Jetpack Compose
- Material 3
- MVVM
- Repository pattern
- Room
- Hilt dependency injection
- Google Maps SDK and Google Places API behind interfaces

## Layers

```text
ui/          Compose screens, state, ViewModels, permission prompts, memory pages
domain/      Visit-centered models, restaurant memory models, repository interfaces, use cases
data/        Room, local repositories, cached provider data
places/      PlacesProvider abstraction and Google Places implementation
location/    Foreground location updates, visit detection sessions, detector adapters
di/          Hilt modules
ai/          Optional AI provider abstraction
```

## Offline-First Rule

All core user value must be backed by local Room data. Network providers can enrich data, but the app must gracefully degrade when providers are unavailable.

For Milestone 1, Google Places results are cached in `cached_places`. If the API key is missing or the network fails, Forkprint falls back to cached places. Manual visit creation remains fully available without location permission or internet.

## Privacy Rule

No social features, mandatory accounts, advertising identifiers, or vendor lock-in are included in the foundation. Location permission is optional and explained in-product. Forkprint currently uses foreground location only; background detection should be implemented later with a user-visible foreground service and explicit controls.

## Visit Detection

The MVP separates visit detection into pure heuristics and Android/provider adapters.

- `AndroidLocationUpdateProvider` produces battery-conscious foreground updates through Fused Location Provider.
- `GooglePlacesProvider` finds nearby restaurant-like places and caches their stable Place IDs, categories, address, and coordinates.
- `VisitDetectionSession` tracks dwell time around the nearest restaurant candidate.
- `VisitDetectionEngine` applies distance, dwell, and confidence thresholds.
- `AndroidRestaurantVisitDetector` emits `VisitDraft` objects with `VisitSource.Detected`.
- `ForkprintViewModel.startVisitDetection()` starts collection only after location permission is granted.

This keeps manual memories, persistence, and UI independent of Google-specific implementation details.

## Memory Experience

Milestone 2 introduces memory-first UI and domain shaping:

- `VisitDetailScreen` presents a visit as a journal page with date, arrival, departure, duration, note, rating, address, categories, source, timeline position, and photo placeholders.
- `RestaurantMemoryScreen` treats restaurants as collections of user memories, not Google Places profiles.
- `BuildRestaurantMemory` derives visit count, first visit, most recent visit, average rating, notes, and complete place timeline from local visits.
- Editing goes through `VisitUpdate` and repository methods so restaurant corrections, notes, ratings, and timing changes remain consistent.
- The timeline groups visits by month to support rediscovery rather than scanning a raw list.

## Battery Posture

Foreground location uses balanced power accuracy, a 15-minute target interval, a 5-minute minimum interval, and a 100-meter minimum displacement. This is intentionally conservative for a calm memory product. Background detection is deferred until it can be implemented with explicit user controls and platform-compliant foreground service behavior.
