package com.example.carematch.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

enum class RequestStatus(val label: String) {
    PENDING("수락 대기중"),
    ACCEPTED("매칭 완료 (수락됨)"),
    REJECTED("거절됨"),
    COMPLETED("돌봄 종료")
}

@Serializable
@Entity(tableName = "care_requests")
data class CareRequest(
    @PrimaryKey(autoGenerate = true)
    val requestId: Long = 0,
    val guardianId: String = "user_guardian_1",
    val guardianName: String = "김민준 보호자",
    val guardianPhone: String = "010-5821-9942",
    val caregiverId: Long,
    val caregiverName: String,
    val caregiverAvatar: String = "",
    val caregiverPhone: String = "010-3849-2910",
    val patientGender: String = "여성",
    val patientAgeGroup: String = "70대 (78세)",
    val patientDiagnosis: String = "뇌경색 회복기 및 편마비",
    val careLevel: String = "부축 시 거동 가능 / 휠체어 이용",
    val locationType: String = "병원 (입원실)",
    val locationAddress: String = "서울아산병원 서관 7층 73병동",
    val careType: String = "24시간 입주 간병",
    val startDate: String = "2026.09.01",
    val endDate: String = "2026.09.15 (14일간)",
    val durationDays: Int = 14,
    val offeredDailyPay: Int = 160000,
    val totalEstimatedPay: Long = 2240000L,
    val specialNotes: String = "식사 보조 및 재활 휠체어 이동 시 낙상 주의 부탁드립니다. 마음이 따뜻하신 분 희망합니다.",
    val status: RequestStatus = RequestStatus.PENDING,
    val rejectionReason: String? = null,
    val createdAt: Long = System.currentTimeMillis(),
    val updatedAt: Long = System.currentTimeMillis()
)
