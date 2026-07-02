# Changelog

## 0.3.0 - Memory Experience

- Added rich visit detail pages that present visits as personal journal memories.
- Added arrival, departure, duration, source, timeline position, address, category, rating, and note presentation.
- Added future-ready photo placeholder surfaces without implementing photo storage yet.
- Added restaurant memory pages derived from the user's local visit history.
- Added restaurant memory statistics: visit count, first visit, most recent visit, average rating, notes, and complete timeline.
- Added visit editing for restaurant correction, categories, arrival/departure time, rating, and private notes.
- Added single-visit delete flow.
- Added month-grouped timeline browsing and more polished empty/search states.
- Added `RestaurantMemory`, `VisitUpdate`, and `BuildRestaurantMemory` domain models/use case.
- Added restaurant categories to the local Room schema with explicit migration from version 2 to 3.
- Added tests for restaurant memory derivation.
- Updated architecture, database, roadmap, and architecture decision documentation.

## 0.2.0 - Real Restaurant Detection

- Added `GooglePlacesProvider` behind the existing `PlacesProvider` abstraction.
- Added Google Places Nearby, Text Search, and Place Details support through the Places API.
- Added local `cached_places` Room table for place IDs, categories, addresses, coordinates, and offline fallback.
- Added explicit Room migration from database version 1 to 2.
- Added foreground Android location updates using Fused Location Provider with battery-conscious intervals.
- Added optional runtime location permission prompt with rationale and graceful denial handling.
- Connected location updates, nearby places, dwell sessions, and the visit detection engine into detected visit drafts.
- Added tests for visit detection sessions and duplicate suppression within a foreground session.
- Updated architecture, database, roadmap, and decision documentation.

## 0.1.0 - Foundation

- Added product, architecture, database, AI, roadmap, and decision documentation.
- Established Android Kotlin/Compose/Room/Hilt project foundation.
- Added visit-centered domain, local repository interfaces, provider abstractions, and MVP Compose shell.
- Added manual food memory entry from the timeline.
- Added test-backed restaurant visit detection heuristic foundation.
