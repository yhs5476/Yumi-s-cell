package com.example.careplus.data.repository

import com.example.careplus.data.local.CareBidEntity
import com.example.careplus.data.local.CareDao
import com.example.careplus.data.local.CareRequestEntity
import com.example.careplus.data.local.CaregiverProfileEntity
import com.example.careplus.data.local.ChatMessageEntity
import com.example.careplus.data.local.ContractEntity
import com.example.careplus.data.model.AgeRange
import com.example.careplus.data.model.BidStatus
import com.example.careplus.data.model.CareType
import com.example.careplus.data.model.Consciousness
import com.example.careplus.data.model.EscrowStatus
import com.example.careplus.data.model.Mobility
import com.example.careplus.data.model.PatientGender
import com.example.careplus.data.model.RequestStatus
import com.example.careplus.data.model.WeightRange
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first

class CarePlusRepository(private val careDao: CareDao) {

    val allRequests: Flow<List<CareRequestEntity>> = careDao.getAllRequests()
    val allCaregivers: Flow<List<CaregiverProfileEntity>> = careDao.getAllCaregivers()
    val allContracts: Flow<List<ContractEntity>> = careDao.getAllContracts()

    fun getBidsForRequest(requestId: Long): Flow<List<CareBidEntity>> = careDao.getBidsForRequest(requestId)

    fun getMessagesForBid(bidId: Long): Flow<List<ChatMessageEntity>> = careDao.getMessagesForBid(bidId)

    suspend fun getRequestById(id: Long): CareRequestEntity? = careDao.getRequestById(id)

    suspend fun getBidById(bidId: Long): CareBidEntity? = careDao.getBidById(bidId)

    suspend fun getContractByBidId(bidId: Long): ContractEntity? = careDao.getContractByBidId(bidId)

    suspend fun getContractById(contractId: Long): ContractEntity? = careDao.getContractById(contractId)

    suspend fun createRequest(request: CareRequestEntity): Long {
        val requestId = careDao.insertRequest(request)
        // Automatically spawn matching bids from surrounding caregivers
        spawnSurroundingBids(requestId, request)
        return requestId
    }

    suspend fun submitBid(bid: CareBidEntity): Long {
        return careDao.insertBid(bid)
    }

    suspend fun updateBidStatus(bidId: Long, status: BidStatus) {
        careDao.updateBidStatus(bidId, status)
    }

    suspend fun sendMessage(message: ChatMessageEntity): Long {
        return careDao.insertMessage(message)
    }

    suspend fun createContractAndPay(
        requestId: Long,
        bidId: Long,
        caregiverId: String,
        caregiverName: String,
        guardianName: String,
        location: String,
        dates: String,
        dailyPrice: Int,
        totalDays: Int
    ): Long {
        val supplyPrice = dailyPrice * totalDays
        val platformFee = (supplyPrice * 0.05).toInt()
        val totalPrice = supplyPrice + platformFee

        val contract = ContractEntity(
            requestId = requestId,
            bidId = bidId,
            caregiverId = caregiverId,
            caregiverName = caregiverName,
            guardianName = guardianName,
            location = location,
            dates = dates,
            dailyPrice = dailyPrice,
            totalDays = totalDays,
            supplyPrice = supplyPrice,
            platformFee = platformFee,
            totalPrice = totalPrice,
            escrowStatus = EscrowStatus.HOLDING
        )
        val contractId = careDao.insertContract(contract)
        careDao.updateBidStatus(bidId, BidStatus.ACCEPTED)

        val req = careDao.getRequestById(requestId)
        if (req != null) {
            careDao.updateRequest(req.copy(status = RequestStatus.MATCHED))
        }

        // Add system message to chat
        careDao.insertMessage(
            ChatMessageEntity(
                bidId = bidId,
                senderRole = "SYSTEM",
                senderName = "포도당 안심 결제",
                content = "토스페이로 ${String.format("%,d", totalPrice)}원 결제가 완료되었습니다. 간병 종료 시까지 안심 에스크로 금고에 안전하게 보관됩니다.",
                isInvoice = false
            )
        )

        return contractId
    }

    suspend fun releaseEscrow(contractId: Long) {
        careDao.updateEscrowStatus(contractId, EscrowStatus.RELEASED)
    }

    suspend fun updateJourneyStep(contractId: Long, nextStep: com.example.careplus.data.model.JourneyStep) {
        careDao.updateJourneyStep(contractId, nextStep)
    }

    fun getCareReport(contract: ContractEntity): com.example.careplus.data.model.CareReportData {
        return com.example.careplus.data.model.CareReportData(
            summary = "오늘 외래 진료 및 검사가 안전하게 완료되었습니다. 어르신 컨디션 양호하시며 처방약 수령 후 귀가 완료되었습니다.",
            treatmentNotes = "서울아산병원 내과 외래 진료 완료. 혈액 검사 및 X-ray 경과 양호. 식사 보조 및 이동 시 부축 지원 완료.",
            nextAppointment = "2026년 9월 18일 (금) 오전 10:00 (신관 3층 내과)",
            medicationInfo = "아침/저녁 식후 30분 복용 (유제품과 동시 복용 금지)",
            guardianAlerts = "당일 수분 섭취 1.5L 권장, 야간 미열 발생 시 비상약 복용 필요",
            shareToken = contract.shareToken
        )
    }

    suspend fun submitReview(contractId: Long, rating: Float, comment: String) {
        careDao.submitReview(contractId, rating, comment)
    }

    suspend fun deleteContract(contractId: Long) {
        careDao.deleteContractById(contractId)
    }

    suspend fun completeAndResetCycle() {
        careDao.deleteAllContracts()
        careDao.deleteAllRequests()
        careDao.deleteAllBids()
    }

    private suspend fun spawnSurroundingBids(requestId: Long, req: CareRequestEntity) {
        val caregivers = careDao.getAllCaregivers().first()
        val samplePitches = listOf(
            "아산병원 및 대학병원 중환자실 6년 경력으로 석션 및 위관영양 케어에 매우 능숙합니다. 부모님처럼 정성껏 모시겠습니다.",
            "간호조무사 자격증 보유 중이며 어르신 안심 케어 배상책임보험 1억원 가입되어 있습니다. 쾌유를 위해 최선을 다하겠습니다.",
            "체위 변경 및 낙상 방지 전문 베테랑입니다. 야간 기상 케어도 수월하게 케어 가능합니다."
        )

        val prices = listOf(135000, 140000, 150000)

        caregivers.take(3).forEachIndexed { index, cg ->
            val bid = CareBidEntity(
                requestId = requestId,
                caregiverId = cg.caregiverId,
                caregiverName = cg.name,
                careerYears = cg.careerYears,
                rating = cg.rating,
                reviewCount = cg.reviewCount,
                distanceKm = cg.distanceKm + (index * 0.4),
                travelTimeMinutes = cg.travelTimeMinutes + (index * 3),
                certList = cg.certList,
                insuranceYn = cg.insuranceYn,
                vaccineYn = cg.vaccineYn,
                gender = cg.gender,
                pitchMessage = samplePitches.getOrElse(index) { "정성을 다해 모시겠습니다." },
                dailyPrice = prices.getOrElse(index) { 140000 },
                status = BidStatus.PENDING,
                createdAt = System.currentTimeMillis() - (index * 120_000L)
            )
            val bidId = careDao.insertBid(bid)

            // Initial welcoming message from the caregiver
            careDao.insertMessage(
                ChatMessageEntity(
                    bidId = bidId,
                    senderRole = "CAREGIVER",
                    senderName = cg.name,
                    content = "안녕하세요! 공고 내용 잘 확인하였습니다. ${req.hospitalName}에서 ${req.specialNeeds.joinToString(", ")} 케어 성심성의껏 지원해 드릴 수 있습니다. 궁금하신 점 편하게 말씀해 주세요.",
                    timestamp = System.currentTimeMillis() - (index * 100_000L)
                )
            )
        }
    }

    suspend fun seedInitialDataIfEmpty() {
        val existing = careDao.getAllRequests().first()
        if (existing.isNotEmpty()) return

        val sampleCaregivers = listOf(
            CaregiverProfileEntity(
                caregiverId = "cg_01",
                name = "김*순 케어메이트",
                careerYears = 6,
                rating = 4.9,
                reviewCount = 42,
                distanceKm = 2.4,
                travelTimeMinutes = 10,
                certList = listOf("요양보호사 1급", "간호조무사"),
                insuranceYn = true,
                vaccineYn = true,
                gender = PatientGender.FEMALE,
                bio = "아산병원 중환자실 및 석션 케어 경험 풍부합니다. 따뜻하고 세심하게 케어합니다.",
                brixScore = 18.8f
            ),
            CaregiverProfileEntity(
                caregiverId = "cg_02",
                name = "박*영 케어메이트",
                careerYears = 8,
                rating = 5.0,
                reviewCount = 68,
                distanceKm = 3.1,
                travelTimeMinutes = 12,
                certList = listOf("요양보호사 1급", "병원동행매니저"),
                insuranceYn = true,
                vaccineYn = true,
                gender = PatientGender.FEMALE,
                bio = "재활 운동 및 휠체어 이승 보조 전문입니다. 병원 시스템에 밝습니다.",
                brixScore = 19.2f
            ),
            CaregiverProfileEntity(
                caregiverId = "cg_03",
                name = "이*철 케어메이트",
                careerYears = 5,
                rating = 4.8,
                reviewCount = 29,
                distanceKm = 4.5,
                travelTimeMinutes = 16,
                certList = listOf("요양보호사 1급", "응급처치사"),
                insuranceYn = true,
                vaccineYn = true,
                gender = PatientGender.MALE,
                bio = "체격 있으신 어르신 체위 변경 및 와상 간병에 특화되어 있습니다.",
                brixScore = 16.5f
            ),
            CaregiverProfileEntity(
                caregiverId = "cg_04",
                name = "최*희 케어메이트",
                careerYears = 4,
                rating = 4.9,
                reviewCount = 31,
                distanceKm = 5.2,
                travelTimeMinutes = 18,
                certList = listOf("간호조무사"),
                insuranceYn = true,
                vaccineYn = true,
                gender = PatientGender.FEMALE,
                bio = "위관영양(콧줄) 및 도뇨관 소변줄 청결 케어 전문입니다.",
                brixScore = 14.2f
            )
        )
        careDao.insertCaregivers(sampleCaregivers)

        val defaultRequest = CareRequestEntity(
            id = 1,
            guardianName = "김민준 보호자",
            location = "서울시 송파구 잠실동 올림픽로 43",
            hospitalName = "서울아산병원 신관 7층",
            careType = CareType.HOSPITAL,
            mobility = Mobility.ASSISTED,
            consciousness = Consciousness.CLEAR,
            weightRange = WeightRange.FROM_50_TO_70,
            gender = PatientGender.FEMALE,
            ageRange = AgeRange.AGE_70S,
            specialNeeds = listOf("석션(가래흡인)", "기저귀 케어", "체위 변경"),
            startDate = "2026.09.01",
            endDate = "2026.09.04",
            totalDays = 3,
            status = RequestStatus.OPEN,
            createdAt = System.currentTimeMillis() - 1000 * 60 * 25
        )
        careDao.insertRequest(defaultRequest)

        val defaultBids = listOf(
            CareBidEntity(
                bidId = 1,
                requestId = 1,
                caregiverId = "cg_01",
                caregiverName = "김*순 케어메이트",
                careerYears = 6,
                rating = 4.9,
                reviewCount = 42,
                distanceKm = 2.4,
                travelTimeMinutes = 10,
                certList = listOf("요양보호사 1급", "간호조무사"),
                insuranceYn = true,
                vaccineYn = true,
                gender = PatientGender.FEMALE,
                pitchMessage = "아산병원 중환자실 및 석션 케어 경험 풍부합니다. 정성껏 돌보겠습니다.",
                dailyPrice = 140000,
                brixScore = 18.8f,
                status = BidStatus.PENDING,
                createdAt = System.currentTimeMillis() - 1000 * 60 * 18
            ),
            CareBidEntity(
                bidId = 2,
                requestId = 1,
                caregiverId = "cg_02",
                caregiverName = "박*영 케어메이트",
                careerYears = 8,
                rating = 5.0,
                reviewCount = 68,
                distanceKm = 3.1,
                travelTimeMinutes = 12,
                certList = listOf("요양보호사 1급", "병원동행매니저"),
                insuranceYn = true,
                vaccineYn = true,
                gender = PatientGender.FEMALE,
                pitchMessage = "부축 및 침상 운동 케어 능숙하며 식사 보조와 기저귀 케어 깔끔하게 진행합니다.",
                dailyPrice = 135000,
                brixScore = 19.2f,
                status = BidStatus.PENDING,
                createdAt = System.currentTimeMillis() - 1000 * 60 * 12
            ),
            CareBidEntity(
                bidId = 3,
                requestId = 1,
                caregiverId = "cg_03",
                caregiverName = "이*철 케어메이트",
                careerYears = 5,
                rating = 4.8,
                reviewCount = 29,
                distanceKm = 4.5,
                travelTimeMinutes = 16,
                certList = listOf("요양보호사 1급", "응급처치사"),
                insuranceYn = true,
                vaccineYn = true,
                gender = PatientGender.MALE,
                pitchMessage = "체위 변경과 야간 응급 대처 확실히 해드립니다. 3일 연속 밀착 케어 가능합니다.",
                dailyPrice = 150000,
                brixScore = 16.5f,
                status = BidStatus.PENDING,
                createdAt = System.currentTimeMillis() - 1000 * 60 * 5
            )
        )
        careDao.insertBids(defaultBids)

        // Seed chat for bid 1
        val initialMessages = listOf(
            ChatMessageEntity(
                messageId = 1,
                bidId = 1,
                senderRole = "CAREGIVER",
                senderName = "김*순 케어메이트",
                content = "안녕하세요 보호자님! 서울아산병원 신관 7층 공고 보고 연락드렸습니다. 석션 및 기저귀 케어 오랜 경험이 있어 안심하고 맡기실 수 있습니다.",
                timestamp = System.currentTimeMillis() - 1000 * 60 * 15
            ),
            ChatMessageEntity(
                messageId = 2,
                bidId = 1,
                senderRole = "GUARDIAN",
                senderName = "김민준 보호자",
                content = "안녕하세요 케어메이트님. 어머님께서 가래 흡인이 하루 3~4회 필요한데 가능하신가요?",
                timestamp = System.currentTimeMillis() - 1000 * 60 * 10
            ),
            ChatMessageEntity(
                messageId = 3,
                bidId = 1,
                senderRole = "CAREGIVER",
                senderName = "김*순 케어메이트",
                content = "네, 간호조무사 자격증도 보유하고 있어 위생적이고 능숙하게 석션 케어 해드립니다. 일정 조율 후 견적서 발행해 드릴까요?",
                timestamp = System.currentTimeMillis() - 1000 * 60 * 6
            )
        )
        initialMessages.forEach { careDao.insertMessage(it) }

        // Seed sample active contract for immediate Door-to-Door Journey testing
        val defaultContract = ContractEntity(
            contractId = 1,
            requestId = 1,
            bidId = 1,
            caregiverId = "cg_01",
            caregiverName = "김*순 케어메이트",
            guardianName = "김민준 보호자",
            location = "서울아산병원 신관 7층",
            dates = "2026.09.01 ~ 2026.09.04",
            dailyPrice = 140000,
            totalDays = 3,
            supplyPrice = 420000,
            platformFee = 21000,
            totalPrice = 441000,
            escrowStatus = EscrowStatus.HOLDING,
            journeyStep = com.example.careplus.data.model.JourneyStep.TREATMENT_IN_PROGRESS
        )
        careDao.insertContract(defaultContract)
    }
}
