package com.forkprint.app.data.local.dao;

import android.database.Cursor;
import android.os.CancellationSignal;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.collection.ArrayMap;
import androidx.room.CoroutinesRoom;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.room.util.RelationUtil;
import androidx.room.util.StringUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.forkprint.app.data.local.ForkprintTypeConverters;
import com.forkprint.app.data.local.entity.RestaurantEntity;
import com.forkprint.app.data.local.entity.VisitEntity;
import com.forkprint.app.data.local.model.VisitWithRestaurant;
import com.forkprint.app.domain.model.VisitSource;
import java.lang.Class;
import java.lang.Double;
import java.lang.Exception;
import java.lang.IllegalStateException;
import java.lang.Integer;
import java.lang.Long;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.StringBuilder;
import java.lang.SuppressWarnings;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Callable;
import javax.annotation.processing.Generated;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlinx.coroutines.flow.Flow;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class VisitDao_Impl implements VisitDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<VisitEntity> __insertionAdapterOfVisitEntity;

  private final ForkprintTypeConverters __forkprintTypeConverters = new ForkprintTypeConverters();

  private final EntityDeletionOrUpdateAdapter<VisitEntity> __updateAdapterOfVisitEntity;

  private final SharedSQLiteStatement __preparedStmtOfUpdateVisit;

  private final SharedSQLiteStatement __preparedStmtOfUpdateRating;

  private final SharedSQLiteStatement __preparedStmtOfUpdateNote;

  private final SharedSQLiteStatement __preparedStmtOfDeleteVisit;

  private final SharedSQLiteStatement __preparedStmtOfDeleteAll;

  public VisitDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfVisitEntity = new EntityInsertionAdapter<VisitEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR ABORT INTO `visits` (`id`,`restaurantId`,`startedAt`,`endedAt`,`rating`,`note`,`source`,`createdAt`,`updatedAt`) VALUES (?,?,?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final VisitEntity entity) {
        statement.bindString(1, entity.getId());
        statement.bindString(2, entity.getRestaurantId());
        final Long _tmp = __forkprintTypeConverters.instantToEpochMillis(entity.getStartedAt());
        if (_tmp == null) {
          statement.bindNull(3);
        } else {
          statement.bindLong(3, _tmp);
        }
        final Long _tmp_1 = __forkprintTypeConverters.instantToEpochMillis(entity.getEndedAt());
        if (_tmp_1 == null) {
          statement.bindNull(4);
        } else {
          statement.bindLong(4, _tmp_1);
        }
        if (entity.getRating() == null) {
          statement.bindNull(5);
        } else {
          statement.bindLong(5, entity.getRating());
        }
        statement.bindString(6, entity.getNote());
        final String _tmp_2 = __forkprintTypeConverters.sourceToString(entity.getSource());
        statement.bindString(7, _tmp_2);
        final Long _tmp_3 = __forkprintTypeConverters.instantToEpochMillis(entity.getCreatedAt());
        if (_tmp_3 == null) {
          statement.bindNull(8);
        } else {
          statement.bindLong(8, _tmp_3);
        }
        final Long _tmp_4 = __forkprintTypeConverters.instantToEpochMillis(entity.getUpdatedAt());
        if (_tmp_4 == null) {
          statement.bindNull(9);
        } else {
          statement.bindLong(9, _tmp_4);
        }
      }
    };
    this.__updateAdapterOfVisitEntity = new EntityDeletionOrUpdateAdapter<VisitEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "UPDATE OR ABORT `visits` SET `id` = ?,`restaurantId` = ?,`startedAt` = ?,`endedAt` = ?,`rating` = ?,`note` = ?,`source` = ?,`createdAt` = ?,`updatedAt` = ? WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final VisitEntity entity) {
        statement.bindString(1, entity.getId());
        statement.bindString(2, entity.getRestaurantId());
        final Long _tmp = __forkprintTypeConverters.instantToEpochMillis(entity.getStartedAt());
        if (_tmp == null) {
          statement.bindNull(3);
        } else {
          statement.bindLong(3, _tmp);
        }
        final Long _tmp_1 = __forkprintTypeConverters.instantToEpochMillis(entity.getEndedAt());
        if (_tmp_1 == null) {
          statement.bindNull(4);
        } else {
          statement.bindLong(4, _tmp_1);
        }
        if (entity.getRating() == null) {
          statement.bindNull(5);
        } else {
          statement.bindLong(5, entity.getRating());
        }
        statement.bindString(6, entity.getNote());
        final String _tmp_2 = __forkprintTypeConverters.sourceToString(entity.getSource());
        statement.bindString(7, _tmp_2);
        final Long _tmp_3 = __forkprintTypeConverters.instantToEpochMillis(entity.getCreatedAt());
        if (_tmp_3 == null) {
          statement.bindNull(8);
        } else {
          statement.bindLong(8, _tmp_3);
        }
        final Long _tmp_4 = __forkprintTypeConverters.instantToEpochMillis(entity.getUpdatedAt());
        if (_tmp_4 == null) {
          statement.bindNull(9);
        } else {
          statement.bindLong(9, _tmp_4);
        }
        statement.bindString(10, entity.getId());
      }
    };
    this.__preparedStmtOfUpdateVisit = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "UPDATE visits SET restaurantId = ?, startedAt = ?, endedAt = ?, rating = ?, note = ?, updatedAt = ? WHERE id = ?";
        return _query;
      }
    };
    this.__preparedStmtOfUpdateRating = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "UPDATE visits SET rating = ?, updatedAt = ? WHERE id = ?";
        return _query;
      }
    };
    this.__preparedStmtOfUpdateNote = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "UPDATE visits SET note = ?, updatedAt = ? WHERE id = ?";
        return _query;
      }
    };
    this.__preparedStmtOfDeleteVisit = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM visits WHERE id = ?";
        return _query;
      }
    };
    this.__preparedStmtOfDeleteAll = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM visits";
        return _query;
      }
    };
  }

  @Override
  public Object insert(final VisitEntity entity, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfVisitEntity.insert(entity);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object update(final VisitEntity entity, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __updateAdapterOfVisitEntity.handle(entity);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object updateVisit(final String visitId, final String restaurantId,
      final Instant startedAt, final Instant endedAt, final Integer rating, final String note,
      final Instant updatedAt, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfUpdateVisit.acquire();
        int _argIndex = 1;
        _stmt.bindString(_argIndex, restaurantId);
        _argIndex = 2;
        final Long _tmp = __forkprintTypeConverters.instantToEpochMillis(startedAt);
        if (_tmp == null) {
          _stmt.bindNull(_argIndex);
        } else {
          _stmt.bindLong(_argIndex, _tmp);
        }
        _argIndex = 3;
        final Long _tmp_1 = __forkprintTypeConverters.instantToEpochMillis(endedAt);
        if (_tmp_1 == null) {
          _stmt.bindNull(_argIndex);
        } else {
          _stmt.bindLong(_argIndex, _tmp_1);
        }
        _argIndex = 4;
        if (rating == null) {
          _stmt.bindNull(_argIndex);
        } else {
          _stmt.bindLong(_argIndex, rating);
        }
        _argIndex = 5;
        _stmt.bindString(_argIndex, note);
        _argIndex = 6;
        final Long _tmp_2 = __forkprintTypeConverters.instantToEpochMillis(updatedAt);
        if (_tmp_2 == null) {
          _stmt.bindNull(_argIndex);
        } else {
          _stmt.bindLong(_argIndex, _tmp_2);
        }
        _argIndex = 7;
        _stmt.bindString(_argIndex, visitId);
        try {
          __db.beginTransaction();
          try {
            _stmt.executeUpdateDelete();
            __db.setTransactionSuccessful();
            return Unit.INSTANCE;
          } finally {
            __db.endTransaction();
          }
        } finally {
          __preparedStmtOfUpdateVisit.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Object updateRating(final String visitId, final Integer rating, final Instant updatedAt,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfUpdateRating.acquire();
        int _argIndex = 1;
        if (rating == null) {
          _stmt.bindNull(_argIndex);
        } else {
          _stmt.bindLong(_argIndex, rating);
        }
        _argIndex = 2;
        final Long _tmp = __forkprintTypeConverters.instantToEpochMillis(updatedAt);
        if (_tmp == null) {
          _stmt.bindNull(_argIndex);
        } else {
          _stmt.bindLong(_argIndex, _tmp);
        }
        _argIndex = 3;
        _stmt.bindString(_argIndex, visitId);
        try {
          __db.beginTransaction();
          try {
            _stmt.executeUpdateDelete();
            __db.setTransactionSuccessful();
            return Unit.INSTANCE;
          } finally {
            __db.endTransaction();
          }
        } finally {
          __preparedStmtOfUpdateRating.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Object updateNote(final String visitId, final String note, final Instant updatedAt,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfUpdateNote.acquire();
        int _argIndex = 1;
        _stmt.bindString(_argIndex, note);
        _argIndex = 2;
        final Long _tmp = __forkprintTypeConverters.instantToEpochMillis(updatedAt);
        if (_tmp == null) {
          _stmt.bindNull(_argIndex);
        } else {
          _stmt.bindLong(_argIndex, _tmp);
        }
        _argIndex = 3;
        _stmt.bindString(_argIndex, visitId);
        try {
          __db.beginTransaction();
          try {
            _stmt.executeUpdateDelete();
            __db.setTransactionSuccessful();
            return Unit.INSTANCE;
          } finally {
            __db.endTransaction();
          }
        } finally {
          __preparedStmtOfUpdateNote.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Object deleteVisit(final String visitId, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteVisit.acquire();
        int _argIndex = 1;
        _stmt.bindString(_argIndex, visitId);
        try {
          __db.beginTransaction();
          try {
            _stmt.executeUpdateDelete();
            __db.setTransactionSuccessful();
            return Unit.INSTANCE;
          } finally {
            __db.endTransaction();
          }
        } finally {
          __preparedStmtOfDeleteVisit.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Object deleteAll(final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteAll.acquire();
        try {
          __db.beginTransaction();
          try {
            _stmt.executeUpdateDelete();
            __db.setTransactionSuccessful();
            return Unit.INSTANCE;
          } finally {
            __db.endTransaction();
          }
        } finally {
          __preparedStmtOfDeleteAll.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Flow<List<VisitWithRestaurant>> observeTimeline() {
    final String _sql = "SELECT * FROM visits ORDER BY startedAt DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, true, new String[] {"restaurants",
        "visits"}, new Callable<List<VisitWithRestaurant>>() {
      @Override
      @NonNull
      public List<VisitWithRestaurant> call() throws Exception {
        __db.beginTransaction();
        try {
          final Cursor _cursor = DBUtil.query(__db, _statement, true, null);
          try {
            final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
            final int _cursorIndexOfRestaurantId = CursorUtil.getColumnIndexOrThrow(_cursor, "restaurantId");
            final int _cursorIndexOfStartedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "startedAt");
            final int _cursorIndexOfEndedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "endedAt");
            final int _cursorIndexOfRating = CursorUtil.getColumnIndexOrThrow(_cursor, "rating");
            final int _cursorIndexOfNote = CursorUtil.getColumnIndexOrThrow(_cursor, "note");
            final int _cursorIndexOfSource = CursorUtil.getColumnIndexOrThrow(_cursor, "source");
            final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
            final int _cursorIndexOfUpdatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "updatedAt");
            final ArrayMap<String, RestaurantEntity> _collectionRestaurant = new ArrayMap<String, RestaurantEntity>();
            while (_cursor.moveToNext()) {
              final String _tmpKey;
              _tmpKey = _cursor.getString(_cursorIndexOfRestaurantId);
              _collectionRestaurant.put(_tmpKey, null);
            }
            _cursor.moveToPosition(-1);
            __fetchRelationshiprestaurantsAscomForkprintAppDataLocalEntityRestaurantEntity(_collectionRestaurant);
            final List<VisitWithRestaurant> _result = new ArrayList<VisitWithRestaurant>(_cursor.getCount());
            while (_cursor.moveToNext()) {
              final VisitWithRestaurant _item;
              final VisitEntity _tmpVisit;
              final String _tmpId;
              _tmpId = _cursor.getString(_cursorIndexOfId);
              final String _tmpRestaurantId;
              _tmpRestaurantId = _cursor.getString(_cursorIndexOfRestaurantId);
              final Instant _tmpStartedAt;
              final Long _tmp;
              if (_cursor.isNull(_cursorIndexOfStartedAt)) {
                _tmp = null;
              } else {
                _tmp = _cursor.getLong(_cursorIndexOfStartedAt);
              }
              final Instant _tmp_1 = __forkprintTypeConverters.epochMillisToInstant(_tmp);
              if (_tmp_1 == null) {
                throw new IllegalStateException("Expected NON-NULL 'java.time.Instant', but it was NULL.");
              } else {
                _tmpStartedAt = _tmp_1;
              }
              final Instant _tmpEndedAt;
              final Long _tmp_2;
              if (_cursor.isNull(_cursorIndexOfEndedAt)) {
                _tmp_2 = null;
              } else {
                _tmp_2 = _cursor.getLong(_cursorIndexOfEndedAt);
              }
              _tmpEndedAt = __forkprintTypeConverters.epochMillisToInstant(_tmp_2);
              final Integer _tmpRating;
              if (_cursor.isNull(_cursorIndexOfRating)) {
                _tmpRating = null;
              } else {
                _tmpRating = _cursor.getInt(_cursorIndexOfRating);
              }
              final String _tmpNote;
              _tmpNote = _cursor.getString(_cursorIndexOfNote);
              final VisitSource _tmpSource;
              final String _tmp_3;
              _tmp_3 = _cursor.getString(_cursorIndexOfSource);
              _tmpSource = __forkprintTypeConverters.stringToSource(_tmp_3);
              final Instant _tmpCreatedAt;
              final Long _tmp_4;
              if (_cursor.isNull(_cursorIndexOfCreatedAt)) {
                _tmp_4 = null;
              } else {
                _tmp_4 = _cursor.getLong(_cursorIndexOfCreatedAt);
              }
              final Instant _tmp_5 = __forkprintTypeConverters.epochMillisToInstant(_tmp_4);
              if (_tmp_5 == null) {
                throw new IllegalStateException("Expected NON-NULL 'java.time.Instant', but it was NULL.");
              } else {
                _tmpCreatedAt = _tmp_5;
              }
              final Instant _tmpUpdatedAt;
              final Long _tmp_6;
              if (_cursor.isNull(_cursorIndexOfUpdatedAt)) {
                _tmp_6 = null;
              } else {
                _tmp_6 = _cursor.getLong(_cursorIndexOfUpdatedAt);
              }
              final Instant _tmp_7 = __forkprintTypeConverters.epochMillisToInstant(_tmp_6);
              if (_tmp_7 == null) {
                throw new IllegalStateException("Expected NON-NULL 'java.time.Instant', but it was NULL.");
              } else {
                _tmpUpdatedAt = _tmp_7;
              }
              _tmpVisit = new VisitEntity(_tmpId,_tmpRestaurantId,_tmpStartedAt,_tmpEndedAt,_tmpRating,_tmpNote,_tmpSource,_tmpCreatedAt,_tmpUpdatedAt);
              final RestaurantEntity _tmpRestaurant;
              final String _tmpKey_1;
              _tmpKey_1 = _cursor.getString(_cursorIndexOfRestaurantId);
              _tmpRestaurant = _collectionRestaurant.get(_tmpKey_1);
              _item = new VisitWithRestaurant(_tmpVisit,_tmpRestaurant);
              _result.add(_item);
            }
            __db.setTransactionSuccessful();
            return _result;
          } finally {
            _cursor.close();
          }
        } finally {
          __db.endTransaction();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Flow<VisitWithRestaurant> observeVisit(final String visitId) {
    final String _sql = "SELECT * FROM visits WHERE id = ? LIMIT 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindString(_argIndex, visitId);
    return CoroutinesRoom.createFlow(__db, true, new String[] {"restaurants",
        "visits"}, new Callable<VisitWithRestaurant>() {
      @Override
      @Nullable
      public VisitWithRestaurant call() throws Exception {
        __db.beginTransaction();
        try {
          final Cursor _cursor = DBUtil.query(__db, _statement, true, null);
          try {
            final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
            final int _cursorIndexOfRestaurantId = CursorUtil.getColumnIndexOrThrow(_cursor, "restaurantId");
            final int _cursorIndexOfStartedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "startedAt");
            final int _cursorIndexOfEndedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "endedAt");
            final int _cursorIndexOfRating = CursorUtil.getColumnIndexOrThrow(_cursor, "rating");
            final int _cursorIndexOfNote = CursorUtil.getColumnIndexOrThrow(_cursor, "note");
            final int _cursorIndexOfSource = CursorUtil.getColumnIndexOrThrow(_cursor, "source");
            final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
            final int _cursorIndexOfUpdatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "updatedAt");
            final ArrayMap<String, RestaurantEntity> _collectionRestaurant = new ArrayMap<String, RestaurantEntity>();
            while (_cursor.moveToNext()) {
              final String _tmpKey;
              _tmpKey = _cursor.getString(_cursorIndexOfRestaurantId);
              _collectionRestaurant.put(_tmpKey, null);
            }
            _cursor.moveToPosition(-1);
            __fetchRelationshiprestaurantsAscomForkprintAppDataLocalEntityRestaurantEntity(_collectionRestaurant);
            final VisitWithRestaurant _result;
            if (_cursor.moveToFirst()) {
              final VisitEntity _tmpVisit;
              final String _tmpId;
              _tmpId = _cursor.getString(_cursorIndexOfId);
              final String _tmpRestaurantId;
              _tmpRestaurantId = _cursor.getString(_cursorIndexOfRestaurantId);
              final Instant _tmpStartedAt;
              final Long _tmp;
              if (_cursor.isNull(_cursorIndexOfStartedAt)) {
                _tmp = null;
              } else {
                _tmp = _cursor.getLong(_cursorIndexOfStartedAt);
              }
              final Instant _tmp_1 = __forkprintTypeConverters.epochMillisToInstant(_tmp);
              if (_tmp_1 == null) {
                throw new IllegalStateException("Expected NON-NULL 'java.time.Instant', but it was NULL.");
              } else {
                _tmpStartedAt = _tmp_1;
              }
              final Instant _tmpEndedAt;
              final Long _tmp_2;
              if (_cursor.isNull(_cursorIndexOfEndedAt)) {
                _tmp_2 = null;
              } else {
                _tmp_2 = _cursor.getLong(_cursorIndexOfEndedAt);
              }
              _tmpEndedAt = __forkprintTypeConverters.epochMillisToInstant(_tmp_2);
              final Integer _tmpRating;
              if (_cursor.isNull(_cursorIndexOfRating)) {
                _tmpRating = null;
              } else {
                _tmpRating = _cursor.getInt(_cursorIndexOfRating);
              }
              final String _tmpNote;
              _tmpNote = _cursor.getString(_cursorIndexOfNote);
              final VisitSource _tmpSource;
              final String _tmp_3;
              _tmp_3 = _cursor.getString(_cursorIndexOfSource);
              _tmpSource = __forkprintTypeConverters.stringToSource(_tmp_3);
              final Instant _tmpCreatedAt;
              final Long _tmp_4;
              if (_cursor.isNull(_cursorIndexOfCreatedAt)) {
                _tmp_4 = null;
              } else {
                _tmp_4 = _cursor.getLong(_cursorIndexOfCreatedAt);
              }
              final Instant _tmp_5 = __forkprintTypeConverters.epochMillisToInstant(_tmp_4);
              if (_tmp_5 == null) {
                throw new IllegalStateException("Expected NON-NULL 'java.time.Instant', but it was NULL.");
              } else {
                _tmpCreatedAt = _tmp_5;
              }
              final Instant _tmpUpdatedAt;
              final Long _tmp_6;
              if (_cursor.isNull(_cursorIndexOfUpdatedAt)) {
                _tmp_6 = null;
              } else {
                _tmp_6 = _cursor.getLong(_cursorIndexOfUpdatedAt);
              }
              final Instant _tmp_7 = __forkprintTypeConverters.epochMillisToInstant(_tmp_6);
              if (_tmp_7 == null) {
                throw new IllegalStateException("Expected NON-NULL 'java.time.Instant', but it was NULL.");
              } else {
                _tmpUpdatedAt = _tmp_7;
              }
              _tmpVisit = new VisitEntity(_tmpId,_tmpRestaurantId,_tmpStartedAt,_tmpEndedAt,_tmpRating,_tmpNote,_tmpSource,_tmpCreatedAt,_tmpUpdatedAt);
              final RestaurantEntity _tmpRestaurant;
              final String _tmpKey_1;
              _tmpKey_1 = _cursor.getString(_cursorIndexOfRestaurantId);
              _tmpRestaurant = _collectionRestaurant.get(_tmpKey_1);
              _result = new VisitWithRestaurant(_tmpVisit,_tmpRestaurant);
            } else {
              _result = null;
            }
            __db.setTransactionSuccessful();
            return _result;
          } finally {
            _cursor.close();
          }
        } finally {
          __db.endTransaction();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Flow<List<VisitWithRestaurant>> observeVisitsForRestaurant(final String restaurantId) {
    final String _sql = "SELECT * FROM visits WHERE restaurantId = ? ORDER BY startedAt DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindString(_argIndex, restaurantId);
    return CoroutinesRoom.createFlow(__db, true, new String[] {"restaurants",
        "visits"}, new Callable<List<VisitWithRestaurant>>() {
      @Override
      @NonNull
      public List<VisitWithRestaurant> call() throws Exception {
        __db.beginTransaction();
        try {
          final Cursor _cursor = DBUtil.query(__db, _statement, true, null);
          try {
            final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
            final int _cursorIndexOfRestaurantId = CursorUtil.getColumnIndexOrThrow(_cursor, "restaurantId");
            final int _cursorIndexOfStartedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "startedAt");
            final int _cursorIndexOfEndedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "endedAt");
            final int _cursorIndexOfRating = CursorUtil.getColumnIndexOrThrow(_cursor, "rating");
            final int _cursorIndexOfNote = CursorUtil.getColumnIndexOrThrow(_cursor, "note");
            final int _cursorIndexOfSource = CursorUtil.getColumnIndexOrThrow(_cursor, "source");
            final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
            final int _cursorIndexOfUpdatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "updatedAt");
            final ArrayMap<String, RestaurantEntity> _collectionRestaurant = new ArrayMap<String, RestaurantEntity>();
            while (_cursor.moveToNext()) {
              final String _tmpKey;
              _tmpKey = _cursor.getString(_cursorIndexOfRestaurantId);
              _collectionRestaurant.put(_tmpKey, null);
            }
            _cursor.moveToPosition(-1);
            __fetchRelationshiprestaurantsAscomForkprintAppDataLocalEntityRestaurantEntity(_collectionRestaurant);
            final List<VisitWithRestaurant> _result = new ArrayList<VisitWithRestaurant>(_cursor.getCount());
            while (_cursor.moveToNext()) {
              final VisitWithRestaurant _item;
              final VisitEntity _tmpVisit;
              final String _tmpId;
              _tmpId = _cursor.getString(_cursorIndexOfId);
              final String _tmpRestaurantId;
              _tmpRestaurantId = _cursor.getString(_cursorIndexOfRestaurantId);
              final Instant _tmpStartedAt;
              final Long _tmp;
              if (_cursor.isNull(_cursorIndexOfStartedAt)) {
                _tmp = null;
              } else {
                _tmp = _cursor.getLong(_cursorIndexOfStartedAt);
              }
              final Instant _tmp_1 = __forkprintTypeConverters.epochMillisToInstant(_tmp);
              if (_tmp_1 == null) {
                throw new IllegalStateException("Expected NON-NULL 'java.time.Instant', but it was NULL.");
              } else {
                _tmpStartedAt = _tmp_1;
              }
              final Instant _tmpEndedAt;
              final Long _tmp_2;
              if (_cursor.isNull(_cursorIndexOfEndedAt)) {
                _tmp_2 = null;
              } else {
                _tmp_2 = _cursor.getLong(_cursorIndexOfEndedAt);
              }
              _tmpEndedAt = __forkprintTypeConverters.epochMillisToInstant(_tmp_2);
              final Integer _tmpRating;
              if (_cursor.isNull(_cursorIndexOfRating)) {
                _tmpRating = null;
              } else {
                _tmpRating = _cursor.getInt(_cursorIndexOfRating);
              }
              final String _tmpNote;
              _tmpNote = _cursor.getString(_cursorIndexOfNote);
              final VisitSource _tmpSource;
              final String _tmp_3;
              _tmp_3 = _cursor.getString(_cursorIndexOfSource);
              _tmpSource = __forkprintTypeConverters.stringToSource(_tmp_3);
              final Instant _tmpCreatedAt;
              final Long _tmp_4;
              if (_cursor.isNull(_cursorIndexOfCreatedAt)) {
                _tmp_4 = null;
              } else {
                _tmp_4 = _cursor.getLong(_cursorIndexOfCreatedAt);
              }
              final Instant _tmp_5 = __forkprintTypeConverters.epochMillisToInstant(_tmp_4);
              if (_tmp_5 == null) {
                throw new IllegalStateException("Expected NON-NULL 'java.time.Instant', but it was NULL.");
              } else {
                _tmpCreatedAt = _tmp_5;
              }
              final Instant _tmpUpdatedAt;
              final Long _tmp_6;
              if (_cursor.isNull(_cursorIndexOfUpdatedAt)) {
                _tmp_6 = null;
              } else {
                _tmp_6 = _cursor.getLong(_cursorIndexOfUpdatedAt);
              }
              final Instant _tmp_7 = __forkprintTypeConverters.epochMillisToInstant(_tmp_6);
              if (_tmp_7 == null) {
                throw new IllegalStateException("Expected NON-NULL 'java.time.Instant', but it was NULL.");
              } else {
                _tmpUpdatedAt = _tmp_7;
              }
              _tmpVisit = new VisitEntity(_tmpId,_tmpRestaurantId,_tmpStartedAt,_tmpEndedAt,_tmpRating,_tmpNote,_tmpSource,_tmpCreatedAt,_tmpUpdatedAt);
              final RestaurantEntity _tmpRestaurant;
              final String _tmpKey_1;
              _tmpKey_1 = _cursor.getString(_cursorIndexOfRestaurantId);
              _tmpRestaurant = _collectionRestaurant.get(_tmpKey_1);
              _item = new VisitWithRestaurant(_tmpVisit,_tmpRestaurant);
              _result.add(_item);
            }
            __db.setTransactionSuccessful();
            return _result;
          } finally {
            _cursor.close();
          }
        } finally {
          __db.endTransaction();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Flow<List<VisitWithRestaurant>> search(final String query) {
    final String _sql = "\n"
            + "        SELECT visits.* FROM visits\n"
            + "        INNER JOIN restaurants ON restaurants.id = visits.restaurantId\n"
            + "        WHERE lower(restaurants.name) LIKE '%' || lower(?) || '%'\n"
            + "           OR lower(coalesce(restaurants.address, '')) LIKE '%' || lower(?) || '%'\n"
            + "           OR lower(restaurants.categories) LIKE '%' || lower(?) || '%'\n"
            + "           OR lower(visits.note) LIKE '%' || lower(?) || '%'\n"
            + "        ORDER BY visits.startedAt DESC\n"
            + "    ";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 4);
    int _argIndex = 1;
    _statement.bindString(_argIndex, query);
    _argIndex = 2;
    _statement.bindString(_argIndex, query);
    _argIndex = 3;
    _statement.bindString(_argIndex, query);
    _argIndex = 4;
    _statement.bindString(_argIndex, query);
    return CoroutinesRoom.createFlow(__db, true, new String[] {"restaurants",
        "visits"}, new Callable<List<VisitWithRestaurant>>() {
      @Override
      @NonNull
      public List<VisitWithRestaurant> call() throws Exception {
        __db.beginTransaction();
        try {
          final Cursor _cursor = DBUtil.query(__db, _statement, true, null);
          try {
            final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
            final int _cursorIndexOfRestaurantId = CursorUtil.getColumnIndexOrThrow(_cursor, "restaurantId");
            final int _cursorIndexOfStartedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "startedAt");
            final int _cursorIndexOfEndedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "endedAt");
            final int _cursorIndexOfRating = CursorUtil.getColumnIndexOrThrow(_cursor, "rating");
            final int _cursorIndexOfNote = CursorUtil.getColumnIndexOrThrow(_cursor, "note");
            final int _cursorIndexOfSource = CursorUtil.getColumnIndexOrThrow(_cursor, "source");
            final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
            final int _cursorIndexOfUpdatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "updatedAt");
            final ArrayMap<String, RestaurantEntity> _collectionRestaurant = new ArrayMap<String, RestaurantEntity>();
            while (_cursor.moveToNext()) {
              final String _tmpKey;
              _tmpKey = _cursor.getString(_cursorIndexOfRestaurantId);
              _collectionRestaurant.put(_tmpKey, null);
            }
            _cursor.moveToPosition(-1);
            __fetchRelationshiprestaurantsAscomForkprintAppDataLocalEntityRestaurantEntity(_collectionRestaurant);
            final List<VisitWithRestaurant> _result = new ArrayList<VisitWithRestaurant>(_cursor.getCount());
            while (_cursor.moveToNext()) {
              final VisitWithRestaurant _item;
              final VisitEntity _tmpVisit;
              final String _tmpId;
              _tmpId = _cursor.getString(_cursorIndexOfId);
              final String _tmpRestaurantId;
              _tmpRestaurantId = _cursor.getString(_cursorIndexOfRestaurantId);
              final Instant _tmpStartedAt;
              final Long _tmp;
              if (_cursor.isNull(_cursorIndexOfStartedAt)) {
                _tmp = null;
              } else {
                _tmp = _cursor.getLong(_cursorIndexOfStartedAt);
              }
              final Instant _tmp_1 = __forkprintTypeConverters.epochMillisToInstant(_tmp);
              if (_tmp_1 == null) {
                throw new IllegalStateException("Expected NON-NULL 'java.time.Instant', but it was NULL.");
              } else {
                _tmpStartedAt = _tmp_1;
              }
              final Instant _tmpEndedAt;
              final Long _tmp_2;
              if (_cursor.isNull(_cursorIndexOfEndedAt)) {
                _tmp_2 = null;
              } else {
                _tmp_2 = _cursor.getLong(_cursorIndexOfEndedAt);
              }
              _tmpEndedAt = __forkprintTypeConverters.epochMillisToInstant(_tmp_2);
              final Integer _tmpRating;
              if (_cursor.isNull(_cursorIndexOfRating)) {
                _tmpRating = null;
              } else {
                _tmpRating = _cursor.getInt(_cursorIndexOfRating);
              }
              final String _tmpNote;
              _tmpNote = _cursor.getString(_cursorIndexOfNote);
              final VisitSource _tmpSource;
              final String _tmp_3;
              _tmp_3 = _cursor.getString(_cursorIndexOfSource);
              _tmpSource = __forkprintTypeConverters.stringToSource(_tmp_3);
              final Instant _tmpCreatedAt;
              final Long _tmp_4;
              if (_cursor.isNull(_cursorIndexOfCreatedAt)) {
                _tmp_4 = null;
              } else {
                _tmp_4 = _cursor.getLong(_cursorIndexOfCreatedAt);
              }
              final Instant _tmp_5 = __forkprintTypeConverters.epochMillisToInstant(_tmp_4);
              if (_tmp_5 == null) {
                throw new IllegalStateException("Expected NON-NULL 'java.time.Instant', but it was NULL.");
              } else {
                _tmpCreatedAt = _tmp_5;
              }
              final Instant _tmpUpdatedAt;
              final Long _tmp_6;
              if (_cursor.isNull(_cursorIndexOfUpdatedAt)) {
                _tmp_6 = null;
              } else {
                _tmp_6 = _cursor.getLong(_cursorIndexOfUpdatedAt);
              }
              final Instant _tmp_7 = __forkprintTypeConverters.epochMillisToInstant(_tmp_6);
              if (_tmp_7 == null) {
                throw new IllegalStateException("Expected NON-NULL 'java.time.Instant', but it was NULL.");
              } else {
                _tmpUpdatedAt = _tmp_7;
              }
              _tmpVisit = new VisitEntity(_tmpId,_tmpRestaurantId,_tmpStartedAt,_tmpEndedAt,_tmpRating,_tmpNote,_tmpSource,_tmpCreatedAt,_tmpUpdatedAt);
              final RestaurantEntity _tmpRestaurant;
              final String _tmpKey_1;
              _tmpKey_1 = _cursor.getString(_cursorIndexOfRestaurantId);
              _tmpRestaurant = _collectionRestaurant.get(_tmpKey_1);
              _item = new VisitWithRestaurant(_tmpVisit,_tmpRestaurant);
              _result.add(_item);
            }
            __db.setTransactionSuccessful();
            return _result;
          } finally {
            _cursor.close();
          }
        } finally {
          __db.endTransaction();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Object findById(final String visitId,
      final Continuation<? super VisitEntity> $completion) {
    final String _sql = "SELECT * FROM visits WHERE id = ? LIMIT 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindString(_argIndex, visitId);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<VisitEntity>() {
      @Override
      @Nullable
      public VisitEntity call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfRestaurantId = CursorUtil.getColumnIndexOrThrow(_cursor, "restaurantId");
          final int _cursorIndexOfStartedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "startedAt");
          final int _cursorIndexOfEndedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "endedAt");
          final int _cursorIndexOfRating = CursorUtil.getColumnIndexOrThrow(_cursor, "rating");
          final int _cursorIndexOfNote = CursorUtil.getColumnIndexOrThrow(_cursor, "note");
          final int _cursorIndexOfSource = CursorUtil.getColumnIndexOrThrow(_cursor, "source");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final int _cursorIndexOfUpdatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "updatedAt");
          final VisitEntity _result;
          if (_cursor.moveToFirst()) {
            final String _tmpId;
            _tmpId = _cursor.getString(_cursorIndexOfId);
            final String _tmpRestaurantId;
            _tmpRestaurantId = _cursor.getString(_cursorIndexOfRestaurantId);
            final Instant _tmpStartedAt;
            final Long _tmp;
            if (_cursor.isNull(_cursorIndexOfStartedAt)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getLong(_cursorIndexOfStartedAt);
            }
            final Instant _tmp_1 = __forkprintTypeConverters.epochMillisToInstant(_tmp);
            if (_tmp_1 == null) {
              throw new IllegalStateException("Expected NON-NULL 'java.time.Instant', but it was NULL.");
            } else {
              _tmpStartedAt = _tmp_1;
            }
            final Instant _tmpEndedAt;
            final Long _tmp_2;
            if (_cursor.isNull(_cursorIndexOfEndedAt)) {
              _tmp_2 = null;
            } else {
              _tmp_2 = _cursor.getLong(_cursorIndexOfEndedAt);
            }
            _tmpEndedAt = __forkprintTypeConverters.epochMillisToInstant(_tmp_2);
            final Integer _tmpRating;
            if (_cursor.isNull(_cursorIndexOfRating)) {
              _tmpRating = null;
            } else {
              _tmpRating = _cursor.getInt(_cursorIndexOfRating);
            }
            final String _tmpNote;
            _tmpNote = _cursor.getString(_cursorIndexOfNote);
            final VisitSource _tmpSource;
            final String _tmp_3;
            _tmp_3 = _cursor.getString(_cursorIndexOfSource);
            _tmpSource = __forkprintTypeConverters.stringToSource(_tmp_3);
            final Instant _tmpCreatedAt;
            final Long _tmp_4;
            if (_cursor.isNull(_cursorIndexOfCreatedAt)) {
              _tmp_4 = null;
            } else {
              _tmp_4 = _cursor.getLong(_cursorIndexOfCreatedAt);
            }
            final Instant _tmp_5 = __forkprintTypeConverters.epochMillisToInstant(_tmp_4);
            if (_tmp_5 == null) {
              throw new IllegalStateException("Expected NON-NULL 'java.time.Instant', but it was NULL.");
            } else {
              _tmpCreatedAt = _tmp_5;
            }
            final Instant _tmpUpdatedAt;
            final Long _tmp_6;
            if (_cursor.isNull(_cursorIndexOfUpdatedAt)) {
              _tmp_6 = null;
            } else {
              _tmp_6 = _cursor.getLong(_cursorIndexOfUpdatedAt);
            }
            final Instant _tmp_7 = __forkprintTypeConverters.epochMillisToInstant(_tmp_6);
            if (_tmp_7 == null) {
              throw new IllegalStateException("Expected NON-NULL 'java.time.Instant', but it was NULL.");
            } else {
              _tmpUpdatedAt = _tmp_7;
            }
            _result = new VisitEntity(_tmpId,_tmpRestaurantId,_tmpStartedAt,_tmpEndedAt,_tmpRating,_tmpNote,_tmpSource,_tmpCreatedAt,_tmpUpdatedAt);
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

  @NonNull
  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }

  private void __fetchRelationshiprestaurantsAscomForkprintAppDataLocalEntityRestaurantEntity(
      @NonNull final ArrayMap<String, RestaurantEntity> _map) {
    final Set<String> __mapKeySet = _map.keySet();
    if (__mapKeySet.isEmpty()) {
      return;
    }
    if (_map.size() > RoomDatabase.MAX_BIND_PARAMETER_CNT) {
      RelationUtil.recursiveFetchArrayMap(_map, false, (map) -> {
        __fetchRelationshiprestaurantsAscomForkprintAppDataLocalEntityRestaurantEntity(map);
        return Unit.INSTANCE;
      });
      return;
    }
    final StringBuilder _stringBuilder = StringUtil.newStringBuilder();
    _stringBuilder.append("SELECT `id`,`name`,`address`,`latitude`,`longitude`,`googlePlaceId`,`categories`,`createdAt`,`updatedAt` FROM `restaurants` WHERE `id` IN (");
    final int _inputSize = __mapKeySet.size();
    StringUtil.appendPlaceholders(_stringBuilder, _inputSize);
    _stringBuilder.append(")");
    final String _sql = _stringBuilder.toString();
    final int _argCount = 0 + _inputSize;
    final RoomSQLiteQuery _stmt = RoomSQLiteQuery.acquire(_sql, _argCount);
    int _argIndex = 1;
    for (String _item : __mapKeySet) {
      _stmt.bindString(_argIndex, _item);
      _argIndex++;
    }
    final Cursor _cursor = DBUtil.query(__db, _stmt, false, null);
    try {
      final int _itemKeyIndex = CursorUtil.getColumnIndex(_cursor, "id");
      if (_itemKeyIndex == -1) {
        return;
      }
      final int _cursorIndexOfId = 0;
      final int _cursorIndexOfName = 1;
      final int _cursorIndexOfAddress = 2;
      final int _cursorIndexOfLatitude = 3;
      final int _cursorIndexOfLongitude = 4;
      final int _cursorIndexOfGooglePlaceId = 5;
      final int _cursorIndexOfCategories = 6;
      final int _cursorIndexOfCreatedAt = 7;
      final int _cursorIndexOfUpdatedAt = 8;
      while (_cursor.moveToNext()) {
        final String _tmpKey;
        _tmpKey = _cursor.getString(_itemKeyIndex);
        if (_map.containsKey(_tmpKey)) {
          final RestaurantEntity _item_1;
          final String _tmpId;
          _tmpId = _cursor.getString(_cursorIndexOfId);
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
          final String _tmpGooglePlaceId;
          if (_cursor.isNull(_cursorIndexOfGooglePlaceId)) {
            _tmpGooglePlaceId = null;
          } else {
            _tmpGooglePlaceId = _cursor.getString(_cursorIndexOfGooglePlaceId);
          }
          final String _tmpCategories;
          _tmpCategories = _cursor.getString(_cursorIndexOfCategories);
          final Instant _tmpCreatedAt;
          final Long _tmp;
          if (_cursor.isNull(_cursorIndexOfCreatedAt)) {
            _tmp = null;
          } else {
            _tmp = _cursor.getLong(_cursorIndexOfCreatedAt);
          }
          final Instant _tmp_1 = __forkprintTypeConverters.epochMillisToInstant(_tmp);
          if (_tmp_1 == null) {
            throw new IllegalStateException("Expected NON-NULL 'java.time.Instant', but it was NULL.");
          } else {
            _tmpCreatedAt = _tmp_1;
          }
          final Instant _tmpUpdatedAt;
          final Long _tmp_2;
          if (_cursor.isNull(_cursorIndexOfUpdatedAt)) {
            _tmp_2 = null;
          } else {
            _tmp_2 = _cursor.getLong(_cursorIndexOfUpdatedAt);
          }
          final Instant _tmp_3 = __forkprintTypeConverters.epochMillisToInstant(_tmp_2);
          if (_tmp_3 == null) {
            throw new IllegalStateException("Expected NON-NULL 'java.time.Instant', but it was NULL.");
          } else {
            _tmpUpdatedAt = _tmp_3;
          }
          _item_1 = new RestaurantEntity(_tmpId,_tmpName,_tmpAddress,_tmpLatitude,_tmpLongitude,_tmpGooglePlaceId,_tmpCategories,_tmpCreatedAt,_tmpUpdatedAt);
          _map.put(_tmpKey, _item_1);
        }
      }
    } finally {
      _cursor.close();
    }
  }
}
