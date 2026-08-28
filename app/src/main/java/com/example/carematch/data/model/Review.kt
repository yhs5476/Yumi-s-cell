package com.example.carematch.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

@Serializable
@Entity(tableName = "caregiver_reviews")
data class Review(
    @PrimaryKey(autoGenerate = true)
    val reviewId: Long = 0,
    val caregiverId: Long,
    val guardianName: String,
    val rating: Float = 5.0f,
    val date: String,
    val content: String,
    val patientCondition: String,
    val period: String = "2주간 간병"
)

@Serializable
@Entity(tableName = "alimtalk_notifications")
data class NotificationItem(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val title: String,
    val message: String,
    val timestamp: Long = System.currentTimeMillis(),
    val type: String, // "REQUEST_SENT", "REQUEST_RECEIVED", "REQUEST_ACCEPTED", "REQUEST_REJECTED"
    val relatedRequestId: Long? = null,
    val isRead: Boolean = false
)
