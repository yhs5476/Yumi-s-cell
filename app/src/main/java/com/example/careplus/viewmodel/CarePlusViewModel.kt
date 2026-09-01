package com.example.careplus.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.careplus.data.local.CareBidEntity
import com.example.careplus.data.local.CareRequestEntity
import com.example.careplus.data.local.CaregiverProfileEntity
import com.example.careplus.data.local.ChatMessageEntity
import com.example.careplus.data.local.ContractEntity
import com.example.careplus.data.model.AgeRange
import com.example.careplus.data.model.BidStatus
import com.example.careplus.data.model.CareType
import com.example.careplus.data.model.Consciousness
import com.example.careplus.data.model.Mobility
import com.example.careplus.data.model.PatientGender
import com.example.careplus.data.model.RequestStatus
import com.example.careplus.data.model.UserRole
import com.example.careplus.data.model.WeightRange
import com.example.careplus.data.repository.CarePlusRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

enum class BidSortOption(val label: String) {
    LOWEST_PRICE("최저가순"),
    HIGHEST_RATING("평점 높은순"),
    NEAREST_DISTANCE("거리순"),
    MOST_EXPERIENCE("경력 많은순")
}

data class FunnelFormState(
    val step: Int = 1,
    val careType: CareType = CareType.HOSPITAL,
    val isHospital: Boolean = true,
    val hospitalName: String = "서울아산병원 신관 7층",
    val homeAddress: String = "서울시 송파구 잠실동 올림픽로 43",
    val mobility: Mobility = Mobility.ASSISTED,
    val consciousness: Consciousness = Consciousness.CLEAR,
    val weightRange: WeightRange = WeightRange.FROM_50_TO_70,
    val gender: PatientGender = PatientGender.FEMALE,
    val ageRange: AgeRange = AgeRange.AGE_70S,
    val specialNeeds: Set<String> = setOf("석션(가래흡인)", "기저귀 케어"),
    val startDate: String = "2026.09.01",
    val endDate: String = "2026.09.04",
    val totalDays: Int = 3
)

class CarePlusViewModel(private val repository: CarePlusRepository) : ViewModel() {

    private val _currentRole = MutableStateFlow(UserRole.GUARDIAN)
    val currentRole: StateFlow<UserRole> = _currentRole.asStateFlow()

    private val _currentLocation = MutableStateFlow("서울시 송파구 잠실동")
    val currentLocation: StateFlow<String> = _currentLocation.asStateFlow()

    private val _formState = MutableStateFlow(FunnelFormState())
    val formState: StateFlow<FunnelFormState> = _formState.asStateFlow()

    private val _selectedBidSort = MutableStateFlow(BidSortOption.LOWEST_PRICE)
    val selectedBidSort: StateFlow<BidSortOption> = _selectedBidSort.asStateFlow()

    private val _filterCertOnly = MutableStateFlow(false)
    val filterCertOnly: StateFlow<Boolean> = _filterCertOnly.asStateFlow()

    private val _filterInsuranceOnly = MutableStateFlow(false)
    val filterInsuranceOnly: StateFlow<Boolean> = _filterInsuranceOnly.asStateFlow()

    private val _filterSameGenderOnly = MutableStateFlow(false)
    val filterSameGenderOnly: StateFlow<Boolean> = _filterSameGenderOnly.asStateFlow()

    // Caregiver radar radius in km
    private val _caregiverRadiusKm = MutableStateFlow(10)
    val caregiverRadiusKm: StateFlow<Int> = _caregiverRadiusKm.asStateFlow()

    val allRequests: StateFlow<List<CareRequestEntity>> = repository.allRequests
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val allCaregivers: StateFlow<List<CaregiverProfileEntity>> = repository.allCaregivers
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val allContracts: StateFlow<List<ContractEntity>> = repository.allContracts
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    init {
        viewModelScope.launch {
            repository.seedInitialDataIfEmpty()
        }
    }

    fun toggleRole() {
        _currentRole.value = if (_currentRole.value == UserRole.GUARDIAN) UserRole.CAREGIVER else UserRole.GUARDIAN
    }

    fun setLocation(loc: String) {
        _currentLocation.value = loc
    }

    fun setBidSort(option: BidSortOption) {
        _selectedBidSort.value = option
    }

    fun toggleFilterCert() {
        _filterCertOnly.value = !_filterCertOnly.value
    }

    fun toggleFilterInsurance() {
        _filterInsuranceOnly.value = !_filterInsuranceOnly.value
    }

    fun toggleFilterGender() {
        _filterSameGenderOnly.value = !_filterSameGenderOnly.value
    }

    fun setCaregiverRadius(radiusKm: Int) {
        _caregiverRadiusKm.value = radiusKm
    }

    fun updateForm(update: (FunnelFormState) -> FunnelFormState) {
        _formState.value = update(_formState.value)
    }

    fun resetForm() {
        _formState.value = FunnelFormState()
    }

    fun submitCareRequest(onSuccess: (Long) -> Unit) {
        val form = _formState.value
        val entity = CareRequestEntity(
            guardianName = "김민준 보호자",
            location = if (form.isHospital) form.hospitalName else form.homeAddress,
            hospitalName = if (form.isHospital) form.hospitalName else "자택 돌봄",
            careType = form.careType,
            mobility = form.mobility,
            consciousness = form.consciousness,
            weightRange = form.weightRange,
            gender = form.gender,
            ageRange = form.ageRange,
            specialNeeds = form.specialNeeds.toList(),
            startDate = form.startDate,
            endDate = form.endDate,
            totalDays = form.totalDays,
            status = RequestStatus.OPEN
        )

        viewModelScope.launch {
            val id = repository.createRequest(entity)
            resetForm()
            onSuccess(id)
        }
    }

    fun getBidsForRequest(requestId: Long) = repository.getBidsForRequest(requestId)

    fun getMessagesForBid(bidId: Long) = repository.getMessagesForBid(bidId)

    fun sendMessage(bidId: Long, content: String, senderRole: String = "GUARDIAN", senderName: String = "김민준 보호자") {
        if (content.isBlank()) return
        viewModelScope.launch {
            repository.sendMessage(
                ChatMessageEntity(
                    bidId = bidId,
                    senderRole = senderRole,
                    senderName = senderName,
                    content = content,
                    timestamp = System.currentTimeMillis()
                )
            )

            // Auto reply simulation if guardian asks a question
            if (senderRole == "GUARDIAN") {
                launch {
                    kotlinx.coroutines.delay(1200)
                    val autoReply = when {
                        content.contains("석션") -> "네! 중환자실 경력이 있어 카테터 멸균 관리 및 주기적 가래 흡인 능숙하게 도와드립니다."
                        content.contains("기상") || content.contains("야간") -> "네, 야간에도 수면 패턴 확인하며 2시간 간격 체위 변경 및 기상 케어 문제없이 가능합니다."
                        content.contains("식사") -> "네, 연하 곤란 어르신 식사 보조 및 콧줄 피딩 모두 위생적으로 관리해 드립니다."
                        content.contains("백신") -> "네! 코로나 및 인플루엔자 예방접종 증명서 프로필에 등록되어 있으며 원본 지참 가능합니다."
                        content.contains("견적서") -> "네! 최종 합의된 조건으로 안심 견적서 즉시 발행해 드리겠습니다."
                        else -> "네, 말씀해주신 내용 꼼꼼히 확인했습니다. 편안하고 안전한 돌봄 약속드립니다."
                    }
                    repository.sendMessage(
                        ChatMessageEntity(
                            bidId = bidId,
                            senderRole = "CAREGIVER",
                            senderName = "케어메이트",
                            content = autoReply,
                            timestamp = System.currentTimeMillis()
                        )
                    )
                }
            }
        }
    }

    fun issueInvoice(bidId: Long, totalDays: Int, totalPrice: Int) {
        viewModelScope.launch {
            repository.sendMessage(
                ChatMessageEntity(
                    bidId = bidId,
                    senderRole = "CAREGIVER",
                    senderName = "케어메이트",
                    content = "최종 간병 확정서가 발행되었습니다. 아래 내역을 확인해 주세요.",
                    isInvoice = true,
                    invoiceTotalPrice = totalPrice,
                    invoiceDays = totalDays,
                    timestamp = System.currentTimeMillis()
                )
            )
        }
    }

    fun payContract(
        requestId: Long,
        bidId: Long,
        caregiverId: String,
        caregiverName: String,
        guardianName: String,
        location: String,
        dates: String,
        dailyPrice: Int,
        totalDays: Int,
        onSuccess: (Long) -> Unit
    ) {
        viewModelScope.launch {
            val contractId = repository.createContractAndPay(
                requestId = requestId,
                bidId = bidId,
                caregiverId = caregiverId,
                caregiverName = caregiverName,
                guardianName = guardianName,
                location = location,
                dates = dates,
                dailyPrice = dailyPrice,
                totalDays = totalDays
            )
            onSuccess(contractId)
        }
    }

    fun releaseEscrow(contractId: Long) {
        viewModelScope.launch {
            repository.releaseEscrow(contractId)
        }
    }

    fun submitReview(contractId: Long, rating: Float, comment: String) {
        viewModelScope.launch {
            repository.submitReview(contractId, rating, comment)
        }
    }

    fun completeAndResetCycle(onResetComplete: () -> Unit = {}) {
        viewModelScope.launch {
            repository.completeAndResetCycle()
            _formState.value = FunnelFormState()
            onResetComplete()
        }
    }

    fun updateJourneyStep(contractId: Long, nextStep: com.example.careplus.data.model.JourneyStep) {
        viewModelScope.launch {
            repository.updateJourneyStep(contractId, nextStep)
        }
    }

    fun getCareReport(contract: ContractEntity): com.example.careplus.data.model.CareReportData {
        return repository.getCareReport(contract)
    }

    fun submitCaregiverBid(
        requestId: Long,
        caregiverName: String,
        dailyPrice: Int,
        pitchMessage: String,
        onSuccess: () -> Unit
    ) {
        viewModelScope.launch {
            val bid = CareBidEntity(
                requestId = requestId,
                caregiverId = "cg_current_user",
                caregiverName = caregiverName,
                careerYears = 5,
                rating = 5.0,
                reviewCount = 18,
                distanceKm = 1.8,
                travelTimeMinutes = 8,
                certList = listOf("요양보호사 1급", "배상책임보험"),
                insuranceYn = true,
                vaccineYn = true,
                gender = PatientGender.FEMALE,
                pitchMessage = pitchMessage,
                dailyPrice = dailyPrice,
                status = BidStatus.PENDING
            )
            repository.submitBid(bid)
            onSuccess()
        }
    }
}

class CarePlusViewModelFactory(private val repository: CarePlusRepository) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CarePlusViewModel::class.java)) {
            return CarePlusViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
