package com.example.carematch.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Message
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import com.example.carematch.data.model.CareRequest
import com.example.carematch.data.model.RequestStatus
import com.example.carematch.ui.components.StatusChip
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
import com.example.carematch.ui.theme.StatusAccepted
import com.example.carematch.ui.theme.StatusAcceptedBg
import com.example.carematch.ui.theme.StatusPending
import com.example.carematch.ui.theme.StatusPendingBg
import com.example.carematch.ui.theme.StatusRejected
import com.example.carematch.ui.theme.StatusRejectedBg
import com.example.carematch.ui.theme.TealContainer
import com.example.carematch.ui.theme.TealDark
import com.example.carematch.ui.theme.TealPrimary
import com.example.carematch.ui.theme.White

@Composable
fun GuardianRequestsScreen(
    requests: List<CareRequest>,
    onExploreClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    var selectedFilter by remember { mutableStateOf("전체") }

    val filteredRequests = when (selectedFilter) {
        "대기중" -> requests.filter { it.status == RequestStatus.PENDING }
        "매칭완료" -> requests.filter { it.status == RequestStatus.ACCEPTED }
        "거절됨" -> requests.filter { it.status == RequestStatus.REJECTED }
        else -> requests
    }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(Slate50),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        // Header
        item {
            Column {
                Text(
                    text = "내 간병 신청 내역",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = Slate900
                )
                Text(
                    text = "내가 지정 신청한 간병인의 수락/거절 상태를 실시간으로 확인합니다",
                    style = MaterialTheme.typography.bodySmall,
                    color = Slate500
                )
            }
        }

        // Filter Tabs
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                listOf("전체", "대기중", "매칭완료", "거절됨").forEach { tab ->
                    val isSelected = selectedFilter == tab
                    Surface(
                        modifier = Modifier
                            .clip(RoundedCornerShape(20.dp))
                            .clickable { selectedFilter = tab }
                            .testTag("tab_$tab"),
                        color = if (isSelected) TealPrimary else White,
                        border = androidx.compose.foundation.BorderStroke(1.dp, if (isSelected) TealPrimary else Slate200)
                    ) {
                        Text(
                            text = tab,
                            color = if (isSelected) White else Slate700,
                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                            fontSize = 13.sp,
                            modifier = Modifier.padding(horizontal = 14.dp, vertical = 7.dp)
                        )
                    }
                }
            }
        }

        if (filteredRequests.isEmpty()) {
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = White)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(40.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Info,
                            contentDescription = null,
                            tint = Slate400,
                            modifier = Modifier.size(48.dp)
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                        Text(
                            text = "해당하는 신청 내역이 없습니다",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = Slate800
                        )
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = "조건에 딱 맞는 간병인을 직접 탐색하고 신청해 보세요.",
                            fontSize = 13.sp,
                            color = Slate500
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Button(
                            onClick = onExploreClick,
                            shape = RoundedCornerShape(10.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = TealPrimary)
                        ) {
                            Text("간병인 탐색하러 가기", fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }
        } else {
            items(filteredRequests, key = { it.requestId }) { request ->
                GuardianRequestCard(request = request, onFindAnotherCaregiver = onExploreClick)
            }
        }
    }
}

@Composable
fun GuardianRequestCard(
    request: CareRequest,
    onFindAnotherCaregiver: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .testTag("request_item_${request.requestId}"),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            // Status & Time Header
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                StatusChip(status = request.status)
                Text(
                    text = "신청번호 #${request.requestId}",
                    fontSize = 12.sp,
                    color = Slate400
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Caregiver Summary Row
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(48.dp)
                        .clip(CircleShape)
                        .background(TealContainer)
                ) {
                    if (request.caregiverAvatar.isNotEmpty()) {
                        AsyncImage(
                            model = request.caregiverAvatar,
                            contentDescription = request.caregiverName,
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Crop
                        )
                    } else {
                        Icon(
                            Icons.Default.Person,
                            contentDescription = null,
                            tint = TealDark,
                            modifier = Modifier.size(28.dp).align(Alignment.Center)
                        )
                    }
                }

                Spacer(modifier = Modifier.width(12.dp))

                Column {
                    Text(
                        text = "지정 간병인: ${request.caregiverName} 선생님",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = Slate900
                    )
                    Text(
                        text = "제시 일당: ${String.format("%,d", request.offeredDailyPay)}원 · 총 ${String.format("%,d", request.totalEstimatedPay)}원",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = TealPrimary
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))
            HorizontalDivider(color = Slate100)
            Spacer(modifier = Modifier.height(12.dp))

            // Details List
            Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.Person, contentDescription = null, tint = Slate400, modifier = Modifier.size(15.dp))
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "환자: ${request.patientGender} / ${request.patientAgeGroup} (${request.patientDiagnosis})",
                        fontSize = 13.sp,
                        color = Slate800
                    )
                }

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.LocationOn, contentDescription = null, tint = Slate400, modifier = Modifier.size(15.dp))
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "${request.locationType} · ${request.locationAddress}",
                        fontSize = 13.sp,
                        color = Slate800
                    )
                }

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.CalendarMonth, contentDescription = null, tint = Slate400, modifier = Modifier.size(15.dp))
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "일정: ${request.startDate} ~ ${request.endDate} (${request.careType})",
                        fontSize = 13.sp,
                        color = Slate800
                    )
                }
            }

            // Status-Specific Actions & Banners
            when (request.status) {
                RequestStatus.ACCEPTED -> {
                    Spacer(modifier = Modifier.height(14.dp))
                    Surface(
                        color = StatusAcceptedBg,
                        shape = RoundedCornerShape(10.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(modifier = Modifier.padding(12.dp)) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(Icons.Default.CheckCircle, contentDescription = null, tint = StatusAccepted, modifier = Modifier.size(18.dp))
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(
                                    text = "간병인 수락 완료! 연락처가 공유되었습니다.",
                                    fontSize = 13.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = StatusAccepted
                                )
                            }
                            Spacer(modifier = Modifier.height(6.dp))
                            Text(
                                text = "간병인 연락처: ${request.caregiverPhone}",
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold,
                                color = Slate900
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Button(
                            onClick = { /* Simulated Call */ },
                            modifier = Modifier.weight(1f),
                            shape = RoundedCornerShape(8.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = StatusAccepted)
                        ) {
                            Icon(Icons.Default.Call, contentDescription = null, modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("전화 걸기")
                        }

                        OutlinedButton(
                            onClick = { /* Simulated SMS */ },
                            modifier = Modifier.weight(1f),
                            shape = RoundedCornerShape(8.dp)
                        ) {
                            Icon(Icons.Default.Message, contentDescription = null, modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("문자 메시지")
                        }
                    }
                }
                RequestStatus.PENDING -> {
                    Spacer(modifier = Modifier.height(12.dp))
                    Surface(
                        color = StatusPendingBg,
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row(
                            modifier = Modifier.padding(10.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(Icons.Default.Info, contentDescription = null, tint = StatusPending, modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "간병인이 신청서를 검토 중입니다. 평균 15분 내 응답합니다.",
                                fontSize = 12.sp,
                                color = StatusPending,
                                fontWeight = FontWeight.Medium
                            )
                        }
                    }
                }
                RequestStatus.REJECTED -> {
                    Spacer(modifier = Modifier.height(12.dp))
                    Surface(
                        color = StatusRejectedBg,
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(modifier = Modifier.padding(10.dp)) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(Icons.Default.Warning, contentDescription = null, tint = StatusRejected, modifier = Modifier.size(16.dp))
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(
                                    text = "일정 중복 등의 사유로 거절되었습니다.",
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = StatusRejected
                                )
                            }
                            if (!request.rejectionReason.isNullOrEmpty()) {
                                Text(
                                    text = "사유: ${request.rejectionReason}",
                                    fontSize = 11.sp,
                                    color = StatusRejected,
                                    modifier = Modifier.padding(top = 2.dp, start = 22.dp)
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))
                    Button(
                        onClick = onFindAnotherCaregiver,
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(8.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = TealPrimary)
                    ) {
                        Text("다른 우수 간병인 탐색하기", fontSize = 13.sp, fontWeight = FontWeight.Bold)
                    }
                }
                RequestStatus.COMPLETED -> {
                    // Completed status
                }
            }
        }
    }
}
