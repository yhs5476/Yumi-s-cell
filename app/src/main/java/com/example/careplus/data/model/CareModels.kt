package com.example.careplus.data.model

import kotlinx.serialization.Serializable

enum class UserRole {
    GUARDIAN, CAREGIVER
}

enum class CareType(val title: String, val subtitle: String, val emoji: String) {
    HOSPITAL("병원 간병", "입원 환자 24시간/주간 케어", "🏥"),
    ACCOMPANIMENT("방문 동행", "외래 진료, 투석 등 시간제 동행", "🚶"),
    HOME("자택 요양", "가정 내 일상 회복 및 식사 지원", "🏠")
}

enum class Mobility(val label: String, val desc: String, val emoji: String) {
    INDEPENDENT("스스로 걸으실 수 있어요", "식사, 화장실을 혼자 이용해요", "🚶"),
    ASSISTED("부축이 필요해요", "일어설 때나 이동할 때 도움이 필요해요", "🧎"),
    WHEELCHAIR("휠체어를 타세요", "휠체어 이동 및 이승 보조가 필요해요", "🦽"),
    BEDRIDDEN("누워만 계세요 (와상)", "체위 변경과 전적인 케어가 필요해요", "🛏️")
}

enum class Consciousness(val label: String) {
    CLEAR("명료"),
    CONFUSED("혼미 · 치매"),
    UNCONSCIOUS("무의식")
}

enum class WeightRange(val label: String) {
    UNDER_50("50kg 미만"),
    FROM_50_TO_70("50 ~ 70kg"),
    FROM_70_TO_90("70 ~ 90kg"),
    OVER_90("90kg 이상")
}

enum class PatientGender(val label: String) {
    FEMALE("여성"),
    MALE("남성")
}

enum class AgeRange(val label: String) {
    AGE_50S("50대"),
    AGE_60S("60대"),
    AGE_70S("70대"),
    AGE_80S("80대 이상")
}

enum class RequestStatus {
    OPEN, MATCHED, COMPLETED, CANCELLED
}

enum class BidStatus {
    PENDING, ACCEPTED, REJECTED
}

enum class EscrowStatus(val label: String, val colorHex: Long) {
    NOT_PAID("결제 대기", 0xFF6B7684),
    HOLDING("안전 보관 중 (에스크로)", 0xFF3182F6),
    RELEASED("정산 완료", 0xFF00C473),
    REFUNDED("환불 완료", 0xFFF04452)
}

enum class JourneyStep(val stepNumber: Int, val title: String, val desc: String, val emoji: String) {
    DEPARTURE(1, "출발 완료", "동행/케어 매니저 이동 시작", "🚗"),
    HOSPITAL_ARRIVED(2, "병원 도착", "접수처 도착 및 본인 확인", "🏥"),
    TREATMENT_IN_PROGRESS(3, "진료/수납", "외래 진료 및 검사 동행", "🩺"),
    PHARMACY_VISITED(4, "약국 수령", "처방전 접수 및 복약 지도", "💊"),
    RETURNING_HOME(5, "귀가 진행", "환자 자택/병동 안전 복귀", "🏠"),
    COMPLETED(6, "케어 완료", "리포트 자동 발급 및 에스크로 정산", "✅")
}

@Serializable
data class CareReportData(
    val summary: String,
    val treatmentNotes: String,
    val nextAppointment: String,
    val medicationInfo: String,
    val guardianAlerts: String,
    val shareToken: String
)

data class BrixInfo(
    val brixScore: Float,
    val tierName: String,
    val emoji: String,
    val badgeColorHex: Long,
    val isTopTier: Boolean
)

fun getBrixInfo(score: Float): BrixInfo {
    val clamped = score.coerceIn(0.0f, 24.0f)
    return when {
        clamped >= 18.1f -> BrixInfo(clamped, "명품 샤인머스캣", "✨🍇", 0xFF8B5CF6, true)
        clamped >= 15.1f -> BrixInfo(clamped, "진한 머스캣", "🍇", 0xFF059669, false)
        clamped >= 12.0f -> BrixInfo(clamped, "달콤한 캠벨포도", "🍇", 0xFF3182F6, false)
        else -> BrixInfo(clamped, "새콤한 청포도", "🍇", 0xFF6B7684, false)
    }
}

