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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material.icons.filled.Save
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
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.carematch.data.model.CaregiverProfile
import com.example.carematch.ui.theme.Slate100
import com.example.carematch.ui.theme.Slate200
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
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun CaregiverProfileEditScreen(
    profile: CaregiverProfile?,
    onSaveProfile: (CaregiverProfile) -> Unit,
    modifier: Modifier = Modifier
) {
    if (profile == null) {
        Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text("간병인 프로필 정보를 불러올 수 없습니다.")
        }
        return
    }

    var name by remember(profile) { mutableStateOf(profile.name) }
    var location by remember(profile) { mutableStateOf(profile.location) }
    var desiredDailyPay by remember(profile) { mutableStateOf(profile.desiredDailyPay) }
    var bio by remember(profile) { mutableStateOf(profile.bio) }
    var detailedIntro by remember(profile) { mutableStateOf(profile.detailedIntroduction) }
    var isActive by remember(profile) { mutableStateOf(profile.isActive) }
    var selectedSpecialties by remember(profile) { mutableStateOf(profile.specialties.toSet()) }

    val allSpecialtyOptions = listOf(
        "석션(가래흡인)",
        "와상 환자 케어",
        "치매 전문 돌봄",
        "욕창 집중 관리",
        "콧줄(L-tube) 피딩",
        "재활운동 보조",
        "정형외과 수술 후 회복",
        "남성 고체중 환자 이동",
        "24시간 입주 가능",
        "야간 돌봄",
        "식사 영양 보조"
    )

    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    Box(modifier = modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Slate50)
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Header
            Column {
                Text(
                    text = "내 간병인 프로필 관리",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = Slate900
                )
                Text(
                    text = "보호자들에게 노출되는 프로필 정보 및 구직 상태를 설정합니다",
                    style = MaterialTheme.typography.bodySmall,
                    color = Slate500
                )
            }

            // Active Status Card (구직 등록 상태 ON/OFF)
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = if (isActive) TealContainer.copy(alpha = 0.5f) else White),
                border = androidx.compose.foundation.BorderStroke(1.dp, if (isActive) TealPrimary else Slate200)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = if (isActive) "구직 등록 활성화 중 (탐색 노출 ON)" else "구직 등록 비활성화 (탐색 노출 OFF)",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = if (isActive) TealDark else Slate800
                        )
                        Text(
                            text = if (isActive) "보호자분들의 간병인 탐색 목록에 노출되고 새 요청을 받습니다." else "새로운 간병 요청을 일시적으로 받지 않습니다.",
                            fontSize = 12.sp,
                            color = Slate600
                        )
                    }

                    Switch(
                        checked = isActive,
                        onCheckedChange = { isActive = it },
                        colors = SwitchDefaults.colors(
                            checkedThumbColor = White,
                            checkedTrackColor = TealPrimary
                        ),
                        modifier = Modifier.testTag("active_status_switch")
                    )
                }
            }

            // Profile Basic Info Card
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = White)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "기본 정보 & 활동 지역",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = Slate900
                    )

                    Spacer(modifier = Modifier.height(14.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .size(64.dp)
                                .clip(CircleShape)
                                .background(TealContainer)
                        ) {
                            if (profile.avatarUrl.isNotEmpty()) {
                                AsyncImage(
                                    model = profile.avatarUrl,
                                    contentDescription = profile.name,
                                    modifier = Modifier.fillMaxSize(),
                                    contentScale = ContentScale.Crop
                                )
                            } else {
                                Icon(Icons.Default.Person, contentDescription = null, tint = TealDark, modifier = Modifier.size(36.dp).align(Alignment.Center))
                            }
                        }

                        Spacer(modifier = Modifier.width(14.dp))

                        Column {
                            Text(
                                text = "${profile.name} (만 ${profile.age}세)",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold
                            )
                            Text("국가공인 요양보호사 1급 인증 완료", fontSize = 12.sp, color = TealDark)
                        }
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    Text("활동 가능 지역", fontSize = 13.sp, fontWeight = FontWeight.SemiBold, color = Slate700)
                    Spacer(modifier = Modifier.height(6.dp))
                    OutlinedTextField(
                        value = location,
                        onValueChange = { location = it },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true,
                        shape = RoundedCornerShape(10.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = TealPrimary,
                            unfocusedBorderColor = Slate200
                        )
                    )
                }
            }

            // Desired Daily Pay Card
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = White)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "희망 일당 설정",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = Slate900
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = "보호자가 탐색할 때 기준 금액으로 표시됩니다.",
                        fontSize = 12.sp,
                        color = Slate500
                    )

                    Spacer(modifier = Modifier.height(14.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        IconButton(
                            onClick = { if (desiredDailyPay > 100000) desiredDailyPay -= 5000 },
                            modifier = Modifier
                                .size(40.dp)
                                .clip(CircleShape)
                                .background(Slate100)
                        ) {
                            Icon(Icons.Default.Remove, contentDescription = "감소")
                        }

                        Text(
                            text = "${String.format("%,d", desiredDailyPay)}원 / 일",
                            style = MaterialTheme.typography.headlineSmall,
                            fontWeight = FontWeight.Bold,
                            color = TealPrimary
                        )

                        IconButton(
                            onClick = { desiredDailyPay += 5000 },
                            modifier = Modifier
                                .size(40.dp)
                                .clip(CircleShape)
                                .background(TealContainer)
                        ) {
                            Icon(Icons.Default.Add, contentDescription = "증가", tint = TealDark)
                        }
                    }
                }
            }

            // Bio & Introduction
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = White)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "한줄 소개 및 상세 경력",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = Slate900
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    Text("한줄 어필", fontSize = 13.sp, fontWeight = FontWeight.SemiBold, color = Slate700)
                    Spacer(modifier = Modifier.height(6.dp))
                    OutlinedTextField(
                        value = bio,
                        onValueChange = { bio = it },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true,
                        shape = RoundedCornerShape(10.dp)
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    Text("상세 경력 및 자기소개", fontSize = 13.sp, fontWeight = FontWeight.SemiBold, color = Slate700)
                    Spacer(modifier = Modifier.height(6.dp))
                    OutlinedTextField(
                        value = detailedIntro,
                        onValueChange = { detailedIntro = it },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(130.dp),
                        shape = RoundedCornerShape(10.dp)
                    )
                }
            }

            // Specialties Checklist
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = White)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "가능한 돌봄 전문 영역 (다중 선택)",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = Slate900
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    FlowRow(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        allSpecialtyOptions.forEach { spec ->
                            val isSelected = selectedSpecialties.contains(spec)
                            Surface(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(8.dp))
                                    .clickable {
                                        selectedSpecialties = if (isSelected) {
                                            selectedSpecialties - spec
                                        } else {
                                            selectedSpecialties + spec
                                        }
                                    },
                                color = if (isSelected) TealContainer else Slate100,
                                border = androidx.compose.foundation.BorderStroke(1.dp, if (isSelected) TealDark else Slate200)
                            ) {
                                Row(
                                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    if (isSelected) {
                                        Icon(Icons.Default.Check, contentDescription = null, tint = TealDark, modifier = Modifier.size(14.dp))
                                        Spacer(modifier = Modifier.width(4.dp))
                                    }
                                    Text(
                                        text = spec,
                                        color = if (isSelected) TealDark else Slate700,
                                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                                        fontSize = 12.sp
                                    )
                                }
                            }
                        }
                    }
                }
            }

            // Save Button
            Button(
                onClick = {
                    val updated = profile.copy(
                        name = name,
                        location = location,
                        desiredDailyPay = desiredDailyPay,
                        bio = bio,
                        detailedIntroduction = detailedIntro,
                        isActive = isActive,
                        specialties = selectedSpecialties.toList()
                    )
                    onSaveProfile(updated)
                    scope.launch {
                        snackbarHostState.showSnackbar("프로필 정보가 성공적으로 저장되었습니다.")
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
                    .testTag("save_profile_button"),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(containerColor = TealPrimary)
            ) {
                Icon(Icons.Default.Save, contentDescription = null, modifier = Modifier.size(18.dp))
                Spacer(modifier = Modifier.width(6.dp))
                Text("프로필 저장하기", fontWeight = FontWeight.Bold, fontSize = 16.sp, color = White)
            }

            Spacer(modifier = Modifier.height(24.dp))
        }

        SnackbarHost(
            hostState = snackbarHostState,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 16.dp)
        )
    }
}
