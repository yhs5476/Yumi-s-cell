package com.example.careplus.ui.screens

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
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.HelpOutline
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.CreditCard
import androidx.compose.material.icons.filled.DeleteOutline
import androidx.compose.material.icons.filled.Group
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material.icons.filled.SwapHoriz
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.careplus.data.model.UserRole
import com.example.careplus.ui.components.BrixProgressBar
import com.example.careplus.ui.components.TossBadge
import com.example.careplus.ui.components.TossCard
import com.example.careplus.ui.theme.TossBackground
import com.example.careplus.ui.theme.TossBlack
import com.example.careplus.ui.theme.TossBlue
import com.example.careplus.ui.theme.TossBlueLight
import com.example.careplus.ui.theme.TossBorder
import com.example.careplus.ui.theme.TossGray
import com.example.careplus.ui.theme.TossGreen
import com.example.careplus.ui.theme.TossSurface
import com.example.careplus.viewmodel.CarePlusViewModel

@Composable
fun MyPageScreen(
    viewModel: CarePlusViewModel,
    onBack: () -> Unit,
    onNavigateToContracts: () -> Unit
) {
    val role by viewModel.currentRole.collectAsState()
    val contracts by viewModel.allContracts.collectAsState()
    var pushNotificationEnabled by remember { mutableStateOf(true) }
    var contractToDelete by remember { mutableStateOf<com.example.careplus.data.local.ContractEntity?>(null) }

    if (contractToDelete != null) {
        androidx.compose.material3.AlertDialog(
            onDismissRequest = { contractToDelete = null },
            title = {
                Text(
                    text = "기록 삭제",
                    fontWeight = FontWeight.Bold,
                    color = TossBlack
                )
            },
            text = {
                Text(
                    text = "선택하신 '${contractToDelete?.location}' 돌봄 기록 및 AI Care Report를 삭제하시겠습니까?",
                    style = MaterialTheme.typography.bodyMedium,
                    color = TossGray
                )
            },
            confirmButton = {
                androidx.compose.material3.TextButton(
                    onClick = {
                        contractToDelete?.let { viewModel.deleteContract(it.contractId) }
                        contractToDelete = null
                    }
                ) {
                    Text("삭제", color = com.example.careplus.ui.theme.TossRed, fontWeight = FontWeight.Bold)
                }
            },
            dismissButton = {
                androidx.compose.material3.TextButton(
                    onClick = { contractToDelete = null }
                ) {
                    Text("취소", color = TossGray)
                }
            },
            containerColor = TossSurface,
            shape = RoundedCornerShape(20.dp)
        )
    }

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
                        text = "마이페이지",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = TossBlack,
                        modifier = Modifier.weight(1f)
                    )
                }
                HorizontalDivider(color = TossBorder)
            }
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            // 1. Profile Summary Card
            item {
                TossCard(
                    modifier = Modifier.fillMaxWidth(),
                    backgroundColor = TossSurface,
                    shape = RoundedCornerShape(22.dp)
                ) {
                    Column(modifier = Modifier.padding(20.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Box(
                                modifier = Modifier
                                    .size(56.dp)
                                    .clip(CircleShape)
                                    .background(TossBlueLight),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = "👤",
                                    fontSize = 28.sp
                                )
                            }
                            Spacer(modifier = Modifier.width(14.dp))
                            Column(modifier = Modifier.weight(1f)) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Text(
                                        text = "김민준 보호자님",
                                        style = MaterialTheme.typography.titleMedium,
                                        fontWeight = FontWeight.Bold,
                                        color = TossBlack
                                    )
                                    Spacer(modifier = Modifier.width(6.dp))
                                    TossBadge(
                                        text = "안심 보호자",
                                        isGreen = false,
                                        isHighlighted = true
                                    )
                                }
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    text = "010-****-5829 (본인인증 완료)",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = TossGray
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(16.dp))
                        HorizontalDivider(color = TossBorder)
                        Spacer(modifier = Modifier.height(14.dp))

                        // Guardian Quick Status Info
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(12.dp))
                                .background(TossBackground)
                                .padding(horizontal = 14.dp, vertical = 10.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text(
                                    text = "🛡️ 안심 돌봄 관리자",
                                    style = MaterialTheme.typography.bodyMedium,
                                    fontWeight = FontWeight.SemiBold,
                                    color = TossBlack
                                )
                            }
                            Text(
                                text = "가족 2명 연동 중 ➔",
                                style = MaterialTheme.typography.labelMedium,
                                fontWeight = FontWeight.Bold,
                                color = TossBlue
                            )
                        }
                    }
                }
            }

            // 2. Grape Brix (°Brix) Trust Metric Card
            item {
                TossCard(
                    modifier = Modifier.fillMaxWidth(),
                    backgroundColor = TossSurface,
                    shape = RoundedCornerShape(22.dp)
                ) {
                    Column(modifier = Modifier.padding(20.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "🍇 포도 당도 (°Brix) 신뢰 지수",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                color = TossBlack
                            )
                            TossBadge(text = "당도 24.0° 만점", isGreen = true)
                        }

                        Spacer(modifier = Modifier.height(16.dp))
                        BrixProgressBar(brixScore = 18.8f)
                        Spacer(modifier = Modifier.height(12.dp))

                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(12.dp))
                                .background(Color(0xFFF5F3FF))
                                .padding(12.dp)
                        ) {
                            Text(
                                text = "💡 Tip: Door-to-Door 트래킹 100% 완료 및 보호자 5점 평가 시 당도가 +0.5°Brix 상승합니다.",
                                style = MaterialTheme.typography.bodySmall,
                                color = Color(0xFF6D28D9)
                            )
                        }
                    }
                }
            }

            // 3. Care History & AI Care Report Archive Section
            item {
                TossCard(
                    modifier = Modifier.fillMaxWidth(),
                    backgroundColor = TossSurface,
                    shape = RoundedCornerShape(22.dp)
                ) {
                    Column(modifier = Modifier.padding(20.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    imageVector = Icons.Default.History,
                                    contentDescription = null,
                                    tint = TossBlue,
                                    modifier = Modifier.size(20.dp)
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = "지난 돌봄 & AI Care Report",
                                    style = MaterialTheme.typography.titleMedium,
                                    fontWeight = FontWeight.Bold,
                                    color = TossBlack
                                )
                            }
                            Text(
                                text = "총 ${contracts.size}건",
                                style = MaterialTheme.typography.bodySmall,
                                color = TossGray
                            )
                        }

                        Spacer(modifier = Modifier.height(12.dp))

                        if (contracts.isNotEmpty()) {
                            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                                contracts.forEach { contract ->
                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .clip(RoundedCornerShape(14.dp))
                                            .background(TossBackground)
                                            .padding(horizontal = 14.dp, vertical = 12.dp),
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Column(
                                            modifier = Modifier
                                                .weight(1f)
                                                .clickable { onNavigateToContracts() }
                                        ) {
                                            Row(verticalAlignment = Alignment.CenterVertically) {
                                                Text(
                                                    text = contract.location,
                                                    style = MaterialTheme.typography.bodyMedium,
                                                    fontWeight = FontWeight.Bold,
                                                    color = TossBlack
                                                )
                                                Spacer(modifier = Modifier.width(6.dp))
                                                TossBadge(text = "AI 리포트", isGreen = true)
                                            }
                                            Spacer(modifier = Modifier.height(3.dp))
                                            Text(
                                                text = "${contract.caregiverName} • ${contract.dates}",
                                                style = MaterialTheme.typography.bodySmall,
                                                color = TossGray
                                            )
                                        }

                                        Row(verticalAlignment = Alignment.CenterVertically) {
                                            IconButton(
                                                onClick = { contractToDelete = contract },
                                                modifier = Modifier.size(32.dp)
                                            ) {
                                                Icon(
                                                    imageVector = Icons.Default.DeleteOutline,
                                                    contentDescription = "삭제",
                                                    tint = com.example.careplus.ui.theme.TossRed,
                                                    modifier = Modifier.size(20.dp)
                                                )
                                            }
                                            Spacer(modifier = Modifier.width(4.dp))
                                            Icon(
                                                imageVector = Icons.Default.ChevronRight,
                                                contentDescription = null,
                                                tint = TossGray,
                                                modifier = Modifier.size(20.dp)
                                            )
                                        }
                                    }
                                }
                            }
                        } else {
                            Text(
                                text = "진행된 돌봄 내역이 없습니다.",
                                style = MaterialTheme.typography.bodySmall,
                                color = TossGray,
                                modifier = Modifier.padding(vertical = 8.dp)
                            )
                        }
                    }
                }
            }

            // 4. Payment & Escrow Management Card
            item {
                TossCard(
                    modifier = Modifier.fillMaxWidth(),
                    backgroundColor = TossSurface,
                    shape = RoundedCornerShape(22.dp)
                ) {
                    Column(modifier = Modifier.padding(20.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.CreditCard,
                                contentDescription = null,
                                tint = TossBlue,
                                modifier = Modifier.size(20.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "결제 수단 및 안심 에스크로",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                color = TossBlack
                            )
                        }

                        Spacer(modifier = Modifier.height(14.dp))
                        MyPageMenuItem(title = "등록된 결제 수단 (토스페이 / 카드)", subtitle = "기본 결제: 토스페이 ⚡")
                        MyPageMenuItem(title = "에스크로 안전 보관 내역", subtitle = "현재 보관 중 1건 (441,000원)")
                    }
                }
            }

            // 5. Family Sharing & Support
            item {
                TossCard(
                    modifier = Modifier.fillMaxWidth(),
                    backgroundColor = TossSurface,
                    shape = RoundedCornerShape(22.dp)
                ) {
                    Column(modifier = Modifier.padding(20.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.Group,
                                contentDescription = null,
                                tint = TossBlue,
                                modifier = Modifier.size(20.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "가족 관리 & 고객센터",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                color = TossBlack
                            )
                        }

                        Spacer(modifier = Modifier.height(14.dp))
                        MyPageMenuItem(title = "가족 공동 관리자 설정", subtitle = "연동된 가족 2명 (김지수, 김서연)")
                        
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 10.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    imageVector = Icons.Default.Notifications,
                                    contentDescription = null,
                                    tint = TossGray,
                                    modifier = Modifier.size(18.dp)
                                )
                                Spacer(modifier = Modifier.width(10.dp))
                                Text("실시간 돌봄 푸시 알림", style = MaterialTheme.typography.bodyMedium, color = TossBlack)
                            }
                            Switch(
                                checked = pushNotificationEnabled,
                                onCheckedChange = { pushNotificationEnabled = it },
                                colors = SwitchDefaults.colors(checkedThumbColor = Color.White, checkedTrackColor = TossBlue)
                            )
                        }

                        MyPageMenuItem(title = "24시간 1:1 카카오 챗봇 상담", subtitle = "응답시간 평균 1분 이내 💬")
                        MyPageMenuItem(title = "약관 및 개인정보 처리방침", subtitle = "v1.2.0")
                    }
                }
            }
        }
    }
}

@Composable
private fun MyPageMenuItem(
    title: String,
    subtitle: String? = null
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { }
            .padding(vertical = 10.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(text = title, style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.Medium, color = TossBlack)
            if (subtitle != null) {
                Spacer(modifier = Modifier.height(2.dp))
                Text(text = subtitle, style = MaterialTheme.typography.bodySmall, color = TossGray)
            }
        }
        Icon(
            imageVector = Icons.Default.ChevronRight,
            contentDescription = null,
            tint = TossGray,
            modifier = Modifier.size(20.dp)
        )
    }
}
