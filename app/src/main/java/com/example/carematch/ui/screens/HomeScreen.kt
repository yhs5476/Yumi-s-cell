package com.example.carematch.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Speed
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.carematch.R
import com.example.carematch.data.model.CaregiverProfile
import com.example.carematch.ui.components.CaregiverCard
import com.example.carematch.ui.components.FilterBottomSheet
import com.example.carematch.ui.theme.*
import com.example.carematch.ui.viewmodel.FilterState
import com.example.carematch.ui.viewmodel.SortOption

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun HomeScreen(
    caregivers: List<CaregiverProfile>,
    filterState: FilterState,
    onSearchChange: (String) -> Unit,
    onRegionSelect: (String) -> Unit,
    onSpecialtyToggle: (String) -> Unit,
    onCertificationToggle: (String) -> Unit,
    onMaxDailyPayChange: (Int) -> Unit,
    onSortOptionChange: (SortOption) -> Unit,
    onResetFilters: () -> Unit,
    onCaregiverClick: (Long) -> Unit,
    onRequestClick: (Long) -> Unit,
    modifier: Modifier = Modifier
) {
    var showFilterSheet by remember { mutableStateOf(false) }

    val quickRegions = listOf("전체", "서울 강남/서초", "서울 송파/강동", "경기 성남/분당", "인천", "부산")
    val quickSpecialties = listOf("석션(가래흡인)", "치매 전문 돌봄", "와상 환자 케어", "재활운동 보조", "콧줄(L-tube) 피딩")

    val activeFilterCount = (if (filterState.selectedRegion != "전체") 1 else 0) +
            filterState.selectedSpecialties.size +
            filterState.selectedCertifications.size +
            (if (filterState.maxDailyPay < 200000) 1 else 0)

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(Slate50),
        contentPadding = PaddingValues(bottom = 24.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        // Hero Banner
        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = TealDark),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Box(modifier = Modifier.fillMaxWidth()) {
                    // Background Image
                    Image(
                        painter = painterResource(id = R.drawable.care_hero),
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(160.dp),
                        contentScale = ContentScale.Crop,
                        alpha = 0.35f
                    )

                    // Gradient Overlay
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(160.dp)
                            .background(
                                Brush.horizontalGradient(
                                    colors = listOf(
                                        TealDark.copy(alpha = 0.95f),
                                        TealDark.copy(alpha = 0.7f),
                                        Color.Transparent
                                    )
                                )
                            )
                    )

                    Column(
                        modifier = Modifier
                            .padding(20.dp)
                            .align(Alignment.CenterStart)
                    ) {
                        Surface(
                            color = CoralSecondary,
                            shape = RoundedCornerShape(6.dp)
                        ) {
                            Text(
                                text = "1:1 맞춤 지정 간병",
                                color = White,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                            )
                        }

                        Spacer(modifier = Modifier.height(8.dp))

                        Text(
                            text = "내 환자에게 딱 맞는\n검증된 전문 간병인 탐색",
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold,
                            color = White,
                            lineHeight = 24.sp
                        )

                        Spacer(modifier = Modifier.height(6.dp))

                        Text(
                            text = "경력·자격증·희망일당을 투명하게 확인하고 직접 신청하세요",
                            style = MaterialTheme.typography.bodySmall,
                            color = White.copy(alpha = 0.9f)
                        )
                    }
                }
            }
        }

        // Trust & Quick Stats Bar
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Surface(
                    modifier = Modifier.weight(1f),
                    color = White,
                    shape = RoundedCornerShape(12.dp),
                    border = androidx.compose.foundation.BorderStroke(1.dp, Slate200)
                ) {
                    Row(
                        modifier = Modifier.padding(vertical = 10.dp, horizontal = 10.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Icon(Icons.Default.Security, contentDescription = null, tint = TealPrimary, modifier = Modifier.size(18.dp))
                        Spacer(modifier = Modifier.width(6.dp))
                        Column {
                            Text("100% 검증", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = Slate900)
                            Text("신원·자격증 확인", fontSize = 10.sp, color = Slate500)
                        }
                    }
                }

                Surface(
                    modifier = Modifier.weight(1f),
                    color = White,
                    shape = RoundedCornerShape(12.dp),
                    border = androidx.compose.foundation.BorderStroke(1.dp, Slate200)
                ) {
                    Row(
                        modifier = Modifier.padding(vertical = 10.dp, horizontal = 10.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Icon(Icons.Default.Speed, contentDescription = null, tint = CoralSecondary, modifier = Modifier.size(18.dp))
                        Spacer(modifier = Modifier.width(6.dp))
                        Column {
                            Text("평균 15분 응답", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = Slate900)
                            Text("빠른 매칭 수락", fontSize = 10.sp, color = Slate500)
                        }
                    }
                }

                Surface(
                    modifier = Modifier.weight(1f),
                    color = White,
                    shape = RoundedCornerShape(12.dp),
                    border = androidx.compose.foundation.BorderStroke(1.dp, Slate200)
                ) {
                    Row(
                        modifier = Modifier.padding(vertical = 10.dp, horizontal = 10.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Icon(Icons.Default.Star, contentDescription = null, tint = Color(0xFFF59E0B), modifier = Modifier.size(18.dp))
                        Spacer(modifier = Modifier.width(6.dp))
                        Column {
                            Text("평점 4.9점", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = Slate900)
                            Text("실제 보호자 후기", fontSize = 10.sp, color = Slate500)
                        }
                    }
                }
            }
        }

        // Search Bar & Filter Button
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                OutlinedTextField(
                    value = filterState.searchQuery,
                    onValueChange = onSearchChange,
                    modifier = Modifier
                        .weight(1f)
                        .testTag("search_input"),
                    placeholder = { Text("간병인 이름, 지역, 질환, 석션 등 검색", fontSize = 13.sp, color = Slate400) },
                    leadingIcon = {
                        Icon(Icons.Default.Search, contentDescription = "검색", tint = TealPrimary)
                    },
                    trailingIcon = {
                        if (filterState.searchQuery.isNotEmpty()) {
                            IconButton(onClick = { onSearchChange("") }) {
                                Icon(Icons.Default.Clear, contentDescription = "지우기", tint = Slate400)
                            }
                        }
                    },
                    singleLine = true,
                    shape = RoundedCornerShape(12.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = White,
                        unfocusedContainerColor = White,
                        focusedBorderColor = TealPrimary,
                        unfocusedBorderColor = Slate200
                    )
                )

                Surface(
                    modifier = Modifier
                        .size(52.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .clickable { showFilterSheet = true }
                        .testTag("filter_button"),
                    color = if (activeFilterCount > 0) TealContainer else White,
                    border = androidx.compose.foundation.BorderStroke(
                        1.dp,
                        if (activeFilterCount > 0) TealPrimary else Slate200
                    )
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        BadgedBox(
                            badge = {
                                if (activeFilterCount > 0) {
                                    Badge(
                                        containerColor = TealPrimary,
                                        contentColor = White
                                    ) {
                                        Text("$activeFilterCount")
                                    }
                                }
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Default.FilterList,
                                contentDescription = "필터",
                                tint = if (activeFilterCount > 0) TealDark else Slate700,
                                modifier = Modifier.size(24.dp)
                            )
                        }
                    }
                }
            }
        }

        // Horizontal Quick Region Filters
        item {
            LazyRow(
                contentPadding = PaddingValues(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(quickRegions) { region ->
                    val isSelected = filterState.selectedRegion == region
                    Surface(
                        modifier = Modifier
                            .clip(RoundedCornerShape(20.dp))
                            .clickable { onRegionSelect(region) }
                            .testTag("region_chip_$region"),
                        color = if (isSelected) TealPrimary else White,
                        border = androidx.compose.foundation.BorderStroke(
                            1.dp,
                            if (isSelected) TealPrimary else Slate200
                        )
                    ) {
                        Text(
                            text = region,
                            color = if (isSelected) White else Slate700,
                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                            fontSize = 13.sp,
                            modifier = Modifier.padding(horizontal = 14.dp, vertical = 7.dp)
                        )
                    }
                }
            }
        }

        // Quick Specialty Tags
        item {
            LazyRow(
                contentPadding = PaddingValues(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                items(quickSpecialties) { specialty ->
                    val isSelected = filterState.selectedSpecialties.contains(specialty)
                    Surface(
                        modifier = Modifier
                            .clip(RoundedCornerShape(8.dp))
                            .clickable { onSpecialtyToggle(specialty) }
                            .testTag("specialty_chip_$specialty"),
                        color = if (isSelected) TealContainer else White,
                        border = androidx.compose.foundation.BorderStroke(
                            1.dp,
                            if (isSelected) TealDark else Slate200
                        )
                    ) {
                        Text(
                            text = specialty,
                            color = if (isSelected) TealDark else Slate600,
                            fontWeight = if (isSelected) FontWeight.SemiBold else FontWeight.Normal,
                            fontSize = 12.sp,
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp)
                        )
                    }
                }
            }
        }

        // Search Results Summary Header & Sort
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 4.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = "등록된 전문 간병인",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = Slate900
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "${caregivers.size}명",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = TealPrimary
                    )
                }

                Surface(
                    modifier = Modifier
                        .clip(RoundedCornerShape(8.dp))
                        .clickable { showFilterSheet = true },
                    color = Slate100
                ) {
                    Text(
                        text = "정렬: ${filterState.sortOption.label} ▾",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Medium,
                        color = Slate700,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                    )
                }
            }
        }

        // Caregivers List
        if (caregivers.isEmpty()) {
            item {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = White)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(32.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = null,
                            tint = Slate400,
                            modifier = Modifier.size(48.dp)
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                        Text(
                            text = "조건에 일치하는 간병인이 없습니다",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = Slate800
                        )
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = "지역이나 희망 일당 필터를 조정해 보세요",
                            fontSize = 13.sp,
                            color = Slate500
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Surface(
                            modifier = Modifier
                                .clip(RoundedCornerShape(8.dp))
                                .clickable { onResetFilters() },
                            color = TealContainer
                        ) {
                            Text(
                                text = "필터 전체 초기화",
                                color = TealDark,
                                fontWeight = FontWeight.Bold,
                                fontSize = 13.sp,
                                modifier = Modifier.padding(horizontal = 14.dp, vertical = 8.dp)
                            )
                        }
                    }
                }
            }
        } else {
            items(caregivers, key = { it.profileId }) { caregiver ->
                Box(modifier = Modifier.padding(horizontal = 16.dp)) {
                    CaregiverCard(
                        caregiver = caregiver,
                        onDetailClick = { onCaregiverClick(caregiver.profileId) },
                        onRequestClick = { onRequestClick(caregiver.profileId) }
                    )
                }
            }
        }
    }

    if (showFilterSheet) {
        FilterBottomSheet(
            filterState = filterState,
            onRegionSelected = { onRegionSelect(it) },
            onSpecialtyToggled = { onSpecialtyToggle(it) },
            onCertificationToggled = { onCertificationToggle(it) },
            onMaxDailyPayChanged = { onMaxDailyPayChange(it) },
            onSortOptionChanged = { onSortOptionChange(it) },
            onResetFilters = { onResetFilters() },
            onDismiss = { showFilterSheet = false }
        )
    }
}
