package com.example.careplus.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Radar
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material.icons.filled.Tune
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.careplus.data.local.CareRequestEntity
import com.example.careplus.ui.components.TossBadge
import com.example.careplus.ui.components.TossButton
import com.example.careplus.ui.components.TossCard
import com.example.careplus.ui.theme.TossBackground
import com.example.careplus.ui.theme.TossBlack
import com.example.careplus.ui.theme.TossBlue
import com.example.careplus.ui.theme.TossBlueDark
import com.example.careplus.ui.theme.TossBlueLight
import com.example.careplus.ui.theme.TossBorder
import com.example.careplus.ui.theme.TossGray
import com.example.careplus.ui.theme.TossGreen
import com.example.careplus.ui.theme.TossGreenLight
import com.example.careplus.ui.theme.TossSurface
import com.example.careplus.viewmodel.CarePlusViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalLayoutApi::class, ExperimentalMaterial3Api::class)
@Composable
fun CaregiverRadarScreen(
    viewModel: CarePlusViewModel,
    onBack: () -> Unit
) {
    val requests by viewModel.allRequests.collectAsState()
    val radiusKm by viewModel.caregiverRadiusKm.collectAsState()

    var selectedRequestForBid by remember { mutableStateOf<CareRequestEntity?>(null) }
    val radii = listOf(3, 5, 10, 20)

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
                    IconButton(onClick = onBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "뒤로가기",
                            tint = TossBlack
                        )
                    }
                    Text(
                        text = "실시간 공고 레이더",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.weight(1f)
                    )
                    TossBadge(text = "간병인 전용", isGreen = true)
                    Spacer(modifier = Modifier.width(16.dp))
                }
            }
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentPadding = PaddingValues(bottom = 32.dp)
        ) {
            // Radar Radius Filter Bar
            item {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(TossSurface)
                        .padding(16.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.Radar,
                                contentDescription = null,
                                tint = TossGreen,
                                modifier = Modifier.size(20.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "내 활동 반경 설정",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold
                            )
                        }
                        Text(
                            text = "현재 ${radiusKm}km 이내",
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = FontWeight.Bold,
                            color = TossGreen
                        )
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        radii.forEach { r ->
                            val isSelected = r == radiusKm
                            Box(
                                modifier = Modifier
                                    .weight(1f)
                                    .clip(RoundedCornerShape(12.dp))
                                    .background(if (isSelected) TossGreen else TossBackground)
                                    .border(1.dp, if (isSelected) TossGreen else TossBorder, RoundedCornerShape(12.dp))
                                    .clickable { viewModel.setCaregiverRadius(r) }
                                    .padding(vertical = 10.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = "${r}km",
                                    fontSize = 13.sp,
                                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                                    color = if (isSelected) TossSurface else TossBlack
                                )
                            }
                        }
                    }
                }
            }

            item {
                Spacer(modifier = Modifier.height(12.dp))
            }

            // Radar Requests List
            items(requests) { req ->
                Box(modifier = Modifier.padding(horizontal = 16.dp, vertical = 6.dp)) {
                    TossCard(
                        modifier = Modifier.fillMaxWidth(),
                        backgroundColor = TossSurface,
                        shape = RoundedCornerShape(20.dp)
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(18.dp)
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                TossBadge(text = req.careType.title, isHighlighted = true)
                                Text(
                                    text = "직선거리 2.4km",
                                    fontSize = 12.sp,
                                    color = TossGreen,
                                    fontWeight = FontWeight.Bold
                                )
                            }

                            Spacer(modifier = Modifier.height(10.dp))

                            Text(
                                text = req.hospitalName,
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold
                            )

                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = "환자 상태: ${req.gender.label} (${req.ageRange.label}) · ${req.mobility.label} · ${req.weightRange.label}",
                                style = MaterialTheme.typography.bodyMedium,
                                color = TossGray
                            )

                            Spacer(modifier = Modifier.height(10.dp))
                            FlowRow(
                                horizontalArrangement = Arrangement.spacedBy(6.dp),
                                verticalArrangement = Arrangement.spacedBy(6.dp)
                            ) {
                                req.specialNeeds.forEach { need ->
                                    TossBadge(text = need)
                                }
                            }

                            Spacer(modifier = Modifier.height(14.dp))
                            HorizontalDivider(color = TossBorder)
                            Spacer(modifier = Modifier.height(14.dp))

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Column {
                                    Text(
                                        text = "예정 기간 (${req.totalDays}일)",
                                        style = MaterialTheme.typography.bodySmall,
                                        color = TossGray
                                    )
                                    Text(
                                        text = "${req.startDate} ~ ${req.endDate}",
                                        style = MaterialTheme.typography.bodyMedium,
                                        fontWeight = FontWeight.SemiBold
                                    )
                                }

                                TossButton(
                                    text = "1초 입찰하기",
                                    onClick = { selectedRequestForBid = req },
                                    modifier = Modifier.width(130.dp),
                                    backgroundColor = TossGreen,
                                    testTag = "bid_request_${req.id}"
                                )
                            }
                        }
                    }
                }
            }
        }

        // Quick Bid Sheet Modal
        if (selectedRequestForBid != null) {
            QuickBidBottomSheet(
                request = selectedRequestForBid!!,
                onDismiss = { selectedRequestForBid = null },
                onSubmitBid = { price, pitch ->
                    viewModel.submitCaregiverBid(
                        requestId = selectedRequestForBid!!.id,
                        caregiverName = "나의 케어메이트",
                        dailyPrice = price,
                        pitchMessage = pitch,
                        onSuccess = {
                            selectedRequestForBid = null
                        }
                    )
                }
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QuickBidBottomSheet(
    request: CareRequestEntity,
    onDismiss: () -> Unit,
    onSubmitBid: (Int, String) -> Unit
) {
    val sheetState = rememberModalBottomSheetState()
    val scope = rememberCoroutineScope()

    var proposedPriceStr by remember { mutableStateOf("140000") }
    var pitchMessage by remember { mutableStateOf("중환자실 7년 경력으로 석션 및 위관영양 케어 능숙하며 정성을 다해 모시겠습니다.") }

    val presetTemplates = listOf(
        "중환자실 7년 경력으로 석션 및 위관영양 케어에 능숙합니다.",
        "재활 운동 및 낙상 방지 전문 요양보호사 1급 자격 보유자입니다.",
        "친절하고 따뜻한 어르신 식사 및 침상 케어 약속드립니다."
    )

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = sheetState,
        containerColor = TossSurface,
        shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp, vertical = 16.dp)
                .navigationBarsPadding()
        ) {
            Text(
                text = "희망 일당 입력 및 간편 입찰",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "${request.hospitalName} (${request.totalDays}일간)",
                style = MaterialTheme.typography.bodyMedium,
                color = TossGray
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Recommended Market Rate Guide
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(14.dp))
                    .background(TossGreenLight)
                    .padding(14.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.Shield,
                        contentDescription = null,
                        tint = TossGreen,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Column {
                        Text(
                            text = "해당 병동 권장 시세",
                            style = MaterialTheme.typography.bodySmall,
                            color = TossGreen,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "1일 135,000원 ~ 150,000원",
                            style = MaterialTheme.typography.titleMedium,
                            color = TossBlack,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Wage Input
            Text("제안 일당 (원)", style = MaterialTheme.typography.bodySmall, fontWeight = FontWeight.Bold, color = TossGray)
            Spacer(modifier = Modifier.height(6.dp))
            OutlinedTextField(
                value = proposedPriceStr,
                onValueChange = { proposedPriceStr = it },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(14.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = TossGreen,
                    unfocusedBorderColor = TossBorder
                ),
                suffix = { Text("원 / 1일", fontWeight = FontWeight.Bold) }
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Pitch & Preset Templates
            Text("자기소개 및 역량 메시지", style = MaterialTheme.typography.bodySmall, fontWeight = FontWeight.Bold, color = TossGray)
            Spacer(modifier = Modifier.height(6.dp))

            LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                items(presetTemplates) { template ->
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(10.dp))
                            .background(TossBackground)
                            .border(1.dp, TossBorder, RoundedCornerShape(10.dp))
                            .clickable { pitchMessage = template }
                            .padding(horizontal = 10.dp, vertical = 6.dp)
                    ) {
                        Text(
                            text = template.take(18) + "...",
                            fontSize = 11.sp,
                            color = TossBlack
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = pitchMessage,
                onValueChange = { pitchMessage = it },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(14.dp),
                maxLines = 3,
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = TossGreen,
                    unfocusedBorderColor = TossBorder
                )
            )

            Spacer(modifier = Modifier.height(20.dp))

            TossButton(
                text = "1초 만에 견적 제출하기",
                backgroundColor = TossGreen,
                onClick = {
                    val price = proposedPriceStr.toIntOrNull() ?: 140000
                    scope.launch {
                        sheetState.hide()
                        onSubmitBid(price, pitchMessage)
                    }
                }
            )
            Spacer(modifier = Modifier.height(10.dp))
        }
    }
}
