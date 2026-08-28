package com.example.carematch.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

enum class UserRole(val displayName: String, val description: String) {
    GUARDIAN("보호자", "환자 맞춤 간병인을 찾고 신청합니다"),
    CAREGIVER("간병인/요양보호사", "프로필을 등록하고 간병 요청을 받습니다")
}

@Serializable
@Entity(tableName = "caregiver_profiles")
data class CaregiverProfile(
    @PrimaryKey(autoGenerate = true)
    val profileId: Long = 0,
    val userId: String,
    val name: String,
    val avatarUrl: String = "",
    val gender: String = "여성",
    val age: Int = 54,
    val rating: Double = 4.9,
    val reviewCount: Int = 28,
    val completedCases: Int = 64,
    val experienceYears: Int = 6,
    val location: String = "서울 강남구/서초구",
    val desiredDailyPay: Int = 150000,
    val certifications: List<String> = emptyList(),
    val specialties: List<String> = emptyList(),
    val bio: String = "",
    val detailedIntroduction: String = "",
    val availableSchedule: String = "즉시 시작 가능 (입주/주간 협의)",
    val isActive: Boolean = true,
    val phone: String = "010-3849-2910",
    val badges: List<String> = listOf("자격증 검증", "본인 인증", "배상보험 가입")
)
