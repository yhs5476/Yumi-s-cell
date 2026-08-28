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
import androidx.room.SharedSQLiteStatement;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.example.carematch.data.model.CareRequest;
import com.example.carematch.data.model.RequestStatus;
import java.lang.Class;
import java.lang.Exception;
import java.lang.IllegalArgumentException;
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
public final class CareRequestDao_Impl implements CareRequestDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<CareRequest> __insertionAdapterOfCareRequest;

  private final EntityDeletionOrUpdateAdapter<CareRequest> __updateAdapterOfCareRequest;

  private final SharedSQLiteStatement __preparedStmtOfUpdateRequestStatus;

  public CareRequestDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfCareRequest = new EntityInsertionAdapter<CareRequest>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `care_requests` (`requestId`,`guardianId`,`guardianName`,`guardianPhone`,`caregiverId`,`caregiverName`,`caregiverAvatar`,`caregiverPhone`,`patientGender`,`patientAgeGroup`,`patientDiagnosis`,`careLevel`,`locationType`,`locationAddress`,`careType`,`startDate`,`endDate`,`durationDays`,`offeredDailyPay`,`totalEstimatedPay`,`specialNotes`,`status`,`rejectionReason`,`createdAt`,`updatedAt`) VALUES (nullif(?, 0),?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final CareRequest entity) {
        statement.bindLong(1, entity.getRequestId());
        statement.bindString(2, entity.getGuardianId());
        statement.bindString(3, entity.getGuardianName());
        statement.bindString(4, entity.getGuardianPhone());
        statement.bindLong(5, entity.getCaregiverId());
        statement.bindString(6, entity.getCaregiverName());
        statement.bindString(7, entity.getCaregiverAvatar());
        statement.bindString(8, entity.getCaregiverPhone());
        statement.bindString(9, entity.getPatientGender());
        statement.bindString(10, entity.getPatientAgeGroup());
        statement.bindString(11, entity.getPatientDiagnosis());
        statement.bindString(12, entity.getCareLevel());
        statement.bindString(13, entity.getLocationType());
        statement.bindString(14, entity.getLocationAddress());
        statement.bindString(15, entity.getCareType());
        statement.bindString(16, entity.getStartDate());
        statement.bindString(17, entity.getEndDate());
        statement.bindLong(18, entity.getDurationDays());
        statement.bindLong(19, entity.getOfferedDailyPay());
        statement.bindLong(20, entity.getTotalEstimatedPay());
        statement.bindString(21, entity.getSpecialNotes());
        statement.bindString(22, __RequestStatus_enumToString(entity.getStatus()));
        if (entity.getRejectionReason() == null) {
          statement.bindNull(23);
        } else {
          statement.bindString(23, entity.getRejectionReason());
        }
        statement.bindLong(24, entity.getCreatedAt());
        statement.bindLong(25, entity.getUpdatedAt());
      }
    };
    this.__updateAdapterOfCareRequest = new EntityDeletionOrUpdateAdapter<CareRequest>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "UPDATE OR ABORT `care_requests` SET `requestId` = ?,`guardianId` = ?,`guardianName` = ?,`guardianPhone` = ?,`caregiverId` = ?,`caregiverName` = ?,`caregiverAvatar` = ?,`caregiverPhone` = ?,`patientGender` = ?,`patientAgeGroup` = ?,`patientDiagnosis` = ?,`careLevel` = ?,`locationType` = ?,`locationAddress` = ?,`careType` = ?,`startDate` = ?,`endDate` = ?,`durationDays` = ?,`offeredDailyPay` = ?,`totalEstimatedPay` = ?,`specialNotes` = ?,`status` = ?,`rejectionReason` = ?,`createdAt` = ?,`updatedAt` = ? WHERE `requestId` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final CareRequest entity) {
        statement.bindLong(1, entity.getRequestId());
        statement.bindString(2, entity.getGuardianId());
        statement.bindString(3, entity.getGuardianName());
        statement.bindString(4, entity.getGuardianPhone());
        statement.bindLong(5, entity.getCaregiverId());
        statement.bindString(6, entity.getCaregiverName());
        statement.bindString(7, entity.getCaregiverAvatar());
        statement.bindString(8, entity.getCaregiverPhone());
        statement.bindString(9, entity.getPatientGender());
        statement.bindString(10, entity.getPatientAgeGroup());
        statement.bindString(11, entity.getPatientDiagnosis());
        statement.bindString(12, entity.getCareLevel());
        statement.bindString(13, entity.getLocationType());
        statement.bindString(14, entity.getLocationAddress());
        statement.bindString(15, entity.getCareType());
        statement.bindString(16, entity.getStartDate());
        statement.bindString(17, entity.getEndDate());
        statement.bindLong(18, entity.getDurationDays());
        statement.bindLong(19, entity.getOfferedDailyPay());
        statement.bindLong(20, entity.getTotalEstimatedPay());
        statement.bindString(21, entity.getSpecialNotes());
        statement.bindString(22, __RequestStatus_enumToString(entity.getStatus()));
        if (entity.getRejectionReason() == null) {
          statement.bindNull(23);
        } else {
          statement.bindString(23, entity.getRejectionReason());
        }
        statement.bindLong(24, entity.getCreatedAt());
        statement.bindLong(25, entity.getUpdatedAt());
        statement.bindLong(26, entity.getRequestId());
      }
    };
    this.__preparedStmtOfUpdateRequestStatus = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "UPDATE care_requests SET status = ?, rejectionReason = ?, updatedAt = ? WHERE requestId = ?";
        return _query;
      }
    };
  }

  @Override
  public Object insertRequest(final CareRequest request,
      final Continuation<? super Long> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Long>() {
      @Override
      @NonNull
      public Long call() throws Exception {
        __db.beginTransaction();
        try {
          final Long _result = __insertionAdapterOfCareRequest.insertAndReturnId(request);
          __db.setTransactionSuccessful();
          return _result;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object insertRequests(final List<CareRequest> requests,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfCareRequest.insert(requests);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object updateRequest(final CareRequest request,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __updateAdapterOfCareRequest.handle(request);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object updateRequestStatus(final long requestId, final RequestStatus status,
      final String reason, final long timestamp, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfUpdateRequestStatus.acquire();
        int _argIndex = 1;
        _stmt.bindString(_argIndex, __RequestStatus_enumToString(status));
        _argIndex = 2;
        if (reason == null) {
          _stmt.bindNull(_argIndex);
        } else {
          _stmt.bindString(_argIndex, reason);
        }
        _argIndex = 3;
        _stmt.bindLong(_argIndex, timestamp);
        _argIndex = 4;
        _stmt.bindLong(_argIndex, requestId);
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
          __preparedStmtOfUpdateRequestStatus.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Flow<List<CareRequest>> getAllRequests() {
    final String _sql = "SELECT * FROM care_requests ORDER BY createdAt DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"care_requests"}, new Callable<List<CareRequest>>() {
      @Override
      @NonNull
      public List<CareRequest> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfRequestId = CursorUtil.getColumnIndexOrThrow(_cursor, "requestId");
          final int _cursorIndexOfGuardianId = CursorUtil.getColumnIndexOrThrow(_cursor, "guardianId");
          final int _cursorIndexOfGuardianName = CursorUtil.getColumnIndexOrThrow(_cursor, "guardianName");
          final int _cursorIndexOfGuardianPhone = CursorUtil.getColumnIndexOrThrow(_cursor, "guardianPhone");
          final int _cursorIndexOfCaregiverId = CursorUtil.getColumnIndexOrThrow(_cursor, "caregiverId");
          final int _cursorIndexOfCaregiverName = CursorUtil.getColumnIndexOrThrow(_cursor, "caregiverName");
          final int _cursorIndexOfCaregiverAvatar = CursorUtil.getColumnIndexOrThrow(_cursor, "caregiverAvatar");
          final int _cursorIndexOfCaregiverPhone = CursorUtil.getColumnIndexOrThrow(_cursor, "caregiverPhone");
          final int _cursorIndexOfPatientGender = CursorUtil.getColumnIndexOrThrow(_cursor, "patientGender");
          final int _cursorIndexOfPatientAgeGroup = CursorUtil.getColumnIndexOrThrow(_cursor, "patientAgeGroup");
          final int _cursorIndexOfPatientDiagnosis = CursorUtil.getColumnIndexOrThrow(_cursor, "patientDiagnosis");
          final int _cursorIndexOfCareLevel = CursorUtil.getColumnIndexOrThrow(_cursor, "careLevel");
          final int _cursorIndexOfLocationType = CursorUtil.getColumnIndexOrThrow(_cursor, "locationType");
          final int _cursorIndexOfLocationAddress = CursorUtil.getColumnIndexOrThrow(_cursor, "locationAddress");
          final int _cursorIndexOfCareType = CursorUtil.getColumnIndexOrThrow(_cursor, "careType");
          final int _cursorIndexOfStartDate = CursorUtil.getColumnIndexOrThrow(_cursor, "startDate");
          final int _cursorIndexOfEndDate = CursorUtil.getColumnIndexOrThrow(_cursor, "endDate");
          final int _cursorIndexOfDurationDays = CursorUtil.getColumnIndexOrThrow(_cursor, "durationDays");
          final int _cursorIndexOfOfferedDailyPay = CursorUtil.getColumnIndexOrThrow(_cursor, "offeredDailyPay");
          final int _cursorIndexOfTotalEstimatedPay = CursorUtil.getColumnIndexOrThrow(_cursor, "totalEstimatedPay");
          final int _cursorIndexOfSpecialNotes = CursorUtil.getColumnIndexOrThrow(_cursor, "specialNotes");
          final int _cursorIndexOfStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "status");
          final int _cursorIndexOfRejectionReason = CursorUtil.getColumnIndexOrThrow(_cursor, "rejectionReason");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final int _cursorIndexOfUpdatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "updatedAt");
          final List<CareRequest> _result = new ArrayList<CareRequest>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final CareRequest _item;
            final long _tmpRequestId;
            _tmpRequestId = _cursor.getLong(_cursorIndexOfRequestId);
            final String _tmpGuardianId;
            _tmpGuardianId = _cursor.getString(_cursorIndexOfGuardianId);
            final String _tmpGuardianName;
            _tmpGuardianName = _cursor.getString(_cursorIndexOfGuardianName);
            final String _tmpGuardianPhone;
            _tmpGuardianPhone = _cursor.getString(_cursorIndexOfGuardianPhone);
            final long _tmpCaregiverId;
            _tmpCaregiverId = _cursor.getLong(_cursorIndexOfCaregiverId);
            final String _tmpCaregiverName;
            _tmpCaregiverName = _cursor.getString(_cursorIndexOfCaregiverName);
            final String _tmpCaregiverAvatar;
            _tmpCaregiverAvatar = _cursor.getString(_cursorIndexOfCaregiverAvatar);
            final String _tmpCaregiverPhone;
            _tmpCaregiverPhone = _cursor.getString(_cursorIndexOfCaregiverPhone);
            final String _tmpPatientGender;
            _tmpPatientGender = _cursor.getString(_cursorIndexOfPatientGender);
            final String _tmpPatientAgeGroup;
            _tmpPatientAgeGroup = _cursor.getString(_cursorIndexOfPatientAgeGroup);
            final String _tmpPatientDiagnosis;
            _tmpPatientDiagnosis = _cursor.getString(_cursorIndexOfPatientDiagnosis);
            final String _tmpCareLevel;
            _tmpCareLevel = _cursor.getString(_cursorIndexOfCareLevel);
            final String _tmpLocationType;
            _tmpLocationType = _cursor.getString(_cursorIndexOfLocationType);
            final String _tmpLocationAddress;
            _tmpLocationAddress = _cursor.getString(_cursorIndexOfLocationAddress);
            final String _tmpCareType;
            _tmpCareType = _cursor.getString(_cursorIndexOfCareType);
            final String _tmpStartDate;
            _tmpStartDate = _cursor.getString(_cursorIndexOfStartDate);
            final String _tmpEndDate;
            _tmpEndDate = _cursor.getString(_cursorIndexOfEndDate);
            final int _tmpDurationDays;
            _tmpDurationDays = _cursor.getInt(_cursorIndexOfDurationDays);
            final int _tmpOfferedDailyPay;
            _tmpOfferedDailyPay = _cursor.getInt(_cursorIndexOfOfferedDailyPay);
            final long _tmpTotalEstimatedPay;
            _tmpTotalEstimatedPay = _cursor.getLong(_cursorIndexOfTotalEstimatedPay);
            final String _tmpSpecialNotes;
            _tmpSpecialNotes = _cursor.getString(_cursorIndexOfSpecialNotes);
            final RequestStatus _tmpStatus;
            _tmpStatus = __RequestStatus_stringToEnum(_cursor.getString(_cursorIndexOfStatus));
            final String _tmpRejectionReason;
            if (_cursor.isNull(_cursorIndexOfRejectionReason)) {
              _tmpRejectionReason = null;
            } else {
              _tmpRejectionReason = _cursor.getString(_cursorIndexOfRejectionReason);
            }
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            final long _tmpUpdatedAt;
            _tmpUpdatedAt = _cursor.getLong(_cursorIndexOfUpdatedAt);
            _item = new CareRequest(_tmpRequestId,_tmpGuardianId,_tmpGuardianName,_tmpGuardianPhone,_tmpCaregiverId,_tmpCaregiverName,_tmpCaregiverAvatar,_tmpCaregiverPhone,_tmpPatientGender,_tmpPatientAgeGroup,_tmpPatientDiagnosis,_tmpCareLevel,_tmpLocationType,_tmpLocationAddress,_tmpCareType,_tmpStartDate,_tmpEndDate,_tmpDurationDays,_tmpOfferedDailyPay,_tmpTotalEstimatedPay,_tmpSpecialNotes,_tmpStatus,_tmpRejectionReason,_tmpCreatedAt,_tmpUpdatedAt);
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
  public Flow<List<CareRequest>> getRequestsForCaregiver(final long caregiverId) {
    final String _sql = "SELECT * FROM care_requests WHERE caregiverId = ? ORDER BY createdAt DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, caregiverId);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"care_requests"}, new Callable<List<CareRequest>>() {
      @Override
      @NonNull
      public List<CareRequest> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfRequestId = CursorUtil.getColumnIndexOrThrow(_cursor, "requestId");
          final int _cursorIndexOfGuardianId = CursorUtil.getColumnIndexOrThrow(_cursor, "guardianId");
          final int _cursorIndexOfGuardianName = CursorUtil.getColumnIndexOrThrow(_cursor, "guardianName");
          final int _cursorIndexOfGuardianPhone = CursorUtil.getColumnIndexOrThrow(_cursor, "guardianPhone");
          final int _cursorIndexOfCaregiverId = CursorUtil.getColumnIndexOrThrow(_cursor, "caregiverId");
          final int _cursorIndexOfCaregiverName = CursorUtil.getColumnIndexOrThrow(_cursor, "caregiverName");
          final int _cursorIndexOfCaregiverAvatar = CursorUtil.getColumnIndexOrThrow(_cursor, "caregiverAvatar");
          final int _cursorIndexOfCaregiverPhone = CursorUtil.getColumnIndexOrThrow(_cursor, "caregiverPhone");
          final int _cursorIndexOfPatientGender = CursorUtil.getColumnIndexOrThrow(_cursor, "patientGender");
          final int _cursorIndexOfPatientAgeGroup = CursorUtil.getColumnIndexOrThrow(_cursor, "patientAgeGroup");
          final int _cursorIndexOfPatientDiagnosis = CursorUtil.getColumnIndexOrThrow(_cursor, "patientDiagnosis");
          final int _cursorIndexOfCareLevel = CursorUtil.getColumnIndexOrThrow(_cursor, "careLevel");
          final int _cursorIndexOfLocationType = CursorUtil.getColumnIndexOrThrow(_cursor, "locationType");
          final int _cursorIndexOfLocationAddress = CursorUtil.getColumnIndexOrThrow(_cursor, "locationAddress");
          final int _cursorIndexOfCareType = CursorUtil.getColumnIndexOrThrow(_cursor, "careType");
          final int _cursorIndexOfStartDate = CursorUtil.getColumnIndexOrThrow(_cursor, "startDate");
          final int _cursorIndexOfEndDate = CursorUtil.getColumnIndexOrThrow(_cursor, "endDate");
          final int _cursorIndexOfDurationDays = CursorUtil.getColumnIndexOrThrow(_cursor, "durationDays");
          final int _cursorIndexOfOfferedDailyPay = CursorUtil.getColumnIndexOrThrow(_cursor, "offeredDailyPay");
          final int _cursorIndexOfTotalEstimatedPay = CursorUtil.getColumnIndexOrThrow(_cursor, "totalEstimatedPay");
          final int _cursorIndexOfSpecialNotes = CursorUtil.getColumnIndexOrThrow(_cursor, "specialNotes");
          final int _cursorIndexOfStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "status");
          final int _cursorIndexOfRejectionReason = CursorUtil.getColumnIndexOrThrow(_cursor, "rejectionReason");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final int _cursorIndexOfUpdatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "updatedAt");
          final List<CareRequest> _result = new ArrayList<CareRequest>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final CareRequest _item;
            final long _tmpRequestId;
            _tmpRequestId = _cursor.getLong(_cursorIndexOfRequestId);
            final String _tmpGuardianId;
            _tmpGuardianId = _cursor.getString(_cursorIndexOfGuardianId);
            final String _tmpGuardianName;
            _tmpGuardianName = _cursor.getString(_cursorIndexOfGuardianName);
            final String _tmpGuardianPhone;
            _tmpGuardianPhone = _cursor.getString(_cursorIndexOfGuardianPhone);
            final long _tmpCaregiverId;
            _tmpCaregiverId = _cursor.getLong(_cursorIndexOfCaregiverId);
            final String _tmpCaregiverName;
            _tmpCaregiverName = _cursor.getString(_cursorIndexOfCaregiverName);
            final String _tmpCaregiverAvatar;
            _tmpCaregiverAvatar = _cursor.getString(_cursorIndexOfCaregiverAvatar);
            final String _tmpCaregiverPhone;
            _tmpCaregiverPhone = _cursor.getString(_cursorIndexOfCaregiverPhone);
            final String _tmpPatientGender;
            _tmpPatientGender = _cursor.getString(_cursorIndexOfPatientGender);
            final String _tmpPatientAgeGroup;
            _tmpPatientAgeGroup = _cursor.getString(_cursorIndexOfPatientAgeGroup);
            final String _tmpPatientDiagnosis;
            _tmpPatientDiagnosis = _cursor.getString(_cursorIndexOfPatientDiagnosis);
            final String _tmpCareLevel;
            _tmpCareLevel = _cursor.getString(_cursorIndexOfCareLevel);
            final String _tmpLocationType;
            _tmpLocationType = _cursor.getString(_cursorIndexOfLocationType);
            final String _tmpLocationAddress;
            _tmpLocationAddress = _cursor.getString(_cursorIndexOfLocationAddress);
            final String _tmpCareType;
            _tmpCareType = _cursor.getString(_cursorIndexOfCareType);
            final String _tmpStartDate;
            _tmpStartDate = _cursor.getString(_cursorIndexOfStartDate);
            final String _tmpEndDate;
            _tmpEndDate = _cursor.getString(_cursorIndexOfEndDate);
            final int _tmpDurationDays;
            _tmpDurationDays = _cursor.getInt(_cursorIndexOfDurationDays);
            final int _tmpOfferedDailyPay;
            _tmpOfferedDailyPay = _cursor.getInt(_cursorIndexOfOfferedDailyPay);
            final long _tmpTotalEstimatedPay;
            _tmpTotalEstimatedPay = _cursor.getLong(_cursorIndexOfTotalEstimatedPay);
            final String _tmpSpecialNotes;
            _tmpSpecialNotes = _cursor.getString(_cursorIndexOfSpecialNotes);
            final RequestStatus _tmpStatus;
            _tmpStatus = __RequestStatus_stringToEnum(_cursor.getString(_cursorIndexOfStatus));
            final String _tmpRejectionReason;
            if (_cursor.isNull(_cursorIndexOfRejectionReason)) {
              _tmpRejectionReason = null;
            } else {
              _tmpRejectionReason = _cursor.getString(_cursorIndexOfRejectionReason);
            }
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            final long _tmpUpdatedAt;
            _tmpUpdatedAt = _cursor.getLong(_cursorIndexOfUpdatedAt);
            _item = new CareRequest(_tmpRequestId,_tmpGuardianId,_tmpGuardianName,_tmpGuardianPhone,_tmpCaregiverId,_tmpCaregiverName,_tmpCaregiverAvatar,_tmpCaregiverPhone,_tmpPatientGender,_tmpPatientAgeGroup,_tmpPatientDiagnosis,_tmpCareLevel,_tmpLocationType,_tmpLocationAddress,_tmpCareType,_tmpStartDate,_tmpEndDate,_tmpDurationDays,_tmpOfferedDailyPay,_tmpTotalEstimatedPay,_tmpSpecialNotes,_tmpStatus,_tmpRejectionReason,_tmpCreatedAt,_tmpUpdatedAt);
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
  public Flow<List<CareRequest>> getRequestsForGuardian(final String guardianId) {
    final String _sql = "SELECT * FROM care_requests WHERE guardianId = ? ORDER BY createdAt DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindString(_argIndex, guardianId);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"care_requests"}, new Callable<List<CareRequest>>() {
      @Override
      @NonNull
      public List<CareRequest> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfRequestId = CursorUtil.getColumnIndexOrThrow(_cursor, "requestId");
          final int _cursorIndexOfGuardianId = CursorUtil.getColumnIndexOrThrow(_cursor, "guardianId");
          final int _cursorIndexOfGuardianName = CursorUtil.getColumnIndexOrThrow(_cursor, "guardianName");
          final int _cursorIndexOfGuardianPhone = CursorUtil.getColumnIndexOrThrow(_cursor, "guardianPhone");
          final int _cursorIndexOfCaregiverId = CursorUtil.getColumnIndexOrThrow(_cursor, "caregiverId");
          final int _cursorIndexOfCaregiverName = CursorUtil.getColumnIndexOrThrow(_cursor, "caregiverName");
          final int _cursorIndexOfCaregiverAvatar = CursorUtil.getColumnIndexOrThrow(_cursor, "caregiverAvatar");
          final int _cursorIndexOfCaregiverPhone = CursorUtil.getColumnIndexOrThrow(_cursor, "caregiverPhone");
          final int _cursorIndexOfPatientGender = CursorUtil.getColumnIndexOrThrow(_cursor, "patientGender");
          final int _cursorIndexOfPatientAgeGroup = CursorUtil.getColumnIndexOrThrow(_cursor, "patientAgeGroup");
          final int _cursorIndexOfPatientDiagnosis = CursorUtil.getColumnIndexOrThrow(_cursor, "patientDiagnosis");
          final int _cursorIndexOfCareLevel = CursorUtil.getColumnIndexOrThrow(_cursor, "careLevel");
          final int _cursorIndexOfLocationType = CursorUtil.getColumnIndexOrThrow(_cursor, "locationType");
          final int _cursorIndexOfLocationAddress = CursorUtil.getColumnIndexOrThrow(_cursor, "locationAddress");
          final int _cursorIndexOfCareType = CursorUtil.getColumnIndexOrThrow(_cursor, "careType");
          final int _cursorIndexOfStartDate = CursorUtil.getColumnIndexOrThrow(_cursor, "startDate");
          final int _cursorIndexOfEndDate = CursorUtil.getColumnIndexOrThrow(_cursor, "endDate");
          final int _cursorIndexOfDurationDays = CursorUtil.getColumnIndexOrThrow(_cursor, "durationDays");
          final int _cursorIndexOfOfferedDailyPay = CursorUtil.getColumnIndexOrThrow(_cursor, "offeredDailyPay");
          final int _cursorIndexOfTotalEstimatedPay = CursorUtil.getColumnIndexOrThrow(_cursor, "totalEstimatedPay");
          final int _cursorIndexOfSpecialNotes = CursorUtil.getColumnIndexOrThrow(_cursor, "specialNotes");
          final int _cursorIndexOfStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "status");
          final int _cursorIndexOfRejectionReason = CursorUtil.getColumnIndexOrThrow(_cursor, "rejectionReason");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final int _cursorIndexOfUpdatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "updatedAt");
          final List<CareRequest> _result = new ArrayList<CareRequest>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final CareRequest _item;
            final long _tmpRequestId;
            _tmpRequestId = _cursor.getLong(_cursorIndexOfRequestId);
            final String _tmpGuardianId;
            _tmpGuardianId = _cursor.getString(_cursorIndexOfGuardianId);
            final String _tmpGuardianName;
            _tmpGuardianName = _cursor.getString(_cursorIndexOfGuardianName);
            final String _tmpGuardianPhone;
            _tmpGuardianPhone = _cursor.getString(_cursorIndexOfGuardianPhone);
            final long _tmpCaregiverId;
            _tmpCaregiverId = _cursor.getLong(_cursorIndexOfCaregiverId);
            final String _tmpCaregiverName;
            _tmpCaregiverName = _cursor.getString(_cursorIndexOfCaregiverName);
            final String _tmpCaregiverAvatar;
            _tmpCaregiverAvatar = _cursor.getString(_cursorIndexOfCaregiverAvatar);
            final String _tmpCaregiverPhone;
            _tmpCaregiverPhone = _cursor.getString(_cursorIndexOfCaregiverPhone);
            final String _tmpPatientGender;
            _tmpPatientGender = _cursor.getString(_cursorIndexOfPatientGender);
            final String _tmpPatientAgeGroup;
            _tmpPatientAgeGroup = _cursor.getString(_cursorIndexOfPatientAgeGroup);
            final String _tmpPatientDiagnosis;
            _tmpPatientDiagnosis = _cursor.getString(_cursorIndexOfPatientDiagnosis);
            final String _tmpCareLevel;
            _tmpCareLevel = _cursor.getString(_cursorIndexOfCareLevel);
            final String _tmpLocationType;
            _tmpLocationType = _cursor.getString(_cursorIndexOfLocationType);
            final String _tmpLocationAddress;
            _tmpLocationAddress = _cursor.getString(_cursorIndexOfLocationAddress);
            final String _tmpCareType;
            _tmpCareType = _cursor.getString(_cursorIndexOfCareType);
            final String _tmpStartDate;
            _tmpStartDate = _cursor.getString(_cursorIndexOfStartDate);
            final String _tmpEndDate;
            _tmpEndDate = _cursor.getString(_cursorIndexOfEndDate);
            final int _tmpDurationDays;
            _tmpDurationDays = _cursor.getInt(_cursorIndexOfDurationDays);
            final int _tmpOfferedDailyPay;
            _tmpOfferedDailyPay = _cursor.getInt(_cursorIndexOfOfferedDailyPay);
            final long _tmpTotalEstimatedPay;
            _tmpTotalEstimatedPay = _cursor.getLong(_cursorIndexOfTotalEstimatedPay);
            final String _tmpSpecialNotes;
            _tmpSpecialNotes = _cursor.getString(_cursorIndexOfSpecialNotes);
            final RequestStatus _tmpStatus;
            _tmpStatus = __RequestStatus_stringToEnum(_cursor.getString(_cursorIndexOfStatus));
            final String _tmpRejectionReason;
            if (_cursor.isNull(_cursorIndexOfRejectionReason)) {
              _tmpRejectionReason = null;
            } else {
              _tmpRejectionReason = _cursor.getString(_cursorIndexOfRejectionReason);
            }
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            final long _tmpUpdatedAt;
            _tmpUpdatedAt = _cursor.getLong(_cursorIndexOfUpdatedAt);
            _item = new CareRequest(_tmpRequestId,_tmpGuardianId,_tmpGuardianName,_tmpGuardianPhone,_tmpCaregiverId,_tmpCaregiverName,_tmpCaregiverAvatar,_tmpCaregiverPhone,_tmpPatientGender,_tmpPatientAgeGroup,_tmpPatientDiagnosis,_tmpCareLevel,_tmpLocationType,_tmpLocationAddress,_tmpCareType,_tmpStartDate,_tmpEndDate,_tmpDurationDays,_tmpOfferedDailyPay,_tmpTotalEstimatedPay,_tmpSpecialNotes,_tmpStatus,_tmpRejectionReason,_tmpCreatedAt,_tmpUpdatedAt);
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
  public Flow<CareRequest> getRequestById(final long requestId) {
    final String _sql = "SELECT * FROM care_requests WHERE requestId = ? LIMIT 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, requestId);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"care_requests"}, new Callable<CareRequest>() {
      @Override
      @Nullable
      public CareRequest call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfRequestId = CursorUtil.getColumnIndexOrThrow(_cursor, "requestId");
          final int _cursorIndexOfGuardianId = CursorUtil.getColumnIndexOrThrow(_cursor, "guardianId");
          final int _cursorIndexOfGuardianName = CursorUtil.getColumnIndexOrThrow(_cursor, "guardianName");
          final int _cursorIndexOfGuardianPhone = CursorUtil.getColumnIndexOrThrow(_cursor, "guardianPhone");
          final int _cursorIndexOfCaregiverId = CursorUtil.getColumnIndexOrThrow(_cursor, "caregiverId");
          final int _cursorIndexOfCaregiverName = CursorUtil.getColumnIndexOrThrow(_cursor, "caregiverName");
          final int _cursorIndexOfCaregiverAvatar = CursorUtil.getColumnIndexOrThrow(_cursor, "caregiverAvatar");
          final int _cursorIndexOfCaregiverPhone = CursorUtil.getColumnIndexOrThrow(_cursor, "caregiverPhone");
          final int _cursorIndexOfPatientGender = CursorUtil.getColumnIndexOrThrow(_cursor, "patientGender");
          final int _cursorIndexOfPatientAgeGroup = CursorUtil.getColumnIndexOrThrow(_cursor, "patientAgeGroup");
          final int _cursorIndexOfPatientDiagnosis = CursorUtil.getColumnIndexOrThrow(_cursor, "patientDiagnosis");
          final int _cursorIndexOfCareLevel = CursorUtil.getColumnIndexOrThrow(_cursor, "careLevel");
          final int _cursorIndexOfLocationType = CursorUtil.getColumnIndexOrThrow(_cursor, "locationType");
          final int _cursorIndexOfLocationAddress = CursorUtil.getColumnIndexOrThrow(_cursor, "locationAddress");
          final int _cursorIndexOfCareType = CursorUtil.getColumnIndexOrThrow(_cursor, "careType");
          final int _cursorIndexOfStartDate = CursorUtil.getColumnIndexOrThrow(_cursor, "startDate");
          final int _cursorIndexOfEndDate = CursorUtil.getColumnIndexOrThrow(_cursor, "endDate");
          final int _cursorIndexOfDurationDays = CursorUtil.getColumnIndexOrThrow(_cursor, "durationDays");
          final int _cursorIndexOfOfferedDailyPay = CursorUtil.getColumnIndexOrThrow(_cursor, "offeredDailyPay");
          final int _cursorIndexOfTotalEstimatedPay = CursorUtil.getColumnIndexOrThrow(_cursor, "totalEstimatedPay");
          final int _cursorIndexOfSpecialNotes = CursorUtil.getColumnIndexOrThrow(_cursor, "specialNotes");
          final int _cursorIndexOfStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "status");
          final int _cursorIndexOfRejectionReason = CursorUtil.getColumnIndexOrThrow(_cursor, "rejectionReason");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final int _cursorIndexOfUpdatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "updatedAt");
          final CareRequest _result;
          if (_cursor.moveToFirst()) {
            final long _tmpRequestId;
            _tmpRequestId = _cursor.getLong(_cursorIndexOfRequestId);
            final String _tmpGuardianId;
            _tmpGuardianId = _cursor.getString(_cursorIndexOfGuardianId);
            final String _tmpGuardianName;
            _tmpGuardianName = _cursor.getString(_cursorIndexOfGuardianName);
            final String _tmpGuardianPhone;
            _tmpGuardianPhone = _cursor.getString(_cursorIndexOfGuardianPhone);
            final long _tmpCaregiverId;
            _tmpCaregiverId = _cursor.getLong(_cursorIndexOfCaregiverId);
            final String _tmpCaregiverName;
            _tmpCaregiverName = _cursor.getString(_cursorIndexOfCaregiverName);
            final String _tmpCaregiverAvatar;
            _tmpCaregiverAvatar = _cursor.getString(_cursorIndexOfCaregiverAvatar);
            final String _tmpCaregiverPhone;
            _tmpCaregiverPhone = _cursor.getString(_cursorIndexOfCaregiverPhone);
            final String _tmpPatientGender;
            _tmpPatientGender = _cursor.getString(_cursorIndexOfPatientGender);
            final String _tmpPatientAgeGroup;
            _tmpPatientAgeGroup = _cursor.getString(_cursorIndexOfPatientAgeGroup);
            final String _tmpPatientDiagnosis;
            _tmpPatientDiagnosis = _cursor.getString(_cursorIndexOfPatientDiagnosis);
            final String _tmpCareLevel;
            _tmpCareLevel = _cursor.getString(_cursorIndexOfCareLevel);
            final String _tmpLocationType;
            _tmpLocationType = _cursor.getString(_cursorIndexOfLocationType);
            final String _tmpLocationAddress;
            _tmpLocationAddress = _cursor.getString(_cursorIndexOfLocationAddress);
            final String _tmpCareType;
            _tmpCareType = _cursor.getString(_cursorIndexOfCareType);
            final String _tmpStartDate;
            _tmpStartDate = _cursor.getString(_cursorIndexOfStartDate);
            final String _tmpEndDate;
            _tmpEndDate = _cursor.getString(_cursorIndexOfEndDate);
            final int _tmpDurationDays;
            _tmpDurationDays = _cursor.getInt(_cursorIndexOfDurationDays);
            final int _tmpOfferedDailyPay;
            _tmpOfferedDailyPay = _cursor.getInt(_cursorIndexOfOfferedDailyPay);
            final long _tmpTotalEstimatedPay;
            _tmpTotalEstimatedPay = _cursor.getLong(_cursorIndexOfTotalEstimatedPay);
            final String _tmpSpecialNotes;
            _tmpSpecialNotes = _cursor.getString(_cursorIndexOfSpecialNotes);
            final RequestStatus _tmpStatus;
            _tmpStatus = __RequestStatus_stringToEnum(_cursor.getString(_cursorIndexOfStatus));
            final String _tmpRejectionReason;
            if (_cursor.isNull(_cursorIndexOfRejectionReason)) {
              _tmpRejectionReason = null;
            } else {
              _tmpRejectionReason = _cursor.getString(_cursorIndexOfRejectionReason);
            }
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            final long _tmpUpdatedAt;
            _tmpUpdatedAt = _cursor.getLong(_cursorIndexOfUpdatedAt);
            _result = new CareRequest(_tmpRequestId,_tmpGuardianId,_tmpGuardianName,_tmpGuardianPhone,_tmpCaregiverId,_tmpCaregiverName,_tmpCaregiverAvatar,_tmpCaregiverPhone,_tmpPatientGender,_tmpPatientAgeGroup,_tmpPatientDiagnosis,_tmpCareLevel,_tmpLocationType,_tmpLocationAddress,_tmpCareType,_tmpStartDate,_tmpEndDate,_tmpDurationDays,_tmpOfferedDailyPay,_tmpTotalEstimatedPay,_tmpSpecialNotes,_tmpStatus,_tmpRejectionReason,_tmpCreatedAt,_tmpUpdatedAt);
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
    final String _sql = "SELECT COUNT(*) FROM care_requests";
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

  private String __RequestStatus_enumToString(@NonNull final RequestStatus _value) {
    switch (_value) {
      case PENDING: return "PENDING";
      case ACCEPTED: return "ACCEPTED";
      case REJECTED: return "REJECTED";
      case COMPLETED: return "COMPLETED";
      default: throw new IllegalArgumentException("Can't convert enum to string, unknown enum value: " + _value);
    }
  }

  private RequestStatus __RequestStatus_stringToEnum(@NonNull final String _value) {
    switch (_value) {
      case "PENDING": return RequestStatus.PENDING;
      case "ACCEPTED": return RequestStatus.ACCEPTED;
      case "REJECTED": return RequestStatus.REJECTED;
      case "COMPLETED": return RequestStatus.COMPLETED;
      default: throw new IllegalArgumentException("Can't convert value to enum, unknown value: " + _value);
    }
  }
}
