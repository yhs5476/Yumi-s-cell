package com.example.carematch.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.carematch.ui.theme.*
import com.example.carematch.ui.theme.Slate700
import com.example.carematch.ui.theme.Slate900
import com.example.carematch.ui.theme.TealContainer
import com.example.carematch.ui.theme.TealDark
import com.example.carematch.ui.theme.TealPrimary
import com.example.carematch.ui.theme.White
import com.example.carematch.ui.viewmodel.FilterState
import com.example.carematch.ui.viewmodel.SortOption

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun FilterBottomSheet(
    filterState: FilterState,
    onRegionSelected: (String) -> Unit,
    onSpecialtyToggled: (String) -> Unit,
    onCertificationToggled: (String) -> Unit,
    onMaxDailyPayChanged: (Int) -> Unit,
    onSortOptionChanged: (SortOption) -> Unit,
    onResetFilters: () -> Unit,
    onDismiss: () -> Unit
) {
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    val regions = listOf("전체", "서울 강남/서초", "서울 송파/강동", "서울 영등포/구로", "서울 종로/중구", "경기 성남/분당", "인천", "부산")
    val certifications = listOf("요양보호사 1급", "간호조무사", "치매전문교육 이수", "심폐소생술(CPR)", "사회복지사 2급")
    val specialties = listOf(
        "석션(가래흡인)",
        "와상 환자 케어",
        "치매 전문 돌봄",
        "욕창 집중 관리",
        "콧줄(L-tube) 피딩",
        "재활운동 보조",
        "정형외과 수술 후 회복",
        "남성 고체중 환자 이동",
        "24시간 입주 가능"
    )

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = sheetState,
        containerColor = White,
        shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
                .verticalScroll(rememberScrollState())
        ) {
            // Header
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "간병인 검색 필터",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = Slate900
                )
                IconButton(onClick = onDismiss) {
                    Icon(imageVector = Icons.Default.Close, contentDescription = "닫기")
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // 1. 정렬 기준
            Text(
                text = "정렬 방식",
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.Bold,
                color = Slate800
            )
            Spacer(modifier = Modifier.height(8.dp))
            FlowRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                SortOption.values().forEach { option ->
                    val isSelected = filterState.sortOption == option
                    Surface(
                        modifier = Modifier
                            .clip(RoundedCornerShape(20.dp))
                            .clickable { onSortOptionChanged(option) }
                            .testTag("sort_${option.name}"),
                        color = if (isSelected) TealPrimary else Slate50,
                        border = androidx.compose.foundation.BorderStroke(
                            1.dp,
                            if (isSelected) TealPrimary else Slate200
                        )
                    ) {
                        Text(
                            text = option.label,
                            color = if (isSelected) White else Slate700,
                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                            fontSize = 13.sp,
                            modifier = Modifier.padding(horizontal = 14.dp, vertical = 8.dp)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // 2. 활동 지역
            Text(
                text = "활동 지역",
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.Bold,
                color = Slate800
            )
            Spacer(modifier = Modifier.height(8.dp))
            FlowRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                regions.forEach { region ->
                    val isSelected = filterState.selectedRegion == region
                    Surface(
                        modifier = Modifier
                            .clip(RoundedCornerShape(20.dp))
                            .clickable { onRegionSelected(region) },
                        color = if (isSelected) TealPrimary else Slate50,
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
                            modifier = Modifier.padding(horizontal = 12.dp, vertical = 7.dp)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // 3. 희망 일당 상한선
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "희망 일당 상한선",
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Bold,
                    color = Slate800
                )
                Text(
                    text = "최대 ${String.format("%,d", filterState.maxDailyPay)}원 이하",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = TealPrimary
                )
            }
            Slider(
                value = filterState.maxDailyPay.toFloat(),
                onValueChange = { onMaxDailyPayChanged(it.toInt()) },
                valueRange = 130000f..200000f,
                steps = 6,
                colors = SliderDefaults.colors(
                    thumbColor = TealPrimary,
                    activeTrackColor = TealPrimary
                ),
                modifier = Modifier.testTag("max_pay_slider")
            )

            Spacer(modifier = Modifier.height(16.dp))

            // 4. 보유 자격증
            Text(
                text = "보유 자격증",
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.Bold,
                color = Slate800
            )
            Spacer(modifier = Modifier.height(8.dp))
            FlowRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                certifications.forEach { cert ->
                    val isSelected = filterState.selectedCertifications.contains(cert)
                    Surface(
                        modifier = Modifier
                            .clip(RoundedCornerShape(8.dp))
                            .clickable { onCertificationToggled(cert) },
                        color = if (isSelected) TealContainer else Slate50,
                        border = androidx.compose.foundation.BorderStroke(
                            1.dp,
                            if (isSelected) TealDark else Slate200
                        )
                    ) {
                        Text(
                            text = cert,
                            color = if (isSelected) TealDark else Slate700,
                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                            fontSize = 12.sp,
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // 5. 돌봄 전문 영역
            Text(
                text = "필요 돌봄 전문 분야",
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.Bold,
                color = Slate800
            )
            Spacer(modifier = Modifier.height(8.dp))
            FlowRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                specialties.forEach { spec ->
                    val isSelected = filterState.selectedSpecialties.contains(spec)
                    Surface(
                        modifier = Modifier
                            .clip(RoundedCornerShape(8.dp))
                            .clickable { onSpecialtyToggled(spec) },
                        color = if (isSelected) TealContainer else Slate50,
                        border = androidx.compose.foundation.BorderStroke(
                            1.dp,
                            if (isSelected) TealDark else Slate200
                        )
                    ) {
                        Text(
                            text = spec,
                            color = if (isSelected) TealDark else Slate700,
                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                            fontSize = 12.sp,
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(28.dp))

            // Action Buttons (Reset & Apply)
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 32.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                OutlinedButton(
                    onClick = onResetFilters,
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Refresh,
                        contentDescription = "초기화",
                        modifier = Modifier.padding(end = 4.dp)
                    )
                    Text("초기화")
                }

                Button(
                    onClick = onDismiss,
                    modifier = Modifier.weight(2f),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = TealPrimary)
                ) {
                    Text("필터 적용하기", fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}
