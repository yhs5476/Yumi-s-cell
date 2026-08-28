# 케어매치 (CareMatch) Android App

간병인 및 요양보호사 1:1 맞춤 탐색·지정 간병 신청 플랫폼 Android 어플리케이션입니다.

## 주요 기능 (Core Features)

1. **보호자 모드 (Guardian Journey)**
   - **간병인 맞춤 탐색 & 검색**: 지역, 보유 자격증(요양보호사 1급, 간호조무사 등), 희망 일당 상한선, 전문 돌봄 분야(석션, 치매, 와상, 콧줄 등) 실시간 다중 필터링
   - **상세 프로필 & 인증 정보 확인**: 간병 경력 타임라인, 자격증 인증 뱃지, 수용 가능 환자 상태 체크리스트, 실제 보호자 평점/후기
   - **1:1 지정 간병 신청서 작성**: 환자 질환 및 거동 상태, 돌봄 장소(병원/자택), 간병 형태(24시간 입주/주간/야간), 제시 일당 및 총액 계산기, 특이사항 전달
   - **실시간 신청 내역 관리**: 간병인의 수락/거절 상태 확인, 매칭 성사 시 직통 전화/문자 연결

2. **간병인 모드 (Caregiver Journey)**
   - **받은 지정 요청 수신함**: 환자 상태, 병동 위치, 일정, 제시 일당 확인 후 원터치 [수락] / [거절]
   - **프로필 관리 & 구직 상태 토글**: 구직 등록 활성화 ON/OFF, 희망 일당 설정, 돌봄 전문 분야 및 자기소개 관리

3. **카카오 알림톡 & 알림 센터**
   - 간병 신청 도착, 매칭 수락, 거절 안내 등 알림톡 시뮬레이션 및 실시간 기록

## 기술 스택
- **Language**: Kotlin 2.1
- **UI Framework**: Jetpack Compose & Material Design 3
- **Architecture**: MVVM + Repository Pattern
- **Local Persistence**: Room Database + KSP (Kotlin Symbol Processing)
- **Image Loading**: Coil Compose
- **Navigation**: Jetpack Navigation Compose
