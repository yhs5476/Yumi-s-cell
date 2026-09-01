package com.example.careplus.data.local;

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
import com.example.careplus.data.model.AgeRange;
import com.example.careplus.data.model.BidStatus;
import com.example.careplus.data.model.CareType;
import com.example.careplus.data.model.Consciousness;
import com.example.careplus.data.model.EscrowStatus;
import com.example.careplus.data.model.JourneyStep;
import com.example.careplus.data.model.Mobility;
import com.example.careplus.data.model.PatientGender;
import com.example.careplus.data.model.RequestStatus;
import com.example.careplus.data.model.WeightRange;
import java.lang.Class;
import java.lang.Exception;
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
public final class CareDao_Impl implements CareDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<CareRequestEntity> __insertionAdapterOfCareRequestEntity;

  private final CareConverters __careConverters = new CareConverters();

  private final EntityInsertionAdapter<CaregiverProfileEntity> __insertionAdapterOfCaregiverProfileEntity;

  private final EntityInsertionAdapter<CareBidEntity> __insertionAdapterOfCareBidEntity;

  private final EntityInsertionAdapter<ChatMessageEntity> __insertionAdapterOfChatMessageEntity;

  private final EntityInsertionAdapter<ContractEntity> __insertionAdapterOfContractEntity;

  private final EntityDeletionOrUpdateAdapter<CareRequestEntity> __updateAdapterOfCareRequestEntity;

  private final SharedSQLiteStatement __preparedStmtOfUpdateBidStatus;

  private final SharedSQLiteStatement __preparedStmtOfUpdateEscrowStatus;

  private final SharedSQLiteStatement __preparedStmtOfUpdateJourneyStep;

  private final SharedSQLiteStatement __preparedStmtOfSubmitReview;

  private final SharedSQLiteStatement __preparedStmtOfDeleteContractById;

  private final SharedSQLiteStatement __preparedStmtOfDeleteAllContracts;

  private final SharedSQLiteStatement __preparedStmtOfDeleteAllRequests;

  private final SharedSQLiteStatement __preparedStmtOfDeleteAllBids;

  public CareDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfCareRequestEntity = new EntityInsertionAdapter<CareRequestEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `care_requests` (`id`,`guardianName`,`location`,`hospitalName`,`careType`,`mobility`,`consciousness`,`weightRange`,`gender`,`ageRange`,`specialNeeds`,`startDate`,`endDate`,`totalDays`,`status`,`createdAt`) VALUES (nullif(?, 0),?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final CareRequestEntity entity) {
        statement.bindLong(1, entity.getId());
        statement.bindString(2, entity.getGuardianName());
        statement.bindString(3, entity.getLocation());
        statement.bindString(4, entity.getHospitalName());
        final String _tmp = __careConverters.fromCareType(entity.getCareType());
        statement.bindString(5, _tmp);
        final String _tmp_1 = __careConverters.fromMobility(entity.getMobility());
        statement.bindString(6, _tmp_1);
        final String _tmp_2 = __careConverters.fromConsciousness(entity.getConsciousness());
        statement.bindString(7, _tmp_2);
        final String _tmp_3 = __careConverters.fromWeightRange(entity.getWeightRange());
        statement.bindString(8, _tmp_3);
        final String _tmp_4 = __careConverters.fromPatientGender(entity.getGender());
        statement.bindString(9, _tmp_4);
        final String _tmp_5 = __careConverters.fromAgeRange(entity.getAgeRange());
        statement.bindString(10, _tmp_5);
        final String _tmp_6 = __careConverters.fromStringList(entity.getSpecialNeeds());
        statement.bindString(11, _tmp_6);
        statement.bindString(12, entity.getStartDate());
        statement.bindString(13, entity.getEndDate());
        statement.bindLong(14, entity.getTotalDays());
        final String _tmp_7 = __careConverters.fromRequestStatus(entity.getStatus());
        statement.bindString(15, _tmp_7);
        statement.bindLong(16, entity.getCreatedAt());
      }
    };
    this.__insertionAdapterOfCaregiverProfileEntity = new EntityInsertionAdapter<CaregiverProfileEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `caregivers` (`caregiverId`,`name`,`careerYears`,`rating`,`reviewCount`,`distanceKm`,`travelTimeMinutes`,`certList`,`insuranceYn`,`vaccineYn`,`gender`,`bio`,`brixScore`,`phoneMasked`) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final CaregiverProfileEntity entity) {
        statement.bindString(1, entity.getCaregiverId());
        statement.bindString(2, entity.getName());
        statement.bindLong(3, entity.getCareerYears());
        statement.bindDouble(4, entity.getRating());
        statement.bindLong(5, entity.getReviewCount());
        statement.bindDouble(6, entity.getDistanceKm());
        statement.bindLong(7, entity.getTravelTimeMinutes());
        final String _tmp = __careConverters.fromStringList(entity.getCertList());
        statement.bindString(8, _tmp);
        final int _tmp_1 = entity.getInsuranceYn() ? 1 : 0;
        statement.bindLong(9, _tmp_1);
        final int _tmp_2 = entity.getVaccineYn() ? 1 : 0;
        statement.bindLong(10, _tmp_2);
        final String _tmp_3 = __careConverters.fromPatientGender(entity.getGender());
        statement.bindString(11, _tmp_3);
        statement.bindString(12, entity.getBio());
        statement.bindDouble(13, entity.getBrixScore());
        statement.bindString(14, entity.getPhoneMasked());
      }
    };
    this.__insertionAdapterOfCareBidEntity = new EntityInsertionAdapter<CareBidEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `care_bids` (`bidId`,`requestId`,`caregiverId`,`caregiverName`,`careerYears`,`rating`,`reviewCount`,`distanceKm`,`travelTimeMinutes`,`certList`,`insuranceYn`,`vaccineYn`,`gender`,`pitchMessage`,`dailyPrice`,`brixScore`,`status`,`createdAt`) VALUES (nullif(?, 0),?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final CareBidEntity entity) {
        statement.bindLong(1, entity.getBidId());
        statement.bindLong(2, entity.getRequestId());
        statement.bindString(3, entity.getCaregiverId());
        statement.bindString(4, entity.getCaregiverName());
        statement.bindLong(5, entity.getCareerYears());
        statement.bindDouble(6, entity.getRating());
        statement.bindLong(7, entity.getReviewCount());
        statement.bindDouble(8, entity.getDistanceKm());
        statement.bindLong(9, entity.getTravelTimeMinutes());
        final String _tmp = __careConverters.fromStringList(entity.getCertList());
        statement.bindString(10, _tmp);
        final int _tmp_1 = entity.getInsuranceYn() ? 1 : 0;
        statement.bindLong(11, _tmp_1);
        final int _tmp_2 = entity.getVaccineYn() ? 1 : 0;
        statement.bindLong(12, _tmp_2);
        final String _tmp_3 = __careConverters.fromPatientGender(entity.getGender());
        statement.bindString(13, _tmp_3);
        statement.bindString(14, entity.getPitchMessage());
        statement.bindLong(15, entity.getDailyPrice());
        statement.bindDouble(16, entity.getBrixScore());
        final String _tmp_4 = __careConverters.fromBidStatus(entity.getStatus());
        statement.bindString(17, _tmp_4);
        statement.bindLong(18, entity.getCreatedAt());
      }
    };
    this.__insertionAdapterOfChatMessageEntity = new EntityInsertionAdapter<ChatMessageEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `chat_messages` (`messageId`,`bidId`,`senderRole`,`senderName`,`content`,`isInvoice`,`invoiceTotalPrice`,`invoiceDays`,`timestamp`) VALUES (nullif(?, 0),?,?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final ChatMessageEntity entity) {
        statement.bindLong(1, entity.getMessageId());
        statement.bindLong(2, entity.getBidId());
        statement.bindString(3, entity.getSenderRole());
        statement.bindString(4, entity.getSenderName());
        statement.bindString(5, entity.getContent());
        final int _tmp = entity.isInvoice() ? 1 : 0;
        statement.bindLong(6, _tmp);
        statement.bindLong(7, entity.getInvoiceTotalPrice());
        statement.bindLong(8, entity.getInvoiceDays());
        statement.bindLong(9, entity.getTimestamp());
      }
    };
    this.__insertionAdapterOfContractEntity = new EntityInsertionAdapter<ContractEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `contracts` (`contractId`,`requestId`,`bidId`,`caregiverId`,`caregiverName`,`guardianName`,`location`,`dates`,`dailyPrice`,`totalDays`,`supplyPrice`,`platformFee`,`totalPrice`,`escrowStatus`,`journeyStep`,`shareToken`,`isReviewed`,`ratingGiven`,`reviewComment`,`createdAt`) VALUES (nullif(?, 0),?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final ContractEntity entity) {
        statement.bindLong(1, entity.getContractId());
        statement.bindLong(2, entity.getRequestId());
        statement.bindLong(3, entity.getBidId());
        statement.bindString(4, entity.getCaregiverId());
        statement.bindString(5, entity.getCaregiverName());
        statement.bindString(6, entity.getGuardianName());
        statement.bindString(7, entity.getLocation());
        statement.bindString(8, entity.getDates());
        statement.bindLong(9, entity.getDailyPrice());
        statement.bindLong(10, entity.getTotalDays());
        statement.bindLong(11, entity.getSupplyPrice());
        statement.bindLong(12, entity.getPlatformFee());
        statement.bindLong(13, entity.getTotalPrice());
        final String _tmp = __careConverters.fromEscrowStatus(entity.getEscrowStatus());
        statement.bindString(14, _tmp);
        final String _tmp_1 = __careConverters.fromJourneyStep(entity.getJourneyStep());
        statement.bindString(15, _tmp_1);
        statement.bindString(16, entity.getShareToken());
        final int _tmp_2 = entity.isReviewed() ? 1 : 0;
        statement.bindLong(17, _tmp_2);
        statement.bindDouble(18, entity.getRatingGiven());
        statement.bindString(19, entity.getReviewComment());
        statement.bindLong(20, entity.getCreatedAt());
      }
    };
    this.__updateAdapterOfCareRequestEntity = new EntityDeletionOrUpdateAdapter<CareRequestEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "UPDATE OR ABORT `care_requests` SET `id` = ?,`guardianName` = ?,`location` = ?,`hospitalName` = ?,`careType` = ?,`mobility` = ?,`consciousness` = ?,`weightRange` = ?,`gender` = ?,`ageRange` = ?,`specialNeeds` = ?,`startDate` = ?,`endDate` = ?,`totalDays` = ?,`status` = ?,`createdAt` = ? WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final CareRequestEntity entity) {
        statement.bindLong(1, entity.getId());
        statement.bindString(2, entity.getGuardianName());
        statement.bindString(3, entity.getLocation());
        statement.bindString(4, entity.getHospitalName());
        final String _tmp = __careConverters.fromCareType(entity.getCareType());
        statement.bindString(5, _tmp);
        final String _tmp_1 = __careConverters.fromMobility(entity.getMobility());
        statement.bindString(6, _tmp_1);
        final String _tmp_2 = __careConverters.fromConsciousness(entity.getConsciousness());
        statement.bindString(7, _tmp_2);
        final String _tmp_3 = __careConverters.fromWeightRange(entity.getWeightRange());
        statement.bindString(8, _tmp_3);
        final String _tmp_4 = __careConverters.fromPatientGender(entity.getGender());
        statement.bindString(9, _tmp_4);
        final String _tmp_5 = __careConverters.fromAgeRange(entity.getAgeRange());
        statement.bindString(10, _tmp_5);
        final String _tmp_6 = __careConverters.fromStringList(entity.getSpecialNeeds());
        statement.bindString(11, _tmp_6);
        statement.bindString(12, entity.getStartDate());
        statement.bindString(13, entity.getEndDate());
        statement.bindLong(14, entity.getTotalDays());
        final String _tmp_7 = __careConverters.fromRequestStatus(entity.getStatus());
        statement.bindString(15, _tmp_7);
        statement.bindLong(16, entity.getCreatedAt());
        statement.bindLong(17, entity.getId());
      }
    };
    this.__preparedStmtOfUpdateBidStatus = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "UPDATE care_bids SET status = ? WHERE bidId = ?";
        return _query;
      }
    };
    this.__preparedStmtOfUpdateEscrowStatus = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "UPDATE contracts SET escrowStatus = ? WHERE contractId = ?";
        return _query;
      }
    };
    this.__preparedStmtOfUpdateJourneyStep = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "UPDATE contracts SET journeyStep = ? WHERE contractId = ?";
        return _query;
      }
    };
    this.__preparedStmtOfSubmitReview = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "UPDATE contracts SET isReviewed = 1, ratingGiven = ?, reviewComment = ? WHERE contractId = ?";
        return _query;
      }
    };
    this.__preparedStmtOfDeleteContractById = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM contracts WHERE contractId = ?";
        return _query;
      }
    };
    this.__preparedStmtOfDeleteAllContracts = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM contracts";
        return _query;
      }
    };
    this.__preparedStmtOfDeleteAllRequests = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM care_requests";
        return _query;
      }
    };
    this.__preparedStmtOfDeleteAllBids = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM care_bids";
        return _query;
      }
    };
  }

  @Override
  public Object insertRequest(final CareRequestEntity request,
      final Continuation<? super Long> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Long>() {
      @Override
      @NonNull
      public Long call() throws Exception {
        __db.beginTransaction();
        try {
          final Long _result = __insertionAdapterOfCareRequestEntity.insertAndReturnId(request);
          __db.setTransactionSuccessful();
          return _result;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object insertCaregivers(final List<CaregiverProfileEntity> caregivers,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfCaregiverProfileEntity.insert(caregivers);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object insertBid(final CareBidEntity bid, final Continuation<? super Long> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Long>() {
      @Override
      @NonNull
      public Long call() throws Exception {
        __db.beginTransaction();
        try {
          final Long _result = __insertionAdapterOfCareBidEntity.insertAndReturnId(bid);
          __db.setTransactionSuccessful();
          return _result;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object insertBids(final List<CareBidEntity> bids,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfCareBidEntity.insert(bids);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object insertMessage(final ChatMessageEntity message,
      final Continuation<? super Long> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Long>() {
      @Override
      @NonNull
      public Long call() throws Exception {
        __db.beginTransaction();
        try {
          final Long _result = __insertionAdapterOfChatMessageEntity.insertAndReturnId(message);
          __db.setTransactionSuccessful();
          return _result;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object insertContract(final ContractEntity contract,
      final Continuation<? super Long> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Long>() {
      @Override
      @NonNull
      public Long call() throws Exception {
        __db.beginTransaction();
        try {
          final Long _result = __insertionAdapterOfContractEntity.insertAndReturnId(contract);
          __db.setTransactionSuccessful();
          return _result;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object updateRequest(final CareRequestEntity request,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __updateAdapterOfCareRequestEntity.handle(request);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object updateBidStatus(final long bidId, final BidStatus status,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfUpdateBidStatus.acquire();
        int _argIndex = 1;
        final String _tmp = __careConverters.fromBidStatus(status);
        _stmt.bindString(_argIndex, _tmp);
        _argIndex = 2;
        _stmt.bindLong(_argIndex, bidId);
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
          __preparedStmtOfUpdateBidStatus.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Object updateEscrowStatus(final long contractId, final EscrowStatus status,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfUpdateEscrowStatus.acquire();
        int _argIndex = 1;
        final String _tmp = __careConverters.fromEscrowStatus(status);
        _stmt.bindString(_argIndex, _tmp);
        _argIndex = 2;
        _stmt.bindLong(_argIndex, contractId);
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
          __preparedStmtOfUpdateEscrowStatus.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Object updateJourneyStep(final long contractId, final JourneyStep step,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfUpdateJourneyStep.acquire();
        int _argIndex = 1;
        final String _tmp = __careConverters.fromJourneyStep(step);
        _stmt.bindString(_argIndex, _tmp);
        _argIndex = 2;
        _stmt.bindLong(_argIndex, contractId);
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
          __preparedStmtOfUpdateJourneyStep.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Object submitReview(final long contractId, final float rating, final String comment,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfSubmitReview.acquire();
        int _argIndex = 1;
        _stmt.bindDouble(_argIndex, rating);
        _argIndex = 2;
        _stmt.bindString(_argIndex, comment);
        _argIndex = 3;
        _stmt.bindLong(_argIndex, contractId);
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
          __preparedStmtOfSubmitReview.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Object deleteContractById(final long contractId,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteContractById.acquire();
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, contractId);
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
          __preparedStmtOfDeleteContractById.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Object deleteAllContracts(final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteAllContracts.acquire();
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
          __preparedStmtOfDeleteAllContracts.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Object deleteAllRequests(final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteAllRequests.acquire();
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
          __preparedStmtOfDeleteAllRequests.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Object deleteAllBids(final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteAllBids.acquire();
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
          __preparedStmtOfDeleteAllBids.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Flow<List<CareRequestEntity>> getAllRequests() {
    final String _sql = "SELECT * FROM care_requests ORDER BY createdAt DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"care_requests"}, new Callable<List<CareRequestEntity>>() {
      @Override
      @NonNull
      public List<CareRequestEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfGuardianName = CursorUtil.getColumnIndexOrThrow(_cursor, "guardianName");
          final int _cursorIndexOfLocation = CursorUtil.getColumnIndexOrThrow(_cursor, "location");
          final int _cursorIndexOfHospitalName = CursorUtil.getColumnIndexOrThrow(_cursor, "hospitalName");
          final int _cursorIndexOfCareType = CursorUtil.getColumnIndexOrThrow(_cursor, "careType");
          final int _cursorIndexOfMobility = CursorUtil.getColumnIndexOrThrow(_cursor, "mobility");
          final int _cursorIndexOfConsciousness = CursorUtil.getColumnIndexOrThrow(_cursor, "consciousness");
          final int _cursorIndexOfWeightRange = CursorUtil.getColumnIndexOrThrow(_cursor, "weightRange");
          final int _cursorIndexOfGender = CursorUtil.getColumnIndexOrThrow(_cursor, "gender");
          final int _cursorIndexOfAgeRange = CursorUtil.getColumnIndexOrThrow(_cursor, "ageRange");
          final int _cursorIndexOfSpecialNeeds = CursorUtil.getColumnIndexOrThrow(_cursor, "specialNeeds");
          final int _cursorIndexOfStartDate = CursorUtil.getColumnIndexOrThrow(_cursor, "startDate");
          final int _cursorIndexOfEndDate = CursorUtil.getColumnIndexOrThrow(_cursor, "endDate");
          final int _cursorIndexOfTotalDays = CursorUtil.getColumnIndexOrThrow(_cursor, "totalDays");
          final int _cursorIndexOfStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "status");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final List<CareRequestEntity> _result = new ArrayList<CareRequestEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final CareRequestEntity _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpGuardianName;
            _tmpGuardianName = _cursor.getString(_cursorIndexOfGuardianName);
            final String _tmpLocation;
            _tmpLocation = _cursor.getString(_cursorIndexOfLocation);
            final String _tmpHospitalName;
            _tmpHospitalName = _cursor.getString(_cursorIndexOfHospitalName);
            final CareType _tmpCareType;
            final String _tmp;
            _tmp = _cursor.getString(_cursorIndexOfCareType);
            _tmpCareType = __careConverters.toCareType(_tmp);
            final Mobility _tmpMobility;
            final String _tmp_1;
            _tmp_1 = _cursor.getString(_cursorIndexOfMobility);
            _tmpMobility = __careConverters.toMobility(_tmp_1);
            final Consciousness _tmpConsciousness;
            final String _tmp_2;
            _tmp_2 = _cursor.getString(_cursorIndexOfConsciousness);
            _tmpConsciousness = __careConverters.toConsciousness(_tmp_2);
            final WeightRange _tmpWeightRange;
            final String _tmp_3;
            _tmp_3 = _cursor.getString(_cursorIndexOfWeightRange);
            _tmpWeightRange = __careConverters.toWeightRange(_tmp_3);
            final PatientGender _tmpGender;
            final String _tmp_4;
            _tmp_4 = _cursor.getString(_cursorIndexOfGender);
            _tmpGender = __careConverters.toPatientGender(_tmp_4);
            final AgeRange _tmpAgeRange;
            final String _tmp_5;
            _tmp_5 = _cursor.getString(_cursorIndexOfAgeRange);
            _tmpAgeRange = __careConverters.toAgeRange(_tmp_5);
            final List<String> _tmpSpecialNeeds;
            final String _tmp_6;
            _tmp_6 = _cursor.getString(_cursorIndexOfSpecialNeeds);
            _tmpSpecialNeeds = __careConverters.toStringList(_tmp_6);
            final String _tmpStartDate;
            _tmpStartDate = _cursor.getString(_cursorIndexOfStartDate);
            final String _tmpEndDate;
            _tmpEndDate = _cursor.getString(_cursorIndexOfEndDate);
            final int _tmpTotalDays;
            _tmpTotalDays = _cursor.getInt(_cursorIndexOfTotalDays);
            final RequestStatus _tmpStatus;
            final String _tmp_7;
            _tmp_7 = _cursor.getString(_cursorIndexOfStatus);
            _tmpStatus = __careConverters.toRequestStatus(_tmp_7);
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            _item = new CareRequestEntity(_tmpId,_tmpGuardianName,_tmpLocation,_tmpHospitalName,_tmpCareType,_tmpMobility,_tmpConsciousness,_tmpWeightRange,_tmpGender,_tmpAgeRange,_tmpSpecialNeeds,_tmpStartDate,_tmpEndDate,_tmpTotalDays,_tmpStatus,_tmpCreatedAt);
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
  public Flow<List<CareRequestEntity>> getRequestsByStatus(final RequestStatus status) {
    final String _sql = "SELECT * FROM care_requests WHERE status = ? ORDER BY createdAt DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    final String _tmp = __careConverters.fromRequestStatus(status);
    _statement.bindString(_argIndex, _tmp);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"care_requests"}, new Callable<List<CareRequestEntity>>() {
      @Override
      @NonNull
      public List<CareRequestEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfGuardianName = CursorUtil.getColumnIndexOrThrow(_cursor, "guardianName");
          final int _cursorIndexOfLocation = CursorUtil.getColumnIndexOrThrow(_cursor, "location");
          final int _cursorIndexOfHospitalName = CursorUtil.getColumnIndexOrThrow(_cursor, "hospitalName");
          final int _cursorIndexOfCareType = CursorUtil.getColumnIndexOrThrow(_cursor, "careType");
          final int _cursorIndexOfMobility = CursorUtil.getColumnIndexOrThrow(_cursor, "mobility");
          final int _cursorIndexOfConsciousness = CursorUtil.getColumnIndexOrThrow(_cursor, "consciousness");
          final int _cursorIndexOfWeightRange = CursorUtil.getColumnIndexOrThrow(_cursor, "weightRange");
          final int _cursorIndexOfGender = CursorUtil.getColumnIndexOrThrow(_cursor, "gender");
          final int _cursorIndexOfAgeRange = CursorUtil.getColumnIndexOrThrow(_cursor, "ageRange");
          final int _cursorIndexOfSpecialNeeds = CursorUtil.getColumnIndexOrThrow(_cursor, "specialNeeds");
          final int _cursorIndexOfStartDate = CursorUtil.getColumnIndexOrThrow(_cursor, "startDate");
          final int _cursorIndexOfEndDate = CursorUtil.getColumnIndexOrThrow(_cursor, "endDate");
          final int _cursorIndexOfTotalDays = CursorUtil.getColumnIndexOrThrow(_cursor, "totalDays");
          final int _cursorIndexOfStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "status");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final List<CareRequestEntity> _result = new ArrayList<CareRequestEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final CareRequestEntity _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpGuardianName;
            _tmpGuardianName = _cursor.getString(_cursorIndexOfGuardianName);
            final String _tmpLocation;
            _tmpLocation = _cursor.getString(_cursorIndexOfLocation);
            final String _tmpHospitalName;
            _tmpHospitalName = _cursor.getString(_cursorIndexOfHospitalName);
            final CareType _tmpCareType;
            final String _tmp_1;
            _tmp_1 = _cursor.getString(_cursorIndexOfCareType);
            _tmpCareType = __careConverters.toCareType(_tmp_1);
            final Mobility _tmpMobility;
            final String _tmp_2;
            _tmp_2 = _cursor.getString(_cursorIndexOfMobility);
            _tmpMobility = __careConverters.toMobility(_tmp_2);
            final Consciousness _tmpConsciousness;
            final String _tmp_3;
            _tmp_3 = _cursor.getString(_cursorIndexOfConsciousness);
            _tmpConsciousness = __careConverters.toConsciousness(_tmp_3);
            final WeightRange _tmpWeightRange;
            final String _tmp_4;
            _tmp_4 = _cursor.getString(_cursorIndexOfWeightRange);
            _tmpWeightRange = __careConverters.toWeightRange(_tmp_4);
            final PatientGender _tmpGender;
            final String _tmp_5;
            _tmp_5 = _cursor.getString(_cursorIndexOfGender);
            _tmpGender = __careConverters.toPatientGender(_tmp_5);
            final AgeRange _tmpAgeRange;
            final String _tmp_6;
            _tmp_6 = _cursor.getString(_cursorIndexOfAgeRange);
            _tmpAgeRange = __careConverters.toAgeRange(_tmp_6);
            final List<String> _tmpSpecialNeeds;
            final String _tmp_7;
            _tmp_7 = _cursor.getString(_cursorIndexOfSpecialNeeds);
            _tmpSpecialNeeds = __careConverters.toStringList(_tmp_7);
            final String _tmpStartDate;
            _tmpStartDate = _cursor.getString(_cursorIndexOfStartDate);
            final String _tmpEndDate;
            _tmpEndDate = _cursor.getString(_cursorIndexOfEndDate);
            final int _tmpTotalDays;
            _tmpTotalDays = _cursor.getInt(_cursorIndexOfTotalDays);
            final RequestStatus _tmpStatus;
            final String _tmp_8;
            _tmp_8 = _cursor.getString(_cursorIndexOfStatus);
            _tmpStatus = __careConverters.toRequestStatus(_tmp_8);
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            _item = new CareRequestEntity(_tmpId,_tmpGuardianName,_tmpLocation,_tmpHospitalName,_tmpCareType,_tmpMobility,_tmpConsciousness,_tmpWeightRange,_tmpGender,_tmpAgeRange,_tmpSpecialNeeds,_tmpStartDate,_tmpEndDate,_tmpTotalDays,_tmpStatus,_tmpCreatedAt);
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
  public Object getRequestById(final long id,
      final Continuation<? super CareRequestEntity> $completion) {
    final String _sql = "SELECT * FROM care_requests WHERE id = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, id);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<CareRequestEntity>() {
      @Override
      @Nullable
      public CareRequestEntity call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfGuardianName = CursorUtil.getColumnIndexOrThrow(_cursor, "guardianName");
          final int _cursorIndexOfLocation = CursorUtil.getColumnIndexOrThrow(_cursor, "location");
          final int _cursorIndexOfHospitalName = CursorUtil.getColumnIndexOrThrow(_cursor, "hospitalName");
          final int _cursorIndexOfCareType = CursorUtil.getColumnIndexOrThrow(_cursor, "careType");
          final int _cursorIndexOfMobility = CursorUtil.getColumnIndexOrThrow(_cursor, "mobility");
          final int _cursorIndexOfConsciousness = CursorUtil.getColumnIndexOrThrow(_cursor, "consciousness");
          final int _cursorIndexOfWeightRange = CursorUtil.getColumnIndexOrThrow(_cursor, "weightRange");
          final int _cursorIndexOfGender = CursorUtil.getColumnIndexOrThrow(_cursor, "gender");
          final int _cursorIndexOfAgeRange = CursorUtil.getColumnIndexOrThrow(_cursor, "ageRange");
          final int _cursorIndexOfSpecialNeeds = CursorUtil.getColumnIndexOrThrow(_cursor, "specialNeeds");
          final int _cursorIndexOfStartDate = CursorUtil.getColumnIndexOrThrow(_cursor, "startDate");
          final int _cursorIndexOfEndDate = CursorUtil.getColumnIndexOrThrow(_cursor, "endDate");
          final int _cursorIndexOfTotalDays = CursorUtil.getColumnIndexOrThrow(_cursor, "totalDays");
          final int _cursorIndexOfStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "status");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final CareRequestEntity _result;
          if (_cursor.moveToFirst()) {
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpGuardianName;
            _tmpGuardianName = _cursor.getString(_cursorIndexOfGuardianName);
            final String _tmpLocation;
            _tmpLocation = _cursor.getString(_cursorIndexOfLocation);
            final String _tmpHospitalName;
            _tmpHospitalName = _cursor.getString(_cursorIndexOfHospitalName);
            final CareType _tmpCareType;
            final String _tmp;
            _tmp = _cursor.getString(_cursorIndexOfCareType);
            _tmpCareType = __careConverters.toCareType(_tmp);
            final Mobility _tmpMobility;
            final String _tmp_1;
            _tmp_1 = _cursor.getString(_cursorIndexOfMobility);
            _tmpMobility = __careConverters.toMobility(_tmp_1);
            final Consciousness _tmpConsciousness;
            final String _tmp_2;
            _tmp_2 = _cursor.getString(_cursorIndexOfConsciousness);
            _tmpConsciousness = __careConverters.toConsciousness(_tmp_2);
            final WeightRange _tmpWeightRange;
            final String _tmp_3;
            _tmp_3 = _cursor.getString(_cursorIndexOfWeightRange);
            _tmpWeightRange = __careConverters.toWeightRange(_tmp_3);
            final PatientGender _tmpGender;
            final String _tmp_4;
            _tmp_4 = _cursor.getString(_cursorIndexOfGender);
            _tmpGender = __careConverters.toPatientGender(_tmp_4);
            final AgeRange _tmpAgeRange;
            final String _tmp_5;
            _tmp_5 = _cursor.getString(_cursorIndexOfAgeRange);
            _tmpAgeRange = __careConverters.toAgeRange(_tmp_5);
            final List<String> _tmpSpecialNeeds;
            final String _tmp_6;
            _tmp_6 = _cursor.getString(_cursorIndexOfSpecialNeeds);
            _tmpSpecialNeeds = __careConverters.toStringList(_tmp_6);
            final String _tmpStartDate;
            _tmpStartDate = _cursor.getString(_cursorIndexOfStartDate);
            final String _tmpEndDate;
            _tmpEndDate = _cursor.getString(_cursorIndexOfEndDate);
            final int _tmpTotalDays;
            _tmpTotalDays = _cursor.getInt(_cursorIndexOfTotalDays);
            final RequestStatus _tmpStatus;
            final String _tmp_7;
            _tmp_7 = _cursor.getString(_cursorIndexOfStatus);
            _tmpStatus = __careConverters.toRequestStatus(_tmp_7);
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            _result = new CareRequestEntity(_tmpId,_tmpGuardianName,_tmpLocation,_tmpHospitalName,_tmpCareType,_tmpMobility,_tmpConsciousness,_tmpWeightRange,_tmpGender,_tmpAgeRange,_tmpSpecialNeeds,_tmpStartDate,_tmpEndDate,_tmpTotalDays,_tmpStatus,_tmpCreatedAt);
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
  public Flow<List<CaregiverProfileEntity>> getAllCaregivers() {
    final String _sql = "SELECT * FROM caregivers";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"caregivers"}, new Callable<List<CaregiverProfileEntity>>() {
      @Override
      @NonNull
      public List<CaregiverProfileEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfCaregiverId = CursorUtil.getColumnIndexOrThrow(_cursor, "caregiverId");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfCareerYears = CursorUtil.getColumnIndexOrThrow(_cursor, "careerYears");
          final int _cursorIndexOfRating = CursorUtil.getColumnIndexOrThrow(_cursor, "rating");
          final int _cursorIndexOfReviewCount = CursorUtil.getColumnIndexOrThrow(_cursor, "reviewCount");
          final int _cursorIndexOfDistanceKm = CursorUtil.getColumnIndexOrThrow(_cursor, "distanceKm");
          final int _cursorIndexOfTravelTimeMinutes = CursorUtil.getColumnIndexOrThrow(_cursor, "travelTimeMinutes");
          final int _cursorIndexOfCertList = CursorUtil.getColumnIndexOrThrow(_cursor, "certList");
          final int _cursorIndexOfInsuranceYn = CursorUtil.getColumnIndexOrThrow(_cursor, "insuranceYn");
          final int _cursorIndexOfVaccineYn = CursorUtil.getColumnIndexOrThrow(_cursor, "vaccineYn");
          final int _cursorIndexOfGender = CursorUtil.getColumnIndexOrThrow(_cursor, "gender");
          final int _cursorIndexOfBio = CursorUtil.getColumnIndexOrThrow(_cursor, "bio");
          final int _cursorIndexOfBrixScore = CursorUtil.getColumnIndexOrThrow(_cursor, "brixScore");
          final int _cursorIndexOfPhoneMasked = CursorUtil.getColumnIndexOrThrow(_cursor, "phoneMasked");
          final List<CaregiverProfileEntity> _result = new ArrayList<CaregiverProfileEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final CaregiverProfileEntity _item;
            final String _tmpCaregiverId;
            _tmpCaregiverId = _cursor.getString(_cursorIndexOfCaregiverId);
            final String _tmpName;
            _tmpName = _cursor.getString(_cursorIndexOfName);
            final int _tmpCareerYears;
            _tmpCareerYears = _cursor.getInt(_cursorIndexOfCareerYears);
            final double _tmpRating;
            _tmpRating = _cursor.getDouble(_cursorIndexOfRating);
            final int _tmpReviewCount;
            _tmpReviewCount = _cursor.getInt(_cursorIndexOfReviewCount);
            final double _tmpDistanceKm;
            _tmpDistanceKm = _cursor.getDouble(_cursorIndexOfDistanceKm);
            final int _tmpTravelTimeMinutes;
            _tmpTravelTimeMinutes = _cursor.getInt(_cursorIndexOfTravelTimeMinutes);
            final List<String> _tmpCertList;
            final String _tmp;
            _tmp = _cursor.getString(_cursorIndexOfCertList);
            _tmpCertList = __careConverters.toStringList(_tmp);
            final boolean _tmpInsuranceYn;
            final int _tmp_1;
            _tmp_1 = _cursor.getInt(_cursorIndexOfInsuranceYn);
            _tmpInsuranceYn = _tmp_1 != 0;
            final boolean _tmpVaccineYn;
            final int _tmp_2;
            _tmp_2 = _cursor.getInt(_cursorIndexOfVaccineYn);
            _tmpVaccineYn = _tmp_2 != 0;
            final PatientGender _tmpGender;
            final String _tmp_3;
            _tmp_3 = _cursor.getString(_cursorIndexOfGender);
            _tmpGender = __careConverters.toPatientGender(_tmp_3);
            final String _tmpBio;
            _tmpBio = _cursor.getString(_cursorIndexOfBio);
            final float _tmpBrixScore;
            _tmpBrixScore = _cursor.getFloat(_cursorIndexOfBrixScore);
            final String _tmpPhoneMasked;
            _tmpPhoneMasked = _cursor.getString(_cursorIndexOfPhoneMasked);
            _item = new CaregiverProfileEntity(_tmpCaregiverId,_tmpName,_tmpCareerYears,_tmpRating,_tmpReviewCount,_tmpDistanceKm,_tmpTravelTimeMinutes,_tmpCertList,_tmpInsuranceYn,_tmpVaccineYn,_tmpGender,_tmpBio,_tmpBrixScore,_tmpPhoneMasked);
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
  public Flow<List<CareBidEntity>> getBidsForRequest(final long requestId) {
    final String _sql = "SELECT * FROM care_bids WHERE requestId = ? ORDER BY dailyPrice ASC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, requestId);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"care_bids"}, new Callable<List<CareBidEntity>>() {
      @Override
      @NonNull
      public List<CareBidEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfBidId = CursorUtil.getColumnIndexOrThrow(_cursor, "bidId");
          final int _cursorIndexOfRequestId = CursorUtil.getColumnIndexOrThrow(_cursor, "requestId");
          final int _cursorIndexOfCaregiverId = CursorUtil.getColumnIndexOrThrow(_cursor, "caregiverId");
          final int _cursorIndexOfCaregiverName = CursorUtil.getColumnIndexOrThrow(_cursor, "caregiverName");
          final int _cursorIndexOfCareerYears = CursorUtil.getColumnIndexOrThrow(_cursor, "careerYears");
          final int _cursorIndexOfRating = CursorUtil.getColumnIndexOrThrow(_cursor, "rating");
          final int _cursorIndexOfReviewCount = CursorUtil.getColumnIndexOrThrow(_cursor, "reviewCount");
          final int _cursorIndexOfDistanceKm = CursorUtil.getColumnIndexOrThrow(_cursor, "distanceKm");
          final int _cursorIndexOfTravelTimeMinutes = CursorUtil.getColumnIndexOrThrow(_cursor, "travelTimeMinutes");
          final int _cursorIndexOfCertList = CursorUtil.getColumnIndexOrThrow(_cursor, "certList");
          final int _cursorIndexOfInsuranceYn = CursorUtil.getColumnIndexOrThrow(_cursor, "insuranceYn");
          final int _cursorIndexOfVaccineYn = CursorUtil.getColumnIndexOrThrow(_cursor, "vaccineYn");
          final int _cursorIndexOfGender = CursorUtil.getColumnIndexOrThrow(_cursor, "gender");
          final int _cursorIndexOfPitchMessage = CursorUtil.getColumnIndexOrThrow(_cursor, "pitchMessage");
          final int _cursorIndexOfDailyPrice = CursorUtil.getColumnIndexOrThrow(_cursor, "dailyPrice");
          final int _cursorIndexOfBrixScore = CursorUtil.getColumnIndexOrThrow(_cursor, "brixScore");
          final int _cursorIndexOfStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "status");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final List<CareBidEntity> _result = new ArrayList<CareBidEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final CareBidEntity _item;
            final long _tmpBidId;
            _tmpBidId = _cursor.getLong(_cursorIndexOfBidId);
            final long _tmpRequestId;
            _tmpRequestId = _cursor.getLong(_cursorIndexOfRequestId);
            final String _tmpCaregiverId;
            _tmpCaregiverId = _cursor.getString(_cursorIndexOfCaregiverId);
            final String _tmpCaregiverName;
            _tmpCaregiverName = _cursor.getString(_cursorIndexOfCaregiverName);
            final int _tmpCareerYears;
            _tmpCareerYears = _cursor.getInt(_cursorIndexOfCareerYears);
            final double _tmpRating;
            _tmpRating = _cursor.getDouble(_cursorIndexOfRating);
            final int _tmpReviewCount;
            _tmpReviewCount = _cursor.getInt(_cursorIndexOfReviewCount);
            final double _tmpDistanceKm;
            _tmpDistanceKm = _cursor.getDouble(_cursorIndexOfDistanceKm);
            final int _tmpTravelTimeMinutes;
            _tmpTravelTimeMinutes = _cursor.getInt(_cursorIndexOfTravelTimeMinutes);
            final List<String> _tmpCertList;
            final String _tmp;
            _tmp = _cursor.getString(_cursorIndexOfCertList);
            _tmpCertList = __careConverters.toStringList(_tmp);
            final boolean _tmpInsuranceYn;
            final int _tmp_1;
            _tmp_1 = _cursor.getInt(_cursorIndexOfInsuranceYn);
            _tmpInsuranceYn = _tmp_1 != 0;
            final boolean _tmpVaccineYn;
            final int _tmp_2;
            _tmp_2 = _cursor.getInt(_cursorIndexOfVaccineYn);
            _tmpVaccineYn = _tmp_2 != 0;
            final PatientGender _tmpGender;
            final String _tmp_3;
            _tmp_3 = _cursor.getString(_cursorIndexOfGender);
            _tmpGender = __careConverters.toPatientGender(_tmp_3);
            final String _tmpPitchMessage;
            _tmpPitchMessage = _cursor.getString(_cursorIndexOfPitchMessage);
            final int _tmpDailyPrice;
            _tmpDailyPrice = _cursor.getInt(_cursorIndexOfDailyPrice);
            final float _tmpBrixScore;
            _tmpBrixScore = _cursor.getFloat(_cursorIndexOfBrixScore);
            final BidStatus _tmpStatus;
            final String _tmp_4;
            _tmp_4 = _cursor.getString(_cursorIndexOfStatus);
            _tmpStatus = __careConverters.toBidStatus(_tmp_4);
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            _item = new CareBidEntity(_tmpBidId,_tmpRequestId,_tmpCaregiverId,_tmpCaregiverName,_tmpCareerYears,_tmpRating,_tmpReviewCount,_tmpDistanceKm,_tmpTravelTimeMinutes,_tmpCertList,_tmpInsuranceYn,_tmpVaccineYn,_tmpGender,_tmpPitchMessage,_tmpDailyPrice,_tmpBrixScore,_tmpStatus,_tmpCreatedAt);
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
  public Object getBidById(final long bidId,
      final Continuation<? super CareBidEntity> $completion) {
    final String _sql = "SELECT * FROM care_bids WHERE bidId = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, bidId);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<CareBidEntity>() {
      @Override
      @Nullable
      public CareBidEntity call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfBidId = CursorUtil.getColumnIndexOrThrow(_cursor, "bidId");
          final int _cursorIndexOfRequestId = CursorUtil.getColumnIndexOrThrow(_cursor, "requestId");
          final int _cursorIndexOfCaregiverId = CursorUtil.getColumnIndexOrThrow(_cursor, "caregiverId");
          final int _cursorIndexOfCaregiverName = CursorUtil.getColumnIndexOrThrow(_cursor, "caregiverName");
          final int _cursorIndexOfCareerYears = CursorUtil.getColumnIndexOrThrow(_cursor, "careerYears");
          final int _cursorIndexOfRating = CursorUtil.getColumnIndexOrThrow(_cursor, "rating");
          final int _cursorIndexOfReviewCount = CursorUtil.getColumnIndexOrThrow(_cursor, "reviewCount");
          final int _cursorIndexOfDistanceKm = CursorUtil.getColumnIndexOrThrow(_cursor, "distanceKm");
          final int _cursorIndexOfTravelTimeMinutes = CursorUtil.getColumnIndexOrThrow(_cursor, "travelTimeMinutes");
          final int _cursorIndexOfCertList = CursorUtil.getColumnIndexOrThrow(_cursor, "certList");
          final int _cursorIndexOfInsuranceYn = CursorUtil.getColumnIndexOrThrow(_cursor, "insuranceYn");
          final int _cursorIndexOfVaccineYn = CursorUtil.getColumnIndexOrThrow(_cursor, "vaccineYn");
          final int _cursorIndexOfGender = CursorUtil.getColumnIndexOrThrow(_cursor, "gender");
          final int _cursorIndexOfPitchMessage = CursorUtil.getColumnIndexOrThrow(_cursor, "pitchMessage");
          final int _cursorIndexOfDailyPrice = CursorUtil.getColumnIndexOrThrow(_cursor, "dailyPrice");
          final int _cursorIndexOfBrixScore = CursorUtil.getColumnIndexOrThrow(_cursor, "brixScore");
          final int _cursorIndexOfStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "status");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final CareBidEntity _result;
          if (_cursor.moveToFirst()) {
            final long _tmpBidId;
            _tmpBidId = _cursor.getLong(_cursorIndexOfBidId);
            final long _tmpRequestId;
            _tmpRequestId = _cursor.getLong(_cursorIndexOfRequestId);
            final String _tmpCaregiverId;
            _tmpCaregiverId = _cursor.getString(_cursorIndexOfCaregiverId);
            final String _tmpCaregiverName;
            _tmpCaregiverName = _cursor.getString(_cursorIndexOfCaregiverName);
            final int _tmpCareerYears;
            _tmpCareerYears = _cursor.getInt(_cursorIndexOfCareerYears);
            final double _tmpRating;
            _tmpRating = _cursor.getDouble(_cursorIndexOfRating);
            final int _tmpReviewCount;
            _tmpReviewCount = _cursor.getInt(_cursorIndexOfReviewCount);
            final double _tmpDistanceKm;
            _tmpDistanceKm = _cursor.getDouble(_cursorIndexOfDistanceKm);
            final int _tmpTravelTimeMinutes;
            _tmpTravelTimeMinutes = _cursor.getInt(_cursorIndexOfTravelTimeMinutes);
            final List<String> _tmpCertList;
            final String _tmp;
            _tmp = _cursor.getString(_cursorIndexOfCertList);
            _tmpCertList = __careConverters.toStringList(_tmp);
            final boolean _tmpInsuranceYn;
            final int _tmp_1;
            _tmp_1 = _cursor.getInt(_cursorIndexOfInsuranceYn);
            _tmpInsuranceYn = _tmp_1 != 0;
            final boolean _tmpVaccineYn;
            final int _tmp_2;
            _tmp_2 = _cursor.getInt(_cursorIndexOfVaccineYn);
            _tmpVaccineYn = _tmp_2 != 0;
            final PatientGender _tmpGender;
            final String _tmp_3;
            _tmp_3 = _cursor.getString(_cursorIndexOfGender);
            _tmpGender = __careConverters.toPatientGender(_tmp_3);
            final String _tmpPitchMessage;
            _tmpPitchMessage = _cursor.getString(_cursorIndexOfPitchMessage);
            final int _tmpDailyPrice;
            _tmpDailyPrice = _cursor.getInt(_cursorIndexOfDailyPrice);
            final float _tmpBrixScore;
            _tmpBrixScore = _cursor.getFloat(_cursorIndexOfBrixScore);
            final BidStatus _tmpStatus;
            final String _tmp_4;
            _tmp_4 = _cursor.getString(_cursorIndexOfStatus);
            _tmpStatus = __careConverters.toBidStatus(_tmp_4);
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            _result = new CareBidEntity(_tmpBidId,_tmpRequestId,_tmpCaregiverId,_tmpCaregiverName,_tmpCareerYears,_tmpRating,_tmpReviewCount,_tmpDistanceKm,_tmpTravelTimeMinutes,_tmpCertList,_tmpInsuranceYn,_tmpVaccineYn,_tmpGender,_tmpPitchMessage,_tmpDailyPrice,_tmpBrixScore,_tmpStatus,_tmpCreatedAt);
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
  public Flow<List<ChatMessageEntity>> getMessagesForBid(final long bidId) {
    final String _sql = "SELECT * FROM chat_messages WHERE bidId = ? ORDER BY timestamp ASC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, bidId);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"chat_messages"}, new Callable<List<ChatMessageEntity>>() {
      @Override
      @NonNull
      public List<ChatMessageEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfMessageId = CursorUtil.getColumnIndexOrThrow(_cursor, "messageId");
          final int _cursorIndexOfBidId = CursorUtil.getColumnIndexOrThrow(_cursor, "bidId");
          final int _cursorIndexOfSenderRole = CursorUtil.getColumnIndexOrThrow(_cursor, "senderRole");
          final int _cursorIndexOfSenderName = CursorUtil.getColumnIndexOrThrow(_cursor, "senderName");
          final int _cursorIndexOfContent = CursorUtil.getColumnIndexOrThrow(_cursor, "content");
          final int _cursorIndexOfIsInvoice = CursorUtil.getColumnIndexOrThrow(_cursor, "isInvoice");
          final int _cursorIndexOfInvoiceTotalPrice = CursorUtil.getColumnIndexOrThrow(_cursor, "invoiceTotalPrice");
          final int _cursorIndexOfInvoiceDays = CursorUtil.getColumnIndexOrThrow(_cursor, "invoiceDays");
          final int _cursorIndexOfTimestamp = CursorUtil.getColumnIndexOrThrow(_cursor, "timestamp");
          final List<ChatMessageEntity> _result = new ArrayList<ChatMessageEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final ChatMessageEntity _item;
            final long _tmpMessageId;
            _tmpMessageId = _cursor.getLong(_cursorIndexOfMessageId);
            final long _tmpBidId;
            _tmpBidId = _cursor.getLong(_cursorIndexOfBidId);
            final String _tmpSenderRole;
            _tmpSenderRole = _cursor.getString(_cursorIndexOfSenderRole);
            final String _tmpSenderName;
            _tmpSenderName = _cursor.getString(_cursorIndexOfSenderName);
            final String _tmpContent;
            _tmpContent = _cursor.getString(_cursorIndexOfContent);
            final boolean _tmpIsInvoice;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsInvoice);
            _tmpIsInvoice = _tmp != 0;
            final int _tmpInvoiceTotalPrice;
            _tmpInvoiceTotalPrice = _cursor.getInt(_cursorIndexOfInvoiceTotalPrice);
            final int _tmpInvoiceDays;
            _tmpInvoiceDays = _cursor.getInt(_cursorIndexOfInvoiceDays);
            final long _tmpTimestamp;
            _tmpTimestamp = _cursor.getLong(_cursorIndexOfTimestamp);
            _item = new ChatMessageEntity(_tmpMessageId,_tmpBidId,_tmpSenderRole,_tmpSenderName,_tmpContent,_tmpIsInvoice,_tmpInvoiceTotalPrice,_tmpInvoiceDays,_tmpTimestamp);
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
  public Flow<List<ContractEntity>> getAllContracts() {
    final String _sql = "SELECT * FROM contracts ORDER BY createdAt DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"contracts"}, new Callable<List<ContractEntity>>() {
      @Override
      @NonNull
      public List<ContractEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfContractId = CursorUtil.getColumnIndexOrThrow(_cursor, "contractId");
          final int _cursorIndexOfRequestId = CursorUtil.getColumnIndexOrThrow(_cursor, "requestId");
          final int _cursorIndexOfBidId = CursorUtil.getColumnIndexOrThrow(_cursor, "bidId");
          final int _cursorIndexOfCaregiverId = CursorUtil.getColumnIndexOrThrow(_cursor, "caregiverId");
          final int _cursorIndexOfCaregiverName = CursorUtil.getColumnIndexOrThrow(_cursor, "caregiverName");
          final int _cursorIndexOfGuardianName = CursorUtil.getColumnIndexOrThrow(_cursor, "guardianName");
          final int _cursorIndexOfLocation = CursorUtil.getColumnIndexOrThrow(_cursor, "location");
          final int _cursorIndexOfDates = CursorUtil.getColumnIndexOrThrow(_cursor, "dates");
          final int _cursorIndexOfDailyPrice = CursorUtil.getColumnIndexOrThrow(_cursor, "dailyPrice");
          final int _cursorIndexOfTotalDays = CursorUtil.getColumnIndexOrThrow(_cursor, "totalDays");
          final int _cursorIndexOfSupplyPrice = CursorUtil.getColumnIndexOrThrow(_cursor, "supplyPrice");
          final int _cursorIndexOfPlatformFee = CursorUtil.getColumnIndexOrThrow(_cursor, "platformFee");
          final int _cursorIndexOfTotalPrice = CursorUtil.getColumnIndexOrThrow(_cursor, "totalPrice");
          final int _cursorIndexOfEscrowStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "escrowStatus");
          final int _cursorIndexOfJourneyStep = CursorUtil.getColumnIndexOrThrow(_cursor, "journeyStep");
          final int _cursorIndexOfShareToken = CursorUtil.getColumnIndexOrThrow(_cursor, "shareToken");
          final int _cursorIndexOfIsReviewed = CursorUtil.getColumnIndexOrThrow(_cursor, "isReviewed");
          final int _cursorIndexOfRatingGiven = CursorUtil.getColumnIndexOrThrow(_cursor, "ratingGiven");
          final int _cursorIndexOfReviewComment = CursorUtil.getColumnIndexOrThrow(_cursor, "reviewComment");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final List<ContractEntity> _result = new ArrayList<ContractEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final ContractEntity _item;
            final long _tmpContractId;
            _tmpContractId = _cursor.getLong(_cursorIndexOfContractId);
            final long _tmpRequestId;
            _tmpRequestId = _cursor.getLong(_cursorIndexOfRequestId);
            final long _tmpBidId;
            _tmpBidId = _cursor.getLong(_cursorIndexOfBidId);
            final String _tmpCaregiverId;
            _tmpCaregiverId = _cursor.getString(_cursorIndexOfCaregiverId);
            final String _tmpCaregiverName;
            _tmpCaregiverName = _cursor.getString(_cursorIndexOfCaregiverName);
            final String _tmpGuardianName;
            _tmpGuardianName = _cursor.getString(_cursorIndexOfGuardianName);
            final String _tmpLocation;
            _tmpLocation = _cursor.getString(_cursorIndexOfLocation);
            final String _tmpDates;
            _tmpDates = _cursor.getString(_cursorIndexOfDates);
            final int _tmpDailyPrice;
            _tmpDailyPrice = _cursor.getInt(_cursorIndexOfDailyPrice);
            final int _tmpTotalDays;
            _tmpTotalDays = _cursor.getInt(_cursorIndexOfTotalDays);
            final int _tmpSupplyPrice;
            _tmpSupplyPrice = _cursor.getInt(_cursorIndexOfSupplyPrice);
            final int _tmpPlatformFee;
            _tmpPlatformFee = _cursor.getInt(_cursorIndexOfPlatformFee);
            final int _tmpTotalPrice;
            _tmpTotalPrice = _cursor.getInt(_cursorIndexOfTotalPrice);
            final EscrowStatus _tmpEscrowStatus;
            final String _tmp;
            _tmp = _cursor.getString(_cursorIndexOfEscrowStatus);
            _tmpEscrowStatus = __careConverters.toEscrowStatus(_tmp);
            final JourneyStep _tmpJourneyStep;
            final String _tmp_1;
            _tmp_1 = _cursor.getString(_cursorIndexOfJourneyStep);
            _tmpJourneyStep = __careConverters.toJourneyStep(_tmp_1);
            final String _tmpShareToken;
            _tmpShareToken = _cursor.getString(_cursorIndexOfShareToken);
            final boolean _tmpIsReviewed;
            final int _tmp_2;
            _tmp_2 = _cursor.getInt(_cursorIndexOfIsReviewed);
            _tmpIsReviewed = _tmp_2 != 0;
            final float _tmpRatingGiven;
            _tmpRatingGiven = _cursor.getFloat(_cursorIndexOfRatingGiven);
            final String _tmpReviewComment;
            _tmpReviewComment = _cursor.getString(_cursorIndexOfReviewComment);
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            _item = new ContractEntity(_tmpContractId,_tmpRequestId,_tmpBidId,_tmpCaregiverId,_tmpCaregiverName,_tmpGuardianName,_tmpLocation,_tmpDates,_tmpDailyPrice,_tmpTotalDays,_tmpSupplyPrice,_tmpPlatformFee,_tmpTotalPrice,_tmpEscrowStatus,_tmpJourneyStep,_tmpShareToken,_tmpIsReviewed,_tmpRatingGiven,_tmpReviewComment,_tmpCreatedAt);
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
  public Object getContractById(final long contractId,
      final Continuation<? super ContractEntity> $completion) {
    final String _sql = "SELECT * FROM contracts WHERE contractId = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, contractId);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<ContractEntity>() {
      @Override
      @Nullable
      public ContractEntity call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfContractId = CursorUtil.getColumnIndexOrThrow(_cursor, "contractId");
          final int _cursorIndexOfRequestId = CursorUtil.getColumnIndexOrThrow(_cursor, "requestId");
          final int _cursorIndexOfBidId = CursorUtil.getColumnIndexOrThrow(_cursor, "bidId");
          final int _cursorIndexOfCaregiverId = CursorUtil.getColumnIndexOrThrow(_cursor, "caregiverId");
          final int _cursorIndexOfCaregiverName = CursorUtil.getColumnIndexOrThrow(_cursor, "caregiverName");
          final int _cursorIndexOfGuardianName = CursorUtil.getColumnIndexOrThrow(_cursor, "guardianName");
          final int _cursorIndexOfLocation = CursorUtil.getColumnIndexOrThrow(_cursor, "location");
          final int _cursorIndexOfDates = CursorUtil.getColumnIndexOrThrow(_cursor, "dates");
          final int _cursorIndexOfDailyPrice = CursorUtil.getColumnIndexOrThrow(_cursor, "dailyPrice");
          final int _cursorIndexOfTotalDays = CursorUtil.getColumnIndexOrThrow(_cursor, "totalDays");
          final int _cursorIndexOfSupplyPrice = CursorUtil.getColumnIndexOrThrow(_cursor, "supplyPrice");
          final int _cursorIndexOfPlatformFee = CursorUtil.getColumnIndexOrThrow(_cursor, "platformFee");
          final int _cursorIndexOfTotalPrice = CursorUtil.getColumnIndexOrThrow(_cursor, "totalPrice");
          final int _cursorIndexOfEscrowStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "escrowStatus");
          final int _cursorIndexOfJourneyStep = CursorUtil.getColumnIndexOrThrow(_cursor, "journeyStep");
          final int _cursorIndexOfShareToken = CursorUtil.getColumnIndexOrThrow(_cursor, "shareToken");
          final int _cursorIndexOfIsReviewed = CursorUtil.getColumnIndexOrThrow(_cursor, "isReviewed");
          final int _cursorIndexOfRatingGiven = CursorUtil.getColumnIndexOrThrow(_cursor, "ratingGiven");
          final int _cursorIndexOfReviewComment = CursorUtil.getColumnIndexOrThrow(_cursor, "reviewComment");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final ContractEntity _result;
          if (_cursor.moveToFirst()) {
            final long _tmpContractId;
            _tmpContractId = _cursor.getLong(_cursorIndexOfContractId);
            final long _tmpRequestId;
            _tmpRequestId = _cursor.getLong(_cursorIndexOfRequestId);
            final long _tmpBidId;
            _tmpBidId = _cursor.getLong(_cursorIndexOfBidId);
            final String _tmpCaregiverId;
            _tmpCaregiverId = _cursor.getString(_cursorIndexOfCaregiverId);
            final String _tmpCaregiverName;
            _tmpCaregiverName = _cursor.getString(_cursorIndexOfCaregiverName);
            final String _tmpGuardianName;
            _tmpGuardianName = _cursor.getString(_cursorIndexOfGuardianName);
            final String _tmpLocation;
            _tmpLocation = _cursor.getString(_cursorIndexOfLocation);
            final String _tmpDates;
            _tmpDates = _cursor.getString(_cursorIndexOfDates);
            final int _tmpDailyPrice;
            _tmpDailyPrice = _cursor.getInt(_cursorIndexOfDailyPrice);
            final int _tmpTotalDays;
            _tmpTotalDays = _cursor.getInt(_cursorIndexOfTotalDays);
            final int _tmpSupplyPrice;
            _tmpSupplyPrice = _cursor.getInt(_cursorIndexOfSupplyPrice);
            final int _tmpPlatformFee;
            _tmpPlatformFee = _cursor.getInt(_cursorIndexOfPlatformFee);
            final int _tmpTotalPrice;
            _tmpTotalPrice = _cursor.getInt(_cursorIndexOfTotalPrice);
            final EscrowStatus _tmpEscrowStatus;
            final String _tmp;
            _tmp = _cursor.getString(_cursorIndexOfEscrowStatus);
            _tmpEscrowStatus = __careConverters.toEscrowStatus(_tmp);
            final JourneyStep _tmpJourneyStep;
            final String _tmp_1;
            _tmp_1 = _cursor.getString(_cursorIndexOfJourneyStep);
            _tmpJourneyStep = __careConverters.toJourneyStep(_tmp_1);
            final String _tmpShareToken;
            _tmpShareToken = _cursor.getString(_cursorIndexOfShareToken);
            final boolean _tmpIsReviewed;
            final int _tmp_2;
            _tmp_2 = _cursor.getInt(_cursorIndexOfIsReviewed);
            _tmpIsReviewed = _tmp_2 != 0;
            final float _tmpRatingGiven;
            _tmpRatingGiven = _cursor.getFloat(_cursorIndexOfRatingGiven);
            final String _tmpReviewComment;
            _tmpReviewComment = _cursor.getString(_cursorIndexOfReviewComment);
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            _result = new ContractEntity(_tmpContractId,_tmpRequestId,_tmpBidId,_tmpCaregiverId,_tmpCaregiverName,_tmpGuardianName,_tmpLocation,_tmpDates,_tmpDailyPrice,_tmpTotalDays,_tmpSupplyPrice,_tmpPlatformFee,_tmpTotalPrice,_tmpEscrowStatus,_tmpJourneyStep,_tmpShareToken,_tmpIsReviewed,_tmpRatingGiven,_tmpReviewComment,_tmpCreatedAt);
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
  public Object getContractByBidId(final long bidId,
      final Continuation<? super ContractEntity> $completion) {
    final String _sql = "SELECT * FROM contracts WHERE bidId = ? LIMIT 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, bidId);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<ContractEntity>() {
      @Override
      @Nullable
      public ContractEntity call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfContractId = CursorUtil.getColumnIndexOrThrow(_cursor, "contractId");
          final int _cursorIndexOfRequestId = CursorUtil.getColumnIndexOrThrow(_cursor, "requestId");
          final int _cursorIndexOfBidId = CursorUtil.getColumnIndexOrThrow(_cursor, "bidId");
          final int _cursorIndexOfCaregiverId = CursorUtil.getColumnIndexOrThrow(_cursor, "caregiverId");
          final int _cursorIndexOfCaregiverName = CursorUtil.getColumnIndexOrThrow(_cursor, "caregiverName");
          final int _cursorIndexOfGuardianName = CursorUtil.getColumnIndexOrThrow(_cursor, "guardianName");
          final int _cursorIndexOfLocation = CursorUtil.getColumnIndexOrThrow(_cursor, "location");
          final int _cursorIndexOfDates = CursorUtil.getColumnIndexOrThrow(_cursor, "dates");
          final int _cursorIndexOfDailyPrice = CursorUtil.getColumnIndexOrThrow(_cursor, "dailyPrice");
          final int _cursorIndexOfTotalDays = CursorUtil.getColumnIndexOrThrow(_cursor, "totalDays");
          final int _cursorIndexOfSupplyPrice = CursorUtil.getColumnIndexOrThrow(_cursor, "supplyPrice");
          final int _cursorIndexOfPlatformFee = CursorUtil.getColumnIndexOrThrow(_cursor, "platformFee");
          final int _cursorIndexOfTotalPrice = CursorUtil.getColumnIndexOrThrow(_cursor, "totalPrice");
          final int _cursorIndexOfEscrowStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "escrowStatus");
          final int _cursorIndexOfJourneyStep = CursorUtil.getColumnIndexOrThrow(_cursor, "journeyStep");
          final int _cursorIndexOfShareToken = CursorUtil.getColumnIndexOrThrow(_cursor, "shareToken");
          final int _cursorIndexOfIsReviewed = CursorUtil.getColumnIndexOrThrow(_cursor, "isReviewed");
          final int _cursorIndexOfRatingGiven = CursorUtil.getColumnIndexOrThrow(_cursor, "ratingGiven");
          final int _cursorIndexOfReviewComment = CursorUtil.getColumnIndexOrThrow(_cursor, "reviewComment");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final ContractEntity _result;
          if (_cursor.moveToFirst()) {
            final long _tmpContractId;
            _tmpContractId = _cursor.getLong(_cursorIndexOfContractId);
            final long _tmpRequestId;
            _tmpRequestId = _cursor.getLong(_cursorIndexOfRequestId);
            final long _tmpBidId;
            _tmpBidId = _cursor.getLong(_cursorIndexOfBidId);
            final String _tmpCaregiverId;
            _tmpCaregiverId = _cursor.getString(_cursorIndexOfCaregiverId);
            final String _tmpCaregiverName;
            _tmpCaregiverName = _cursor.getString(_cursorIndexOfCaregiverName);
            final String _tmpGuardianName;
            _tmpGuardianName = _cursor.getString(_cursorIndexOfGuardianName);
            final String _tmpLocation;
            _tmpLocation = _cursor.getString(_cursorIndexOfLocation);
            final String _tmpDates;
            _tmpDates = _cursor.getString(_cursorIndexOfDates);
            final int _tmpDailyPrice;
            _tmpDailyPrice = _cursor.getInt(_cursorIndexOfDailyPrice);
            final int _tmpTotalDays;
            _tmpTotalDays = _cursor.getInt(_cursorIndexOfTotalDays);
            final int _tmpSupplyPrice;
            _tmpSupplyPrice = _cursor.getInt(_cursorIndexOfSupplyPrice);
            final int _tmpPlatformFee;
            _tmpPlatformFee = _cursor.getInt(_cursorIndexOfPlatformFee);
            final int _tmpTotalPrice;
            _tmpTotalPrice = _cursor.getInt(_cursorIndexOfTotalPrice);
            final EscrowStatus _tmpEscrowStatus;
            final String _tmp;
            _tmp = _cursor.getString(_cursorIndexOfEscrowStatus);
            _tmpEscrowStatus = __careConverters.toEscrowStatus(_tmp);
            final JourneyStep _tmpJourneyStep;
            final String _tmp_1;
            _tmp_1 = _cursor.getString(_cursorIndexOfJourneyStep);
            _tmpJourneyStep = __careConverters.toJourneyStep(_tmp_1);
            final String _tmpShareToken;
            _tmpShareToken = _cursor.getString(_cursorIndexOfShareToken);
            final boolean _tmpIsReviewed;
            final int _tmp_2;
            _tmp_2 = _cursor.getInt(_cursorIndexOfIsReviewed);
            _tmpIsReviewed = _tmp_2 != 0;
            final float _tmpRatingGiven;
            _tmpRatingGiven = _cursor.getFloat(_cursorIndexOfRatingGiven);
            final String _tmpReviewComment;
            _tmpReviewComment = _cursor.getString(_cursorIndexOfReviewComment);
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            _result = new ContractEntity(_tmpContractId,_tmpRequestId,_tmpBidId,_tmpCaregiverId,_tmpCaregiverName,_tmpGuardianName,_tmpLocation,_tmpDates,_tmpDailyPrice,_tmpTotalDays,_tmpSupplyPrice,_tmpPlatformFee,_tmpTotalPrice,_tmpEscrowStatus,_tmpJourneyStep,_tmpShareToken,_tmpIsReviewed,_tmpRatingGiven,_tmpReviewComment,_tmpCreatedAt);
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
}
