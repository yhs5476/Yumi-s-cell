package com.example.carematch.data.local

import com.example.carematch.data.model.CareRequest
import com.example.carematch.data.model.CaregiverProfile
import com.example.carematch.data.model.NotificationItem
import com.example.carematch.data.model.RequestStatus
import com.example.carematch.data.model.Review

object DatabaseInitializer {

    suspend fun seedDatabaseIfEmpty(database: AppDatabase) {
        val caregiverDao = database.caregiverDao()
        val careRequestDao = database.careRequestDao()
        val reviewDao = database.reviewDao()
        val notificationDao = database.notificationDao()

        if (caregiverDao.getCount() == 0) {
            val caregivers = listOf(
                CaregiverProfile(
                    profileId = 1,
                    userId = "caregiver_park_soyeon",
                    name = "박소연",
                    avatarUrl = "https://images.unsplash.com/photo-1594824813589-32219d27376c?auto=format&fit=crop&w=400&q=80",
                    gender = "여성",
                    age = 53,
                    rating = 4.95,
                    reviewCount = 42,
                    completedCases = 89,
                    experienceYears = 8,
                    location = "서울 강남구/서초구/송파구",
                    desiredDailyPay = 160000,
                    certifications = listOf("요양보호사 1급", "간호조무사", "치매전문교육 이수", "심폐소생술(CPR)"),
                    specialties = listOf("석션(가래흡인)", "와상 환자 케어", "치매 전문 돌봄", "욕창 집중 관리", "콧줄(L-tube) 피딩", "재활운동 보조"),
                    bio = "대학병원 중환자실 및 신경외과 간병 8년 경력, 어르신을 내 가족처럼 따뜻하게 모십니다.",
                    detailedIntroduction = "안녕하세요! 요양보호사 1급 및 간호조무사 자격을 보유한 간병인 박소연입니다.\n\n주요 경력:\n- 서울아산병원/삼성서울병원 뇌신경계 및 정형외과 집중 간병 8년\n- 치매 3등급~1등급 전문 케어 및 낙상 예방 훈련 수료\n- 욕창 2~3단계 소독 및 체위 변경(2시간 간격) 숙련\n\n항상 밝은 미소와 정직함으로 환자분의 편안한 회복을 돕겠습니다. 24시간 입주 및 주간 돌봄 모두 성심껏 진행합니다.",
                    availableSchedule = "즉시 가능 (입주/병원/자택)",
                    isActive = true,
                    phone = "010-3849-2910",
                    badges = listOf("우수 간병인", "신원 확인", "자격증 검증", "배상책임보험")
                ),
                CaregiverProfile(
                    profileId = 2,
                    userId = "caregiver_kim_hyunjung",
                    name = "김현정",
                    avatarUrl = "https://images.unsplash.com/photo-1559839734-2b71ea197ec2?auto=format&fit=crop&w=400&q=80",
                    gender = "여성",
                    age = 48,
                    rating = 4.88,
                    reviewCount = 31,
                    completedCases = 62,
                    experienceYears = 5,
                    location = "경기 성남시/분당구/용인시",
                    desiredDailyPay = 150000,
                    certifications = listOf("요양보호사 1급", "병원코디네이터", "노인심리상담사"),
                    specialties = listOf("정형외과 수술 후 회복", "보행 및 재활 보조", "식사 영양 관리", "인지 강화 활동", "기저귀 케어"),
                    bio = "친절하고 세심한 성격으로 고관절 수술 및 재활 환자 케어에 특화되어 있습니다.",
                    detailedIntroduction = "분당서울대병원 및 재활병원에서 다년간 근무하며 어르신들의 일상 복귀를 도와왔습니다. 밝은 에너지로 환자분의 우울감을 덜어드리고, 영양가 있는 맞춤 식단 보조와 꼼꼼한 관절 운동 보조를 제공합니다.",
                    availableSchedule = "평일/주말 상시 가능",
                    isActive = true,
                    phone = "010-4491-8832",
                    badges = listOf("자격증 검증", "본인 인증", "친절 우수")
                ),
                CaregiverProfile(
                    profileId = 3,
                    userId = "caregiver_lee_jungwoo",
                    name = "이정우",
                    avatarUrl = "https://images.unsplash.com/photo-1622253692010-333f2da6031d?auto=format&fit=crop&w=400&q=80",
                    gender = "남성",
                    age = 51,
                    rating = 4.92,
                    reviewCount = 26,
                    completedCases = 54,
                    experienceYears = 7,
                    location = "서울 영등포구/구로구/마포구",
                    desiredDailyPay = 170000,
                    certifications = listOf("요양보호사 1급", "물리치료보조", "응급처치 강사증"),
                    specialties = listOf("남성 고체중 환자 이동", "편마비 체위변경", "석션(가래흡인)", "와상 환자 전신 목욕", "24시간 입주"),
                    bio = "체격이 크시거나 거동이 전혀 안 되시는 중증 환자분도 안전하고 든든하게 케어합니다.",
                    detailedIntroduction = "남성 간병인으로서 체력이 필요한 와상 환자 전신 목욕, 휠체어 리프트 및 2시간 주기 체위 변경에 많은 노하우가 있습니다. 성인 남성 환자나 체중이 나가시는 어르신 전문입니다.",
                    availableSchedule = "24시간 입주 간병 즉시 가능",
                    isActive = true,
                    phone = "010-9123-5582",
                    badges = listOf("우수 간병인", "전문 체위변경", "신원 확인")
                ),
                CaregiverProfile(
                    profileId = 4,
                    userId = "caregiver_jung_myungsook",
                    name = "정명숙",
                    avatarUrl = "https://images.unsplash.com/photo-1573496359142-b8d87734a5a2?auto=format&fit=crop&w=400&q=80",
                    gender = "여성",
                    age = 57,
                    rating = 4.82,
                    reviewCount = 38,
                    completedCases = 78,
                    experienceYears = 10,
                    location = "서울 종로구/중구/동대문구",
                    desiredDailyPay = 140000,
                    certifications = listOf("요양보호사 1급", "호스피스 전문과정 수료"),
                    specialties = listOf("말기 암 환자 호스피스", "통증 완화 마사지", "정서적 지지", "콧줄/소변줄 관리", "24시간 간병"),
                    bio = "10년 경력의 베테랑 간병인, 환자분의 마음을 보듬는 섬세한 케어를 약속드립니다.",
                    detailedIntroduction = "국립암센터 및 서울대병원 암병동에서 오랜 기간 근무했습니다. 통증으로 힘들어하시는 환자분들의 손발 마사지와 정서적 안정을 최우선으로 돌봅니다.",
                    availableSchedule = "상시 협의 가능",
                    isActive = true,
                    phone = "010-6712-3341",
                    badges = listOf("10년 베테랑", "자격증 검증", "호스피스 이수")
                ),
                CaregiverProfile(
                    profileId = 5,
                    userId = "caregiver_choi_eunji",
                    name = "최은지",
                    avatarUrl = "https://images.unsplash.com/photo-1582750433449-648ed127bb54?auto=format&fit=crop&w=400&q=80",
                    gender = "여성",
                    age = 45,
                    rating = 4.97,
                    reviewCount = 19,
                    completedCases = 35,
                    experienceYears = 4,
                    location = "인천 남동구/부평구/연수구",
                    desiredDailyPay = 145000,
                    certifications = listOf("요양보호사 1급", "사회복지사 2급"),
                    specialties = listOf("치매 경증/중등도", "인지 프로그램 진행", "병원 동행", "복약 지도", "식사 보조"),
                    bio = "따뜻하고 꼼꼼한 성격, 매일 돌봄 일지를 작성하여 보호자님께 투명하게 공유합니다.",
                    detailedIntroduction = "사회복지사 자격을 함께 갖추고 있어 어르신과의 교감 및 인지 유지 활동에 강점이 있습니다. 매일 혈압, 체온, 식사량, 배변 상태를 사진과 함께 기록해 드립니다.",
                    availableSchedule = "주간/야간/단기 가능",
                    isActive = true,
                    phone = "010-8234-1192",
                    badges = listOf("돌봄일지 제공", "본인 인증", "친절 우수")
                ),
                CaregiverProfile(
                    profileId = 6,
                    userId = "caregiver_han_sangchul",
                    name = "한상철",
                    avatarUrl = "https://images.unsplash.com/photo-1537368910025-700350fe46c7?auto=format&fit=crop&w=400&q=80",
                    gender = "남성",
                    age = 56,
                    rating = 4.89,
                    reviewCount = 22,
                    completedCases = 49,
                    experienceYears = 6,
                    location = "부산 해운대구/수영구/동래구",
                    desiredDailyPay = 155000,
                    certifications = listOf("요양보호사 1급", "안전관리사"),
                    specialties = listOf("뇌출혈 재활", "침상 목욕", "석션(가래흡인)", "야간 돌봄", "낙상 예방"),
                    bio = "부산 및 경남권 병원/자택 전문, 책임감과 성실함으로 신뢰를 쌓아왔습니다.",
                    detailedIntroduction = "부산대병원, 동아대병원에서 중증 환자 간병 경험이 풍부합니다. 위급 상황 대처 능력이 뛰어나며 밤낮으로 안심하실 수 있도록 성심을 다합니다.",
                    availableSchedule = "즉시 가능",
                    isActive = true,
                    phone = "010-7731-9042",
                    badges = listOf("자격증 검증", "신원 확인")
                )
            )
            caregiverDao.insertCaregivers(caregivers)

            // Seed Reviews
            val reviews = listOf(
                Review(
                    reviewId = 1,
                    caregiverId = 1,
                    guardianName = "김*수 보호자",
                    rating = 5.0f,
                    date = "2026.08.15",
                    content = "어머니가 뇌경색으로 쓰러지셔서 경황이 없었는데, 박소연 간병인님께서 석션도 너무 침착하게 잘 해주시고 2시간마다 체위 변경을 꼼꼼히 해주셔서 욕창 하나 없이 퇴원하셨습니다. 정말 감사드립니다.",
                    patientCondition = "뇌경색 와상 환자 (석션/L-tube)",
                    period = "3주 입주 간병"
                ),
                Review(
                    reviewId = 2,
                    caregiverId = 1,
                    guardianName = "이*진 보호자",
                    rating = 5.0f,
                    date = "2026.07.28",
                    content = "환자 상태가 까다로웠음에도 언제나 웃으시며 대해주셔서 큰 위로가 되었습니다. 베스트 간병인 인정합니다!",
                    patientCondition = "고관절 골절 및 치매",
                    period = "10일간 병원 간병"
                ),
                Review(
                    reviewId = 3,
                    caregiverId = 2,
                    guardianName = "박*호 보호자",
                    rating = 5.0f,
                    date = "2026.08.02",
                    content = "수술 후 보행 연습을 너무 친절하고 무리 없이 잘 도와주셨어요. 식사도 매끼 챙겨주셔서 빠르게 회복하셨습니다.",
                    patientCondition = "인공관절 수술 후 재활",
                    period = "2주간 주간 간병"
                ),
                Review(
                    reviewId = 4,
                    caregiverId = 3,
                    guardianName = "최*원 보호자",
                    rating = 5.0f,
                    date = "2026.08.19",
                    content = "아버지 체격이 85kg이라 여자 간병인분들이 힘들어하셨는데, 이정우 선생님께서 능숙하게 자세 바꿔주시고 목욕도 시원하게 시켜주셨습니다. 최고입니다.",
                    patientCondition = "편마비 남성 환자",
                    period = "4주 입주 간병"
                )
            )
            reviewDao.insertReviews(reviews)

            // Seed Care Requests
            val sampleRequests = listOf(
                CareRequest(
                    requestId = 101,
                    guardianId = "user_guardian_1",
                    guardianName = "김민준 보호자",
                    guardianPhone = "010-5821-9942",
                    caregiverId = 1,
                    caregiverName = "박소연",
                    caregiverAvatar = "https://images.unsplash.com/photo-1594824813589-32219d27376c?auto=format&fit=crop&w=400&q=80",
                    caregiverPhone = "010-3849-2910",
                    patientGender = "여성",
                    patientAgeGroup = "70대 (78세)",
                    patientDiagnosis = "뇌경색 회복기 및 우측 편마비",
                    careLevel = "와상 (침상 거동 불가) / 석션 필요",
                    locationType = "병원 (입원실)",
                    locationAddress = "서울아산병원 서관 7층 73병동",
                    careType = "24시간 입주 간병",
                    startDate = "2026.09.01",
                    endDate = "2026.09.15 (14일간)",
                    durationDays = 14,
                    offeredDailyPay = 165000,
                    totalEstimatedPay = 2310000L,
                    specialNotes = "석션 및 기저귀 케어에 능숙하신 박소연 선생님께 꼭 부탁드리고 싶습니다. 가족 면회가 제한적이어서 안심할 수 있는 분 희망합니다.",
                    status = RequestStatus.PENDING,
                    createdAt = System.currentTimeMillis() - 3600000L * 2
                ),
                CareRequest(
                    requestId = 102,
                    guardianId = "user_guardian_2",
                    guardianName = "정수진 보호자",
                    guardianPhone = "010-9912-3401",
                    caregiverId = 1,
                    caregiverName = "박소연",
                    caregiverAvatar = "https://images.unsplash.com/photo-1594824813589-32219d27376c?auto=format&fit=crop&w=400&q=80",
                    caregiverPhone = "010-3849-2910",
                    patientGender = "남성",
                    patientAgeGroup = "80대 (82세)",
                    patientDiagnosis = "치매 중기 및 식사 거부 증상",
                    careLevel = "부축 시 거동 가능",
                    locationType = "자택",
                    locationAddress = "서울시 서초구 반포자이아파트 104동",
                    careType = "주간 돌봄 (09:00 ~ 18:00)",
                    startDate = "2026.09.05",
                    endDate = "2026.09.19 (15일간)",
                    durationDays = 15,
                    offeredDailyPay = 150000,
                    totalEstimatedPay = 2250000L,
                    specialNotes = "인지 자극 활동과 정성스러운 식사 보조 부탁드립니다.",
                    status = RequestStatus.ACCEPTED,
                    createdAt = System.currentTimeMillis() - 86400000L * 3
                )
            )
            careRequestDao.insertRequests(sampleRequests)

            // Seed initial notifications
            val notifications = listOf(
                NotificationItem(
                    id = 1,
                    title = "[알림톡] 지정 간병 요청 도착",
                    message = "김민준 보호자님께서 박소연 간병인님께 [서울아산병원 / 14일간] 1:1 지정 간병 요청을 보냈습니다.",
                    timestamp = System.currentTimeMillis() - 3600000L * 2,
                    type = "REQUEST_RECEIVED",
                    relatedRequestId = 101,
                    isRead = false
                ),
                NotificationItem(
                    id = 2,
                    title = "[알림톡] 간병 매칭 성사 안내",
                    message = "정수진 보호자님, 박소연 간병인님께서 [반포동 자택] 간병 요청을 수락하셨습니다! 간병인 연락처로 통화가 가능합니다.",
                    timestamp = System.currentTimeMillis() - 86400000L * 2,
                    type = "REQUEST_ACCEPTED",
                    relatedRequestId = 102,
                    isRead = true
                )
            )
            for (noti in notifications) {
                notificationDao.insertNotification(noti)
            }
        }
    }
}
