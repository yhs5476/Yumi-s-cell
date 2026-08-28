package com.example.carematch.data.local;

import android.database.Cursor;
import android.os.CancellationSignal;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.CoroutinesRoom;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.example.carematch.data.model.CaregiverProfile;
import java.lang.Class;
import java.lang.Exception;
import java.lang.Integer;
import java.lang.Long;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import javax.annotation.processing.Generated;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlinx.coroutines.flow.Flow;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class CaregiverDao_Impl implements CaregiverDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<CaregiverProfile> __insertionAdapterOfCaregiverProfile;

  private final Converters __converters = new Converters();

  private final EntityDeletionOrUpdateAdapter<CaregiverProfile> __updateAdapterOfCaregiverProfile;

  public CaregiverDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfCaregiverProfile = new EntityInsertionAdapter<CaregiverProfile>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `caregiver_profiles` (`profileId`,`userId`,`name`,`avatarUrl`,`gender`,`age`,`rating`,`reviewCount`,`completedCases`,`experienceYears`,`location`,`desiredDailyPay`,`certifications`,`specialties`,`bio`,`detailedIntroduction`,`availableSchedule`,`isActive`,`phone`,`badges`) VALUES (nullif(?, 0),?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final CaregiverProfile entity) {
        statement.bindLong(1, entity.getProfileId());
        statement.bindString(2, entity.getUserId());
        statement.bindString(3, entity.getName());
        statement.bindString(4, entity.getAvatarUrl());
        statement.bindString(5, entity.getGender());
        statement.bindLong(6, entity.getAge());
        statement.bindDouble(7, entity.getRating());
        statement.bindLong(8, entity.getReviewCount());
        statement.bindLong(9, entity.getCompletedCases());
        statement.bindLong(10, entity.getExperienceYears());
        statement.bindString(11, entity.getLocation());
        statement.bindLong(12, entity.getDesiredDailyPay());
        final String _tmp = __converters.fromStringList(entity.getCertifications());
        statement.bindString(13, _tmp);
        final String _tmp_1 = __converters.fromStringList(entity.getSpecialties());
        statement.bindString(14, _tmp_1);
        statement.bindString(15, entity.getBio());
        statement.bindString(16, entity.getDetailedIntroduction());
        statement.bindString(17, entity.getAvailableSchedule());
        final int _tmp_2 = entity.isActive() ? 1 : 0;
        statement.bindLong(18, _tmp_2);
        statement.bindString(19, entity.getPhone());
        final String _tmp_3 = __converters.fromStringList(entity.getBadges());
        statement.bindString(20, _tmp_3);
      }
    };
    this.__updateAdapterOfCaregiverProfile = new EntityDeletionOrUpdateAdapter<CaregiverProfile>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "UPDATE OR ABORT `caregiver_profiles` SET `profileId` = ?,`userId` = ?,`name` = ?,`avatarUrl` = ?,`gender` = ?,`age` = ?,`rating` = ?,`reviewCount` = ?,`completedCases` = ?,`experienceYears` = ?,`location` = ?,`desiredDailyPay` = ?,`certifications` = ?,`specialties` = ?,`bio` = ?,`detailedIntroduction` = ?,`availableSchedule` = ?,`isActive` = ?,`phone` = ?,`badges` = ? WHERE `profileId` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final CaregiverProfile entity) {
        statement.bindLong(1, entity.getProfileId());
        statement.bindString(2, entity.getUserId());
        statement.bindString(3, entity.getName());
        statement.bindString(4, entity.getAvatarUrl());
        statement.bindString(5, entity.getGender());
        statement.bindLong(6, entity.getAge());
        statement.bindDouble(7, entity.getRating());
        statement.bindLong(8, entity.getReviewCount());
        statement.bindLong(9, entity.getCompletedCases());
        statement.bindLong(10, entity.getExperienceYears());
        statement.bindString(11, entity.getLocation());
        statement.bindLong(12, entity.getDesiredDailyPay());
        final String _tmp = __converters.fromStringList(entity.getCertifications());
        statement.bindString(13, _tmp);
        final String _tmp_1 = __converters.fromStringList(entity.getSpecialties());
        statement.bindString(14, _tmp_1);
        statement.bindString(15, entity.getBio());
        statement.bindString(16, entity.getDetailedIntroduction());
        statement.bindString(17, entity.getAvailableSchedule());
        final int _tmp_2 = entity.isActive() ? 1 : 0;
        statement.bindLong(18, _tmp_2);
        statement.bindString(19, entity.getPhone());
        final String _tmp_3 = __converters.fromStringList(entity.getBadges());
        statement.bindString(20, _tmp_3);
        statement.bindLong(21, entity.getProfileId());
      }
    };
  }

  @Override
  public Object insertCaregiver(final CaregiverProfile caregiver,
      final Continuation<? super Long> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Long>() {
      @Override
      @NonNull
      public Long call() throws Exception {
        __db.beginTransaction();
        try {
          final Long _result = __insertionAdapterOfCaregiverProfile.insertAndReturnId(caregiver);
          __db.setTransactionSuccessful();
          return _result;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object insertCaregivers(final List<CaregiverProfile> caregivers,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfCaregiverProfile.insert(caregivers);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object updateCaregiver(final CaregiverProfile caregiver,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __updateAdapterOfCaregiverProfile.handle(caregiver);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Flow<List<CaregiverProfile>> getAllActiveCaregivers() {
    final String _sql = "SELECT * FROM caregiver_profiles WHERE isActive = 1 ORDER BY rating DESC, completedCases DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"caregiver_profiles"}, new Callable<List<CaregiverProfile>>() {
      @Override
      @NonNull
      public List<CaregiverProfile> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfProfileId = CursorUtil.getColumnIndexOrThrow(_cursor, "profileId");
          final int _cursorIndexOfUserId = CursorUtil.getColumnIndexOrThrow(_cursor, "userId");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfAvatarUrl = CursorUtil.getColumnIndexOrThrow(_cursor, "avatarUrl");
          final int _cursorIndexOfGender = CursorUtil.getColumnIndexOrThrow(_cursor, "gender");
          final int _cursorIndexOfAge = CursorUtil.getColumnIndexOrThrow(_cursor, "age");
          final int _cursorIndexOfRating = CursorUtil.getColumnIndexOrThrow(_cursor, "rating");
          final int _cursorIndexOfReviewCount = CursorUtil.getColumnIndexOrThrow(_cursor, "reviewCount");
          final int _cursorIndexOfCompletedCases = CursorUtil.getColumnIndexOrThrow(_cursor, "completedCases");
          final int _cursorIndexOfExperienceYears = CursorUtil.getColumnIndexOrThrow(_cursor, "experienceYears");
          final int _cursorIndexOfLocation = CursorUtil.getColumnIndexOrThrow(_cursor, "location");
          final int _cursorIndexOfDesiredDailyPay = CursorUtil.getColumnIndexOrThrow(_cursor, "desiredDailyPay");
          final int _cursorIndexOfCertifications = CursorUtil.getColumnIndexOrThrow(_cursor, "certifications");
          final int _cursorIndexOfSpecialties = CursorUtil.getColumnIndexOrThrow(_cursor, "specialties");
          final int _cursorIndexOfBio = CursorUtil.getColumnIndexOrThrow(_cursor, "bio");
          final int _cursorIndexOfDetailedIntroduction = CursorUtil.getColumnIndexOrThrow(_cursor, "detailedIntroduction");
          final int _cursorIndexOfAvailableSchedule = CursorUtil.getColumnIndexOrThrow(_cursor, "availableSchedule");
          final int _cursorIndexOfIsActive = CursorUtil.getColumnIndexOrThrow(_cursor, "isActive");
          final int _cursorIndexOfPhone = CursorUtil.getColumnIndexOrThrow(_cursor, "phone");
          final int _cursorIndexOfBadges = CursorUtil.getColumnIndexOrThrow(_cursor, "badges");
          final List<CaregiverProfile> _result = new ArrayList<CaregiverProfile>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final CaregiverProfile _item;
            final long _tmpProfileId;
            _tmpProfileId = _cursor.getLong(_cursorIndexOfProfileId);
            final String _tmpUserId;
            _tmpUserId = _cursor.getString(_cursorIndexOfUserId);
            final String _tmpName;
            _tmpName = _cursor.getString(_cursorIndexOfName);
            final String _tmpAvatarUrl;
            _tmpAvatarUrl = _cursor.getString(_cursorIndexOfAvatarUrl);
            final String _tmpGender;
            _tmpGender = _cursor.getString(_cursorIndexOfGender);
            final int _tmpAge;
            _tmpAge = _cursor.getInt(_cursorIndexOfAge);
            final double _tmpRating;
            _tmpRating = _cursor.getDouble(_cursorIndexOfRating);
            final int _tmpReviewCount;
            _tmpReviewCount = _cursor.getInt(_cursorIndexOfReviewCount);
            final int _tmpCompletedCases;
            _tmpCompletedCases = _cursor.getInt(_cursorIndexOfCompletedCases);
            final int _tmpExperienceYears;
            _tmpExperienceYears = _cursor.getInt(_cursorIndexOfExperienceYears);
            final String _tmpLocation;
            _tmpLocation = _cursor.getString(_cursorIndexOfLocation);
            final int _tmpDesiredDailyPay;
            _tmpDesiredDailyPay = _cursor.getInt(_cursorIndexOfDesiredDailyPay);
            final List<String> _tmpCertifications;
            final String _tmp;
            _tmp = _cursor.getString(_cursorIndexOfCertifications);
            _tmpCertifications = __converters.toStringList(_tmp);
            final List<String> _tmpSpecialties;
            final String _tmp_1;
            _tmp_1 = _cursor.getString(_cursorIndexOfSpecialties);
            _tmpSpecialties = __converters.toStringList(_tmp_1);
            final String _tmpBio;
            _tmpBio = _cursor.getString(_cursorIndexOfBio);
            final String _tmpDetailedIntroduction;
            _tmpDetailedIntroduction = _cursor.getString(_cursorIndexOfDetailedIntroduction);
            final String _tmpAvailableSchedule;
            _tmpAvailableSchedule = _cursor.getString(_cursorIndexOfAvailableSchedule);
            final boolean _tmpIsActive;
            final int _tmp_2;
            _tmp_2 = _cursor.getInt(_cursorIndexOfIsActive);
            _tmpIsActive = _tmp_2 != 0;
            final String _tmpPhone;
            _tmpPhone = _cursor.getString(_cursorIndexOfPhone);
            final List<String> _tmpBadges;
            final String _tmp_3;
            _tmp_3 = _cursor.getString(_cursorIndexOfBadges);
            _tmpBadges = __converters.toStringList(_tmp_3);
            _item = new CaregiverProfile(_tmpProfileId,_tmpUserId,_tmpName,_tmpAvatarUrl,_tmpGender,_tmpAge,_tmpRating,_tmpReviewCount,_tmpCompletedCases,_tmpExperienceYears,_tmpLocation,_tmpDesiredDailyPay,_tmpCertifications,_tmpSpecialties,_tmpBio,_tmpDetailedIntroduction,_tmpAvailableSchedule,_tmpIsActive,_tmpPhone,_tmpBadges);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Flow<List<CaregiverProfile>> getAllCaregivers() {
    final String _sql = "SELECT * FROM caregiver_profiles ORDER BY profileId ASC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"caregiver_profiles"}, new Callable<List<CaregiverProfile>>() {
      @Override
      @NonNull
      public List<CaregiverProfile> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfProfileId = CursorUtil.getColumnIndexOrThrow(_cursor, "profileId");
          final int _cursorIndexOfUserId = CursorUtil.getColumnIndexOrThrow(_cursor, "userId");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfAvatarUrl = CursorUtil.getColumnIndexOrThrow(_cursor, "avatarUrl");
          final int _cursorIndexOfGender = CursorUtil.getColumnIndexOrThrow(_cursor, "gender");
          final int _cursorIndexOfAge = CursorUtil.getColumnIndexOrThrow(_cursor, "age");
          final int _cursorIndexOfRating = CursorUtil.getColumnIndexOrThrow(_cursor, "rating");
          final int _cursorIndexOfReviewCount = CursorUtil.getColumnIndexOrThrow(_cursor, "reviewCount");
          final int _cursorIndexOfCompletedCases = CursorUtil.getColumnIndexOrThrow(_cursor, "completedCases");
          final int _cursorIndexOfExperienceYears = CursorUtil.getColumnIndexOrThrow(_cursor, "experienceYears");
          final int _cursorIndexOfLocation = CursorUtil.getColumnIndexOrThrow(_cursor, "location");
          final int _cursorIndexOfDesiredDailyPay = CursorUtil.getColumnIndexOrThrow(_cursor, "desiredDailyPay");
          final int _cursorIndexOfCertifications = CursorUtil.getColumnIndexOrThrow(_cursor, "certifications");
          final int _cursorIndexOfSpecialties = CursorUtil.getColumnIndexOrThrow(_cursor, "specialties");
          final int _cursorIndexOfBio = CursorUtil.getColumnIndexOrThrow(_cursor, "bio");
          final int _cursorIndexOfDetailedIntroduction = CursorUtil.getColumnIndexOrThrow(_cursor, "detailedIntroduction");
          final int _cursorIndexOfAvailableSchedule = CursorUtil.getColumnIndexOrThrow(_cursor, "availableSchedule");
          final int _cursorIndexOfIsActive = CursorUtil.getColumnIndexOrThrow(_cursor, "isActive");
          final int _cursorIndexOfPhone = CursorUtil.getColumnIndexOrThrow(_cursor, "phone");
          final int _cursorIndexOfBadges = CursorUtil.getColumnIndexOrThrow(_cursor, "badges");
          final List<CaregiverProfile> _result = new ArrayList<CaregiverProfile>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final CaregiverProfile _item;
            final long _tmpProfileId;
            _tmpProfileId = _cursor.getLong(_cursorIndexOfProfileId);
            final String _tmpUserId;
            _tmpUserId = _cursor.getString(_cursorIndexOfUserId);
            final String _tmpName;
            _tmpName = _cursor.getString(_cursorIndexOfName);
            final String _tmpAvatarUrl;
            _tmpAvatarUrl = _cursor.getString(_cursorIndexOfAvatarUrl);
            final String _tmpGender;
            _tmpGender = _cursor.getString(_cursorIndexOfGender);
            final int _tmpAge;
            _tmpAge = _cursor.getInt(_cursorIndexOfAge);
            final double _tmpRating;
            _tmpRating = _cursor.getDouble(_cursorIndexOfRating);
            final int _tmpReviewCount;
            _tmpReviewCount = _cursor.getInt(_cursorIndexOfReviewCount);
            final int _tmpCompletedCases;
            _tmpCompletedCases = _cursor.getInt(_cursorIndexOfCompletedCases);
            final int _tmpExperienceYears;
            _tmpExperienceYears = _cursor.getInt(_cursorIndexOfExperienceYears);
            final String _tmpLocation;
            _tmpLocation = _cursor.getString(_cursorIndexOfLocation);
            final int _tmpDesiredDailyPay;
            _tmpDesiredDailyPay = _cursor.getInt(_cursorIndexOfDesiredDailyPay);
            final List<String> _tmpCertifications;
            final String _tmp;
            _tmp = _cursor.getString(_cursorIndexOfCertifications);
            _tmpCertifications = __converters.toStringList(_tmp);
            final List<String> _tmpSpecialties;
            final String _tmp_1;
            _tmp_1 = _cursor.getString(_cursorIndexOfSpecialties);
            _tmpSpecialties = __converters.toStringList(_tmp_1);
            final String _tmpBio;
            _tmpBio = _cursor.getString(_cursorIndexOfBio);
            final String _tmpDetailedIntroduction;
            _tmpDetailedIntroduction = _cursor.getString(_cursorIndexOfDetailedIntroduction);
            final String _tmpAvailableSchedule;
            _tmpAvailableSchedule = _cursor.getString(_cursorIndexOfAvailableSchedule);
            final boolean _tmpIsActive;
            final int _tmp_2;
            _tmp_2 = _cursor.getInt(_cursorIndexOfIsActive);
            _tmpIsActive = _tmp_2 != 0;
            final String _tmpPhone;
            _tmpPhone = _cursor.getString(_cursorIndexOfPhone);
            final List<String> _tmpBadges;
            final String _tmp_3;
            _tmp_3 = _cursor.getString(_cursorIndexOfBadges);
            _tmpBadges = __converters.toStringList(_tmp_3);
            _item = new CaregiverProfile(_tmpProfileId,_tmpUserId,_tmpName,_tmpAvatarUrl,_tmpGender,_tmpAge,_tmpRating,_tmpReviewCount,_tmpCompletedCases,_tmpExperienceYears,_tmpLocation,_tmpDesiredDailyPay,_tmpCertifications,_tmpSpecialties,_tmpBio,_tmpDetailedIntroduction,_tmpAvailableSchedule,_tmpIsActive,_tmpPhone,_tmpBadges);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Flow<CaregiverProfile> getCaregiverById(final long profileId) {
    final String _sql = "SELECT * FROM caregiver_profiles WHERE profileId = ? LIMIT 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, profileId);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"caregiver_profiles"}, new Callable<CaregiverProfile>() {
      @Override
      @Nullable
      public CaregiverProfile call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfProfileId = CursorUtil.getColumnIndexOrThrow(_cursor, "profileId");
          final int _cursorIndexOfUserId = CursorUtil.getColumnIndexOrThrow(_cursor, "userId");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfAvatarUrl = CursorUtil.getColumnIndexOrThrow(_cursor, "avatarUrl");
          final int _cursorIndexOfGender = CursorUtil.getColumnIndexOrThrow(_cursor, "gender");
          final int _cursorIndexOfAge = CursorUtil.getColumnIndexOrThrow(_cursor, "age");
          final int _cursorIndexOfRating = CursorUtil.getColumnIndexOrThrow(_cursor, "rating");
          final int _cursorIndexOfReviewCount = CursorUtil.getColumnIndexOrThrow(_cursor, "reviewCount");
          final int _cursorIndexOfCompletedCases = CursorUtil.getColumnIndexOrThrow(_cursor, "completedCases");
          final int _cursorIndexOfExperienceYears = CursorUtil.getColumnIndexOrThrow(_cursor, "experienceYears");
          final int _cursorIndexOfLocation = CursorUtil.getColumnIndexOrThrow(_cursor, "location");
          final int _cursorIndexOfDesiredDailyPay = CursorUtil.getColumnIndexOrThrow(_cursor, "desiredDailyPay");
          final int _cursorIndexOfCertifications = CursorUtil.getColumnIndexOrThrow(_cursor, "certifications");
          final int _cursorIndexOfSpecialties = CursorUtil.getColumnIndexOrThrow(_cursor, "specialties");
          final int _cursorIndexOfBio = CursorUtil.getColumnIndexOrThrow(_cursor, "bio");
          final int _cursorIndexOfDetailedIntroduction = CursorUtil.getColumnIndexOrThrow(_cursor, "detailedIntroduction");
          final int _cursorIndexOfAvailableSchedule = CursorUtil.getColumnIndexOrThrow(_cursor, "availableSchedule");
          final int _cursorIndexOfIsActive = CursorUtil.getColumnIndexOrThrow(_cursor, "isActive");
          final int _cursorIndexOfPhone = CursorUtil.getColumnIndexOrThrow(_cursor, "phone");
          final int _cursorIndexOfBadges = CursorUtil.getColumnIndexOrThrow(_cursor, "badges");
          final CaregiverProfile _result;
          if (_cursor.moveToFirst()) {
            final long _tmpProfileId;
            _tmpProfileId = _cursor.getLong(_cursorIndexOfProfileId);
            final String _tmpUserId;
            _tmpUserId = _cursor.getString(_cursorIndexOfUserId);
            final String _tmpName;
            _tmpName = _cursor.getString(_cursorIndexOfName);
            final String _tmpAvatarUrl;
            _tmpAvatarUrl = _cursor.getString(_cursorIndexOfAvatarUrl);
            final String _tmpGender;
            _tmpGender = _cursor.getString(_cursorIndexOfGender);
            final int _tmpAge;
            _tmpAge = _cursor.getInt(_cursorIndexOfAge);
            final double _tmpRating;
            _tmpRating = _cursor.getDouble(_cursorIndexOfRating);
            final int _tmpReviewCount;
            _tmpReviewCount = _cursor.getInt(_cursorIndexOfReviewCount);
            final int _tmpCompletedCases;
            _tmpCompletedCases = _cursor.getInt(_cursorIndexOfCompletedCases);
            final int _tmpExperienceYears;
            _tmpExperienceYears = _cursor.getInt(_cursorIndexOfExperienceYears);
            final String _tmpLocation;
            _tmpLocation = _cursor.getString(_cursorIndexOfLocation);
            final int _tmpDesiredDailyPay;
            _tmpDesiredDailyPay = _cursor.getInt(_cursorIndexOfDesiredDailyPay);
            final List<String> _tmpCertifications;
            final String _tmp;
            _tmp = _cursor.getString(_cursorIndexOfCertifications);
            _tmpCertifications = __converters.toStringList(_tmp);
            final List<String> _tmpSpecialties;
            final String _tmp_1;
            _tmp_1 = _cursor.getString(_cursorIndexOfSpecialties);
            _tmpSpecialties = __converters.toStringList(_tmp_1);
            final String _tmpBio;
            _tmpBio = _cursor.getString(_cursorIndexOfBio);
            final String _tmpDetailedIntroduction;
            _tmpDetailedIntroduction = _cursor.getString(_cursorIndexOfDetailedIntroduction);
            final String _tmpAvailableSchedule;
            _tmpAvailableSchedule = _cursor.getString(_cursorIndexOfAvailableSchedule);
            final boolean _tmpIsActive;
            final int _tmp_2;
            _tmp_2 = _cursor.getInt(_cursorIndexOfIsActive);
            _tmpIsActive = _tmp_2 != 0;
            final String _tmpPhone;
            _tmpPhone = _cursor.getString(_cursorIndexOfPhone);
            final List<String> _tmpBadges;
            final String _tmp_3;
            _tmp_3 = _cursor.getString(_cursorIndexOfBadges);
            _tmpBadges = __converters.toStringList(_tmp_3);
            _result = new CaregiverProfile(_tmpProfileId,_tmpUserId,_tmpName,_tmpAvatarUrl,_tmpGender,_tmpAge,_tmpRating,_tmpReviewCount,_tmpCompletedCases,_tmpExperienceYears,_tmpLocation,_tmpDesiredDailyPay,_tmpCertifications,_tmpSpecialties,_tmpBio,_tmpDetailedIntroduction,_tmpAvailableSchedule,_tmpIsActive,_tmpPhone,_tmpBadges);
          } else {
            _result = null;
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Flow<CaregiverProfile> getCaregiverByUserId(final String userId) {
    final String _sql = "SELECT * FROM caregiver_profiles WHERE userId = ? LIMIT 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindString(_argIndex, userId);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"caregiver_profiles"}, new Callable<CaregiverProfile>() {
      @Override
      @Nullable
      public CaregiverProfile call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfProfileId = CursorUtil.getColumnIndexOrThrow(_cursor, "profileId");
          final int _cursorIndexOfUserId = CursorUtil.getColumnIndexOrThrow(_cursor, "userId");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfAvatarUrl = CursorUtil.getColumnIndexOrThrow(_cursor, "avatarUrl");
          final int _cursorIndexOfGender = CursorUtil.getColumnIndexOrThrow(_cursor, "gender");
          final int _cursorIndexOfAge = CursorUtil.getColumnIndexOrThrow(_cursor, "age");
          final int _cursorIndexOfRating = CursorUtil.getColumnIndexOrThrow(_cursor, "rating");
          final int _cursorIndexOfReviewCount = CursorUtil.getColumnIndexOrThrow(_cursor, "reviewCount");
          final int _cursorIndexOfCompletedCases = CursorUtil.getColumnIndexOrThrow(_cursor, "completedCases");
          final int _cursorIndexOfExperienceYears = CursorUtil.getColumnIndexOrThrow(_cursor, "experienceYears");
          final int _cursorIndexOfLocation = CursorUtil.getColumnIndexOrThrow(_cursor, "location");
          final int _cursorIndexOfDesiredDailyPay = CursorUtil.getColumnIndexOrThrow(_cursor, "desiredDailyPay");
          final int _cursorIndexOfCertifications = CursorUtil.getColumnIndexOrThrow(_cursor, "certifications");
          final int _cursorIndexOfSpecialties = CursorUtil.getColumnIndexOrThrow(_cursor, "specialties");
          final int _cursorIndexOfBio = CursorUtil.getColumnIndexOrThrow(_cursor, "bio");
          final int _cursorIndexOfDetailedIntroduction = CursorUtil.getColumnIndexOrThrow(_cursor, "detailedIntroduction");
          final int _cursorIndexOfAvailableSchedule = CursorUtil.getColumnIndexOrThrow(_cursor, "availableSchedule");
          final int _cursorIndexOfIsActive = CursorUtil.getColumnIndexOrThrow(_cursor, "isActive");
          final int _cursorIndexOfPhone = CursorUtil.getColumnIndexOrThrow(_cursor, "phone");
          final int _cursorIndexOfBadges = CursorUtil.getColumnIndexOrThrow(_cursor, "badges");
          final CaregiverProfile _result;
          if (_cursor.moveToFirst()) {
            final long _tmpProfileId;
            _tmpProfileId = _cursor.getLong(_cursorIndexOfProfileId);
            final String _tmpUserId;
            _tmpUserId = _cursor.getString(_cursorIndexOfUserId);
            final String _tmpName;
            _tmpName = _cursor.getString(_cursorIndexOfName);
            final String _tmpAvatarUrl;
            _tmpAvatarUrl = _cursor.getString(_cursorIndexOfAvatarUrl);
            final String _tmpGender;
            _tmpGender = _cursor.getString(_cursorIndexOfGender);
            final int _tmpAge;
            _tmpAge = _cursor.getInt(_cursorIndexOfAge);
            final double _tmpRating;
            _tmpRating = _cursor.getDouble(_cursorIndexOfRating);
            final int _tmpReviewCount;
            _tmpReviewCount = _cursor.getInt(_cursorIndexOfReviewCount);
            final int _tmpCompletedCases;
            _tmpCompletedCases = _cursor.getInt(_cursorIndexOfCompletedCases);
            final int _tmpExperienceYears;
            _tmpExperienceYears = _cursor.getInt(_cursorIndexOfExperienceYears);
            final String _tmpLocation;
            _tmpLocation = _cursor.getString(_cursorIndexOfLocation);
            final int _tmpDesiredDailyPay;
            _tmpDesiredDailyPay = _cursor.getInt(_cursorIndexOfDesiredDailyPay);
            final List<String> _tmpCertifications;
            final String _tmp;
            _tmp = _cursor.getString(_cursorIndexOfCertifications);
            _tmpCertifications = __converters.toStringList(_tmp);
            final List<String> _tmpSpecialties;
            final String _tmp_1;
            _tmp_1 = _cursor.getString(_cursorIndexOfSpecialties);
            _tmpSpecialties = __converters.toStringList(_tmp_1);
            final String _tmpBio;
            _tmpBio = _cursor.getString(_cursorIndexOfBio);
            final String _tmpDetailedIntroduction;
            _tmpDetailedIntroduction = _cursor.getString(_cursorIndexOfDetailedIntroduction);
            final String _tmpAvailableSchedule;
            _tmpAvailableSchedule = _cursor.getString(_cursorIndexOfAvailableSchedule);
            final boolean _tmpIsActive;
            final int _tmp_2;
            _tmp_2 = _cursor.getInt(_cursorIndexOfIsActive);
            _tmpIsActive = _tmp_2 != 0;
            final String _tmpPhone;
            _tmpPhone = _cursor.getString(_cursorIndexOfPhone);
            final List<String> _tmpBadges;
            final String _tmp_3;
            _tmp_3 = _cursor.getString(_cursorIndexOfBadges);
            _tmpBadges = __converters.toStringList(_tmp_3);
            _result = new CaregiverProfile(_tmpProfileId,_tmpUserId,_tmpName,_tmpAvatarUrl,_tmpGender,_tmpAge,_tmpRating,_tmpReviewCount,_tmpCompletedCases,_tmpExperienceYears,_tmpLocation,_tmpDesiredDailyPay,_tmpCertifications,_tmpSpecialties,_tmpBio,_tmpDetailedIntroduction,_tmpAvailableSchedule,_tmpIsActive,_tmpPhone,_tmpBadges);
          } else {
            _result = null;
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Object getCount(final Continuation<? super Integer> $completion) {
    final String _sql = "SELECT COUNT(*) FROM caregiver_profiles";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<Integer>() {
      @Override
      @NonNull
      public Integer call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final Integer _result;
          if (_cursor.moveToFirst()) {
            final int _tmp;
            _tmp = _cursor.getInt(0);
            _result = _tmp;
          } else {
            _result = 0;
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
