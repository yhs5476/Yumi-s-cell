package com.example.carematch.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.carematch.data.model.NotificationItem
import com.example.carematch.data.model.Review
import kotlinx.coroutines.flow.Flow

@Dao
interface ReviewDao {
    @Query("SELECT * FROM caregiver_reviews WHERE caregiverId = :caregiverId ORDER BY reviewId DESC")
    fun getReviewsForCaregiver(caregiverId: Long): Flow<List<Review>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertReview(review: Review): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertReviews(reviews: List<Review>)

    @Query("SELECT COUNT(*) FROM caregiver_reviews")
    suspend fun getCount(): Int
}

@Dao
interface NotificationDao {
    @Query("SELECT * FROM alimtalk_notifications ORDER BY timestamp DESC")
    fun getAllNotifications(): Flow<List<NotificationItem>>

    @Query("SELECT COUNT(*) FROM alimtalk_notifications WHERE isRead = 0")
    fun getUnreadCount(): Flow<Int>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNotification(notification: NotificationItem): Long

    @Query("UPDATE alimtalk_notifications SET isRead = 1 WHERE id = :id")
    suspend fun markAsRead(id: Long)

    @Query("UPDATE alimtalk_notifications SET isRead = 1")
    suspend fun markAllAsRead()
}
