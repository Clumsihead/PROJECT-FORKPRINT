package com.forkprint.app.data.local;

import androidx.annotation.NonNull;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.RoomDatabase;
import androidx.room.RoomOpenHelper;
import androidx.room.migration.AutoMigrationSpec;
import androidx.room.migration.Migration;
import androidx.room.util.DBUtil;
import androidx.room.util.TableInfo;
import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;
import com.forkprint.app.data.local.dao.CachedPlaceDao;
import com.forkprint.app.data.local.dao.CachedPlaceDao_Impl;
import com.forkprint.app.data.local.dao.RestaurantDao;
import com.forkprint.app.data.local.dao.RestaurantDao_Impl;
import com.forkprint.app.data.local.dao.VisitDao;
import com.forkprint.app.data.local.dao.VisitDao_Impl;
import java.lang.Class;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.annotation.processing.Generated;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class ForkprintDatabase_Impl extends ForkprintDatabase {
  private volatile RestaurantDao _restaurantDao;

  private volatile VisitDao _visitDao;

  private volatile CachedPlaceDao _cachedPlaceDao;

  @Override
  @NonNull
  protected SupportSQLiteOpenHelper createOpenHelper(@NonNull final DatabaseConfiguration config) {
    final SupportSQLiteOpenHelper.Callback _openCallback = new RoomOpenHelper(config, new RoomOpenHelper.Delegate(3) {
      @Override
      public void createAllTables(@NonNull final SupportSQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS `restaurants` (`id` TEXT NOT NULL, `name` TEXT NOT NULL, `address` TEXT, `latitude` REAL, `longitude` REAL, `googlePlaceId` TEXT, `categories` TEXT NOT NULL, `createdAt` INTEGER NOT NULL, `updatedAt` INTEGER NOT NULL, PRIMARY KEY(`id`))");
        db.execSQL("CREATE UNIQUE INDEX IF NOT EXISTS `index_restaurants_googlePlaceId` ON `restaurants` (`googlePlaceId`)");
        db.execSQL("CREATE TABLE IF NOT EXISTS `visits` (`id` TEXT NOT NULL, `restaurantId` TEXT NOT NULL, `startedAt` INTEGER NOT NULL, `endedAt` INTEGER, `rating` INTEGER, `note` TEXT NOT NULL, `source` TEXT NOT NULL, `createdAt` INTEGER NOT NULL, `updatedAt` INTEGER NOT NULL, PRIMARY KEY(`id`), FOREIGN KEY(`restaurantId`) REFERENCES `restaurants`(`id`) ON UPDATE NO ACTION ON DELETE CASCADE )");
        db.execSQL("CREATE INDEX IF NOT EXISTS `index_visits_restaurantId` ON `visits` (`restaurantId`)");
        db.execSQL("CREATE INDEX IF NOT EXISTS `index_visits_startedAt` ON `visits` (`startedAt`)");
        db.execSQL("CREATE TABLE IF NOT EXISTS `cached_places` (`googlePlaceId` TEXT NOT NULL, `name` TEXT NOT NULL, `address` TEXT, `latitude` REAL, `longitude` REAL, `categories` TEXT NOT NULL, `fetchedAt` INTEGER NOT NULL, PRIMARY KEY(`googlePlaceId`))");
        db.execSQL("CREATE INDEX IF NOT EXISTS `index_cached_places_name` ON `cached_places` (`name`)");
        db.execSQL("CREATE INDEX IF NOT EXISTS `index_cached_places_latitude_longitude` ON `cached_places` (`latitude`, `longitude`)");
        db.execSQL("CREATE TABLE IF NOT EXISTS room_master_table (id INTEGER PRIMARY KEY,identity_hash TEXT)");
        db.execSQL("INSERT OR REPLACE INTO room_master_table (id,identity_hash) VALUES(42, '744c34e745995c3a58dabdc08677096b')");
      }

      @Override
      public void dropAllTables(@NonNull final SupportSQLiteDatabase db) {
        db.execSQL("DROP TABLE IF EXISTS `restaurants`");
        db.execSQL("DROP TABLE IF EXISTS `visits`");
        db.execSQL("DROP TABLE IF EXISTS `cached_places`");
        final List<? extends RoomDatabase.Callback> _callbacks = mCallbacks;
        if (_callbacks != null) {
          for (RoomDatabase.Callback _callback : _callbacks) {
            _callback.onDestructiveMigration(db);
          }
        }
      }

      @Override
      public void onCreate(@NonNull final SupportSQLiteDatabase db) {
        final List<? extends RoomDatabase.Callback> _callbacks = mCallbacks;
        if (_callbacks != null) {
          for (RoomDatabase.Callback _callback : _callbacks) {
            _callback.onCreate(db);
          }
        }
      }

      @Override
      public void onOpen(@NonNull final SupportSQLiteDatabase db) {
        mDatabase = db;
        db.execSQL("PRAGMA foreign_keys = ON");
        internalInitInvalidationTracker(db);
        final List<? extends RoomDatabase.Callback> _callbacks = mCallbacks;
        if (_callbacks != null) {
          for (RoomDatabase.Callback _callback : _callbacks) {
            _callback.onOpen(db);
          }
        }
      }

      @Override
      public void onPreMigrate(@NonNull final SupportSQLiteDatabase db) {
        DBUtil.dropFtsSyncTriggers(db);
      }

      @Override
      public void onPostMigrate(@NonNull final SupportSQLiteDatabase db) {
      }

      @Override
      @NonNull
      public RoomOpenHelper.ValidationResult onValidateSchema(
          @NonNull final SupportSQLiteDatabase db) {
        final HashMap<String, TableInfo.Column> _columnsRestaurants = new HashMap<String, TableInfo.Column>(9);
        _columnsRestaurants.put("id", new TableInfo.Column("id", "TEXT", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsRestaurants.put("name", new TableInfo.Column("name", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsRestaurants.put("address", new TableInfo.Column("address", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsRestaurants.put("latitude", new TableInfo.Column("latitude", "REAL", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsRestaurants.put("longitude", new TableInfo.Column("longitude", "REAL", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsRestaurants.put("googlePlaceId", new TableInfo.Column("googlePlaceId", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsRestaurants.put("categories", new TableInfo.Column("categories", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsRestaurants.put("createdAt", new TableInfo.Column("createdAt", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsRestaurants.put("updatedAt", new TableInfo.Column("updatedAt", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysRestaurants = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesRestaurants = new HashSet<TableInfo.Index>(1);
        _indicesRestaurants.add(new TableInfo.Index("index_restaurants_googlePlaceId", true, Arrays.asList("googlePlaceId"), Arrays.asList("ASC")));
        final TableInfo _infoRestaurants = new TableInfo("restaurants", _columnsRestaurants, _foreignKeysRestaurants, _indicesRestaurants);
        final TableInfo _existingRestaurants = TableInfo.read(db, "restaurants");
        if (!_infoRestaurants.equals(_existingRestaurants)) {
          return new RoomOpenHelper.ValidationResult(false, "restaurants(com.forkprint.app.data.local.entity.RestaurantEntity).\n"
                  + " Expected:\n" + _infoRestaurants + "\n"
                  + " Found:\n" + _existingRestaurants);
        }
        final HashMap<String, TableInfo.Column> _columnsVisits = new HashMap<String, TableInfo.Column>(9);
        _columnsVisits.put("id", new TableInfo.Column("id", "TEXT", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsVisits.put("restaurantId", new TableInfo.Column("restaurantId", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsVisits.put("startedAt", new TableInfo.Column("startedAt", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsVisits.put("endedAt", new TableInfo.Column("endedAt", "INTEGER", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsVisits.put("rating", new TableInfo.Column("rating", "INTEGER", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsVisits.put("note", new TableInfo.Column("note", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsVisits.put("source", new TableInfo.Column("source", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsVisits.put("createdAt", new TableInfo.Column("createdAt", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsVisits.put("updatedAt", new TableInfo.Column("updatedAt", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysVisits = new HashSet<TableInfo.ForeignKey>(1);
        _foreignKeysVisits.add(new TableInfo.ForeignKey("restaurants", "CASCADE", "NO ACTION", Arrays.asList("restaurantId"), Arrays.asList("id")));
        final HashSet<TableInfo.Index> _indicesVisits = new HashSet<TableInfo.Index>(2);
        _indicesVisits.add(new TableInfo.Index("index_visits_restaurantId", false, Arrays.asList("restaurantId"), Arrays.asList("ASC")));
        _indicesVisits.add(new TableInfo.Index("index_visits_startedAt", false, Arrays.asList("startedAt"), Arrays.asList("ASC")));
        final TableInfo _infoVisits = new TableInfo("visits", _columnsVisits, _foreignKeysVisits, _indicesVisits);
        final TableInfo _existingVisits = TableInfo.read(db, "visits");
        if (!_infoVisits.equals(_existingVisits)) {
          return new RoomOpenHelper.ValidationResult(false, "visits(com.forkprint.app.data.local.entity.VisitEntity).\n"
                  + " Expected:\n" + _infoVisits + "\n"
                  + " Found:\n" + _existingVisits);
        }
        final HashMap<String, TableInfo.Column> _columnsCachedPlaces = new HashMap<String, TableInfo.Column>(7);
        _columnsCachedPlaces.put("googlePlaceId", new TableInfo.Column("googlePlaceId", "TEXT", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCachedPlaces.put("name", new TableInfo.Column("name", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCachedPlaces.put("address", new TableInfo.Column("address", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCachedPlaces.put("latitude", new TableInfo.Column("latitude", "REAL", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCachedPlaces.put("longitude", new TableInfo.Column("longitude", "REAL", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCachedPlaces.put("categories", new TableInfo.Column("categories", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCachedPlaces.put("fetchedAt", new TableInfo.Column("fetchedAt", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysCachedPlaces = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesCachedPlaces = new HashSet<TableInfo.Index>(2);
        _indicesCachedPlaces.add(new TableInfo.Index("index_cached_places_name", false, Arrays.asList("name"), Arrays.asList("ASC")));
        _indicesCachedPlaces.add(new TableInfo.Index("index_cached_places_latitude_longitude", false, Arrays.asList("latitude", "longitude"), Arrays.asList("ASC", "ASC")));
        final TableInfo _infoCachedPlaces = new TableInfo("cached_places", _columnsCachedPlaces, _foreignKeysCachedPlaces, _indicesCachedPlaces);
        final TableInfo _existingCachedPlaces = TableInfo.read(db, "cached_places");
        if (!_infoCachedPlaces.equals(_existingCachedPlaces)) {
          return new RoomOpenHelper.ValidationResult(false, "cached_places(com.forkprint.app.data.local.entity.CachedPlaceEntity).\n"
                  + " Expected:\n" + _infoCachedPlaces + "\n"
                  + " Found:\n" + _existingCachedPlaces);
        }
        return new RoomOpenHelper.ValidationResult(true, null);
      }
    }, "744c34e745995c3a58dabdc08677096b", "31b66ad4c0739c8ae519ab0c7cfdf709");
    final SupportSQLiteOpenHelper.Configuration _sqliteConfig = SupportSQLiteOpenHelper.Configuration.builder(config.context).name(config.name).callback(_openCallback).build();
    final SupportSQLiteOpenHelper _helper = config.sqliteOpenHelperFactory.create(_sqliteConfig);
    return _helper;
  }

  @Override
  @NonNull
  protected InvalidationTracker createInvalidationTracker() {
    final HashMap<String, String> _shadowTablesMap = new HashMap<String, String>(0);
    final HashMap<String, Set<String>> _viewTables = new HashMap<String, Set<String>>(0);
    return new InvalidationTracker(this, _shadowTablesMap, _viewTables, "restaurants","visits","cached_places");
  }

  @Override
  public void clearAllTables() {
    super.assertNotMainThread();
    final SupportSQLiteDatabase _db = super.getOpenHelper().getWritableDatabase();
    final boolean _supportsDeferForeignKeys = android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP;
    try {
      if (!_supportsDeferForeignKeys) {
        _db.execSQL("PRAGMA foreign_keys = FALSE");
      }
      super.beginTransaction();
      if (_supportsDeferForeignKeys) {
        _db.execSQL("PRAGMA defer_foreign_keys = TRUE");
      }
      _db.execSQL("DELETE FROM `restaurants`");
      _db.execSQL("DELETE FROM `visits`");
      _db.execSQL("DELETE FROM `cached_places`");
      super.setTransactionSuccessful();
    } finally {
      super.endTransaction();
      if (!_supportsDeferForeignKeys) {
        _db.execSQL("PRAGMA foreign_keys = TRUE");
      }
      _db.query("PRAGMA wal_checkpoint(FULL)").close();
      if (!_db.inTransaction()) {
        _db.execSQL("VACUUM");
      }
    }
  }

  @Override
  @NonNull
  protected Map<Class<?>, List<Class<?>>> getRequiredTypeConverters() {
    final HashMap<Class<?>, List<Class<?>>> _typeConvertersMap = new HashMap<Class<?>, List<Class<?>>>();
    _typeConvertersMap.put(RestaurantDao.class, RestaurantDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(VisitDao.class, VisitDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(CachedPlaceDao.class, CachedPlaceDao_Impl.getRequiredConverters());
    return _typeConvertersMap;
  }

  @Override
  @NonNull
  public Set<Class<? extends AutoMigrationSpec>> getRequiredAutoMigrationSpecs() {
    final HashSet<Class<? extends AutoMigrationSpec>> _autoMigrationSpecsSet = new HashSet<Class<? extends AutoMigrationSpec>>();
    return _autoMigrationSpecsSet;
  }

  @Override
  @NonNull
  public List<Migration> getAutoMigrations(
      @NonNull final Map<Class<? extends AutoMigrationSpec>, AutoMigrationSpec> autoMigrationSpecs) {
    final List<Migration> _autoMigrations = new ArrayList<Migration>();
    return _autoMigrations;
  }

  @Override
  public RestaurantDao restaurantDao() {
    if (_restaurantDao != null) {
      return _restaurantDao;
    } else {
      synchronized(this) {
        if(_restaurantDao == null) {
          _restaurantDao = new RestaurantDao_Impl(this);
        }
        return _restaurantDao;
      }
    }
  }

  @Override
  public VisitDao visitDao() {
    if (_visitDao != null) {
      return _visitDao;
    } else {
      synchronized(this) {
        if(_visitDao == null) {
          _visitDao = new VisitDao_Impl(this);
        }
        return _visitDao;
      }
    }
  }

  @Override
  public CachedPlaceDao cachedPlaceDao() {
    if (_cachedPlaceDao != null) {
      return _cachedPlaceDao;
    } else {
      synchronized(this) {
        if(_cachedPlaceDao == null) {
          _cachedPlaceDao = new CachedPlaceDao_Impl(this);
        }
        return _cachedPlaceDao;
      }
    }
  }
}
