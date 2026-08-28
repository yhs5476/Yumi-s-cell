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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
fun CaregiverInboxScreen(
    requests: List<CareRequest>,
    onAccept: (Long) -> Unit,
    onReject: (Long, String) -> Unit,
    modifier: Modifier = Modifier
) {
    var rejectDialogRequestId by remember { mutableStateOf<Long?>(null) }
    var rejectReason by remember { mutableStateOf("기존 다른 환자 간병 일정과 중복됩니다.") }

    var selectedTab by remember { mutableStateOf("받은 요청") }

    val pendingRequests = requests.filter { it.status == RequestStatus.PENDING }
    val processedRequests = requests.filter { it.status != RequestStatus.PENDING }

    val displayList = if (selectedTab == "받은 요청") pendingRequests else processedRequests

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(Slate50),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        item {
            Column {
                Text(
                    text = "간병 요청 수신함",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = Slate900
                )
                Text(
                    text = "보호자님들께서 나를 직접 지정하여 보낸 간병 요청서입니다",
                    style = MaterialTheme.typography.bodySmall,
                    color = Slate500
                )
            }
        }

        // Tabs
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Surface(
                    modifier = Modifier
                        .weight(1f)
                        .clip(RoundedCornerShape(10.dp))
                        .clickable { selectedTab = "받은 요청" }
                        .testTag("inbox_tab_pending"),
                    color = if (selectedTab == "받은 요청") TealPrimary else White,
                    border = androidx.compose.foundation.BorderStroke(1.dp, if (selectedTab == "받은 요청") TealPrimary else Slate200)
                ) {
                    Box(modifier = Modifier.padding(vertical = 10.dp), contentAlignment = Alignment.Center) {
                        Text(
                            text = "대기중인 요청 (${pendingRequests.size})",
                            color = if (selectedTab == "받은 요청") White else Slate700,
                            fontWeight = FontWeight.Bold,
                            fontSize = 13.sp
                        )
                    }
                }

                Surface(
                    modifier = Modifier
                        .weight(1f)
                        .clip(RoundedCornerShape(10.dp))
                        .clickable { selectedTab = "완료/처리 내역" }
                        .testTag("inbox_tab_history"),
                    color = if (selectedTab == "완료/처리 내역") TealPrimary else White,
                    border = androidx.compose.foundation.BorderStroke(1.dp, if (selectedTab == "완료/처리 내역") TealPrimary else Slate200)
                ) {
                    Box(modifier = Modifier.padding(vertical = 10.dp), contentAlignment = Alignment.Center) {
                        Text(
                            text = "처리 완료 내역 (${processedRequests.size})",
                            color = if (selectedTab == "완료/처리 내역") White else Slate700,
                            fontWeight = FontWeight.Bold,
                            fontSize = 13.sp
                        )
                    }
                }
            }
        }

        if (displayList.isEmpty()) {
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
                            text = if (selectedTab == "받은 요청") "새로 도착한 간병 요청이 없습니다" else "처리된 내역이 없습니다",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = Slate800
                        )
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = "프로필을 항상 최신으로 유지하면 더 많은 요청을 받을 수 있습니다.",
                            fontSize = 13.sp,
                            color = Slate500
                        )
                    }
                }
            }
        } else {
            items(displayList, key = { it.requestId }) { request ->
                CaregiverRequestCard(
                    request = request,
                    onAccept = { onAccept(request.requestId) },
                    onRejectClick = { rejectDialogRequestId = request.requestId }
                )
            }
        }
    }

    // Rejection Dialog
    if (rejectDialogRequestId != null) {
        val defaultReasons = listOf(
            "기존 다른 환자 간병 일정과 중복됩니다.",
            "활동 가능 지역(거리)과 맞지 않습니다.",
            "환자 중증도 대비 일정 조율이 어렵습니다."
        )

        AlertDialog(
            onDismissRequest = { rejectDialogRequestId = null },
            title = {
                Text("간병 요청 거절", fontWeight = FontWeight.Bold)
            },
            text = {
                Column {
                    Text(
                        text = "보호자님께 정중한 거절 알림톡이 전달됩니다. 거절 사유를 선택하거나 입력해 주세요.",
                        fontSize = 13.sp,
                        color = Slate600
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    defaultReasons.forEach { reason ->
                        val isSelected = rejectReason == reason
                        Surface(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 3.dp)
                                .clip(RoundedCornerShape(6.dp))
                                .clickable { rejectReason = reason },
                            color = if (isSelected) TealContainer else Slate100
                        ) {
                            Text(
                                text = reason,
                                fontSize = 12.sp,
                                color = if (isSelected) TealDark else Slate800,
                                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                                modifier = Modifier.padding(horizontal = 10.dp, vertical = 8.dp)
                            )
                        }
                    }
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        rejectDialogRequestId?.let { id ->
                            onReject(id, rejectReason)
                        }
                        rejectDialogRequestId = null
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = StatusRejected)
                ) {
                    Text("거절 확인", color = White, fontWeight = FontWeight.Bold)
                }
            },
            dismissButton = {
                TextButton(onClick = { rejectDialogRequestId = null }) {
                    Text("취소")
                }
            }
        )
    }
}

@Composable
fun CaregiverRequestCard(
    request: CareRequest,
    onAccept: () -> Unit,
    onRejectClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .testTag("caregiver_request_card_${request.requestId}"),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            // Top Row
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                StatusChip(status = request.status)
                Text(
                    text = "요청 번호 #${request.requestId}",
                    fontSize = 12.sp,
                    color = Slate400
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Guardian Header
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = "${request.guardianName}님의 1:1 지정 요청",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = Slate900
                    )
                    Text(
                        text = "보호자 연락처: ${request.guardianPhone}",
                        fontSize = 12.sp,
                        color = Slate500
                    )
                }

                Surface(
                    color = TealContainer,
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
                        horizontalAlignment = Alignment.End
                    ) {
                        Text("제안 일당", fontSize = 10.sp, color = TealDark)
                        Text(
                            text = "${String.format("%,d", request.offeredDailyPay)}원",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            color = TealDark
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(10.dp))
            HorizontalDivider(color = Slate100)
            Spacer(modifier = Modifier.height(10.dp))

            // Patient & Location specs
            Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.Person, contentDescription = null, tint = Slate400, modifier = Modifier.size(15.dp))
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "환자: ${request.patientGender} · ${request.patientAgeGroup} (${request.patientDiagnosis})",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Medium,
                        color = Slate800
                    )
                }

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.LocationOn, contentDescription = null, tint = Slate400, modifier = Modifier.size(15.dp))
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "${request.locationType} : ${request.locationAddress}",
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

                if (request.specialNotes.isNotEmpty()) {
                    Surface(
                        color = Slate50,
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = "요청 메모: “${request.specialNotes}”",
                            fontSize = 12.sp,
                            color = Slate700,
                            modifier = Modifier.padding(8.dp)
                        )
                    }
                }
            }

            // Action Buttons for Pending Request
            if (request.status == RequestStatus.PENDING) {
                Spacer(modifier = Modifier.height(16.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    OutlinedButton(
                        onClick = onRejectClick,
                        modifier = Modifier
                            .weight(1f)
                            .testTag("reject_button_${request.requestId}"),
                        shape = RoundedCornerShape(10.dp)
                    ) {
                        Icon(Icons.Default.Close, contentDescription = null, modifier = Modifier.size(16.dp), tint = StatusRejected)
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("거절", color = StatusRejected, fontWeight = FontWeight.Bold)
                    }

                    Button(
                        onClick = onAccept,
                        modifier = Modifier
                            .weight(2f)
                            .testTag("accept_button_${request.requestId}"),
                        shape = RoundedCornerShape(10.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = TealPrimary)
                    ) {
                        Icon(Icons.Default.Check, contentDescription = null, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("요청 수락하기", fontWeight = FontWeight.Bold, color = White)
                    }
                }
            } else if (request.status == RequestStatus.ACCEPTED) {
                Spacer(modifier = Modifier.height(12.dp))
                Surface(
                    color = StatusAcceptedBg,
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier.padding(10.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(Icons.Default.Check, contentDescription = null, tint = StatusAccepted, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "수락 완료된 간병 일정입니다. 보호자님과 통화하여 준비사항을 확인하세요.",
                            fontSize = 12.sp,
                            color = StatusAccepted,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }
            }
        }
    }
}
