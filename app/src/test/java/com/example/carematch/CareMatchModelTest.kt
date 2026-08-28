package com.example.carematch

import com.example.carematch.data.model.CareRequest
import com.example.carematch.data.model.CaregiverProfile
import com.example.carematch.data.model.RequestStatus
import com.example.carematch.data.model.UserRole
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Test

class CareMatchModelTest {

    @Test
    fun testCaregiverProfileCreation() {
        val profile = CaregiverProfile(
            profileId = 1L,
            name = "박소연",
            gender = "여성",
            age = 52,
            experienceYears = 8,
            location = "서울 송파구",
            desiredDailyPay = 140000,
            rating = 4.95f,
            reviewCount = 42,
            completedCases = 156,
            certifications = listOf("요양보호사 1급", "간호조무사"),
            specialties = listOf("석션(가래흡인)", "치매 전문 돌봄", "와상 환자 케어"),
            bio = "8년 경력의 정성 돌봄 요양보호사",
            detailedIntroduction = "소통과 신뢰를 가장 중요하게 생각합니다.",
            badges = listOf("우수 간병인", "보건복지부 인증"),
            isActive = true
        )

        assertEquals("박소연", profile.name)
        assertEquals(140000, profile.desiredDailyPay)
        assertTrue(profile.certifications.contains("요양보호사 1급"))
        assertTrue(profile.specialties.contains("석션(가래흡인)"))
        assertEquals(3, profile.specialties.size)
    }

    @Test
    fun testCareRequestCalculation() {
        val dailyPay = 150000
        val durationDays = 14
        val totalPay = dailyPay.toLong() * durationDays

        val request = CareRequest(
            requestId = 1001L,
            guardianId = "guardian_kim",
            guardianName = "김지훈",
            guardianPhone = "010-9876-5432",
            caregiverId = 1L,
            caregiverName = "박소연",
            caregiverPhone = "010-1234-5678",
            patientGender = "여성",
            patientAgeGroup = "80대",
            patientDiagnosis = "뇌졸중 / 편마비",
            careLevel = "와상 (침상 거동 불가)",
            locationType = "병원 (입원실)",
            locationAddress = "서울아산병원 동관 8층 82병동",
            careType = "24시간 입주 간병",
            startDate = "2026.09.01",
            endDate = "2026.09.15",
            offeredDailyPay = dailyPay,
            totalEstimatedPay = totalPay,
            specialNotes = "석션 관리 능숙하신 분 희망합니다.",
            status = RequestStatus.PENDING
        )

        assertEquals(2100000L, request.totalEstimatedPay)
        assertEquals(RequestStatus.PENDING, request.status)
        assertEquals("수락 대기중", request.status.label)
    }

    @Test
    fun testStatusTransitions() {
        var status = RequestStatus.PENDING
        assertEquals("수락 대기중", status.label)

        status = RequestStatus.ACCEPTED
        assertEquals("매칭 완료", status.label)

        status = RequestStatus.REJECTED
        assertEquals("요청 거절", status.label)
    }

    @Test
    fun testUserRoleSwitching() {
        var role = UserRole.GUARDIAN
        assertEquals("보호자", role.title)

        role = UserRole.CAREGIVER
        assertEquals("간병인", role.title)
    }
}
