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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.ReceiptLong
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
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
import com.example.careplus.data.local.ContractEntity
import com.example.careplus.data.model.EscrowStatus
import com.example.careplus.data.model.JourneyStep
import com.example.careplus.ui.components.AiCareReportBottomSheet
import com.example.careplus.ui.components.DoorToDoorTimelineCard
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
import com.example.careplus.ui.theme.TossYellow
import com.example.careplus.viewmodel.CarePlusViewModel

@Composable
fun ContractDetailScreen(
    viewModel: CarePlusViewModel,
    onBack: () -> Unit
) {
    val contracts by viewModel.allContracts.collectAsState()
    val userRole by viewModel.currentRole.collectAsState()
    var selectedContractForReview by remember { mutableStateOf<ContractEntity?>(null) }
    var selectedContractForReport by remember { mutableStateOf<ContractEntity?>(null) }
    var reviewRating by remember { mutableFloatStateOf(5f) }
    var reviewText by remember { mutableStateOf("병원 시스템에도 능숙하시고 석션과 기저귀 케어 모두 정성껏 돌봐주셨습니다. 진심으로 감사드립니다!") }

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
                        text = "계약 및 에스크로 관리",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Header Explanation
            item {
                TossCard(
                    modifier = Modifier.fillMaxWidth(),
                    backgroundColor = TossSurface,
                    shape = RoundedCornerShape(20.dp)
                ) {
                    Row(
                        modifier = Modifier.padding(18.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .size(44.dp)
                                .clip(CircleShape)
                                .background(TossBlueLight),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.Shield,
                                contentDescription = null,
                                tint = TossBlue,
                                modifier = Modifier.size(24.dp)
                            )
                        }
                        Spacer(modifier = Modifier.width(14.dp))
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = "포도당 안심 에스크로",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold
                            )
                            Spacer(modifier = Modifier.height(2.dp))
                            Text(
                                text = "간병비는 안전 금고에 보관되며 케어 종료 후 보호자의 승인 하에 정산됩니다.",
                                style = MaterialTheme.typography.bodySmall,
                                color = TossGray
                            )
                        }
                    }
                }
            }

            if (contracts.isEmpty()) {
                item {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(40.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text("📄", fontSize = 40.sp)
                            Spacer(modifier = Modifier.height(12.dp))
                            Text("아직 체결된 계약이 없습니다", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                            Spacer(modifier = Modifier.height(4.dp))
                            Text("견적을 비교하고 1:1 상담 후 계약을 체결해 보세요", style = MaterialTheme.typography.bodySmall, color = TossGray)
                        }
                    }
                }
            } else {
                items(contracts) { contract ->
                    ContractCardItem(
                        contract = contract,
                        userRole = userRole,
                        onReleaseEscrow = { viewModel.releaseEscrow(contract.contractId) },
                        onReviewClick = { selectedContractForReview = contract },
                        onAdvanceStep = { nextStep -> viewModel.updateJourneyStep(contract.contractId, nextStep) },
                        onViewReportClick = { selectedContractForReport = contract },
                        onResetCycle = { viewModel.completeAndResetCycle { onBack() } }
                    )
                }
            }
        }

        // AI Care Report Bottom Sheet
        if (selectedContractForReport != null) {
            val reportData = viewModel.getCareReport(selectedContractForReport!!)
            AiCareReportBottomSheet(
                report = reportData,
                onDismiss = { selectedContractForReport = null }
            )
        }

        // Review Dialog
        if (selectedContractForReview != null) {
            AlertDialog(
                onDismissRequest = { selectedContractForReview = null },
                title = {
                    Text(
                        text = "케어메이트 안심 리뷰 작성",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                },
                text = {
                    Column {
                        Text(
                            text = "${selectedContractForReview!!.caregiverName}님과의 돌봄은 만족스러우셨나요?",
                            style = MaterialTheme.typography.bodyMedium,
                            color = TossGray
                        )
                        Spacer(modifier = Modifier.height(12.dp))

                        // Star rating row
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.Center
                        ) {
                            (1..5).forEach { starIndex ->
                                IconButton(
                                    onClick = { reviewRating = starIndex.toFloat() }
                                ) {
                                    Icon(
                                        imageVector = if (starIndex <= reviewRating) Icons.Default.Star else Icons.Outlined.Star,
                                        contentDescription = "$starIndex 점",
                                        tint = if (starIndex <= reviewRating) TossYellow else TossBorder,
                                        modifier = Modifier.size(32.dp)
                                    )
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(12.dp))

                        OutlinedTextField(
                            value = reviewText,
                            onValueChange = { reviewText = it },
                            placeholder = { Text("상세한 후기를 작성해 주세요...") },
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(12.dp),
                            maxLines = 4,
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = TossBlue,
                                unfocusedBorderColor = TossBorder
                            )
                        )
                    }
                },
                confirmButton = {
                    TextButton(
                        onClick = {
                            val contractId = selectedContractForReview!!.contractId
                            viewModel.submitReview(
                                contractId = contractId,
                                rating = reviewRating,
                                comment = reviewText
                            )
                            selectedContractForReview = null
                            // Reset cycle and navigate back to initial home state for new recruitment
                            viewModel.completeAndResetCycle {
                                onBack()
                            }
                        }
                    ) {
                        Text("후기 등록 및 새 모집 시작 🚀", fontWeight = FontWeight.Bold, color = TossBlue)
                    }
                },
                dismissButton = {
                    TextButton(onClick = { selectedContractForReview = null }) {
                        Text("취소", color = TossGray)
                    }
                },
                containerColor = TossSurface,
                shape = RoundedCornerShape(20.dp)
            )
        }
    }
}

@Composable
fun ContractCardItem(
    contract: ContractEntity,
    userRole: com.example.careplus.data.model.UserRole = com.example.careplus.data.model.UserRole.GUARDIAN,
    onReleaseEscrow: () -> Unit,
    onReviewClick: () -> Unit,
    onAdvanceStep: (JourneyStep) -> Unit,
    onViewReportClick: () -> Unit,
    onResetCycle: () -> Unit = {}
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        // Door-to-Door Live Timeline Stepper
        DoorToDoorTimelineCard(
            currentStep = contract.journeyStep,
            onAdvanceStep = onAdvanceStep,
            userRole = userRole
        )

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
                // Status Header
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.ReceiptLong,
                            contentDescription = null,
                            tint = TossBlue,
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "계약 번호 #${contract.contractId}",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold
                        )
                    }
                    TossBadge(
                        text = contract.escrowStatus.label,
                        isGreen = contract.escrowStatus == EscrowStatus.RELEASED,
                        isHighlighted = contract.escrowStatus == EscrowStatus.HOLDING
                    )
                }

                Spacer(modifier = Modifier.height(14.dp))
                HorizontalDivider(color = TossBorder)
                Spacer(modifier = Modifier.height(14.dp))

                // Detail Items
                ContractDetailRow(label = "담당 케어메이트", value = "${contract.caregiverName} 케어메이트")
                ContractDetailRow(label = "환자/보호자", value = contract.guardianName)
                ContractDetailRow(label = "케어 장소", value = contract.location)
                ContractDetailRow(label = "케어 기간", value = contract.dates)

                Spacer(modifier = Modifier.height(12.dp))
                HorizontalDivider(color = TossBorder)
                Spacer(modifier = Modifier.height(12.dp))

                // Financial Breakdown
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    Text("간병 공급가액 (${String.format("%,d", contract.dailyPrice)}원 × ${contract.totalDays}일)", style = MaterialTheme.typography.bodySmall, color = TossGray)
                    Text("${String.format("%,d", contract.supplyPrice)}원", style = MaterialTheme.typography.bodySmall, fontWeight = FontWeight.SemiBold)
                }
                Spacer(modifier = Modifier.height(4.dp))
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    Text("플랫폼 안심 수수료 (5%)", style = MaterialTheme.typography.bodySmall, color = TossGray)
                    Text("${String.format("%,d", contract.platformFee)}원", style = MaterialTheme.typography.bodySmall, fontWeight = FontWeight.SemiBold)
                }
                Spacer(modifier = Modifier.height(8.dp))
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    Text("총 에스크로 예치금액", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                    Text(
                        text = "${String.format("%,d", contract.totalPrice)}원",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = TossBlue
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                // AI Care Report Button
                TossButton(
                    text = "✨ AI Care Report & 가족 공유 🔗",
                    backgroundColor = TossBlueDark,
                    onClick = onViewReportClick,
                    testTag = "btn_ai_care_report"
                )

                Spacer(modifier = Modifier.height(10.dp))

                // Actions based on escrow status
                if (!contract.isReviewed) {
                    TossButton(
                        text = "케어메이트 안심 후기 작성하기",
                        backgroundColor = TossBlue,
                        onClick = onReviewClick,
                        testTag = "btn_write_review"
                    )
                } else {
                    TossButton(
                        text = "🔄 초기 화면으로 돌아가기 (새 간병 공고 등록)",
                        backgroundColor = TossGreen,
                        onClick = onResetCycle,
                        testTag = "btn_reset_cycle"
                    )
                }
            }
        }
    }
}

@Composable
private fun ContractDetailRow(label: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 2.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = label, style = MaterialTheme.typography.bodySmall, color = TossGray)
        Text(text = value, style = MaterialTheme.typography.bodySmall, fontWeight = FontWeight.SemiBold, color = TossBlack)
    }
}
