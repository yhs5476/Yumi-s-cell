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
import androidx.compose.material.icons.automirrored.filled.Chat
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.DirectionsCar
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import com.example.careplus.data.local.CareBidEntity
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
import com.example.careplus.ui.theme.TossDisabled
import com.example.careplus.ui.theme.TossGray
import com.example.careplus.ui.theme.TossGrayLight
import com.example.careplus.ui.theme.TossGreen
import com.example.careplus.ui.theme.TossGreenLight
import com.example.careplus.ui.theme.TossSurface
import com.example.careplus.ui.theme.TossYellow
import com.example.careplus.viewmodel.BidSortOption
import com.example.careplus.viewmodel.CarePlusViewModel

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun BidFeedScreen(
    requestId: Long,
    viewModel: CarePlusViewModel,
    onBack: () -> Unit,
    onNavigateToChat: (Long) -> Unit
) {
    val requests by viewModel.allRequests.collectAsState()
    val request = requests.firstOrNull { it.id == requestId } ?: requests.firstOrNull()

    val bidsFlow = remember(requestId) { viewModel.getBidsForRequest(requestId) }
    val bids by bidsFlow.collectAsState(initial = emptyList())

    val selectedSort by viewModel.selectedBidSort.collectAsState()
    val filterCert by viewModel.filterCertOnly.collectAsState()
    val filterInsurance by viewModel.filterInsuranceOnly.collectAsState()
    val filterGender by viewModel.filterSameGenderOnly.collectAsState()

    var showSortMenu by remember { mutableStateOf(false) }

    val filteredBids = bids.filter { bid ->
        if (filterCert && !bid.certList.any { it.contains("요양보호사") }) return@filter false
        if (filterInsurance && !bid.insuranceYn) return@filter false
        if (filterGender && request != null && bid.gender != request.gender) return@filter false
        true
    }.let { list ->
        when (selectedSort) {
            BidSortOption.LOWEST_PRICE -> list.sortedBy { it.dailyPrice }
            BidSortOption.HIGHEST_RATING -> list.sortedByDescending { it.rating }
            BidSortOption.NEAREST_DISTANCE -> list.sortedBy { it.distanceKm }
            BidSortOption.MOST_EXPERIENCE -> list.sortedByDescending { it.careerYears }
        }
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
                        text = "실시간 견적 비교",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.weight(1f)
                    )
                    TossBadge(text = "${filteredBids.size}건 인입", isHighlighted = true)
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
            // Request Summary Card
            if (request != null) {
                item {
                    Box(modifier = Modifier.padding(16.dp)) {
                        TossCard(
                            modifier = Modifier.fillMaxWidth(),
                            backgroundColor = TossSurface,
                            shape = RoundedCornerShape(20.dp)
                        ) {
                            Column(modifier = Modifier.padding(18.dp)) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        text = request.hospitalName,
                                        style = MaterialTheme.typography.titleMedium,
                                        fontWeight = FontWeight.Bold
                                    )
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Icon(
                                            imageVector = Icons.Default.AccessTime,
                                            contentDescription = null,
                                            tint = TossBlue,
                                            modifier = Modifier.size(14.dp)
                                        )
                                        Spacer(modifier = Modifier.width(4.dp))
                                        Text(
                                            text = "23시간 남음",
                                            fontSize = 12.sp,
                                            color = TossBlue,
                                            fontWeight = FontWeight.SemiBold
                                        )
                                    }
                                }
                                Spacer(modifier = Modifier.height(8.dp))
                                Text(
                                    text = "환자 상태: ${request.gender.label} · ${request.ageRange.label} · ${request.mobility.label} (${request.weightRange.label})",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = TossGray
                                )
                                Spacer(modifier = Modifier.height(10.dp))
                                FlowRow(
                                    horizontalArrangement = Arrangement.spacedBy(6.dp),
                                    verticalArrangement = Arrangement.spacedBy(6.dp)
                                ) {
                                    request.specialNeeds.forEach { need ->
                                        TossBadge(text = need)
                                    }
                                }
                            }
                        }
                    }
                }
            }

            // Filter & Sort Bar
            item {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(TossSurface)
                        .padding(vertical = 10.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box {
                            Row(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(8.dp))
                                    .clickable { showSortMenu = true }
                                    .padding(vertical = 4.dp, horizontal = 6.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    imageVector = Icons.Default.FilterList,
                                    contentDescription = null,
                                    tint = TossBlue,
                                    modifier = Modifier.size(16.dp)
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(
                                    text = selectedSort.label,
                                    style = MaterialTheme.typography.bodyMedium,
                                    fontWeight = FontWeight.Bold,
                                    color = TossBlue
                                )
                            }

                            DropdownMenu(
                                expanded = showSortMenu,
                                onDismissRequest = { showSortMenu = false }
                            ) {
                                BidSortOption.values().forEach { option ->
                                    DropdownMenuItem(
                                        text = {
                                            Text(
                                                text = option.label,
                                                fontWeight = if (option == selectedSort) FontWeight.Bold else FontWeight.Normal
                                            )
                                        },
                                        onClick = {
                                            viewModel.setBidSort(option)
                                            showSortMenu = false
                                        }
                                    )
                                }
                            }
                        }

                        Text(
                            text = "총 ${filteredBids.size}명의 케어메이트",
                            style = MaterialTheme.typography.bodySmall,
                            color = TossGray
                        )
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    LazyRow(
                        contentPadding = PaddingValues(horizontal = 16.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        item {
                            FilterChipItem(
                                label = "요양보호사 1급",
                                isSelected = filterCert,
                                onClick = { viewModel.toggleFilterCert() }
                            )
                        }
                        item {
                            FilterChipItem(
                                label = "배상보험 가입",
                                isSelected = filterInsurance,
                                onClick = { viewModel.toggleFilterInsurance() }
                            )
                        }
                        item {
                            FilterChipItem(
                                label = "동일 성별만",
                                isSelected = filterGender,
                                onClick = { viewModel.toggleFilterGender() }
                            )
                        }
                    }
                }
            }

            item {
                Spacer(modifier = Modifier.height(12.dp))
            }

            // Bid Cards List
            if (filteredBids.isEmpty()) {
                item {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(40.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text("⏳", fontSize = 40.sp)
                            Spacer(modifier = Modifier.height(12.dp))
                            Text(
                                text = "조건에 맞는 견적을 검색 중입니다",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = "필터를 조정하거나 잠시만 기다려 주세요",
                                style = MaterialTheme.typography.bodySmall,
                                color = TossGray
                            )
                        }
                    }
                }
            } else {
                items(filteredBids) { bid ->
                    BidFeedCard(
                        bid = bid,
                        onChatClick = { onNavigateToChat(bid.bidId) }
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                }
            }
        }
    }
}

@Composable
fun FilterChipItem(
    label: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(16.dp))
            .background(if (isSelected) TossBlue else TossBackground)
            .border(1.dp, if (isSelected) TossBlue else TossBorder, RoundedCornerShape(16.dp))
            .clickable(onClick = onClick)
            .padding(horizontal = 12.dp, vertical = 6.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            if (isSelected) {
                Icon(
                    imageVector = Icons.Default.Check,
                    contentDescription = null,
                    tint = TossSurface,
                    modifier = Modifier.size(12.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
            }
            Text(
                text = label,
                fontSize = 12.sp,
                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                color = if (isSelected) TossSurface else TossBlack
            )
        }
    }
}

@Composable
fun BidFeedCard(
    bid: CareBidEntity,
    onChatClick: () -> Unit
) {
    Box(modifier = Modifier.padding(horizontal = 16.dp)) {
        TossCard(
            modifier = Modifier.fillMaxWidth(),
            backgroundColor = TossSurface,
            shape = RoundedCornerShape(22.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp)
            ) {
                // Top Badges & Time
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                        bid.certList.forEach { cert ->
                            TossBadge(text = cert, isHighlighted = true)
                        }
                        if (bid.insuranceYn) {
                            TossBadge(text = "보험가입", isGreen = true)
                        }
                    }
                    Text(
                        text = "5분 전 인입",
                        fontSize = 11.sp,
                        color = TossGrayLight
                    )
                }

                Spacer(modifier = Modifier.height(14.dp))

                // Profile Row
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(50.dp)
                            .clip(CircleShape)
                            .background(TossBlueLight),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = if (bid.gender.name == "FEMALE") "👩‍⚕️" else "👨‍⚕️",
                            fontSize = 26.sp
                        )
                    }
                    Spacer(modifier = Modifier.width(14.dp))
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "${bid.caregiverName} (경력 ${bid.careerYears}년)",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = TossBlack
                        )
                        Spacer(modifier = Modifier.height(3.dp))
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.Star,
                                contentDescription = null,
                                tint = TossYellow,
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(modifier = Modifier.width(2.dp))
                            Text(
                                text = "${bid.rating} (후기 ${bid.reviewCount}개)",
                                style = MaterialTheme.typography.bodySmall,
                                fontWeight = FontWeight.Bold,
                                color = TossBlack
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "•",
                                color = TossGrayLight,
                                fontSize = 10.sp
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Icon(
                                imageVector = Icons.Default.DirectionsCar,
                                contentDescription = null,
                                tint = TossGray,
                                modifier = Modifier.size(14.dp)
                            )
                            Spacer(modifier = Modifier.width(3.dp))
                            Text(
                                text = "${bid.distanceKm}km (${bid.travelTimeMinutes}분)",
                                style = MaterialTheme.typography.bodySmall,
                                color = TossGray
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))
                com.example.careplus.ui.components.BrixGaugeBadge(brixScore = bid.brixScore)

                Spacer(modifier = Modifier.height(14.dp))

                // Pitch message quote box
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(12.dp))
                        .background(TossBackground)
                        .padding(12.dp)
                ) {
                    Text(
                        text = "\"${bid.pitchMessage}\"",
                        style = MaterialTheme.typography.bodyMedium,
                        color = TossBlack
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))
                HorizontalDivider(color = TossBorder)
                Spacer(modifier = Modifier.height(14.dp))

                // Price and CTA Row
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = "제안 금액 (1일 기준)",
                            style = MaterialTheme.typography.bodySmall,
                            color = TossGray
                        )
                        Spacer(modifier = Modifier.height(2.dp))
                        Text(
                            text = "${String.format("%,d", bid.dailyPrice)}원",
                            style = MaterialTheme.typography.headlineMedium,
                            fontWeight = FontWeight.Bold,
                            color = TossBlack
                        )
                    }

                    Button(
                        onClick = onChatClick,
                        shape = RoundedCornerShape(14.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = TossBlue),
                        contentPadding = PaddingValues(horizontal = 14.dp, vertical = 0.dp),
                        modifier = Modifier
                            .height(44.dp)
                            .testTag("chat_bid_${bid.bidId}")
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.Chat,
                                contentDescription = null,
                                modifier = Modifier.size(16.dp),
                                tint = TossSurface
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "1:1 채팅 상담",
                                style = MaterialTheme.typography.labelLarge,
                                fontWeight = FontWeight.Bold,
                                color = TossSurface,
                                fontSize = 13.sp,
                                maxLines = 1,
                                softWrap = false
                            )
                        }
                    }
                }
            }
        }
    }
}
