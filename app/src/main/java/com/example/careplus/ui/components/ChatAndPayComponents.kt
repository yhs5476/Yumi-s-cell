package com.example.careplus.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Fingerprint
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.ReceiptLong
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.careplus.data.local.ChatMessageEntity
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
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun SmartQuickChipsRow(
    isGuardian: Boolean,
    onChipSelected: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val chips = if (isGuardian) {
        listOf(
            "석션 케어 경험이 얼마나 되시나요?",
            "야간 기상 케어 가능하신가요?",
            "식사 보조 가능하신가요?",
            "백신 접종 증명 확인 부탁드려요",
            "최종 견적서 발행 요청드려요"
        )
    } else {
        listOf(
            "환자분 체중이 정확히 어떻게 되나요?",
            "보호자 상주 여부가 어떻게 되나요?",
            "식사 및 복약 지도 확인했습니다",
            "최종 견적서 발행해 드리겠습니다"
        )
    }

    LazyRow(
        modifier = modifier
            .fillMaxWidth()
            .background(TossBackground)
            .padding(vertical = 8.dp),
        contentPadding = PaddingValues(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(chips) { chipText ->
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(20.dp))
                    .background(TossSurface)
                    .border(1.dp, TossBorder, RoundedCornerShape(20.dp))
                    .clickable { onChipSelected(chipText) }
                    .padding(horizontal = 14.dp, vertical = 8.dp)
            ) {
                Text(
                    text = chipText,
                    style = MaterialTheme.typography.bodySmall,
                    fontWeight = FontWeight.Medium,
                    color = TossBlack
                )
            }
        }
    }
}

@Composable
fun ChatBubbleItem(
    message: ChatMessageEntity,
    isCurrentUser: Boolean,
    onPayInvoiceClick: () -> Unit = {}
) {
    val timeFormat = SimpleDateFormat("a h:mm", Locale.KOREA)
    val timeStr = timeFormat.format(Date(message.timestamp))

    if (message.senderRole == "SYSTEM") {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp, horizontal = 24.dp),
            contentAlignment = Alignment.Center
        ) {
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(14.dp))
                    .background(TossGreenLight)
                    .padding(horizontal = 16.dp, vertical = 10.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.Shield,
                        contentDescription = null,
                        tint = TossGreen,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = message.content,
                        style = MaterialTheme.typography.bodySmall,
                        color = TossGreen,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }
        }
        return
    }

    if (message.isInvoice) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 10.dp, horizontal = 16.dp),
            contentAlignment = Alignment.Center
        ) {
            InvoiceCard(
                totalDays = message.invoiceDays,
                totalPrice = message.invoiceTotalPrice,
                onPayClick = onPayInvoiceClick
            )
        }
        return
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp, horizontal = 16.dp),
        horizontalAlignment = if (isCurrentUser) Alignment.End else Alignment.Start
    ) {
        if (!isCurrentUser) {
            Text(
                text = message.senderName,
                style = MaterialTheme.typography.bodySmall,
                color = TossGray,
                modifier = Modifier.padding(start = 4.dp, bottom = 2.dp)
            )
        }

        Row(
            verticalAlignment = Alignment.Bottom,
            horizontalArrangement = if (isCurrentUser) Arrangement.End else Arrangement.Start,
            modifier = Modifier.fillMaxWidth()
        ) {
            if (isCurrentUser) {
                Text(
                    text = timeStr,
                    style = MaterialTheme.typography.bodySmall,
                    fontSize = 10.sp,
                    color = TossGrayLight,
                    modifier = Modifier.padding(end = 4.dp, bottom = 2.dp)
                )
            }

            Box(
                modifier = Modifier
                    .clip(
                        RoundedCornerShape(
                            topStart = 18.dp,
                            topEnd = 18.dp,
                            bottomStart = if (isCurrentUser) 18.dp else 4.dp,
                            bottomEnd = if (isCurrentUser) 4.dp else 18.dp
                        )
                    )
                    .background(if (isCurrentUser) TossBlue else TossSurface)
                    .then(
                        if (!isCurrentUser) Modifier.border(1.dp, TossBorder, RoundedCornerShape(18.dp))
                        else Modifier
                    )
                    .padding(horizontal = 16.dp, vertical = 12.dp)
            ) {
                Text(
                    text = message.content,
                    style = MaterialTheme.typography.bodyLarge,
                    color = if (isCurrentUser) TossSurface else TossBlack
                )
            }

            if (!isCurrentUser) {
                Text(
                    text = timeStr,
                    style = MaterialTheme.typography.bodySmall,
                    fontSize = 10.sp,
                    color = TossGrayLight,
                    modifier = Modifier.padding(start = 4.dp, bottom = 2.dp)
                )
            }
        }
    }
}

@Composable
fun InvoiceCard(
    totalDays: Int,
    totalPrice: Int,
    location: String = "서울아산병원 신관 7층",
    dates: String = "2026.09.01 ~ 2026.09.04 (3일간)",
    onPayClick: () -> Unit
) {
    val dailyPrice = if (totalDays > 0) ((totalPrice / 1.05) / totalDays).toInt() else 140000
    val supplyPrice = dailyPrice * totalDays
    val platformFee = (supplyPrice * 0.05).toInt()
    val finalTotal = supplyPrice + platformFee

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
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.ReceiptLong,
                    contentDescription = null,
                    tint = TossBlue,
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "최종 간병 확정서",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = TossBlack
                )
                Spacer(modifier = Modifier.weight(1f))
                TossBadge(text = "에스크로 보증", isGreen = true)
            }

            Spacer(modifier = Modifier.height(14.dp))
            HorizontalDivider(color = TossBorder)
            Spacer(modifier = Modifier.height(14.dp))

            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text("• 케어 기간", style = MaterialTheme.typography.bodyMedium, color = TossGray)
                Text(dates, style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.SemiBold)
            }
            Spacer(modifier = Modifier.height(8.dp))
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text("• 케어 장소", style = MaterialTheme.typography.bodyMedium, color = TossGray)
                Text(location, style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.SemiBold)
            }
            Spacer(modifier = Modifier.height(8.dp))
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text("• 간병비 (${String.format("%,d", dailyPrice)}원 × ${totalDays}일)", style = MaterialTheme.typography.bodyMedium, color = TossGray)
                Text("${String.format("%,d", supplyPrice)}원", style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.SemiBold)
            }
            Spacer(modifier = Modifier.height(8.dp))
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text("• 플랫폼 안심 이용료 (5%)", style = MaterialTheme.typography.bodyMedium, color = TossGray)
                Text("${String.format("%,d", platformFee)}원", style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.SemiBold)
            }

            Spacer(modifier = Modifier.height(16.dp))
            HorizontalDivider(color = TossBorder)
            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("총 결제 금액", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                Text(
                    text = "${String.format("%,d", finalTotal)}원",
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold,
                    color = TossBlue
                )
            }

            Spacer(modifier = Modifier.height(18.dp))

            TossButton(
                text = "토스페이로 1초 만에 결제하기 ⚡",
                onClick = onPayClick,
                modifier = Modifier.fillMaxWidth(),
                testTag = "pay_invoice_button"
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TossPayBottomSheet(
    totalAmount: Int,
    onDismiss: () -> Unit,
    onPaymentSuccess: () -> Unit
) {
    val sheetState = rememberModalBottomSheetState()
    val scope = rememberCoroutineScope()
    var isProcessing by remember { mutableStateOf(false) }
    var isSuccess by remember { mutableStateOf(false) }

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = sheetState,
        containerColor = TossSurface,
        shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp, vertical = 20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (isSuccess) {
                Box(
                    modifier = Modifier
                        .size(64.dp)
                        .clip(CircleShape)
                        .background(TossGreenLight),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.CheckCircle,
                        contentDescription = null,
                        tint = TossGreen,
                        modifier = Modifier.size(40.dp)
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "결제가 완료되었습니다",
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "간병이 끝날 때까지 포도당이 비용을 안전하게 보관해요.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = TossGray,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(24.dp))
                TossButton(
                    text = "확인",
                    onClick = {
                        scope.launch {
                            sheetState.hide()
                            onPaymentSuccess()
                        }
                    }
                )
            } else if (isProcessing) {
                CircularProgressIndicator(color = TossBlue, modifier = Modifier.size(48.dp))
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "토스 안심 에스크로 결제 처리 중...",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "잠시만 기다려 주세요",
                    style = MaterialTheme.typography.bodyMedium,
                    color = TossGray
                )
                Spacer(modifier = Modifier.height(24.dp))
            } else {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Box(
                        modifier = Modifier
                            .size(36.dp)
                            .clip(CircleShape)
                            .background(TossBlue),
                        contentAlignment = Alignment.Center
                    ) {
                        Text("toss", color = TossSurface, fontWeight = FontWeight.Bold, fontSize = 11.sp)
                    }
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(
                        text = "토스페이 안심 원클릭 결제",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                }

                Spacer(modifier = Modifier.height(20.dp))

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(18.dp))
                        .background(TossBackground)
                        .padding(18.dp)
                ) {
                    Column {
                        Text(text = "결제 금액", style = MaterialTheme.typography.bodyMedium, color = TossGray)
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "${String.format("%,d", totalAmount)}원",
                            style = MaterialTheme.typography.headlineLarge,
                            fontWeight = FontWeight.Bold,
                            color = TossBlack
                        )
                        Spacer(modifier = Modifier.height(10.dp))
                        HorizontalDivider(color = TossBorder)
                        Spacer(modifier = Modifier.height(10.dp))
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.Shield,
                                contentDescription = null,
                                tint = TossBlue,
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "100% 에스크로 안전 보관 보증",
                                style = MaterialTheme.typography.bodySmall,
                                color = TossBlueDark,
                                fontWeight = FontWeight.SemiBold
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))

                TossButton(
                    text = "생체 인증 또는 비밀번호로 결제",
                    leadingIcon = Icons.Default.Fingerprint,
                    onClick = {
                        isProcessing = true
                        scope.launch {
                            delay(1200)
                            isProcessing = false
                            isSuccess = true
                        }
                    },
                    testTag = "confirm_biometric_pay"
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}
