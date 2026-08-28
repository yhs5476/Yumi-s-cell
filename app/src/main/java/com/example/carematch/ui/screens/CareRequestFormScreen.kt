package com.example.carematch.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Healing
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material.icons.filled.Verified
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.carematch.data.model.CaregiverProfile
import com.example.carematch.ui.theme.CoralSecondary
import com.example.carematch.ui.theme.Slate100
import com.example.carematch.ui.theme.Slate200
import com.example.carematch.ui.theme.Slate400
import com.example.carematch.ui.theme.Slate50
import com.example.carematch.ui.theme.Slate500
import com.example.carematch.ui.theme.Slate600
import com.example.carematch.ui.theme.Slate700
import com.example.carematch.ui.theme.Slate800
import com.example.carematch.ui.theme.Slate900
import com.example.carematch.ui.theme.TealContainer
import com.example.carematch.ui.theme.TealDark
import com.example.carematch.ui.theme.TealPrimary
import com.example.carematch.ui.theme.White
import com.example.carematch.ui.viewmodel.RequestFormState

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun CareRequestFormScreen(
    caregiver: CaregiverProfile?,
    formState: RequestFormState,
    onFormUpdate: ((RequestFormState) -> RequestFormState) -> Unit,
    onSubmit: () -> Unit,
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    if (caregiver == null) {
        Box(
            modifier = modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text("간병인 정보를 찾을 수 없습니다.")
        }
        return
    }

    val diagnoses = listOf(
        "뇌졸중 / 편마비",
        "치매 / 알츠하이머",
        "정형외과 수술 후 재활",
        "암 환자 집중 케어",
        "폐렴 / 석션 필요",
        "기력 저하 / 거동 보조"
    )

    val careLevels = listOf(
        "와상 (침상 거동 불가)",
        "휠체어 이동 가능",
        "부축 시 거동 가능",
        "보행 보조기 이용"
    )

    val careTypes = listOf(
        "24시간 입주 간병",
        "주간 돌봄 (09:00~18:00)",
        "야간 돌봄 (20:00~08:00)",
        "단기 집중 케어"
    )

    val totalPay = formState.offeredDailyPay.toLong() * formState.durationDays

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "1:1 지정 간병 신청서 작성",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "뒤로가기")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = White)
            )
        },
        bottomBar = {
            Surface(
                color = White,
                shadowElevation = 8.dp,
                modifier = Modifier
                    .fillMaxWidth()
                    .navigationBarsPadding()
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text("총 예상 간병 비용 (${formState.durationDays}일간)", fontSize = 12.sp, color = Slate500)
                            Text(
                                text = "${String.format("%,d", totalPay)}원",
                                style = MaterialTheme.typography.titleLarge,
                                fontWeight = FontWeight.Bold,
                                color = TealPrimary
                            )
                        }

                        Button(
                            onClick = onSubmit,
                            shape = RoundedCornerShape(12.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = TealPrimary),
                            modifier = Modifier
                                .height(48.dp)
                                .testTag("submit_request_button")
                        ) {
                            Text("간병 신청서 제출", fontWeight = FontWeight.Bold, fontSize = 15.sp, color = White)
                        }
                    }
                }
            }
        }
    ) { innerPadding ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .background(Slate50)
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 16.dp, vertical = 12.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Selected Caregiver Card
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = White),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Row(
                    modifier = Modifier.padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(54.dp)
                            .clip(CircleShape)
                            .background(TealContainer)
                    ) {
                        if (caregiver.avatarUrl.isNotEmpty()) {
                            AsyncImage(
                                model = caregiver.avatarUrl,
                                contentDescription = caregiver.name,
                                modifier = Modifier.fillMaxSize(),
                                contentScale = ContentScale.Crop
                            )
                        } else {
                            Icon(Icons.Default.Person, contentDescription = null, tint = TealDark, modifier = Modifier.size(32.dp).align(Alignment.Center))
                        }
                    }

                    Spacer(modifier = Modifier.width(12.dp))

                    Column(modifier = Modifier.weight(1f)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = "${caregiver.name} 간병인",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                color = Slate900
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Icon(Icons.Default.Verified, contentDescription = null, tint = TealPrimary, modifier = Modifier.size(16.dp))
                        }
                        Text(
                            text = "경력 ${caregiver.experienceYears}년 · 희망 일당 ${String.format("%,d", caregiver.desiredDailyPay)}원",
                            fontSize = 12.sp,
                            color = Slate600
                        )
                    }
                }
            }

            // Section 1: Patient Information (환자 정보)
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = White)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "1. 환자 기본 정보",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = Slate900
                    )

                    Spacer(modifier = Modifier.height(14.dp))

                    // Gender Selection
                    Text("환자 성별", fontSize = 13.sp, fontWeight = FontWeight.SemiBold, color = Slate700)
                    Spacer(modifier = Modifier.height(6.dp))
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        listOf("여성", "남성").forEach { gender ->
                            val isSelected = formState.patientGender == gender
                            Surface(
                                modifier = Modifier
                                    .weight(1f)
                                    .clip(RoundedCornerShape(8.dp))
                                    .clickable { onFormUpdate { it.copy(patientGender = gender) } }
                                    .testTag("gender_$gender"),
                                color = if (isSelected) TealPrimary else Slate100
                            ) {
                                Box(modifier = Modifier.padding(vertical = 10.dp), contentAlignment = Alignment.Center) {
                                    Text(
                                        text = gender,
                                        color = if (isSelected) White else Slate700,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 14.sp
                                    )
                                }
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    // Age Group
                    Text("연령대", fontSize = 13.sp, fontWeight = FontWeight.SemiBold, color = Slate700)
                    Spacer(modifier = Modifier.height(6.dp))
                    FlowRow(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        listOf("60대", "70대", "80대", "90대 이상").forEach { age ->
                            val isSelected = formState.patientAgeGroup.startsWith(age.take(2))
                            Surface(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(8.dp))
                                    .clickable { onFormUpdate { it.copy(patientAgeGroup = age) } },
                                color = if (isSelected) TealContainer else Slate100,
                                border = androidx.compose.foundation.BorderStroke(1.dp, if (isSelected) TealDark else Color.Transparent)
                            ) {
                                Text(
                                    text = age,
                                    color = if (isSelected) TealDark else Slate700,
                                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                                    fontSize = 13.sp,
                                    modifier = Modifier.padding(horizontal = 14.dp, vertical = 8.dp)
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    // Diagnosis
                    Text("주요 질환 / 진단명", fontSize = 13.sp, fontWeight = FontWeight.SemiBold, color = Slate700)
                    Spacer(modifier = Modifier.height(6.dp))
                    FlowRow(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        diagnoses.forEach { diag ->
                            val isSelected = formState.patientDiagnosis == diag
                            Surface(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(8.dp))
                                    .clickable { onFormUpdate { it.copy(patientDiagnosis = diag) } },
                                color = if (isSelected) TealContainer else Slate100,
                                border = androidx.compose.foundation.BorderStroke(1.dp, if (isSelected) TealDark else Color.Transparent)
                            ) {
                                Text(
                                    text = diag,
                                    color = if (isSelected) TealDark else Slate700,
                                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                                    fontSize = 12.sp,
                                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp)
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    // Care Level (거동 상태)
                    Text("환자 거동 상태", fontSize = 13.sp, fontWeight = FontWeight.SemiBold, color = Slate700)
                    Spacer(modifier = Modifier.height(6.dp))
                    Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                        careLevels.forEach { level ->
                            val isSelected = formState.careLevel == level
                            Surface(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clip(RoundedCornerShape(8.dp))
                                    .clickable { onFormUpdate { it.copy(careLevel = level) } },
                                color = if (isSelected) TealContainer.copy(alpha = 0.7f) else Slate50,
                                border = androidx.compose.foundation.BorderStroke(1.dp, if (isSelected) TealPrimary else Slate200)
                            ) {
                                Row(
                                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 10.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Icon(
                                        imageVector = if (isSelected) Icons.Default.CheckCircle else Icons.Default.Healing,
                                        contentDescription = null,
                                        tint = if (isSelected) TealPrimary else Slate400,
                                        modifier = Modifier.size(16.dp)
                                    )
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Text(
                                        text = level,
                                        fontSize = 13.sp,
                                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                                        color = if (isSelected) TealDark else Slate800
                                    )
                                }
                            }
                        }
                    }
                }
            }

            // Section 2: Care Location (돌봄 장소)
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = White)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "2. 돌봄 장소",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = Slate900
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        listOf("병원 (입원실)", "자택").forEach { locType ->
                            val isSelected = formState.locationType == locType
                            Surface(
                                modifier = Modifier
                                    .weight(1f)
                                    .clip(RoundedCornerShape(8.dp))
                                    .clickable { onFormUpdate { it.copy(locationType = locType) } },
                                color = if (isSelected) TealPrimary else Slate100
                            ) {
                                Box(modifier = Modifier.padding(vertical = 10.dp), contentAlignment = Alignment.Center) {
                                    Text(
                                        text = locType,
                                        color = if (isSelected) White else Slate700,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 13.sp
                                    )
                                }
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    Text("상세 위치 및 병동/동호수", fontSize = 13.sp, fontWeight = FontWeight.SemiBold, color = Slate700)
                    Spacer(modifier = Modifier.height(6.dp))
                    OutlinedTextField(
                        value = formState.locationAddress,
                        onValueChange = { addr -> onFormUpdate { it.copy(locationAddress = addr) } },
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("location_input"),
                        placeholder = { Text("예: 서울아산병원 동관 8층 82병동") },
                        singleLine = true,
                        shape = RoundedCornerShape(10.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = TealPrimary,
                            unfocusedBorderColor = Slate200
                        )
                    )
                }
            }

            // Section 3: Schedule & Care Type (돌봄 일정 및 형태)
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = White)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "3. 간병 형태 및 기간",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = Slate900
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    Text("돌봄 형태", fontSize = 13.sp, fontWeight = FontWeight.SemiBold, color = Slate700)
                    Spacer(modifier = Modifier.height(6.dp))
                    FlowRow(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        careTypes.forEach { type ->
                            val isSelected = formState.careType == type
                            Surface(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(8.dp))
                                    .clickable { onFormUpdate { it.copy(careType = type) } },
                                color = if (isSelected) TealContainer else Slate100,
                                border = androidx.compose.foundation.BorderStroke(1.dp, if (isSelected) TealDark else Color.Transparent)
                            ) {
                                Text(
                                    text = type,
                                    color = if (isSelected) TealDark else Slate700,
                                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                                    fontSize = 13.sp,
                                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp)
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text("시작일", fontSize = 12.sp, color = Slate600)
                            Spacer(modifier = Modifier.height(4.dp))
                            OutlinedTextField(
                                value = formState.startDate,
                                onValueChange = { s -> onFormUpdate { it.copy(startDate = s) } },
                                shape = RoundedCornerShape(8.dp),
                                singleLine = true
                            )
                        }

                        Column(modifier = Modifier.weight(1f)) {
                            Text("종료일 (${formState.durationDays}일간)", fontSize = 12.sp, color = Slate600)
                            Spacer(modifier = Modifier.height(4.dp))
                            OutlinedTextField(
                                value = formState.endDate,
                                onValueChange = { e -> onFormUpdate { it.copy(endDate = e) } },
                                shape = RoundedCornerShape(8.dp),
                                singleLine = true
                            )
                        }
                    }
                }
            }

            // Section 4: Daily Pay Offer (제시 일당)
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = White)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "4. 제시 일당 설정",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = Slate900
                        )
                        Text(
                            text = "간병인 희망: ${String.format("%,d", caregiver.desiredDailyPay)}원",
                            fontSize = 11.sp,
                            color = Slate500
                        )
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        IconButton(
                            onClick = {
                                if (formState.offeredDailyPay > 100000) {
                                    onFormUpdate { it.copy(offeredDailyPay = it.offeredDailyPay - 5000) }
                                }
                            },
                            modifier = Modifier
                                .size(40.dp)
                                .clip(CircleShape)
                                .background(Slate100)
                        ) {
                            Icon(Icons.Default.Remove, contentDescription = "일당 감소")
                        }

                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(
                                text = "${String.format("%,d", formState.offeredDailyPay)}원",
                                style = MaterialTheme.typography.headlineSmall,
                                fontWeight = FontWeight.Bold,
                                color = TealPrimary
                            )
                            Text("1일 기준 금액", fontSize = 11.sp, color = Slate500)
                        }

                        IconButton(
                            onClick = {
                                onFormUpdate { it.copy(offeredDailyPay = it.offeredDailyPay + 5000) }
                            },
                            modifier = Modifier
                                .size(40.dp)
                                .clip(CircleShape)
                                .background(TealContainer)
                        ) {
                            Icon(Icons.Default.Add, contentDescription = "일당 증가", tint = TealDark)
                        }
                    }
                }
            }

            // Section 5: Guardian Contact & Special Notes (보호자 연락처 및 요청사항)
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = White)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "5. 요청 메모 & 특이사항",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = Slate900
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    OutlinedTextField(
                        value = formState.specialNotes,
                        onValueChange = { notes -> onFormUpdate { it.copy(specialNotes = notes) } },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(100.dp)
                            .testTag("special_notes_input"),
                        placeholder = { Text("환자분 특이사항, 선호하시는 돌봄 방식, 알레르기 등을 적어주시면 간병인 수락률이 높아집니다.", fontSize = 12.sp) },
                        shape = RoundedCornerShape(10.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = TealPrimary,
                            unfocusedBorderColor = Slate200
                        )
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}
