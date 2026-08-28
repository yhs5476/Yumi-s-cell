package com.example.carematch.data.repository

import com.example.carematch.data.local.AppDatabase
import com.example.carematch.data.model.CareRequest
import com.example.carematch.data.model.CaregiverProfile
import com.example.carematch.data.model.NotificationItem
import com.example.carematch.data.model.RequestStatus
import com.example.carematch.data.model.Review
import com.example.carematch.data.model.UserRole
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class CareMatchRepository(private val database: AppDatabase) {

    private val caregiverDao = database.caregiverDao()
    private val careRequestDao = database.careRequestDao()
    private val reviewDao = database.reviewDao()
    private val notificationDao = database.notificationDao()

    // Current active role toggle for demonstration/testing
    private val _currentUserRole = MutableStateFlow(UserRole.GUARDIAN)
    val currentUserRole: Flow<UserRole> = _currentUserRole.asStateFlow()

    // Current user context
    val currentGuardianId = "user_guardian_1"
    val currentGuardianName = "김민준 보호자"
    val currentCaregiverId: Long = 1L // Default test caregiver profile is "박소연"

    fun setRole(role: UserRole) {
        _currentUserRole.value = role
    }

    fun getAllActiveCaregivers(): Flow<List<CaregiverProfile>> =
        caregiverDao.getAllActiveCaregivers()

    fun getAllCaregivers(): Flow<List<CaregiverProfile>> =
        caregiverDao.getAllCaregivers()

    fun getCaregiverById(profileId: Long): Flow<CaregiverProfile?> =
        caregiverDao.getCaregiverById(profileId)

    fun getCaregiverByUserId(userId: String): Flow<CaregiverProfile?> =
        caregiverDao.getCaregiverByUserId(userId)

    suspend fun updateCaregiverProfile(profile: CaregiverProfile) {
        caregiverDao.updateCaregiver(profile)
    }

    suspend fun insertCaregiverProfile(profile: CaregiverProfile): Long {
        return caregiverDao.insertCaregiver(profile)
    }

    // Care Requests
    fun getRequestsForGuardian(guardianId: String = currentGuardianId): Flow<List<CareRequest>> =
        careRequestDao.getRequestsForGuardian(guardianId)

    fun getRequestsForCaregiver(caregiverId: Long = currentCaregiverId): Flow<List<CareRequest>> =
        careRequestDao.getRequestsForCaregiver(caregiverId)

    fun getRequestById(requestId: Long): Flow<CareRequest?> =
        careRequestDao.getRequestById(requestId)

    suspend fun submitCareRequest(request: CareRequest): Long {
        val id = careRequestDao.insertRequest(request)

        // Generate simulated Kakao Alimtalk to Caregiver
        notificationDao.insertNotification(
            NotificationItem(
                title = "[카카오 알림톡] 1:1 지정 간병 요청 도착",
                message = "${request.guardianName}님께서 [${request.locationAddress} / ${request.startDate}~${request.endDate}] 간병 요청을 보냈습니다. (제시일당: ${String.format("%,d", request.offeredDailyPay)}원)",
                timestamp = System.currentTimeMillis(),
                type = "REQUEST_RECEIVED",
                relatedRequestId = id,
                isRead = false
            )
        )

        return id
    }

    suspend fun acceptCareRequest(requestId: Long) {
        careRequestDao.updateRequestStatus(requestId, RequestStatus.ACCEPTED, null)

        notificationDao.insertNotification(
            NotificationItem(
                title = "[카카오 알림톡] 간병 요청 수락 완료",
                message = "간병인께서 요청서를 수락하셨습니다! 보호자님과 간병인님의 상호 연락처가 공유되었습니다. 안전하고 편안한 돌봄이 시작됩니다.",
                timestamp = System.currentTimeMillis(),
                type = "REQUEST_ACCEPTED",
                relatedRequestId = requestId,
                isRead = false
            )
        )
    }

    suspend fun rejectCareRequest(requestId: Long, reason: String) {
        careRequestDao.updateRequestStatus(requestId, RequestStatus.REJECTED, reason)

        notificationDao.insertNotification(
            NotificationItem(
                title = "[카카오 알림톡] 간병 요청 미매칭 안내",
                message = "요청하신 일정이 간병인의 기존 일정과 중복되어 부득이하게 거절되었습니다. 사유: $reason. 다른 우수 간병인을 즉시 탐색해 보세요.",
                timestamp = System.currentTimeMillis(),
                type = "REQUEST_REJECTED",
                relatedRequestId = requestId,
                isRead = false
            )
        )
    }

    // Reviews
    fun getReviewsForCaregiver(caregiverId: Long): Flow<List<Review>> =
        reviewDao.getReviewsForCaregiver(caregiverId)

    suspend fun addReview(review: Review): Long =
        reviewDao.insertReview(review)

    // Notifications
    fun getAllNotifications(): Flow<List<NotificationItem>> =
        notificationDao.getAllNotifications()

    fun getUnreadNotificationCount(): Flow<Int> =
        notificationDao.getUnreadCount()

    suspend fun markNotificationAsRead(id: Long) =
        notificationDao.markAsRead(id)

    suspend fun markAllNotificationsAsRead() =
        notificationDao.markAllAsRead()
}
