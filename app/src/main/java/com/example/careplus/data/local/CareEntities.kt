package com.example.careplus.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.careplus.data.model.AgeRange
import com.example.careplus.data.model.BidStatus
import com.example.careplus.data.model.CareType
import com.example.careplus.data.model.Consciousness
import com.example.careplus.data.model.EscrowStatus
import com.example.careplus.data.model.Mobility
import com.example.careplus.data.model.PatientGender
import com.example.careplus.data.model.RequestStatus
import com.example.careplus.data.model.WeightRange

@Entity(tableName = "care_requests")
data class CareRequestEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val guardianName: String = "김민준 보호자",
    val location: String = "서울시 송파구 올림픽로 43",
    val hospitalName: String = "서울아산병원 신관 7층",
    val careType: CareType = CareType.HOSPITAL,
    val mobility: Mobility = Mobility.ASSISTED,
    val consciousness: Consciousness = Consciousness.CLEAR,
    val weightRange: WeightRange = WeightRange.FROM_50_TO_70,
    val gender: PatientGender = PatientGender.FEMALE,
    val ageRange: AgeRange = AgeRange.AGE_70S,
    val specialNeeds: List<String> = listOf("석션(가래흡인)", "기저귀 케어"),
    val startDate: String = "2026.09.01",
    val endDate: String = "2026.09.04",
    val totalDays: Int = 3,
    val status: RequestStatus = RequestStatus.OPEN,
    val createdAt: Long = System.currentTimeMillis()
)

@Entity(tableName = "caregivers")
data class CaregiverProfileEntity(
    @PrimaryKey val caregiverId: String,
    val name: String,
    val careerYears: Int,
    val rating: Double,
    val reviewCount: Int,
    val distanceKm: Double,
    val travelTimeMinutes: Int,
    val certList: List<String>,
    val insuranceYn: Boolean,
    val vaccineYn: Boolean,
    val gender: PatientGender,
    val bio: String,
    val phoneMasked: String = "010-****-5829"
)

@Entity(tableName = "care_bids")
data class CareBidEntity(
    @PrimaryKey(autoGenerate = true) val bidId: Long = 0,
    val requestId: Long,
    val caregiverId: String,
    val caregiverName: String,
    val careerYears: Int,
    val rating: Double,
    val reviewCount: Int,
    val distanceKm: Double,
    val travelTimeMinutes: Int,
    val certList: List<String>,
    val insuranceYn: Boolean,
    val vaccineYn: Boolean,
    val gender: PatientGender,
    val pitchMessage: String,
    val dailyPrice: Int,
    val status: BidStatus = BidStatus.PENDING,
    val createdAt: Long = System.currentTimeMillis()
)

@Entity(tableName = "chat_messages")
data class ChatMessageEntity(
    @PrimaryKey(autoGenerate = true) val messageId: Long = 0,
    val bidId: Long,
    val senderRole: String, // "GUARDIAN" or "CAREGIVER"
    val senderName: String,
    val content: String,
    val isInvoice: Boolean = false,
    val invoiceTotalPrice: Int = 0,
    val invoiceDays: Int = 0,
    val timestamp: Long = System.currentTimeMillis()
)

@Entity(tableName = "contracts")
data class ContractEntity(
    @PrimaryKey(autoGenerate = true) val contractId: Long = 0,
    val requestId: Long,
    val bidId: Long,
    val caregiverId: String,
    val caregiverName: String,
    val guardianName: String,
    val location: String,
    val dates: String,
    val dailyPrice: Int,
    val totalDays: Int,
    val supplyPrice: Int,
    val platformFee: Int,
    val totalPrice: Int,
    val escrowStatus: EscrowStatus = EscrowStatus.HOLDING,
    val isReviewed: Boolean = false,
    val ratingGiven: Float = 0f,
    val reviewComment: String = "",
    val createdAt: Long = System.currentTimeMillis()
)
