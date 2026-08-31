package com.example.careplus.data.local;

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
  private volatile CareDao _careDao;

  @Override
  @NonNull
  protected SupportSQLiteOpenHelper createOpenHelper(@NonNull final DatabaseConfiguration config) {
    final SupportSQLiteOpenHelper.Callback _openCallback = new RoomOpenHelper(config, new RoomOpenHelper.Delegate(1) {
      @Override
      public void createAllTables(@NonNull final SupportSQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS `care_requests` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `guardianName` TEXT NOT NULL, `location` TEXT NOT NULL, `hospitalName` TEXT NOT NULL, `careType` TEXT NOT NULL, `mobility` TEXT NOT NULL, `consciousness` TEXT NOT NULL, `weightRange` TEXT NOT NULL, `gender` TEXT NOT NULL, `ageRange` TEXT NOT NULL, `specialNeeds` TEXT NOT NULL, `startDate` TEXT NOT NULL, `endDate` TEXT NOT NULL, `totalDays` INTEGER NOT NULL, `status` TEXT NOT NULL, `createdAt` INTEGER NOT NULL)");
        db.execSQL("CREATE TABLE IF NOT EXISTS `caregivers` (`caregiverId` TEXT NOT NULL, `name` TEXT NOT NULL, `careerYears` INTEGER NOT NULL, `rating` REAL NOT NULL, `reviewCount` INTEGER NOT NULL, `distanceKm` REAL NOT NULL, `travelTimeMinutes` INTEGER NOT NULL, `certList` TEXT NOT NULL, `insuranceYn` INTEGER NOT NULL, `vaccineYn` INTEGER NOT NULL, `gender` TEXT NOT NULL, `bio` TEXT NOT NULL, `phoneMasked` TEXT NOT NULL, PRIMARY KEY(`caregiverId`))");
        db.execSQL("CREATE TABLE IF NOT EXISTS `care_bids` (`bidId` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `requestId` INTEGER NOT NULL, `caregiverId` TEXT NOT NULL, `caregiverName` TEXT NOT NULL, `careerYears` INTEGER NOT NULL, `rating` REAL NOT NULL, `reviewCount` INTEGER NOT NULL, `distanceKm` REAL NOT NULL, `travelTimeMinutes` INTEGER NOT NULL, `certList` TEXT NOT NULL, `insuranceYn` INTEGER NOT NULL, `vaccineYn` INTEGER NOT NULL, `gender` TEXT NOT NULL, `pitchMessage` TEXT NOT NULL, `dailyPrice` INTEGER NOT NULL, `status` TEXT NOT NULL, `createdAt` INTEGER NOT NULL)");
        db.execSQL("CREATE TABLE IF NOT EXISTS `chat_messages` (`messageId` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `bidId` INTEGER NOT NULL, `senderRole` TEXT NOT NULL, `senderName` TEXT NOT NULL, `content` TEXT NOT NULL, `isInvoice` INTEGER NOT NULL, `invoiceTotalPrice` INTEGER NOT NULL, `invoiceDays` INTEGER NOT NULL, `timestamp` INTEGER NOT NULL)");
        db.execSQL("CREATE TABLE IF NOT EXISTS `contracts` (`contractId` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `requestId` INTEGER NOT NULL, `bidId` INTEGER NOT NULL, `caregiverId` TEXT NOT NULL, `caregiverName` TEXT NOT NULL, `guardianName` TEXT NOT NULL, `location` TEXT NOT NULL, `dates` TEXT NOT NULL, `dailyPrice` INTEGER NOT NULL, `totalDays` INTEGER NOT NULL, `supplyPrice` INTEGER NOT NULL, `platformFee` INTEGER NOT NULL, `totalPrice` INTEGER NOT NULL, `escrowStatus` TEXT NOT NULL, `isReviewed` INTEGER NOT NULL, `ratingGiven` REAL NOT NULL, `reviewComment` TEXT NOT NULL, `createdAt` INTEGER NOT NULL)");
        db.execSQL("CREATE TABLE IF NOT EXISTS room_master_table (id INTEGER PRIMARY KEY,identity_hash TEXT)");
        db.execSQL("INSERT OR REPLACE INTO room_master_table (id,identity_hash) VALUES(42, '88361f90fecb5c188ebb377e0164c6db')");
      }

      @Override
      public void dropAllTables(@NonNull final SupportSQLiteDatabase db) {
        db.execSQL("DROP TABLE IF EXISTS `care_requests`");
        db.execSQL("DROP TABLE IF EXISTS `caregivers`");
        db.execSQL("DROP TABLE IF EXISTS `care_bids`");
        db.execSQL("DROP TABLE IF EXISTS `chat_messages`");
        db.execSQL("DROP TABLE IF EXISTS `contracts`");
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
        final HashMap<String, TableInfo.Column> _columnsCareRequests = new HashMap<String, TableInfo.Column>(16);
        _columnsCareRequests.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCareRequests.put("guardianName", new TableInfo.Column("guardianName", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCareRequests.put("location", new TableInfo.Column("location", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCareRequests.put("hospitalName", new TableInfo.Column("hospitalName", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCareRequests.put("careType", new TableInfo.Column("careType", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCareRequests.put("mobility", new TableInfo.Column("mobility", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCareRequests.put("consciousness", new TableInfo.Column("consciousness", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCareRequests.put("weightRange", new TableInfo.Column("weightRange", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCareRequests.put("gender", new TableInfo.Column("gender", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCareRequests.put("ageRange", new TableInfo.Column("ageRange", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCareRequests.put("specialNeeds", new TableInfo.Column("specialNeeds", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCareRequests.put("startDate", new TableInfo.Column("startDate", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCareRequests.put("endDate", new TableInfo.Column("endDate", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCareRequests.put("totalDays", new TableInfo.Column("totalDays", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCareRequests.put("status", new TableInfo.Column("status", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCareRequests.put("createdAt", new TableInfo.Column("createdAt", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysCareRequests = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesCareRequests = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoCareRequests = new TableInfo("care_requests", _columnsCareRequests, _foreignKeysCareRequests, _indicesCareRequests);
        final TableInfo _existingCareRequests = TableInfo.read(db, "care_requests");
        if (!_infoCareRequests.equals(_existingCareRequests)) {
          return new RoomOpenHelper.ValidationResult(false, "care_requests(com.example.careplus.data.local.CareRequestEntity).\n"
                  + " Expected:\n" + _infoCareRequests + "\n"
                  + " Found:\n" + _existingCareRequests);
        }
        final HashMap<String, TableInfo.Column> _columnsCaregivers = new HashMap<String, TableInfo.Column>(13);
        _columnsCaregivers.put("caregiverId", new TableInfo.Column("caregiverId", "TEXT", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCaregivers.put("name", new TableInfo.Column("name", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCaregivers.put("careerYears", new TableInfo.Column("careerYears", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCaregivers.put("rating", new TableInfo.Column("rating", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCaregivers.put("reviewCount", new TableInfo.Column("reviewCount", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCaregivers.put("distanceKm", new TableInfo.Column("distanceKm", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCaregivers.put("travelTimeMinutes", new TableInfo.Column("travelTimeMinutes", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCaregivers.put("certList", new TableInfo.Column("certList", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCaregivers.put("insuranceYn", new TableInfo.Column("insuranceYn", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCaregivers.put("vaccineYn", new TableInfo.Column("vaccineYn", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCaregivers.put("gender", new TableInfo.Column("gender", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCaregivers.put("bio", new TableInfo.Column("bio", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCaregivers.put("phoneMasked", new TableInfo.Column("phoneMasked", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysCaregivers = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesCaregivers = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoCaregivers = new TableInfo("caregivers", _columnsCaregivers, _foreignKeysCaregivers, _indicesCaregivers);
        final TableInfo _existingCaregivers = TableInfo.read(db, "caregivers");
        if (!_infoCaregivers.equals(_existingCaregivers)) {
          return new RoomOpenHelper.ValidationResult(false, "caregivers(com.example.careplus.data.local.CaregiverProfileEntity).\n"
                  + " Expected:\n" + _infoCaregivers + "\n"
                  + " Found:\n" + _existingCaregivers);
        }
        final HashMap<String, TableInfo.Column> _columnsCareBids = new HashMap<String, TableInfo.Column>(17);
        _columnsCareBids.put("bidId", new TableInfo.Column("bidId", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCareBids.put("requestId", new TableInfo.Column("requestId", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCareBids.put("caregiverId", new TableInfo.Column("caregiverId", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCareBids.put("caregiverName", new TableInfo.Column("caregiverName", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCareBids.put("careerYears", new TableInfo.Column("careerYears", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCareBids.put("rating", new TableInfo.Column("rating", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCareBids.put("reviewCount", new TableInfo.Column("reviewCount", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCareBids.put("distanceKm", new TableInfo.Column("distanceKm", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCareBids.put("travelTimeMinutes", new TableInfo.Column("travelTimeMinutes", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCareBids.put("certList", new TableInfo.Column("certList", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCareBids.put("insuranceYn", new TableInfo.Column("insuranceYn", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCareBids.put("vaccineYn", new TableInfo.Column("vaccineYn", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCareBids.put("gender", new TableInfo.Column("gender", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCareBids.put("pitchMessage", new TableInfo.Column("pitchMessage", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCareBids.put("dailyPrice", new TableInfo.Column("dailyPrice", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCareBids.put("status", new TableInfo.Column("status", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCareBids.put("createdAt", new TableInfo.Column("createdAt", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysCareBids = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesCareBids = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoCareBids = new TableInfo("care_bids", _columnsCareBids, _foreignKeysCareBids, _indicesCareBids);
        final TableInfo _existingCareBids = TableInfo.read(db, "care_bids");
        if (!_infoCareBids.equals(_existingCareBids)) {
          return new RoomOpenHelper.ValidationResult(false, "care_bids(com.example.careplus.data.local.CareBidEntity).\n"
                  + " Expected:\n" + _infoCareBids + "\n"
                  + " Found:\n" + _existingCareBids);
        }
        final HashMap<String, TableInfo.Column> _columnsChatMessages = new HashMap<String, TableInfo.Column>(9);
        _columnsChatMessages.put("messageId", new TableInfo.Column("messageId", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsChatMessages.put("bidId", new TableInfo.Column("bidId", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsChatMessages.put("senderRole", new TableInfo.Column("senderRole", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsChatMessages.put("senderName", new TableInfo.Column("senderName", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsChatMessages.put("content", new TableInfo.Column("content", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsChatMessages.put("isInvoice", new TableInfo.Column("isInvoice", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsChatMessages.put("invoiceTotalPrice", new TableInfo.Column("invoiceTotalPrice", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsChatMessages.put("invoiceDays", new TableInfo.Column("invoiceDays", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsChatMessages.put("timestamp", new TableInfo.Column("timestamp", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysChatMessages = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesChatMessages = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoChatMessages = new TableInfo("chat_messages", _columnsChatMessages, _foreignKeysChatMessages, _indicesChatMessages);
        final TableInfo _existingChatMessages = TableInfo.read(db, "chat_messages");
        if (!_infoChatMessages.equals(_existingChatMessages)) {
          return new RoomOpenHelper.ValidationResult(false, "chat_messages(com.example.careplus.data.local.ChatMessageEntity).\n"
                  + " Expected:\n" + _infoChatMessages + "\n"
                  + " Found:\n" + _existingChatMessages);
        }
        final HashMap<String, TableInfo.Column> _columnsContracts = new HashMap<String, TableInfo.Column>(18);
        _columnsContracts.put("contractId", new TableInfo.Column("contractId", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsContracts.put("requestId", new TableInfo.Column("requestId", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsContracts.put("bidId", new TableInfo.Column("bidId", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsContracts.put("caregiverId", new TableInfo.Column("caregiverId", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsContracts.put("caregiverName", new TableInfo.Column("caregiverName", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsContracts.put("guardianName", new TableInfo.Column("guardianName", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsContracts.put("location", new TableInfo.Column("location", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsContracts.put("dates", new TableInfo.Column("dates", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsContracts.put("dailyPrice", new TableInfo.Column("dailyPrice", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsContracts.put("totalDays", new TableInfo.Column("totalDays", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsContracts.put("supplyPrice", new TableInfo.Column("supplyPrice", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsContracts.put("platformFee", new TableInfo.Column("platformFee", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsContracts.put("totalPrice", new TableInfo.Column("totalPrice", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsContracts.put("escrowStatus", new TableInfo.Column("escrowStatus", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsContracts.put("isReviewed", new TableInfo.Column("isReviewed", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsContracts.put("ratingGiven", new TableInfo.Column("ratingGiven", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsContracts.put("reviewComment", new TableInfo.Column("reviewComment", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsContracts.put("createdAt", new TableInfo.Column("createdAt", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysContracts = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesContracts = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoContracts = new TableInfo("contracts", _columnsContracts, _foreignKeysContracts, _indicesContracts);
        final TableInfo _existingContracts = TableInfo.read(db, "contracts");
        if (!_infoContracts.equals(_existingContracts)) {
          return new RoomOpenHelper.ValidationResult(false, "contracts(com.example.careplus.data.local.ContractEntity).\n"
                  + " Expected:\n" + _infoContracts + "\n"
                  + " Found:\n" + _existingContracts);
        }
        return new RoomOpenHelper.ValidationResult(true, null);
      }
    }, "88361f90fecb5c188ebb377e0164c6db", "146004cb09c7f1e76e1f1ddb4bf9fe39");
    final SupportSQLiteOpenHelper.Configuration _sqliteConfig = SupportSQLiteOpenHelper.Configuration.builder(config.context).name(config.name).callback(_openCallback).build();
    final SupportSQLiteOpenHelper _helper = config.sqliteOpenHelperFactory.create(_sqliteConfig);
    return _helper;
  }

  @Override
  @NonNull
  protected InvalidationTracker createInvalidationTracker() {
    final HashMap<String, String> _shadowTablesMap = new HashMap<String, String>(0);
    final HashMap<String, Set<String>> _viewTables = new HashMap<String, Set<String>>(0);
    return new InvalidationTracker(this, _shadowTablesMap, _viewTables, "care_requests","caregivers","care_bids","chat_messages","contracts");
  }

  @Override
  public void clearAllTables() {
    super.assertNotMainThread();
    final SupportSQLiteDatabase _db = super.getOpenHelper().getWritableDatabase();
    try {
      super.beginTransaction();
      _db.execSQL("DELETE FROM `care_requests`");
      _db.execSQL("DELETE FROM `caregivers`");
      _db.execSQL("DELETE FROM `care_bids`");
      _db.execSQL("DELETE FROM `chat_messages`");
      _db.execSQL("DELETE FROM `contracts`");
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
    _typeConvertersMap.put(CareDao.class, CareDao_Impl.getRequiredConverters());
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
  public CareDao careDao() {
    if (_careDao != null) {
      return _careDao;
    } else {
      synchronized(this) {
        if(_careDao == null) {
          _careDao = new CareDao_Impl(this);
        }
        return _careDao;
      }
    }
  }
}
