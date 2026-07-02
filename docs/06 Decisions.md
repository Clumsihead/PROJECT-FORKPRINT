# Architecture Decisions

## ADR-0001: Visit is the aggregate root for MVP memory features

**Status:** Accepted

Restaurants are locations; visits are memories. Notes, ratings, search snippets, timeline grouping, and analytics should be modeled around visits first.

## ADR-0002: Local Room database is the single source of truth for all user memories. External providers may enrich data but must never become authoritative over user-owned information.

**Status:** Accepted

Places, Maps, location detection, and AI are provider layers. They can enrich or suggest data, but local Room data remains the source of truth.

## ADR-0003: AI provider starts as a no-op abstraction

**Status:** Accepted

The codebase prepares for future NVIDIA APIs through an interface and no-op implementation, without adding network AI complexity to the MVP.

## ADR-0004: Use Hilt for dependency injection

**Status:** Accepted

Hilt keeps repositories, DAOs, and provider abstractions swappable while staying standard in modern Android projects.

## ADR-0005: External mapping and place services must remain behind provider abstractions.

**Status:** Accepted

The app uses `GooglePlacesProvider` for Nearby/Text/Details calls, but UI, repositories, and detection logic depend only on `PlacesProvider`. This keeps Google-specific request formats, API keys, field masks, and failure handling out of the product layers.

## ADR-0006: Cache Places data locally for offline degradation

**Status:** Accepted

Milestone 1 adds `cached_places` with an explicit migration. Cached places are enrichment/provider cache, not the source of truth for visits. When network or API credentials are unavailable, detection/search providers may use cached place records.

## ADR-0007: Use foreground location before background detection

**Status:** Accepted

Forkprint starts with optional foreground location updates using balanced power accuracy. Background detection is future-ready through `LocationUpdateProvider` and `RestaurantVisitDetector`, but it should not be enabled until the app has explicit user controls, a platform-compliant foreground service, and real-world battery testing.

## ADR-0008: Detect visits through dwell sessions, not single pings

**Status:** Accepted

A single nearby restaurant location fix is not enough to create a memory. `VisitDetectionSession` waits for sufficient dwell time around a restaurant candidate before emitting a detected visit draft. This reduces false positives and better matches the memory-first product philosophy.

## ADR-0009: Restaurant pages are derived memory collections

**Status:** Accepted

Restaurant pages should not become business-directory pages. `RestaurantMemory` is derived from the user's local visits and summarizes personal history: count, first/most recent visits, notes, rating, and timeline.

## ADR-0010: Visit editing owns restaurant correction for now

**Status:** Accepted

Milestone 2 lets users correct restaurant name, address, categories, timing, rating, and notes from the visit edit flow. This avoids adding a separate restaurant-management surface before the product needs it while still supporting data correction.

## ADR-0011: Month grouping improves rediscovery without adding feed complexity

**Status:** Accepted

The timeline groups visits by month. This makes browsing feel more like a personal archive while avoiding social-feed mechanics, gamification, or algorithmic ranking.

## ADR-0012: Core product functionality must degrade gracefully

**Status:** Accepted

Forkprint is designed around an Offline First philosophy.

Every external dependency—including AI providers, mapping providers, network connectivity, and future cloud services—must enhance the experience without becoming a requirement for the application's core functionality.

When an external provider becomes unavailable, the application should continue functioning using locally available data whenever technically possible.

Graceful degradation is a product requirement rather than an implementation detail.

## ADR-0013: User memories take precedence over provider data

**Status:** Accepted

Forkprint treats the user's own memories as authoritative.

If user-entered information conflicts with information returned by external providers, the user's version is preserved unless they explicitly choose otherwise.

Forkprint remembers experiences rather than attempting to maintain a perfect business directory.