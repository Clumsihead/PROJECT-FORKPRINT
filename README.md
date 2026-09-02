# Project Forkprint

> **Remember every restaurant, relive every food memory.**

Forkprint is a privacy-first, local-first Android application for preserving and rediscovering personal food experiences.

It is inspired by the idea of a personal food map, but is intentionally built around **memories rather than businesses**. Forkprint records where you have eaten, helps you revisit those experiences, and eventually uses AI to help you understand the patterns in your own food journey.

## Philosophy

Forkprint is built around a few core principles:

- **Offline First** — core memories should remain usable without an internet connection.
- **AI Enhanced** — AI should enrich the experience, not become a requirement for using the app.
- **Privacy by Default** — personal memories belong to the user.
- **Memory over Businesses** — restaurants are context; the user's experiences are the product.
- **Long-term Maintainability** — architecture should remain understandable and replaceable as the project grows.

See [`docs/00 Philosophy.md`](docs/00%20Philosophy.md) for the project's full philosophy.

## Current Status

Forkprint is currently in active MVP development.

### Completed

- Kotlin Android application
- Jetpack Compose + Material 3
- Room local database
- Hilt dependency injection
- Visit-centered domain model
- Manual visit creation and editing
- Local timeline
- Restaurant memory pages
- Local analytics
- Google Places integration behind a provider abstraction
- Offline Places cache
- Foreground location integration
- Dwell-based restaurant visit detection
- Journey Map
- Local fallback when Maps/network/API configuration is unavailable
- Tested domain/use-case layer
- Architecture Decision Records and project documentation

### In Progress

- Food Passport
- AI-powered personal insights
- Real-world device validation and reliability hardening
- Further UX polish

The roadmap is intentionally iterative. See [`docs/05 Roadmap.md`](docs/05%20Roadmap.md).

## Architecture

Forkprint follows a local-first architecture in which the local Room database is the source of truth for user memories.

External services act as providers or enrichment layers:

```text
                    ┌─────────────────────┐
                    │     Forkprint UI    │
                    │   Compose / M3       │
                    └──────────┬──────────┘
                               │
                    ┌──────────▼──────────┐
                    │ Domain / Use Cases  │
                    └──────────┬──────────┘
                               │
                    ┌──────────▼──────────┐
                    │    Repositories     │
                    └──────────┬──────────┘
                               │
                 ┌─────────────▼─────────────┐
                 │       Local Room DB       │
                 │     Source of Truth       │
                 └───────────────────────────┘
                         ▲             ▲
                         │             │
                ┌────────┘             └────────┐
                │                               │
        Places / Maps Provider              AI Provider
        (optional enrichment)             (future enhancement)
```

Google-specific functionality is kept behind provider abstractions so that the rest of the application does not depend directly on Google APIs.

For architectural decisions, see [`docs/06 Decisions.md`](docs/06%20Decisions.md).

## Technology

- **Kotlin**
- **Jetpack Compose**
- **Material 3**
- **Room**
- **Hilt**
- **Google Maps / Places APIs**
- **Gradle**
- **JUnit**

The project uses the Gradle wrapper, so a separate Gradle installation is not required.

## Development Setup

### Requirements

- Android Studio with a compatible Android SDK
- JDK compatible with the project's Gradle/Android configuration
- An Android device or emulator

### Clone

```bash
git clone <your-repository-url>
cd "PROJECT Forkprint"
```

### Google Maps configuration

Forkprint keeps API keys out of source control.

Create `local.properties` in the project root if it does not already exist:

```properties
MAPS_API_KEY=YOUR_MAPS_KEY
```

The project also supports the `FORKPRINT_MAPS_API_KEY` property.

**Do not commit `local.properties` or API keys to Git.**

For development and prototyping, Google Maps Platform's Maps Demo Key may be used where its supported APIs and quotas are sufficient. Production use should use an appropriately configured Google Cloud project.

Forkprint is designed to continue functioning with local memory fallbacks when Maps, Places, network access, or API configuration is unavailable.

### Build

On Windows:

```powershell
.\gradlew.bat assembleDebug
```

On macOS/Linux:

```bash
./gradlew assembleDebug
```

### Run tests

```bash
./gradlew testDebugUnitTest
```

### Build and test together

```bash
./gradlew testDebugUnitTest assembleDebug --no-daemon
```

The debug APK is produced under:

```text
app/build/outputs/apk/debug/app-debug.apk
```

## Project Structure

```text
PROJECT Forkprint/
├── app/                  # Android application
├── docs/                 # Project philosophy, architecture, decisions and roadmap
├── gradle/               # Gradle wrapper configuration
├── .gitignore
├── build.gradle.kts
├── gradle.properties
├── gradlew
├── gradlew.bat
└── settings.gradle.kts
```

The `docs/` directory is an important part of the project, not an afterthought. It records the reasoning behind architectural and product decisions so the project can evolve without losing its original intent.

## Privacy & Data Ownership

Forkprint is designed so that personal memories remain under the user's control.

The core memory model is local.

External providers may supply enrichment such as place information or map rendering, but they are not the authoritative source for the user's memories.

The long-term goal is to make user data portable, understandable, and recoverable.

## Roadmap

The current direction is:

```text
Foundation              ✓
Restaurant Detection    ✓
Memory Experience       ✓
Journey Map             ✓
Food Passport           → current
AI Companion            → future
User Ownership & Trust  → future
Optional Cloud Sync     → future
```

The roadmap may change as real-world usage reveals what matters most.

## Contributing

Forkprint is currently a personal/experimental project under active development.

The architecture and philosophy are intentionally documented so future contributors can understand not only **what** the project does, but **why** it is built this way.

Before making substantial architectural changes, read:

1. [`docs/00 Philosophy.md`](docs/00%20Philosophy.md)
2. [`docs/02 Architecture.md`](docs/02%20Architecture.md)
3. [`docs/06 Decisions.md`](docs/06%20Decisions.md)

## License

License information will be added when the project's distribution model is finalized.

---

**Project Forkprint**  
*Your food journey. Your memories. Your story.*
