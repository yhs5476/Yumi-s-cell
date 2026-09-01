package com.example.careplus.ui.components

import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.careplus.data.model.CareReportData
import com.example.careplus.data.model.JourneyStep
import com.example.careplus.ui.theme.TossBlack
import com.example.careplus.ui.theme.TossBlue
import com.example.careplus.ui.theme.TossBlueLight
import com.example.careplus.ui.theme.TossBorder
import com.example.careplus.ui.theme.TossGray
import com.example.careplus.ui.theme.TossGreen
import com.example.careplus.ui.theme.TossSurface

@Composable
fun DoorToDoorTimelineCard(
    currentStep: JourneyStep,
    onAdvanceStep: (JourneyStep) -> Unit,
    userRole: com.example.careplus.data.model.UserRole = com.example.careplus.data.model.UserRole.GUARDIAN,
    modifier: Modifier = Modifier
) {
    TossCard(
        modifier = modifier.fillMaxWidth(),
        backgroundColor = TossSurface,
        shape = RoundedCornerShape(22.dp)
    ) {
        Column(
            modifier = Modifier.padding(20.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = "🚗 Door-to-Door 실시간 트래킹",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = TossBlack
                    )
                }
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(8.dp))
                        .background(if (userRole == com.example.careplus.data.model.UserRole.CAREGIVER) Color(0xFFE6F9F1) else TossBlueLight)
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                ) {
                    Text(
                        text = if (userRole == com.example.careplus.data.model.UserRole.CAREGIVER) "간병인 현장 입력 모드 ✏️" else "보호자 안심 모니터링 👁️",
                        style = MaterialTheme.typography.labelSmall,
                        color = if (userRole == com.example.careplus.data.model.UserRole.CAREGIVER) TossGreen else TossBlue,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            // Current Active Step Banner
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(14.dp))
                    .background(Color(0xFFF3F0FF))
                    .padding(14.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = currentStep.emoji,
                        fontSize = 24.sp
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "현재 단계: ${currentStep.title}",
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF6D28D9)
                        )
                        Text(
                            text = currentStep.desc,
                            style = MaterialTheme.typography.bodySmall,
                            color = TossGray
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // 6-step progress dots
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                JourneyStep.values().forEach { step ->
                    val isPastOrCurrent = step.stepNumber <= currentStep.stepNumber
                    val isCurrent = step == currentStep

                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.weight(1f)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(if (isCurrent) 32.dp else 24.dp)
                                .clip(CircleShape)
                                .background(
                                    when {
                                        isCurrent -> TossBlue
                                        isPastOrCurrent -> TossGreen
                                        else -> TossBorder
                                    }
                                ),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = step.emoji,
                                fontSize = if (isCurrent) 14.sp else 10.sp
                            )
                        }
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = step.title.split(" ").lastOrNull() ?: step.title,
                            style = MaterialTheme.typography.labelSmall,
                            fontSize = 10.sp,
                            fontWeight = if (isCurrent) FontWeight.Bold else FontWeight.Normal,
                            color = if (isCurrent) TossBlue else if (isPastOrCurrent) TossBlack else TossGray,
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            val nextStep = getNextJourneyStep(currentStep)

            if (nextStep == null) {
                // Completed state
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(12.dp))
                        .background(Color(0xFFE8F5E9))
                        .padding(vertical = 10.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.CheckCircle,
                            contentDescription = null,
                            tint = TossGreen,
                            modifier = Modifier.size(18.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "Door-to-Door 전체 케어가 완료되었습니다",
                            style = MaterialTheme.typography.labelMedium,
                            fontWeight = FontWeight.Bold,
                            color = TossGreen
                        )
                    }
                }
            } else if (userRole == com.example.careplus.data.model.UserRole.CAREGIVER) {
                // Caregiver Mode: Input Action Button
                Button(
                    onClick = { onAdvanceStep(nextStep) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(44.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = TossGreen)
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = "✏️ [간병인 현장 입력] '${nextStep.title}' 완료 업데이트",
                            style = MaterialTheme.typography.labelLarge,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Icon(
                            imageVector = Icons.Default.ChevronRight,
                            contentDescription = null,
                            tint = Color.White,
                            modifier = Modifier.size(18.dp)
                        )
                    }
                }
            } else {
                // Guardian Mode: Monitoring View + Simulation Test Button
                Column(modifier = Modifier.fillMaxWidth()) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(12.dp))
                            .background(Color(0xFFF8FAFC))
                            .padding(12.dp)
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text("💡", fontSize = 16.sp)
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "담당 케어메이트가 현장에서 이동/진료 상황을 실시간으로 업데이트 중입니다.",
                                style = MaterialTheme.typography.bodySmall,
                                color = TossGray
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Button(
                        onClick = { onAdvanceStep(nextStep) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(38.dp),
                        shape = RoundedCornerShape(10.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = TossBlueLight)
                    ) {
                        Text(
                            text = "⚡ [시뮬레이션 테스트] 다음 단계('${nextStep.title}')로 진행 시켜보기",
                            style = MaterialTheme.typography.labelMedium,
                            fontWeight = FontWeight.Bold,
                            color = TossBlue
                        )
                    }
                }
            }
        }
    }
}

private fun getNextJourneyStep(current: JourneyStep): JourneyStep? {
    val steps = JourneyStep.values()
    val nextIndex = current.ordinal + 1
    return if (nextIndex < steps.size) steps[nextIndex] else null
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AiCareReportBottomSheet(
    report: CareReportData,
    onDismiss: () -> Unit
) {
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val context = LocalContext.current

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = sheetState,
        containerColor = TossSurface,
        shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 12.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(36.dp)
                            .clip(CircleShape)
                            .background(Color(0xFFF3F0FF)),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(text = "✨", fontSize = 18.sp)
                    }
                    Spacer(modifier = Modifier.width(10.dp))
                    Column {
                        Text(
                            text = "포도당 AI Care Report",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = TossBlack
                        )
                        Text(
                            text = "진료 노트 & 복약 지도 자동 요약",
                            style = MaterialTheme.typography.bodySmall,
                            color = TossGray
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
            HorizontalDivider(color = TossBorder)
            Spacer(modifier = Modifier.height(16.dp))

            // 1. Summary
            ReportSectionCard(
                title = "📌 오늘의 돌봄 요약",
                content = report.summary,
                bgColor = Color(0xFFF8FAFC)
            )

            Spacer(modifier = Modifier.height(10.dp))

            // 2. Treatment Notes
            ReportSectionCard(
                title = "🩺 진료 & 경과 기록",
                content = report.treatmentNotes,
                bgColor = Color(0xFFF0FDF4)
            )

            Spacer(modifier = Modifier.height(10.dp))

            // 3. Next Appointment
            ReportSectionCard(
                title = "🗓️ 다음 예약일",
                content = report.nextAppointment,
                bgColor = Color(0xFFEFF6FF)
            )

            Spacer(modifier = Modifier.height(10.dp))

            // 4. Medication Info
            ReportSectionCard(
                title = "💊 복약 지도",
                content = report.medicationInfo,
                bgColor = Color(0xFFFFF7ED)
            )

            Spacer(modifier = Modifier.height(10.dp))

            // 5. Guardian Alerts
            ReportSectionCard(
                title = "⚠️ 보호자 필수 확인 사항",
                content = report.guardianAlerts,
                bgColor = Color(0xFFFEF2F2)
            )

            Spacer(modifier = Modifier.height(20.dp))

            // Family Share Button
            FamilyShareButton(
                shareToken = report.shareToken,
                context = context
            )

            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

@Composable
fun ReportSectionCard(
    title: String,
    content: String,
    bgColor: Color
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(14.dp))
            .background(bgColor)
            .padding(14.dp)
    ) {
        Column {
            Text(
                text = title,
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Bold,
                color = TossBlack
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = content,
                style = MaterialTheme.typography.bodySmall,
                color = Color(0xFF334155)
            )
        }
    }
}

@Composable
fun FamilyShareButton(
    shareToken: String,
    context: Context
) {
    Button(
        onClick = {
            val shareIntent = Intent(Intent.ACTION_SEND).apply {
                type = "text/plain"
                putExtra(
                    Intent.EXTRA_TEXT,
                    "[포도당] 가족 안심 Care Report가도착했습니다 🍇\n\n오늘의 진료 내용 및 다음 예약일을 온 가족이 한눈에 확인하세요:\nhttps://pododang.app/report?token=$shareToken"
                )
            }
            context.startActivity(Intent.createChooser(shareIntent, "포도당 Care Report 가족 공유"))
            Toast.makeText(context, "가족 공유 링크가 생성되었습니다 🔗", Toast.LENGTH_SHORT).show()
        },
        modifier = Modifier
            .fillMaxWidth()
            .height(52.dp),
        shape = RoundedCornerShape(14.dp),
        colors = ButtonDefaults.buttonColors(containerColor = TossBlue)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                imageVector = Icons.Default.Share,
                contentDescription = "공유",
                tint = Color.White,
                modifier = Modifier.size(18.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "가족에게 카톡/링크로 공유하기 🔗",
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
        }
    }
}
