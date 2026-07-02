package com.forkprint.app.data.local

import androidx.room.TypeConverter
import com.forkprint.app.domain.model.VisitSource
import java.time.Instant

class ForkprintTypeConverters {
    @TypeConverter fun instantToEpochMillis(value: Instant?): Long? = value?.toEpochMilli()
    @TypeConverter fun epochMillisToInstant(value: Long?): Instant? = value?.let(Instant::ofEpochMilli)
    @TypeConverter fun sourceToString(value: VisitSource): String = value.name
    @TypeConverter fun stringToSource(value: String): VisitSource = VisitSource.valueOf(value)
}
