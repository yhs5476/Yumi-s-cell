package com.example.careplus.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.People
import androidx.compose.material.icons.filled.Receipt
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material.icons.filled.SwapHoriz
import androidx.compose.material.icons.filled.VerifiedUser
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
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
import com.example.careplus.data.local.CareRequestEntity
import com.example.careplus.data.model.CareType
import com.example.careplus.data.model.RequestStatus
import com.example.careplus.data.model.UserRole
import com.example.careplus.ui.components.TossBadge
import com.example.careplus.ui.components.TossButton
import com.example.careplus.ui.components.TossCard
import com.example.careplus.ui.components.TossLiveTicker
import com.example.careplus.ui.theme.TossBackground
import com.example.careplus.ui.theme.TossBlack
import com.example.careplus.ui.theme.TossBlue
import com.example.careplus.ui.theme.TossBlueDark
import com.example.careplus.ui.theme.TossBlueLight
import com.example.careplus.ui.theme.TossBorder
import com.example.careplus.ui.theme.TossGray
import com.example.careplus.ui.theme.TossGrayLight
import com.example.careplus.ui.theme.TossGreen
import com.example.careplus.ui.theme.TossGreenLight
import com.example.careplus.ui.theme.TossSurface
import com.example.careplus.viewmodel.CarePlusViewModel

@Composable
fun HomeScreen(
    viewModel: CarePlusViewModel,
    onNavigateToRequestForm: (CareType) -> Unit,
    onNavigateToBidFeed: (Long) -> Unit,
    onNavigateToContracts: () -> Unit,
    onNavigateToRadar: () -> Unit
) {
    val role by viewModel.currentRole.collectAsState()
    val location by viewModel.currentLocation.collectAsState()
    val requests by viewModel.allRequests.collectAsState()
    val contracts by viewModel.allContracts.collectAsState()

    var showLocationMenu by remember { mutableStateOf(false) }
    val locations = listOf(
        "서울시 송파구 잠실동",
        "서울시 강남구 역삼동",
        "서울시 종로구 대학로",
        "경기도 성남시 분당구",
        "서울시 서대문구 신촌동"
    )

    val activeRequest = requests.firstOrNull { it.status == RequestStatus.OPEN }

    Scaffold(
        containerColor = TossBackground,
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    if (role == UserRole.GUARDIAN) {
                        viewModel.updateForm { it.copy(careType = CareType.HOSPITAL) }
                        onNavigateToRequestForm(CareType.HOSPITAL)
                    } else {
                        onNavigateToRadar()
                    }
                },
                containerColor = TossBlue,
                contentColor = TossSurface,
                shape = RoundedCornerShape(18.dp),
                modifier = Modifier.testTag("fab_main_action")
            ) {
                Row(
                    modifier = Modifier.padding(horizontal = 16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = if (role == UserRole.GUARDIAN) Icons.Default.Add else Icons.Default.LocationOn,
                        contentDescription = null
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = if (role == UserRole.GUARDIAN) "간병 공고 등록" else "실시간 공고 탐색",
                        fontWeight = FontWeight.Bold,
                        fontSize = 15.sp
                    )
                }
            }
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentPadding = PaddingValues(bottom = 96.dp)
        ) {
            // Header: Location & Role Switcher
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(TossSurface)
                        .padding(horizontal = 20.dp, vertical = 16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box {
                        Row(
                            modifier = Modifier
                                .clip(RoundedCornerShape(12.dp))
                                .clickable { showLocationMenu = true }
                                .padding(vertical = 4.dp, horizontal = 4.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = location,
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                color = TossBlack
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Icon(
                                imageVector = Icons.Default.KeyboardArrowDown,
                                contentDescription = "위치 변경",
                                tint = TossGray
                            )
                        }

                        DropdownMenu(
                            expanded = showLocationMenu,
                            onDismissRequest = { showLocationMenu = false }
                        ) {
                            locations.forEach { loc ->
                                DropdownMenuItem(
                                    text = { Text(loc, fontWeight = if (loc == location) FontWeight.Bold else FontWeight.Normal) },
                                    onClick = {
                                        viewModel.setLocation(loc)
                                        showLocationMenu = false
                                    }
                                )
                            }
                        }
                    }

                    // Role Switch Button
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(20.dp))
                            .background(if (role == UserRole.GUARDIAN) TossBlueLight else TossGreenLight)
                            .clickable { viewModel.toggleRole() }
                            .padding(horizontal = 12.dp, vertical = 6.dp)
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.SwapHoriz,
                                contentDescription = "역할 전환",
                                tint = if (role == UserRole.GUARDIAN) TossBlue else TossGreen,
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = if (role == UserRole.GUARDIAN) "보호자 모드" else "간병인 모드",
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold,
                                color = if (role == UserRole.GUARDIAN) TossBlue else TossGreen
                            )
                        }
                    }
                }
            }

            // Realtime Live Ticker
            item {
                Box(modifier = Modifier.padding(horizontal = 16.dp, vertical = 10.dp)) {
                    TossLiveTicker()
                }
            }

            // Active Contract Door-to-Door Banner (if any contract exists)
            val activeContract = contracts.firstOrNull()
            if (activeContract != null && role == UserRole.GUARDIAN) {
                item {
                    Box(modifier = Modifier.padding(horizontal = 16.dp, vertical = 6.dp)) {
                        TossCard(
                            modifier = Modifier.fillMaxWidth(),
                            backgroundColor = Color(0xFF6D28D9),
                            onClick = { onNavigateToContracts() }
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(20.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Column(modifier = Modifier.weight(1f)) {
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Box(
                                            modifier = Modifier
                                                .clip(RoundedCornerShape(6.dp))
                                                .background(TossSurface.copy(alpha = 0.25f))
                                                .padding(horizontal = 8.dp, vertical = 3.dp)
                                        ) {
                                            Text(
                                                text = "🚗 Door-to-Door 트래킹",
                                                color = TossSurface,
                                                fontSize = 11.sp,
                                                fontWeight = FontWeight.Bold
                                            )
                                        }
                                        Spacer(modifier = Modifier.width(8.dp))
                                        Text(
                                            text = activeContract.caregiverName,
                                            color = TossSurface.copy(alpha = 0.9f),
                                            fontSize = 13.sp,
                                            fontWeight = FontWeight.Medium
                                        )
                                    }
                                    Spacer(modifier = Modifier.height(8.dp))
                                    Text(
                                        text = "${activeContract.journeyStep.emoji} 현재 단계: ${activeContract.journeyStep.title}",
                                        style = MaterialTheme.typography.titleMedium,
                                        fontWeight = FontWeight.Bold,
                                        color = TossSurface
                                    )
                                    Spacer(modifier = Modifier.height(2.dp))
                                    Text(
                                        text = "터치하여 6단계 트래킹 및 ✨ AI Care Report 확인 👉",
                                        style = MaterialTheme.typography.bodySmall,
                                        color = TossSurface.copy(alpha = 0.85f)
                                    )
                                }
                                Icon(
                                    imageVector = Icons.Default.ChevronRight,
                                    contentDescription = null,
                                    tint = TossSurface,
                                    modifier = Modifier.size(28.dp)
                                )
                            }
                        }
                    }
                }
            }

            // Active Request Bidding Banner (Only if no active contract matched yet)
            if (activeRequest != null && activeContract == null && role == UserRole.GUARDIAN) {
                item {
                    Box(modifier = Modifier.padding(horizontal = 16.dp, vertical = 6.dp)) {
                        TossCard(
                            modifier = Modifier.fillMaxWidth(),
                            backgroundColor = TossBlue,
                            onClick = { onNavigateToBidFeed(activeRequest.id) }
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(20.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Column(modifier = Modifier.weight(1f)) {
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Box(
                                            modifier = Modifier
                                                .clip(RoundedCornerShape(6.dp))
                                                .background(TossSurface.copy(alpha = 0.25f))
                                                .padding(horizontal = 8.dp, vertical = 3.dp)
                                        ) {
                                            Text(
                                                text = "실시간 입찰 진행 중",
                                                color = TossSurface,
                                                fontSize = 11.sp,
                                                fontWeight = FontWeight.Bold
                                            )
                                        }
                                        Spacer(modifier = Modifier.width(8.dp))
                                        Text(
                                            text = activeRequest.hospitalName,
                                            color = TossSurface.copy(alpha = 0.9f),
                                            fontSize = 13.sp,
                                            fontWeight = FontWeight.Medium
                                        )
                                    }
                                    Spacer(modifier = Modifier.height(8.dp))
                                    Text(
                                        text = "3명의 케어메이트가 견적을 보냈어요",
                                        style = MaterialTheme.typography.titleMedium,
                                        fontWeight = FontWeight.Bold,
                                        color = TossSurface
                                    )
                                    Spacer(modifier = Modifier.height(2.dp))
                                    Text(
                                        text = "터치하여 견적 금액과 프로필을 비교해 보세요 👉",
                                        style = MaterialTheme.typography.bodySmall,
                                        color = TossSurface.copy(alpha = 0.85f)
                                    )
                                }
                                Icon(
                                    imageVector = Icons.Default.ChevronRight,
                                    contentDescription = null,
                                    tint = TossSurface,
                                    modifier = Modifier.size(28.dp)
                                )
                            }
                        }
                    }
                }
            }

            // Hero Section: "어떤 돌봄이 필요하신가요?"
            item {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp, vertical = 16.dp)
                ) {
                    Text(
                        text = if (role == UserRole.GUARDIAN) "어떤 돌봄이\n필요하신가요?" else "전문 분야에 맞는\n공고를 수주하세요",
                        style = MaterialTheme.typography.headlineLarge,
                        fontWeight = FontWeight.Bold,
                        color = TossBlack
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = if (role == UserRole.GUARDIAN) "3분 만에 간편 등록하고 반경 내 맞춤 견적을 비교하세요" else "내 이동 반경 및 전문 자격에 맞는 실시간 요청서",
                        style = MaterialTheme.typography.bodyMedium,
                        color = TossGray
                    )
                }
            }

            // 3 Large Pictogram Category Cards
            item {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    CareType.values().forEach { careType ->
                        TossCard(
                            modifier = Modifier.fillMaxWidth(),
                            backgroundColor = TossSurface,
                            onClick = {
                                if (role == UserRole.GUARDIAN) {
                                    viewModel.updateForm { it.copy(careType = careType) }
                                    onNavigateToRequestForm(careType)
                                } else {
                                    onNavigateToRadar()
                                }
                            }
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(20.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(54.dp)
                                        .clip(CircleShape)
                                        .background(TossBackground),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(text = careType.emoji, fontSize = 26.sp)
                                }
                                Spacer(modifier = Modifier.width(16.dp))
                                Column(modifier = Modifier.weight(1f)) {
                                    Text(
                                        text = careType.title,
                                        style = MaterialTheme.typography.titleMedium,
                                        fontWeight = FontWeight.Bold,
                                        color = TossBlack
                                    )
                                    Spacer(modifier = Modifier.height(4.dp))
                                    Text(
                                        text = careType.subtitle,
                                        style = MaterialTheme.typography.bodyMedium,
                                        color = TossGray
                                    )
                                }
                                Icon(
                                    imageVector = Icons.Default.ChevronRight,
                                    contentDescription = null,
                                    tint = TossGrayLight,
                                    modifier = Modifier.size(22.dp)
                                )
                            }
                        }
                    }
                }
            }

            // Quick Menu Bar: Active Contracts & Care Radar
            item {
                Spacer(modifier = Modifier.height(16.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    TossCard(
                        modifier = Modifier.weight(1f),
                        backgroundColor = TossSurface,
                        onClick = onNavigateToContracts
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Box(
                                modifier = Modifier
                                    .size(36.dp)
                                    .clip(CircleShape)
                                    .background(TossBlueLight),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Receipt,
                                    contentDescription = null,
                                    tint = TossBlue,
                                    modifier = Modifier.size(20.dp)
                                )
                            }
                            Spacer(modifier = Modifier.height(10.dp))
                            Text("계약 및 에스크로", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                            Spacer(modifier = Modifier.height(2.dp))
                            Text("${contracts.size}건 관리 중", style = MaterialTheme.typography.bodySmall, color = TossGray)
                        }
                    }

                    TossCard(
                        modifier = Modifier.weight(1f),
                        backgroundColor = TossSurface,
                        onClick = onNavigateToRadar
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Box(
                                modifier = Modifier
                                    .size(36.dp)
                                    .clip(CircleShape)
                                    .background(TossGreenLight),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Default.LocationOn,
                                    contentDescription = null,
                                    tint = TossGreen,
                                    modifier = Modifier.size(20.dp)
                                )
                            }
                            Spacer(modifier = Modifier.height(10.dp))
                            Text("공고 레이더", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                            Spacer(modifier = Modifier.height(2.dp))
                            Text("반경 10km 이내", style = MaterialTheme.typography.bodySmall, color = TossGray)
                        }
                    }
                }
            }

            // Trust & Escrow Guarantee Banner
            item {
                Spacer(modifier = Modifier.height(16.dp))
                Box(modifier = Modifier.padding(horizontal = 16.dp)) {
                    TossCard(
                        modifier = Modifier.fillMaxWidth(),
                        backgroundColor = TossSurface,
                        shape = RoundedCornerShape(20.dp)
                    ) {
                        Column(modifier = Modifier.padding(20.dp)) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    imageVector = Icons.Default.Shield,
                                    contentDescription = null,
                                    tint = TossBlue,
                                    modifier = Modifier.size(22.dp)
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = "포도당 3대 안심 보증",
                                    style = MaterialTheme.typography.titleMedium,
                                    fontWeight = FontWeight.Bold,
                                    color = TossBlack
                                )
                            }
                            Spacer(modifier = Modifier.height(14.dp))
                            HorizontalDivider(color = TossBorder)
                            Spacer(modifier = Modifier.height(14.dp))

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                    Text("🪪", fontSize = 24.sp)
                                    Spacer(modifier = Modifier.height(4.dp))
                                    Text("100% 실명인증", style = MaterialTheme.typography.bodySmall, fontWeight = FontWeight.SemiBold)
                                    Text("신원 검증 완료", style = MaterialTheme.typography.bodySmall, fontSize = 11.sp, color = TossGray)
                                }
                                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                    Text("🛡️", fontSize = 24.sp)
                                    Spacer(modifier = Modifier.height(4.dp))
                                    Text("1억원 책임보험", style = MaterialTheme.typography.bodySmall, fontWeight = FontWeight.SemiBold)
                                    Text("안심 케어 보증", style = MaterialTheme.typography.bodySmall, fontSize = 11.sp, color = TossGray)
                                }
                                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                    Text("🔒", fontSize = 24.sp)
                                    Spacer(modifier = Modifier.height(4.dp))
                                    Text("에스크로 금고", style = MaterialTheme.typography.bodySmall, fontWeight = FontWeight.SemiBold)
                                    Text("종료 후 안전 정산", style = MaterialTheme.typography.bodySmall, fontSize = 11.sp, color = TossGray)
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
