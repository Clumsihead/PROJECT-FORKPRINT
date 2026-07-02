# Database

Forkprint stores user-owned data locally in Room.

## Core Tables

### restaurants

Represents a location/business. Restaurants accumulate visits.

Key fields:
- `id`: local UUID
- `name`
- `address`
- `latitude`, `longitude`
- `googlePlaceId`: nullable enrichment key
- `categories`: place/category labels used for memory context, not discovery ranking
- timestamps

### visits

Represents the memory and is the central domain object.

Key fields:
- `id`: local UUID
- `restaurantId`
- `startedAt`, `endedAt`
- `rating`: nullable 1-5
- `note`: private user note
- `source`: manual, detected, imported
- `createdAt`, `updatedAt`

### cached_places

Stores Google Places enrichment data locally so automatic detection and enrichment degrade gracefully offline.

Key fields:
- `googlePlaceId`: stable external Place ID, primary key
- `name`
- `address`
- `latitude`, `longitude`
- `categories`: pipe-delimited Google place types
- `fetchedAt`

## Migrations

- Version 1: restaurants + visits
- Version 2: cached_places
- Version 3: restaurant categories

The app uses explicit migrations instead of destructive migration to protect user memories.

## Design Notes

- Visits remain valid even if Places enrichment is unavailable.
- Ratings and notes belong to visits, not restaurants, because opinions change by meal and memory.
- Restaurant-level memory pages are derived from visits.
- Cached places are provider cache data, not the user-owned memory source of truth.
- Restaurant categories are copied into local restaurant records for calm memory context and offline display.
