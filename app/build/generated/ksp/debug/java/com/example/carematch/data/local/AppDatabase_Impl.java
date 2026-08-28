package com.example.carematch.data.local;

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
import java.lang.Class;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.annotation.processing.Generated;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class AppDatabase_Impl extends AppDatabase {
  private volatile CaregiverDao _caregiverDao;

  private volatile CareRequestDao _careRequestDao;

  private volatile ReviewDao _reviewDao;

  private volatile NotificationDao _notificationDao;

  @Override
  @NonNull
  protected SupportSQLiteOpenHelper createOpenHelper(@NonNull final DatabaseConfiguration config) {
    final SupportSQLiteOpenHelper.Callback _openCallback = new RoomOpenHelper(config, new RoomOpenHelper.Delegate(1) {
      @Override
      public void createAllTables(@NonNull final SupportSQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS `caregiver_profiles` (`profileId` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `userId` TEXT NOT NULL, `name` TEXT NOT NULL, `avatarUrl` TEXT NOT NULL, `gender` TEXT NOT NULL, `age` INTEGER NOT NULL, `rating` REAL NOT NULL, `reviewCount` INTEGER NOT NULL, `completedCases` INTEGER NOT NULL, `experienceYears` INTEGER NOT NULL, `location` TEXT NOT NULL, `desiredDailyPay` INTEGER NOT NULL, `certifications` TEXT NOT NULL, `specialties` TEXT NOT NULL, `bio` TEXT NOT NULL, `detailedIntroduction` TEXT NOT NULL, `availableSchedule` TEXT NOT NULL, `isActive` INTEGER NOT NULL, `phone` TEXT NOT NULL, `badges` TEXT NOT NULL)");
        db.execSQL("CREATE TABLE IF NOT EXISTS `care_requests` (`requestId` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `guardianId` TEXT NOT NULL, `guardianName` TEXT NOT NULL, `guardianPhone` TEXT NOT NULL, `caregiverId` INTEGER NOT NULL, `caregiverName` TEXT NOT NULL, `caregiverAvatar` TEXT NOT NULL, `caregiverPhone` TEXT NOT NULL, `patientGender` TEXT NOT NULL, `patientAgeGroup` TEXT NOT NULL, `patientDiagnosis` TEXT NOT NULL, `careLevel` TEXT NOT NULL, `locationType` TEXT NOT NULL, `locationAddress` TEXT NOT NULL, `careType` TEXT NOT NULL, `startDate` TEXT NOT NULL, `endDate` TEXT NOT NULL, `durationDays` INTEGER NOT NULL, `offeredDailyPay` INTEGER NOT NULL, `totalEstimatedPay` INTEGER NOT NULL, `specialNotes` TEXT NOT NULL, `status` TEXT NOT NULL, `rejectionReason` TEXT, `createdAt` INTEGER NOT NULL, `updatedAt` INTEGER NOT NULL)");
        db.execSQL("CREATE TABLE IF NOT EXISTS `caregiver_reviews` (`reviewId` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `caregiverId` INTEGER NOT NULL, `guardianName` TEXT NOT NULL, `rating` REAL NOT NULL, `date` TEXT NOT NULL, `content` TEXT NOT NULL, `patientCondition` TEXT NOT NULL, `period` TEXT NOT NULL)");
        db.execSQL("CREATE TABLE IF NOT EXISTS `alimtalk_notifications` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `title` TEXT NOT NULL, `message` TEXT NOT NULL, `timestamp` INTEGER NOT NULL, `type` TEXT NOT NULL, `relatedRequestId` INTEGER, `isRead` INTEGER NOT NULL)");
        db.execSQL("CREATE TABLE IF NOT EXISTS room_master_table (id INTEGER PRIMARY KEY,identity_hash TEXT)");
        db.execSQL("INSERT OR REPLACE INTO room_master_table (id,identity_hash) VALUES(42, '855f6d6b3f556e2fbadb3dc5c4bdafdd')");
      }

      @Override
      public void dropAllTables(@NonNull final SupportSQLiteDatabase db) {
        db.execSQL("DROP TABLE IF EXISTS `caregiver_profiles`");
        db.execSQL("DROP TABLE IF EXISTS `care_requests`");
        db.execSQL("DROP TABLE IF EXISTS `caregiver_reviews`");
        db.execSQL("DROP TABLE IF EXISTS `alimtalk_notifications`");
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
        final HashMap<String, TableInfo.Column> _columnsCaregiverProfiles = new HashMap<String, TableInfo.Column>(20);
        _columnsCaregiverProfiles.put("profileId", new TableInfo.Column("profileId", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCaregiverProfiles.put("userId", new TableInfo.Column("userId", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCaregiverProfiles.put("name", new TableInfo.Column("name", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCaregiverProfiles.put("avatarUrl", new TableInfo.Column("avatarUrl", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCaregiverProfiles.put("gender", new TableInfo.Column("gender", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCaregiverProfiles.put("age", new TableInfo.Column("age", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCaregiverProfiles.put("rating", new TableInfo.Column("rating", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCaregiverProfiles.put("reviewCount", new TableInfo.Column("reviewCount", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCaregiverProfiles.put("completedCases", new TableInfo.Column("completedCases", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCaregiverProfiles.put("experienceYears", new TableInfo.Column("experienceYears", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCaregiverProfiles.put("location", new TableInfo.Column("location", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCaregiverProfiles.put("desiredDailyPay", new TableInfo.Column("desiredDailyPay", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCaregiverProfiles.put("certifications", new TableInfo.Column("certifications", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCaregiverProfiles.put("specialties", new TableInfo.Column("specialties", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCaregiverProfiles.put("bio", new TableInfo.Column("bio", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCaregiverProfiles.put("detailedIntroduction", new TableInfo.Column("detailedIntroduction", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCaregiverProfiles.put("availableSchedule", new TableInfo.Column("availableSchedule", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCaregiverProfiles.put("isActive", new TableInfo.Column("isActive", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCaregiverProfiles.put("phone", new TableInfo.Column("phone", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCaregiverProfiles.put("badges", new TableInfo.Column("badges", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysCaregiverProfiles = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesCaregiverProfiles = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoCaregiverProfiles = new TableInfo("caregiver_profiles", _columnsCaregiverProfiles, _foreignKeysCaregiverProfiles, _indicesCaregiverProfiles);
        final TableInfo _existingCaregiverProfiles = TableInfo.read(db, "caregiver_profiles");
        if (!_infoCaregiverProfiles.equals(_existingCaregiverProfiles)) {
          return new RoomOpenHelper.ValidationResult(false, "caregiver_profiles(com.example.carematch.data.model.CaregiverProfile).\n"
                  + " Expected:\n" + _infoCaregiverProfiles + "\n"
                  + " Found:\n" + _existingCaregiverProfiles);
        }
        final HashMap<String, TableInfo.Column> _columnsCareRequests = new HashMap<String, TableInfo.Column>(25);
        _columnsCareRequests.put("requestId", new TableInfo.Column("requestId", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCareRequests.put("guardianId", new TableInfo.Column("guardianId", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCareRequests.put("guardianName", new TableInfo.Column("guardianName", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCareRequests.put("guardianPhone", new TableInfo.Column("guardianPhone", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCareRequests.put("caregiverId", new TableInfo.Column("caregiverId", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCareRequests.put("caregiverName", new TableInfo.Column("caregiverName", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCareRequests.put("caregiverAvatar", new TableInfo.Column("caregiverAvatar", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCareRequests.put("caregiverPhone", new TableInfo.Column("caregiverPhone", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCareRequests.put("patientGender", new TableInfo.Column("patientGender", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCareRequests.put("patientAgeGroup", new TableInfo.Column("patientAgeGroup", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCareRequests.put("patientDiagnosis", new TableInfo.Column("patientDiagnosis", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCareRequests.put("careLevel", new TableInfo.Column("careLevel", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCareRequests.put("locationType", new TableInfo.Column("locationType", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCareRequests.put("locationAddress", new TableInfo.Column("locationAddress", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCareRequests.put("careType", new TableInfo.Column("careType", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCareRequests.put("startDate", new TableInfo.Column("startDate", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCareRequests.put("endDate", new TableInfo.Column("endDate", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCareRequests.put("durationDays", new TableInfo.Column("durationDays", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCareRequests.put("offeredDailyPay", new TableInfo.Column("offeredDailyPay", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCareRequests.put("totalEstimatedPay", new TableInfo.Column("totalEstimatedPay", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCareRequests.put("specialNotes", new TableInfo.Column("specialNotes", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCareRequests.put("status", new TableInfo.Column("status", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCareRequests.put("rejectionReason", new TableInfo.Column("rejectionReason", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCareRequests.put("createdAt", new TableInfo.Column("createdAt", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCareRequests.put("updatedAt", new TableInfo.Column("updatedAt", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysCareRequests = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesCareRequests = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoCareRequests = new TableInfo("care_requests", _columnsCareRequests, _foreignKeysCareRequests, _indicesCareRequests);
        final TableInfo _existingCareRequests = TableInfo.read(db, "care_requests");
        if (!_infoCareRequests.equals(_existingCareRequests)) {
          return new RoomOpenHelper.ValidationResult(false, "care_requests(com.example.carematch.data.model.CareRequest).\n"
                  + " Expected:\n" + _infoCareRequests + "\n"
                  + " Found:\n" + _existingCareRequests);
        }
        final HashMap<String, TableInfo.Column> _columnsCaregiverReviews = new HashMap<String, TableInfo.Column>(8);
        _columnsCaregiverReviews.put("reviewId", new TableInfo.Column("reviewId", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCaregiverReviews.put("caregiverId", new TableInfo.Column("caregiverId", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCaregiverReviews.put("guardianName", new TableInfo.Column("guardianName", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCaregiverReviews.put("rating", new TableInfo.Column("rating", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCaregiverReviews.put("date", new TableInfo.Column("date", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCaregiverReviews.put("content", new TableInfo.Column("content", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCaregiverReviews.put("patientCondition", new TableInfo.Column("patientCondition", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCaregiverReviews.put("period", new TableInfo.Column("period", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysCaregiverReviews = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesCaregiverReviews = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoCaregiverReviews = new TableInfo("caregiver_reviews", _columnsCaregiverReviews, _foreignKeysCaregiverReviews, _indicesCaregiverReviews);
        final TableInfo _existingCaregiverReviews = TableInfo.read(db, "caregiver_reviews");
        if (!_infoCaregiverReviews.equals(_existingCaregiverReviews)) {
          return new RoomOpenHelper.ValidationResult(false, "caregiver_reviews(com.example.carematch.data.model.Review).\n"
                  + " Expected:\n" + _infoCaregiverReviews + "\n"
                  + " Found:\n" + _existingCaregiverReviews);
        }
        final HashMap<String, TableInfo.Column> _columnsAlimtalkNotifications = new HashMap<String, TableInfo.Column>(7);
        _columnsAlimtalkNotifications.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsAlimtalkNotifications.put("title", new TableInfo.Column("title", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsAlimtalkNotifications.put("message", new TableInfo.Column("message", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsAlimtalkNotifications.put("timestamp", new TableInfo.Column("timestamp", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsAlimtalkNotifications.put("type", new TableInfo.Column("type", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsAlimtalkNotifications.put("relatedRequestId", new TableInfo.Column("relatedRequestId", "INTEGER", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsAlimtalkNotifications.put("isRead", new TableInfo.Column("isRead", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysAlimtalkNotifications = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesAlimtalkNotifications = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoAlimtalkNotifications = new TableInfo("alimtalk_notifications", _columnsAlimtalkNotifications, _foreignKeysAlimtalkNotifications, _indicesAlimtalkNotifications);
        final TableInfo _existingAlimtalkNotifications = TableInfo.read(db, "alimtalk_notifications");
        if (!_infoAlimtalkNotifications.equals(_existingAlimtalkNotifications)) {
          return new RoomOpenHelper.ValidationResult(false, "alimtalk_notifications(com.example.carematch.data.model.NotificationItem).\n"
                  + " Expected:\n" + _infoAlimtalkNotifications + "\n"
                  + " Found:\n" + _existingAlimtalkNotifications);
        }
        return new RoomOpenHelper.ValidationResult(true, null);
      }
    }, "855f6d6b3f556e2fbadb3dc5c4bdafdd", "83bb3a2a1dea44f60f1d904a17d20646");
    final SupportSQLiteOpenHelper.Configuration _sqliteConfig = SupportSQLiteOpenHelper.Configuration.builder(config.context).name(config.name).callback(_openCallback).build();
    final SupportSQLiteOpenHelper _helper = config.sqliteOpenHelperFactory.create(_sqliteConfig);
    return _helper;
  }

  @Override
  @NonNull
  protected InvalidationTracker createInvalidationTracker() {
    final HashMap<String, String> _shadowTablesMap = new HashMap<String, String>(0);
    final HashMap<String, Set<String>> _viewTables = new HashMap<String, Set<String>>(0);
    return new InvalidationTracker(this, _shadowTablesMap, _viewTables, "caregiver_profiles","care_requests","caregiver_reviews","alimtalk_notifications");
  }

  @Override
  public void clearAllTables() {
    super.assertNotMainThread();
    final SupportSQLiteDatabase _db = super.getOpenHelper().getWritableDatabase();
    try {
      super.beginTransaction();
      _db.execSQL("DELETE FROM `caregiver_profiles`");
      _db.execSQL("DELETE FROM `care_requests`");
      _db.execSQL("DELETE FROM `caregiver_reviews`");
      _db.execSQL("DELETE FROM `alimtalk_notifications`");
      super.setTransactionSuccessful();
    } finally {
      super.endTransaction();
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
    _typeConvertersMap.put(CaregiverDao.class, CaregiverDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(CareRequestDao.class, CareRequestDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(ReviewDao.class, ReviewDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(NotificationDao.class, NotificationDao_Impl.getRequiredConverters());
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
  public CaregiverDao caregiverDao() {
    if (_caregiverDao != null) {
      return _caregiverDao;
    } else {
      synchronized(this) {
        if(_caregiverDao == null) {
          _caregiverDao = new CaregiverDao_Impl(this);
        }
        return _caregiverDao;
      }
    }
  }

  @Override
  public CareRequestDao careRequestDao() {
    if (_careRequestDao != null) {
      return _careRequestDao;
    } else {
      synchronized(this) {
        if(_careRequestDao == null) {
          _careRequestDao = new CareRequestDao_Impl(this);
        }
        return _careRequestDao;
      }
    }
  }

  @Override
  public ReviewDao reviewDao() {
    if (_reviewDao != null) {
      return _reviewDao;
    } else {
      synchronized(this) {
        if(_reviewDao == null) {
          _reviewDao = new ReviewDao_Impl(this);
        }
        return _reviewDao;
      }
    }
  }

  @Override
  public NotificationDao notificationDao() {
    if (_notificationDao != null) {
      return _notificationDao;
    } else {
      synchronized(this) {
        if(_notificationDao == null) {
          _notificationDao = new NotificationDao_Impl(this);
        }
        return _notificationDao;
      }
    }
  }
}
