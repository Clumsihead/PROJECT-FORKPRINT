package com.forkprint.app.data.local.dao;

import android.database.Cursor;
import android.os.CancellationSignal;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.CoroutinesRoom;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.forkprint.app.data.local.ForkprintTypeConverters;
import com.forkprint.app.data.local.entity.CachedPlaceEntity;
import java.lang.Class;
import java.lang.Double;
import java.lang.Exception;
import java.lang.IllegalStateException;
import java.lang.Long;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import javax.annotation.processing.Generated;
import kotlin.Unit;
import kotlin.coroutines.Continuation;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class CachedPlaceDao_Impl implements CachedPlaceDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<CachedPlaceEntity> __insertionAdapterOfCachedPlaceEntity;

  private final ForkprintTypeConverters __forkprintTypeConverters = new ForkprintTypeConverters();

  public CachedPlaceDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfCachedPlaceEntity = new EntityInsertionAdapter<CachedPlaceEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `cached_places` (`googlePlaceId`,`name`,`address`,`latitude`,`longitude`,`categories`,`fetchedAt`) VALUES (?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final CachedPlaceEntity entity) {
        statement.bindString(1, entity.getGooglePlaceId());
        statement.bindString(2, entity.getName());
        if (entity.getAddress() == null) {
          statement.bindNull(3);
        } else {
          statement.bindString(3, entity.getAddress());
        }
        if (entity.getLatitude() == null) {
          statement.bindNull(4);
        } else {
          statement.bindDouble(4, entity.getLatitude());
        }
        if (entity.getLongitude() == null) {
          statement.bindNull(5);
        } else {
          statement.bindDouble(5, entity.getLongitude());
        }
        statement.bindString(6, entity.getCategories());
        final Long _tmp = __forkprintTypeConverters.instantToEpochMillis(entity.getFetchedAt());
        if (_tmp == null) {
          statement.bindNull(7);
        } else {
          statement.bindLong(7, _tmp);
        }
      }
    };
  }

  @Override
  public Object upsertAll(final List<CachedPlaceEntity> places,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfCachedPlaceEntity.insert(places);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object upsert(final CachedPlaceEntity place,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfCachedPlaceEntity.insert(place);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object findByPlaceId(final String placeId,
      final Continuation<? super CachedPlaceEntity> $completion) {
    final String _sql = "SELECT * FROM cached_places WHERE googlePlaceId = ? LIMIT 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindString(_argIndex, placeId);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<CachedPlaceEntity>() {
      @Override
      @Nullable
      public CachedPlaceEntity call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfGooglePlaceId = CursorUtil.getColumnIndexOrThrow(_cursor, "googlePlaceId");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfAddress = CursorUtil.getColumnIndexOrThrow(_cursor, "address");
          final int _cursorIndexOfLatitude = CursorUtil.getColumnIndexOrThrow(_cursor, "latitude");
          final int _cursorIndexOfLongitude = CursorUtil.getColumnIndexOrThrow(_cursor, "longitude");
          final int _cursorIndexOfCategories = CursorUtil.getColumnIndexOrThrow(_cursor, "categories");
          final int _cursorIndexOfFetchedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "fetchedAt");
          final CachedPlaceEntity _result;
          if (_cursor.moveToFirst()) {
            final String _tmpGooglePlaceId;
            _tmpGooglePlaceId = _cursor.getString(_cursorIndexOfGooglePlaceId);
            final String _tmpName;
            _tmpName = _cursor.getString(_cursorIndexOfName);
            final String _tmpAddress;
            if (_cursor.isNull(_cursorIndexOfAddress)) {
              _tmpAddress = null;
            } else {
              _tmpAddress = _cursor.getString(_cursorIndexOfAddress);
            }
            final Double _tmpLatitude;
            if (_cursor.isNull(_cursorIndexOfLatitude)) {
              _tmpLatitude = null;
            } else {
              _tmpLatitude = _cursor.getDouble(_cursorIndexOfLatitude);
            }
            final Double _tmpLongitude;
            if (_cursor.isNull(_cursorIndexOfLongitude)) {
              _tmpLongitude = null;
            } else {
              _tmpLongitude = _cursor.getDouble(_cursorIndexOfLongitude);
            }
            final String _tmpCategories;
            _tmpCategories = _cursor.getString(_cursorIndexOfCategories);
            final Instant _tmpFetchedAt;
            final Long _tmp;
            if (_cursor.isNull(_cursorIndexOfFetchedAt)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getLong(_cursorIndexOfFetchedAt);
            }
            final Instant _tmp_1 = __forkprintTypeConverters.epochMillisToInstant(_tmp);
            if (_tmp_1 == null) {
              throw new IllegalStateException("Expected NON-NULL 'java.time.Instant', but it was NULL.");
            } else {
              _tmpFetchedAt = _tmp_1;
            }
            _result = new CachedPlaceEntity(_tmpGooglePlaceId,_tmpName,_tmpAddress,_tmpLatitude,_tmpLongitude,_tmpCategories,_tmpFetchedAt);
          } else {
            _result = null;
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @Override
  public Object search(final String query, final int limit,
      final Continuation<? super List<CachedPlaceEntity>> $completion) {
    final String _sql = "\n"
            + "        SELECT * FROM cached_places\n"
            + "        WHERE lower(name) LIKE '%' || lower(?) || '%'\n"
            + "           OR lower(coalesce(address, '')) LIKE '%' || lower(?) || '%'\n"
            + "           OR lower(categories) LIKE '%' || lower(?) || '%'\n"
            + "        ORDER BY fetchedAt DESC\n"
            + "        LIMIT ?\n"
            + "    ";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 4);
    int _argIndex = 1;
    _statement.bindString(_argIndex, query);
    _argIndex = 2;
    _statement.bindString(_argIndex, query);
    _argIndex = 3;
    _statement.bindString(_argIndex, query);
    _argIndex = 4;
    _statement.bindLong(_argIndex, limit);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<List<CachedPlaceEntity>>() {
      @Override
      @NonNull
      public List<CachedPlaceEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfGooglePlaceId = CursorUtil.getColumnIndexOrThrow(_cursor, "googlePlaceId");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfAddress = CursorUtil.getColumnIndexOrThrow(_cursor, "address");
          final int _cursorIndexOfLatitude = CursorUtil.getColumnIndexOrThrow(_cursor, "latitude");
          final int _cursorIndexOfLongitude = CursorUtil.getColumnIndexOrThrow(_cursor, "longitude");
          final int _cursorIndexOfCategories = CursorUtil.getColumnIndexOrThrow(_cursor, "categories");
          final int _cursorIndexOfFetchedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "fetchedAt");
          final List<CachedPlaceEntity> _result = new ArrayList<CachedPlaceEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final CachedPlaceEntity _item;
            final String _tmpGooglePlaceId;
            _tmpGooglePlaceId = _cursor.getString(_cursorIndexOfGooglePlaceId);
            final String _tmpName;
            _tmpName = _cursor.getString(_cursorIndexOfName);
            final String _tmpAddress;
            if (_cursor.isNull(_cursorIndexOfAddress)) {
              _tmpAddress = null;
            } else {
              _tmpAddress = _cursor.getString(_cursorIndexOfAddress);
            }
            final Double _tmpLatitude;
            if (_cursor.isNull(_cursorIndexOfLatitude)) {
              _tmpLatitude = null;
            } else {
              _tmpLatitude = _cursor.getDouble(_cursorIndexOfLatitude);
            }
            final Double _tmpLongitude;
            if (_cursor.isNull(_cursorIndexOfLongitude)) {
              _tmpLongitude = null;
            } else {
              _tmpLongitude = _cursor.getDouble(_cursorIndexOfLongitude);
            }
            final String _tmpCategories;
            _tmpCategories = _cursor.getString(_cursorIndexOfCategories);
            final Instant _tmpFetchedAt;
            final Long _tmp;
            if (_cursor.isNull(_cursorIndexOfFetchedAt)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getLong(_cursorIndexOfFetchedAt);
            }
            final Instant _tmp_1 = __forkprintTypeConverters.epochMillisToInstant(_tmp);
            if (_tmp_1 == null) {
              throw new IllegalStateException("Expected NON-NULL 'java.time.Instant', but it was NULL.");
            } else {
              _tmpFetchedAt = _tmp_1;
            }
            _item = new CachedPlaceEntity(_tmpGooglePlaceId,_tmpName,_tmpAddress,_tmpLatitude,_tmpLongitude,_tmpCategories,_tmpFetchedAt);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @Override
  public Object nearby(final double minLatitude, final double maxLatitude,
      final double minLongitude, final double maxLongitude, final int limit,
      final Continuation<? super List<CachedPlaceEntity>> $completion) {
    final String _sql = "\n"
            + "        SELECT * FROM cached_places\n"
            + "        WHERE latitude IS NOT NULL AND longitude IS NOT NULL\n"
            + "          AND latitude BETWEEN ? AND ?\n"
            + "          AND longitude BETWEEN ? AND ?\n"
            + "        ORDER BY fetchedAt DESC\n"
            + "        LIMIT ?\n"
            + "    ";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 5);
    int _argIndex = 1;
    _statement.bindDouble(_argIndex, minLatitude);
    _argIndex = 2;
    _statement.bindDouble(_argIndex, maxLatitude);
    _argIndex = 3;
    _statement.bindDouble(_argIndex, minLongitude);
    _argIndex = 4;
    _statement.bindDouble(_argIndex, maxLongitude);
    _argIndex = 5;
    _statement.bindLong(_argIndex, limit);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<List<CachedPlaceEntity>>() {
      @Override
      @NonNull
      public List<CachedPlaceEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfGooglePlaceId = CursorUtil.getColumnIndexOrThrow(_cursor, "googlePlaceId");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfAddress = CursorUtil.getColumnIndexOrThrow(_cursor, "address");
          final int _cursorIndexOfLatitude = CursorUtil.getColumnIndexOrThrow(_cursor, "latitude");
          final int _cursorIndexOfLongitude = CursorUtil.getColumnIndexOrThrow(_cursor, "longitude");
          final int _cursorIndexOfCategories = CursorUtil.getColumnIndexOrThrow(_cursor, "categories");
          final int _cursorIndexOfFetchedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "fetchedAt");
          final List<CachedPlaceEntity> _result = new ArrayList<CachedPlaceEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final CachedPlaceEntity _item;
            final String _tmpGooglePlaceId;
            _tmpGooglePlaceId = _cursor.getString(_cursorIndexOfGooglePlaceId);
            final String _tmpName;
            _tmpName = _cursor.getString(_cursorIndexOfName);
            final String _tmpAddress;
            if (_cursor.isNull(_cursorIndexOfAddress)) {
              _tmpAddress = null;
            } else {
              _tmpAddress = _cursor.getString(_cursorIndexOfAddress);
            }
            final Double _tmpLatitude;
            if (_cursor.isNull(_cursorIndexOfLatitude)) {
              _tmpLatitude = null;
            } else {
              _tmpLatitude = _cursor.getDouble(_cursorIndexOfLatitude);
            }
            final Double _tmpLongitude;
            if (_cursor.isNull(_cursorIndexOfLongitude)) {
              _tmpLongitude = null;
            } else {
              _tmpLongitude = _cursor.getDouble(_cursorIndexOfLongitude);
            }
            final String _tmpCategories;
            _tmpCategories = _cursor.getString(_cursorIndexOfCategories);
            final Instant _tmpFetchedAt;
            final Long _tmp;
            if (_cursor.isNull(_cursorIndexOfFetchedAt)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getLong(_cursorIndexOfFetchedAt);
            }
            final Instant _tmp_1 = __forkprintTypeConverters.epochMillisToInstant(_tmp);
            if (_tmp_1 == null) {
              throw new IllegalStateException("Expected NON-NULL 'java.time.Instant', but it was NULL.");
            } else {
              _tmpFetchedAt = _tmp_1;
            }
            _item = new CachedPlaceEntity(_tmpGooglePlaceId,_tmpName,_tmpAddress,_tmpLatitude,_tmpLongitude,_tmpCategories,_tmpFetchedAt);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @NonNull
  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
