package com.example.carematch.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.carematch.data.model.NotificationItem
import com.example.carematch.ui.theme.Slate500
import com.example.carematch.ui.theme.Slate900
import com.example.carematch.ui.theme.TealPrimary
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun NotificationPopupDialog(
    notifications: List<NotificationItem>,
    onDismiss: () -> Unit,
    onMarkAllRead: () -> Unit,
    onViewFullCenter: () -> Unit
) {
    val dateFormat = SimpleDateFormat("MM.dd HH:mm", Locale.KOREA)

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("실시간 알림톡 내역", fontWeight = FontWeight.Bold)
                IconButton(onClick = onDismiss) {
                    Icon(Icons.Default.Close, contentDescription = "닫기")
                }
            }
        },
        text = {
            if (notifications.isEmpty()) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 24.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text("도착한 알림이 없습니다.", color = Slate500, fontSize = 13.sp)
                }
            } else {
                LazyColumn(
                    modifier = Modifier.heightIn(max = 360.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    items(notifications.take(5)) { noti ->
                        val timeStr = dateFormat.format(Date(noti.timestamp))
                        KakaoAlimtalkCard(
                            title = noti.title,
                            message = noti.message,
                            timestamp = timeStr
                        )
                    }
                }
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    onDismiss()
                    onViewFullCenter()
                },
                colors = ButtonDefaults.buttonColors(containerColor = TealPrimary)
            ) {
                Text("전체 알림함 이동")
            }
        },
        dismissButton = {
            if (notifications.isNotEmpty()) {
                TextButton(onClick = onMarkAllRead) {
                    Text("모두 읽음")
                }
            }
        }
    )
}
