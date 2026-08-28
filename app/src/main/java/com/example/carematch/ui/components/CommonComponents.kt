package com.example.carematch.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.SwapHoriz
import androidx.compose.material.icons.filled.Verified
import androidx.compose.material.icons.filled.Work
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.carematch.data.model.CaregiverProfile
import com.example.carematch.data.model.RequestStatus
import com.example.carematch.data.model.UserRole
import com.example.carematch.ui.theme.*
import com.example.carematch.ui.theme.CoralSecondary
import com.example.carematch.ui.theme.Slate200
import com.example.carematch.ui.theme.Slate50
import com.example.carematch.ui.theme.Slate500
import com.example.carematch.ui.theme.Slate600
import com.example.carematch.ui.theme.Slate700
import com.example.carematch.ui.theme.Slate800
import com.example.carematch.ui.theme.Slate900
import com.example.carematch.ui.theme.StatusAccepted
import com.example.carematch.ui.theme.StatusAcceptedBg
import com.example.carematch.ui.theme.StatusPending
import com.example.carematch.ui.theme.StatusPendingBg
import com.example.carematch.ui.theme.StatusRejected
import com.example.carematch.ui.theme.StatusRejectedBg
import com.example.carematch.ui.theme.TealContainer
import com.example.carematch.ui.theme.TealDark
import com.example.carematch.ui.theme.TealPrimary
import com.example.carematch.ui.theme.White

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CareMatchTopAppBar(
    currentRole: UserRole,
    unreadNotificationCount: Int,
    onSwitchRole: () -> Unit,
    onNotificationClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        color = White,
        shadowElevation = 2.dp
    ) {
        TopAppBar(
            title = {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(32.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .background(TealPrimary),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Verified,
                            contentDescription = "CareMatch",
                            tint = White,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                    Column {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = "케어매치",
                                style = MaterialTheme.typography.titleLarge,
                                fontWeight = FontWeight.Bold,
                                color = Slate900
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Surface(
                                color = if (currentRole == UserRole.GUARDIAN) TealContainer else CoralSecondary.copy(alpha = 0.15f),
                                shape = RoundedCornerShape(4.dp)
                            ) {
                                Text(
                                    text = currentRole.displayName,
                                    color = if (currentRole == UserRole.GUARDIAN) TealDark else CoralSecondary,
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                )
                            }
                        }
                        Text(
                            text = if (currentRole == UserRole.GUARDIAN) "1:1 지정 간병 매칭" else "간병인 전용 공간",
                            style = MaterialTheme.typography.labelSmall,
                            color = Slate500
                        )
                    }
                }
            },
            actions = {
                // Role Switch Toggle Button
                Surface(
                    modifier = Modifier
                        .clip(RoundedCornerShape(20.dp))
                        .clickable { onSwitchRole() }
                        .testTag("role_switch_button"),
                    color = Slate50,
                    border = androidx.compose.foundation.BorderStroke(1.dp, Slate200)
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.SwapHoriz,
                            contentDescription = "역할 전환",
                            tint = TealPrimary,
                            modifier = Modifier.size(16.dp)
                        )
                        Text(
                            text = if (currentRole == UserRole.GUARDIAN) "간병인 전환" else "보호자 전환",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Medium,
                            color = Slate700
                        )
                    }
                }

                Spacer(modifier = Modifier.width(4.dp))

                // Notification Bell
                IconButton(
                    onClick = onNotificationClick,
                    modifier = Modifier.testTag("notification_button")
                ) {
                    BadgedBox(
                        badge = {
                            if (unreadNotificationCount > 0) {
                                Badge(
                                    containerColor = CoralSecondary,
                                    contentColor = White
                                ) {
                                    Text("$unreadNotificationCount")
                                }
                            }
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Notifications,
                            contentDescription = "알림",
                            tint = Slate700
                        )
                    }
                }
            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = White
            ),
            modifier = modifier
        )
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun CaregiverCard(
    caregiver: CaregiverProfile,
    onDetailClick: () -> Unit,
    onRequestClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onDetailClick() }
            .testTag("caregiver_card_${caregiver.profileId}"),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            // Top Profile Row
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Avatar
                Box(
                    modifier = Modifier
                        .size(64.dp)
                        .clip(CircleShape)
                        .background(TealContainer)
                ) {
                    if (caregiver.avatarUrl.isNotEmpty()) {
                        AsyncImage(
                            model = caregiver.avatarUrl,
                            contentDescription = caregiver.name,
                            modifier = Modifier.fillMaxWidth(),
                            contentScale = ContentScale.Crop
                        )
                    } else {
                        Icon(
                            imageVector = Icons.Default.Person,
                            contentDescription = null,
                            tint = TealDark,
                            modifier = Modifier
                                .size(36.dp)
                                .align(Alignment.Center)
                        )
                    }
                }

                Spacer(modifier = Modifier.width(14.dp))

                // Basic Info
                Column(modifier = Modifier.weight(1f)) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Text(
                            text = caregiver.name,
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = Slate900
                        )
                        Text(
                            text = "${caregiver.gender} · ${caregiver.age}세",
                            fontSize = 12.sp,
                            color = Slate500
                        )
                    }

                    Spacer(modifier = Modifier.height(2.dp))

                    // Rating & Experience
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.Star,
                                contentDescription = "평점",
                                tint = Color(0xFFF59E0B),
                                modifier = Modifier.size(15.dp)
                            )
                            Spacer(modifier = Modifier.width(2.dp))
                            Text(
                                text = String.format("%.2f", caregiver.rating),
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Bold,
                                color = Slate800
                            )
                            Text(
                                text = " (${caregiver.reviewCount})",
                                fontSize = 12.sp,
                                color = Slate500
                            )
                        }

                        Text("·", color = Slate400)

                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.Work,
                                contentDescription = null,
                                tint = TealPrimary,
                                modifier = Modifier.size(14.dp)
                            )
                            Spacer(modifier = Modifier.width(2.dp))
                            Text(
                                text = "경력 ${caregiver.experienceYears}년",
                                fontSize = 12.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = Slate700
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(2.dp))

                    // Location
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.LocationOn,
                            contentDescription = null,
                            tint = Slate400,
                            modifier = Modifier.size(13.dp)
                        )
                        Spacer(modifier = Modifier.width(2.dp))
                        Text(
                            text = caregiver.location,
                            fontSize = 12.sp,
                            color = Slate600,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Bio
            if (caregiver.bio.isNotEmpty()) {
                Surface(
                    color = Slate50,
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "“${caregiver.bio}”",
                        style = MaterialTheme.typography.bodySmall,
                        color = Slate700,
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Badges / Certifications
            FlowRow(
                horizontalArrangement = Arrangement.spacedBy(4.dp),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                caregiver.certifications.take(2).forEach { cert ->
                    Surface(
                        color = TealContainer.copy(alpha = 0.6f),
                        shape = RoundedCornerShape(4.dp)
                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 3.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Default.CheckCircle,
                                contentDescription = null,
                                tint = TealDark,
                                modifier = Modifier.size(12.dp)
                            )
                            Spacer(modifier = Modifier.width(3.dp))
                            Text(
                                text = cert,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Medium,
                                color = TealDark
                            )
                        }
                    }
                }

                caregiver.specialties.take(3).forEach { spec ->
                    Surface(
                        color = Slate100,
                        shape = RoundedCornerShape(4.dp)
                    ) {
                        Text(
                            text = spec,
                            fontSize = 11.sp,
                            color = Slate700,
                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 3.dp)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Wage & CTA Buttons
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text(
                        text = "희망 일당",
                        fontSize = 11.sp,
                        color = Slate500
                    )
                    Row(verticalAlignment = Alignment.Bottom) {
                        Text(
                            text = String.format("%,d", caregiver.desiredDailyPay),
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold,
                            color = TealPrimary
                        )
                        Text(
                            text = "원 / 일",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Medium,
                            color = Slate600,
                            modifier = Modifier.padding(bottom = 2.dp, start = 2.dp)
                        )
                    }
                }

                Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                    OutlinedButton(
                        onClick = onDetailClick,
                        shape = RoundedCornerShape(8.dp),
                        contentPadding = androidx.compose.foundation.layout.PaddingValues(horizontal = 12.dp, vertical = 6.dp),
                        modifier = Modifier.testTag("view_detail_${caregiver.profileId}")
                    ) {
                        Text("프로필", fontSize = 13.sp)
                    }

                    Button(
                        onClick = onRequestClick,
                        shape = RoundedCornerShape(8.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = TealPrimary),
                        contentPadding = androidx.compose.foundation.layout.PaddingValues(horizontal = 14.dp, vertical = 6.dp),
                        modifier = Modifier.testTag("request_care_${caregiver.profileId}")
                    ) {
                        Text("간병 신청", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = White)
                    }
                }
            }
        }
    }
}

@Composable
fun StatusChip(status: RequestStatus) {
    val bgColor: Color = when (status) {
        RequestStatus.PENDING -> StatusPendingBg
        RequestStatus.ACCEPTED -> StatusAcceptedBg
        RequestStatus.REJECTED -> StatusRejectedBg
        RequestStatus.COMPLETED -> Slate100
    }
    val textColor: Color = when (status) {
        RequestStatus.PENDING -> StatusPending
        RequestStatus.ACCEPTED -> StatusAccepted
        RequestStatus.REJECTED -> StatusRejected
        RequestStatus.COMPLETED -> Slate600
    }

    Surface(
        color = bgColor,
        shape = RoundedCornerShape(6.dp)
    ) {
        Text(
            text = status.label,
            color = textColor,
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
        )
    }
}

@Composable
fun KakaoAlimtalkCard(
    title: String,
    message: String,
    timestamp: String,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFFEE500).copy(alpha = 0.2f)),
        border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFFEE500).copy(alpha = 0.6f))
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(20.dp)
                            .clip(CircleShape)
                            .background(Color(0xFF3C1E1E)),
                        contentAlignment = Alignment.Center
                    ) {
                        Text("K", color = Color(0xFFFEE500), fontSize = 11.sp, fontWeight = FontWeight.Bold)
                    }
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "케어매치 알림톡",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF3C1E1E)
                    )
                }
                Text(
                    text = timestamp,
                    fontSize = 11.sp,
                    color = Slate500
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = title,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = Slate900
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = message,
                fontSize = 13.sp,
                lineHeight = 18.sp,
                color = Slate800
            )
        }
    }
}
