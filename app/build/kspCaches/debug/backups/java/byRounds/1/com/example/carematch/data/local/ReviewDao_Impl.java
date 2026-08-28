package com.example.carematch.data.local;

import android.database.Cursor;
import android.os.CancellationSignal;
import androidx.annotation.NonNull;
import androidx.room.CoroutinesRoom;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.example.carematch.data.model.Review;
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
public final class ReviewDao_Impl implements ReviewDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<Review> __insertionAdapterOfReview;

  public ReviewDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfReview = new EntityInsertionAdapter<Review>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `caregiver_reviews` (`reviewId`,`caregiverId`,`guardianName`,`rating`,`date`,`content`,`patientCondition`,`period`) VALUES (nullif(?, 0),?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final Review entity) {
        statement.bindLong(1, entity.getReviewId());
        statement.bindLong(2, entity.getCaregiverId());
        statement.bindString(3, entity.getGuardianName());
        statement.bindDouble(4, entity.getRating());
        statement.bindString(5, entity.getDate());
        statement.bindString(6, entity.getContent());
        statement.bindString(7, entity.getPatientCondition());
        statement.bindString(8, entity.getPeriod());
      }
    };
  }

  @Override
  public Object insertReview(final Review review, final Continuation<? super Long> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Long>() {
      @Override
      @NonNull
      public Long call() throws Exception {
        __db.beginTransaction();
        try {
          final Long _result = __insertionAdapterOfReview.insertAndReturnId(review);
          __db.setTransactionSuccessful();
          return _result;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object insertReviews(final List<Review> reviews,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfReview.insert(reviews);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Flow<List<Review>> getReviewsForCaregiver(final long caregiverId) {
    final String _sql = "SELECT * FROM caregiver_reviews WHERE caregiverId = ? ORDER BY reviewId DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, caregiverId);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"caregiver_reviews"}, new Callable<List<Review>>() {
      @Override
      @NonNull
      public List<Review> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfReviewId = CursorUtil.getColumnIndexOrThrow(_cursor, "reviewId");
          final int _cursorIndexOfCaregiverId = CursorUtil.getColumnIndexOrThrow(_cursor, "caregiverId");
          final int _cursorIndexOfGuardianName = CursorUtil.getColumnIndexOrThrow(_cursor, "guardianName");
          final int _cursorIndexOfRating = CursorUtil.getColumnIndexOrThrow(_cursor, "rating");
          final int _cursorIndexOfDate = CursorUtil.getColumnIndexOrThrow(_cursor, "date");
          final int _cursorIndexOfContent = CursorUtil.getColumnIndexOrThrow(_cursor, "content");
          final int _cursorIndexOfPatientCondition = CursorUtil.getColumnIndexOrThrow(_cursor, "patientCondition");
          final int _cursorIndexOfPeriod = CursorUtil.getColumnIndexOrThrow(_cursor, "period");
          final List<Review> _result = new ArrayList<Review>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final Review _item;
            final long _tmpReviewId;
            _tmpReviewId = _cursor.getLong(_cursorIndexOfReviewId);
            final long _tmpCaregiverId;
            _tmpCaregiverId = _cursor.getLong(_cursorIndexOfCaregiverId);
            final String _tmpGuardianName;
            _tmpGuardianName = _cursor.getString(_cursorIndexOfGuardianName);
            final float _tmpRating;
            _tmpRating = _cursor.getFloat(_cursorIndexOfRating);
            final String _tmpDate;
            _tmpDate = _cursor.getString(_cursorIndexOfDate);
            final String _tmpContent;
            _tmpContent = _cursor.getString(_cursorIndexOfContent);
            final String _tmpPatientCondition;
            _tmpPatientCondition = _cursor.getString(_cursorIndexOfPatientCondition);
            final String _tmpPeriod;
            _tmpPeriod = _cursor.getString(_cursorIndexOfPeriod);
            _item = new Review(_tmpReviewId,_tmpCaregiverId,_tmpGuardianName,_tmpRating,_tmpDate,_tmpContent,_tmpPatientCondition,_tmpPeriod);
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
  public Object getCount(final Continuation<? super Integer> $completion) {
    final String _sql = "SELECT COUNT(*) FROM caregiver_reviews";
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
