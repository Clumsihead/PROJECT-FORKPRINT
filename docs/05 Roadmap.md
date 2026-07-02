# Roadmap

## Milestone 0: Foundation

Status: Complete

- Project documentation
- Android project setup
- Room schema
- Visit-first repository
- Compose MVP shell
- AI/provider abstractions with no hard dependency

## Milestone 1: Real Restaurant Detection

Status: First implementation complete

- Timeline of visits
- Add visit notes and ratings
- Search local memories
- Local analytics
- Google Places lookup/enrichment
- Cached place fallback
- Foreground location permission flow
- Battery-conscious foreground location updates
- Basic automatic restaurant visit detection using dwell heuristics

Remaining hardening before broad release:

- On-device/manual QA with real API key across commute, restaurant, and false-positive scenarios
- Duplicate visit suppression across app restarts
- User-visible detection controls and detection status
- Optional background detection with foreground service and explicit opt-in

## Milestone 2: Memory Experience

Status: First implementation complete

- Rich visit detail pages
- Restaurant memory pages derived from the user's visits
- Month-grouped timeline browsing
- Visit edit/delete flows
- Restaurant information correction through visit editing
- Future-ready photo placeholder surfaces

Remaining hardening before broad release:

- More granular navigation/back stack polish
- Dedicated restaurant list/index once the user's memory library grows
- Better date/time editing controls using platform pickers
- UI tests for edit/delete/detail flows

## Milestone 3: Ownership

- Export/import
- Backup/restore controls
- Delete everything

## Milestone 4: Intelligence

- Natural language search
- Weekly summaries
- Taste analysis
- Food stories
- Trip summaries

AI remains optional throughout.
