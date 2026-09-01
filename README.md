# 포도당 (Pododang) - 안드로이드 앱

위치 기반 실시간 돌봄 입찰 및 토스 스타일 안심 간병 매칭 플랫폼

## 📱 주요 기능 (Key Features)

1. **홈 돌봄 대시보드 (Home Dashboard)**
   - 위치 기반 헤더 및 보호자/간병인 모드 간편 전환
   - 3대 돌봄 카테고리 (병원 간병, 방문 동행, 자택 요양)
   - 실시간 매칭 라이브 티커 (토스 피드 스타일)
   - 100% 실명 인증, 1억원 책임보험, 안심 에스크로 보증

2. **3분 컷 토스형 견적 요청 폼 (Funnel UX)**
   - **Step 1: 케어 장소 및 일정** (주요 대학병원 자동 선택 및 병동 설정, 자택 도로명 주소, 케어 기간)
   - **Step 2: 환자 기본 상태 (One Thing at a Time)** (거동 상태, 의식 상태, 체중 구간, 성별 및 연령대)
   - **Step 3: 필수 전문 케어** (석션, 콧줄/피딩, 소변줄, 투석, 기저귀, 체위 변경 등 태그 칩 선택)

3. **실시간 견적 비교 피드 (Real-time Reverse Auction)**
   - 반경 내 케어메이트 실시간 입찰 카드 비교
   - 요양보호사 1급, 간호조무사, 배상보험 가입, 동일 성별 필터
   - 최저가순, 평점순, 거리순, 경력순 정렬
   - 거리 및 이동 시간, 전문 자격 배지, 평점 및 후기, 제안 일당 확인

4. **1:1 안심 채팅 & 토스 원클릭 결제 (Chat & Pay)**
   - 안심 번호 보호 배너 (개인 연락처 비노출)
   - 원클릭 스마트 퀵 질문 칩 (석션 케어, 야간 기상, 식사 보조 등)
   - 인앱 최종 간병 확정서 (인보이스 카드)
   - 토스페이 1초 생체/PIN 에스크로 결제 시스템

5. **간병인용 실시간 공고 레이더 (Caregiver Radar)**
   - 3km / 5km / 10km / 20km 활동 반경 설정
   - 반경 내 실시간 환자 공고 확인
   - 해당 병동 권장 시세 가이드 및 1초 간편 입찰 시트

6. **계약 및 에스크로 관리 (Contracts & Escrow)**
   - 에스크로 안전 보관 및 종료 후 정산 승인
   - 5점 만점 안심 별점 및 상세 후기 작성

## 🛠️ 기술 스택 (Tech Stack)

- **Language:** Kotlin
- **UI Framework:** Jetpack Compose (Material Design 3, Toss UI/UX Design System)
- **Architecture:** MVVM (Model-View-ViewModel), Repository Pattern
- **Local Persistence:** Room Database (Flow, KSP)
- **Navigation:** Jetpack Navigation Compose
- **State Management:** Kotlin StateFlow & Coroutines
