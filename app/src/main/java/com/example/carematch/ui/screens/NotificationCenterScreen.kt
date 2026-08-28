package com.example.carematch.ui.screens

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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DoneAll
import androidx.compose.material.icons.filled.NotificationsNone
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.carematch.data.model.NotificationItem
import com.example.carematch.ui.components.KakaoAlimtalkCard
import com.example.carematch.ui.theme.Slate400
import com.example.carematch.ui.theme.Slate50
import com.example.carematch.ui.theme.Slate500
import com.example.carematch.ui.theme.Slate800
import com.example.carematch.ui.theme.Slate900
import com.example.carematch.ui.theme.TealPrimary
import com.example.carematch.ui.theme.White
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun NotificationCenterScreen(
    notifications: List<NotificationItem>,
    onMarkAllAsRead: () -> Unit,
    onNotificationClick: (NotificationItem) -> Unit,
    modifier: Modifier = Modifier
) {
    val dateFormat = SimpleDateFormat("MM.dd HH:mm", Locale.KOREA)

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(Slate50),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text(
                        text = "카카오 알림톡 및 알림함",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        color = Slate900
                    )
                    Text(
                        text = "지정 신청, 수락/거절 상태가 카카오 알림톡으로 전송됩니다",
                        style = MaterialTheme.typography.bodySmall,
                        color = Slate500
                    )
                }

                if (notifications.isNotEmpty()) {
                    TextButton(
                        onClick = onMarkAllAsRead,
                        modifier = Modifier.testTag("mark_all_read_button")
                    ) {
                        Icon(Icons.Default.DoneAll, contentDescription = null, tint = TealPrimary, modifier = Modifier.size(16.dp))
                        Text("모두 읽음", fontSize = 12.sp, color = TealPrimary, fontWeight = FontWeight.Bold)
                    }
                }
            }
        }

        if (notifications.isEmpty()) {
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = White)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(40.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.NotificationsNone,
                            contentDescription = null,
                            tint = Slate400,
                            modifier = Modifier.size(48.dp)
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                        Text(
                            text = "도착한 알림이 없습니다",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = Slate800
                        )
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = "간병 신청을 제출하거나 수락/거절 시 알림이 도착합니다.",
                            fontSize = 13.sp,
                            color = Slate500
                        )
                    }
                }
            }
        } else {
            items(notifications, key = { it.id }) { noti ->
                val timeStr = dateFormat.format(Date(noti.timestamp))
                Box(
                    modifier = Modifier.clickable { onNotificationClick(noti) }
                ) {
                    KakaoAlimtalkCard(
                        title = noti.title,
                        message = noti.message,
                        timestamp = timeStr
                    )
                }
            }
        }
    }
}
