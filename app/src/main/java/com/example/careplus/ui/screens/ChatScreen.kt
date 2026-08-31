package com.example.careplus.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.ReceiptLong
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import com.example.careplus.data.model.UserRole
import com.example.careplus.ui.components.ChatBubbleItem
import com.example.careplus.ui.components.SmartQuickChipsRow
import com.example.careplus.ui.components.TossBadge
import com.example.careplus.ui.components.TossPayBottomSheet
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
fun ChatScreen(
    bidId: Long,
    viewModel: CarePlusViewModel,
    onBack: () -> Unit,
    onNavigateToContract: (Long) -> Unit
) {
    val role by viewModel.currentRole.collectAsState()
    val messagesFlow = remember(bidId) { viewModel.getMessagesForBid(bidId) }
    val messages by messagesFlow.collectAsState(initial = emptyList())

    val requests by viewModel.allRequests.collectAsState()
    val activeRequest = requests.firstOrNull()

    var textInput by remember { mutableStateOf("") }
    var showPaySheet by remember { mutableStateOf(false) }

    val listState = rememberLazyListState()

    LaunchedEffect(messages.size) {
        if (messages.isNotEmpty()) {
            listState.animateScrollToItem(messages.size - 1)
        }
    }

    val dailyPrice = 140000
    val totalDays = activeRequest?.totalDays ?: 3
    val supplyPrice = dailyPrice * totalDays
    val platformFee = (supplyPrice * 0.05).toInt()
    val finalTotal = supplyPrice + platformFee

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
                    Column(modifier = Modifier.weight(1f)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = if (role == UserRole.GUARDIAN) "김*순 케어메이트" else "김민준 보호자",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            TossBadge(text = if (role == UserRole.GUARDIAN) "요양보호사 1급" else "보호자", isHighlighted = true)
                        }
                        Text(
                            text = if (role == UserRole.GUARDIAN) "아산병원 신관 7층 · 1일 140,000원" else "환자: 여성 (70대) · 거동 부축",
                            style = MaterialTheme.typography.bodySmall,
                            color = TossGray,
                            fontSize = 11.sp
                        )
                    }

                    // Invoice creation button (useful for caregiver or testing)
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(12.dp))
                            .background(TossBlueLight)
                            .clickable {
                                viewModel.issueInvoice(bidId, totalDays, finalTotal)
                            }
                            .padding(horizontal = 10.dp, vertical = 6.dp)
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.ReceiptLong,
                                contentDescription = null,
                                tint = TossBlue,
                                modifier = Modifier.size(14.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = "견적서 발행",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                color = TossBlue
                            )
                        }
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                }

                // Top Safe Privacy Banner
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color(0xFFF9FAFB))
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.Lock,
                        contentDescription = null,
                        tint = TossGray,
                        modifier = Modifier.size(14.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "개인 전화번호는 전달되지 않아요. 안심하고 대화하세요.",
                        style = MaterialTheme.typography.bodySmall,
                        color = TossGray,
                        fontSize = 11.sp
                    )
                }
            }
        },
        bottomBar = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(TossSurface)
                    .navigationBarsPadding()
                    .imePadding()
            ) {
                // Smart Question Chips
                SmartQuickChipsRow(
                    isGuardian = role == UserRole.GUARDIAN,
                    onChipSelected = { chipText ->
                        val senderRole = if (role == UserRole.GUARDIAN) "GUARDIAN" else "CAREGIVER"
                        val senderName = if (role == UserRole.GUARDIAN) "김민준 보호자" else "김*순 케어메이트"
                        viewModel.sendMessage(bidId, chipText, senderRole, senderName)
                    }
                )

                // Input Bar
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 12.dp, vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    OutlinedTextField(
                        value = textInput,
                        onValueChange = { textInput = it },
                        placeholder = { Text("메시지를 입력하세요...") },
                        modifier = Modifier
                            .weight(1f)
                            .testTag("chat_text_input"),
                        shape = RoundedCornerShape(20.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = TossBlue,
                            unfocusedBorderColor = TossBorder,
                            focusedContainerColor = TossBackground,
                            unfocusedContainerColor = TossBackground
                        ),
                        maxLines = 3
                    )

                    Spacer(modifier = Modifier.width(8.dp))

                    IconButton(
                        onClick = {
                            if (textInput.isNotBlank()) {
                                val senderRole = if (role == UserRole.GUARDIAN) "GUARDIAN" else "CAREGIVER"
                                val senderName = if (role == UserRole.GUARDIAN) "김민준 보호자" else "김*순 케어메이트"
                                viewModel.sendMessage(bidId, textInput, senderRole, senderName)
                                textInput = ""
                            }
                        },
                        modifier = Modifier
                            .size(48.dp)
                            .clip(CircleShape)
                            .background(if (textInput.isNotBlank()) TossBlue else TossBorder)
                            .testTag("send_message_button")
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.Send,
                            contentDescription = "전송",
                            tint = TossSurface,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }
            }
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            state = listState,
            contentPadding = androidx.compose.foundation.layout.PaddingValues(vertical = 12.dp)
        ) {
            items(messages) { msg ->
                val isCurrentUser = if (role == UserRole.GUARDIAN) msg.senderRole == "GUARDIAN" else msg.senderRole == "CAREGIVER"
                ChatBubbleItem(
                    message = msg,
                    isCurrentUser = isCurrentUser,
                    onPayInvoiceClick = {
                        showPaySheet = true
                    }
                )
            }
        }

        if (showPaySheet) {
            TossPayBottomSheet(
                totalAmount = finalTotal,
                onDismiss = { showPaySheet = false },
                onPaymentSuccess = {
                    showPaySheet = false
                    viewModel.payContract(
                        requestId = activeRequest?.id ?: 1,
                        bidId = bidId,
                        caregiverId = "cg_01",
                        caregiverName = "김*순 케어메이트",
                        guardianName = "김민준 보호자",
                        location = activeRequest?.hospitalName ?: "서울아산병원 신관 7층",
                        dates = "${activeRequest?.startDate ?: "2026.09.01"} ~ ${activeRequest?.endDate ?: "2026.09.04"}",
                        dailyPrice = dailyPrice,
                        totalDays = totalDays,
                        onSuccess = { contractId ->
                            onNavigateToContract(contractId)
                        }
                    )
                }
            )
        }
    }
}
