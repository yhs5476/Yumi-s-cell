package com.example.careplus.ui.screens

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.LocalHospital
import androidx.compose.material.icons.filled.LocationCity
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.careplus.data.model.AgeRange
import com.example.careplus.data.model.CareType
import com.example.careplus.data.model.Consciousness
import com.example.careplus.data.model.Mobility
import com.example.careplus.data.model.PatientGender
import com.example.careplus.data.model.WeightRange
import com.example.careplus.ui.components.TossButton
import com.example.careplus.ui.components.TossCard
import com.example.careplus.ui.components.TossSelectableCard
import com.example.careplus.ui.components.TossTagChip
import com.example.careplus.ui.theme.TossBackground
import com.example.careplus.ui.theme.TossBlack
import com.example.careplus.ui.theme.TossBlue
import com.example.careplus.ui.theme.TossBlueDark
import com.example.careplus.ui.theme.TossBlueLight
import com.example.careplus.ui.theme.TossBorder
import com.example.careplus.ui.theme.TossDisabled
import com.example.careplus.ui.theme.TossGray
import com.example.careplus.ui.theme.TossGreen
import com.example.careplus.ui.theme.TossSurface
import com.example.careplus.viewmodel.CarePlusViewModel

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun RequestFormScreen(
    viewModel: CarePlusViewModel,
    onBack: () -> Unit,
    onComplete: (Long) -> Unit
) {
    val formState by viewModel.formState.collectAsState()
    var currentStep by remember { mutableStateOf(1) }

    val hospitals = listOf(
        "서울아산병원 신관 7층",
        "삼성서울병원 본관 9층",
        "서울대학교병원 대한외래",
        "강남세브란스병원 5병동",
        "건국대학교병원 입원병동"
    )

    val specialCareOptions = listOf(
        "석션(가래흡인)",
        "콧줄/위관영양",
        "소변줄/장루",
        "투석 동행",
        "기저귀 케어",
        "치매/섬망",
        "체위 변경",
        "격리병동(CRE/VRE)",
        "특이사항 없음"
    )

    Scaffold(
        containerColor = TossBackground,
        topBar = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(TossSurface)
                    .statusBarsPadding()
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp)
                        .padding(horizontal = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(
                        onClick = {
                            if (currentStep > 1) {
                                currentStep -= 1
                            } else {
                                onBack()
                            }
                        }
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "이전",
                            tint = TossBlack
                        )
                    }
                    Text(
                        text = "간병 견적 신청",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.weight(1f)
                    )
                    Text(
                        text = "$currentStep / 3",
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Bold,
                        color = TossBlue,
                        modifier = Modifier.padding(end = 16.dp)
                    )
                }
                LinearProgressIndicator(
                    progress = { currentStep / 3f },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(3.dp),
                    color = TossBlue,
                    trackColor = TossBorder
                )
            }
        },
        bottomBar = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(TossSurface)
                    .navigationBarsPadding()
                    .imePadding()
                    .padding(horizontal = 20.dp, vertical = 14.dp)
            ) {
                TossButton(
                    text = if (currentStep < 3) "다음 ($currentStep/3)" else "공고 등록하고 실시간 견적 받기",
                    onClick = {
                        if (currentStep < 3) {
                            currentStep += 1
                        } else {
                            viewModel.submitCareRequest { requestId ->
                                onComplete(requestId)
                            }
                        }
                    },
                    testTag = "btn_next_step"
                )
            }
        }
    ) { innerPadding ->
        AnimatedContent(
            targetState = currentStep,
            transitionSpec = {
                if (targetState > initialState) {
                    (slideInHorizontally { it } + fadeIn()).togetherWith(slideOutHorizontally { -it } + fadeOut())
                } else {
                    (slideInHorizontally { -it } + fadeIn()).togetherWith(slideOutHorizontally { it } + fadeOut())
                }
            },
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            label = "funnel_animation"
        ) { step ->
            val scrollState = rememberScrollState()
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(scrollState)
                    .padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(20.dp)
            ) {
                when (step) {
                    1 -> {
                        // Step 1: Place & Schedule
                        Text(
                            text = "어디서 돌봄이\n필요하신가요?",
                            style = MaterialTheme.typography.headlineLarge,
                            fontWeight = FontWeight.Bold,
                            color = TossBlack
                        )
                        Text(
                            text = "병원 입원 또는 자택 돌봄 중 선택해 주세요",
                            style = MaterialTheme.typography.bodyMedium,
                            color = TossGray
                        )

                        TossSelectableCard(
                            title = "병원에 입원 중이에요",
                            subtitle = "담당 의료진과 협력하여 24시간/주간 케어",
                            emoji = "🏥",
                            isSelected = formState.isHospital,
                            onClick = { viewModel.updateForm { it.copy(isHospital = true) } },
                            testTag = "select_hospital"
                        )

                        if (formState.isHospital) {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clip(RoundedCornerShape(16.dp))
                                    .background(TossSurface)
                                    .padding(16.dp)
                            ) {
                                Text(
                                    text = "주요 병원 선택 또는 상세 병동 입력",
                                    style = MaterialTheme.typography.bodySmall,
                                    fontWeight = FontWeight.SemiBold,
                                    color = TossGray
                                )
                                Spacer(modifier = Modifier.height(10.dp))
                                FlowRow(
                                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                                    verticalArrangement = Arrangement.spacedBy(8.dp)
                                ) {
                                    hospitals.forEach { hospital ->
                                        val isSelected = formState.hospitalName == hospital
                                        Box(
                                            modifier = Modifier
                                                .clip(RoundedCornerShape(10.dp))
                                                .background(if (isSelected) TossBlue else TossBackground)
                                                .clickable { viewModel.updateForm { it.copy(hospitalName = hospital) } }
                                                .padding(horizontal = 12.dp, vertical = 8.dp)
                                        ) {
                                            Text(
                                                text = hospital,
                                                style = MaterialTheme.typography.bodySmall,
                                                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                                                color = if (isSelected) TossSurface else TossBlack
                                            )
                                        }
                                    }
                                }
                            }
                        }

                        TossSelectableCard(
                            title = "집에서 돌봄이 필요해요",
                            subtitle = "가정 내 일상 회복, 외래 동행 및 식사 지원",
                            emoji = "🏠",
                            isSelected = !formState.isHospital,
                            onClick = { viewModel.updateForm { it.copy(isHospital = false) } },
                            testTag = "select_home"
                        )

                        if (!formState.isHospital) {
                            OutlinedTextField(
                                value = formState.homeAddress,
                                onValueChange = { addr -> viewModel.updateForm { it.copy(homeAddress = addr) } },
                                label = { Text("자택 도로명 주소") },
                                placeholder = { Text("예: 서울시 송파구 잠실동 올림픽로 43") },
                                modifier = Modifier.fillMaxWidth(),
                                shape = RoundedCornerShape(16.dp),
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedBorderColor = TossBlue,
                                    unfocusedBorderColor = TossBorder,
                                    focusedContainerColor = TossSurface,
                                    unfocusedContainerColor = TossSurface
                                )
                            )
                        }

                        // Schedule summary card
                        TossCard(
                            modifier = Modifier.fillMaxWidth(),
                            backgroundColor = TossSurface,
                            shape = RoundedCornerShape(18.dp)
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    imageVector = Icons.Default.CalendarMonth,
                                    contentDescription = null,
                                    tint = TossBlue,
                                    modifier = Modifier.size(24.dp)
                                )
                                Spacer(modifier = Modifier.width(12.dp))
                                Column(modifier = Modifier.weight(1f)) {
                                    Text(
                                        text = "예정 기간: ${formState.startDate} ~ ${formState.endDate}",
                                        style = MaterialTheme.typography.bodyMedium,
                                        fontWeight = FontWeight.Bold
                                    )
                                    Text(
                                        text = "총 ${formState.totalDays}일간 (24시간 집중 케어)",
                                        style = MaterialTheme.typography.bodySmall,
                                        color = TossGray
                                    )
                                }
                            }
                        }
                    }

                    2 -> {
                        // Step 2: Patient Condition
                        Text(
                            text = "환자분의 상태를\n알려주세요",
                            style = MaterialTheme.typography.headlineLarge,
                            fontWeight = FontWeight.Bold,
                            color = TossBlack
                        )
                        Text(
                            text = "간병인이 최적의 케어 계획과 견적을 제시합니다",
                            style = MaterialTheme.typography.bodyMedium,
                            color = TossGray
                        )

                        // Mobility selection
                        Text(
                            text = "1. 거동 상태",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold
                        )
                        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                            Mobility.values().forEach { mobility ->
                                TossSelectableCard(
                                    title = mobility.label,
                                    subtitle = mobility.desc,
                                    emoji = mobility.emoji,
                                    isSelected = formState.mobility == mobility,
                                    onClick = { viewModel.updateForm { it.copy(mobility = mobility) } }
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(8.dp))

                        // Consciousness
                        Text(
                            text = "2. 의식 상태",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold
                        )
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Consciousness.values().forEach { consciousness ->
                                val isSelected = formState.consciousness == consciousness
                                Box(
                                    modifier = Modifier
                                        .weight(1f)
                                        .clip(RoundedCornerShape(14.dp))
                                        .background(if (isSelected) TossBlue else TossSurface)
                                        .border(1.dp, if (isSelected) TossBlue else TossBorder, RoundedCornerShape(14.dp))
                                        .clickable { viewModel.updateForm { it.copy(consciousness = consciousness) } }
                                        .padding(vertical = 14.dp),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        text = consciousness.label,
                                        style = MaterialTheme.typography.bodyMedium,
                                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                                        color = if (isSelected) TossSurface else TossBlack
                                    )
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(8.dp))

                        // Weight Range
                        Text(
                            text = "3. 환자 체중 구간",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold
                        )
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            WeightRange.values().forEach { weight ->
                                val isSelected = formState.weightRange == weight
                                Box(
                                    modifier = Modifier
                                        .weight(1f)
                                        .clip(RoundedCornerShape(14.dp))
                                        .background(if (isSelected) TossBlue else TossSurface)
                                        .border(1.dp, if (isSelected) TossBlue else TossBorder, RoundedCornerShape(14.dp))
                                        .clickable { viewModel.updateForm { it.copy(weightRange = weight) } }
                                        .padding(vertical = 14.dp),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        text = weight.label,
                                        style = MaterialTheme.typography.bodySmall,
                                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                                        color = if (isSelected) TossSurface else TossBlack
                                    )
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(8.dp))

                        // Gender & Age
                        Text(
                            text = "4. 환자 성별 및 연령대",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold
                        )
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            PatientGender.values().forEach { gender ->
                                val isSelected = formState.gender == gender
                                Box(
                                    modifier = Modifier
                                        .weight(1f)
                                        .clip(RoundedCornerShape(14.dp))
                                        .background(if (isSelected) TossBlue else TossSurface)
                                        .border(1.dp, if (isSelected) TossBlue else TossBorder, RoundedCornerShape(14.dp))
                                        .clickable { viewModel.updateForm { it.copy(gender = gender) } }
                                        .padding(vertical = 14.dp),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        text = gender.label,
                                        style = MaterialTheme.typography.bodyMedium,
                                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                                        color = if (isSelected) TossSurface else TossBlack
                                    )
                                }
                            }
                        }
                    }

                    3 -> {
                        // Step 3: Special Care items
                        Text(
                            text = "특별히 신경 써야 할\n케어가 있나요?",
                            style = MaterialTheme.typography.headlineLarge,
                            fontWeight = FontWeight.Bold,
                            color = TossBlack
                        )
                        Text(
                            text = "해당 항목에 특화된 전문 케어메이트에게 우선 전달됩니다 (복수 선택)",
                            style = MaterialTheme.typography.bodyMedium,
                            color = TossGray
                        )

                        FlowRow(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(10.dp),
                            verticalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            specialCareOptions.forEach { careItem ->
                                val isSelected = formState.specialNeeds.contains(careItem)
                                TossTagChip(
                                    text = "# $careItem",
                                    isSelected = isSelected,
                                    onClick = {
                                        val updated = formState.specialNeeds.toMutableSet()
                                        if (careItem == "특이사항 없음") {
                                            updated.clear()
                                            updated.add("특이사항 없음")
                                        } else {
                                            updated.remove("특이사항 없음")
                                            if (isSelected) updated.remove(careItem) else updated.add(careItem)
                                        }
                                        viewModel.updateForm { it.copy(specialNeeds = updated) }
                                    }
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        // Review summary box
                        TossCard(
                            modifier = Modifier.fillMaxWidth(),
                            backgroundColor = TossBlueLight.copy(alpha = 0.5f),
                            shape = RoundedCornerShape(18.dp)
                        ) {
                            Column(modifier = Modifier.padding(18.dp)) {
                                Text(
                                    text = "💡 공고 등록 즉시 15km 반경 내 케어메이트에게 실시간 푸시가 전송되어 평균 15분 내 3~5건의 안심 견적이 도착합니다.",
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = TossBlueDark,
                                    lineHeight = 22.sp
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
