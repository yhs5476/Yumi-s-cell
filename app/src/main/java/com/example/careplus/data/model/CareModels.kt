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
