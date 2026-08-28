package com.example.carematch.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.carematch.data.model.CareRequest
import com.example.carematch.data.model.CaregiverProfile
import com.example.carematch.data.model.NotificationItem
import com.example.carematch.data.model.Review
import com.example.carematch.data.model.UserRole
import com.example.carematch.data.repository.CareMatchRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

enum class SortOption(val label: String) {
    RECOMMENDED("추천순"),
    EXPERIENCE_DESC("경력 많은순"),
    PAY_ASC("일당 낮은순"),
    RATING_DESC("평점 높은순")
}

data class FilterState(
    val searchQuery: String = "",
    val selectedRegion: String = "전체",
    val selectedSpecialties: Set<String> = emptySet(),
    val selectedCertifications: Set<String> = emptySet(),
    val maxDailyPay: Int = 200000,
    val sortOption: SortOption = SortOption.RECOMMENDED
)

data class RequestFormState(
    val patientGender: String = "여성",
    val patientAgeGroup: String = "70대",
    val patientDiagnosis: String = "뇌졸중 / 편마비",
    val careLevel: String = "부축 시 거동 가능 / 휠체어 이용",
    val locationType: String = "병원 (입원실)",
    val locationAddress: String = "서울아산병원 서관 7층 73병동",
    val careType: String = "24시간 입주 간병",
    val startDate: String = "2026.09.01",
    val endDate: String = "2026.09.15",
    val durationDays: Int = 14,
    val offeredDailyPay: Int = 160000,
    val specialNotes: String = "",
    val guardianName: String = "김민준 보호자",
    val guardianPhone: String = "010-5821-9942"
)

class CareMatchViewModel(private val repository: CareMatchRepository) : ViewModel() {

    // Current Role
    val currentRole: StateFlow<UserRole> = repository.currentUserRole
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), UserRole.GUARDIAN)

    // Filter & Search State
    private val _filterState = MutableStateFlow(FilterState())
    val filterState: StateFlow<FilterState> = _filterState

    // Raw Caregivers from DB
    private val rawCaregivers = repository.getAllActiveCaregivers()

    // Filtered Caregivers
    val caregivers: StateFlow<List<CaregiverProfile>> = combine(
        rawCaregivers,
        _filterState
    ) { list, filter ->
        list.filter { caregiver ->
            val matchQuery = if (filter.searchQuery.isBlank()) true else {
                caregiver.name.contains(filter.searchQuery, ignoreCase = true) ||
                        caregiver.location.contains(filter.searchQuery, ignoreCase = true) ||
                        caregiver.bio.contains(filter.searchQuery, ignoreCase = true) ||
                        caregiver.specialties.any { it.contains(filter.searchQuery, ignoreCase = true) }
            }

            val matchRegion = if (filter.selectedRegion == "전체") true else {
                caregiver.location.contains(filter.selectedRegion.replace("서울 ", "").replace("경기 ", ""))
            }

            val matchSpecialties = if (filter.selectedSpecialties.isEmpty()) true else {
                filter.selectedSpecialties.all { spec ->
                    caregiver.specialties.any { it.contains(spec.take(3)) }
                }
            }

            val matchCertifications = if (filter.selectedCertifications.isEmpty()) true else {
                filter.selectedCertifications.all { cert ->
                    caregiver.certifications.contains(cert)
                }
            }

            val matchPay = caregiver.desiredDailyPay <= filter.maxDailyPay

            matchQuery && matchRegion && matchSpecialties && matchCertifications && matchPay
        }.let { filtered ->
            when (filter.sortOption) {
                SortOption.RECOMMENDED -> filtered.sortedByDescending { it.rating * 10 + it.completedCases }
                SortOption.EXPERIENCE_DESC -> filtered.sortedByDescending { it.experienceYears }
                SortOption.PAY_ASC -> filtered.sortedBy { it.desiredDailyPay }
                SortOption.RATING_DESC -> filtered.sortedByDescending { it.rating }
            }
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    // Selected Caregiver Profile for Detail
    private val _selectedCaregiverId = MutableStateFlow<Long?>(null)
    val selectedCaregiver: StateFlow<CaregiverProfile?> = _selectedCaregiverId
        .combine(repository.getAllCaregivers()) { id, list ->
            list.find { it.profileId == id } ?: list.firstOrNull()
        }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), null)

    // Reviews for Selected Caregiver
    val selectedCaregiverReviews: StateFlow<List<Review>> = _selectedCaregiverId
        .combine(repository.getAllCaregivers()) { id, _ -> id }
        .combine(repository.getReviewsForCaregiver(_selectedCaregiverId.value ?: 1L)) { _, reviews ->
            reviews
        }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    // Guardian Requests
    val guardianRequests: StateFlow<List<CareRequest>> = repository.getRequestsForGuardian()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    // Caregiver Requests
    val caregiverRequests: StateFlow<List<CareRequest>> = repository.getRequestsForCaregiver()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    // Active Caregiver Profile (For editing when in Caregiver mode)
    val myCaregiverProfile: StateFlow<CaregiverProfile?> = repository.getCaregiverById(1L)
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), null)

    // Notifications
    val notifications: StateFlow<List<NotificationItem>> = repository.getAllNotifications()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val unreadNotificationCount: StateFlow<Int> = repository.getUnreadNotificationCount()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 0)

    // Request Form State
    private val _requestForm = MutableStateFlow(RequestFormState())
    val requestForm: StateFlow<RequestFormState> = _requestForm

    // Dialog state for quick notifications popup
    val showNotificationDialog = MutableStateFlow(false)

    fun switchRole(role: UserRole) {
        repository.setRole(role)
    }

    fun updateSearchQuery(query: String) {
        _filterState.value = _filterState.value.copy(searchQuery = query)
    }

    fun setRegion(region: String) {
        _filterState.value = _filterState.value.copy(selectedRegion = region)
    }

    fun toggleSpecialty(specialty: String) {
        val current = _filterState.value.selectedSpecialties.toMutableSet()
        if (current.contains(specialty)) current.remove(specialty) else current.add(specialty)
        _filterState.value = _filterState.value.copy(selectedSpecialties = current)
    }

    fun toggleCertification(cert: String) {
        val current = _filterState.value.selectedCertifications.toMutableSet()
        if (current.contains(cert)) current.remove(cert) else current.add(cert)
        _filterState.value = _filterState.value.copy(selectedCertifications = current)
    }

    fun setMaxDailyPay(pay: Int) {
        _filterState.value = _filterState.value.copy(maxDailyPay = pay)
    }

    fun setSortOption(option: SortOption) {
        _filterState.value = _filterState.value.copy(sortOption = option)
    }

    fun resetFilters() {
        _filterState.value = FilterState()
    }

    fun selectCaregiver(caregiverId: Long) {
        _selectedCaregiverId.value = caregiverId
        // Initialize form default wage with caregiver's desired pay
        viewModelScope.launch {
            val caregiver = repository.getCaregiverById(caregiverId)
            caregiver.collect { profile ->
                if (profile != null) {
                    _requestForm.value = _requestForm.value.copy(
                        offeredDailyPay = profile.desiredDailyPay
                    )
                }
            }
        }
    }

    fun updateRequestForm(update: (RequestFormState) -> RequestFormState) {
        _requestForm.value = update(_requestForm.value)
    }

    fun submitCareRequest(caregiver: CaregiverProfile, onSuccess: (Long) -> Unit) {
        viewModelScope.launch {
            val form = _requestForm.value
            val totalPay = form.offeredDailyPay.toLong() * form.durationDays
            val request = CareRequest(
                guardianId = repository.currentGuardianId,
                guardianName = form.guardianName,
                guardianPhone = form.guardianPhone,
                caregiverId = caregiver.profileId,
                caregiverName = caregiver.name,
                caregiverAvatar = caregiver.avatarUrl,
                caregiverPhone = caregiver.phone,
                patientGender = form.patientGender,
                patientAgeGroup = form.patientAgeGroup,
                patientDiagnosis = form.patientDiagnosis,
                careLevel = form.careLevel,
                locationType = form.locationType,
                locationAddress = form.locationAddress,
                careType = form.careType,
                startDate = form.startDate,
                endDate = form.endDate,
                durationDays = form.durationDays,
                offeredDailyPay = form.offeredDailyPay,
                totalEstimatedPay = totalPay,
                specialNotes = form.specialNotes
            )
            val newId = repository.submitCareRequest(request)
            onSuccess(newId)
        }
    }

    fun acceptRequest(requestId: Long) {
        viewModelScope.launch {
            repository.acceptCareRequest(requestId)
        }
    }

    fun rejectRequest(requestId: Long, reason: String) {
        viewModelScope.launch {
            repository.rejectCareRequest(requestId, reason)
        }
    }

    fun updateMyCaregiverProfile(profile: CaregiverProfile) {
        viewModelScope.launch {
            repository.updateCaregiverProfile(profile)
        }
    }

    fun toggleCaregiverActiveStatus() {
        val current = myCaregiverProfile.value ?: return
        viewModelScope.launch {
            repository.updateCaregiverProfile(current.copy(isActive = !current.isActive))
        }
    }

    fun markNotificationAsRead(id: Long) {
        viewModelScope.launch {
            repository.markNotificationAsRead(id)
        }
    }

    fun markAllNotificationsAsRead() {
        viewModelScope.launch {
            repository.markAllNotificationsAsRead()
        }
    }
}

class CareMatchViewModelFactory(
    private val repository: CareMatchRepository
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CareMatchViewModel::class.java)) {
            return CareMatchViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
